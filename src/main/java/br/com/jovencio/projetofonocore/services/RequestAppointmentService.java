package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IRequestAppointmentRepository;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import br.com.jovencio.projetofonocore.model.dtos.RequestAppointmentDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.entities.RequestAppointment;
import br.com.jovencio.projetofonocore.model.grid.RequestAppointmentGrid;
import br.com.jovencio.projetofonocore.model.grid.options.RequestAppointmentGridOptions;
import br.com.jovencio.projetofonocore.services.auth.component.AuthComponent;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import br.com.jovencio.projetofonocore.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestAppointmentService {

	private final IRequestAppointmentRepository repository;
	private final AuthComponent authComponent;

	public RequestAppointmentDTO insert( RequestAppointmentDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( entity );
		entity.setCreatedDate( LocalDateTime.now() );
		repository.save( entity );
		return ModelMapperUtils.map( entity );
	}

	public SearchReturnDTO<RequestAppointmentGrid> search( RequestAppointmentGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public RequestAppointmentDTO getById( Long id ) {
		return repository.findById( id ).map( ModelMapperUtils::map ).orElseThrow( () -> new IllegalArgumentException( "Solicitação de Consulta não encontrada" ) );
	}

	public void conclude( Long id ) {
		var entity = repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Solicitação de Consulta não encontrada" ) );
		if( entity.getConclusionDate() != null ) {
			throw new IllegalStateException( "Requisição já conclusa" );
		}
		entity.setConclusionDate( LocalDateTime.now() );
		entity.setUserOfConclusion( new User( authComponent.getUserDTO().getId() ) );
		repository.save( entity );
	}

	private void validate( RequestAppointment entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Solicitação de consulta não informada" );
		}
		List<String> errors = new ArrayList<>();

		if( entity.getName() == null || entity.getName().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 120 ) {
			errors.add( "Nome muito longo(máximo 120 caracteres)" );
		}
		if( entity.getEmail() == null ) {
			errors.add( "E-mail deve ser informado" );
		} else if( !ValidationUtils.mail( entity.getEmail() ) ) {
			errors.add( "E-mail deve ser válido" );
		}

		if( entity.getPhone1() != null && !entity.getPhone1().isEmpty() && !entity.getPhone1().isBlank() ) {
			if( !ValidationUtils.phone( entity.getPhone1() ) ) {
				errors.add( "Telefone 1 deve ser válido" );
			}
		}
		if( entity.getPhone2() != null && !entity.getPhone2().isEmpty() && !entity.getPhone2().isBlank() ) {
			if( !ValidationUtils.phone( entity.getPhone2() ) ) {
				errors.add( "Telefone 2 deve ser válido" );
			}
		}
		if( ( entity.getPhone1() == null || entity.getPhone1().isBlank() || entity.getPhone1().isEmpty() ) && ( entity.getPhone2() == null || entity.getPhone2().isBlank() || entity.getPhone2().isEmpty() ) ) {
			errors.add( "Deve ser informado pelo menos um telefone para contato" );
		}
		if( entity.getProblemDescription() == null || entity.getProblemDescription().isEmpty() || entity.getProblemDescription().isBlank() ) {
			errors.add( "Descrição do problema deve ser informada" );
		} else if( entity.getProblemDescription().length() > 500 ) {

			errors.add( "Descrição do problema muito longo(máximo 500 caracteres)" );
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}

}
