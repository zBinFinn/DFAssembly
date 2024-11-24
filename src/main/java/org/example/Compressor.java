package org.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

public class Compressor {
    public static String compress(String str) throws IOException {
        byte[] original = str.getBytes(StandardCharsets.UTF_8);
        byte[] gzipToBase64 = encodeToBase64(encodeToGZIP(original));
        return new String(gzipToBase64);
    }

    public static byte[] encodeToBase64(byte[] arr) {
        return Base64.getEncoder().encode(arr);
    }

    public static byte[] encodeToGZIP(byte[] arr) throws IOException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(baos);
        gzip.write(arr);
        gzip.finish();
        return baos.toByteArray();
    }
}
