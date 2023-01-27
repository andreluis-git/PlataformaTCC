package com.projeto.plataforma.Utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Constants {

    public static final String urlRandomImg = "https://picsum.photos";

    //region String de imagem base64
    public static String imgBase64String;

    static  {
        try {
            imgBase64String = (new String(Files.readAllBytes(Paths.get("imgBase64.txt"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
