package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.SocialMediaDTO;
import br.com.jovencio.projetofonocore.model.entities.SocialMedia;
import br.com.jovencio.projetofonocore.model.grid.options.SocialMediaGridOptions;
import jakarta.persistence.Persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface ISocialMediaRepository extends JpaRepository<SocialMedia, Long>, CustomSearchRepository {

	default Long searchCount( SocialMediaGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT count(1) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<SocialMediaDTO> search( SocialMediaGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.dtos.SocialMediaDTO( " );
		hql.append( "     socialMedia.id, " );
		hql.append( "     socialMedia.iconClass, " );
		hql.append( "     socialMedia.iconColor, " );
		hql.append( "     socialMedia.title, " );
		hql.append( "     socialMedia.url " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, SocialMediaDTO.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, SocialMediaGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM SocialMedia socialMedia " );
		hql.append( "WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( "   AND socialMedia.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getIconClass() != null ) {
			hql.append( "  AND socialMedia.iconClass like :iconClass " );
			params.put( "iconClass", options.getIconClass() );
		}
		if( options.getTitle() != null ) {
			hql.append( "  AND socialMedia.title ilike :title " );
			params.put( "title", String.format( "%s%s%s", "%", options.getTitle(), "%" ) );
		}
	}
}
