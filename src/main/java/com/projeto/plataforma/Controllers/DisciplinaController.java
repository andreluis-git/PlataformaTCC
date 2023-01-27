package com.projeto.plataforma.Controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.projeto.plataforma.Model.Disciplina;
import com.projeto.plataforma.Repository.DisciplinaRepository;
import com.projeto.plataforma.Utils.Seed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;


    @GetMapping("/listarDisciplinas")
    public ResponseEntity<Object> listarDisciplinas() {
        List<Disciplina> listaDisciplinas = disciplinaRepository.findAll();
        return ResponseEntity.ok(listaDisciplinas);
    }

//    @GetMapping("/json")
//    public ResponseEntity<Object> json() {
//        try {
//            JsonObject jsObj;
//            JsonArray jsonArray = Seed.jsonObject.getAsJsonArray("usuarios");
//            Iterator<JsonElement> it = jsonArray.iterator();
//            while(it.hasNext()) {
//                jsObj = it.next().getAsJsonObject();
//                System.out.println(jsObj.get("login").getAsString());
//            }
//
//            return ResponseEntity.ok().build();
//        } catch(Exception ex) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}
