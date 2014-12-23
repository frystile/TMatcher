import java.util.Scanner;

public class ICharStream {
    private String stream;
    private int count;

    ICharStream(String s) {
        stream = s;
        count = 0;
    }

    char getChar() {
        ++count;
        return stream.charAt(count - 1);
    }
    //считывает строго 1 символ

    boolean isEmpty() {
        return count == stream.length();
    }
    //проверяет можно ли считать хотя бы 1 символ
}
