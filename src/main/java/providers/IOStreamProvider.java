package providers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface IOStreamProvider extends AutoCloseable {
    OutputStream getOutputStream();
    List<InputStream> getInputStreamList();
}
