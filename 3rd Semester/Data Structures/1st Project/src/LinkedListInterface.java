import java.io.PrintStream;
import java.util.NoSuchElementException;

public interface LinkedListInterface<T> {

    boolean isEmpty();

    void insertAtFront(T data);

    void insertAtBack(T data);

    T removeFromFront() throws NoSuchElementException;

    T removeFromBack() throws NoSuchElementException;

    int getSize();

    Node<T> getHead();

    Node<T> getTail();

    void printList(PrintStream stream, String structure, String s);
}
