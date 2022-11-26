package com.database.atypon.Node.services.authentication;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RSAEncryption {

    public final String ALGORITHM = "RSA";
    public final SecretKey secretKey = new SecretKeySpec("1234567890123456".getBytes(), ALGORITHM);

    //encrypt method
    public byte[] encrypt(byte[] plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedText = cipher.doFinal(plainText);
        return encryptedText;
    }

    //decrypt method
    public byte[] decrypt(byte[] encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedText = cipher.doFinal(encryptedText);
        return decryptedText;
    }



}
