package br.ufms.cpcx.api.delivery.controllers;

import br.ufms.cpcx.api.delivery.dtos.EntregadorDTO;
import br.ufms.cpcx.api.delivery.models.Entregador;
import br.ufms.cpcx.api.delivery.service.EntregadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/entregador")
public class EntregadorController {

    @Autowired
    private EntregadorService entregadorService;

    @PostMapping
    public ResponseEntity<Entregador> saveEntregador(@RequestBody @Valid EntregadorDTO entregadorDto) {
        Entregador savedEntregador = entregadorService.saveEntregador(entregadorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntregador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entregador> updateEntregador(@PathVariable Long id, @RequestBody @Valid EntregadorDTO entregadorDto) {
        Entregador updatedEntregador = entregadorService.updateEntregador(id, entregadorDto);
        return ResponseEntity.ok(updatedEntregador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntregador(@PathVariable Long id) {
        entregadorService.deleteEntregador(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entregador> getEntregadorById(@PathVariable Long id) {
        Entregador entregador = entregadorService.findEntregadorById(id);
        return ResponseEntity.ok(entregador);
    }

    @GetMapping
    public ResponseEntity<Page<Entregador>> getAllEntregadores(Pageable pageable) {
        Page<Entregador> entregadores = entregadorService.findAllEntregadores(pageable);
        return ResponseEntity.ok(entregadores);
    }
}
