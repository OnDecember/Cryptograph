import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an operation: [-e] [-d]");
        String operation = scanner.nextLine();
        Cryptograph cryptograph = Cryptograph.createCryptograph(operation);
        System.out.print("Path: ");
        String path = scanner.nextLine();
        Path srcFile = Path.of(path);
        Path outFile = cryptograph.createNewFile(srcFile);
        System.out.print("Key: ");
        int key = scanner.nextInt();
        cryptograph.code(srcFile, outFile, key);
    }
}