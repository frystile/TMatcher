import java.util.Scanner;

public class ICharStream {
    Scanner scanner;

    ICharStream(Scanner sc) {
        scanner = sc;
    }

    char getChar() {
        return scanner.findInLine(".").charAt(0);
    }
    //считывает строго 1 символ

    boolean isEmpty() {
        return !scanner.hasNext();
    }
    //проверяет можно ли считать хотя бы 1 символ
}
