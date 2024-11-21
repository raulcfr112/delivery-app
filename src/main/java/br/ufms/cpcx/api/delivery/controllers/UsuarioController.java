package br.ufms.cpcx.api.delivery.controllers;

import br.ufms.cpcx.api.delivery.dtos.UsuarioDTO;
import br.ufms.cpcx.api.delivery.models.Usuario;
import br.ufms.cpcx.api.delivery.models.UsuarioRepresentationModelAssembler;
import br.ufms.cpcx.api.delivery.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    final UsuarioRepresentationModelAssembler usuarioRepresentationModelAssembler;
    final PagedResourcesAssembler pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> saveUsuario(@RequestBody @Valid UsuarioDTO usuarioDto) {
        EntityModel<Usuario> usuario = usuarioRepresentationModelAssembler
                .toModel(usuarioService.saveUsuario(usuarioDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDto) {
        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuarioDto);
        EntityModel<Usuario> usuario = usuarioRepresentationModelAssembler.toModel(updatedUsuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findUsuarioById(id);
        EntityModel<Usuario> usuarioModel = usuarioRepresentationModelAssembler.toModel(usuario);
        return ResponseEntity.ok(usuarioModel);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Usuario>> getAllUsuarios(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        var page = usuarioService.findAllUsuarios(pageable);
        PagedModel<Usuario> collModel = pagedResourcesAssembler.toModel(page, usuarioRepresentationModelAssembler);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }

    @GetMapping("/isCadastrado")
    public boolean isUsuarioCadastrado(@RequestBody UsuarioDTO usuarioDto) {
        return usuarioService.isUsuarioCadastrado(usuarioDto.getEmail(), usuarioDto.getSenha());
    }
}
