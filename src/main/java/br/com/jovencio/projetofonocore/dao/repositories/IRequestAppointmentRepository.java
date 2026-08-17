package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.RequestAppointment;
import br.com.jovencio.projetofonocore.model.grid.MedicalAppointmentPlanningGrid;
import br.com.jovencio.projetofonocore.model.grid.RequestAppointmentGrid;
import br.com.jovencio.projetofonocore.model.grid.options.RequestAppointmentGridOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface IRequestAppointmentRepository extends JpaRepository<RequestAppointment, Long>, CustomSearchRepository {

	default Long searchCount( RequestAppointmentGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT count(1) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<RequestAppointmentGrid> search( RequestAppointmentGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.grid.RequestAppointmentGrid( " );
		hql.append( "     requestAppointment.id AS id, " );
		hql.append( "     requestAppointment.name AS name, " );
		hql.append( "     requestAppointment.email AS email, " );
		hql.append( "     requestAppointment.phone1 AS phone1, " );
		hql.append( "     requestAppointment.phone2 AS phone2, " );
		hql.append( "     requestAppointment.createdDate AS createdDate, " );
		hql.append( "     requestAppointment.conclusionDate AS conclusionDate, " );
		hql.append( "     userOfConclusion.id AS userOfConclusionId, " );
		hql.append( "     userOfConclusion.name AS userOfConclusionName " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, RequestAppointmentGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, RequestAppointmentGridOptions options, Map<String, Object> params ) {
		hql.append( " FROM RequestAppointment requestAppointment " );
		hql.append( " LEFT JOIN requestAppointment.userOfConclusion userOfConclusion  " );
		hql.append( " WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( " AND requestAppointment.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getName() != null && !options.getName().isBlank() && !options.getName().isEmpty() ) {
			hql.append( "   AND requestAppointment.name ilike :name " );
			params.put( "name", String.format( "%s%s%s", "%", options.getName(), "%" ) );
		}
		if( options.getStatus() != null ) {
			switch ( options.getStatus() ) {
				case "OPENED":
					hql.append( "   AND requestAppointment.conclusionDate IS NULL " );
					break;
				case "CONCLUDED":
					hql.append( "   AND requestAppointment.conclusionDate IS NOT NULL " );
					break;
			}
		}

	}
}
