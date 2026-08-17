package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanCommentChatViewDTO;
import br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanTextCommentDTO;
import br.com.jovencio.projetofonocore.services.TherapeuticPlanCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/therapeutic-plan-comment" )
@RestController
@RequiredArgsConstructor
public class TherapeuticPlanCommentController {

	private final TherapeuticPlanCommentService service;

	@GetMapping( "/therapeutic-plan/{therapeuticPlanId}" )
	@PostAuthorize( "hasAuthority('atendimento.plano-terapeutico.comentario.view')" )
	public ResponseEntity<List<TherapeuticPlanCommentChatViewDTO>> findAllByTherapeuticPlanId( @PathVariable long therapeuticPlanId ) {
		return ResponseEntity.ok( service.findAllByTherapeuticPlanId( therapeuticPlanId ) );
	}

	@PatchMapping( "/therapeutic-plan/{therapeuticPlanId}" )
	@PostAuthorize( "hasAnyAuthority('atendimento.plano-terapeutico.comentario.novo', 'atendimento.plano-terapeutico.comentario.admin')" )
	public ResponseEntity<?> newComment( @PathVariable long therapeuticPlanId, @RequestBody TherapeuticPlanTextCommentDTO dto ) {
		service.newComment( therapeuticPlanId, dto );
		return ResponseEntity.ok().build();
	}

	@DeleteMapping( "/{id}" )
	@PostAuthorize( "hasAnyAuthority('atendimento.plano-terapeutico.comentario.novo', 'atendimento.plano-terapeutico.comentario.excluir')" )
	public ResponseEntity<?> deleteComment( @PathVariable long id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}

}
