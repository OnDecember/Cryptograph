import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public interface Cryptograph {

    default Path createNewFile(Path srcPath) throws IOException {
        String coder = "";
        if (this instanceof Encrypter) {
            coder = " Encrypted";
        } else if (this instanceof Decrypter) {
            coder = " Decrypted";
        }
        String srcFileName = srcPath.getFileName().toString();
        String srcFileExtension = srcFileName.substring(srcFileName.lastIndexOf("."));
        String newFileName = srcFileName.substring(0, srcFileName.lastIndexOf('.')) + coder + srcFileExtension;
        String parent = srcPath.getParent().toString();
        Path newFile = Path.of(parent, newFileName);
        if (Files.notExists(newFile)) {
            Files.createFile(newFile);
        }
        return newFile;
    }

    default void code(Path src, Path out, int key) throws IOException {
        if (this instanceof Decrypter) {
            key = -key;
        }
        try (FileChannel srcFile = FileChannel.open(src, StandardOpenOption.READ); FileChannel outFile = FileChannel.open(out, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            StringBuilder builder = new StringBuilder();
            while (srcFile.read(buffer) > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    builder.append((char) (buffer.get() + key));
                }
                buffer.clear();
                String text = builder.toString();
                buffer = ByteBuffer.allocate(text.getBytes().length);
                buffer.put(text.getBytes());
                buffer.flip();
                outFile.write(buffer);
                buffer.clear();
            }
        }
    }

    static Cryptograph createCryptograph(String operation) {
        return switch (operation) {
            case "-e" -> new Encrypter();
            case "-d" -> new Decrypter();
            default -> throw new IllegalArgumentException("Incorrect operation");
        };
    }
}