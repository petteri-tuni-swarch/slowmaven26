package fi.arch26.taxicars;

import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class PackagedAppIT {
    @TempDir Path directory;

    @Test void launchesExecutableJarWithItsDependencies() throws Exception {
        Path output = directory.resolve("app.log");
        String javaExecutable = Path.of(System.getProperty("java.home"), "bin", "java").toString();
        Process process = new ProcessBuilder(javaExecutable, "-jar", System.getProperty("app.jar"))
                .redirectErrorStream(true).redirectOutput(output.toFile()).start();
        try {
            assertTrue(process.waitFor(20, TimeUnit.SECONDS), "Application did not exit");
            String text = java.nio.file.Files.readString(output, StandardCharsets.UTF_8);
            assertEquals(0, process.exitValue(), text);
            assertTrue(text.contains("100 km will cost 7.30"), text);
            assertTrue(text.contains("Car not found: ABC-12"), text);
        } finally {
            process.destroyForcibly();
        }
    }
}
