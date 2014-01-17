package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesUtils {

    public static final Properties createFromMap(final Map<String, String> map) {
        final Properties res = new Properties();
        for (final Entry<String, String> e : map.entrySet()) {
            res.setProperty(e.getKey(), e.getValue());
        }
        return res;
    }

    public static final Properties createFromFile(final File f) throws IOException {
        return create(new BufferedInputStream(new FileInputStream(f)));
    }

    public static final Properties createFromResource(final Class<?> ctxt, final String rsrc) throws IOException {
        return create(ctxt.getResourceAsStream(rsrc));
    }

    protected static final Properties create(final InputStream stream) throws IOException {
        return create(stream, true);
    }

    public static final Properties create(final InputStream stream, final boolean close) throws IOException {
        if (stream != null) {
            try {
                final Properties res = new Properties();
                res.load(stream);
                return res;
            } finally {
                if (close)
                    stream.close();
            }
        } else {
            return null;
        }
    }

    public static final void load(final Properties props, final Properties toLoad) throws IOException {
        for (final String key : toLoad.stringPropertyNames()) {
            final String value = toLoad.getProperty(key);
            assert value != null;
            props.setProperty(key, value);
        }
    }
}