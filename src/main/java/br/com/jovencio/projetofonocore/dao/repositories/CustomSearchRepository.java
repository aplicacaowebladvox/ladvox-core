package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.BaseGridOptions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public interface CustomSearchRepository {

	Query mountSearch( StringBuilder hql, BaseGridOptions options, Class<?> returnedClass );

	Query mountSearchCount( StringBuilder hql, Class<?> returnedClass );

	Query mountNativeSearch( StringBuilder sql, BaseGridOptions options, Class<?> returnedClass );

	Query mountNativeSearchCount( StringBuilder sql, Class<?> returnedClass );

	EntityManager getEntityManager();
}
