package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import br.com.jovencio.projetofonocore.model.entities.TeamMedicalAppointmentPlanning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITeamMedicalAppointmentPlanningRepository extends JpaRepository<TeamMedicalAppointmentPlanning, Long>, CustomSearchRepository {

	@Query( value = "SELECT EXISTS (SELECT 1 FROM TeamMedicalAppointmentPlanning tmap JOIN MedicalAppointment ma on ma.team.id = tmap.id WHERE tmap.id = :id) " )
	boolean existsSomeMedicalAppointmentForTeamMedicalAppointmentPlanningId( long id );

	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO( "
			+ "tmap.id, "
			+ "tmap.name, "
			+ "tmap.color "
			+ ") "
			+ "FROM TeamMedicalAppointmentPlanning tmap "
			+ "WHERE tmap.medicalAppointmentPlanning.id = :medicalAppointmentPlanningId " )
	List<SelectOptionExtraAttributeDTO<Long, String, String>> getSelectOptions( Long medicalAppointmentPlanningId );

}
