import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * Queue implementation class
 */
public class IntQueueImpl<T> implements IntQueue<T>{

    /**
     * Queue is implemented as LinkedList object
     */
    private final LinkedList<T> queue = new LinkedList<T>();

    /**
     * @return true if the queue is empty
     */
    public boolean isEmpty() { return queue.isEmpty(); }

    /**
     * insert a T item to the queue
     */
    public void put(T item) { queue.insertAtBack(item); }

    /**
     * remove and return the oldest item of the queue
     * @return oldest item of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public T get() throws NoSuchElementException { return queue.removeFromFront(); }

    /**
     * return without removing the oldest item of the queue
     * @return oldest item of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();
        else
            return queue.getHead().getData();
    }

    /**
     * print the elements of the queue, starting from the oldest
     * item, to the print stream given as argument. For example, to
     * print the elements to the
     * standard output, pass System.out as parameter. E.g.,
     * printQueue(System.out);
     */
    public void printQueue(PrintStream stream){
        queue.printList(stream, "queue", "   <--   " );
    }

    /**
     * return the size of the queue, 0 if it is empty
     * @return number of elements in the queue
     */
    public int size() { return queue.getSize(); }

    /**
     * return head Node of the queue
     * @return Head Node
     */
    public Node<T> getHead() { return queue.getHead(); }
}
