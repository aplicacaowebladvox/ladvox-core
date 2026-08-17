package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.RequestAppointmentDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.grid.RequestAppointmentGrid;
import br.com.jovencio.projetofonocore.model.grid.options.RequestAppointmentGridOptions;
import br.com.jovencio.projetofonocore.services.RequestAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/request-appointment" )
@RestController
@RequiredArgsConstructor
public class RequestAppointmentController {

	private final RequestAppointmentService service;

	@PostMapping( "/search" )
	@PostAuthorize( "hasAuthority('solicitacoes-atendimento.view')" )
	public ResponseEntity<SearchReturnDTO<RequestAppointmentGrid>> search( @RequestBody RequestAppointmentGridOptions options ) {
		return ResponseEntity.ok( service.search( options ) );
	}

	@GetMapping( "/{id}" )
	@PostAuthorize( "hasAuthority('solicitacoes-atendimento.view')" )
	public ResponseEntity<RequestAppointmentDTO> getById( @PathVariable Long id ) {
		return ResponseEntity.ok( service.getById( id ) );
	}

	@PatchMapping( "/{id}/conclude" )
	@PostAuthorize( "hasAuthority('solicitacoes-atendimento.conclude')" )
	public ResponseEntity<?> conclude( @PathVariable Long id ) {
		service.conclude( id );
		return ResponseEntity.ok().build();
	}
}
