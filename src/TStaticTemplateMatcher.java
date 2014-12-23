import javafx.util.Pair;
import java.util.ArrayList;

public class TStaticTemplateMatcher {
    ArrayList<String> templates;
    Bor bor;

    TStaticTemplateMatcher() {
        templates = new ArrayList<String>();

    }

    int add(String template) {
        if (templates.contains(template)) {
            throw new IllegalArgumentException("This string has already added");
        }

        templates.add(template);
        return templates.size() - 1;
    }

    ArrayList<Pair<Integer, Integer>> match(ICharStream stream) throws IllegalStateException {
        if (templates.size() == 0) {
            throw new IllegalStateException("There are no templates");
        }
        bor = new Bor();

        for (String template : templates) {
            bor.addString(template);
        }

        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        bor.getSuffLink();
        while (!stream.isEmpty()) {
            char c = stream.getChar();
            result.addAll(bor.match(c));
        }
        return result;
    }

    boolean contains(String s) {
        return templates.contains(s);
    }

    int get(String s) {
        return templates.indexOf(s);
    }
}
