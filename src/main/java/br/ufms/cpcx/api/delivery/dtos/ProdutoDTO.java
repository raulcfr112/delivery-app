package br.ufms.cpcx.api.delivery.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProdutoDTO {

    private Long id;

    @NotBlank
    @Size(max = 255)
    private String descricao;

    @NotNull
    private Double valor;

    @NotBlank
    @Size(max = 100)
    private String categoria;
}
