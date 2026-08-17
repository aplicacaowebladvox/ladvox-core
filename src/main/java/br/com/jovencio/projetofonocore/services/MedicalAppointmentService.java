package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IMedicalAppointmentRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IPatientRepository;
import br.com.jovencio.projetofonocore.dao.repositories.ITeamMedicalAppointmentPlanningRepository;
import br.com.jovencio.projetofonocore.model.dtos.MedicalAppointmentDTO;
import br.com.jovencio.projetofonocore.model.dtos.NextSessionDTO;
import br.com.jovencio.projetofonocore.model.entities.MedicalAppointment;
import br.com.jovencio.projetofonocore.model.entities.MedicalAppointmentPlanning;
import br.com.jovencio.projetofonocore.model.entities.Patient;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentService {

	private final IMedicalAppointmentRepository repository;
	private final ITeamMedicalAppointmentPlanningRepository teamMedicalAppointmentPlanningRepository;
	private final IPatientRepository patientRepository;

	public NextSessionDTO getNextSessionByUserId( UUID userId ) {
		return repository.getNextSessionByUserId( userId );
	}

	public List<MedicalAppointmentDTO> saveAll( long medicalAppointmentPlanningId, List<MedicalAppointmentDTO> dtos, boolean needValidation ) {
		var entities = ModelMapperUtils.mapAllMedicalAppointmentDTO( dtos );
		entities.forEach( e -> e.setMedicalAppointmentPlanning( new MedicalAppointmentPlanning( medicalAppointmentPlanningId ) ) );
		if( needValidation ) {
			var invalids = entities.stream().map( e -> validate( e.getId(), e ) ).filter( le -> le != null && !le.isEmpty() ).toList();
			if( !invalids.isEmpty() ) {
				throw new IllegalArgumentException( invalids.toString() );
			}
		}
		if( entities.stream().map( MedicalAppointment::getPatient ).map( Patient::getId ).collect( Collectors.groupingBy( patientId -> patientId, Collectors.counting() ) ).values().stream().anyMatch( v -> v > 1 ) ) {
			throw new IllegalArgumentException( "Cada paciente deve ser atendido por somente uma equipe" );
		}
		entities = repository.saveAll( entities );
		return ModelMapperUtils.mapAllMedicalAppointment( entities );
	}

	public void delete( long id ) {
		repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Equipe não encontrada" ) );
		if( repository.existsSomeTherapeuticPlanForMedicalPlanningId( id ) ) {
			throw new IllegalStateException( "Não pode remover um atendimento que possui plano terapeutico atrelado" );
		}
		repository.deleteById( id );

	}

	private List<String> validate( Long id, MedicalAppointment entity ) {
		if( entity == null ) {
			return List.of( "Atendimento não informado" );
		}
		List<String> errors = new ArrayList<>();
		if( entity.getTeam() == null || entity.getTeam().getId() == null ) {
			errors.add( "Equipe de atendimento não informada" );
		} else {
			teamMedicalAppointmentPlanningRepository.findById( entity.getTeam().getId() ).ifPresentOrElse( teamMedicalAppointmentPlanning -> {
				if( entity.getMedicalAppointmentPlanning() == null || entity.getMedicalAppointmentPlanning().getId() == null ) {
					errors.add( "Planejamento de atendimento não informado" );
				} else if( teamMedicalAppointmentPlanning.getMedicalAppointmentPlanning().getId().compareTo( entity.getMedicalAppointmentPlanning().getId() ) != 0 ) {
					errors.add( "O atendimento e a equipe precisam estar cadastrados no mesmo Planejamento" );
				}
			}, () -> {
				errors.add( "Equipe de atendimento não encontrada no banco de dados" );
			} );
		}
		if( entity.getRoom() == null || entity.getRoom().isEmpty() ) {
			errors.add( "Sala não informada" );
		} else if( entity.getRoom().length() > 50 ) {
			errors.add( "Sala muito grande(máximo 50 caracteres)" );
		}
		if( entity.getPatient() == null || entity.getPatient().getId() == null ) {
			errors.add( "Paciente não informado" );
		} else if( patientRepository.findById( entity.getPatient().getId() ).isEmpty() ) {
			errors.add( "Paciente não encontreado na base de dados" );
		}
		if( entity.getWeekDay() == null ) {
			errors.add( "Dia do atendimento não informado" );
		}
		if( entity.getHour() == null ) {
			errors.add( "Hora não informada" );
		}
		return errors;
	}

}
