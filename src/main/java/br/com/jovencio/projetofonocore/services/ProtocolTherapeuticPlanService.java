package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IProtocolRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IProtocolTherapeuticPlanRepository;
import br.com.jovencio.projetofonocore.dao.repositories.ITherapeuticPlanRepository;
import br.com.jovencio.projetofonocore.model.dtos.ProtocolCalculatedDTO;
import br.com.jovencio.projetofonocore.model.dtos.ProtocolTherapeuticPlanDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.entities.ProtocolTherapeuticPlan;
import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlan;
import br.com.jovencio.projetofonocore.model.grid.MyProtocolGrid;
import br.com.jovencio.projetofonocore.model.grid.ProtocolTherapeuticPlanGrid;
import br.com.jovencio.projetofonocore.model.grid.options.MyProtocolGridOptions;
import br.com.jovencio.projetofonocore.model.grid.options.ProtocolTherapeuticPlanGridOptions;
import br.com.jovencio.projetofonocore.services.auth.component.AuthComponent;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProtocolTherapeuticPlanService {

	private final IProtocolTherapeuticPlanRepository repository;
	private final IProtocolRepository protocolRepository;
	private final ITherapeuticPlanRepository therapeuticPlanRepository;
	private final AnswerQuestionProtocolService answerQuestionProtocolService;
	private final AuthComponent authComponent;
	private final ProtocolCalculatorService protocolCalculatorService;
	private final UserNotificationService userNotificationService;

	public SearchReturnDTO<ProtocolTherapeuticPlanGrid> search( ProtocolTherapeuticPlanGridOptions options ) {
		Long count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public ProtocolTherapeuticPlanDTO get( Long id ) {
		return repository.findById( id ).map( ModelMapperUtils::map ).orElseThrow();
	}

	public ProtocolTherapeuticPlanDTO save( ProtocolTherapeuticPlanDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( entity );
		dto = ModelMapperUtils.map( repository.save( entity ) );
		userNotificationService.notifyNewProtocolRequested( entity.getId() );
		return dto;
	}

	public ProtocolTherapeuticPlanDTO save( long id, ProtocolTherapeuticPlanDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( id, entity );
		dto = ModelMapperUtils.map( repository.save( entity ) );
		userNotificationService.notifyNewProtocolRequested( id );
		return dto;
	}

	public void delete( long id ) {
		var entity = repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Protocolo do Plano terapeutico não encontrado" ) );
		if( entity.getAnsweredDate() != null ) {
			throw new IllegalStateException( "Protocolo já respondido não pode ser removido" );
		}
		repository.delete( entity );
		userNotificationService.deleteByProtocolTherapeuticPlanId( id );
	}

	public void saveAnswers( long id, ProtocolTherapeuticPlanDTO dto ) {
		var entity = repository.findById( id ).orElseThrow();
		answerQuestionProtocolService.save( entity, dto.getAnswers() );
		entity.setAnsweredDate( LocalDateTime.now() );
		repository.save( entity );
		userNotificationService.notifyNewProtocolAnswered( id );
	}

	public ProtocolCalculatedDTO findResults( long id ) {
		return protocolCalculatorService.calulate( id );
	}

	private void validate( ProtocolTherapeuticPlan entity ) {
		validate( null, entity );
	}

	private void validate( Long id, ProtocolTherapeuticPlan entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Protocolo do Plano Terapêutico não informado" );
		}
		List<String> errors = new ArrayList<>();
		if( entity.getProtocol() == null || entity.getProtocol().getId() == null ) {
			errors.add( "Protocolo não informado" );
		} else if( protocolRepository.findById( entity.getProtocol().getId() ).isEmpty() ) {
			errors.add( "Protocolo não existente na base de dados" );
		}
		if( entity.getTherapeuticPlan() == null || entity.getTherapeuticPlan().getId() == null ) {
			errors.add( "Plano Terapêutico não informado" );
		} else if( therapeuticPlanRepository.findById( entity.getTherapeuticPlan().getId() ).isEmpty() ) {
			errors.add( "Plano Terapêutico não existente na base de dados" );
		}
		if( id == null ) {
			entity.setRequestDate( LocalDateTime.now() );
		} else {
			repository.findById( id ).ifPresentOrElse( entityFromDatabase -> {
				entity.setRequestDate( entityFromDatabase.getRequestDate() );
				if( entityFromDatabase.getProtocol().getId().compareTo( entity.getProtocol().getId() ) != 0 ) {
					errors.add( "Protocolo não pode ser alterado" );
				}
				if( entityFromDatabase.getTherapeuticPlan().getId().compareTo( Optional.ofNullable( entity.getTherapeuticPlan() ).orElse( new TherapeuticPlan() ).getId() ) != 0 ) {
					errors.add( "Plano Terapêutico não pode ser alterado" );
				}
			}, () -> {
				errors.add( "ID informado não existente na base de dados" );
			} );
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}

	public SearchReturnDTO<MyProtocolGrid> searchMyProtocols( MyProtocolGridOptions options ) {
		options.setUserId( authComponent.getUserDTO().getId() );
		Long count = repository.searchMyProtocolsCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.searchMyProtocols( options ), count );

	}
}
