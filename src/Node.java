import java.util.ArrayList;

public class Node {
    int[] nextNode;
    //переходы

    ArrayList<Integer> childs;
    //в какие ноды идет, их перечисление, чтобы не тратить
    //255 проходов для их нахождения

    int parentNode;
    //родительская нода

    int index;
    //уникальный номер ноды

    char value;
    //хранимый символ

    int flag;
    //номер заканчивающейся строки

    int suffLink;
    //суффиксная ссылка

    int nextString;
    //хорошие суффиксные ссылки

    Node(int parent, int id, char c, int sid) throws IllegalArgumentException{
        if (c < 32 || c > 255) {
            throw new IllegalArgumentException("Incorrect symbol");
        }
        nextNode = new int[255];
        childs = new ArrayList<>();
        parentNode = parent;
        index = id;
        value = c;
        flag = sid;
        suffLink = -1;
        nextString = -1;
    }
}
