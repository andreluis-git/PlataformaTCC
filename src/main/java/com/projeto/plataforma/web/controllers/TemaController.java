package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.AlunoRepository;
import com.projeto.plataforma.persistence.dao.DisciplinaRepository;
import com.projeto.plataforma.persistence.dao.TemaRepository;
import com.projeto.plataforma.persistence.model.Aluno;
import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.model.Tema;
import com.projeto.plataforma.persistence.model.Usuario;
import com.projeto.plataforma.web.dto.DisciplinaDTO;
import com.projeto.plataforma.web.dto.TemaDTO;
import com.projeto.plataforma.web.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tema")
public class TemaController {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private EntityManager em;

    @GetMapping("/buscarTema")
    public ResponseEntity<Object> buscarTemaPorId(@RequestParam Long id) {

        try {
            return ResponseEntity.ok(temaRepository.findById(id).get());
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscarTemasPorTituloOuDescricao")
    public ResponseEntity<Object> buscarTemasPorTituloOuDescricao(@RequestHeader HttpHeaders headers, @RequestParam String texto) {

        try {
            Usuario user = currentUser.getCurrentUser(headers);
            return ResponseEntity.ok(temaRepository.findAllByTituloAndDescricaoQuery(texto, user.getId()));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscarTemasAnunciadosPorTituloOuDescricao")
    public ResponseEntity<Object> buscarTemasAnunciadosPorTituloOuDescricao(@RequestHeader HttpHeaders headers, @RequestParam String texto) {

        try {
            Usuario user = currentUser.getCurrentUser(headers);

            List<Tema> temas = temaRepository.findAllAnunciadosByTituloAndDescricaoQuery(texto, user.getId());

            return ResponseEntity.ok(temas);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscarTemasCandidaturasPorTituloOuDescricao")
    public ResponseEntity<Object> buscarTemasCandidaturasPorTituloOuDescricao(@RequestHeader HttpHeaders headers, @RequestParam String texto) {

        try {
            Usuario user = currentUser.getCurrentUser(headers);
            return ResponseEntity.ok(temaRepository.findAllCandidaturasByTituloAndDescricaoQuery(texto, user.getId()));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listarTemas")
    public ResponseEntity<Object> listarTemas(@RequestHeader HttpHeaders headers) {

        try {
            Usuario user = currentUser.getCurrentUser(headers);
            Aluno aluno = alunoRepository.findById(user.getId()).get();

            List<Long> disciplinaIds = aluno.getDisciplinasInteresse().stream().map(Disciplina::getId).collect(Collectors.toList());

            List<Tema> temas = temaRepository.findAllTemasQuery(disciplinaIds, aluno.getId(), aluno.getCursoAluno().getId());

            return ResponseEntity.ok(temas);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }

    }

    @GetMapping("/listarTemasAnunciados")
    public ResponseEntity<Object> listarTemasAnunciados(@RequestHeader HttpHeaders headers) {

        try {
            Usuario user = currentUser.getCurrentUser(headers);
            Aluno aluno = alunoRepository.findById(user.getId()).get();

            List<Tema> temas = temaRepository.findAllByCriadorTemaIdOrderByIdDesc(aluno.getId());

            return ResponseEntity.ok(temas);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }

    }

    @GetMapping("/listarTemasCandidaturas")
    public ResponseEntity<Object> listarTemasCandidaturas(@RequestHeader HttpHeaders headers) {

        try {
            Usuario user = currentUser.getCurrentUser(headers);
            Aluno aluno = alunoRepository.findById(user.getId()).get();

            List<Tema> temas = temaRepository.findAllByCandidatosTemaIdOrderByIdDesc(aluno.getId());

            return ResponseEntity.ok(temas);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }

    }

    @GetMapping("/testeListaTema")
    public ResponseEntity<Object> testeListaTema(@RequestHeader HttpHeaders headers) {

        try {
            Usuario user = currentUser.getCurrentUser(headers);
            Aluno aluno = alunoRepository.findById(user.getId()).get();

            List<Tema> temas = temaRepository.findAllQuery(aluno.getCursoAluno().getId());

            return ResponseEntity.ok(temas);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }

    }

    @PostMapping(value="/gravarTema", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> gravarTema(@RequestHeader HttpHeaders headers, @RequestBody TemaDTO temaDTO) {
        try {
            if(temaDTO == null) {
                return ResponseEntity.badRequest().build();
            }

            Usuario user = currentUser.getCurrentUser(headers);
            Aluno aluno = alunoRepository.findById(user.getId()).get();

            Tema tema = new Tema();
            tema.setTitulo(temaDTO.getTitulo());
            tema.setDescricao(temaDTO.getDescricao());
            if(temaDTO.getDisciplinasRelacionadas().isEmpty()) {
                temaDTO.setDisciplinasRelacionadas(new ArrayList<>());
            }
            List<Long> disciplinaIds = temaDTO.getDisciplinasRelacionadas().stream().map(DisciplinaDTO::getId).collect(Collectors.toList());
            Set<Disciplina> disciplinasRelacionadas = disciplinaRepository.findAllByIdIn(disciplinaIds);
            tema.setDisciplinasRelacionadas(disciplinasRelacionadas);
            tema.setCursoTema(aluno.getCursoAluno());
            tema.setCriadorTema(aluno);

            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            String strDate = dateFormat.format(date);

            tema.setDataCriacao(strDate);
            tema = temaRepository.save(tema);

            return ResponseEntity.ok(tema);
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value="/editarTema", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> editarTema(@RequestBody TemaDTO temaDTO) {

        try {
            if(temaDTO == null) {
                return ResponseEntity.badRequest().build();
            }

            Tema tema = temaRepository.findById(temaDTO.getId()).get();
            tema.setTitulo(temaDTO.getTitulo());
            tema.setDescricao(temaDTO.getDescricao());
            List<Long> disciplinaIds = temaDTO.getDisciplinasRelacionadas().stream().map(DisciplinaDTO::getId).collect(Collectors.toList());
            Set<Disciplina> disciplinasRelacionadas = disciplinaRepository.findAllByIdIn(disciplinaIds);
            tema.setDisciplinasRelacionadas(disciplinasRelacionadas);
            tema.setAtivo(temaDTO.getAtivo());

            tema = temaRepository.save(tema);

            return ResponseEntity.ok(tema);
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletarTema/{temaId}")
    public ResponseEntity<Object> deletarTema(@RequestHeader HttpHeaders headers, @PathVariable Long temaId) {
        try {
            Tema tema = temaRepository.findById(temaId).get();
            tema.setCandidatosTema(new HashSet<>());
            tema.setDisciplinasRelacionadas(new HashSet<>());
            temaRepository.save(tema);

            temaRepository.deleteById(temaId);

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }
    }

    @PostMapping(value = "/candidatarTema", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> candidatarTema(@RequestHeader HttpHeaders headers, @RequestParam Long temaId) {

        try {
            Usuario user = currentUser.getCurrentUser(headers);
            Aluno aluno = alunoRepository.findById(user.getId()).get();

            Tema temaBanco = temaRepository.findById(temaId).orElse(null);

            if (temaBanco == null) {
                return ResponseEntity.badRequest().body("Tema não encontrado");
            }

            if (aluno.getCandidaturasAluno().stream().map(Tema::getId).filter(temaId::equals).findFirst().isPresent()) {
                return ResponseEntity.badRequest().body("Tema já candidatado");
            }

            temaBanco.adicionarCandidatoTema(aluno);

            temaRepository.save(temaBanco);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }

    }

    @PostMapping(value = "/removerCandidaturaTema", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> removerCandidaturaTema(@RequestHeader HttpHeaders headers, @RequestParam Long temaId) {

        try {
            Usuario user = currentUser.getCurrentUser(headers);
            Aluno aluno = alunoRepository.findById(user.getId()).get();

            Tema temaBanco = temaRepository.findById(temaId).orElse(null);

            if (temaBanco == null) {
                return ResponseEntity.badRequest().body("Tema não encontrado");
            }

            if (!aluno.getCandidaturasAluno().stream().map(Tema::getId).filter(temaId::equals).findFirst().isPresent()) {
                return ResponseEntity.badRequest().body("Aluno não é candidato do tema");
            }

            temaBanco.removerCandidatoTema(aluno);

            temaRepository.save(temaBanco);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }

    }

    @GetMapping("/listarCandidatosTema")
    public ResponseEntity<Object> listarCandidatosTema(@RequestParam Long temaId) {

        try {
            List<Aluno> candidatos = alunoRepository.findAllByCandidaturasAlunoId(temaId);

            return ResponseEntity.ok(candidatos);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }

    }

}
