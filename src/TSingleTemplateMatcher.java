import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TSingleTemplateMatcher {
    ArrayList<Integer> result;
    StringBuilder template;
    int[] pref;

    TSingleTemplateMatcher(String s) {
        if (s == null) {
            throw new NullPointerException("Null template");
        }
        result = new ArrayList<Integer>();
        template = new StringBuilder(s);
        setPref();
    }

    ArrayList<Integer> match(ICharStream stream) {
        this.setPref();

        int index = 0;
        int last = 0;
        String s = template.toString() + (char) 1;

        while (!stream.isEmpty()) {
            char next = stream.getChar();
            if (next < 32 || next > 255) {
                throw new IllegalArgumentException("Incorrect symbol");
            }

            while (last > 0 && s.charAt(last) != next) {
                last = pref[last];
            }

            if (s.charAt(last) == next) {
                ++last;
            }

            if (last == s.length() - 1) {
                result.add(index);
            }
            ++index;
        }
        return result;
    }

    void setPref() {
        String s = template.toString() + (char) 1;
        pref = new int[s.length()];

        for (int i = 1; i < s.length(); ++i) {
            while (pref[i] > 0 && s.charAt(pref[i]) != s.charAt(i)) {
                pref[i] = pref[pref[i]];
            }

            if (s.charAt(i) == s.charAt(pref[i])) {
                ++pref[i];
            }
        }
    }

    void appendCharToTemplate(char c) {
        if (c < 32 || c > 255) {
            throw new IllegalArgumentException("Incorrect symbol");
        }

        template.append(c);
    }

    void prependCharToTemplate(char c) {
        if (c < 32 || c > 255) {
            throw new IllegalArgumentException("Incorrect symbol");
        }

        template.insert(0, c);
    }
}