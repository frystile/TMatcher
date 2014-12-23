import javafx.util.Pair;
import java.util.ArrayList;

public class TNaiveTemplateMatcher {
    ArrayList<String> strings;
    int maxLength;

    TNaiveTemplateMatcher() {
        strings = new ArrayList<>();
        maxLength = 0;
    }

    int addString(String template) {
        if (strings.contains(template)) {
            throw new IllegalArgumentException("This string has been added");
        }

        strings.add(template);
        if (template.length() > maxLength) {
            maxLength = template.length();
        }
        return strings.size() - 1;
    }

    ArrayList<Pair<Integer, Integer>> match(ICharStream stream) {
        char c;
        StringBuilder tmp = new StringBuilder();
        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        int index = 0;
        while (!stream.isEmpty()) {
            c = stream.getChar();
            tmp.append(c);

            if (tmp.length() > maxLength) {
                tmp.deleteCharAt(0);
            }

            for (int i = 0; i < strings.size(); ++i) {
                String str = strings.get(i);
                if (str.length() > tmp.length()) {
                    continue;
                }
                if (tmp.substring(tmp.length() - str.length(), tmp.length()).equals(str)) {
                    result.add(new Pair<>(index, i));
                }
            }
            ++index;
        }
        return result;
    }
}
