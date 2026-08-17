package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.entities.Patient;
import br.com.jovencio.projetofonocore.model.grid.PatientGrid;
import br.com.jovencio.projetofonocore.model.grid.options.PatientGridOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface IPatientRepository extends JpaRepository<Patient, Long>, CustomSearchRepository {

	default Long searchCount( PatientGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT count(1) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();

	}

	default List<PatientGrid> search( PatientGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.grid.PatientGrid( " );
		hql.append( "     p.id, " );
		hql.append( "     p.name, " );
		hql.append( "     p.document, " );
		hql.append( "     p.birthDate, " );
		hql.append( "     TO_CHAR(AGE(current_date, p.birthDate), 'YY\"a\" MM\"m\" DD\"d\"') as age " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, PatientGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, PatientGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM Patient p " );
		hql.append( "WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( "   AND p.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getName() != null ) {
			hql.append( "  AND p.name ilike :name " );
			params.put( "name", String.format( "%s%s%s", "%", options.getName(), "%" ) );
		}
		if( options.getDocument() != null ) {
			hql.append( "  AND p.document ilike :document " );
			params.put( "document", String.format( "%s%s%s", "%", options.getDocument(), "%" ) );
		}
		if( options.getAgeStarts() != null ) {
			hql.append( "  AND CAST(TO_CHAR(AGE(current_date, p.birthDate), 'YY') AS INTEGER) >= :ageStarts " );
			params.put( "ageStarts", options.getAgeStarts() );
		}
		if( options.getAgeEnds() != null ) {
			hql.append( "  AND CAST(TO_CHAR(AGE(current_date, p.birthDate), 'YY') AS INTEGER) <= :ageEnds " );
			params.put( "ageEnds", options.getAgeEnds() );
		}
	}

	Optional<Patient> findByDocument( String document );

	Optional<Patient> findByUserId( UUID userId );

	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO( "
			+ "p.id, "
			+ "p.name "
			+ ") "
			+ "FROM Patient p "
			+ "WHERE (:id IS NULL OR (:id IS NOT NULL AND  p.id = :id))" )
	List<SelectOptionDTO<Long, String>> getSelectOptions( Long id );
}
