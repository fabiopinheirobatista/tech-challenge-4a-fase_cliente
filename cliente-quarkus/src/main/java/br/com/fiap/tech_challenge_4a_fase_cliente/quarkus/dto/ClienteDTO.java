package br.com.fiap.tech_challenge_4a_fase_cliente.quarkus.dto;

import java.time.LocalDate;

public class ClienteDTO {
    public Long id;
    public String nome;
    public String cpf;
    public LocalDate dataNascimento;
    public String logradouro;
    public String numero;
    public String complemento;
    public String bairro;
    public String cidade;
    public String estado;
    public String cep;
}
