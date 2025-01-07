package org.example;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Scanner;

public class Main {

    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;
    private static final Scanner INPUT_READER = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            String keyPath = getKeyPathFromUser();
            String dataToEncrypt = getDataFromUser();

            File privateKeyFile = new File(keyPath);
            KeyPair keyPair = loadOrCreateKeyPair(privateKeyFile);

            byte[] encryptedData = encryptData(dataToEncrypt, keyPair.getPublic());
            System.out.println("Data encrypted: " + new String(encryptedData));

            byte[] decryptedData = decryptData(encryptedData, keyPair.getPrivate());
            System.out.println("Decrypted data: " + new String(decryptedData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getKeyPathFromUser() {
        System.out.println("Enter the path of private key to apply to encrypt the data. Example Windows: c:/keycripto/privateKey.key");
        System.out.println("Please identify in the same directory if exists public key as 'publicKey.key' and private key as 'privateKey.key' files");
        System.out.println("If the private key doesn't exist, private and public keys will be created.");
        return INPUT_READER.nextLine();
    }

    private static String getDataFromUser() {
        System.out.println("Enter the data to encrypt");
        return INPUT_READER.nextLine();
    }

    private static KeyPair loadOrCreateKeyPair(File privateKeyFile) throws Exception {
        File publicKeyFile = new File(privateKeyFile.getParent(), "publicKey.key");

        // Verifica se as chaves já existem
        if (privateKeyFile.exists()) {
            return loadKeyPair(privateKeyFile, publicKeyFile);
        } else {
            return createKeyPair(privateKeyFile, publicKeyFile);
        }
    }

    private static KeyPair loadKeyPair(File privateKeyFile, File publicKeyFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream privateKeyStream = new ObjectInputStream(new FileInputStream(privateKeyFile));
             ObjectInputStream publicKeyStream = new ObjectInputStream(new FileInputStream(publicKeyFile))) {

            PrivateKey privateKey = (PrivateKey) privateKeyStream.readObject();
            PublicKey publicKey = (RSAPublicKey) publicKeyStream.readObject();
            return new KeyPair(publicKey, privateKey);
        }
    }

    private static KeyPair createKeyPair(File privateKeyFile, File publicKeyFile) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(KEY_SIZE); // Usando 2048 bits para maior segurança
        KeyPair keyPair = keyGen.generateKeyPair();

        // Salva as chaves nos arquivos
        saveKeyToFile(privateKeyFile, keyPair.getPrivate());
        saveKeyToFile(publicKeyFile, keyPair.getPublic());

        return keyPair;
    }

    private static void saveKeyToFile(File keyFile, Object key) throws IOException {
        if (keyFile.getParentFile() != null && !keyFile.getParentFile().exists()) {
            keyFile.getParentFile().mkdirs();
        }
        try (ObjectOutputStream keyStream = new ObjectOutputStream(new FileOutputStream(keyFile))) {
            keyStream.writeObject(key);
        }
    }

    private static byte[] encryptData(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    private static byte[] decryptData(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
}
