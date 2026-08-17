package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.UserNotificationDTO;
import br.com.jovencio.projetofonocore.services.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/user-notification" )
@RestController
@RequiredArgsConstructor
public class UserNotificationController {

	private final UserNotificationService service;

	@GetMapping( "/filter" )
	public ResponseEntity<List<UserNotificationDTO>> findAllByLoggedUser( @RequestParam( required = false ) Integer amountOfNotifications ) {
		return ResponseEntity.ok( service.findAllByLoggedUser( amountOfNotifications ) );
	}

	@PatchMapping( "/{id}/mark-as-read" )
	public ResponseEntity<?> markAsRead( @PathVariable long id ) {
		service.markAsRead( id );
		return ResponseEntity.ok().build();
	}

	@DeleteMapping( "/{id}" )
	public ResponseEntity<?> delete( @PathVariable long id ) {
		service.delete( id );
		return ResponseEntity.ok().build();
	}
}
