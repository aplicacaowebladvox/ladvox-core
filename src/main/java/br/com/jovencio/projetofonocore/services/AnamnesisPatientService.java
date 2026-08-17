package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IAnamnesisPatientRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IPatientRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IUserRepository;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import br.com.jovencio.projetofonocore.model.dtos.AnamnesisPatientDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.entities.AnamnesisPatient;
import br.com.jovencio.projetofonocore.model.entities.Patient;
import br.com.jovencio.projetofonocore.model.grid.AnamnesisPatientGrid;
import br.com.jovencio.projetofonocore.model.grid.options.AnamnesisPatientGridOptions;
import br.com.jovencio.projetofonocore.services.auth.component.AuthComponent;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnamnesisPatientService {

	private final IAnamnesisPatientRepository repository;
	private final IPatientRepository patientRepository;
	private final AuthComponent authComponent;
	private final IUserRepository userRepository;

	public SearchReturnDTO<AnamnesisPatientGrid> search( AnamnesisPatientGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public AnamnesisPatientDTO get( Long id ) {
		return repository.findById( id ).map( ModelMapperUtils::map ).orElseThrow();
	}

	public AnamnesisPatientDTO save( AnamnesisPatientDTO dto ) {
		if( dto.getId() != null ) {
			return save( dto.getId(), dto );
		}
		var entity = ModelMapperUtils.map( dto );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public AnamnesisPatientDTO save( long id, AnamnesisPatientDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( id, entity );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public AnamnesisPatient generateNewForAttachment( long patientId ) {
		var entity = new AnamnesisPatient();
		entity.setPatient( new Patient( patientId ) );
		entity.setTherapist( new User( authComponent.getUserDTO().getId() ) );
		entity.setCreatedDate( LocalDateTime.now() );
		return repository.save( entity );
	}

	private void validate( Long id, AnamnesisPatient entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Anamnese do paciente não informada" );
		}
		List<String> errors = new ArrayList<>();

		if( entity.getPatient() == null || entity.getPatient().getId() == null ) {
			errors.add( "Paciente não informado" );
		} else if( patientRepository.findById( entity.getPatient().getId() ).isEmpty() ) {
			errors.add( "Paciente não registrado na base de dados" );
		}

		if( id == null ) {
			entity.setTherapist( new User( authComponent.getUserDTO().getId() ) );
		}
		if( entity.getTherapist() == null || entity.getTherapist().getId() == null ) {
			errors.add( "Terapeuta não informado" );
		} else if( userRepository.findById( entity.getTherapist().getId() ).isEmpty() ) {
			errors.add( "Terapeuta não registrado na base de dados" );
		}
		if( id != null ) {
			var anamnesisPatient = repository.findById( id ).orElseThrow();
			if( anamnesisPatient.getTherapist().getId().compareTo( entity.getTherapist().getId() ) != 0 ) {
				errors.add( "Terapeuta não pode ser alterado" );
			}
			if( anamnesisPatient.getPatient().getId().compareTo( entity.getPatient().getId() ) != 0 ) {
				errors.add( "Paciente não pode ser alterado" );
			}
			anamnesisPatient.setCreatedDate( entity.getCreatedDate() );
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}
}
