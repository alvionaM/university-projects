import java.io.PrintStream;
import java.util.NoSuchElementException;

public class LinkedList<T> implements LinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int numOfNodes;

    public LinkedList() {
        head = tail = null;
        numOfNodes = 0;
    }


    public boolean isEmpty() {
        return head == null;
    }


    public void insertAtFront(T data) {
        Node<T> n = new Node<T>(data);

        if (isEmpty())
            head = tail = n;
        else {
            n.setNext(head);
            head = n;
        }
        numOfNodes++;
    }

    public void insertAtBack(T data) {
        Node<T> n = new Node<T>(data);

        if (isEmpty())
            head = tail = n;
        else {
            tail.setNext(n);
            tail = n;
        }
        numOfNodes++;
    }


    public T removeFromFront() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();

        T data = head.getData();

        if (head == tail)
            head = tail = null;
        else
            head = head.getNext();

        numOfNodes--;
        return data;
    }

    public T removeFromBack() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();

        T data = tail.getData();

        Node<T> current;
        if (head == tail)
            head = tail = null;
        else {
            for (current = head; current.getNext() != tail; current = current.getNext()) ;
            current.setNext(null);
            tail = current;
        }

        numOfNodes--;
        return data;
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public int getSize() {
        return numOfNodes;
    }

    public void printList(PrintStream stream, String structure, String s){
        if (isEmpty())
            stream.println("\tThe "+ structure + " is empty!");
        else {
            stream.println("================= "+structure.toUpperCase()+" ==================\n");
            Node<T> current;
            for (current = this.getHead(); current != null; current = current.getNext()) {
                stream.print(current);
                stream.print(s);
            }
            stream.println("\n============== End of "+structure.toUpperCase()+" ==============\n");
        }
    }
}
