import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * Stack implementation class
 */
public class StringStackImpl<T> implements StringStack<T>{

    /**
     * Stack is implemented as LinkedList object
     */
    private final LinkedList<T> stack = new LinkedList<T>();

    /**
     * @return true if the stack is empty
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * insert a T item to the stack
     */
    public void push(T item) {
        stack.insertAtFront(item);
    }

    /**
     * remove and return the item on the top of the stack
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    public T pop() throws NoSuchElementException { return stack.removeFromFront(); }

    /**
     * return without removing the item on the top of the stack
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    public T peek() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();
        else
            return stack.getHead().getData();
    }

    /**
     * print the elements of the stack, starting from the item
     * on the top,
     * to the stream given as argument. For example,
     * to print to the standard output you need to pass System.out as
     * an argument. E.g.,
     * printStack(System.out);
     */
    public void printStack(PrintStream stream) {
        stack.printList(stream, "stack", "\n---------------------------------\n");
    }

    /**
     * return the size of the stack, 0 if it is empty
     * @return the number of items currently in the stack
     */
    public int size() { return stack.getSize(); }
}
