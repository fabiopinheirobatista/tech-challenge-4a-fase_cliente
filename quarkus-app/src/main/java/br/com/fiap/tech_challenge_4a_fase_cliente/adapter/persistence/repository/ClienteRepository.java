package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.persistence.repository;

import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.persistence.entity.ClienteEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<ClienteEntity> {
    public Optional<ClienteEntity> findByCpf(String cpf) {
        return find("cpf", cpf).firstResultOptional();
    }
}
