package com.metechvn.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CryptoUtils {
    private CryptoUtils() {}

    public static String decrypt(String cipherText, String passPhrase, String salt, String iv) {
        var ivBytes = new byte[16];
        var sourceIvBytes = iv.getBytes();
        System.arraycopy(sourceIvBytes, 0, ivBytes, 0, Math.min(sourceIvBytes.length, ivBytes.length));


        return decrypt(cipherText, passPhrase, salt.getBytes(), ivBytes, 1000, 256);
    }

    public static String decrypt(String cipherText, String passPhrase, byte[] salt, byte[] initVector, int iterations, int keySize) {
        try {
            var cipherTextBytes = Base64.getDecoder().decode(cipherText);
            var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            var spec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterations, keySize);
            var keyBytes = factory.generateSecret(spec).getEncoded();
            var secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            var ivParameterSpec = new IvParameterSpec(initVector);
            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            var plainTextBytes = cipher.doFinal(cipherTextBytes);

            return new String(plainTextBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}
