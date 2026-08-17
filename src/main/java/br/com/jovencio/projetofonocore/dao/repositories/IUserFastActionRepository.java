package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.UserFastAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IUserFastActionRepository extends JpaRepository<UserFastAction, Long> {

	@Query( value = "SELECT ufa from UserFastAction ufa where ufa.user.id = :userId " )
	List<UserFastAction> findAllByUserId( UUID userId );

}
