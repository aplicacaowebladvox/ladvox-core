package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.grid.ProjectGrid;
import br.com.jovencio.projetofonocore.model.grid.RoleGrid;
import br.com.jovencio.projetofonocore.model.grid.options.RoleGridOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<Role, UUID>, CustomSearchRepository {

	List<Role> findByName( String name );

	default Long searchCount( RoleGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT COUNT(DISTINCT r.id) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearchCount( hql, Long.class );
		params.forEach( query::setParameter );
		return ( Long ) query.getSingleResult();
	}

	default List<RoleGrid> search( RoleGridOptions options ) {
		var hql = new StringBuilder();
		Map<String, Object> params = new HashMap<>();

		hql.append( " SELECT DISTINCT new br.com.jovencio.projetofonocore.model.grid.RoleGrid( " );
		hql.append( "     r " );
		hql.append( " ) " );
		mountSearchFromClause( hql, options, params );

		var query = mountSearch( hql, options, RoleGrid.class );
		params.forEach( query::setParameter );
		return query.getResultList();
	}

	private void mountSearchFromClause( StringBuilder hql, RoleGridOptions options, Map<String, Object> params ) {
		hql.append( "FROM Role r " );
		hql.append( "WHERE r.name <> 'Paciente' " );

		if( options.getId() != null ) {
			hql.append( "   AND r.id = :id " );
			params.put( "id", options.getId() );
		}
		if( options.getName() != null ) {
			hql.append( "  AND r.name ilike :name " );
			params.put( "name", String.format( "%s%s%s", "%", options.getName(), "%" ) );
		}
	}

	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO("
			+ "r.id, "
			+ "r.name "
			+ ") "
			+ "FROM Role r "
			+ "WHERE r.name <> 'Paciente' " )
	List<SelectOptionDTO<UUID, String>> queryAllForSelectOptions();

}
