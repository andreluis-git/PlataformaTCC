package com.projeto.plataforma.Config.Security.Permissoes;

import com.projeto.plataforma.persistence.model.Privilege;
import com.projeto.plataforma.persistence.model.Role;
import com.projeto.plataforma.persistence.model.Usuario;
import com.projeto.plataforma.persistence.dao.UsuarioRepository;
import com.projeto.plataforma.persistence.dao.PrivilegeRepository;
import com.projeto.plataforma.persistence.dao.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        final Role userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);

        // == create initial user
        createUserIfNotFound("useradmin@test.com", "Test", "Test", "test", new ArrayList<>(Arrays.asList(adminRole)));
        createUserIfNotFound("user@test.com", "Test", "Test", "test", new ArrayList<>(Arrays.asList(userRole)));

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
    Usuario createUserIfNotFound(final String email, final String firstName, final String lastName, final String password, final Collection<Role> roles) {
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

}
