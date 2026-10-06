import java.util.Scanner;

public class Menu {
    private final Translator translator;
    private final Scanner sc = new Scanner(System.in);

    public Menu(Translator translator) {
        this.translator = translator;

    }

    public void execute() {
        int option;
        while (true) {
            System.out.print("""
                \n--- Menu ---
                1 - Add word translation
                2 - Translate a word or phrase
                3 - Print dictionary
                4 - Fill dictionary with basic words
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

                handleSelection(option);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    private void handleSelection(int option) {
        switch (option) {
            case 1:
                String engWord = useScanner("Enter a word in English: ");
                String ukrWord = useScanner("Enter a word in Ukrainian: ");

                boolean isAdded = translator.addWordTranslation(engWord, ukrWord);
                if (isAdded) {
                    System.out.println("Word translations added successfully.");
                } else {
                    System.out.println("Word patterns are incorrect.");
                }
                break;
            case 2:
                if (translator.getDict().isEmpty()) {
                    System.out.println("Dictionary is empty.");
                    break;
                }
                String input = useScanner("Enter a word or phrase to translate: ");
                String phrase = translator.translate(input);
                System.out.println(phrase);
                break;
            case 3:
                System.out.println("Words dictionary: ");
                String dict = translator.printDictionary();
                System.out.println(dict);
                break;
            case 4:
                boolean isLoaded = translator.loadBasicWords();
                if (isLoaded) {
                    System.out.println("Basic words loaded successfully.");
                } else {
                    System.out.println("Dictionary already contains words.");
                }
                break;
        }
    }

    public String useScanner(String question) {
        String result;
        while (true) {
            System.out.print(question);

            String line = sc.nextLine();
            if (!line.trim().isEmpty()) {
                result = line;
                break;
            }

            System.out.println("Invalid input.");
        }
        return result;
    }
}
