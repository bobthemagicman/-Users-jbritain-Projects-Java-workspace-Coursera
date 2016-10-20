import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] backingArray;
    private int end;

    public RandomizedQueue() {
        backingArray = (Item[]) new Object[10];
        end = -1;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return end < 0;
    }

    // return the number of items on the queue
    public int size() {
        return end + 1;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size() == backingArray.length) {
            final Object[] resizedArray = new Object[backingArray.length * 2];

            int x = 0;
            while (x < backingArray.length) {
                resizedArray[x] = backingArray[x];
                backingArray[x++] = null;
            }

            backingArray = (Item[]) resizedArray;
            //System.out.println("BL: " + backingArray.length);
        }

            backingArray[++end] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        validate();

        int random = end == 0 ? 0 : StdRandom.uniform(end + 1);
        Item i = backingArray[random];
        backingArray[random] = backingArray[end];
        backingArray[end--] = null;
        
        if ((size() <= backingArray.length / 4) && backingArray.length >= 20) {
            backingArray = reduceCapacity(backingArray);
        }
        
        return i;
    }

    private Item[] reduceCapacity(final Item[] arrayToAnalize) {
        final Object[] resizedArray = new Object[arrayToAnalize.length / 2];

        int x = 0;
        while (x < resizedArray.length) {
            resizedArray[x] = arrayToAnalize[x++];
        }

        return (Item[]) resizedArray;
    }

    // return (but do not remove) a random item
    public Item sample() {
        validate();

        final int random = end == 0 ? 0 : StdRandom.uniform(end + 1);
        
        return backingArray[random];
    }

    private void validate() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Item[] itrArray = backingArray.clone();
            int itrEnd = end;

            public boolean hasNext() {
                return itrEnd >= 0;
            }

            public Item next() {
                if (itrEnd < 0) {
                    throw new NoSuchElementException();
                }

                int random = StdRandom.uniform(itrEnd + 1);
                Item i = itrArray[random];
                itrArray[random] = itrArray[itrEnd];
                itrArray[itrEnd--] = null;

                if ((itrEnd + 1 <= itrArray.length / 4) && itrArray.length >= 20) {
                    itrArray = reduceCapacity(itrArray);
                }
                
                return i;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing
    public static void main(String[] args) {
    }
}