package com.johann.designPattern.designPatterns23.D_prototype;

import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.io.*;

/**
 * 序列化相关工具类
 */
public class SerializationUtils {

    /**
     * 使用序列化和反序列化克隆对象
     * @param object
     * @return
     * @param <T>
     */
    public static <T extends Serializable> T clone(final T object) {
        if (object == null) {
            return null;
        }
        byte[] objectData = serialize(object);
        return deserialize(objectData);
    }


    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(final Serializable object) {
        if (object == null) {
            return null;
        }
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
             final ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            oos.flush();
            return baos.toByteArray();
        } catch (final IOException ex) {
            throw new SerializationException("Failed to serialize object", ex);
        }
    }

    /**
     * 反序列化
     *
     * @param objectData
     * @return
     */
    public static <T> T deserialize(final byte[] objectData) {
        if (objectData == null) {
            return null;
        }
        try (final ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
             final ObjectInputStream ois = new ObjectInputStream(bais)) {
            @SuppressWarnings("unchecked")
            final T obj = (T) ois.readObject();
            return obj;
        } catch (final IOException | ClassNotFoundException ex) {
            throw new SerializationException(ex);
        }
    }


}
