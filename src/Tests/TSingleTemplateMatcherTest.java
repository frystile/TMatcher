import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TSingleTemplateMatcherTest {
    TSingleTemplateMatcher test;

//    @Test
    public void testMatch() throws Exception {
        test = new TSingleTemplateMatcher("longString");
        Assert.assertTrue(test.match(new ICharStream("short")).size() == 0);
    }

    @Test
    public void testMatchStress() throws Exception {

        for (int i = 0; i < 1000; ++i) {
            Random random = new Random();
            int templateSize = random.nextInt(20) + 1;
            int streamSize = random.nextInt(1000) + 1;

            StringBuilder template = new StringBuilder();
            StringBuilder stream = new StringBuilder();

            for (int k = 0; k < templateSize; ++k) {
                if (random.nextBoolean()) {
                    template.append('a');
                } else {
                    template.append('b');
                }
            }
            for (int k = 0; k < streamSize; ++k) {
                if (random.nextBoolean()) {
                    stream.append('a');
                } else {
                    stream.append('b');
                }
            }
            TNaiveTemplateMatcher naive = new TNaiveTemplateMatcher();
            naive.addString(template.toString());

            test = new TSingleTemplateMatcher(template.toString());

            ArrayList<Pair<Integer, Integer>> naiveAns = naive.match(new ICharStream(stream.toString()));
            for (Integer value : test.match(new ICharStream(stream.toString()))) {
                Assert.assertTrue(naiveAns.contains(new Pair<>(value, 0)));
            }

        }
    }

    @Test
    public void testBigValue() {
        test = new TSingleTemplateMatcher("a");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10000000; ++i) {
            builder.append('a');
        }
        ArrayList<Integer> ans = test.match(new ICharStream(builder.toString()));
        Assert.assertEquals(ans.size(), 10000000);
    }

    @Test
    public void testBigRandomValue() {
        test = new TSingleTemplateMatcher("a");
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 10000000; ++i) {
            if (random.nextBoolean()) {
                builder.append('a');
            } else {
                builder.append('b');
            }
        }

        test.match(new ICharStream(builder.toString()));
    }

    @Test
    public void testAppend() {
        test = new TSingleTemplateMatcher("a");
        StringBuilder builder = new StringBuilder(2000000);
        for (int i = 0; i < 10000000; ++i) {
            test.appendCharToTemplate('a');
            builder.append('a');
        }

        builder.append(builder.toString());
        ArrayList<Integer> result = test.match(new ICharStream(builder.toString()));
        Assert.assertEquals(result.size(), 10000000);
    }


    @Test
    public void testPreAppend() {
        test = new TSingleTemplateMatcher("a");
        StringBuilder builder = new StringBuilder(2000000);
        for (int i = 0; i < 10000000; ++i) {
            test.prependCharToTemplate('a');
            builder.append('a');
        }

        builder.append(builder.toString());
        ArrayList<Integer> result = test.match(new ICharStream(builder.toString()));
        Assert.assertEquals(result.size(), 10000000);
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
        test = new TSingleTemplateMatcher(template.toString());
        test.match(new ICharStream(stream.toString()));
    }

}
