package de.niklasriepen.mailapi.session.util;

import java.nio.charset.Charset;

public class Base64 {

    public static String decode(String text) {
        return new String(java.util.Base64.getDecoder().decode(text));
    }

    public static String decode(String text, Charset charset) {
        return new String(java.util.Base64.getDecoder().decode(text), charset);
    }

    public static String encode(String text) {
        return java.util.Base64.getEncoder().encodeToString(text.getBytes());
    }

    public static String encode(String text, Charset charset) {
        return java.util.Base64.getEncoder().encodeToString(text.getBytes(charset));
    }

}
