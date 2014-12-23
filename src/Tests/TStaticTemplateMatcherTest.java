import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TStaticTemplateMatcherTest {
    TStaticTemplateMatcher test;

    @Before
    public void setUp() throws Exception {
        test = new TStaticTemplateMatcher();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdd() throws Exception {
        test.add("test");
        test.add("test");
    }

    @Test
    public void testMatch() throws Exception {
        Random random = new Random();
        TNaiveTemplateMatcher naive = new TNaiveTemplateMatcher();

        int templateCount = random.nextInt(40 + 1);
        ArrayList<String> templates = new ArrayList<>(40 + 1);

        for (int i = 0; i < templateCount; ++i) {
            int length = random.nextInt(10) + 5;
            StringBuilder builder = new StringBuilder();

            while (builder.toString().length() < length || templates.contains(builder.toString())) {
                builder = new StringBuilder();
                for (int k = 0; k < length; ++k) {
                    if (random.nextBoolean()) {
                        builder.append('a');
                    } else {
                        builder.append('b');
                    }
                }
            }
            templates.add(builder.toString());
            naive.addString(builder.toString());
            test.add(builder.toString());
        }

        StringBuilder stream = new StringBuilder();
        int steamSize = random.nextInt(10000) + 1;
        for (int k = 0; k < steamSize; ++k) {
            if (random.nextBoolean()) {
                stream.append('a');
            } else {
                stream.append('b');
            }
        }
        ArrayList<Pair<Integer, Integer>> naiveAns = naive.match(new ICharStream(new Scanner(stream.toString())));
        ArrayList<Pair<Integer, Integer>> staticAns = test.match(new ICharStream(new Scanner(stream.toString())));
        for (Pair<Integer, Integer> pair : staticAns) {
            Assert.assertTrue(naiveAns.contains(pair));
        }
        Assert.assertEquals(staticAns.size(), naiveAns.size());
    }
}
