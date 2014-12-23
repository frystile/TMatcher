import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TWildCardSingleTemplateMatcherTest {
    TWildCardSingleTemplateMatcher test;

    @Before
    public void setUp() throws Exception {
        test = new TWildCardSingleTemplateMatcher();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSet() throws Exception {
        test.set("тест");
    }

    @Test
    public void testMatch() throws Exception {
        Random random = new Random();

        for (int h = 0; h < 1000; ++h) {
            test = new TWildCardSingleTemplateMatcher();
//            int templateSize = random.nextInt() + 5;
            int templateSize = 5;
            StringBuilder template = new StringBuilder();
            for (int i = 0; i < templateSize; ++i) {
                int wtf = random.nextInt(3);
                switch (wtf) {
                    case 0:
                        template.append('a');
                        break;
                    case 1:
                        template.append('b');
                        break;
                    case 2:
                        template.append('?');
                        break;
                    case 3:
                        template.append('?');
                        break;
                }
            }

            int streamSize = random.nextInt(1000) + 10;
            StringBuilder stream = new StringBuilder();
            for (int i = 0; i < streamSize; ++i) {
                if (random.nextBoolean()) {
                    stream.append('a');
                } else {
                    stream.append('b');
                }
            }


            String input = stream.toString();
            String str = template.toString();
            ArrayList<Integer> naiveAns = naiveWild(str, input);

            test.set(str);
            ArrayList<Integer> wildAns = test.match(new ICharStream(new Scanner(input)));

            if (wildAns.size() != naiveAns.size()) {
                System.out.println("NO");
                System.out.println(str);
                System.out.println(input);
                System.out.println("WILD = " + wildAns);
                System.out.println("NAIVE = " + naiveAns);
            }
            Assert.assertEquals(wildAns.size(), naiveAns.size());
            for (Integer t : wildAns) {
                Assert.assertTrue(naiveAns.contains(t));
            }
        }
    }

    ArrayList<Integer> naiveWild(String template, String input) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i <= input.length() - template.length(); ++i) {
            boolean flag = true;
            for (int k = 0; k < template.length(); ++k) {
                if (template.charAt(k) != '?' && template.charAt(k) != input.charAt(i + k)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result.add(i + template.length() - 1);
            }
        }
        return result;
    }
}