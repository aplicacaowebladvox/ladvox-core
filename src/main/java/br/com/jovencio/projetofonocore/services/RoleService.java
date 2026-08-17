package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IRoleRepository;
import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import br.com.jovencio.projetofonocore.model.dtos.RoleResumedDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.grid.RoleGrid;
import br.com.jovencio.projetofonocore.model.grid.options.RoleGridOptions;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final IRoleRepository repository;

	public SearchReturnDTO<RoleGrid> search( RoleGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );

	}

	public RoleResumedDTO get( UUID id ) {
		return repository.findById( id ).map( ModelMapperUtils::mapResumed ).orElseThrow();
	}

	public RoleResumedDTO save( RoleResumedDTO dto ) {
		var entity = ModelMapperUtils.mapResumed( dto );
		validate( entity );
		return ModelMapperUtils.mapResumed( repository.save( entity ) );
	}

	public RoleResumedDTO save( UUID id, RoleResumedDTO dto ) {
		var entity = ModelMapperUtils.mapResumed( dto );
		validate( id, entity );
		return ModelMapperUtils.mapResumed( repository.save( entity ) );
	}

	public void delete( UUID id ) {
		repository.findById( id ).orElseThrow();
		repository.deleteById( id );
	}

	public List<SelectOptionDTO<UUID, String>> getSelectOptions() {
		return repository.queryAllForSelectOptions();
	}

	private void validate( Role entity ) {
		validate( null, entity );
	}

	private void validate( UUID id, Role entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Papel não informado" );
		}
		List<String> errors = new ArrayList<>();
		if( entity.getName() == null || entity.getName().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 120 ) {
			errors.add( "Nome muito longo(máximo 120 caracteres)" );
		}

		if( entity.getPermissions() == null || entity.getPermissions().isEmpty() ) {
			errors.add( "Nenhuma permissão informada" );
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}

	public List<Role> findAllRolesForPatient() {
		return repository.findByName( "Paciente" );
	}

	public List<Role> concatRoles( List<UUID> currentUserRoles, List<Role> allRolesForPatient ) {
		var currentRoles = repository.findAllById( currentUserRoles );
		currentRoles.addAll( allRolesForPatient.stream().filter( r -> !currentRoles.contains( r.getId() ) ).toList() );
		return currentRoles;
	}
}
