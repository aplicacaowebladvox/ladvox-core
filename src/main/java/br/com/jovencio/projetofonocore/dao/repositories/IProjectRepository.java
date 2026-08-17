package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.Project;
import br.com.jovencio.projetofonocore.model.grid.ProjectGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TermGridOptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IProjectRepository extends JpaRepository<Project, Long>, CustomSearchRepository {

	default Long searchCount( TermGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT count(1) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<ProjectGrid> search( TermGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.grid.ProjectGrid( " );
		hql.append( "     p.id, " );
		hql.append( "     p.title, " );
		hql.append( "     p.status " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, ProjectGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, TermGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM Project p " );
		hql.append( "WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( "   AND p.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getTerm() != null ) {
			hql.append( "  AND (p.title ilike :term " );
			hql.append( "    OR p.status ilike :term " );
			hql.append( "    OR p.description ilike :term) " );
			params.put( "term", String.format( "%s%s%s", "%", options.getTerm(), "%" ) );
		}
	}
}
