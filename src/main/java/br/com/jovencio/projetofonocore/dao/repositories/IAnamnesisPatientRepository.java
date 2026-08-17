package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.AnamnesisPatient;
import br.com.jovencio.projetofonocore.model.grid.AnamnesisPatientGrid;
import br.com.jovencio.projetofonocore.model.grid.PatientGrid;
import br.com.jovencio.projetofonocore.model.grid.options.AnamnesisPatientGridOptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IAnamnesisPatientRepository extends JpaRepository<AnamnesisPatient, Long>, CustomSearchRepository {

	default Long searchCount( AnamnesisPatientGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT COUNT(DISTINCT ap.id) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<AnamnesisPatientGrid> search( AnamnesisPatientGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.grid.AnamnesisPatientGrid( " );
		hql.append( "     ap.id, " );
		hql.append( "     p.id, " );
		hql.append( "     p.name, " );
		hql.append( "     t.id, " );
		hql.append( "     t.name, " );
		hql.append( "     ap.createdDate " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, AnamnesisPatientGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, AnamnesisPatientGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM AnamnesisPatient ap " );
		hql.append( "JOIN ap.patient p " );
		hql.append( "JOIN ap.therapist t " );
		hql.append( "WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( "   AND ap.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getPatientId() != null ) {
			hql.append( "  AND p.id = :patientId " );
			params.put( "patientId", options.getPatientId() );
		}
		if( options.getTherapistId() != null ) {
			hql.append( "  AND t.id = :therapistId " );
			params.put( "therapistId", options.getTherapistId() );
		}
		if( options.getCreatedDateStarts() != null ) {
			hql.append( "  AND CAST(ap.createdDate AS DATE) >= :createdDateStarts " );
			params.put( "createdDateStarts", options.getCreatedDateStarts() );
		}
		if( options.getCreatedDateEnds() != null ) {
			hql.append( "  AND CAST(ap.createdDate AS DATE) <= :createdDateEnds " );
			params.put( "createdDateEnds", options.getCreatedDateEnds() );
		}
	}

}
