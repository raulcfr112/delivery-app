package br.ufms.cpcx.api.delivery.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UsuarioDTO {

    private Long id;

    @Size(max = 100)
    private String nome;

    @NotBlank(message = "Senha não pode ser vazia.")
    @Size(min = 6, max = 100, message = "Senha deve ser maior que 6 caracteres.")
    private String senha;

    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Insira um email correto!.")
    private String email;
}
