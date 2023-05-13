package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.*;
import com.projeto.plataforma.persistence.model.*;
import com.projeto.plataforma.web.dto.AlunoDTO;
import com.projeto.plataforma.web.dto.DisciplinaDTO;
import com.projeto.plataforma.web.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CurrentUser currentUser;


    @GetMapping("/buscarAluno/{id}")
    public ResponseEntity<Object> buscarAlunoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoRepository.findById(id).get());
    }

    @GetMapping("/buscarAlunoPorNome/{email}")
    public ResponseEntity<Object> buscarAlunoPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(alunoRepository.findByEmail(email).get());
    }

    @GetMapping("/buscarAluno")
    public ResponseEntity<Object> buscarAlunoPorHeader(@RequestHeader HttpHeaders headers) {
        try {
            Usuario user = currentUser.getCurrentUser(headers);
            Aluno aluno = alunoRepository.findById(user.getId()).get();

            return ResponseEntity.ok(aluno);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }
    }

    @GetMapping("/buscarAlunosPorInstituicaoId")
    public ResponseEntity<Object> buscarAlunosPorInstituicaoId(@RequestHeader HttpHeaders headers) {
        try {
            Usuario user = currentUser.getCurrentUser(headers);
            List<Aluno> alunos = alunoRepository.findAlunosByInstituicaoIdQuery(user.getId());

            return ResponseEntity.ok(alunos);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }
    }

    @GetMapping("/buscarAlunosPorInstituicaoIdAndEmail")
    public ResponseEntity<Object> buscarAlunosPorInstituicaoIdAndEmail(@RequestHeader HttpHeaders headers, @RequestParam String texto) {
        try {
            Usuario user = currentUser.getCurrentUser(headers);
            List<Aluno> alunos = alunoRepository.findAlunosByInstituicaoIdAndEmailQuery(user.getId(), texto);

            return ResponseEntity.ok(alunos);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }
    }

    @PostMapping(value = "/cadastrarAluno", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> cadastrarAluno(@RequestBody AlunoDTO alunoDTO) {

        try {
            Aluno aluno = new Aluno();
            aluno.setNome(alunoDTO.getNome());
            aluno.setEmail(alunoDTO.getEmail());
            aluno.setCursoAluno(cursoRepository.findById(alunoDTO.getCursoAluno().getId()).get());
            aluno.setPassword(encoder.encode(aluno.getEmail()));
            aluno.setAtivo(true);
            aluno.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
            return ResponseEntity.ok(alunoRepository.save(aluno));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/cadastrarAlunoApi", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> cadastrarAlunoApi(@RequestBody Aluno aluno) {
        try {
            aluno.setPassword(encoder.encode(aluno.getPassword()));
            return ResponseEntity.ok(alunoRepository.save(aluno));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editarAluno", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> editarAluno(@RequestHeader HttpHeaders headers, @RequestBody AlunoDTO alunoDTO) {
        try {
            Optional<Aluno> optAluno = alunoRepository.findById(alunoDTO.getId());
            if(optAluno.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Aluno alunoDb = optAluno.get();

            List<Long> disciplinaIds = alunoDTO.getDisciplinasInteresse().stream().map(DisciplinaDTO::getId).collect(Collectors.toList());
            Set<Disciplina> disciplinasInteresse = disciplinaRepository.findAllByIdIn(disciplinaIds);
            if(!alunoDTO.getNome().isEmpty()) {
                alunoDb.setNome(alunoDTO.getNome());
            }
            alunoDb.setDisciplinasInteresse(disciplinasInteresse);
            alunoDb.setSobre(alunoDTO.getSobre());
            alunoDb.setLinkedin(alunoDTO.getLinkedin());
            alunoDb.setInstagram(alunoDTO.getInstagram());
            alunoDb.setFacebook(alunoDTO.getFacebook());
            alunoDb.setWhatsapp(alunoDTO.getWhatsapp());

            alunoRepository.save(alunoDb);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + ex.getMessage().toString());
        }
    }

    @PutMapping(value = "/editarAlunoInstituicao", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> editarAlunoInstituicao(@RequestHeader HttpHeaders headers, @RequestBody AlunoDTO alunoDTO) {
        try {
            Optional<Aluno> optAluno = alunoRepository.findById(alunoDTO.getId());
            if(optAluno.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Aluno alunoDb = optAluno.get();

            if(!alunoDTO.getNome().isEmpty()) {
                alunoDb.setNome(alunoDTO.getNome());
            }
            if(!alunoDTO.getEmail().isEmpty()) {
                alunoDb.setEmail(alunoDTO.getEmail());
            }

            Curso curso = cursoRepository.findById(alunoDTO.getCursoAluno().getId()).get();

            alunoDb.setCursoAluno(curso);

            alunoRepository.save(alunoDb);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + ex.getMessage().toString());
        }
    }

    @PutMapping("/alterarStatusAluno")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> alterarStatusAluno(@RequestParam Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId).get();
        aluno.setAtivo(!aluno.isEnabled());
        alunoRepository.save(aluno);

        return  ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletarAluno/{alunoId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> deletarAluno(@PathVariable Long alunoId) {
        try {

        Aluno aluno = alunoRepository.findById(alunoId).get();
        aluno.setDisciplinasInteresse(new HashSet<>());
        List<Tema> temas = aluno.getTemasAluno();

        for (Tema tema: temas) {
            tema.setCandidatosTema(new HashSet<>());
            tema.setDisciplinasRelacionadas(new HashSet<>());
            temaRepository.save(tema);

            temaRepository.deleteById(tema.getId());
        }

        Set<Tema> candidaturas = aluno.getCandidaturasAluno();

        for (Tema tema: candidaturas) {
            tema.removerCandidatoTema(aluno);
            temaRepository.save(tema);
        }

        alunoRepository.save(aluno);

        alunoRepository.deleteById(alunoId);

        return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return  ResponseEntity.ok().build();
        }
    }
}
