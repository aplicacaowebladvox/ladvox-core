package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.NextSessionDTO;
import br.com.jovencio.projetofonocore.model.entities.MedicalAppointment;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public interface IMedicalAppointmentRepository extends JpaRepository<MedicalAppointment, Long>, CustomSearchRepository {

	@Transactional
	default NextSessionDTO getNextSessionByUserId( UUID userId ) {
		var sql = new StringBuilder();

		sql.append( " select " );
		sql.append( "     medicalAppointment.cd_medical_appointment_week_day as weekday, " );
		sql.append( "     patient.nm_patient as patientName, " );
		sql.append( "     medicalAppointment.hr_medical_appointment as sessionHour, " );
		sql.append( "     medicalAppointment.cd_medical_appointment_room as sessionRoom, " );
		sql.append( "     concat(medicalAppointmentPlanning.nm_medical_appointment_planning, ': ', teamMedicalAppointmentPlanning.nm_team_medical_appointment_planning) as therapistsTeamDescription " );
		sql.append( " from medical_appointments medicalAppointment " );
		sql.append( " inner join medical_appointment_plannings medicalAppointmentPlanning " );
		sql.append( "    on medicalAppointmentPlanning.id_medical_appointment_planning = medicalAppointment.id_medical_appointment_planning " );
		sql.append( "   and current_date " );
		sql.append( "     between medicalAppointmentPlanning.dt_medical_appointment_planning_initial_validity " );
		sql.append( "         and coalesce(medicalAppointmentPlanning.dt_medical_appointment_planning_final_validity, current_date) " );
		sql.append( " inner join teams_medical_appointment_planning teamMedicalAppointmentPlanning " );
		sql.append( "    on teamMedicalAppointmentPlanning.id_medical_appointment_planning = medicalAppointmentPlanning.id_medical_appointment_planning " );
		sql.append( " inner join m2m_teamsmedicalappointmentplanning_users m2m_team_user " );
		sql.append( "    on m2m_team_user.id_team_medical_appointment_planning = teamMedicalAppointmentPlanning.id_team_medical_appointment_planning " );
		sql.append( " inner join users therapist " );
		sql.append( "    on therapist.id_user = m2m_team_user.id_user " );
		sql.append( " inner join patients patient " );
		sql.append( "    on patient.id_patient = medicalAppointment.id_patient " );
		sql.append( " where therapist.id_user = :userId " );
		sql.append( " order by weekday, sessionHour " );

		var query = getEntityManager().createNativeQuery( sql.toString(), Tuple.class );
		query.setParameter( "userId", userId );
		return NextSessionDTO.map( Optional.ofNullable( query.getResultList() ).orElse( Collections.emptyList() ) );
	}

	@Query( value = "SELECT EXISTS (SELECT 1 FROM MedicalAppointment ma JOIN TherapeuticPlan tp on tp.medicalAppointment.id = ma.id WHERE ma.id = :id) " )
	boolean existsSomeTherapeuticPlanForMedicalPlanningId( long id );

}
