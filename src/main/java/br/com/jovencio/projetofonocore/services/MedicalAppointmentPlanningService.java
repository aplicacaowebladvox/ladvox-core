package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IMedicalAppointmentPlanningRepository;
import br.com.jovencio.projetofonocore.model.dtos.*;
import br.com.jovencio.projetofonocore.model.entities.MedicalAppointmentPlanning;
import br.com.jovencio.projetofonocore.model.grid.MedicalAppointmentPlanningGrid;
import br.com.jovencio.projetofonocore.model.grid.options.MedicalAppointmentPlanningGridOptions;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentPlanningService {

	private final IMedicalAppointmentPlanningRepository repository;
	private final TeamMedicalAppointmentPlanningService teamMedicalAppointmentPlanningService;
	private final MedicalAppointmentService medicalAppointmentService;
	private final MedicalAppointmentPlanningAttachmentService medicalAppointmentPlanningAttachmentService;

	public SearchReturnDTO<MedicalAppointmentPlanningGrid> search( MedicalAppointmentPlanningGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public Object get( Long id, Integer step ) {
		var error = new IllegalArgumentException( "Planejamento não encontrado na base de dados" );
		if( step == 2 ) {
			return repository.findById( id ).map( ModelMapperUtils::mapSecondStep ).orElseThrow( () -> error );
		} else if( step == 3 ) {
			return repository.findById( id ).map( ModelMapperUtils::mapThirdStep ).orElseThrow( () -> error );
		}
		return repository.findById( id ).map( ModelMapperUtils::mapFirstStep ).orElseThrow();
	}

	public MedicalAppointmentPlanningFirstStepDTO save( MedicalAppointmentPlanningFirstStepDTO dto ) {
		var entity = ModelMapperUtils.mapFirstStep( dto );
		validate( entity );
		return ModelMapperUtils.mapFirstStep( repository.save( entity ) );
	}

	public MedicalAppointmentPlanningFirstStepDTO save( long id, MedicalAppointmentPlanningFirstStepDTO dto ) {
		var entity = ModelMapperUtils.mapFirstStep( dto );
		validate( id, entity );
		repository.updateFirstStep( id, entity );
		return ModelMapperUtils.mapFirstStep( entity );
	}

	public MedicalAppointmentPlanningSecondStepDTO saveSecond( long id, MedicalAppointmentPlanningSecondStepDTO dto ) {
		dto.setTeams( teamMedicalAppointmentPlanningService.saveAll( id, dto.getTeams(), true ) );
		return dto;
	}

	public MedicalAppointmentPlanningThirdStepDTO saveThird( long id, MedicalAppointmentPlanningThirdStepDTO dto ) {
		dto.setMedicalAppointments( medicalAppointmentService.saveAll( id, dto.getMedicalAppointments(), true ) );
		return dto;
	}

	public List<SelectOptionDTO<Long, String>> getSelectOptions() {
		return repository.getSelectOptions();
	}

	private void validate( MedicalAppointmentPlanning entity ) {
		validate( null, entity );
	}

	public void deleteTeamMedicalAppointmentPlanning( long id, long teamMedicalAppointmentPlanningId ) {
		repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Planejamento não encontrado" ) );
		teamMedicalAppointmentPlanningService.delete( teamMedicalAppointmentPlanningId );
	}

	public void deleteMedicalAppointment( long id, long medicalAppointmentId ) {
		repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Planejamento não encontrado" ) );
		medicalAppointmentService.delete( medicalAppointmentId );
	}

	public List<AttachmentDTO> findAllAttachmentsOfMedicalAppointmentPlanning( long id ) {
		return medicalAppointmentPlanningAttachmentService.findAllAttachmentsOfMedicalAppointmentPlanning( id );
	}

	public AttachmentDTO saveAttachmentForMedicalAppointmentPlanning( long id, AttachmentDTO dto ) {
		return medicalAppointmentPlanningAttachmentService.saveAttachmentForMedicalAppointmentPlanning( id, dto );
	}

	public void deleteAttachment( long attachmentId ) {
		medicalAppointmentPlanningAttachmentService.deleteByAttachment( attachmentId );
	}

	private void validate( Long id, MedicalAppointmentPlanning entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Planejamento de atendimento não informado" );
		}
		List<String> errors = new ArrayList<>();

		if( entity.getName() == null || entity.getName().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 120 ) {
			errors.add( "Nome muito longo(máximo 120 caracteres)" );
		}
		if( entity.getInitialValidity() == null ) {
			errors.add( "Vigência inicial deve ser informada" );
		} else if( entity.getFinalValidity() != null && !entity.getFinalValidity().isAfter( entity.getInitialValidity() ) ) {
			errors.add( "Vigência final deve ser depois da inicial" );
		}

		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}
}
