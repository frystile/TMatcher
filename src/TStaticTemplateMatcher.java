import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TStaticTemplateMatcher {
    ArrayList<String> templates;
    Bor bor;

    TStaticTemplateMatcher() {
        templates = new ArrayList<String>();
        bor = new Bor();
    }

    int add(String template) {
        if (templates.contains(template)) {
            throw new IllegalArgumentException("This string has already added");
        }

        templates.add(template);
        return templates.size() - 1;
    }

    ArrayList<Pair<Integer, Integer>> match(ICharStream stream) throws IllegalStateException, IllegalAccessException {
        if (templates.size() == 0) {
            throw new IllegalStateException("There are no templates");
        }

        for (String template : templates) {
            bor.addString(template);
        }

        return bor.match(stream);
    }

    boolean contains(String s) {
        return templates.contains(s);
    }

    int get(String s) {
        return templates.indexOf(s);
    }
}
