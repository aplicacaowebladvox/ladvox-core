package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IPatientRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IUserRepository;
import br.com.jovencio.projetofonocore.model.auth.dtos.ChooseableUserRoleDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.UserDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.UserResumedDTO;
import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import br.com.jovencio.projetofonocore.model.dtos.*;
import br.com.jovencio.projetofonocore.model.entities.Patient;
import br.com.jovencio.projetofonocore.model.grid.UserGrid;
import br.com.jovencio.projetofonocore.model.grid.options.UserGridOptions;
import br.com.jovencio.projetofonocore.services.auth.component.AuthComponent;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import br.com.jovencio.projetofonocore.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

	private final IUserRepository userRepository;
	private final RoleService roleService;
	private final IPatientRepository patientRepository;
	private final AuthComponent authComponent;
	private final AttachmentService attachmentService;

	public List<ChooseableUserRoleDTO> mountChooseableRole() {
		var user = userRepository.findById( authComponent.getUserDTO().getId() ).orElseThrow();
		ChooseableUserRoleDTO patientChoice = null;
		ChooseableUserRoleDTO generalChoice = null;
		var isPatient = user.getRoles().stream().anyMatch( Role::isPatient );
		var isOtherUser = user.getRoles().stream().anyMatch( role -> !role.isPatient() );
		if( isPatient ) {
			patientChoice = ChooseableUserRoleDTO.builder().code( "patient" ).roleName( "Paciente" ).notifications( new ArrayList<>() ).build();
		}
		if( isOtherUser ) {
			final var innerGeneralChoice = ChooseableUserRoleDTO.builder().code( "general" ).otherRoles( new ArrayList<>() ).notifications( new ArrayList<>() ).build();
			user.getRoles().stream().filter( role -> !role.isPatient() ).forEach( role -> {
				if( innerGeneralChoice.getRoleName() == null && role.isShowOnLandingPage() ) {
					innerGeneralChoice.setRoleName( role.getName() );
				} else {
					innerGeneralChoice.getOtherRoles().add( role.getName() );
				}
			} );
			if( innerGeneralChoice.getRoleName() == null ) {
				innerGeneralChoice.setRoleName( innerGeneralChoice.getOtherRoles().getFirst() );
				innerGeneralChoice.getOtherRoles().removeFirst();
			}
			generalChoice = innerGeneralChoice;
		}
		if( isPatient && !isOtherUser ) {
			return List.of( patientChoice );
		} else if( !isPatient && isOtherUser ) {
			return List.of( generalChoice );
		}
		return Stream.of( patientChoice, generalChoice ).filter( Objects::nonNull ).toList();
	}

	public UserDTO get( UUID id ) {
		return userRepository.findById( id ).map( ModelMapperUtils::map ).orElseThrow();
	}

	public List<UserDTO> get() {
		var users = userRepository.findAll();
		return ModelMapperUtils.mapAllUser( users );
	}

	public SearchReturnDTO<UserGrid> search( UserGridOptions options ) {
		var count = userRepository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( userRepository.search( options ), count );
	}

	public UserResumedDTO getBy( UUID id ) {
		return userRepository.findById( id ).map( ModelMapperUtils::mapResumed ).orElseThrow();
	}

	public UserResumedDTO save( UserResumedDTO dto ) {
		var entity = ModelMapperUtils.mapResumed( dto );
		validate( entity );
		return ModelMapperUtils.mapResumed( userRepository.save( entity ) );
	}

	public UserResumedDTO save( UUID id, UserResumedDTO dto ) {
		var entity = ModelMapperUtils.mapResumed( dto );
		validate( id, entity );
		patientRepository.findByUserId( id ).ifPresent( patient -> {
			refreshDataOfPatient( patient, dto );
			entity.setRoles( roleService.concatRoles( entity.getRoles().stream().map( Role::getId ).toList(), roleService.findAllRolesForPatient() ) );
		} );
		return ModelMapperUtils.mapResumed( userRepository.save( entity ) );
	}

	public void delete( UUID id ) {
		var user = userRepository.findById( id ).orElseThrow();
		user.setInactivationDate( LocalDateTime.now() );
		user.setInacinactivationUser( userRepository.findById( authComponent.getUserDTO().getId() ).orElseThrow() );
		userRepository.save( user );
	}

	private void validate( User entity ) {
		validate( null, entity );
	}

	public User generateByPatient( PatientDTO patientDTO ) {
		var user = userRepository.findByDocument( patientDTO.getDocument() );
		if( user.isEmpty() ) {
			return userRepository.save( User.builder().name( patientDTO.getName() ).document( patientDTO.getDocument() ).birthDate( patientDTO.getBirthDate() ).roles( roleService.findAllRolesForPatient() ).build() );
		}

		return refreshDataOfUser( user.get(), patientDTO );
	}

	public User refreshDataOfUser( User user, PatientDTO patientDTO ) {
		user = userRepository.findById( user.getId() ).orElseThrow();
		if( !user.getName().equals( patientDTO.getName() ) ) {
			user.setName( patientDTO.getName() );
		}
		if( !user.getDocument().equals( patientDTO.getDocument() ) ) {
			user.setDocument( patientDTO.getDocument() );
		}
		if( !user.getBirthDate().equals( patientDTO.getBirthDate() ) ) {
			user.setBirthDate( patientDTO.getBirthDate() );
		}
		user.setRoles( roleService.concatRoles( user.getRoles().stream().map( Role::getId ).toList(), roleService.findAllRolesForPatient() ) );
		return userRepository.save( user );
	}

	private void validate( UUID id, User entity ) {
		var isUpdate = id != null;
		if( entity == null ) {
			throw new IllegalArgumentException( "Usuário não informado" );
		}
		List<String> errors = new ArrayList<>();

		if( entity.getName() == null || entity.getName().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 255 ) {
			errors.add( "Nome muito grande(máximo de 255 caracteres)" );
		}

		if( entity.getDocument() == null || entity.getDocument().isEmpty() ) {
			errors.add( "Documento não informado" );
		} else if( !ValidationUtils.cpf( entity.getDocument() ) ) {
			errors.add( "Documento inválido: " + entity.getDocument() );
		}

		if( entity.getBirthDate() == null ) {
			errors.add( "Data de nascimento não informada" );
		} else if( entity.getBirthDate().isAfter( LocalDate.now() ) ) {
			errors.add( "Data de nascimento inválida: " + entity.getBirthDate() );
		}

		if( entity.getRoles() == null || entity.getRoles().isEmpty() ) {
			errors.add( "Papéis não informados" );
		}
		if( isUpdate ) {
			var entityFromDatabase = userRepository.findById( id ).orElseThrow();
			if( entityFromDatabase.getInactivationDate() != null ) {
				throw new IllegalStateException( "Usuário desativado" );
			}
			if( !entityFromDatabase.getDocument().equals( entity.getDocument() ) ) {
				errors.add( "Documento não pode ser alterado: " + entity.getDocument() );
			}
			entity.setPassword( entityFromDatabase.getPassword() );
			entity.setLogin( entityFromDatabase.getLogin() );
			entity.setProfileImage( entityFromDatabase.getProfileImage() );
		}

		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}

	private void refreshDataOfPatient( Patient patient, UserResumedDTO userDTO ) {
		patient = patientRepository.findById( patient.getId() ).orElseThrow();
		if( !patient.getName().equals( userDTO.getName() ) ) {
			patient.setName( userDTO.getName() );
		}
		if( !patient.getDocument().equals( userDTO.getDocument() ) ) {
			patient.setDocument( userDTO.getDocument() );
		}
		if( !patient.getBirthDate().equals( userDTO.getBirthDate() ) ) {
			patient.setBirthDate( userDTO.getBirthDate() );
		}
		patientRepository.save( patient );
	}

	public List<SelectOptionDTO<UUID, String>> getSelectOptions( boolean therapists ) {
		return userRepository.queryAllForSelectOptions( therapists );
	}

	public UserMyDataDTO getDataOfLoggedUser() {
		var patient = patientRepository.findByUserId( authComponent.getUserDTO().getId() );
		if( patient.isPresent() ) {
			return ModelMapperUtils.mapUserMyData( patient.get() );
		}
		return ModelMapperUtils.mapUserMyData( userRepository.findById( authComponent.getUserDTO().getId() ).orElseThrow() );
	}

	public UserMyDataDTO updateBaseData( UUID id, UserResumedDTO dto ) {
		var user = userRepository.findById( id ).orElseThrow();
		user.setName( dto.getName() );
		user.setDocument( dto.getDocument() );
		user.setBirthDate( dto.getBirthDate() );
		validate( user );
		userRepository.save( user );
		var patient = patientRepository.findByUserId( authComponent.getUserDTO().getId() );
		if( patient.isPresent() ) {
			refreshDataOfPatient( patient.get(), dto );
			return ModelMapperUtils.mapUserMyData( patient.get() );
		}
		return ModelMapperUtils.mapUserMyData( userRepository.findById( authComponent.getUserDTO().getId() ).orElseThrow() );
	}

	public AttachmentDTO saveProfileImage( UUID id, AttachmentDTO dto ) {
		var user = userRepository.findById( id ).orElseThrow();
		if( user.getProfileImage() != null ) {
			dto.setId( user.getProfileImage().getId() );
			dto.setLastModifiedDate( Optional.ofNullable( dto.getLastModifiedDate() ).orElse( LocalDateTime.now() ) );
			attachmentService.save( dto );
		} else {
			var profileImage = attachmentService.save( dto );
			dto.setId( profileImage.getId() );
			user.setProfileImage( profileImage );
			userRepository.save( user );
		}
		return dto;
	}

	public AttachmentDTO getProfileImage() {
		var user = userRepository.findById( authComponent.getUserDTO().getId() ).orElseThrow();
		return ModelMapperUtils.map( user.getProfileImage() );
	}
}
