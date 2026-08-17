package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.grid.UserGrid;
import br.com.jovencio.projetofonocore.model.grid.options.UserGridOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface IUserRepository extends JpaRepository<User, UUID>, CustomSearchRepository {

	default Long searchCount( UserGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT COUNT(DISTINCT u.id) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<UserGrid> search( UserGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT DISTINCT new br.com.jovencio.projetofonocore.model.grid.UserGrid( " );
		hql.append( "     u " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		if( options.getOrderByFilters() != null && !options.getOrderByFilters().isEmpty() ) {
			for( var orderByFilter : options.getOrderByFilters() ) {
				if( orderByFilter.getField().equalsIgnoreCase( "name" ) ) {
					orderByFilter.setField( "u.name" );
				}
			}
		}
		var query = mountSearch( hql, options, UserGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, UserGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM User u " );
		hql.append( "JOIN u.roles r " );
		hql.append( "WHERE 1=1 " );

		if( options.getId() != null ) {
			hql.append( "   AND u.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getName() != null ) {
			hql.append( "  AND u.name ilike :name " );
			params.put( "name", String.format( "%s%s%s", "%", options.getName(), "%" ) );
		}
		if( options.getDocument() != null ) {
			hql.append( "  AND u.document ilike :document " );
			params.put( "document", String.format( "%s%s%s", "%", options.getDocument(), "%" ) );
		}
		if( options.getRoleIds() != null && !options.getRoleIds().isEmpty() ) {
			hql.append( "   AND r.id IN (:roleIds) " );
			params.put( "roleIds", options.getRoleIds() );
		}
	}

	@Query( "SELECT u FROM User u WHERE u.login = :login OR u.document = :login" )
	Optional<User> findByLogin( String login );

	@Query( value =
			"SELECT user FROM User user LEFT JOIN user.roles role WHERE user.showOnLandingPage IS TRUE AND role.showOnLandingPage IS TRUE"
	)
	List<User> findAllUserForLandingPage();

	Optional<User> findByDocument( String document );

	default List<SelectOptionDTO<UUID, String>> queryAllForSelectOptions( boolean therapists ) {
		var hql = new StringBuilder();
		hql.append( " SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO( " );
		hql.append( "     u.id, " );
		hql.append( "     u.name " );
		hql.append( " ) " );
		hql.append( " FROM User u " );
		hql.append( " WHERE :therapists IS FALSE " );
		hql.append( "    OR (:therapists IS TRUE " );
		hql.append( "     AND EXISTS ( " );
		hql.append( "         SELECT 1 " );
		hql.append( "         FROM Permission p " );
		hql.append( "         JOIN p.roles r " );
		hql.append( "         JOIN r.users _u " );
		hql.append( "         WHERE _u.id = u.id " );
		hql.append( "           AND p.code = 'exibir-como-terapeuta-em-select-options' " );
		hql.append( "     ) " );
		hql.append( "    ) " );

		var query = getEntityManager().createQuery( hql.toString(), SelectOptionDTO.getClassOfUuidAndString() );
		query.setParameter( "therapists", therapists );
		return query.getResultList();
	}
}
