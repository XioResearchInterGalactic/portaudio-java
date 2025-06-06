package com.portaudio;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public final class FileUtils {
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
        try {
            String libraryNameWithExtension = new File(resource).getName();
            String libraryName;
            if (libraryNameWithExtension.contains("."))
                libraryName = libraryNameWithExtension.substring(0, libraryNameWithExtension.lastIndexOf('.'));
            else
                libraryName = libraryNameWithExtension;
            System.loadLibrary(libraryName);
        } catch (Throwable t) {
            // Ignore the error, fall back to loading from resource (used in development)
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
}