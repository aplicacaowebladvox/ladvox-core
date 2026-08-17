package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAttachmentReposiory extends JpaRepository<Attachment, Long>, CustomSearchRepository {
}
