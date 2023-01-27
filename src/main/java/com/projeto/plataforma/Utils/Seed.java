package com.projeto.plataforma.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;

public class Seed {

    public static JsonObject jsonObject;

    static {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get("seed.json").toString()));

            String line;

            StringBuilder stringBuilder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();

            JsonObject jsonObject = JsonParser.parseString(stringBuilder.toString()).getAsJsonObject();

            Seed.jsonObject =  jsonObject;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
