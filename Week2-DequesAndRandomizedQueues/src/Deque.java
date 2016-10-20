import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> firstNode;
    private Node<Item> lastNode;
    private int size = 0;

    // is the deque empty?
    public boolean isEmpty() {
        return firstNode == null && lastNode == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateAdd(item);

        Node<Item> newFirst = new Node<>(item);
        newFirst.next = firstNode;

        if (firstNode != null) {
            firstNode.previous = newFirst;
        }

        firstNode = newFirst;

        if (lastNode == null) {
            lastNode = firstNode;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        validateAdd(item);

        Node<Item> newLast = new Node<>(item);
        newLast.previous = lastNode;

        if (lastNode != null) {
            lastNode.next = newLast;
        }

        lastNode = newLast;

        if (firstNode == null) {
            firstNode = lastNode;
        }

        size++;
    }

    private void validateAdd(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateRemove();

        Item val = firstNode.val;
        firstNode = firstNode.next;
        
        if (firstNode != null) {
            firstNode.previous = null;
        } else {
            lastNode = firstNode;
        }
        
        size--;

        return val;
    }

    public Item removeLast() {
        validateRemove();

        Item val = lastNode.val;
        lastNode = lastNode.previous;
        
        if (lastNode != null) {
            lastNode.next = null;
        }else{
            firstNode = lastNode;
        }

        size--;
        
        return val;
    }

    private void validateRemove() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node<Item> localFirst = firstNode;

            @Override
            public boolean hasNext() {
                return localFirst != null;
            }

            @Override
            public Item next() {
                if (localFirst == null) {
                    throw new NoSuchElementException();
                }
                
                Item val = localFirst.val;
                localFirst = localFirst.next;
                
                return val;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private static class Node<Item> {

        private Node<Item> next;
        private Node<Item> previous;
        private Item val;

        public Node(Item item) {
            val = item;
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();

//        System.out.println("Is Empty?? " + deque.isEmpty());
//        System.out.println("");
//        System.out.println("first:first");
//        populateAllFirst(deque);
//        while (!deque.isEmpty()) {
//            System.out.println(deque.removeFirst());
//        }
//
//        System.out.println("");
//        System.out.println("first:last");
//        populateAllFirst(deque);
//        while (!deque.isEmpty()) {
//            System.out.println(deque.removeLast());
//        }
//
//        System.out.println("");
//        System.out.println("last:first");
//        populateAllLast(deque);
//        while (!deque.isEmpty()) {
//            System.out.println(deque.removeFirst());
//        }
//
//        System.out.println("");
//        System.out.println("last:last");
//        populateAlternating(deque);
//        while (!deque.isEmpty()) {
//            System.out.println(deque.removeLast());
//        }
//
//        System.out.println("");
//        System.out.println("Test 6:");
//        deque.addFirst("lizzard");
//        deque.addLast("king");
//        System.out.println("rm first: " + deque.removeFirst());
//        System.out.println("rm last: " + deque.removeLast());
//        System.out.println("empty: " + deque.isEmpty());
//        System.out.println("size: " + deque.size());
//
//        populateAllFirst(deque);
//        Iterator<String> i = deque.iterator();
//        System.out.println("size: " + deque.size());
//
//        int x = 1;
//        while (i.hasNext()) {
//            String t = i.next();
//            System.out.println("hasNext: " + i.hasNext());
//            System.out.println("val: " + t);
//            System.out.println("count: " + x++);
//        }
//
//        deque.addLast("0");
//        deque.removeFirst();
//        deque.addLast("2");
//        deque.addLast("3");
//        deque.addLast("4");
//        deque.addLast("5");
//        deque.addLast("6");
//        deque.removeFirst();
//        deque.addLast("8");

        deque.addLast("the");
        System.out.println(deque.size());
        System.out.println(deque.removeLast());
        System.out.println(deque.size());
    }

    private static void populateAllFirst(Deque<String> deque) {
        deque.addFirst("the");
        deque.addFirst("quick");
        deque.addFirst("brown");
        deque.addFirst("fox");
        deque.addFirst("jumped");
        deque.addFirst("over");
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.addFirst("the");
        deque.addFirst("lazy");
        deque.addFirst("dog");
        deque.addFirst("my");
        deque.addFirst("name");
        deque.addFirst("is");
        deque.addFirst("bob");
    }

    private static void populateAllLast(Deque<String> deque) {
        deque.addLast("the");
        deque.addLast("quick");
        deque.addLast("brown");
        deque.addLast("fox");
        deque.addLast("jumped");
        deque.addLast("over");
        deque.addLast("the");
        deque.addLast("lazy");
        deque.addLast("dog");
        deque.addLast("my");
        deque.addLast("name");
        deque.addLast("is");
        deque.addLast("bob");
    }

    private static void populateAlternating(Deque<String> deque) {
        deque.addLast("the");
        deque.addFirst("quick");
        deque.addLast("brown");
        deque.addFirst("fox");
        deque.addLast("jumped");
        deque.addFirst("over");
        deque.addLast("the");
        deque.addFirst("lazy");
        deque.addLast("dog");
        deque.addFirst("my");
        deque.addLast("name");
        deque.addFirst("is");
        deque.addLast("bob");
    }
}