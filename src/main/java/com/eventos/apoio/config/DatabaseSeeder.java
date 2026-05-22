package com.eventos.apoio.config;

import com.eventos.apoio.enums.EstadoInscricao;
import com.eventos.apoio.model.Certificado;
import com.eventos.apoio.model.Evento;
import com.eventos.apoio.model.Inscricao;
import com.eventos.apoio.model.Participante;
import com.eventos.apoio.repository.CertificadoRepository; // <--- Adicionado
import com.eventos.apoio.repository.EventoRepository;
import com.eventos.apoio.repository.InscricaoRepository;
import com.eventos.apoio.repository.ParticipanteRepository;
import com.eventos.apoio.repository.SessaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ParticipanteRepository participanteRepository;
    private final EventoRepository eventoRepository;
    private final SessaoRepository sessaoRepository;
    private final InscricaoRepository inscricaoRepository;
    private final CertificadoRepository certificadoRepository;

    // Construtor atualizado com TODOS os repositórios necessários
    public DatabaseSeeder(ParticipanteRepository participanteRepository,
                          EventoRepository eventoRepository,
                          SessaoRepository sessaoRepository,
                          InscricaoRepository inscricaoRepository,
                          CertificadoRepository certificadoRepository) {
        this.participanteRepository = participanteRepository;
        this.eventoRepository = eventoRepository;
        this.sessaoRepository = sessaoRepository;
        this.inscricaoRepository = inscricaoRepository;
        this.certificadoRepository = certificadoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🌱 A iniciar o Seeder da Base de Dados...");

        // 1. Criar ou buscar o Participante de teste
        Participante p1 = null;
        if (participanteRepository.count() == 0) {
            p1 = new Participante();
            p1.setNome("Gigi Breusson");
            p1.setEmail("gigi@email.com");
            p1.setCurso("Engenharia Informática");
            p1.setNumeroEstudante("12345");
            p1.setTelefone("912345678");
            p1 = participanteRepository.save(p1);
            System.out.println("✅ Participante de teste inserido com sucesso!");
        } else {
            p1 = participanteRepository.findAll().get(0);
        }

        // 2. Criar ou buscar o Evento de teste
        Evento e1 = null;
        if (eventoRepository.count() == 0) {
            e1 = new Evento();
            e1.setNome("Fórum de Empregabilidade 2026");
            e1.setDescricao("Evento de networking com empresas tecnológicas.");
            e1.setLocal("Auditório Principal");
            e1.setCapacidadeMaxima(150);
            e1.setEstaAtivo(true);
            e1.setDataInicio(LocalDateTime.now());
            e1.setDataFim(LocalDateTime.now().plusDays(2));
            e1 = eventoRepository.save(e1);
            System.out.println("✅ Evento de teste inserido com sucesso!");
        } else {
            e1 = eventoRepository.findAll().get(0);
        }

        // 3. Criar Inscrição de teste (Liga o Participante p1 ao Evento e1)
        Inscricao i1 = null; // <--- 3. Declarada fora para ser visível no passo 4!
        if (inscricaoRepository.count() == 0 && p1 != null && e1 != null) {
            i1 = new Inscricao();
            i1.setParticipante(p1);
            i1.setEvento(e1);
            i1.setEstadoInscricao(EstadoInscricao.Confirmada); // Se der erro aqui, muda para 'Pendente' ou 'Confirmada' conforme o teu Enum

            i1 = inscricaoRepository.save(i1);
            System.out.println("✅ Inscrição de teste realizada com sucesso!");
        } else if (inscricaoRepository.count() > 0) {
            i1 = inscricaoRepository.findAll().get(0); // Busca a inscrição existente para o certificado usar
        }

        // 4. Emitir Certificado de teste automático ligado à inscrição i1
        if (certificadoRepository.count() == 0 && i1 != null) {
            Certificado c1 = new Certificado();
            c1.setInscricao(i1);
            c1.setCodigoCertificado("CERT-TESTE123"); // Definimos um código base para o teste do Seeder

            certificadoRepository.save(c1);
            System.out.println("✅ Certificado de teste emitido e gravado com sucesso!");
        }
    }
}