import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TSingleTemplateMatcherTest {
    TSingleTemplateMatcher test;

    @Test
    public void testMatch() throws Exception {
        test = new TSingleTemplateMatcher("a");
        ArrayList<Integer> result = test.match(new ICharStream(new Scanner("aaaaaa")));
        Assert.assertTrue(result.contains(0));
        Assert.assertTrue(result.contains(0));
        Assert.assertTrue(result.contains(0));
        Assert.assertTrue(result.contains(0));
        Assert.assertTrue(result.contains(0));
        Assert.assertTrue(result.contains(0));
        Assert.assertTrue(result.size() == 6);

        test = new TSingleTemplateMatcher("longString");
        Assert.assertTrue(test.match(new ICharStream(new Scanner("short"))).size() == 0);
    }

    @Test
    public void testMatchStress() throws Exception {

        // Я брал именно такое количество тестов, при больший мой ноут зависал и ребутить приходилось
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

            ArrayList<Pair<Integer, Integer>> naiveAns = naive.match(new ICharStream(new Scanner(stream.toString())));
            for (Integer value : test.match(new ICharStream(new Scanner(stream.toString())))) {
                Assert.assertTrue(naiveAns.contains(new Pair<>(value, 0)));
            }

        }
    }
}
