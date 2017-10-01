/**
 * Iterator for arrays
 *
 * @author rbk
 * Ver 1.0: 2017/08/08
 * Ver 1.1: 2017/08/28.  Updated some methods to public
<<<<<<< HEAD
*/
=======
 */
>>>>>>> eab2ebe1104e0cd46861c3e2ed897c4ab03ea7c0

package cs6301.g00;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {
    T[] arr;
    int startIndex, endIndex, cursor;

    ArrayIterator(T[] a) {
        arr = a;
        startIndex = 0;
        endIndex = a.length - 1;
        cursor = -1;
    }

    ArrayIterator(T[] a, int start, int end) {
        arr = a;
        startIndex = start;
        endIndex = end;
        cursor = start - 1;
    }

    public boolean hasNext() {
        return cursor < endIndex;
    }

    public T next() {
        return arr[++cursor];
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
