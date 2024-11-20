package br.ufms.cpcx.api.delivery.service;

import br.ufms.cpcx.api.delivery.dtos.EntregadorDTO;
import br.ufms.cpcx.api.delivery.models.Entregador;
import br.ufms.cpcx.api.delivery.repositories.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EntregadorService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Transactional
    public Entregador saveEntregador(EntregadorDTO entregadorDto) {

        Entregador entregador = new Entregador();
        entregador.setNome(entregadorDto.getNome());
        entregador.setContato(entregadorDto.getContato());

        return entregadorRepository.save(entregador);
    }

    @Transactional
    public Entregador updateEntregador(Long id, EntregadorDTO entregadorDto) {
        Entregador entregador = entregadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entregador não encontrado"));

        entregador.setNome(entregadorDto.getNome());
        entregador.setContato(entregadorDto.getContato());

        return entregadorRepository.save(entregador);
    }

    @Transactional
    public void deleteEntregador(Long id) {
        Entregador entregador = entregadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entregador não encontrado"));

        entregadorRepository.delete(entregador);
    }

    public Entregador findEntregadorById(Long id) {
        return entregadorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entregador não encontrado"));
    }

    public Page<Entregador> findAllEntregadores(Pageable pageable) {
        return entregadorRepository.findAll(pageable);
    }
}

