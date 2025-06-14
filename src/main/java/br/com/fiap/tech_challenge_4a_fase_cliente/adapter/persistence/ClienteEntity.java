package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "clientes")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @Embedded
    private EnderecoEntity endereco;
}
