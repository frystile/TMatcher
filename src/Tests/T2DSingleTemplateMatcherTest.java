import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class T2DSingleTemplateMatcherTest {
    private T2DSingleTemplateMatcher test;

    @Test
    public void testMatch() throws Exception {
        test = new T2DSingleTemplateMatcher();
        Random random = new Random();
        for (int h = 0; h < 100; ++h) {
            int high = 4;
            int width = 4;

            ArrayList<String> template = new ArrayList<>(high);
            for (int i = 0; i < high; ++i) {
                StringBuilder builder = new StringBuilder(width);
                for (int k = 0; k < width; ++k) {
                    if (random.nextBoolean()) {
                        builder.append('a');
                    } else {
                        builder.append('b');
                    }
                }
                template.add(builder.toString());
            }

            test.set(template);

            int streamHigh = 100;
            int streamWidth = 100;
            ArrayList<String> stream = new ArrayList<>(streamHigh);
            for (int i = 0; i < streamHigh; ++i) {
                StringBuilder builder = new StringBuilder(streamWidth);
                for (int k = 0; k < streamWidth; ++k) {
                    if (random.nextBoolean()) {
                        builder.append('a');
                    } else {
                        builder.append('b');
                    }
                }
                stream.add(builder.toString());
            }

            ArrayList<Pair<Integer, Integer>> ans = test.match(stream);
            ArrayList<Pair<Integer, Integer>> naive = naiveAns(template, stream);

            Assert.assertEquals(ans.size(), naive.size());
            for (Pair<Integer, Integer> pair : ans) {
                Assert.assertTrue(naive.contains(pair));
            }
        }
    }

    ArrayList<Pair<Integer, Integer>> naiveAns(ArrayList<String> template, ArrayList<String> stream) {
        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        int templateLength = template.get(0).length();
        int streamLength = stream.get(0).length();
        for (int i = 0; i <= stream.size() - template.size(); ++i) {
            for (int k = 0; k <= streamLength - templateLength; ++k) {
                boolean flag = true;
                for (int j = 0; j < template.size(); ++j) {
                    if (!stream.get(i).substring(k, k + templateLength - 1).equals(template.get(j))) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    result.add(new Pair<>(i + template.size() - 1, k + templateLength - 1));
                }
            }
        }
        return result;
    }

    @Test(expected = NullPointerException.class)
    public void testSetNull() throws Exception {
        test = new T2DSingleTemplateMatcher();
        test.set(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIllegal() throws Exception {
        test = new T2DSingleTemplateMatcher();
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add("test");
        tmp.add("longtest");
        test.set(tmp);
    }
}