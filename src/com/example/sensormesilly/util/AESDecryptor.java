package com.example.sensormesilly.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESDecryptor {


    private AESDecryptor() {}

    private static final SecretKey key = new SecretKeySpec(
            new byte[]{68, 13, -38, 27, -8, -92, -33, 67, 11, 20, -79, 112, 6, 113, 115, 69},"AES");


    public static String decrypt(String cipherText, String nonce) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        byte[] nonceByteArray = Base64.getDecoder().decode(nonce);

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(nonceByteArray));
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static class Message {

        private final String iv;
        private final String ct;


        public String getIv() {
            return iv;
        }

        public String getCt() {
            return ct;
        }

        public Message(String iv, String ct) {
            this.iv = iv;
            this.ct = ct;
        }
    }
}
