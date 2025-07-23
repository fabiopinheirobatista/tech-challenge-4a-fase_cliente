package br.com.fiap.tech_challenge_4a_fase_cliente.quarkus.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
public class Cliente extends PanacheEntity {

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false, unique = true, length = 14)
    public String cpf;

    @Column(name = "data_nascimento")
    public LocalDate dataNascimento;

    @Column
    public String logradouro;

    @Column
    public String numero;

    @Column
    public String complemento;

    @Column
    public String bairro;

    @Column
    public String cidade;

    @Column
    public String estado;

    @Column(length = 9)
    public String cep;
}
