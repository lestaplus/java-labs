public class StringAnalyzer {
    public static double avgStrLength(String[] arr) {
        if (arr == null || arr.length == 0) return 0.0;

        int totalLength = 0;

        for (String str : arr) {
            if (str != null) {
                totalLength += str.length();
            }
        }

        return (double) totalLength / arr.length;
    }

    public static String[] stringsDiffFromAvg(String[] arr, boolean greaterThanAvg) {
        if (arr == null || arr.length == 0) return new String[0];

        double avgStrLength = avgStrLength(arr);

        int count = 0;
        for (String str : arr) {
            if (matches(str, avgStrLength, greaterThanAvg)) {
                count++;
            }
        }

        int index = 0;
        String[] result = new String[count];

        for (String str : arr) {
            if (matches(str, avgStrLength, greaterThanAvg)) {
                result[index++] = str;
            }
        }

        return result;
    }

    private static boolean matches(String str, double avg, boolean greaterThanAvg) {
        if (str == null) return false;

        return greaterThanAvg ? str.length() > avg : str.length() < avg;
    }
}
