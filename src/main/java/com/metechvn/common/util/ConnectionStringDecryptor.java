package com.metechvn.common.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

public class ConnectionStringDecryptor {
    private static final int ITERATIONS = 1000;
    private static final int KEY_SIZE = 256;

    public static String decrypt(String cipherText, String passPhrase, byte[] salt, byte[] initVector) {
        try {
            byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt, ITERATIONS, KEY_SIZE);
            byte[] keyBytes = factory.generateSecret(spec).getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);
            return new String(plainTextBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
