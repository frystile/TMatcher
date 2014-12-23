import javafx.util.Pair;

import java.util.ArrayList;

public class TDynamicTemplateMatcher {
    ArrayList<Bor> bors;
    ArrayList<ArrayList<Pair<String, Integer>>> index;
    ArrayList<String> strings;
    ArrayList<Boolean> isEmpty;
    int count;

    TDynamicTemplateMatcher() {
        bors = new ArrayList<>();
        index = new ArrayList<>();
        strings = new ArrayList<>();
        isEmpty = new ArrayList<>();
        count = 0;
    }

    int addString(String template) {
        if (strings.contains(template)) {
            throw new IllegalArgumentException("This string has been added");
        }
        strings.add(template);

        Bor tmp = new Bor();
        ArrayList<Pair<String, Integer>> addedList = new ArrayList<>();
        addedList.add(new Pair<>(template, count));

        int num = 0;
        while (num < count && !isEmpty.get(num)) {
            addedList.addAll(index.get(num));
            index.get(num).clear();
            isEmpty.set(num, true);
            ++num;
        }

        if (num == count) {
            bors.add(null);
            index.add(null);
            isEmpty.add(false);
            ++count;
        }

        for (Pair<String, Integer> pair : addedList) {
            tmp.addString(pair.getKey());
        }
        tmp.getSuffLink();
        bors.set(num, tmp);
        index.set(num, addedList);
        isEmpty.set(num, false);

        return strings.size() - 1;
    }

    ArrayList<Pair<Integer, Integer>> match(ICharStream stream) {
        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        char c;
        while (!stream.isEmpty()) {
            c = stream.getChar();
            for (int i = 0; i < count; ++i) {
                if (isEmpty.get(i)) {
                    continue;
                }
                for (Pair<Integer, Integer> pair : bors.get(i).match(c)) {
                    result.add(new Pair<>(pair.getKey(), index.get(i).get(pair.getValue()).getValue()));
                }
            }
        }
        return result;
    }
}
