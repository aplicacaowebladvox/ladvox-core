package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.entities.Protocol;
import br.com.jovencio.projetofonocore.model.grid.ProtocolGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TermGridOptions;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IProtocolRepository extends JpaRepository<Protocol, Long>, CustomSearchRepository {

	default Long searchCount( TermGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT count(1) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<ProtocolGrid> search( TermGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.grid.ProtocolGrid( " );
		hql.append( "     p.id, " );
		hql.append( "     p.name, " );
		hql.append( "     p.abbreviation, " );
		hql.append( "     p.initialValidity, " );
		hql.append( "     p.finalValidity " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, ProtocolGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, TermGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM Protocol p " );
		hql.append( "WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( "   AND p.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getTerm() != null && !options.getTerm().isEmpty() ) {
			hql.append( "  AND (p.name ilike :term " );
			hql.append( "    OR p.abbreviation ilike :term) " );
			params.put( "term", options.getTerm() );
		}
	}

	@Modifying
	@Transactional
	default void updateFirstStep( Long id, Protocol protocol ) {
		var hql = new StringBuilder();
		hql.append( " UPDATE Protocol SET " );
		hql.append( "     name = :name, " );
		hql.append( "     abbreviation = :abbreviation, " );
		hql.append( "     description = :description, " );
		hql.append( "     initialValidity = :initialValidity, " );
		if( protocol.getFinalValidity() == null ) {
			hql.append( "     finalValidity = CAST(NULL AS DATE) " );
		} else {
			hql.append( "     finalValidity = :finalValidity " );
		}
		hql.append( " WHERE id = :id " );

		var query = getEntityManager().createQuery( hql.toString() );
		query.setParameter( "name", protocol.getName() );
		query.setParameter( "abbreviation", protocol.getAbbreviation() );
		query.setParameter( "description", protocol.getDescription() );
		query.setParameter( "initialValidity", protocol.getInitialValidity() );
		if( protocol.getFinalValidity() != null ) {
			query.setParameter( "finalValidity", protocol.getFinalValidity() );
		}
		query.setParameter( "id", id );
		query.executeUpdate();
	}

	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO( "
			+ "p.id, "
			+ "concat( "
			+ "    case when p.abbreviation is not null then concat('(', p.abbreviation, ') - ') else '' end, "
			+ "    p.name, "
			+ "    ' válido de ', to_char(p.initialValidity, 'dd/mm/yyyy'), "
			+ "        case when p.finalValidity is null then ' em diante' else concat(' até ', to_char(p.finalValidity, 'dd/MM/yyyy')) end) "
			+ ") "
			+ "FROM Protocol p " )
	List<SelectOptionDTO<Long, String>> getSelectOptions();
}
