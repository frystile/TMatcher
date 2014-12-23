import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Scanner;

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

    ArrayList<Integer> match(ICharStream stream) {
        String input = readStream(stream);
        ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
        try {
            list = matcher.match(new ICharStream(new Scanner(input)));
        } catch (IllegalStateException e) {

        }

        int[][] counter = new int[input.length()][mini.length];

        for (Pair<Integer, Integer> pair : list) {
            counter[pair.getKey()][pair.getValue()] = 1;
        }

        for (int i = 0; i <= input.length() - template.length(); ++i) {
            int index = i - 1;
            boolean flag = true;
            for (int k = 0; k < mini.length; ++k) {
                if (mini[k].length() == 0) {
                    ++index;
                    continue;
                }
                index += mini[k].length();
                if (counter[index][id[k]] != 1) {
                    flag = false;
                    break;
                }
                ++index;
            }

            if (flag) {
                result.add(i + template.length() - 1);
            }
        }

        return result;
    }

    String readStream(ICharStream stream) {
        StringBuilder builder = new StringBuilder();
        while (!stream.isEmpty()) {
            builder.append(stream.getChar());
        }
        return builder.toString();
    }
}
