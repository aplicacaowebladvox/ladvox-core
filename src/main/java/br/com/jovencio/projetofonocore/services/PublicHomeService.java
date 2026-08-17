package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.*;
import br.com.jovencio.projetofonocore.model.dtos.*;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PublicHomeService {

	private final ISystemContactRepository systemContactRepository;
	private final ISocialMediaRepository socialMediaRepository;
	private final IArticleRepository articleRepository;
	private final IUserRepository userRepository;
	private final IProjectRepository projectRepository;
	private final IPublicationRepository publicationRepository;
	private final RequestAppointmentService requestAppointmentService;

	public List<SystemContactDTO> getContactsActives() {
		return ModelMapperUtils.mapAllSystemContact( systemContactRepository.findAll() );
	}

	public List<SocialMediaDTO> getSocialMediasActives() {
		return ModelMapperUtils.mapAllSocialMedia( socialMediaRepository.findAll() );
	}

	public List<ArticleDTO> getArticlesActives() {
		return ModelMapperUtils.mapAllArticle( articleRepository.findAll() );
	}

	public List<UserMemberLandingPageDTO> getUserMembersLandingPage() {
		return userRepository.findAllUserForLandingPage().stream().map( UserMemberLandingPageDTO::new ).toList();
	}

	public List<ProjectDTO> getProjectsActives() {
		return ModelMapperUtils.mapAllProject( projectRepository.findAll() );
	}

	public List<PublicationDTO> getPublicationsActives() {
		return ModelMapperUtils.mapAllPublication( publicationRepository.findAll() );
	}

	public RequestAppointmentDTO requestAppointment( RequestAppointmentDTO dto ) {
		return requestAppointmentService.insert( dto );
	}
}
