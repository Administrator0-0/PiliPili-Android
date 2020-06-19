package com.example.pilipili_android.util;


import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class SerializeAndDeserializeUtil {

    //序列化音乐
    public static String serializeImage(Drawable drawable) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(drawable);
        String serializedStr = byteArrayOutputStream.toString("ISO-8859-1");
        serializedStr = java.net.URLEncoder.encode(serializedStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serializedStr;
    }

    //反序列化音乐
    public static Drawable deserializeImage(String str) throws IOException, ClassNotFoundException {
        String readStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                readStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Drawable drawable = (Drawable) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return drawable;
    }

}
