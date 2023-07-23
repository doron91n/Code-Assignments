package game;

/**
 * a SerializableObj interface.
 *
 * @param <T> - generic type.
 */
public interface SerializableObj<T> {
    /**
     * @return a String format of the Serializable Object.
     */
    String serialize();

    /**
     * translate the given string into a Serializable object.
     *
     * @param s - a string format of the object.
     * @return a Serializable object <T> , translated from the given string (s).
     * @throws Exception - Deserialization Exception , failed to deserialize the given string.
     */
    T deserialize(String s) throws Exception;
}
