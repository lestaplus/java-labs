import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlTagCounter {
    private static final String URL_REGEX = "^https?://.*";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX, Pattern.CASE_INSENSITIVE);

    public static void countTagsFromUrl(String url) {
        Map<String, Integer> tagMap = new TreeMap<>();

        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            StringBuilder pageContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    pageContent.append(inputLine).append(" ");
                }
            }

            Pattern pattern = Pattern.compile("<\\s*/?([a-zA-Z][a-zA-Z0-9]*)\\b[^>]*>");
            Matcher matcher = pattern.matcher(pageContent.toString());

            while (matcher.find()) {
                String tagName = matcher.group(1).toLowerCase();
                tagMap.put(tagName, tagMap.getOrDefault(tagName, 0) + 1);
            }

            System.out.println("\nTags in lexicographical order:");
            for (Map.Entry<String, Integer> entry : tagMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("\nTags by frequency:");
            List<Map.Entry<String, Integer>> list = new ArrayList<>(tagMap.entrySet());
            list.sort(Map.Entry.comparingByValue());

            for (Map.Entry<String, Integer> entry : list) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error reading URL: " + e.getMessage());
        }
    }

    public static boolean isValidUrl(String url) {
        if (url == null) return false;

        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }
}
