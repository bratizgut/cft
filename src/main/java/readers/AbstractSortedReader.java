package readers;

import java.util.*;

public abstract class AbstractSortedReader<T> {

    final List<Scanner> scanners;
    final List<T> curElements;
    final Comparator<T> comparator;

    protected AbstractSortedReader(Comparator<T> comparator) {
        this.comparator = comparator;
        this.curElements = new ArrayList<>();
        this.scanners = new ArrayList<>();
    }

    public boolean hasNext() {
        return !curElements.stream().allMatch(Objects::isNull) || scanners.stream().anyMatch(Scanner::hasNext);
    }

    protected void removeScanner(int index) {
        scanners.remove(index).close();
    }

    public T next() {
        T curElement = Collections.min(curElements, comparator);
        int minArg = curElements.indexOf(curElement);
        updateCurElements(minArg, curElement);
        return curElement;
    }

    protected void updateCurElements(int minArg, T curElement) {
        Scanner minScanner = scanners.get(minArg);
        if (minScanner.hasNext()) {
            curElements.remove(minArg);
            T next = supplyElementFromScanner(minScanner);
            if (comparator.compare(next, curElement) > 0) {
                curElements.add(minArg, next);
            } else {
                removeScanner(minArg);
            }
        } else {
            removeScanner(minArg);
            curElements.remove(minArg);
        }
    }

    protected abstract T supplyElementFromScanner(Scanner scanner);
}
