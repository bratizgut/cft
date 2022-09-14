package readers;

import exception.SpaceContainingStringException;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegerSortedReader extends AbstractSortedReader<Integer> {

    private static final Logger logger = Logger.getLogger(IntegerSortedReader.class.getName());

    public IntegerSortedReader(List<InputStream> inputStreamList, SortOrder sortOrder) {
        super(sortOrder.equals(SortOrder.ASC) ? Comparator.naturalOrder() : Comparator.reverseOrder());
        for (InputStream inputStream: inputStreamList) {
            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNext()) {
                try {
                    curElements.add(supplyElementFromScanner(scanner));
                    scanners.add(scanner);
                } catch (SpaceContainingStringException | NumberFormatException exception) {
                    logger.log(Level.WARNING, exception.getMessage());
                }
            }
        }
    }

    @Override
    protected void updateCurElements(int minArg, Integer curElement) {
        try {
            super.updateCurElements(minArg, curElement);
        } catch (SpaceContainingStringException | NumberFormatException exception) {
            logger.log(Level.WARNING, exception.getMessage());
            removeScanner(minArg);
        }
    }

    @Override
    protected Integer supplyElementFromScanner(Scanner scanner) {
        String str = scanner.nextLine();
        if (str.contains(" "))
            throw new SpaceContainingStringException("Line contains space character.");
        return Integer.parseInt(str);
    }
}
