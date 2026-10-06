import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Task 1
        String filePath = null;
        while (!isPathValid(filePath)) {
            System.out.print("Enter valid relative file path: ");
            filePath = sc.nextLine();
        }

        FileHandler fileHandler = new FileHandler(filePath);

        int linesCount = fileHandler.enterLinesCount();
        fileHandler.writeLines(linesCount);

        String maxLine = fileHandler.maxWordsLine();
        System.out.println("Max words in line: " + (maxLine != null ? maxLine : "(empty)") + "\n");

        // Task 3
        String originalFile = "original.txt";
        String encryptedFile = "encrypted.txt";
        String decryptedFile = "decrypted.txt";

        char keyChar = 'L';

        try {
            String exampleText = "Hello World! This is an example text.";
            try (FileWriter writer = new FileWriter(originalFile)) {
                writer.write(exampleText);
            }
            System.out.println("Original file: " + originalFile);
            System.out.println("Content:\n" + exampleText + "\n");

            System.out.println("Encrypting file with key '" + keyChar + "'...");
            CryptoFileHandler.encryptFile(originalFile, encryptedFile, keyChar);

            String encryptedContent = readFile(encryptedFile);
            System.out.println("Encrypted content:\n" + encryptedContent + "\n");

            System.out.println("Decrypting file with key '" + keyChar + "'...");
            CryptoFileHandler.decryptFile(encryptedFile, decryptedFile, keyChar);

            String decryptedContent = readFile(decryptedFile);
            System.out.println("Decrypted content:\n" + decryptedContent + "\n");

            if (exampleText.equals(decryptedContent)) {
                System.out.println("Decrypting successful! Text matches.\n");
            } else {
                System.out.println("Error. Texts do not match.\n");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Task 4
        String url;
        do {
            System.out.print("Enter valid URL: ");
            url = sc.nextLine();

        } while (!HtmlTagCounter.isValidUrl(url));

        System.out.println("HTML tags from url: " + url);
        HtmlTagCounter.countTagsFromUrl(url);
    }

    public static boolean isPathValid(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        try {
            File file = new File(filePath);
            file.getCanonicalPath();
            return true;
        } catch (Exception e) {
            System.out.println("Invalid file path!");
            return false;
        }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!sb.isEmpty()) {
                    sb.append(System.lineSeparator());
                }
                sb.append(line);
            }
        }
        return sb.toString();
    }
}

