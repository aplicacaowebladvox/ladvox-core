package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping( "/permission" )
@RestController
@RequiredArgsConstructor
public class PermissionController {

	private final PermissionService service;

	@GetMapping( "/select-options" )
	@PostAuthorize( "hasAnyAuthority( 'controle-acesso.usuarios.form.new', 'controle-acesso.usuarios.form.edit')" )
	public ResponseEntity<List<SelectOptionDTO<UUID, String>>> getSelectOptions() {
		return ResponseEntity.ok( service.getSelectOptions() );
	}
}
