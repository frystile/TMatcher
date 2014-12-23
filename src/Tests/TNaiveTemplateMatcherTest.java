import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TNaiveTemplateMatcherTest {

    private TNaiveTemplateMatcher test;

    @Before
    public void setUp() throws Exception {
        test = new TNaiveTemplateMatcher();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddString() throws Exception {
        test.addString("test");
        test.addString("test");
    }

    @Test
    public void testMatch() throws Exception {
        test.addString("aba");
        ArrayList<Pair<Integer, Integer>> list =
                test.match(new ICharStream(new Scanner("abaccaba")));
        Assert.assertTrue(list.contains(new Pair<>(2, 0)));
        Assert.assertTrue(list.contains(new Pair<>(7, 0)));
    }
}