import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);

    public void executeMenu(List<String> words) {
        int option;
        while (true) {
            System.out.print("""
                    \n--- Menu ---
                    1 - Add custom word
                    2 - Add basic words
                    3 - Print words shorter than average
                    4 - Print words longer than average
                    0 - Exit
                    Enter menu item number (0..4):\s""");

            String line = sc.nextLine();
            if (line.trim().isEmpty()) {
                System.out.println("Invalid input.");
                continue;
            }

            try {
                option = Integer.parseInt(line);

                if (option < 0 || option > 4) {
                    System.out.println("Invalid input.");
                    continue;
                }

                if (option == 0) {
                    System.out.println("Exiting...");
                    return;
                }

                executeMenuOption(option, words);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private void executeMenuOption(int option, List<String> words) {
        switch (option) {
            case 1 -> {
                while (true) {
                    System.out.print("\nEnter a word: ");
                    String word = sc.nextLine().trim();

                    if (word.isEmpty()) {
                        System.out.println("Invalid input.");
                        continue;
                    }

                    words.add(word);
                    System.out.println("Word added successfully.");
                    break;
                }
            }
            case 2 -> {
                addBasicWords(words);
                System.out.println("\nBasic words added successfully.");
            }
            case 3 -> {
                if (words.isEmpty()) {
                    System.out.println("\nNo words in list.");
                    break;
                }

                String[] wordsArray = words.toArray(String[]::new);
                String[] shorterWordsArr = StringAnalyzer.stringsDiffFromAvg(wordsArray, false);

                printAvgInfo(wordsArray);

                System.out.println("\nWords shorter than average:");
                printWords(shorterWordsArr);
            }
            case 4 -> {
                if (words.isEmpty()) {
                    System.out.println("\nNo words in list.");
                    break;
                }

                String[] wordsArray = words.toArray(String[]::new);
                String[] longerWordsArr = StringAnalyzer.stringsDiffFromAvg(wordsArray, true);

                printAvgInfo(wordsArray);

                System.out.println("\nWords longer than average:");
                printWords(longerWordsArr);
            }
        }
    }

    private void addBasicWords(List<String> words) {
        words.addAll(List.of(
                "Backend", "Code", "Database", "Developer",
                "Orange", "Smartphone", "Coffee", "Framework",
                "Project", "Java", "Git", "Database",
                "Software", "UI", "Spring", "Laptop"
        ));
    }

    private void printWords(String[] words) {
        if (words == null || words.length == 0) {
            System.out.println("(empty)");
            return;
        }

        Arrays.stream(words)
                .sorted(Comparator.comparingInt(String::length))
                .forEach(s -> System.out.println(s + " (length: " + s.length() + ")"));
    }

    private void printAvgInfo(String[] words) {
        double avg = StringAnalyzer.calcAvgStrLength(words);
        System.out.println("\nAverage word length: " + String.format("%.2f", avg));
    }
}
