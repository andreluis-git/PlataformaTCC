package com.projeto.plataforma.web.util;

import com.projeto.plataforma.persistence.dao.*;
import com.projeto.plataforma.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private InstituicaoRepository instituicaoRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TemaRepository temaRepository;


    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final Role userRole = createRoleIfNotFound("ROLE_USER", adminPrivileges);
        final Role instituicaoRole = createRoleIfNotFound("ROLE_INSTITUICAO", adminPrivileges);

        // == create initial user
        createUserIfNotFound("admin@email.com", "Admin", "senha", new ArrayList<>(Arrays.asList(adminRole)));
        createUserIfNotFound("user@email.com", "User", "senha", new ArrayList<>(Arrays.asList(userRole)));
        Instituicao instituicao1 = createInstituicaoIfNotFound("instituicaoFatec@email.com", "InstituicaoFatec", "senha", new ArrayList<>(Arrays.asList(instituicaoRole)));
        Instituicao instituicao2 = createInstituicaoIfNotFound("instituicaoUSP@email.com", "InstituicaoUSP", "senha", new ArrayList<>(Arrays.asList(instituicaoRole)));

        // create initial cursos
        Curso cursoADS = createCursoIfNotFound("Analise e desenvolvimento de sistemas", "ADS", instituicao1);
        Curso cursoGE = createCursoIfNotFound("Gestão empresarial", "GE", instituicao1);
        Curso cursoGA = createCursoIfNotFound("Gestão ambiental", "GA", instituicao2);

        // create initial disciplinas
        Disciplina disciplinaADS1 = createDisciplinaIfNotFound("Estrutura de dados", "ED", cursoADS);
        Disciplina disciplinaADS2 = createDisciplinaIfNotFound("Programacao orientada a objetos", "POO", cursoADS);
        Disciplina disciplinaGE1 = createDisciplinaIfNotFound("Sociedade tecnologia e inovacao", "STI", cursoGE);
        Disciplina disciplinaGA1 = createDisciplinaIfNotFound("Conservacao do meio ambiente", "CMA", cursoGA);

        //create initial alunos
        for (int i = 0; i < 40; i++) {
            Aluno aluno = new Aluno();
            aluno.setEmail("aluno"+i+"@email.com");
            aluno.setNome("Aluno"+i);
            aluno.setPassword("senha");

            if (i <= 20) {
                aluno.setCursoAluno(cursoADS);
                if (i < 10) {
                    aluno.setDisciplinasInteresse(new ArrayList<>(Arrays.asList(disciplinaADS1)));
                }
                else {
                    aluno.setDisciplinasInteresse(new ArrayList<>(Arrays.asList(disciplinaADS2)));
                }
            } else if (i <= 30){
                aluno.setCursoAluno(cursoGE);
                aluno.setDisciplinasInteresse(new ArrayList<>(Arrays.asList(disciplinaGE1)));
            } else {
                aluno.setCursoAluno(cursoGA);
                aluno.setDisciplinasInteresse(new ArrayList<>(Arrays.asList(disciplinaGA1)));
            }

            aluno = createAlunoIfNotFound(aluno, new ArrayList<>(Arrays.asList(userRole)));

            if (aluno.getId() == 15 && temaRepository.findByTitulo("Um titulo qualquer 0").orElse(null) == null) {
                for (int j = 0; j < 25; j++) {
                    Tema tema = new Tema();
                    tema.setCriadorTema(aluno);
                    tema.setTitulo("Um titulo qualquer " + j);
                    tema.setDescricao("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
                    tema.setCursoTema(aluno.getCursoAluno());
                    tema.setDisciplinasRelacionadas(new ArrayList<>(Arrays.asList(disciplinaADS1)));

                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = dateFormat.format(date);

                    tema.setDataCriacao(strDate);

                    temaRepository.save(tema);
                }
            }
        }

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    Usuario createUserIfNotFound(final String email, final String firstName, final String password, final Collection<Role> roles) {
        Usuario user = usuarioRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = new Usuario();
            user.setNome(firstName);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setAtivo(true);
        }
        user.setRoles(roles);
        user = usuarioRepository.save(user);
        return user;
    }

    @Transactional
    Curso createCursoIfNotFound(final String nome, final String sigla, final Instituicao instituicao) {
        Curso curso = cursoRepository.findByNome(nome).orElse(null);

        if (curso == null) {
            curso = new Curso();
            curso.setNome(nome);
            curso.setSigla(sigla);
            curso.setInstituicaoCurso(instituicao);
        }

        curso = cursoRepository.save(curso);
        return curso;
    }

    @Transactional
    Disciplina createDisciplinaIfNotFound(final String nome, final String sigla, final Curso curso) {
        Disciplina disciplina = disciplinaRepository.findByNome(nome).orElse(null);

        if (disciplina == null) {
            disciplina = new Disciplina();
            disciplina.setNome(nome);
            disciplina.setSigla(sigla);
            disciplina.setCursoDisciplina(curso);
        }

        disciplina = disciplinaRepository.save(disciplina);

        return disciplina;
    }

    @Transactional
    Aluno createAlunoIfNotFound(final Aluno aluno, final Collection<Role> roles) {
        Aluno alunoBanco = alunoRepository.findByEmail(aluno.getEmail()).orElse(null);

        if (alunoBanco == null) {
            alunoBanco = aluno;
            alunoBanco.setPassword(passwordEncoder.encode(aluno.getPassword()));
            alunoBanco.setAtivo(true);
            alunoBanco.setRoles(roles);
            alunoBanco = alunoRepository.save(alunoBanco);
        }
        return alunoBanco;
    }

    @Transactional
    Instituicao createInstituicaoIfNotFound(final String email, final String firstName, final String password, final Collection<Role> roles) {
        Instituicao instituicaoBanco = instituicaoRepository.findByEmail(email).orElse(null);

        if (instituicaoBanco == null) {
            instituicaoBanco = new Instituicao();
            instituicaoBanco.setNome(firstName);
            instituicaoBanco.setPassword(passwordEncoder.encode(password));
            instituicaoBanco.setEmail(email);
            instituicaoBanco.setAtivo(true);
        }
        instituicaoBanco.setRoles(roles);
        instituicaoBanco = instituicaoRepository.save(instituicaoBanco);

        return instituicaoBanco;
    }
}
