package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "USERS_PERFORMED_ACTIONS" )
public class UserPerformedAction {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_USER_PERFORMED_ACTION" )
	private Long id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_USER", nullable = false )
	private User user;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_MENU_ITEM", nullable = false )
	private MenuItem menuItem;

	@Column( name = "NR_USER_PERFORMED_ACTION_TIMES", columnDefinition = "INT DEFAULT 0", nullable = false )
	private int times;

	public UserPerformedAction( UUID userId, long menuItemId ) {
		this.user = new User( userId );
		this.menuItem = new MenuItem( menuItemId );
		this.times = 0;
	}

	public void addOneTime() {
		this.times = this.times + 1;
	}
}
