package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IMenuItemRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IUserFastActionRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IUserPerformedActionRepository;
import br.com.jovencio.projetofonocore.model.auth.dtos.ChooseableUserRoleDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.UserDTO;
import br.com.jovencio.projetofonocore.model.dtos.FastActionDTO;
import br.com.jovencio.projetofonocore.model.dtos.MenuItemDTO;
import br.com.jovencio.projetofonocore.model.dtos.MenuResponseDTO;
import br.com.jovencio.projetofonocore.model.dtos.NextSessionDTO;
import br.com.jovencio.projetofonocore.model.entities.MenuItem;
import br.com.jovencio.projetofonocore.model.entities.UserFastAction;
import br.com.jovencio.projetofonocore.model.entities.UserPerformedAction;
import br.com.jovencio.projetofonocore.services.auth.JwtService;
import br.com.jovencio.projetofonocore.services.auth.component.AuthComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SystemConfigService {

	private final AuthComponent authComponent;
	private final IMenuItemRepository menuItemRepository;
	private final IUserPerformedActionRepository userPerformedActionRepository;
	private final IUserFastActionRepository userFastActionRepository;
	private final MedicalAppointmentService medicalAppointmentService;
	private final JwtService jwtService;

	public MenuResponseDTO getMenu( ChooseableUserRoleDTO choice ) {
		var user = authComponent.getUserDTO();
		user.setRoles( user.getRoles().stream().filter( r -> choice.getCode().equals( r.getName() ) || choice.getRoleName().equals( r.getName() ) || Optional.ofNullable( choice.getOtherRoles() ).orElse( Collections.emptyList() ).contains( r.getName() ) ).toList() );
		List<MenuItemDTO> items = new ArrayList<>();
		for( var item : menuItemRepository.findAllParentIsNull() ) {
			items.add( findAll( item, user ) );
		}
		return new MenuResponseDTO( items.stream().filter( i -> Objects.nonNull( i ) && ( ( i.getPath() != null && !i.getPath().isEmpty() ) || ( i.getSubMenus() != null && !i.getSubMenus().isEmpty() ) ) ).toList() );
	}

	private MenuItemDTO findAll( MenuItem item, UserDTO user ) {
		MenuItemDTO itemInclude = null;
		if( item.getPermission() != null ) {
			if( user.getAuthorities().stream().anyMatch( p -> p.getCode().equalsIgnoreCase( item.getPermission() ) ) ) {
				itemInclude = item.cloneForFrontEnd();
				for( var subItem : Optional.ofNullable( item.getSubMenus() ).orElse( Collections.emptyList() ) ) {
					itemInclude.getSubMenus().add( findAll( subItem, user ) );
				}
			}
		} else if( item.getPermissions() != null && !item.getPermissions().isEmpty() ) {
			if( user.getAuthorities().stream().anyMatch( p -> item.getPermissions().stream().anyMatch( a -> p.getCode().equalsIgnoreCase( a.getCode() ) ) ) ) {
				itemInclude = item.cloneForFrontEnd();
				for( var subItem : Optional.ofNullable( item.getSubMenus() ).orElse( Collections.emptyList() ) ) {
					itemInclude.getSubMenus().add( findAll( subItem, user ) );
				}
			}
		} else if( item.getRole() != null ) {
			if( user.getRoles().stream().anyMatch( r -> r.getName().equalsIgnoreCase( item.getRole() ) ) ) {
				itemInclude = item.cloneForFrontEnd();
				for( var subItem : Optional.ofNullable( item.getSubMenus() ).orElse( Collections.emptyList() ) ) {
					itemInclude.getSubMenus().add( findAll( subItem, user ) );
				}

			}
		} else if( item.getRoles() != null && !item.getRoles().isEmpty() ) {
			if( user.getRoles().stream().anyMatch( userRole -> item.getRoles().stream().anyMatch( itemRole -> userRole.getName().equals( itemRole.getName() ) ) ) ) {
				itemInclude = item.cloneForFrontEnd();
				for( var subItem : Optional.ofNullable( item.getSubMenus() ).orElse( Collections.emptyList() ) ) {
					itemInclude.getSubMenus().add( findAll( subItem, user ) );
				}
			}
		} else {
			itemInclude = item.cloneForFrontEnd();
			for( var subItem : Optional.ofNullable( item.getSubMenus() ).orElse( Collections.emptyList() ) ) {
				itemInclude.getSubMenus().add( findAll( subItem, user ) );
			}
		}
		if( itemInclude != null && itemInclude.getSubMenus() != null ) {
			itemInclude.setSubMenus( itemInclude.getSubMenus().stream().filter( Objects::nonNull ).toList() );
		}
		if( ( item.getPath() == null || item.getPath().isEmpty() || item.getPath().isBlank() ) && ( item.getSubMenus() == null || item.getSubMenus().isEmpty() ) ) {
			return null;
		}
		return itemInclude;
	}

	public void registerNewPerformedAction( long menuItemId ) {
		var userPerformedAction = userPerformedActionRepository.findByUserIdAndMenuItemId( authComponent.getUserDTO().getId(), menuItemId );
		if( userPerformedAction != null ) {
			userPerformedAction.addOneTime();
		} else {
			userPerformedAction = new UserPerformedAction( authComponent.getUserDTO().getId(), menuItemId );
		}
		userPerformedActionRepository.save( userPerformedAction );
	}

	public FastActionDTO getFastActions( String token ) {
		var permissions = jwtService.extractPermissions( token );
		var roles = jwtService.extractRoles( token );
		List<UserFastAction> fastActions = Optional.ofNullable( userFastActionRepository.findAllByUserId( authComponent.getUserDTO().getId() ) ).orElse( Collections.emptyList() ).stream().filter( fa -> canShow( permissions, roles, fa.getMenuItem() ) ).toList();
		var favoriteMenuItems = fastActions.stream().map( fa -> fa.getMenuItem().getId() ).toList();
		List<UserPerformedAction> moreUsedActions = userPerformedActionRepository.findAllByUserIdOrderByTimesDesc( authComponent.getUserDTO().getId() );
		return FastActionDTO.builder().fixedActions( fastActions.stream().map( UserFastAction::getMenuItem ).map( MenuItem::cloneForFastAction ).toList() )
				.suggestedActions( moreUsedActions.stream().filter( mua -> !favoriteMenuItems.contains( mua.getMenuItem().getId() ) && canShow( permissions, roles, mua.getMenuItem() ) ).limit( 10 ).map( UserPerformedAction::getMenuItem ).map( MenuItem::cloneForFastAction ).toList() ).build();
	}

	public NextSessionDTO getNextSession() {
		return medicalAppointmentService.getNextSessionByUserId( authComponent.getUserDTO().getId() );
	}

	private boolean canShow( List<String> userPermissions, List<String> userRoles, MenuItem menuItem ) {
		return menuItem.getPermission() != null && userPermissions.contains( menuItem.getPermission() ) || ( menuItem.getPermissions() != null && !menuItem.getPermissions().isEmpty() && menuItem.getPermissions().stream()
				.anyMatch( permission -> userPermissions.contains( permission.getCode() ) ) ) || ( menuItem.getRole() != null
				&& userRoles.contains( menuItem.getRole() ) ) || menuItem.getRoles() != null && !menuItem.getRoles().isEmpty() && menuItem.getRoles().stream().anyMatch( role -> userRoles.contains( role.getName() ) );
	}

}
