package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.MenuItem;
import br.com.jovencio.projetofonocore.model.entities.UserPerformedAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IUserPerformedActionRepository extends JpaRepository<UserPerformedAction, Long> {

	@Query(
			value = "SELECT upa "
					+ "FROM UserPerformedAction upa "
					+ "WHERE upa.user.id = :userId"
					+ "  AND upa.menuItem.id = :menuItemId "
	)
	UserPerformedAction findByUserIdAndMenuItemId( UUID userId, Long menuItemId );

	@Query(value = "select upa FROM UserPerformedAction upa WHERE upa.user.id = :userId ORDER BY upa.times DESC ")
	List<UserPerformedAction> findAllByUserIdOrderByTimesDesc( UUID userId );
}
