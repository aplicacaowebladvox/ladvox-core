package br.com.jovencio.projetofonocore.listeners.application;

import br.com.jovencio.projetofonocore.dao.repositories.IPermissionRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IRoleRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IUserRepository;
import br.com.jovencio.projetofonocore.model.auth.entities.Permission;
import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthDataInitializr implements ApplicationListener<ContextRefreshedEvent> {

	private final IUserRepository userRepository;
	private final IRoleRepository roleRepository;
	private final IPermissionRepository permissionRepository;
	private final PasswordEncoder passwordEncoder;

	AuthDataInitializr(IUserRepository userRepository, IRoleRepository roleRepository, IPermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.permissionRepository = permissionRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public void onApplicationEvent( ContextRefreshedEvent arg0 ) {
//		var permissions = permissionRepository.findAll();
//		if( permissions.isEmpty() ) {
//			var admin = new Permission();
//			admin.setName( "Administrador" );
//			admin.setCode( "admin" );
//			permissionRepository.save( admin );
//		}
//		var roles = roleRepository.findAll();
//		if( roles.isEmpty() ) {
//			var admin = new Role();
//			admin.setName( "Administrador" );
//			admin.setShowOnLandingPage( false );
//			admin.setPermissions( permissionRepository.findByName( "Administrador" ) );
//			roleRepository.save( admin );
//		}
//		var users = userRepository.findAll();
//		if( users.isEmpty() ) {
//			var admin = new User();
//			admin.setName( "Administrador" );
//			admin.setLogin( "admin" );
//			admin.setPassword( passwordEncoder.encode( "admin" ) );
//			admin.setDocument( "72425340700" );
//			admin.setRoles( roleRepository.findByName( "Administrador" ) );
//			userRepository.save( admin );
//		}
	}
}
