package com.portaudio;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public final class FileUtils {
    static Path dir;

    static {
        try {
            dir = Files.createTempDirectory("native-libs");
            dir.toFile().deleteOnExit();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    static void load(String resource) {
        try {
            Path dest = Files.createFile(dir.resolve(Paths.get(resource).getFileName()));
            try (InputStream stream = FileUtils.class.getResourceAsStream("/" + resource)) {
                Files.copy(Objects.requireNonNull(stream, "Failed to copy resource: " + resource), dest, StandardCopyOption.REPLACE_EXISTING);
                System.load(dest.toAbsolutePath().toString());
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}