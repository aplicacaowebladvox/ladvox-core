package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.SystemContactDTO;
import br.com.jovencio.projetofonocore.services.SystemContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/system-contact" )
@RestController
@RequiredArgsConstructor
public class SystemContactController {

	private final SystemContactService systemConfigService;

	@GetMapping( "/active" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.contato.view')" )
	public ResponseEntity<SystemContactDTO> getActive() {
		return ResponseEntity.ok( systemConfigService.getActive() );
	}

	@PatchMapping( "/" )
	@PostAuthorize( "hasAuthority('configuracao-sistema.contato.form')" )
	public ResponseEntity<SystemContactDTO> mergeChanges( @RequestBody SystemContactDTO systemContactDTO ) {
		return ResponseEntity.ok( systemConfigService.mergeChanges( systemContactDTO ) );
	}

}
