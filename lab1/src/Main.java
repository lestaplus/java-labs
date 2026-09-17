public class Main {
    public static void main(String[] args) {
        String[] wordsArray = {
                "Backend", "Code", "Database", "Developer", "Orange",
                "Smartphone", "Coffee", "Framework", "Project", "Java",
                "Git", "Software", "UI", "Spring", "Laptop"
        };

        String[] longerStrings = StringAnalyzer.stringsDiffFromAvg(wordsArray, true);
        String[] shorterStrings = StringAnalyzer.stringsDiffFromAvg(wordsArray, false);

        double avg = StringAnalyzer.avgStrLength(wordsArray);
        System.out.println("Average string length: " + avg);
        System.out.println();

        System.out.println("Strings longer than average");
        for (String str : longerStrings) {
            System.out.println(str + " (length: " +  str.length() + ")");
        }
        System.out.println();

        System.out.println("Strings shorter than average");
        for (String str : shorterStrings) {
            System.out.println(str + " (length: " +  str.length() + ")");
        }
    }
}