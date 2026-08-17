package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.ITherapeuticPlanEditorHistoryRepository;
import br.com.jovencio.projetofonocore.dao.repositories.ITherapeuticPlanEditorHistoryTextVersionRepository;
import br.com.jovencio.projetofonocore.dao.repositories.ITherapeuticPlanRepository;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import br.com.jovencio.projetofonocore.model.dtos.*;
import br.com.jovencio.projetofonocore.model.entities.MedicalAppointment;
import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlan;
import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlanEditorHistory;
import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlanEditorHistoryTextVersion;
import br.com.jovencio.projetofonocore.model.grid.TherapeuticPlanGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TherapeuticPlanGridOptions;
import br.com.jovencio.projetofonocore.services.auth.component.AuthComponent;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TherapeuticPlanService {

	private final ITherapeuticPlanRepository repository;
	private final AuthComponent authComponent;
	private final ITherapeuticPlanEditorHistoryRepository therapeuticPlanEditorHistoryRepository;
	private final ITherapeuticPlanEditorHistoryTextVersionRepository therapeuticPlanEditorHistoryTextVersionRepository;

	public SearchReturnDTO<TherapeuticPlanGrid> search( TherapeuticPlanGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public TherapeuticPlanDTO get( Long id ) {
		return repository.findById( id ).map( ModelMapperUtils::map ).orElseThrow( () -> new IllegalArgumentException( "Plano terapêutico não encontrado" ) );
	}

	public TherapeuticPlanDTO save( TherapeuticPlanDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( entity );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public TherapeuticPlanDTO save( long id, TherapeuticPlanDTO dto ) {
		return null;
	}

	public TherapeuticPlanDTO takeOverAsEditor( Long therapeuticPlanId, Long medicalAppointmentId ) {
		if( therapeuticPlanId == null && medicalAppointmentId == null ) {
			throw new IllegalArgumentException( "Plano Terapeutico e Consulta não informados" );
		}
		if( therapeuticPlanId != null ) {
			var therapeuticPlan = repository.findById( therapeuticPlanId ).orElseThrow();
			var currentEditor = therapeuticPlan.getEditor();
			if( currentEditor != null && currentEditor.getEndedDate() == null ) {
				throw new IllegalArgumentException( "Plano Terapeutico já possui editor ativo" );
			}
			changeCurrentEditorByTherapeuticPlanId( therapeuticPlanId, authComponent.getUserDTO().getId() );
		} else {
			var entity = generate( medicalAppointmentId );
			therapeuticPlanId = entity.getId();
			changeCurrentEditorByTherapeuticPlanId( entity.getId(), authComponent.getUserDTO().getId() );
		}
		return new TherapeuticPlanDTO( therapeuticPlanId );
	}

	public void resignAsEditor( long id ) {
		var currentEditor = repository.findById( id ).orElseThrow().getEditor();
		if( !currentEditor.getEditor().getId().equals( authComponent.getUserDTO().getId() ) ) {
			throw new IllegalArgumentException();
		}
		currentEditor.setEndedDate( LocalDateTime.now() );
		currentEditor.setEndedUser( currentEditor.getEditor() );
		therapeuticPlanEditorHistoryRepository.save( currentEditor );
	}

	public void changeCurrentEditor( Long therapeuticPlanId, Long medicalAppointmentId, UUID newUserEditorId ) {
		if( therapeuticPlanId == null && medicalAppointmentId == null ) {
			throw new IllegalArgumentException( "Plano Terapeutico e Consulta não informados" );
		}
		if( therapeuticPlanId != null ) {
			changeCurrentEditorByTherapeuticPlanId( therapeuticPlanId, newUserEditorId );
		} else {
			changeCurrentEditorByTherapeuticPlanId( generate( medicalAppointmentId ).getId(), newUserEditorId );
		}
	}

	private TherapeuticPlan generate( Long medicalAppointmentId ) {
		return repository.findByMedicalAppointmentId( medicalAppointmentId ).orElseGet( () -> {
			var therapeuticPlan = new TherapeuticPlan();
			therapeuticPlan.setMedicalAppointment( new MedicalAppointment( medicalAppointmentId ) );
			return repository.save( therapeuticPlan );
		} );
	}

	public void changeCurrentEditorByTherapeuticPlanId( long therapeuticPlanId, UUID newUserEditorId ) {
		var therapeuticPlan = repository.findById( therapeuticPlanId ).orElseThrow();
		if( therapeuticPlan.getMedicalAppointment().getTeam().getTherapists().stream().filter( user -> user.getId().equals( newUserEditorId ) ).findFirst().isEmpty() ) {
			throw new IllegalArgumentException( "Usuário não faz parte desse Plano Terapêutico" );
		}
		var currentEditor = therapeuticPlan.getEditor();

		var therapeuticPlanEditorHistory = new TherapeuticPlanEditorHistory();
		therapeuticPlanEditorHistory.setTherapeuticPlan( therapeuticPlan );
		therapeuticPlanEditorHistory.setEditor( new User( newUserEditorId ) );
		therapeuticPlanEditorHistory.setStartedDate( LocalDateTime.now() );
		therapeuticPlanEditorHistory.setStartedUser( new User( authComponent.getUserDTO().getId() ) );
		if( currentEditor != null && currentEditor.getStartedDate() == null ) {
			if( currentEditor.getEditor().getId().equals( newUserEditorId ) ) {
				throw new IllegalArgumentException( "Usuário já é o atual editor desse Plano Terapêutico" );
			}
			currentEditor.setEndedDate( therapeuticPlanEditorHistory.getStartedDate() );
			currentEditor.setEndedUser( therapeuticPlanEditorHistory.getStartedUser() );
			therapeuticPlanEditorHistoryRepository.save( currentEditor );
		}
		therapeuticPlanEditorHistoryRepository.save( therapeuticPlanEditorHistory );
	}

	public TherapeuticPlanDTO findOrGenerate( Long therapeuticPlanId, Long medicalAppointmentId ) {
		if( therapeuticPlanId == null ) {
			return ModelMapperUtils.map( generate( medicalAppointmentId ) );
		}
		return repository.findById( therapeuticPlanId ).map( ModelMapperUtils::map ).orElseThrow( () -> new IllegalArgumentException( "Plano Terapêutico não encontrado" ) );
	}

	public void updateTherapeuticTextPlan( long id, TherapeuticTextPlanDTO dto ) {
		var therapeuticPlan = repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Plano Terapêutico não encontrado" ) );
		if( Optional.ofNullable( therapeuticPlan.getTherapeuticTextPlan() ).orElse( "" ).equals( Optional.ofNullable( dto.getTherapeuticTextPlan() ).orElse( "" ) ) ) {
			return;
		}
		therapeuticPlan.setTherapeuticTextPlan( dto.getTherapeuticTextPlan() );
		validate( therapeuticPlan );
		therapeuticPlanEditorHistoryTextVersionRepository.save( TherapeuticPlanEditorHistoryTextVersion.builder().therapeuticPlanEditorHistory( therapeuticPlan.getEditor() ).therapeuticTextPlan( dto.getTherapeuticTextPlan() ).createdDate( LocalDateTime.now() ).build() );
		repository.save( therapeuticPlan );
	}

	public TherapeuticTextPlanDTO getTherapeuticTextPlan( long id ) {
		var therapeuticPlan = repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Plano Terapêutico não encontrado" ) );
		return new TherapeuticTextPlanDTO( therapeuticPlan.getEditor() != null ? therapeuticPlan.getEditor().getEditor().getId() : null, therapeuticPlan.getTherapeuticTextPlan() );
	}

	public TherapeuticPlanBaseDataDTO getTherapeuticPlanBaseData( long id ) {
		var therapeuticPlan = repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Plano Terapêutico não encontrado" ) );
		var dto = new TherapeuticPlanBaseDataDTO();

		dto.setId( therapeuticPlan.getId() );
		var medicalAppointment = therapeuticPlan.getMedicalAppointment();
		dto.setMedicalAppointmentId( medicalAppointment.getId() );
		dto.setMedicalAppointmentRoom( medicalAppointment.getRoom() );
		dto.setMedicalAppointmentWeekDay( medicalAppointment.getWeekDay() );
		var patient = medicalAppointment.getPatient();
		dto.setMedicalAppointmentPatientId( patient.getId() );
		dto.setMedicalAppointmentPatientName( patient.getName() );
		var medicalAppointmentPlanning = medicalAppointment.getMedicalAppointmentPlanning();
		dto.setMedicalAppointmentMedicalAppointmentPlanningId( medicalAppointmentPlanning.getId() );
		dto.setMedicalAppointmentMedicalAppointmentPlanningName( medicalAppointmentPlanning.getName() );
		return dto;
	}

	private void validate( TherapeuticPlan therapeuticPlan ) {
		if( therapeuticPlan == null ) {
			throw new IllegalArgumentException( "Plano Terapêutico não encontrado" );
		}
		if( therapeuticPlan.getTherapeuticTextPlan() != null && ( therapeuticPlan.getTherapeuticTextPlan().isEmpty() || therapeuticPlan.getTherapeuticTextPlan().isBlank() ) ) {
			therapeuticPlan.setTherapeuticTextPlan( null );
		}
	}

	public List<SelectOptionExtraAttributeDTO<UUID, String, String>> loadTherapists( Long therapeuticPlanId, Long medicalAppointmentId ) {
		if( therapeuticPlanId == null && medicalAppointmentId == null ) {
			throw new IllegalArgumentException( "Plano Terapeutico e Consulta não informados" );
		}
		if( therapeuticPlanId != null ) {
			return repository.findAllTherapists( therapeuticPlanId );
		} else {
			return repository.findAllTherapists( generate( medicalAppointmentId ).getId() );
		}
	}
}
