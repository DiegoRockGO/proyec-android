package com.uy.csi.sige.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dtrinidad on 03/08/2016.
 */
public class Encrypta {

    private static final long serialVersionUID = 1L;
    public static final String ENCRIPTA_MD5;
    public static final String ENCRIPTA_MD2;
    public static final String ENCRIPTA_SHA1;

    static {
        ENCRIPTA_MD2 = "MD2";
        ENCRIPTA_MD5 = "MD5";
        ENCRIPTA_SHA1 = "SHA-1";
    }

    public Encrypta() {
        super();
    }

    private static String toHexadecimal(byte[] diggest) {
        String h = "";
        for (byte paso : diggest) {
            int b = paso & 0xff;
            if (Integer.toHexString(b).length() == 1) {
                h += "0";
            }
            h += Integer.toHexString(b);
        }
        return h;
    }

    public static String encriptar(String message, String algoritmo) {
        byte[] digest = null;
        byte[] buffer = message.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algoritmo);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error creando Digest");
        }
        return toHexadecimal(digest);
    }
}
