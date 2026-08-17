package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "USERS_NOTIFICATIONS" )
@SQLDelete( sql = "update users_notifications SET dt_user_notification_deleted_date = NOW() WHERE id_user_notification = ?" )
public class UserNotification {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_USER_NOTIFICATION" )
	private Long id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_USER", nullable = false )
	private User user;

	@Column( name = "TX_USER_NOTIFICATION", columnDefinition = "VARCHAR(1000)", nullable = false )
	private String notification;

	@Column( name = "CD_USER_NOTIFICATION_OUT_ENTITY", columnDefinition = "VARCHAR(1000)", nullable = false )
	private String outEntity;

	@Column( name = "DT_USER_NOTIFICATION_CREATED_DATE", columnDefinition = "TIMESTAMP DEFAULT NOW()", nullable = false )
	private LocalDateTime createdDate;

	@Column( name = "DT_USER_NOTIFICATION_DELETED_DATE", columnDefinition = "TIMESTAMP" )
	private LocalDateTime deletedeDate;

	@Column( name = "BL_USER_NOTIFICATION_MARK_AS_READ", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false )
	private boolean markAsRead;

	@Column( name = "BL_USER_NOTIFICATION_FOR_PATIENT", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false )
	private boolean forPatient;

	public void setOutEntityProtocolTherapeuticPlan( long protocolTherapeuticPlanId ) {
		this.outEntity = "ProtocolTherapeuticPlan=" + protocolTherapeuticPlanId;
	}
}
