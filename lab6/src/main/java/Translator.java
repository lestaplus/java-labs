import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
    private final Pattern engPattern = Pattern.compile("^[a-zA-Z]+$");
    private final Pattern ukrPattern = Pattern.compile("^[а-яА-ЯёЁЄєІіЇїҐґ'-]+$");

    private final Pattern wordPattern = Pattern.compile("[a-zA-Zа-яА-ЯёЁЄєІіЇїҐґ'-]+");

    private final HashMap<String, String> dict;

    public Translator() {
        dict = new HashMap<>();
    }

    public boolean addWordTranslation(String word, String translatedWord) {
        if (word == null || translatedWord == null ||
                !engPattern.matcher(word).matches() ||
                !ukrPattern.matcher(translatedWord).matches()) return false;

        dict.put(word.toLowerCase(), translatedWord.toLowerCase());
        dict.put(translatedWord.toLowerCase(), word.toLowerCase());
        return true;
    }

    public String translate(String line) {
        if (line == null || line.trim().isEmpty()) return "";

        Matcher matcher = wordPattern.matcher(line);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            String word = matcher.group();
            String lowerWord = word.toLowerCase();

            String translatedWord = dict.getOrDefault(lowerWord, lowerWord);

            if (!translatedWord.isEmpty() && Character.isUpperCase(word.charAt(0))) {
                translatedWord = translatedWord.substring(0, 1).toUpperCase() + translatedWord.substring(1);
            }

            matcher.appendReplacement(sb, Matcher.quoteReplacement(translatedWord));
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    public String printDictionary() {
        if (dict.isEmpty()) return "(empty)";

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : dict.entrySet()) {
            if (!engPattern.matcher(entry.getKey()).matches()) continue;
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }

    public boolean loadBasicWords() {
        if (!dict.isEmpty()) return false;

        addWordTranslation("hello", "привіт");
        addWordTranslation("good", "добрий");
        addWordTranslation("day", "день");
        addWordTranslation("morning", "ранок");
        addWordTranslation("evening", "вечір");
        addWordTranslation("anatolii", "анатолій");
        addWordTranslation("i", "я");
        addWordTranslation("you", "ти");
        addWordTranslation("we", "ми");
        addWordTranslation("they", "вони");
        addWordTranslation("my", "моє");
        addWordTranslation("your", "твій");
        addWordTranslation("is", "є");
        addWordTranslation("have", "мати");
        addWordTranslation("like", "любити");
        addWordTranslation("love", "любити");
        addWordTranslation("study", "вчитися");
        addWordTranslation("write", "писати");
        addWordTranslation("name", "ім'я");
        addWordTranslation("friend", "друг");
        addWordTranslation("student", "студент");
        addWordTranslation("programming", "програмування");
        addWordTranslation("language", "мова");
        addWordTranslation("world", "світ");
        addWordTranslation("summer", "літо");
        addWordTranslation("book", "книга");
        addWordTranslation("code", "код");
        addWordTranslation("big", "великий");
        addWordTranslation("small", "маленький");
        addWordTranslation("new", "новий");
        addWordTranslation("warm", "теплий");

        return true;
    }

    public HashMap<String, String> getDict() {
        return dict;
    }
}
