import javafx.util.Pair;
import java.util.ArrayList;

public class Bor {
    private ArrayList<Node> nodes;
    private int stringCount;
    private ArrayList<Pair<Integer, Integer>> matchResult;
    private int current;
    private int streamLength;

    Bor() {
        nodes = new ArrayList<Node>();
        nodes.add(new Node(-1, 0, '$', -1));
        stringCount = 0;
        matchResult = null;
        streamLength = 0;
        current = 0;
    }

    void addString(String template) throws IllegalArgumentException {
        int num = 0;

        for (int i = 0; i < template.length(); ++i) {
            int id = template.charAt(i);
            if (nodes.get(num).nextNode[id] == 0) {
                int flag = (i == template.length() - 1) ? stringCount : -1;
                int index = nodes.size();
                nodes.add(new Node(num, index, template.charAt(i), flag));
                nodes.get(num).nextNode[id] = index;
                nodes.get(num).childs.add(index);
            }

            num = nodes.get(num).nextNode[id];

            if (i == template.length() - 1) {
                nodes.get(num).flag = stringCount;
            }
        }
        ++stringCount;
    }

//    void print() {
//        for (int i = 0; i < nodes.size(); ++i) {
//            System.out.println(i + " - parent = " + nodes.get(i).parentNode + " value = " + nodes.get(i).value + " flag = "
//                    + nodes.get(i).flag + " sufflink = " + nodes.get(i).suffLink + " NextString = " + nodes.get(i).nextString);
//            for (int id : nodes.get(i).childs) {
//                System.out.println(id + " ");
//            }
//        }
//    }

    void getSuffLink() {
        ArrayList<Integer> sequence = new ArrayList<>();
        sequence.addAll(nodes.get(0).childs);
        if (sequence.size() == 0) {
            return;
        }
        int num = 0;
        while (num < sequence.size()) {
            int index = sequence.get(num);
            setLink(index);
            Node pref = nodes.get(nodes.get(index).suffLink);
            nodes.get(index).nextString = (pref.flag != -1) ? nodes.get(index).suffLink : pref.nextString;
            if (nodes.get(index).childs.size() > 0) {
                sequence.addAll(nodes.get(index).childs);
            }
            ++num;
        }
    }

    void setLink(int index) {
        if (nodes.get(index).suffLink != -1) {
            return;
        }
        int result = nodes.get(index).parentNode;
        if (result == 0) {
            nodes.get(index).suffLink = 0;
            return;
        }
        result = nodes.get(result).suffLink;

        int id = nodes.get(index).value;

        result = move(result, id);
        nodes.get(index).suffLink = result;
    }

    int move(int index, int symbol) {
        if (symbol < 32 || symbol > 255) {
            throw new IllegalArgumentException("Illegal symbol");
        }

        Node node = nodes.get(index);

        if (node.nextNode[symbol] == 0) {
            if (index == 0) {
                return 0;
            } else {
                if (node.suffLink == -1) {
                    return move(0, symbol);
                } else {
                    return move(node.suffLink, symbol);
                }
            }
        } else {
            return node.nextNode[symbol];
        }
    }



    ArrayList<Pair<Integer, Integer>> match(char c) {
        if (c < 32 || c > 255) {
            throw new IllegalArgumentException("Illegal symbol");
        }

        current = move(current, c);
        check(current, streamLength);
        ++streamLength;

        return matchResult;
    }

    void check(int index, int number) {
        Node tmp = nodes.get(index);
        matchResult = new ArrayList<>();

        if (tmp.flag != -1) {
            matchResult.add(new Pair<Integer, Integer>(number, tmp.flag));
        }

        while (tmp.index != 0 && tmp.nextString != -1) {
            tmp = nodes.get(tmp.nextString);
            if (tmp.flag != -1) {
                matchResult.add(new Pair<Integer, Integer>(number, tmp.flag));
            }
        }
    }

}
