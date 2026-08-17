package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.ITeamMedicalAppointmentPlanningRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IUserRepository;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import br.com.jovencio.projetofonocore.model.dtos.TeamMedicalAppointmentPlanningDTO;
import br.com.jovencio.projetofonocore.model.entities.MedicalAppointmentPlanning;
import br.com.jovencio.projetofonocore.model.entities.TeamMedicalAppointmentPlanning;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMedicalAppointmentPlanningService {

	private final ITeamMedicalAppointmentPlanningRepository repository;
	private final IUserRepository userRepository;

	public List<TeamMedicalAppointmentPlanningDTO> saveAll( long medicalAppointmentPlanningId, List<TeamMedicalAppointmentPlanningDTO> dtos, boolean needValidation ) {
		var entities = ModelMapperUtils.mapAllTeamMedicalAppointmentPlanningDTO( dtos );
		entities.forEach( e -> e.setMedicalAppointmentPlanning( new MedicalAppointmentPlanning( medicalAppointmentPlanningId ) ) );
		if( needValidation ) {
			var invalids = entities.stream().map( e -> validate( e.getId(), e ) ).filter( le -> le != null && !le.isEmpty() ).toList();
			if( !invalids.isEmpty() ) {
				throw new IllegalArgumentException( invalids.toString() );
			}
		}
		entities = repository.saveAll( entities );
		return ModelMapperUtils.mapAllTeamMedicalAppointmentPlanning( entities );
	}

	public void delete( long id ) {
		repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Equipe não encontrada" ) );
		if( repository.existsSomeMedicalAppointmentForTeamMedicalAppointmentPlanningId( id ) ) {
			throw new IllegalStateException( "Não pode remover uma equipe que possui atendimento à paciente " );
		}
		repository.deleteById( id );
	}

	private List<String> validate( Long id, TeamMedicalAppointmentPlanning entity ) {
		if( entity == null ) {
			return List.of( "Equipe de atendimento não informada" );
		}
		List<String> errors = new ArrayList<>();
		if( entity.getName() == null || entity.getName().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 120 ) {
			errors.add( "Nome muito longo(máximo 120 caracteres)" );
		}
		if( entity.getColor() == null || entity.getColor().isEmpty() ) {
			errors.add( "Cor não informada" );
		} else if( !entity.getColor().startsWith( "#" ) ) {
			errors.add( "Cor inválida: precisa começar com '#'" );
		} else if( entity.getColor().length() > 9 ) {
			errors.add( "Cor muito longa(máximo 9 caracteres)" );
		}
		if( entity.getTherapists() == null || entity.getTherapists().isEmpty() ) {
			errors.add( "Nenhum terapeuta informado" );
		} else {
			if( userRepository.findAllById( entity.getTherapists().stream().map( User::getId ).toList() ).stream().toList().size() != entity.getTherapists().size() ) {
				errors.add( "Existem usuários não encontreados na base de dados" );
			}
		}
		return errors;
	}

	public List<SelectOptionExtraAttributeDTO<Long, String, String>> getSelectOptions( Long medicalAppointmentPlanningId ) {
		return repository.getSelectOptions( medicalAppointmentPlanningId );
	}
}
