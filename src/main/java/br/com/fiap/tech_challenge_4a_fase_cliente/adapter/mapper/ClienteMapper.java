package br.com.fiap.tech_challenge_4a_fase_cliente.adapter.mapper;

import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.ClienteDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.EnderecoDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.request.ClienteRequestDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.request.ClienteRequestDto.EnderecoRequestDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.response.ClienteResponseDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.controller.response.ClienteResponseDto.EnderecoResponseDto;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.persistence.entity.ClienteEntity;
import br.com.fiap.tech_challenge_4a_fase_cliente.adapter.persistence.entity.EnderecoEntity;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.cliente.Cliente;
import br.com.fiap.tech_challenge_4a_fase_cliente.core.domain.entities.endereco.Endereco;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public static ClienteEntity toClienteEntity(Cliente cliente) {
        if (cliente == null) return null;
        return new ClienteEntity(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getDataNascimento(),
                cliente.getEndereco()
        );
    }

    public static Cliente toClientDomain(ClienteEntity entity) {
        Endereco endereco = toEnderecoDomain(entity.getEndereco());
        if (entity == null) return null;
        return new Cliente(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                entity.getDataNascimento(),
                endereco
        );
    }

    public static EnderecoEntity toEnderecoEntity(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoEntity(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }

    public static Endereco toEnderecoDomain(EnderecoEntity entity) {
        if (entity == null) return null;
        return new Endereco(
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado(),
                entity.getCep()
        );
    }

    public static Cliente toClienteDomain(ClienteRequestDto dto) {
        if (dto == null) return null;
        Endereco endereco = toEnderecoDomain(dto.endereco());
        return new Cliente(
                dto.nome(),
                dto.cpf(),
                dto.dataNascimento(),
                endereco
        );
    }

    public static ClienteResponseDto toClienteResponseDto(Cliente cliente) {
        if (cliente == null) return null;
        EnderecoResponseDto enderecoDto =
                toEnderecoResponseDto(cliente.getEndereco());
        return new ClienteResponseDto(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf().getDocument(), // Ajuste aqui: supondo que getCpf() retorna um objeto CPF, use getNumero() ou toString()
                cliente.getDataNascimento(),
                enderecoDto
        );
    }

    public static ClienteRequestDto toClienteRequestDto(Cliente cliente) {
        if (cliente == null) return null;
        EnderecoResponseDto enderecoDto =
                toEnderecoResponseDto(cliente.getEndereco());
        return new ClienteRequestDto(
                cliente.getNome(),
                cliente.getCpf().getDocument(), // Ajuste aqui: supondo que getCpf() retorna um objeto CPF, use getNumero() ou toString()
                cliente.getDataNascimento(),
                toEnderecoRequestDto(enderecoDto)
        );

    }

    private static EnderecoRequestDto toEnderecoRequestDto(EnderecoResponseDto endereco) {
        if (endereco == null) return null;
        return new EnderecoRequestDto(
                endereco.logradouro(),
                endereco.numero(),
                endereco.complemento(),
                endereco.bairro(),
                endereco.cidade(),
                endereco.estado(),
                endereco.cep()
        );
    }

    private static EnderecoResponseDto toEnderecoResponseDto(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoResponseDto(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }

    private static Endereco toEnderecoDomain(EnderecoRequestDto dto) {
        if (dto == null) return null;
        return new Endereco(
                dto.logradouro(),
                dto.numero(),
                dto.complemento(),
                dto.bairro(),
                dto.cidade(),
                dto.estado(),
                dto.cep()
        );
    }

    public static EnderecoDto toEnderecoDto(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoDto(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }
    public static ClienteDto toClienteDto(Cliente cliente) {
        if (cliente == null) return null;
        return new ClienteDto(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf() != null ? cliente.getCpf().getDocument() : null,
                cliente.getDataNascimento(),
                cliente.getEndereco() != null ? toEnderecoDto(cliente.getEndereco()) : null
        );
    }

    public static ClienteRequestDto toClienteRequestDto(ClienteDto clienteDto) {
        if (clienteDto == null) return null;
        return new ClienteRequestDto(
                clienteDto.getNome(),
                clienteDto.getCpf(),
                clienteDto.getDataNascimento(),
                toEnderecoRequestDto(clienteDto.getEndereco())
        );
    }

    private static EnderecoRequestDto toEnderecoRequestDto(EnderecoDto enderecoDto) {
        if (enderecoDto == null) return null;
        return new EnderecoRequestDto(
                enderecoDto.getLogradouro(),
                enderecoDto.getNumero(),
                enderecoDto.getComplemento(),
                enderecoDto.getBairro(),
                enderecoDto.getCidade(),
                enderecoDto.getEstado(),
                enderecoDto.getCep()
        );
    }
}
