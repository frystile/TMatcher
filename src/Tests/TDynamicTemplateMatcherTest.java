import javafx.print.PageOrientation;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TDynamicTemplateMatcherTest {
    private TDynamicTemplateMatcher test;

    @Test(expected = IllegalArgumentException.class)
    public void testAddString() throws Exception {
        test = new TDynamicTemplateMatcher();
        test.addString("test");
        test.addString("test");
    }

    @Test
    public void testMatch() throws Exception {
        Random random = new Random();

        for (int h = 0; h < 100; ++h) {
            test = new TDynamicTemplateMatcher();
            TStaticTemplateMatcher before = new TStaticTemplateMatcher();
            TStaticTemplateMatcher after = new TStaticTemplateMatcher();

            int templateSize = 10;
            int templateLength = 5;

            ArrayList<String> strings = new ArrayList<String>();
            for (int i = 0; i < templateSize; ++i) {
                StringBuilder builder = new StringBuilder();
                while (builder.toString().length() < templateLength || strings.contains(builder.toString())) {
                    builder = new StringBuilder();
                    for (int k = 0; k < templateLength; ++k) {
                        if (random.nextBoolean()) {
                            builder.append('a');
                        } else {
                            builder.append('b');
                        }
                    }
                }
                strings.add(builder.toString());

                test.addString(builder.toString());
                before.add(builder.toString());
                after.add(builder.toString());
            }

            int inputSize = 1000;
            StringBuilder inputBuilder = new StringBuilder();
            for (int i = 0; i < inputSize; ++i) {
                if (random.nextBoolean()) {
                    inputBuilder.append('a');
                } else {
                    inputBuilder.append('b');
                }
            }
            String input = inputBuilder.toString();

            ArrayList<Pair<Integer, Integer>> naiveAns = before.match(new ICharStream(new Scanner(input)));
            ArrayList<Pair<Integer, Integer>> dynamicAns = test.match(new ICharStream(new Scanner(input)));

            Assert.assertEquals(naiveAns.size(), dynamicAns.size());

            for (Pair<Integer, Integer> pair : dynamicAns) {
//                if (!naiveAns.contains(pair)) {
//                    System.out.println("NO");
//                    System.out.println(pair);
//                    System.out.println(naiveAns);
//                    System.out.println(dynamicAns);
//                    for (String s: strings) {
//                        System.out.println("STRING - " + s);
//                    }
//                    System.out.println(input);
//                }
                Assert.assertTrue(naiveAns.contains(pair));
            }

            for (int i = 0; i < templateSize; ++i) {
                StringBuilder builder = new StringBuilder();
                while (builder.toString().length() < templateLength || strings.contains(builder.toString())) {
                    builder = new StringBuilder();
                    for (int k = 0; k < templateLength; ++k) {
                        if (random.nextBoolean()) {
                            builder.append('a');
                        } else {
                            builder.append('b');
                        }
                    }
                }
                strings.add(builder.toString());

                test.addString(builder.toString());
                after.add(builder.toString());
            }

            inputBuilder = new StringBuilder();
            for (int i = 0; i < inputSize; ++i) {
                if (random.nextBoolean()) {
                    inputBuilder.append('a');
                } else {
                    inputBuilder.append('b');
                }
            }
            input = inputBuilder.toString();

            naiveAns = after.match(new ICharStream(new Scanner(input)));
            dynamicAns = test.match(new ICharStream(new Scanner(input)));

            Assert.assertEquals(naiveAns.size(), dynamicAns.size());

            for (Pair<Integer, Integer> pair : dynamicAns) {
//                if (!naiveAns.contains(pair)) {
//                    System.out.println("NO");
//                    System.out.println(pair);
//                    System.out.println(naiveAns);
//                    System.out.println(dynamicAns);
//                    for (String s: strings) {
//                        System.out.println("STRING - " + s);
//                    }
//                    System.out.println(input);
//                }
                Assert.assertTrue(naiveAns.contains(pair));
            }
        }

    }
}