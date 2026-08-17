package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "USERS_FAST_ACTIONS" )
public class UserFastAction {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_USER_FAST_ACTION" )
	private Long id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_USER", nullable = false )
	private User user;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ID_MENU_ITEM", nullable = false )
	private MenuItem menuItem;

	@Column( name = "NR_USER_FAST_ACTION_POSITION", columnDefinition = "INT DEFAULT 0", nullable = false )
	private int position;

}
