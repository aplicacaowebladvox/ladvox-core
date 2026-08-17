package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.AnamnesisPatientAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAnamnesisPatientAttachmentRepository extends JpaRepository<AnamnesisPatientAttachment, Long>, CustomSearchRepository {

	List<AnamnesisPatientAttachment> findAllByAnamnesisPatientId( long anamnesisPatientId );
}
