package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlan;
import br.com.jovencio.projetofonocore.model.grid.TherapeuticPlanGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TherapeuticPlanGridOptions;
import br.com.jovencio.projetofonocore.utils.TupleUtils;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface ITherapeuticPlanRepository extends JpaRepository<TherapeuticPlan, Long>, CustomSearchRepository {

	default Long searchCount( TherapeuticPlanGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT COUNT(1) AS counter " );
		mountSearchFromClause( hql, options, params );

		var query = mountNativeSearchCount( hql, Tuple.class );
		params.forEach( query::setParameter );
		return TupleUtils.getLong( ( Tuple ) query.getSingleResult(), "counter" );
	}

	default List<TherapeuticPlanGrid> search( TherapeuticPlanGridOptions options ) {
		var sql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		sql.append( " SELECT " );
		sql.append( "     therapeuticPlan.id_therapeutic_plan AS id, " );
		sql.append( "     patient.id_patient AS patientId, " );
		sql.append( "     patient.nm_patient AS patientName, " );
		sql.append( "     ( " );
		sql.append( "         SELECT CAST(therapeuticPlanEditorHistory.id_user_editor AS TEXT) " );
		sql.append( "         FROM therapeutic_plan_editors_history therapeuticPlanEditorHistory " );
		sql.append( "         WHERE therapeuticPlanEditorHistory.id_therapeutic_plan = therapeuticPlan.id_therapeutic_plan " );
		sql.append( "           AND therapeuticPlanEditorHistory.dt_therapeutic_plan_editor_history_ended IS NULL " );
		sql.append( "         ORDER BY therapeuticPlanEditorHistory.dt_therapeutic_plan_editor_history_ended DESC NULLS FIRST " );
		sql.append( "         LIMIT 1 " );
		sql.append( "     ) AS currentEditorId, " );
		sql.append( "     medicalAppointmentPlanning.id_medical_appointment_planning AS medicalAppointmentPlanningId, " );
		sql.append( "     CONCAT( " );
		sql.append( "             '(', " );
		sql.append( "             medicalAppointmentPlanning.id_medical_appointment_planning, " );
		sql.append( "             ') - ', " );
		sql.append( "             medicalAppointmentPlanning.nm_medical_appointment_planning, " );
		sql.append( "             ' válido de ', " );
		sql.append( "             TO_CHAR(medicalAppointmentPlanning.dt_medical_appointment_planning_initial_validity, 'dd/mm/yyyy'), " );
		sql.append( "             CASE " );
		sql.append( "                 WHEN medicalAppointmentPlanning.dt_medical_appointment_planning_final_validity IS NULL THEN ' em diante' " );
		sql.append( "                 ELSE CONCAT(' até ', TO_CHAR(medicalAppointmentPlanning.dt_medical_appointment_planning_final_validity, 'dd/MM/yyyy')) " );
		sql.append( "             END " );
		sql.append( "     ) AS medicalAppointmentPlanningDescription, " );
		sql.append( "     teamMedicalAppointmentPlanning.id_team_medical_appointment_planning AS medicalAppointmentTeamId, " );
		sql.append( "     teamMedicalAppointmentPlanning.cd_team_medical_appointment_planning_hex_color AS medicalAppointmentTeamColor, " );
		sql.append( "     CONCAT( " );
		sql.append( "         teamMedicalAppointmentPlanning.nm_team_medical_appointment_planning, " );
		sql.append( "         ': ', " );
		sql.append( "         ( " );
		sql.append( "             select string_agg(u.nm_user, ', ') " );
		sql.append( "             from users u " );
		sql.append( "             inner join M2M_TEAMSMEDICALAPPOINTMENTPLANNING_USERS mtu on mtu.id_user = u.id_user " );
		sql.append( "             where mtu.id_team_medical_appointment_planning = teamMedicalAppointmentPlanning.id_team_medical_appointment_planning " );
		sql.append( "         ) " );
		sql.append( "     ) AS medicalAppointmentTeamDescription, " );
		sql.append( "     medicalAppointment.id_medical_appointment AS medicalAppointmentId, " );
		sql.append( "     medicalAppointment.cd_medical_appointment_room AS medicalAppointmentRoom, " );
		sql.append( "     medicalAppointment.cd_medical_appointment_week_day AS medicalAppointmentWeekDay, " );
		sql.append( "     medicalAppointment.hr_medical_appointment AS medicalAppointmentHour " );
		mountSearchFromClause( sql, options, params );
		var query = mountNativeSearch( sql, options, Tuple.class );
		params.forEach( query::setParameter );
		return Optional.ofNullable( query.getResultList() ).orElse( Collections.emptyList() ).stream().map( t -> new TherapeuticPlanGrid( ( Tuple ) t ) ).toList();
	}

	private void mountSearchFromClause( StringBuilder sql, TherapeuticPlanGridOptions options, Map<String, Object> params ) {
		sql.append( " FROM medical_appointments medicalAppointment " );
		sql.append( " LEFT JOIN therapeutic_plans therapeuticPlan " );
		sql.append( "    ON therapeuticPlan.id_medical_appointment = medicalAppointment.id_medical_appointment " );
		sql.append( " LEFT JOIN patients patient " );
		sql.append( "    ON patient.id_patient = medicalAppointment.id_patient " );
		sql.append( " LEFT JOIN medical_appointment_plannings medicalAppointmentPlanning " );
		sql.append( "    ON medicalAppointmentPlanning.id_medical_appointment_planning = medicalAppointment.id_medical_appointment_planning " );
		sql.append( " LEFT JOIN teams_medical_appointment_planning teamMedicalAppointmentPlanning " );
		sql.append( "    ON teamMedicalAppointmentPlanning.id_team_medical_appointment_planning = medicalAppointment.id_team_medical_appointment_planning " );
		sql.append( " WHERE 1=1 " );

		if( options.getId() != null ) {
			sql.append( "   AND therapeuticPlan.id_therapeutic_plan = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getPatientId() != null ) {
			sql.append( "  AND patient.id_patient = :patientId " );
			params.put( "patientId", options.getPatientId() );
		}
	}

	@Query( value = "SELECT tp FROM TherapeuticPlan tp WHERE tp.medicalAppointment.id = :medicalAppointmentId " )
	Optional<TherapeuticPlan> findByMedicalAppointmentId( Long medicalAppointmentId );

	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO( "
			+ " therapist.id, "
			+ " therapist.name, "
			+ " CASE WHEN profileImage IS NOT NULL THEN profileImage.base64 ELSE NULL END "
			+ " ) "
			+ " FROM TherapeuticPlan therapeuticPlan "
			+ " JOIN therapeuticPlan.medicalAppointment medicalAppointment "
			+ " JOIN medicalAppointment.team team "
			+ " JOIN team.therapists therapist "
			+ " LEFT JOIN therapist.profileImage profileImage "
			+ " WHERE therapeuticPlan.id = :therapeuticPlanId " )
	List<SelectOptionExtraAttributeDTO<UUID, String, String>> findAllTherapists( Long therapeuticPlanId );
}
