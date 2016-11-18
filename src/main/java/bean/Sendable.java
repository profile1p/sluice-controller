package bean;

import java.lang.reflect.Field;

/**
 * @author Lilx
 * @since 2016
 */
public interface Sendable {
    default int length() {
        int length = 0;
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                if (null != value) {
                    length += String.valueOf(value).length();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return length;
    }
}
