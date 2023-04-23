import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Decrypter implements Cryptograph {
    @Override
    public Path createNewFile(Path srcPath) throws IOException {
        String srcFileName = srcPath.getFileName().toString();
        String srcFileExtension = srcFileName.substring(srcFileName.lastIndexOf("."));
        String newFileName = srcFileName.substring(0, srcFileName.lastIndexOf('.')) + " Decrypt" + srcFileExtension;
        String parent = srcPath.getParent().toString();
        Path newFile = Path.of(parent, newFileName);
        if (Files.notExists(newFile)) {
            Files.createFile(newFile);
        }
        return newFile;
    }

    @Override
    public void code(Path src, Path out, int key) throws IOException {
        key = checkKey(key);
        try (BufferedReader srcFile = new BufferedReader(new FileReader(src.toString()), 8192);
             BufferedWriter outFile = new BufferedWriter(new FileWriter(out.toString(), true))) {
            StringBuilder builder = new StringBuilder();
            while (srcFile.ready()) {
                int symbol;
                while ((symbol = srcFile.read()) != -1) {
                    builder.append((char) (symbol + (-key)));
                }
                String text = builder.toString();
                outFile.write(text);
            }
        }
    }
}
