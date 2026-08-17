package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.entities.MedicalAppointmentPlanning;
import br.com.jovencio.projetofonocore.model.grid.MedicalAppointmentPlanningGrid;
import br.com.jovencio.projetofonocore.model.grid.ProtocolGrid;
import br.com.jovencio.projetofonocore.model.grid.options.MedicalAppointmentPlanningGridOptions;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IMedicalAppointmentPlanningRepository extends JpaRepository<MedicalAppointmentPlanning, Long>, CustomSearchRepository {

	default Long searchCount( MedicalAppointmentPlanningGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT count(1) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<MedicalAppointmentPlanningGrid> search( MedicalAppointmentPlanningGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.grid.MedicalAppointmentPlanningGrid( " );
		hql.append( "     map.id, " );
		hql.append( "     map.name, " );
		hql.append( "     map.initialValidity, " );
		hql.append( "     map.finalValidity " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, MedicalAppointmentPlanningGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, MedicalAppointmentPlanningGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM MedicalAppointmentPlanning map " );
		hql.append( "WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( "   AND map.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getName() != null && !options.getName().isEmpty() && !options.getName().isBlank() ) {
			hql.append( "   AND map.name ilike :name " );
			params.put( "name", String.format( "%s%s%s", "%", options.getName(), "%" ) );
		}
		if( options.getInitialValidityStarts() != null ) {
			hql.append( "   AND CAST(map.initialValidity AS DATE) >= :initialValidityStarts " );
			params.put( "initialValidityStarts", options.getInitialValidityStarts() );
		}
		if( options.getInitialValidityEnds() != null ) {
			hql.append( "   AND CAST(map.initialValidity AS DATE) <= :initialValidityEnds " );
			params.put( "initialValidityEnds", options.getInitialValidityEnds() );
		}
		if( options.getFinalValidityStarts() != null ) {
			hql.append( "   AND CAST(COALESCE(map.finalValidity, :now) AS DATE) >= :finalValidityStarts " );
			params.put( "now", LocalDateTime.now() );
			params.put( "finalValidityStarts", options.getFinalValidityStarts() );
		}
		if( options.getFinalValidityEnds() != null ) {
			hql.append( "   AND CAST(COALESCE(map.finalValidity, :now) AS DATE) <= :finalValidityEnds " );
			params.put( "now", LocalDateTime.now() );
			params.put( "finalValidityEnds", options.getFinalValidityEnds() );
		}
		if( options.getTeamName() != null && !options.getTeamName().isEmpty() && !options.getTeamName().isBlank() ) {
			hql.append( "   AND EXISTS ( " );
			hql.append( "     SELECT 1 " );
			hql.append( "     FROM TeamMedicalAppointmentPlanning _tmap1 " );
			hql.append( "     WHERE _tmap1.medicalAppointmentPlanning.id = map.id " );
			hql.append( "       AND _tmap1.name ILIKE :teamName " );
			hql.append( "   ) " );
			params.put( "teamName", String.format( "%s%s%s", "%", options.getTeamName(), "%" ) );
		}
		if( ( options.getRoom() != null && !options.getRoom().isEmpty() && !options.getRoom().isBlank() ) || options.getPatientId() != null ) {
			hql.append( "   AND EXISTS ( " );
			hql.append( "     SELECT 1 " );
			hql.append( "     FROM MedicalAppointment _ma1 " );
			hql.append( "     WHERE _ma1.medicalAppointmentPlanning.id = map.id " );
			if( options.getRoom() != null && !options.getRoom().isEmpty() && !options.getRoom().isBlank() ) {
				hql.append( "       AND _ma1.room ILIKE :room " );
				params.put( "room", String.format( "%s%s%s", "%", options.getRoom(), "%" ) );
			}
			if( options.getPatientId() != null ) {
				hql.append( "       AND _ma1.patient.id = :patientId " );
				params.put( "patientId", options.getPatientId() );
			}
			hql.append( "   ) " );
		}
	}

	@Modifying
	@Transactional
	default void updateFirstStep( Long id, MedicalAppointmentPlanning entity ) {
		var hql = new StringBuilder();
		hql.append( " UPDATE MedicalAppointmentPlanning SET " );
		hql.append( "     name = :name, " );
		hql.append( "     initialValidity = :initialValidity, " );
		if( entity.getFinalValidity() == null ) {
			hql.append( "     finalValidity = CAST(NULL AS DATE) " );
		} else {
			hql.append( "     finalValidity = :finalValidity " );
		}
		hql.append( " WHERE id = :id " );

		var query = getEntityManager().createQuery( hql.toString() );
		query.setParameter( "name", entity.getName() );
		query.setParameter( "initialValidity", entity.getInitialValidity() );
		if( entity.getFinalValidity() != null ) {
			query.setParameter( "finalValidity", entity.getFinalValidity() );
		}
		query.setParameter( "id", id );
		query.executeUpdate();
	}

	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO( "
			+ "map.id, "
			+ "concat( "
			+ "    '(', map.id, ') - ', "
			+ "    map.name, "
			+ "    ' válido de ', to_char(map.initialValidity, 'dd/mm/yyyy'), "
			+ "        case when map.finalValidity is null then ' em diante' else concat(' até ', to_char(map.finalValidity, 'dd/MM/yyyy')) end) "
			+ ") "
			+ "FROM MedicalAppointmentPlanning map " )
	List<SelectOptionDTO<Long, String>> getSelectOptions();
}
