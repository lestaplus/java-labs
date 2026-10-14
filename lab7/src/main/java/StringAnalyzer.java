import java.util.Arrays;

public class StringAnalyzer {
    public static double calcAvgStrLength(String[] words) {
        if (words == null || words.length == 0) return 0.0;

        return Arrays.stream(words)
                .mapToDouble(String::length)
                .average()
                .orElse(0.0);
    }

    public static String[] stringsDiffFromAvg(String[] words, boolean greaterThanAvg) {
        if (words == null || words.length == 0) return new String[0];

        double avgStrLength = calcAvgStrLength(words);

        return Arrays.stream(words)
                .filter(s -> matches(s, avgStrLength, greaterThanAvg))
                .toArray(String[]::new);
    }

    private static boolean matches(String str, double avg, boolean greaterThanAvg) {
        if (str == null) return false;

        return greaterThanAvg ? str.length() > avg : str.length() < avg;
    }
}
