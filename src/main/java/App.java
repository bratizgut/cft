import options.OptionsHandler;
import org.apache.commons.cli.*;
import providers.FileIOStreamProvider;
import providers.IOStreamProvider;
import readers.AbstractSortedReader;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            OptionsHandler optionsHandler = new OptionsHandler();
            optionsHandler.parse(args);
            try (IOStreamProvider provider = new FileIOStreamProvider(optionsHandler.getOutputFile(), optionsHandler.getInputFiles())) {
                PrintWriter outputWriter = new PrintWriter(provider.getOutputStream());
                AbstractSortedReader<?> sortedReader = optionsHandler.getSortedReader(provider);
                while (sortedReader.hasNext()) {
                    outputWriter.println(sortedReader.next());
                }
                outputWriter.flush();
            } catch (Exception exception) {
                logger.log(Level.SEVERE, exception.getMessage());
            }
        } catch (IllegalArgumentException | ParseException exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        }
    }
}
