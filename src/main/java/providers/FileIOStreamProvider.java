package providers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileIOStreamProvider implements IOStreamProvider {

    private final OutputStream outputStream;
    private final List<InputStream> inputStreamList;

    public FileIOStreamProvider(String out, List<String> in) throws FileNotFoundException {
        outputStream = new BufferedOutputStream(new FileOutputStream(out));
        inputStreamList = new ArrayList<>();
        for (String inFile: in) {
            inputStreamList.add(new BufferedInputStream(new FileInputStream(inFile)));
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public List<InputStream> getInputStreamList() {
        return inputStreamList;
    }

    @Override
    public void close() throws Exception {
        outputStream.close();
        for (InputStream inputStream : inputStreamList) {
            inputStream.close();
        }
    }
}
