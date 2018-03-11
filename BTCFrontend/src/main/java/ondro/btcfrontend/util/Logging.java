package ondro.btcdataproducer.util;

import java.util.logging.Logger;

/**
 *
 * @author Ondrej Mihalyi
 */
public final class Logging {

    private Logging() {
    }

    public static Logger of(Class<?> cls) {
        return Logger.getLogger(cls.getName());
    }

    public static Logger of(Object obj) {
        return Logger.getLogger(obj.getClass().getName());
    }
}
