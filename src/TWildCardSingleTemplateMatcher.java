import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TWildCardSingleTemplateMatcher {
    String template;
    String[] mini;
    int[] id;
    TStaticTemplateMatcher matcher;

    TWildCardSingleTemplateMatcher(String s) {
        template = s;
        mini = template.split("\\?");
        matcher = new TStaticTemplateMatcher();

        id = new int[mini.length];
        for (int i = 0; i < mini.length; ++i) {
            if (mini[i].length() == 0) {
                continue;
            }
            if (matcher.contains(mini[i])) {
                id[i] = matcher.get(mini[i]);
            } else {
                id[i] = matcher.add(mini[i]);
            }
        }

    }

    ArrayList<Integer> result = new ArrayList<Integer>();

    ArrayList<Integer> match(ICharStream stream) throws IllegalAccessException {
        ArrayList<Pair<Integer, Integer>> list = matcher.match(stream);
        for (Pair<Integer, Integer> pair : list) {
            if (pair.getValue() == 0) {
                boolean flag = true;

                int index = pair.getKey();

                for (int i = 1; i < mini.length; ++i) {
                    if (mini[i].length() == 0) {
                        ++index;
                        continue;
                    }

                    if (!list.contains(new Pair<>(index + 1 + mini[i].length(), id[i]))) {
                        flag = false;
                        break;
                    }

                    index += 1 + mini[i].length();
                }

                if (flag) {
                    result.add(index);
                }
            }
        }

        return result;
    }
}
