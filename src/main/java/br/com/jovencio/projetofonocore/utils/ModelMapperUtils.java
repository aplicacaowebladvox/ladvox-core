package br.com.jovencio.projetofonocore.utils;

import br.com.jovencio.projetofonocore.model.auth.dtos.PermissionDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.RoleDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.UserDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.UserResumedDTO;
import br.com.jovencio.projetofonocore.model.auth.entities.Permission;
import br.com.jovencio.projetofonocore.model.auth.entities.Role;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import br.com.jovencio.projetofonocore.model.dtos.*;
import br.com.jovencio.projetofonocore.model.entities.*;

import java.util.*;

public class ModelMapperUtils {

	// User
	public static UserDTO map( User entity ) {
		if( entity == null )
			return null;
		var dto = new UserDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setLogin( entity.getLogin() );
		dto.setDocument( entity.getDocument() );
		dto.setPassword( entity.getPassword() );
		dto.setShowOnLandingPage( entity.isShowOnLandingPage() );
		dto.setRoles( ModelMapperUtils.mapAllRole( entity.getRoles() ) );
		dto.setInactivationDate( entity.getInactivationDate() );
		dto.setInacinactivationUserId( Optional.ofNullable( entity.getInacinactivationUser() ).orElse( new User() ).getId() );
		return dto;
	}

