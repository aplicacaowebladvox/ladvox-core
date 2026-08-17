package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.EmailContact;
import br.com.jovencio.projetofonocore.model.entities.PhoneContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmailContactRepository extends JpaRepository<EmailContact, Long> {}
