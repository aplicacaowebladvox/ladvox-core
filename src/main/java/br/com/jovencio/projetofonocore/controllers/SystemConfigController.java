package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.auth.dtos.ChooseableUserRoleDTO;
import br.com.jovencio.projetofonocore.model.dtos.FastActionDTO;
import br.com.jovencio.projetofonocore.model.dtos.MenuResponseDTO;
import br.com.jovencio.projetofonocore.model.dtos.NextSessionDTO;
import br.com.jovencio.projetofonocore.services.SystemConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/system-config" )
@RestController
@RequiredArgsConstructor
public class SystemConfigController {

	private final SystemConfigService systemConfigService;

	@PostMapping( "menu" )
	public ResponseEntity<MenuResponseDTO> getMenu( @RequestBody ChooseableUserRoleDTO choice ) {
		return ResponseEntity.ok( systemConfigService.getMenu( choice ) );
	}

	@PatchMapping( "performed-action" )
	public ResponseEntity<?> registerNewPerformedAction( @RequestParam long menuItemId ) {
		this.systemConfigService.registerNewPerformedAction( menuItemId );
		return ResponseEntity.ok().build();
	}

	@GetMapping( "fast-actions" )
	public ResponseEntity<FastActionDTO> getFastActions( @RequestHeader( "Authorization" ) String token ) {
		return ResponseEntity.ok( systemConfigService.getFastActions( token.split( " " )[1] ) );
	}

	@GetMapping( "next-session" )
	public ResponseEntity<NextSessionDTO> getNextSession() {
		return ResponseEntity.ok( systemConfigService.getNextSession() );
	}

}
