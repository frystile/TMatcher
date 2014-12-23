import java.util.ArrayList;
public class TSingleTemplateMatcher {
    private ArrayList<Integer> result;
    private StringBuilder template;
    private int[] pref;
    private StringBuilder append;

    TSingleTemplateMatcher(String s) {
        if (s == null) {
            throw new NullPointerException("Null template");
        }
        result = new ArrayList<Integer>();
        template = new StringBuilder(s);
        append = new StringBuilder();
    }
    ArrayList<Integer> match(ICharStream stream) {
        setPref();
        int index = 0;
        int last = 0;
        String s = new StringBuilder(append).reverse().append(template).toString() + (char) 1;
//        String s = template.toString() + (char) 1;

        while (!stream.isEmpty()) {
            char next = stream.getChar();
            if (next < 32 || next > 255) {
                throw new IllegalArgumentException("Incorrect symbol");
            }
            while (last > 0 && s.charAt(last) != next) {
                last = pref[last - 1];
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
    private void setPref() {
        String s = new StringBuilder(append).reverse().append(template).toString() + (char) 1;
//        String s = template.toString() + (char) 1;
        pref = new int[s.length()];
        for (int i = 1; i < s.length(); ++i) {
            pref[i] = pref[i - 1];
            while (pref[i] > 0 && s.charAt(pref[i]) != s.charAt(i)) {
                pref[i] = pref[pref[i] - 1];
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
        append.append(c);
//        template.reverse().append(c);
    }
}