import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class BruteForce implements Cryptograph {
    @Override
    public Path createNewFile(Path srcPath) throws IOException {
        String srcFileName = srcPath.getFileName().toString();
        String srcFileExtension = srcFileName.substring(srcFileName.lastIndexOf("."));
        String newFileName = srcFileName.substring(0, srcFileName.lastIndexOf('.')) + " Brute Force" + srcFileExtension;
        String parent = srcPath.getParent().toString();
        Path newFile = Path.of(parent, newFileName);
        if (Files.notExists(newFile)) {
            Files.createFile(newFile);
        }
        return newFile;
    }

    public void bruteForce(Path src, Path out) throws IOException {
        int key = 0;
        try (BufferedReader srcFile = new BufferedReader(new FileReader(src.toString()), 8192)) {
            StringBuilder builder = new StringBuilder();
            out:
            while (srcFile.ready()) {
                char[] line = srcFile.readLine().toCharArray();
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j < line.length; j++) {
                            builder.append((char) (line[j] + (-i)));
                    }
                    String text = builder.toString();
                    if (text.contains(",") && text.contains(" ")) {
                        key = i;
                        break out;
                    }
                    builder = new StringBuilder();
                }
            }
        }
        code(src, out, key);
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