	public static List<UserDTO> mapAllUser( List<User> entities ) {
		List<UserDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static UserResumedDTO mapResumed( User entity ) {
		if( entity == null )
			return null;
		var dto = new UserResumedDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setDocument( entity.getDocument() );
		dto.setBirthDate( entity.getBirthDate() );
		dto.setShowOnLandingPage( entity.isShowOnLandingPage() );
		dto.setInactivationDate( entity.getInactivationDate() );
		dto.setInacinactivationUserId( Optional.ofNullable( entity.getInacinactivationUser() ).orElse( new User() ).getId() );
		dto.setRoleIds( Optional.ofNullable( entity.getRoles() ).orElse( Collections.emptyList() ).stream().map( Role::getId ).toList() );
		return dto;
	}

	public static User mapResumed( UserResumedDTO dto ) {
		if( dto == null )
			return null;
		var entity = new User();
		entity.setId( dto.getId() );
		entity.setName( dto.getName() );
		entity.setDocument( dto.getDocument() );
		entity.setBirthDate( dto.getBirthDate() );
		entity.setShowOnLandingPage( dto.isShowOnLandingPage() );
		entity.setRoles( Optional.ofNullable( dto.getRoleIds() ).orElse( Collections.emptyList() ).stream().map( Role::new ).toList() );
		return entity;
	}
	// User

	// Role
	public static RoleDTO map( Role entity ) {
		if( entity == null )
			return null;
		var dto = new RoleDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setShowOnLandingPage( entity.isShowOnLandingPage() );
		dto.setPermissions( mapAllPermission( entity.getPermissions() ) );
		return dto;
	}

	public static List<RoleDTO> mapAllRole( List<Role> roles ) {
		List<RoleDTO> list = new ArrayList<>();
		if( roles == null || roles.isEmpty() ) {
			return list;
		}
		for( Role role : roles ) {
			list.add( map( role ) );
		}
		return list;
	}

	public static RoleResumedDTO mapResumed( Role entity ) {
		if( entity == null )
			return null;
		var dto = new RoleResumedDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setShowOnLandingPage( entity.isShowOnLandingPage() );
		dto.setPermissionsIds( Optional.ofNullable( entity.getPermissions() ).orElse( Collections.emptyList() ).stream().map( Permission::getId ).toList() );
		return dto;
	}

	public static Role mapResumed( RoleResumedDTO dto ) {
		if( dto == null )
			return null;
		var entity = new Role();
		entity.setId( dto.getId() );
		entity.setName( dto.getName() );
		entity.setShowOnLandingPage( dto.isShowOnLandingPage() );
		entity.setPermissions( Optional.ofNullable( dto.getPermissionsIds() ).orElse( Collections.emptyList() ).stream().map( Permission::new ).toList() );
		return entity;
	}
	// Role

	// Permission
	public static PermissionDTO map( Permission entity ) {
		if( entity == null )
			return null;
		var dto = new PermissionDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setCode( entity.getCode() );
		return dto;
	}

	public static List<PermissionDTO> mapAllPermission( List<Permission> permissions ) {
		List<PermissionDTO> list = new ArrayList<>();
		if( permissions == null || permissions.isEmpty() ) {
			return list;
		}
		for( var permission : permissions ) {
			list.add( map( permission ) );
		}
		return list;

	}
	// Permission

	// SystemContact
	public static SystemContactDTO map( SystemContact entity ) {
		if( entity == null )
			return null;
		var dto = new SystemContactDTO();
		dto.setId( entity.getId() );
		dto.setAddresses( mapAllAddress( entity.getAddresses() ) );
		dto.setPhones( mapAllPhoneContact( entity.getPhones() ) );
		dto.setEmails( mapAllEmailContact( entity.getEmails() ) );
		return dto;
	}

	public static List<SystemContactDTO> mapAllSystemContact( List<SystemContact> entities ) {
		List<SystemContactDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static SystemContact map( SystemContactDTO dto ) {
		if( dto == null )
			return null;
		var entity = new SystemContact();
		entity.setId( dto.getId() );
		entity.setAddresses( mapAllAddressDTO( dto.getAddresses() ) );
		entity.setPhones( mapAllPhoneContactDTO( dto.getPhones() ) );
		entity.setEmails( mapAllEmailContactDTO( dto.getEmails() ) );
		return entity;
	}
	// SystemContact

	// Address
	public static AddressDTO map( Address entity ) {
		if( entity == null )
			return null;
		var dto = new AddressDTO();
		dto.setId( entity.getId() );
		dto.setCountry( entity.getCountry() );
		dto.setState( entity.getState() );
		dto.setCity( entity.getCity() );
		dto.setNeighborhood( entity.getNeighborhood() );
		dto.setStreet( entity.getStreet() );
		dto.setComplement( entity.getComplement() );
		dto.setNumber( entity.getNumber() );
		dto.setZipCode( entity.getZipCode() );
		return dto;
	}

	public static List<AddressDTO> mapAllAddress( List<Address> entities ) {
		List<AddressDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static Address map( AddressDTO dto ) {
		if( dto == null )
			return null;
		var entity = new Address();
		entity.setId( dto.getId() );
		entity.setCountry( dto.getCountry() );
		entity.setState( dto.getState() );
		entity.setCity( dto.getCity() );
		entity.setNeighborhood( dto.getNeighborhood() );
		entity.setStreet( dto.getStreet() );
		entity.setComplement( dto.getComplement() );
		entity.setNumber( dto.getNumber() );
		entity.setZipCode( dto.getZipCode() );
		return entity;
	}

	public static List<Address> mapAllAddressDTO( List<AddressDTO> dtos ) {
		List<Address> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// Address

	// PoneContact
	public static PhoneContactDTO map( PhoneContact entity ) {
		if( entity == null )
			return null;
		var dto = new PhoneContactDTO();
		dto.setId( entity.getId() );
		dto.setPhoneNumber( entity.getPhoneNumber() );
		return dto;
	}

	public static List<PhoneContactDTO> mapAllPhoneContact( List<PhoneContact> entities ) {
		List<PhoneContactDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static PhoneContact map( PhoneContactDTO dto ) {
		if( dto == null )
			return null;
		var entity = new PhoneContact();
		entity.setId( dto.getId() );
		entity.setPhoneNumber( dto.getPhoneNumber() );
		return entity;
	}

	public static List<PhoneContact> mapAllPhoneContactDTO( List<PhoneContactDTO> dtos ) {
		List<PhoneContact> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// PoneContact

	// PoneContact
	public static EmailContactDTO map( EmailContact entity ) {
		var dto = new EmailContactDTO();
		dto.setId( entity.getId() );
		dto.setEmailAddress( entity.getEmailAddress() );
		return dto;
	}

	public static List<EmailContactDTO> mapAllEmailContact( List<EmailContact> entities ) {
		List<EmailContactDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static EmailContact map( EmailContactDTO dto ) {
		var entity = new EmailContact();
		entity.setId( dto.getId() );
		entity.setEmailAddress( dto.getEmailAddress() );
		return entity;
	}

	public static List<EmailContact> mapAllEmailContactDTO( List<EmailContactDTO> dtos ) {
		List<EmailContact> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// PoneContact

	// SocialMedia
	public static SocialMediaDTO map( SocialMedia entity ) {
		if( entity == null )
			return null;
		var dto = new SocialMediaDTO();
		dto.setId( entity.getId() );
		dto.setIconClass( entity.getIconClass() );
		dto.setIconColor( entity.getIconColor() );
		dto.setTitle( entity.getTitle() );
		dto.setUrl( entity.getUrl() );
		return dto;
	}

	public static List<SocialMediaDTO> mapAllSocialMedia( List<SocialMedia> entities ) {
		List<SocialMediaDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static SocialMedia map( SocialMediaDTO dto ) {
		if( dto == null )
			return null;
		var entity = new SocialMedia();
		entity.setId( dto.getId() );
		entity.setIconClass( dto.getIconClass() );
		entity.setIconColor( dto.getIconColor() );
		entity.setTitle( dto.getTitle() );
		entity.setUrl( dto.getUrl() );
		return entity;
	}

	// SocialMedia

	// Attachment
	public static AttachmentDTO map( Attachment entity ) {
		if( entity == null )
			return null;
		var dto = new AttachmentDTO();
		dto.setId( entity.getId() );
		dto.setCreatedDate( entity.getCreatedDate() );
		dto.setName( entity.getName() );
		dto.setBytesSize( entity.getBytesSize() );
		dto.setType( entity.getType() );
		dto.setLastModifiedDate( entity.getLastModifiedDate() );
		dto.setBase64( entity.getBase64() );
		return dto;
	}

	public static Attachment map( AttachmentDTO dto ) {
		if( dto == null )
			return null;
		var entity = new Attachment();
		entity.setId( dto.getId() );
		entity.setCreatedDate( dto.getCreatedDate() );
		entity.setName( dto.getName() );
		entity.setBytesSize( dto.getBytesSize() );
		entity.setType( dto.getType() );
		entity.setLastModifiedDate( dto.getLastModifiedDate() );
		entity.setBase64( dto.getBase64() );
		return entity;
	}
	// Attachment

	// Article
	public static ArticleDTO map( Article entity ) {
		if( entity == null )
			return null;
		var dto = new ArticleDTO();
		dto.setId( entity.getId() );
		dto.setArticle( entity.getArticle() );
		dto.setTitle( entity.getTitle() );
		return dto;
	}

	public static List<ArticleDTO> mapAllArticle( List<Article> entities ) {
		List<ArticleDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}
	// Article

	// Project
	public static ProjectDTO map( Project entity ) {
		if( entity == null )
			return null;
		var dto = new ProjectDTO();
		dto.setId( entity.getId() );
		dto.setTitle( entity.getTitle() );
		dto.setStatus( entity.getStatus() );
		dto.setType( entity.getType() );
		dto.setDescription( entity.getDescription() );
		return dto;
	}

	public static List<ProjectDTO> mapAllProject( List<Project> entities ) {
		List<ProjectDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static Project map( ProjectDTO dto ) {
		if( dto == null )
			return null;
		var entity = new Project();
		entity.setId( dto.getId() );
		entity.setTitle( dto.getTitle() );
		entity.setStatus( dto.getStatus() );
		entity.setType( dto.getType() );
		entity.setDescription( dto.getDescription() );
		return entity;
	}
	// Project

	// Publication
	public static PublicationDTO map( Publication entity ) {
		if( entity == null )
			return null;
		var dto = new PublicationDTO();
		dto.setId( entity.getId() );
		dto.setTitle( entity.getTitle() );
		dto.setBibliographicReference( entity.getBibliographicReference() );
		dto.setPublicationAbstract( entity.getPublicationAbstract() );
		return dto;
	}

	public static List<PublicationDTO> mapAllPublication( List<Publication> entities ) {
		List<PublicationDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static Publication map( PublicationDTO dto ) {
		if( dto == null )
			return null;
		var entity = new Publication();
		entity.setId( dto.getId() );
		entity.setTitle( dto.getTitle() );
		entity.setBibliographicReference( dto.getBibliographicReference() );
		entity.setPublicationAbstract( dto.getPublicationAbstract() );
		return entity;
	}

	// Publication

	// Protocol
	public static ProtocolFirstStepDTO mapFirstStep( Protocol entity ) {
		if( entity == null )
			return null;
		var dto = new ProtocolFirstStepDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setName( entity.getName() );
		dto.setAbbreviation( entity.getAbbreviation() );
		dto.setDescription( entity.getDescription() );
		dto.setInitialValidity( entity.getInitialValidity() );
		dto.setFinalValidity( entity.getFinalValidity() );
		return dto;
	}

	public static Protocol mapFirstStep( ProtocolFirstStepDTO dto ) {
		if( dto == null )
			return null;
		var entity = new Protocol();
		entity.setId( dto.getId() );
		entity.setKey( dto.getKey() );
		entity.setName( dto.getName() );
		entity.setAbbreviation( dto.getAbbreviation() );
		entity.setDescription( dto.getDescription() );
		entity.setInitialValidity( dto.getInitialValidity() );
		entity.setFinalValidity( dto.getFinalValidity() );
		return entity;
	}

	public static ProtocolSecondStepDTO mapSecondStep( Protocol entity ) {
		if( entity == null )
			return null;
		var dto = new ProtocolSecondStepDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setAnswersType( ModelMapperUtils.mapAllAnswerTypeProtocol( entity.getAnswersType() ) );
		dto.setNextStepIsEnable( ( entity.getAnswersType() != null && !entity.getAnswersType().isEmpty() ) || ( entity.getQuestions() != null && !entity.getQuestions().isEmpty() ) );
		return dto;
	}

	public static ProtocolThirdStepDTO mapThirdStep( Protocol entity ) {
		if( entity == null )
			return null;
		var dto = new ProtocolThirdStepDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setQuestions( ModelMapperUtils.mapAllQuestionProtocol( entity.getQuestions() ) );
		dto.setNextStepIsEnable( entity.getGroups() != null && !entity.getGroups().isEmpty() );
		return dto;
	}

	public static ProtocolFourthStepDTO mapFourthStep( Protocol entity ) {
		if( entity == null )
			return null;
		var dto = new ProtocolFourthStepDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setAnswersType( ModelMapperUtils.mapAllAnswerTypeProtocol( entity.getAnswersType() ) );
		dto.setQuestions( ModelMapperUtils.mapAllQuestionProtocol( entity.getQuestions() ) );
		dto.setGroups( ModelMapperUtils.mapAllQuestionGroupProtocol( entity.getGroups() ) );
		dto.setNextStepIsEnable( entity.getGroups() != null && !entity.getGroups().isEmpty() );
		return dto;
	}

	public static MyProtocolDTO mapMy( Protocol entity ) {
		if( entity == null )
			return null;
		var dto = new MyProtocolDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setAbbreviation( entity.getAbbreviation() );
		dto.setDescription( entity.getDescription() );
		dto.setAnswersType( ModelMapperUtils.mapAllAnswerTypeProtocol( entity.getAnswersType() ) );
		dto.setQuestions( ModelMapperUtils.mapAllQuestionProtocol( entity.getQuestions() ) );
		return dto;
	}
	// Protocol

	// AnswerTypeProtocol
	public static List<AnswerTypeProtocolDTO> mapAllAnswerTypeProtocol( List<AnswerTypeProtocol> entities ) {
		List<AnswerTypeProtocolDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static AnswerTypeProtocolDTO map( AnswerTypeProtocol entity ) {
		if( entity == null )
			return null;
		var dto = new AnswerTypeProtocolDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setSequence( entity.getSequence() );
		dto.setName( entity.getName() );
		dto.setAcceptableValues( ModelMapperUtils.mapAllAcceptedValueAnswerTypeProtocol( entity.getAcceptableValues() ) );
		dto.setUseRangeAnswer( entity.isUseRangeAnswer() );
		dto.setAnswerValueType( entity.getAnswerValueType() );
		return dto;
	}

	public static AnswerTypeProtocol map( AnswerTypeProtocolDTO dto ) {
		if( dto == null )
			return null;
		var entity = new AnswerTypeProtocol();
		entity.setId( dto.getId() );
		entity.setKey( dto.getKey() );
		entity.setSequence( dto.getSequence() );
		entity.setName( dto.getName() );
		entity.setAcceptableValues( ModelMapperUtils.mapAllAcceptedValueAnswerTypeProtocolDTO( dto.getAcceptableValues() ) );
		entity.setUseRangeAnswer( dto.isUseRangeAnswer() );
		entity.setAnswerValueType( dto.getAnswerValueType() );
		return entity;
	}

	public static List<AnswerTypeProtocol> mapAllAnswerTypeProtocolDTO( List<AnswerTypeProtocolDTO> dtos ) {
		List<AnswerTypeProtocol> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// AnswerTypeProtocol

	// AcceptedValueAnswerTypeProtocol
	public static AcceptedValueAnswerTypeProtocolDTO map( AcceptedValueAnswerTypeProtocol entity ) {
		if( entity == null )
			return null;
		var dto = new AcceptedValueAnswerTypeProtocolDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setValue( entity.getValue() );
		dto.setPresentation( entity.getPresentation() );
		dto.setAnswerTypeProtocolId( Optional.ofNullable( entity.getAnswerTypeProtocol() ).orElse( new AnswerTypeProtocol() ).getId() );
		return dto;
	}

	public static List<AcceptedValueAnswerTypeProtocolDTO> mapAllAcceptedValueAnswerTypeProtocol( List<AcceptedValueAnswerTypeProtocol> entities ) {
		List<AcceptedValueAnswerTypeProtocolDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static AcceptedValueAnswerTypeProtocol map( AcceptedValueAnswerTypeProtocolDTO dto ) {
		if( dto == null )
			return null;
		var entity = new AcceptedValueAnswerTypeProtocol();
		entity.setId( dto.getId() );
		entity.setKey( dto.getKey() );
		entity.setValue( dto.getValue() );
		entity.setPresentation( dto.getPresentation() );
		entity.setAnswerTypeProtocol( dto.getAnswerTypeProtocolId() == null ? null : new AnswerTypeProtocol( dto.getAnswerTypeProtocolId() ) );
		return entity;
	}

	public static List<AcceptedValueAnswerTypeProtocol> mapAllAcceptedValueAnswerTypeProtocolDTO( List<AcceptedValueAnswerTypeProtocolDTO> dtos ) {
		List<AcceptedValueAnswerTypeProtocol> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	//AcceptedValueAnswerTypeProtocol

	// QuestionProtocol
	public static QuestionProtocolDTO map( QuestionProtocol entity ) {
		if( entity == null )
			return null;
		var dto = new QuestionProtocolDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setSequence( entity.getSequence() );
		dto.setQuestion( entity.getQuestion() );
		dto.setAbbreviation( entity.getAbbreviation() );
		dto.setProtocolId( Optional.ofNullable( entity.getProtocol() ).orElse( new Protocol() ).getId() );
		return dto;
	}

	public static List<QuestionProtocol> mapAllQuestionProtocolDTO( List<QuestionProtocolDTO> dtos ) {
		List<QuestionProtocol> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}

	public static QuestionProtocol map( QuestionProtocolDTO dto ) {
		if( dto == null )
			return null;
		var entity = new QuestionProtocol();
		entity.setId( dto.getId() );
		entity.setKey( dto.getKey() );
		entity.setSequence( dto.getSequence() );
		entity.setQuestion( dto.getQuestion() );
		entity.setAbbreviation( dto.getAbbreviation() );
		entity.setProtocol( dto.getProtocolId() == null ? null : new Protocol( dto.getProtocolId() ) );
		return entity;
	}

	public static List<QuestionProtocolDTO> mapAllQuestionProtocol( List<QuestionProtocol> entities ) {
		List<QuestionProtocolDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list.stream().sorted( Comparator.comparingInt( QuestionProtocolDTO::getSequence ) ).toList();
	}
	// QuestionProtocol

	// QuestionGroupProtocol
	public static QuestionGroupProtocolDTO map( QuestionGroupProtocol entity ) {
		if( entity == null ) {
			return null;
		}
		var dto = new QuestionGroupProtocolDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setName( entity.getName() );
		dto.setOrderView( entity.getOrderView() );
		dto.setQuestionsIds( Optional.ofNullable( entity.getQuestions() ).orElse( Collections.emptyList() ).stream().map( QuestionProtocol::getId ).toList() );
		dto.setBaseResults( ModelMapperUtils.mapAllBaseResultQuestionGroupProtocol( entity.getBaseResults() ) );
		dto.setFunctions( ModelMapperUtils.mapAllFunctionCalculator( entity.getFunctions() ) );
		dto.setProtocolId( Optional.ofNullable( entity.getProtocol() ).orElse( new Protocol() ).getId() );
		return dto;
	}

	public static List<QuestionGroupProtocolDTO> mapAllQuestionGroupProtocol( List<QuestionGroupProtocol> entities ) {
		List<QuestionGroupProtocolDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static QuestionGroupProtocol map( QuestionGroupProtocolDTO dto ) {
		if( dto == null ) {
			return null;
		}
		var entity = new QuestionGroupProtocol();
		entity.setId( dto.getId() );
		entity.setKey( dto.getKey() );
		entity.setName( dto.getName() );
		entity.setOrderView( dto.getOrderView() );
		entity.setQuestions( Optional.ofNullable( dto.getQuestionsIds() ).orElse( Collections.emptyList() ).stream().filter( Objects::nonNull ).map( QuestionProtocol::new ).toList() );
		entity.setBaseResults( ModelMapperUtils.mapAllBaseResultQuestionGroupProtocolDTO( dto.getBaseResults() ) );
		entity.setFunctions( ModelMapperUtils.mapAllFunctionCalculatorDTO( dto.getFunctions() ) );
		entity.setProtocol( dto.getProtocolId() == null ? null : new Protocol( dto.getProtocolId() ) );
		return entity;
	}

	public static List<QuestionGroupProtocol> mapAllQuestionGroupProtocolDTO( List<QuestionGroupProtocolDTO> dtos ) {
		List<QuestionGroupProtocol> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// QuestionGroupProtocol

	// BaseResultQuestionGroupProtocol
	public static BaseResultQuestionGroupProtocolDTO map( BaseResultQuestionGroupProtocol entity ) {
		if( entity == null )
			return null;
		var dto = new BaseResultQuestionGroupProtocolDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setName( entity.getName() );
		dto.setIsRange( entity.isRange() );
		dto.setInitialValue( entity.getInitialValue() );
		dto.setFinalValue( entity.getFinalValue() );
		dto.setAnswerValueType( entity.getAnswerValueType() );
		dto.setQuestionGroupProtocolId( Optional.ofNullable( entity.getQuestionGroupProtocol() ).orElse( new QuestionGroupProtocol() ).getId() );
		return dto;
	}

	public static List<BaseResultQuestionGroupProtocolDTO> mapAllBaseResultQuestionGroupProtocol( List<BaseResultQuestionGroupProtocol> entities ) {
		List<BaseResultQuestionGroupProtocolDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static BaseResultQuestionGroupProtocol map( BaseResultQuestionGroupProtocolDTO dto ) {
		if( dto == null )
			return null;
		var entity = new BaseResultQuestionGroupProtocol();
		entity.setId( dto.getId() );
		entity.setKey( dto.getKey() );
		entity.setName( dto.getName() );
		entity.setRange( Boolean.TRUE.equals( dto.getIsRange() ) );
		entity.setInitialValue( dto.getInitialValue() );
		entity.setFinalValue( dto.getFinalValue() );
		entity.setAnswerValueType( dto.getAnswerValueType() );
		entity.setQuestionGroupProtocol( dto.getQuestionGroupProtocolId() == null ? null : new QuestionGroupProtocol( dto.getQuestionGroupProtocolId() ) );
		return entity;
	}

	public static List<BaseResultQuestionGroupProtocol> mapAllBaseResultQuestionGroupProtocolDTO( List<BaseResultQuestionGroupProtocolDTO> dtos ) {
		List<BaseResultQuestionGroupProtocol> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// BaseResultQuestionGroupProtocol

	// FunctionCalculator
	public static FunctionCalculatorDTO map( FunctionCalculator entity ) {
		if( entity == null )
			return null;
		var dto = new FunctionCalculatorDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setFunctionText( entity.getFunctionText() );
		dto.setType( entity.getType() );
		return dto;
	}

	public static List<FunctionCalculatorDTO> mapAllFunctionCalculator( List<FunctionCalculator> entities ) {
		List<FunctionCalculatorDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static FunctionCalculator map( FunctionCalculatorDTO dto ) {
		if( dto == null )
			return null;
		var entity = new FunctionCalculator();
		entity.setId( dto.getId() );
		entity.setName( dto.getName() );
		entity.setFunctionText( dto.getFunctionText() );
		entity.setType( dto.getType() );
		return entity;
	}

	public static List<FunctionCalculator> mapAllFunctionCalculatorDTO( List<FunctionCalculatorDTO> dtos ) {
		List<FunctionCalculator> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// BaseResultQuestionGroupProtocol

	// Patient
	public static PatientDTO map( Patient entity ) {
		if( entity == null )
			return null;
		var dto = new PatientDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setDocument( entity.getDocument() );
		dto.setNationalHealthCard( entity.getNationalHealthCard() );
		dto.setGenderId( Optional.ofNullable( entity.getGender() ).orElse( new Gender() ).getId() );
		dto.setOtherGenderDefinition( entity.getOtherGenderDefinition() );
		dto.setEducationId( Optional.ofNullable( entity.getEducation() ).orElse( new Education() ).getId() );
		dto.setOtherEducationDefinition( entity.getOtherEducationDefinition() );
		dto.setProfession( entity.getProfession() );
		dto.setMedicalRecord( entity.getMedicalRecord() );
		dto.setBirthDate( entity.getBirthDate() );
		dto.setMainPhone( entity.getMainPhone() );
		dto.setPhoneSecundary( entity.getPhoneSecundary() );
		dto.setPhoneObservation( entity.getPhoneObservation() );
		dto.setMainMail( entity.getMainMail() );
		dto.setMailSecundary( entity.getMailSecundary() );
		dto.setMailObservation( entity.getMailObservation() );
		dto.setAddress( ModelMapperUtils.map( entity.getAddress() ) );
		dto.setAnamnesisIds( Optional.ofNullable( entity.getAnamnesis() ).orElse( Collections.emptyList() ).stream().map( AnamnesisPatient::getId ).toList() );
		dto.setUserId( Optional.ofNullable( entity.getUser() ).orElse( new User() ).getId() );
		return dto;
	}

	public static Patient map( PatientDTO dto ) {
		if( dto == null )
			return null;
		var entity = new Patient();
		entity.setId( dto.getId() );
		entity.setName( dto.getName() );
		entity.setDocument( dto.getDocument() );
		entity.setNationalHealthCard( dto.getNationalHealthCard() );
		entity.setGender( dto.getGenderId() == null ? null : new Gender( dto.getGenderId() ) );
		entity.setOtherGenderDefinition( dto.getOtherGenderDefinition() );
		entity.setEducation( dto.getEducationId() == null ? null : new Education( dto.getEducationId() ) );
		entity.setOtherEducationDefinition( dto.getOtherEducationDefinition() );
		entity.setProfession( dto.getProfession() );
		entity.setMedicalRecord( dto.getMedicalRecord() );
		entity.setBirthDate( dto.getBirthDate() );
		entity.setMainPhone( dto.getMainPhone() );
		entity.setPhoneSecundary( dto.getPhoneSecundary() );
		entity.setPhoneObservation( dto.getPhoneObservation() );
		entity.setMainMail( dto.getMainMail() );
		entity.setMailSecundary( dto.getMailSecundary() );
		entity.setMailObservation( dto.getMailObservation() );
		entity.setAddress( ModelMapperUtils.map( dto.getAddress() ) );
		entity.setAnamnesis( Optional.ofNullable( dto.getAnamnesisIds() ).orElse( Collections.emptyList() ).stream().map( AnamnesisPatient::new ).toList() );
		entity.setUser( dto.getUserId() != null ? new User( dto.getUserId() ) : null );
		return entity;
	}
	// Patient

	// AnamnesisPatient
	public static AnamnesisPatientDTO map( AnamnesisPatient entity ) {
		if( entity == null )
			return null;
		var dto = new AnamnesisPatientDTO();
		dto.setId( entity.getId() );
		dto.setPatientId( Optional.ofNullable( entity.getPatient() ).orElse( new Patient() ).getId() );
		dto.setTherapistId( Optional.ofNullable( entity.getTherapist() ).orElse( new User() ).getId() );
		dto.setCreatedDate( entity.getCreatedDate() );
		dto.setAnamnesiText( entity.getAnamnesiText() );
		return dto;
	}

	public static AnamnesisPatient map( AnamnesisPatientDTO dto ) {
		if( dto == null )
			return null;
		var entity = new AnamnesisPatient();
		entity.setId( dto.getId() );
		entity.setPatient( dto.getPatientId() == null ? null : new Patient( dto.getPatientId() ) );
		entity.setTherapist( dto.getTherapistId() == null ? null : new User( dto.getTherapistId() ) );
		entity.setCreatedDate( dto.getCreatedDate() );
		entity.setAnamnesiText( dto.getAnamnesiText() );
		return entity;
	}
	// AnamnesisPatient

	// AnamnesisPatientAttachment
	public static AnamnesisPatientAttachmentDTO map( AnamnesisPatientAttachment entity ) {
		if( entity == null )
			return null;
		var dto = new AnamnesisPatientAttachmentDTO();
		dto.setId( entity.getId() );
		dto.setAnamnesisPatientId( Optional.ofNullable( entity.getAnamnesisPatient() ).orElse( new AnamnesisPatient() ).getId() );
		dto.setAttachment( ModelMapperUtils.map( entity.getAttachment() ) );
		return dto;
	}

	public static AnamnesisPatientAttachment map( AnamnesisPatientAttachmentDTO dto ) {
		if( dto == null )
			return null;
		var entity = new AnamnesisPatientAttachment();
		entity.setId( dto.getId() );
		entity.setAnamnesisPatient( dto.getAnamnesisPatientId() == null ? null : new AnamnesisPatient( dto.getAnamnesisPatientId() ) );
		entity.setAttachment( ModelMapperUtils.map( dto.getAttachment() ) );
		return entity;
	}

	public static List<AnamnesisPatientAttachmentDTO> mapAllAnamnesisPatientAttachment( List<AnamnesisPatientAttachment> entities ) {
		List<AnamnesisPatientAttachmentDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}
	// AnamnesisPatientAttachment

	// ProtocolTherapeuticPlan
	public static ProtocolTherapeuticPlanDTO map( ProtocolTherapeuticPlan entity ) {
		if( entity == null )
			return null;
		var dto = new ProtocolTherapeuticPlanDTO();
		dto.setId( entity.getId() );
		dto.setProtocolId( Optional.ofNullable( entity.getProtocol() ).orElse( new Protocol() ).getId() );
		dto.setRequestDate( entity.getRequestDate() );
		dto.setAnsweredDate( entity.getAnsweredDate() );
		dto.setAnswers( ModelMapperUtils.mapAllAnswerQuestionProtocol( entity.getAnswers() ) );
		dto.setTherapeuticPlanId( Optional.ofNullable( entity.getTherapeuticPlan() ).orElse( new TherapeuticPlan() ).getId() );
		return dto;
	}

	public static ProtocolTherapeuticPlan map( ProtocolTherapeuticPlanDTO dto ) {
		if( dto == null )
			return null;
		var entity = new ProtocolTherapeuticPlan();
		entity.setId( dto.getId() );
		entity.setProtocol( dto.getProtocolId() == null ? null : new Protocol( dto.getProtocolId() ) );
		entity.setRequestDate( dto.getRequestDate() );
		entity.setAnsweredDate( dto.getAnsweredDate() );
		entity.setAnswers( ModelMapperUtils.mapAllAnswerQuestionProtocolDTO( dto.getAnswers() ) );
		entity.setTherapeuticPlan( dto.getTherapeuticPlanId() == null ? null : new TherapeuticPlan( dto.getTherapeuticPlanId() ) );
		return entity;
	}
	// ProtocolTherapeuticPlan

	// AnswerQuestionProtocol
	public static AnswerQuestionProtocolDTO map( AnswerQuestionProtocol entity ) {
		if( entity == null )
			return null;
		var dto = new AnswerQuestionProtocolDTO();
		dto.setId( entity.getId() );
		dto.setKey( entity.getKey() );
		dto.setAnswerTypeId( Optional.ofNullable( entity.getAnswerType() ).orElse( new AnswerTypeProtocol() ).getId() );
		dto.setAnswer( entity.getAnswer() );
		dto.setAnswerValueType( entity.getAnswerValueType() );
		dto.setQuestionId( Optional.ofNullable( entity.getQuestion() ).orElse( new QuestionProtocol() ).getId() );
		dto.setProtocolTherapeuticPlanId( Optional.ofNullable( entity.getProtocolTherapeuticPlan() ).orElse( new ProtocolTherapeuticPlan() ).getId() );
		return dto;
	}

	public static AnswerQuestionProtocol map( AnswerQuestionProtocolDTO dto ) {
		if( dto == null )
			return null;
		var entity = new AnswerQuestionProtocol();
		entity.setId( dto.getId() );
		entity.setKey( dto.getKey() );
		entity.setAnswerType( dto.getAnswerTypeId() == null ? null : new AnswerTypeProtocol( dto.getAnswerTypeId() ) );
		entity.setAnswer( dto.getAnswer() );
		entity.setAnswerValueType( dto.getAnswerValueType() );
		entity.setQuestion( dto.getQuestionId() == null ? null : new QuestionProtocol( dto.getQuestionId() ) );
		entity.setProtocolTherapeuticPlan( dto.getProtocolTherapeuticPlanId() == null ? null : new ProtocolTherapeuticPlan( dto.getProtocolTherapeuticPlanId() ) );
		return entity;
	}

	public static List<AnswerQuestionProtocolDTO> mapAllAnswerQuestionProtocol( List<AnswerQuestionProtocol> entities ) {
		List<AnswerQuestionProtocolDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static List<AnswerQuestionProtocol> mapAllAnswerQuestionProtocolDTO( List<AnswerQuestionProtocolDTO> dtos ) {
		List<AnswerQuestionProtocol> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// AnswerQuestionProtocol

	// MedicalAppointmentPlanning
	public static MedicalAppointmentPlanningFirstStepDTO mapFirstStep( MedicalAppointmentPlanning entity ) {
		if( entity == null )
			return null;
		var dto = new MedicalAppointmentPlanningFirstStepDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setInitialValidity( entity.getInitialValidity() );
		dto.setFinalValidity( entity.getFinalValidity() );
		dto.setNextStepIsEnable( entity.getId() != null && entity.getName() != null && !entity.getName().isEmpty() );
		return dto;
	}

	public static MedicalAppointmentPlanning mapFirstStep( MedicalAppointmentPlanningFirstStepDTO dto ) {
		if( dto == null )
			return null;
		var entity = new MedicalAppointmentPlanning();
		entity.setId( dto.getId() );
		entity.setName( dto.getName() );
		entity.setInitialValidity( dto.getInitialValidity() );
		entity.setFinalValidity( dto.getFinalValidity() );
		return entity;
	}

	public static MedicalAppointmentPlanningSecondStepDTO mapSecondStep( MedicalAppointmentPlanning entity ) {
		if( entity == null )
			return null;
		var dto = new MedicalAppointmentPlanningSecondStepDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setTeams( ModelMapperUtils.mapAllTeamMedicalAppointmentPlanning( entity.getTeams() ) );
		dto.setNextStepIsEnable( entity.getTeams() != null && !entity.getTeams().isEmpty() );
		return dto;
	}

	public static MedicalAppointmentPlanningThirdStepDTO mapThirdStep( MedicalAppointmentPlanning entity ) {
		if( entity == null )
			return null;
		var dto = new MedicalAppointmentPlanningThirdStepDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setMedicalAppointments( ModelMapperUtils.mapAllMedicalAppointment( entity.getMedicalAppointments() ) );
		dto.setNextStepIsEnable( entity.getMedicalAppointments() != null && !entity.getMedicalAppointments().isEmpty() );
		return dto;
	}
	// MedicalAppointmentPlanning

	// TeamMedicalAppointmentPlanning
	public static TeamMedicalAppointmentPlanningDTO map( TeamMedicalAppointmentPlanning entity ) {
		if( entity == null )
			return null;
		var dto = new TeamMedicalAppointmentPlanningDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setColor( entity.getColor() );
		dto.setMedicalAppointmentPlanningId( Optional.ofNullable( entity.getMedicalAppointmentPlanning() ).orElse( new MedicalAppointmentPlanning() ).getId() );
		dto.setTherapistsIds( Optional.ofNullable( entity.getTherapists() ).orElse( Collections.emptyList() ).stream().map( User::getId ).toList() );
		return dto;
	}

	public static TeamMedicalAppointmentPlanning map( TeamMedicalAppointmentPlanningDTO dto ) {
		if( dto == null )
			return null;
		var entity = new TeamMedicalAppointmentPlanning();
		entity.setId( dto.getId() );
		entity.setName( dto.getName() );
		entity.setColor( dto.getColor() );
		entity.setMedicalAppointmentPlanning( dto.getMedicalAppointmentPlanningId() == null ? null : new MedicalAppointmentPlanning( dto.getMedicalAppointmentPlanningId() ) );
		entity.setTherapists( Optional.ofNullable( dto.getTherapistsIds() ).orElse( Collections.emptyList() ).stream().map( User::new ).toList() );
		return entity;
	}

	public static List<TeamMedicalAppointmentPlanningDTO> mapAllTeamMedicalAppointmentPlanning( List<TeamMedicalAppointmentPlanning> entities ) {
		List<TeamMedicalAppointmentPlanningDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static List<TeamMedicalAppointmentPlanning> mapAllTeamMedicalAppointmentPlanningDTO( List<TeamMedicalAppointmentPlanningDTO> dtos ) {
		List<TeamMedicalAppointmentPlanning> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// TeamMedicalAppointmentPlanning

	// MedicalAppointment
	public static MedicalAppointmentDTO map( MedicalAppointment entity ) {
		if( entity == null )
			return null;
		var dto = new MedicalAppointmentDTO();
		dto.setId( entity.getId() );
		dto.setTeamId( Optional.ofNullable( entity.getTeam() ).orElse( new TeamMedicalAppointmentPlanning() ).getId() );
		dto.setRoom( entity.getRoom() );
		dto.setPatientId( Optional.ofNullable( entity.getPatient() ).orElse( new Patient() ).getId() );
		dto.setWeekDay( entity.getWeekDay() );
		dto.setHour( entity.getHour() );
		dto.setObservation( entity.getObservation() );
		dto.setMedicalAppointmentPlanningId( Optional.ofNullable( entity.getMedicalAppointmentPlanning() ).orElse( new MedicalAppointmentPlanning() ).getId() );
		return dto;
	}

	public static MedicalAppointment map( MedicalAppointmentDTO dto ) {
		if( dto == null )
			return null;
		var entity = new MedicalAppointment();
		entity.setId( dto.getId() );
		entity.setTeam( dto.getTeamId() == null ? null : new TeamMedicalAppointmentPlanning( dto.getTeamId() ) );
		entity.setRoom( dto.getRoom() );
		entity.setPatient( dto.getPatientId() == null ? null : new Patient( dto.getPatientId() ) );
		entity.setWeekDay( dto.getWeekDay() );
		entity.setHour( dto.getHour() );
		entity.setObservation( dto.getObservation() );
		entity.setMedicalAppointmentPlanning( dto.getMedicalAppointmentPlanningId() == null ? null : new MedicalAppointmentPlanning( dto.getMedicalAppointmentPlanningId() ) );
		return entity;
	}

	public static List<MedicalAppointmentDTO> mapAllMedicalAppointment( List<MedicalAppointment> entities ) {
		List<MedicalAppointmentDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}

	public static List<MedicalAppointment> mapAllMedicalAppointmentDTO( List<MedicalAppointmentDTO> dtos ) {
		List<MedicalAppointment> list = new ArrayList<>();
		if( dtos == null || dtos.isEmpty() ) {
			return list;
		}
		for( var dto : dtos ) {
			list.add( map( dto ) );
		}
		return list;
	}
	// MedicalAppointment

	// TherapeuticPlan
	public static TherapeuticPlanDTO map( TherapeuticPlan entity ) {
		if( entity == null )
			return null;
		var dto = new TherapeuticPlanDTO();
		dto.setId( entity.getId() );
		dto.setTherapeuticTextPlan( entity.getTherapeuticTextPlan() );
		dto.setProtocolsIds( Optional.ofNullable( entity.getProtocols() ).orElse( Collections.emptyList() ).stream().map( ProtocolTherapeuticPlan::getId ).toList() );
		dto.setMedicalAppointmentId( Optional.ofNullable( entity.getMedicalAppointment() ).orElse( new MedicalAppointment() ).getId() );
		return dto;
	}

	public static TherapeuticPlan map( TherapeuticPlanDTO dto ) {
		if( dto == null )
			return null;
		var entity = new TherapeuticPlan();
		entity.setId( dto.getId() );
		entity.setTherapeuticTextPlan( dto.getTherapeuticTextPlan() );
		entity.setProtocols( Optional.ofNullable( dto.getProtocolsIds() ).orElse( Collections.emptyList() ).stream().map( ProtocolTherapeuticPlan::new ).toList() );
		entity.setMedicalAppointment( dto.getMedicalAppointmentId() == null ? null : new MedicalAppointment( dto.getMedicalAppointmentId() ) );
		return entity;
	}
	// TherapeuticPlan

	// TherapeuticPlanAttachmentDTO
	public static TherapeuticPlanAttachmentDTO map( TherapeuticPlanAttachment entity ) {
		if( entity == null )
			return null;
		var dto = new TherapeuticPlanAttachmentDTO();
		dto.setId( entity.getId() );
		dto.setTherapeuticPlanId( Optional.ofNullable( entity.getTherapeuticPlan() ).orElse( new TherapeuticPlan() ).getId() );
		dto.setAttachment( ModelMapperUtils.map( entity.getAttachment() ) );
		return dto;
	}

	public static TherapeuticPlanAttachment map( TherapeuticPlanAttachmentDTO dto ) {
		if( dto == null )
			return null;
		var entity = new TherapeuticPlanAttachment();
		entity.setId( dto.getId() );
		entity.setTherapeuticPlan( dto.getTherapeuticPlanId() == null ? null : new TherapeuticPlan( dto.getTherapeuticPlanId() ) );
		entity.setAttachment( ModelMapperUtils.map( dto.getAttachment() ) );
		return entity;
	}

	public static List<TherapeuticPlanAttachmentDTO> mapAllTherapeuticPlanAttachment( List<TherapeuticPlanAttachment> entities ) {
		List<TherapeuticPlanAttachmentDTO> list = new ArrayList<>();
		if( entities == null || entities.isEmpty() ) {
			return list;
		}
		for( var entity : entities ) {
			list.add( map( entity ) );
		}
		return list;
	}
	// TherapeuticPlanAttachmentDTO

	// UserMyDataDTO
	public static UserMyDataDTO mapUserMyData( Patient entity ) {
		if( entity == null )
			return null;
		var dto = new UserMyDataDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setDocument( entity.getDocument() );
		dto.setNationalHealthCard( entity.getNationalHealthCard() );
		dto.setGenderId( Optional.ofNullable( entity.getGender() ).orElse( new Gender() ).getId() );
		dto.setOtherGenderDefinition( entity.getOtherGenderDefinition() );
		dto.setEducationId( Optional.ofNullable( entity.getEducation() ).orElse( new Education() ).getId() );
		dto.setOtherEducationDefinition( entity.getOtherEducationDefinition() );
		dto.setProfession( entity.getProfession() );
		dto.setMedicalRecord( entity.getMedicalRecord() );
		dto.setBirthDate( entity.getBirthDate() );
		dto.setMainPhone( entity.getMainPhone() );
		dto.setPhoneSecundary( entity.getPhoneSecundary() );
		dto.setPhoneObservation( entity.getPhoneObservation() );
		dto.setMainMail( entity.getMainMail() );
		dto.setMailSecundary( entity.getMailSecundary() );
		dto.setMailObservation( entity.getMailObservation() );
		dto.setAddress( ModelMapperUtils.map( entity.getAddress() ) );
		var user = Optional.ofNullable( entity.getUser() ).orElse( new User() );
		dto.setUserId( user.getId() );
		dto.setProfileImage( ModelMapperUtils.map( user.getProfileImage() ) );
		return dto;

	}

	public static UserMyDataDTO mapUserMyData( User entity ) {
		if( entity == null )
			return null;
		var dto = new UserMyDataDTO();
		dto.setUserId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setDocument( entity.getDocument() );
		dto.setBirthDate( entity.getBirthDate() );
		dto.setProfileImage( ModelMapperUtils.map( entity.getProfileImage() ) );
		return dto;
	}
	// UserMyDataDTO

	// UserNotification
	public static UserNotificationDTO map( UserNotification entity ) {
		if( entity == null )
			return null;
		var dto = new UserNotificationDTO();
		dto.setId( entity.getId() );
		dto.setUserId( entity.getUser().getId() );
		dto.setNotification( entity.getNotification() );
		dto.setCreatedDate( entity.getCreatedDate() );
		dto.setMarkAsRead( entity.isMarkAsRead() );
		dto.setForPatient( entity.isForPatient() );
		return dto;
	}
	// UserNotification

	// RequestAppointment
	public static RequestAppointmentDTO map( RequestAppointment entity ) {
		if( entity == null ) {
			return null;
		}
		var dto = new RequestAppointmentDTO();
		dto.setId( entity.getId() );
		dto.setName( entity.getName() );
		dto.setEmail( entity.getEmail() );
		dto.setPhone1( entity.getPhone1() );
		dto.setPhone2( entity.getPhone2() );
		dto.setProblemDescription( entity.getProblemDescription() );
		dto.setCreatedDate( entity.getCreatedDate() );
		dto.setConclusionDate( entity.getConclusionDate() );
		dto.setUserOfConclusionId( entity.getUserOfConclusion() == null ? null : entity.getUserOfConclusion().getId() );
		dto.setUserOfConclusionName( entity.getUserOfConclusion() == null ? null : entity.getUserOfConclusion().getName() );
		return dto;
	}

	public static RequestAppointment map( RequestAppointmentDTO dto ) {
		if( dto == null ) {
			return null;
		}
		var entity = new RequestAppointment();
		entity.setId( dto.getId() );
		entity.setName( dto.getName() );
		entity.setEmail( dto.getEmail() );
		entity.setPhone1( dto.getPhone1() );
		entity.setPhone2( dto.getPhone2() );
		entity.setProblemDescription( dto.getProblemDescription() );
		entity.setCreatedDate( dto.getCreatedDate() );
		entity.setConclusionDate( dto.getConclusionDate() );
		entity.setUserOfConclusion( dto.getUserOfConclusionId() == null ? null : new User( dto.getUserOfConclusionId() ) );
		return entity;
	}
	// RequestAppointment
}

