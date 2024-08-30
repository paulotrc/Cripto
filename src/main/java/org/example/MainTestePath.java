package org.example;

import java.io.File;
import java.io.IOException;

public class MainTestePath {

    public static void main(String[] args) throws IOException {
        String path = "/home/paulotrc/keys/privateKey.key";
        File fileKey = new File(path);
        if(!fileKey.exists()){
            System.out.println(fileKey.getPath().substring(0, fileKey.getPath().lastIndexOf("/") + 1));

            if (fileKey.getParentFile() != null) {
                fileKey.getParentFile().mkdirs();
            }
        }
    }
}
