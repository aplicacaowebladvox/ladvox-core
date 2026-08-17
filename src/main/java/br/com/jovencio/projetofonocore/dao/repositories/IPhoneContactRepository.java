package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.Address;
import br.com.jovencio.projetofonocore.model.entities.PhoneContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPhoneContactRepository extends JpaRepository<PhoneContact, Long> {}
