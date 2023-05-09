package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtility {
    public Path createNewFile(Path srcPath, String operation, int key) throws IOException {
        String suffix = "";
        if ("-e".equals(operation)) {
            suffix = " [ENCRYPTED]";
        } else if ("-d".equals(operation)) {
            suffix = " [DECRYPTED]";
        } else if ("-bf".equals(operation)) {
            suffix = "(B key-" + key + ")";
        }
        String srcFile = srcPath.toString();
        int dotIndex = srcFile.lastIndexOf('.');
        String newFileName = srcFile.substring(0, dotIndex) + suffix + srcFile.substring(dotIndex);
        Path outFile = Path.of(newFileName);
        if (Files.notExists(outFile)) {
            Files.createFile(outFile);
        }
        return outFile;
    }
}
