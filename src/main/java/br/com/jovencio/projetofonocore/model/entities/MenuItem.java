package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.Permission;
import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import br.com.jovencio.projetofonocore.model.dtos.MenuItemDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "MENU_ITEMS" )
public class MenuItem {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_MENU_ITEM" )
	private Long id;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_MENUITEMS_ROLES",
			joinColumns = @JoinColumn( name = "ID_MENU_ITEM" ),
			inverseJoinColumns = @JoinColumn( name = "ID_ROLE" ) )
	private List<Role> roles;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "M2M_MENUITEMS_PERMISSIONS",
			joinColumns = @JoinColumn( name = "ID_MENU_ITEM" ),
			inverseJoinColumns = @JoinColumn( name = "ID_PERMISSION" ) )
	private List<Permission> permissions;

	@Column( name = "TX_MENU_ITEM_ICON_CLASS", columnDefinition = "VARCHAR(50)", nullable = false )
	private String iconClass;

	@Column( name = "NM_MENU_ITEM", columnDefinition = "VARCHAR(50)", nullable = false )
	private String name;

	@Column( name = "DS_MENU_ITEM", columnDefinition = "VARCHAR(155)" )
	private String description;

	@Column( name = "TX_MENU_ITEM_PATH", columnDefinition = "VARCHAR(155)" )
	private String path;

	@Column( name = "BL_MENU_ITEM_OPEN_IN_NEW_TAB", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false )
	private boolean openInNewTab;

	@OneToMany( mappedBy = "parent", fetch = FetchType.EAGER )
	private List<MenuItem> subMenus;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ID_MENU_ITEM_PARENT" )
	private MenuItem parent;

	public MenuItem( long id ) {
		this.id = id;
	}

	public String getPermission() {
		if( this.permissions != null && this.permissions.size() == 1 ) {
			return this.permissions.getFirst().getCode();
		}
		return null;
	}

	public MenuItemDTO cloneForFrontEnd() {
		return MenuItemDTO.builder()
				.id( id )
				.iconClass( iconClass )
				.name( name )
				.description( description )
				.path( path )
				.subMenus( subMenus != null ? new ArrayList<>() : null )
				.build();
	}

	public MenuItemDTO cloneForFastAction() {
		if( List.of( "fa-solid fa-list-ul", "fa-solid fa-circle-plus" ).contains( iconClass ) && parent != null ) {
			iconClass = parent.getIconClass();
			name = parent.getName() + " > " + name;
		}
		return MenuItemDTO.builder()
				.id( id )
				.iconClass( iconClass )
				.name( name )
				.description( description )
				.path( path )
				.subMenus( subMenus != null ? new ArrayList<>() : null )
				.build();
	}

	public String getRole() {
		if( this.roles != null && this.roles.size() == 1 ) {
			return this.roles.getFirst().getName();
		}
		return null;
	}
}
