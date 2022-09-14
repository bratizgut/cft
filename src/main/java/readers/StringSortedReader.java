package readers;

import exception.SpaceContainingStringException;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StringSortedReader extends AbstractSortedReader<String> {

    private static final Logger logger = Logger.getLogger(StringSortedReader.class.getName());

    public StringSortedReader(List<InputStream> inputStreams, SortOrder sortOrder) {
        super(sortOrder.equals(SortOrder.ASC) ? Comparator.naturalOrder() : Comparator.reverseOrder());
        for (InputStream inputStream : inputStreams) {
            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNext()) {
                try {
                    curElements.add(supplyElementFromScanner(scanner));
                    scanners.add(scanner);
                } catch (SpaceContainingStringException exception) {
                    logger.log(Level.WARNING, exception.getMessage());
                }
            }
        }
    }

    @Override
    protected void updateCurElements(int minArg, String curElement) {
        try {
            super.updateCurElements(minArg, curElement);
        } catch (SpaceContainingStringException exception) {
            removeScanner(minArg);
            logger.log(Level.WARNING, exception.getMessage());
        }
    }

    @Override
    protected String supplyElementFromScanner(Scanner scanner) {
        String nextString = scanner.nextLine();
        if (nextString.contains(" ")) {
            throw new SpaceContainingStringException("Line contains space character.");
        }
        return nextString;
    }
}
