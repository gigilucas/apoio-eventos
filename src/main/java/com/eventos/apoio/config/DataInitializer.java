package com.eventos.apoio.config;

import com.eventos.apoio.model.Participante;
import com.eventos.apoio.model.Role;
import com.eventos.apoio.repository.ParticipanteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ParticipanteRepository participanteRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(ParticipanteRepository participanteRepository, PasswordEncoder passwordEncoder) {
        this.participanteRepository = participanteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Criar utilizador admin se não existir
        if (!participanteRepository.existsByEmail("admin@eventos.com")) {
            Participante admin = new Participante();
            admin.setNome("Administrador");
            admin.setEmail("admin@eventos.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setTelefone("+351912345678");
            admin.setNumeroEstudante("ADMIN001");
            admin.setCurso("Administração");
            admin.setRole(Role.ADMIN);
            participanteRepository.save(admin);
            System.out.println("Utilizador admin criado com sucesso!");
            System.out.println("Email: admin@eventos.com");
            System.out.println("Senha: admin123");
        }
    }
}
