package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IUserNotificationRepository extends JpaRepository<UserNotification, Long> {

	@Query( value = "SELECT userNotification.* FROM users_notifications userNotification WHERE userNotification.dt_user_notification_deleted_date IS NULL AND userNotification.id_user = :userId AND userNotification.bl_user_notification_for_patient IS TRUE ORDER BY userNotification.dt_user_notification_created_date DESC LIMIT :amountOfNotifications", nativeQuery = true )
	List<UserNotification> findAllByUserIdAndForPatientIsTrue( UUID userId, int amountOfNotifications );

	@Query( value = "SELECT userNotification.* FROM users_notifications userNotification WHERE userNotification.dt_user_notification_deleted_date IS NULL AND userNotification.id_user = :userId AND userNotification.bl_user_notification_for_patient = :isPatient AND CASE :onlyUnread WHEN TRUE THEN userNotification.BL_USER_NOTIFICATION_MARK_AS_READ IS FALSE ELSE TRUE END ORDER BY userNotification.dt_user_notification_created_date DESC LIMIT :amountOfNotifications", nativeQuery = true )
	List<UserNotification> findAllByUserIdAndIsPatientAndIsOnlyUnreadLimitedBy( UUID userId, boolean isPatient, boolean onlyUnread, int amountOfNotifications );

	@Query( value = "SELECT userNotification.* FROM users_notifications userNotification WHERE userNotification.dt_user_notification_deleted_date IS NULL AND userNotification.id_user = :userId ORDER BY userNotification.dt_user_notification_created_date DESC LIMIT :amountOfNotifications", nativeQuery = true )
	List<UserNotification> findAllByUserId( UUID userId, int amountOfNotifications );

	@Query( value = "SELECT userNotification FROM UserNotification userNotification WHERE userNotification.outEntity = :outEntity" )
	List<UserNotification> findAllByOutEntity( String outEntity );
}
