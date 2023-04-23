import java.io.IOException;
import java.nio.file.Path;

public interface Cryptograph {

    Path createNewFile(Path srcPath) throws IOException;

    void code(Path src, Path out, int key) throws IOException;

    static Cryptograph createCryptograph(String operation) {
        return switch (operation) {
            case "-e" -> new Encrypter();
            case "-d" -> new Decrypter();
            case "-bf" -> new BruteForce();
            default -> throw new IllegalArgumentException("Incorrect operation");
        };
    }

    default int checkKey(int key) {
        return Math.abs(key) % 50;
    }
}