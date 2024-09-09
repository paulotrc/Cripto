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
    private static Scanner imputReader = new Scanner(System.in);


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //Read keys
        System.out.println("Enter the path of private key to apply to cypher the data");
        System.out.println("Please identify in the same directory if exists public key as \"'publicKey.key'\" and private key as \"'privateKey.key'\" files");
        System.out.println("If the private key doesn't exists, private and public keys will be created in the path informed. Don't worry.");
        String keyPath = imputReader.nextLine();

        System.out.println("Enter the data to cypher");
        String dataInfoToCypher = imputReader.nextLine();

        boolean flagKeyExist = true;
        String pathOfFile = null;
        File privateKeyFile = null;
        File publicKeyFile = null;
        ObjectOutputStream privateKeyStream = null;
        ObjectOutputStream publicKeyStream = null;
        KeyPair key = null;
        PublicKey pubKey = null;
        PrivateKey privKey = null;
        String slashSeparator = "/";

        //home/paulotrc/Desenvolvimento/chaves/publicKey.key
        //c:/Dev/chave/publicKey.key
        //Teste de validaço de cypher

        //verify if files exists
        try{
            if(System.getProperty("os.name").toLowerCase().contains("windows")){
                slashSeparator = "\\";
            }
            privateKeyFile = new File(keyPath);
            pathOfFile = (privateKeyFile.getPath().substring(0, privateKeyFile.getPath().lastIndexOf(slashSeparator) + 1));
            if(!privateKeyFile.exists()){
                publicKeyFile = new File(pathOfFile + "publicKey.key");
                flagKeyExist = false;
            }
        }catch (Exception e){
            flagKeyExist = false;
        }

        //Create keys if doesn't exist
        if(!flagKeyExist){
            try {
                final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
                keyGen.initialize(1024);
                key = keyGen.generateKeyPair();

                if (privateKeyFile.getParentFile() != null) {
                    privateKeyFile.getParentFile().mkdirs();
                }

                privateKeyFile.createNewFile();

                if (publicKeyFile.getParentFile() != null) {
                    publicKeyFile.getParentFile().mkdirs();
                }

                publicKeyFile.createNewFile();

                privateKeyStream = new ObjectOutputStream(
                        new FileOutputStream(privateKeyFile));
                privateKeyStream.writeObject(key.getPrivate());
                privateKeyStream.close();

                publicKeyStream = new ObjectOutputStream(
                        new FileOutputStream(publicKeyFile));
                publicKeyStream.writeObject(key.getPublic());
                publicKeyStream.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(pathOfFile + "publicKey.key"));
            pubKey = (RSAPublicKey) inputStream.readObject();
        }

        //Cypher the data
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Criptografa o texto puro usando a chave Púlica
            cipher.init(Cipher.ENCRYPT_MODE, !flagKeyExist ? key.getPrivate() : privKey);
            cipherText = cipher.doFinal(dataInfoToCypher.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Data applied cypher: " + cipherText);



        //decypher the data
            byte[] dectyptedText = null;

            try {
                final Cipher cipher = Cipher.getInstance(ALGORITHM);
                // Decriptografa o texto puro usando a chave Privada
                cipher.init(Cipher.DECRYPT_MODE, !flagKeyExist ? key.getPublic() : pubKey);
                dectyptedText = cipher.doFinal(cipherText);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println(new String(dectyptedText));
    }
}
