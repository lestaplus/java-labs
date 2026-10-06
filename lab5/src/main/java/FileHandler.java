import java.io.*;
import java.util.Scanner;

public class FileHandler {
    private final Scanner sc = new Scanner(System.in);
    private final String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public int enterLinesCount() {
        int linesCount;

        while (true) {
            System.out.print("Enter number of lines greater than 0: ");

            String line = sc.nextLine();

            if (line.trim().isEmpty()) {
                System.out.println("Wrong input!");
                continue;
            }

            try {
                linesCount = Integer.parseInt(line);

                if (linesCount < 1) {
                    System.out.println("Wrong input!");
                    continue;
                }

                return linesCount;
            } catch (NumberFormatException e) {
                System.out.println("Wrong input!");
            }
        }
    }

    public void writeLines(int linesCount) {
        try (FileWriter fw = new FileWriter(filePath)) {
            int counter = 0;
            while (counter < linesCount) {
                System.out.print("Enter line " + (++counter) + ": ");
                String line = sc.nextLine();
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String maxWordsLine() {
        int max = 0;
        String maxLine = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                int count = 0;

                for (String word : words) {
                    if (!word.isEmpty()) count++;
                }
                if (count > max) {
                    max = count;
                    maxLine = line;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return maxLine;
    }
}
