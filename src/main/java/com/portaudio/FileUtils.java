package com.portaudio;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.logging.Logger;

public final class FileUtils {
    private FileUtils() { /* no init */ }

    static Logger logger = Logger.getLogger(FileUtils.class.getCanonicalName());

    static Path dir = null;

    static private Path getTempDirectory() {
        if (dir != null) {
            return dir;
        }
        try {
            dir = Files.createTempDirectory("native-libs");
            dir.toFile().deleteOnExit();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        return dir;
    }

    // Context: we used to extract libraries from within the JAR resources into a temporary directory
    //  We're keeping this for development, but for production we're switching to asking Conveyor
    //  to extract all the libraries to make the app slightly faster and reduce runtime errors.
    static void load(String resource) {

        String libraryNameWithExtension = new File(resource).getName();
        String libraryName;
        if (libraryNameWithExtension.contains("."))
            libraryName = libraryNameWithExtension.substring(0, libraryNameWithExtension.lastIndexOf('.'));
        else
            libraryName = libraryNameWithExtension;

        String appDir = System.getProperty("app.dir");
        if (appDir != null) {
            // && means app.dir
            // Windows: -Djava.library.path=&&;&&..\bin
            // macOS:   -Djava.library.path=&&/../runtime/Contents/Home/lib:&&
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                if (tryLoadLibrary(appDir, libraryNameWithExtension)) return;
                if (tryLoadLibrary(appDir, "..\\bin", libraryNameWithExtension)) return;
            } else {
                if (tryLoadLibrary(appDir, "../runtime/Contents/Home/lib", libraryNameWithExtension)) return;
                if (tryLoadLibrary(appDir, libraryNameWithExtension)) return;
            }
        }

        try {
            System.loadLibrary(libraryName);
            return;
        } catch (Throwable t) {
            // Ignore the error, fall back to loading from resource (used in development)
            logger.warning("Error loading " + libraryName + ". Will try to extract it from resources.");
        }

        try {
            Path dest = Files.createFile(getTempDirectory().resolve(Paths.get(resource).getFileName()));
            try (InputStream stream = FileUtils.class.getResourceAsStream("/" + resource)) {
                Files.copy(Objects.requireNonNull(stream, "Failed to copy resource: " + resource), dest, StandardCopyOption.REPLACE_EXISTING);
                System.load(dest.toAbsolutePath().toString());
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static boolean tryLoadLibrary(String... pathElements) {
        File file = null;
        for (String pathElement : pathElements) {
            if (file == null)
                file = new File(pathElement);
            else
                file = new File(file, pathElement);
        }
        if (file == null) {
            logger.severe("Error loading library as the absolute path is null. This was absolutely unexpected and is probably due to bad application code. May continue trying with other paths.");
            return false;
        }
        try {
            System.load(file.getAbsolutePath());
            return true;
        } catch (Throwable ex) {
            logger.warning("Error loading " + file.getAbsolutePath() + ". May continue trying with other paths.");
        }
        return false;
    }
}