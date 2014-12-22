import javafx.util.Pair;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        TStaticTemplateMatcher m = new TStaticTemplateMatcher();
        m.add("ab");
//        m.add("abcd"); //0
//        m.add("bcd"); //1
//        m.add("cc"); //2
//        m.add("bc"); //3
//        try {
//            for (Pair<Integer, Integer> pair : m.match(new ICharStream(new Scanner("abacabadabacaba")))) {
//                System.out.println(pair.getKey() + " " + pair.getValue());
//            }
//        } catch (IllegalAccessException e) {
//            System.err.println(e.getMessage());
//        }

//        TSingleTemplateMatcher singleTemplateMatcher = new TSingleTemplateMatcher("");
//        singleTemplateMatcher.appendCharToTemplate('b');
//        singleTemplateMatcher.appendCharToTemplate('a');
//        singleTemplateMatcher.prependCharToTemplate('a');
//        for (Pair<Integer, Integer> pair : singleTemplateMatcher.match(new ICharStream(new Scanner("abacabadabacaba")))) {
//            System.out.println(pair.getKey() + " " + pair.getValue());
//        }

        TWildCardSingleTemplateMatcher wildCardSingleTemplateMatcher = new TWildCardSingleTemplateMatcher("a?a");
        for (Integer index : wildCardSingleTemplateMatcher.match(new ICharStream(new Scanner("abacaga")))) {
            System.out.println(index);
        }
    }
}
