package options;

import org.apache.commons.cli.*;
import providers.IOStreamProvider;
import readers.AbstractSortedReader;
import readers.IntegerSortedReader;
import readers.SortOrder;
import readers.StringSortedReader;

import java.util.List;

public class OptionsHandler {
    private final Options options;
    private final CommandLineParser parser;

    private String outputFile;
    private List<String> inputFiles;
    private CommandLine cmd;

    public OptionsHandler() {
        options = new Options();
        options.addOption(new Option("i", false, "Sort integer values."));
        options.addOption(new Option("s", false, "Sort string values."));
        options.addOption(new Option("a", false, "Ascending sort mode."));
        options.addOption(new Option("d", false, "Descending sort mode."));
        parser = new DefaultParser();
    }

    public CommandLine parse(String[] args) throws ParseException {
        if (args.length < 3)
            throw new IllegalArgumentException("Program requires at least 3 arguments.");

        cmd = parser.parse(options, args);
        if (!cmd.hasOption("i") && !cmd.hasOption("s"))
            throw new IllegalArgumentException("Argument -i or -s is required.");
        if (cmd.hasOption("i") && cmd.hasOption("s"))
            throw new IllegalArgumentException("Only one argument -i or -s is required.");
        if (cmd.hasOption("a") && cmd.hasOption("d"))
            throw new IllegalArgumentException("Only one argument -a or -d is required.");

        List<String> files = cmd.getArgList();
        inputFiles = files.subList(1, files.size());
        outputFile = files.get(0);
        return cmd;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public SortOrder getSortOrder() {
        if (cmd.hasOption("d"))
            return SortOrder.DESC;
        return SortOrder.ASC;
    }

    public AbstractSortedReader<?> getSortedReader(IOStreamProvider provider) {
        if (cmd.hasOption("i")) {
            return new IntegerSortedReader(provider.getInputStreamList(), getSortOrder());
        } else if (cmd.hasOption("s")) {
            return new StringSortedReader(provider.getInputStreamList(), getSortOrder());
        }
        throw new IllegalArgumentException("No sort type specified.");
    }
}
