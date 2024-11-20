package br.ufms.cpcx.api.delivery.service;

import br.ufms.cpcx.api.delivery.dtos.PedidoDTO;
import br.ufms.cpcx.api.delivery.models.Entregador;
import br.ufms.cpcx.api.delivery.models.Pedido;
import br.ufms.cpcx.api.delivery.models.Produto;
import br.ufms.cpcx.api.delivery.models.Usuario;
import br.ufms.cpcx.api.delivery.repositories.EntregadorRepository;
import br.ufms.cpcx.api.delivery.repositories.PedidoRepository;
import br.ufms.cpcx.api.delivery.repositories.ProdutoRepository;
import br.ufms.cpcx.api.delivery.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public Pedido savePedido(PedidoDTO pedidoDto) {
        Usuario usuario = usuarioRepository.findById(pedidoDto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        Entregador entregador = entregadorRepository.findById(pedidoDto.getEntregadorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entregador não encontrado"));

        Double totalProdutos = calcularValorProdutos(pedidoDto.getIdsProdutos());
        if (totalProdutos < 15.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor total dos produtos deve ser maior que R$ 15,00.");
        }

        Double frete = calcularFrete(totalProdutos);

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEntregador(entregador);
        pedido.setIdsProdutos(pedidoDto.getIdsProdutos());
        pedido.setEnderecoEntrega(pedidoDto.getEnderecoEntrega());
        pedido.setFrete(frete);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> findAllPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido findPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @Transactional
    public Pedido updatePedido(Long id, PedidoDTO pedidoDto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        pedido.setIdsProdutos(pedidoDto.getIdsProdutos());
        pedido.setEnderecoEntrega(pedidoDto.getEnderecoEntrega());

        Double totalProdutos = calcularValorProdutos(pedidoDto.getIdsProdutos());
        if (totalProdutos < 15.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor total dos produtos deve ser maior que R$ 15,00.");
        }

        pedido.setFrete(calcularFrete(totalProdutos));
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void deletePedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        pedidoRepository.delete(pedido);
    }

    private Double calcularValorProdutos(List<Long> idsProdutos) {
        double total = 0.0;
        for (Long id : idsProdutos) {
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
            total += produto.getValor();
        }
        return total;
    }

    private Double calcularFrete(Double totalProdutos) {
        if (totalProdutos > 50.0) return 0.0;
        return 2.0;
    }

}

