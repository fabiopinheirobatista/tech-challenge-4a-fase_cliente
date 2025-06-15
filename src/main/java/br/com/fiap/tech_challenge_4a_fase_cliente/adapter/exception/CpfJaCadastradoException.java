package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.exception;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String cpf) {
        super("Cliente com CPF " + cpf + " já cadastrado.");
    }
}
