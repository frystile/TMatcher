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
        int steamSize = 100000;
        for (int k = 0; k < steamSize; ++k) {
            if (random.nextBoolean()) {
                stream.append('a');
            } else {
                stream.append('b');
            }
        }
        ArrayList<Pair<Integer, Integer>> naiveAns = naive.match(new ICharStream(stream.toString()));
        ArrayList<Pair<Integer, Integer>> staticAns = test.match(new ICharStream(stream.toString()));
        for (Pair<Integer, Integer> pair : staticAns) {
            Assert.assertTrue(naiveAns.contains(pair));
        }
        Assert.assertEquals(staticAns.size(), naiveAns.size());
    }

    @Test
    public void testBigValue() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000000; ++i) {
            builder.append('a');
        }

        test.add("a");
        test.add("aa");
        test.add("aaa");
        //1000000 + 1000000 - 1 + 1000000 - 2
        ArrayList<Pair<Integer, Integer>> ans = test.match(new ICharStream(builder.toString()));
        Assert.assertEquals(ans.size(), 3000000 - 3);
    }

    @Test
    public void testSingleBitValue() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10000000; ++i) {
            if (random.nextBoolean()) {
                builder.append('a');
            } else {
                builder.append('b');
            }
        }

        test.add("ab");
        TSingleTemplateMatcher tmp = new TSingleTemplateMatcher("ab");
        Assert.assertEquals(tmp.match(new ICharStream(builder.toString())).size(), test.match(new ICharStream(builder.toString())).size());
    }

    @Test
    public void testBigTemplate() {
        StringBuilder template = new StringBuilder();
        for (int i = 0; i < 100000; ++i) {
            template.append('a');
        }

        StringBuilder stream = new StringBuilder();
        for (int i = 0; i < 10000000; ++i) {
            stream.append('a');
        }
        test.add(template.toString());


        Assert.assertEquals(test.match(new ICharStream(stream.toString())).size(), 10000000 - 100000 + 1);
    }

    @Test
    public void testManyTemplates() {
        StringBuilder template = new StringBuilder();
        for (int i = 0; i < 100; ++i) {
            template.append('a');
            test.add(template.toString());
        }

        int count = 0;
        StringBuilder stream = new StringBuilder();
        for (int i = 0; i < 100; ++i) {
            stream.append('a');
            count += 10000 - stream.length() + 1;
        }
        test.match(new ICharStream(stream.toString()));
    }


}
