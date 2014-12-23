import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class T2DSingleTemplateMatcher {
    private ArrayList<String> matrix;
    private int mSize;

    void set(ArrayList<String> m) {
        if (m == null) {
            throw new NullPointerException("Null string");
        }
        int length = m.get(0).length();
        for (String list : m) {
            if (list.length() != length) {
                throw new IllegalArgumentException("It is not a matrix");
            }
        }
        matrix = new ArrayList<>(m);
        mSize = length;
    }

    ArrayList<Pair<Integer, Integer>> match(ArrayList<String> template) {
        if (template == null) {
            throw new NullPointerException("Null string");
        }
        int length = template.get(0).length();
        for (String list : template) {
            if (list.length() != length) {
                throw new IllegalArgumentException("It is not a matrix");
            }
        }

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ArrayList<Integer> seq = new ArrayList<Integer>();
        TStaticTemplateMatcher matcher = new TStaticTemplateMatcher();

        for (String aTemplate : template) {
            if (map.containsKey(aTemplate)) {
                seq.add(map.get(aTemplate));
            } else {
                int id = matcher.add(aTemplate);
                map.put(aTemplate, id);
                seq.add(id);
            }
        }
        seq.add(-1);

        ArrayList<ArrayList<Integer>> result = new ArrayList<>(matrix.size());

        for (int i = 0; i < matrix.size(); ++i) {
            result.add(new ArrayList<Integer>(mSize));
            for (int k = 0; k < mSize; ++k) {
                result.get(i).add(-1);
            }

            for (Pair<Integer, Integer> pair : matcher.match(new ICharStream(matrix.get(i)))) {
                result.get(i).set(pair.getKey(), pair.getValue());
            }
        }

        int[] last = new int[seq.size()];

        for (int i = 1; i < last.length; ++i) {
            last[i] = last[i - 1];
            while (last[i] > 0 && !(seq.get(last[i]).equals(seq.get(i)))) {
                last[i] = last[last[i] - 1];
            }
            if (seq.get(last[i]).equals(seq.get(i))) {
                ++last[i];
            }
        }

        int[] prev = new int[mSize];
        ArrayList<Pair<Integer, Integer>> ans = new ArrayList<>();

        for (int k = 0; k < matrix.size(); ++k) {
            for (int i = 0; i < mSize; ++i) {

                while ((prev[i] == seq.size()) || (prev[i] > 0 && !seq.get(prev[i]).equals(result.get(k).get(i)))) {
                    prev[i] = last[prev[i] - 1];
                }
                if (seq.get(prev[i]).equals(result.get(k).get(i))) {
                    ++prev[i];
                }

                if (prev[i] == seq.size() - 1) {
                    ans.add(new Pair(k, i));
                }
            }
        }
        return ans;
    }
}
