package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IPatientRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IProtocolRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IProtocolTherapeuticPlanRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IUserNotificationRepository;
import br.com.jovencio.projetofonocore.model.dtos.UserNotificationDTO;
import br.com.jovencio.projetofonocore.model.entities.UserNotification;
import br.com.jovencio.projetofonocore.services.auth.component.AuthComponent;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserNotificationService {

	private final IUserNotificationRepository repository;
	private final IPatientRepository patientRepository;
	private final IProtocolRepository protocolRepository;
	private final IProtocolTherapeuticPlanRepository protocolTherapeuticPlanRepository;
	private final AuthComponent authComponent;

	public List<UserNotificationDTO> findAllByLoggedUser( Integer amountOfNotifications ) {
		amountOfNotifications = Optional.ofNullable( amountOfNotifications ).orElse( 10 );
		if( authComponent.getUserDTO().getRoles().stream().anyMatch( r -> r.getName().equalsIgnoreCase( "Paciente" ) ) ) {
			return repository.findAllByUserIdAndForPatientIsTrue( authComponent.getUserDTO().getId(), amountOfNotifications ).stream().map( ModelMapperUtils::map ).toList();
		}
		return repository.findAllByUserId( authComponent.getUserDTO().getId(), amountOfNotifications ).stream().map( ModelMapperUtils::map ).toList();
	}

	public List<String> findAllOfPatient() {
		return repository.findAllByUserIdAndIsPatientAndIsOnlyUnreadLimitedBy( authComponent.getUserDTO().getId(), true, true, 11 ).stream().map( UserNotification::getNotification ).toList();
	}

	public List<String> findAllNotPatient() {
		return repository.findAllByUserIdAndIsPatientAndIsOnlyUnreadLimitedBy( authComponent.getUserDTO().getId(), false, true, 11 ).stream().map( UserNotification::getNotification ).toList();
	}

	public void notifyNewProtocolRequested( long protocolTherapeuticPlanId ) {
		var entity = new UserNotification();
		var protocolTherapeuticPlan = protocolTherapeuticPlanRepository.findById( protocolTherapeuticPlanId ).orElseThrow();
		entity.setUser( patientRepository.findById( protocolTherapeuticPlan.getTherapeuticPlan().getMedicalAppointment().getPatient().getId() ).orElseThrow().getUser() );
		entity.setCreatedDate( LocalDateTime.now() );
		entity.setNotification( String.format( "Há uma nova solicitação de resposta para o protocolo <strong>%s</strong>", protocolTherapeuticPlan.getProtocol().getName() ) );
		entity.setForPatient( true );
		entity.setOutEntityProtocolTherapeuticPlan( protocolTherapeuticPlanId );
		repository.save( entity );
	}

	public void notifyNewProtocolAnswered( long protocolTherapeuticPlanId ) {
		var protocolTherapeuticPlan = protocolTherapeuticPlanRepository.findById( protocolTherapeuticPlanId ).orElseThrow();
		var notification = String.format( "O protocolo <strong>%s</strong> do paciente <strong>%s</strong> foi respondido em <strong>%s</strong>", protocolTherapeuticPlan.getProtocol().getName(), protocolTherapeuticPlan.getTherapeuticPlan().getMedicalAppointment().getPatient().getName(),
				protocolTherapeuticPlan.getAnsweredDate().format( DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm:ss" ) ) );
		for( var user : protocolTherapeuticPlan.getTherapeuticPlan().getMedicalAppointment().getTeam().getTherapists() ) {
			var entity = new UserNotification();
			entity.setUser( user );
			entity.setCreatedDate( protocolTherapeuticPlan.getAnsweredDate() );
			entity.setNotification( notification );
			entity.setOutEntityProtocolTherapeuticPlan( protocolTherapeuticPlanId );
			repository.save( entity );
		}
		var notifications = repository.findAllByOutEntity( "ProtocolTherapeuticPlan=" + protocolTherapeuticPlanId );
		notifications.forEach( userNotification -> userNotification.setMarkAsRead( true ) );
		repository.saveAll( notifications );
	}

	public void markAsRead( long id ) {
		var entity = repository.findById( id ).orElseThrow();
		entity.setMarkAsRead( !entity.isMarkAsRead() );
		repository.save( entity );
	}

	public void delete( long id ) {
		repository.deleteById( id );
	}

	public void deleteByProtocolTherapeuticPlanId( long protocolTherapeuticPlanId ) {
		repository.deleteAll( repository.findAllByOutEntity( "ProtocolTherapeuticPlan=" + protocolTherapeuticPlanId ) );
	}
}
