package br.com.jovencio.projetofonocore.controllers;

import br.com.jovencio.projetofonocore.model.dtos.*;
import br.com.jovencio.projetofonocore.services.PublicHomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/public/home" )
@RestController
public class PublicHomeController {

	private final PublicHomeService service;

	PublicHomeController( PublicHomeService service ) {
		this.service = service;
	}

	@GetMapping( "contacts" )
	public ResponseEntity<List<SystemContactDTO>> getContactsActives() {
		return ResponseEntity.ok( service.getContactsActives() );
	}

	@GetMapping( "social-medias" )
	public ResponseEntity<List<SocialMediaDTO>> getSocialMediasActives() {
		return ResponseEntity.ok( service.getSocialMediasActives() );
	}

	@GetMapping( "articles" )
	public ResponseEntity<List<ArticleDTO>> getArticlesActives() {
		return ResponseEntity.ok( service.getArticlesActives() );
	}

	@GetMapping( "users-members" )
	public ResponseEntity<List<UserMemberLandingPageDTO>> getUserMembersLandingPage() {
		return ResponseEntity.ok( service.getUserMembersLandingPage() );
	}

	@GetMapping( "projects" )
	public ResponseEntity<List<ProjectDTO>> getProjectsActives() {
		return ResponseEntity.ok( service.getProjectsActives() );
	}

	@GetMapping( "publications" )
	public ResponseEntity<List<PublicationDTO>> getPublicationsActives() {
		return ResponseEntity.ok( service.getPublicationsActives() );
	}

	@PostMapping( "request-appointment" )
	public ResponseEntity<RequestAppointmentDTO> insert( @RequestBody RequestAppointmentDTO dto ) {
		return ResponseEntity.ok( service.requestAppointment( dto ) );
	}
}
