package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.Publication;
import br.com.jovencio.projetofonocore.model.grid.PublicationGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TermGridOptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPublicationRepository extends JpaRepository<Publication, Long>, CustomSearchRepository {

	default Long searchCount( TermGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT count(1) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<PublicationGrid> search( TermGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.grid.PublicationGrid( " );
		hql.append( "     p.id, " );
		hql.append( "     p.title, " );
		hql.append( "     p.bibliographicReference " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, PublicationGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, TermGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM Publication p " );
		hql.append( "WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( "   AND p.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getTerm() != null ) {
			hql.append( "  AND (p.title ilike :term " );
			hql.append( "    OR p.bibliographicReference ilike :term " );
			hql.append( "    OR p.publicationAbstract ilike :term) " );
			params.put( "term", options.getTerm() );
		}
	}
}
