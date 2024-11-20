package br.ufms.cpcx.api.delivery.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class PedidoDTO {

    private Long id;

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long entregadorId;

    @NotNull
    @Size(min = 1)
    private List<Long> idsProdutos;

    @NotBlank
    @Size(max = 255)
    private String enderecoEntrega;
}
