package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.BaseFilterGridOptions;
import br.com.jovencio.projetofonocore.model.dtos.BaseGridOptions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.Comparator;
import java.util.Objects;

public class CustomSearchRepositoryImpl implements CustomSearchRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public Query mountSearch( StringBuilder hql, BaseGridOptions options, Class<?> returnedClass ) {
		if( options.getOrderByFilters() != null && !options.getOrderByFilters().isEmpty() && !options.getOrderByFilters().stream().filter( o -> Objects.nonNull( o.getTypeOrder() ) ).toList().isEmpty() ) {
			hql.append( " ORDER BY " ).append( String.join( ", ", options.getOrderByFilters().stream().filter( o -> Objects.nonNull( o.getTypeOrder() ) ).sorted( Comparator.comparingInt( BaseFilterGridOptions::getOrderPriority ) ).map( o -> o.getField() + " " + o.getTypeOrderSql() ).toList() ) );
		}
		var query = em.createQuery( hql.toString(), returnedClass );
		query.setMaxResults( options.getPageSize() );
		query.setFirstResult( options.getOffset() );
		return query;
	}

	@Override
	public Query mountSearchCount( StringBuilder hql, Class<?> returnedClass ) {
		return em.createQuery( hql.toString(), returnedClass );

	}

	@Override
	public Query mountNativeSearch( StringBuilder sql, BaseGridOptions options, Class<?> returnedClass ) {
		if( options.getOrderByFilters() != null && !options.getOrderByFilters().isEmpty() && !options.getOrderByFilters().stream().filter( o -> Objects.nonNull( o.getTypeOrder() ) ).toList().isEmpty() ) {
			sql.append( " ORDER BY " ).append( String.join( ", ", options.getOrderByFilters().stream().filter( o -> Objects.nonNull( o.getTypeOrder() ) ).sorted( Comparator.comparingInt( BaseFilterGridOptions::getOrderPriority ) ).map( o -> o.getField() + " " + o.getTypeOrderSql() ).toList() ) );
		}
		var query = em.createNativeQuery( sql.toString(), returnedClass );
		query.setMaxResults( options.getPageSize() );
		query.setFirstResult( options.getOffset() );
		return query;
	}

	@Override
	public Query mountNativeSearchCount( StringBuilder hql, Class<?> returnedClass ) {
		return em.createNativeQuery( hql.toString(), returnedClass );

	}
}
