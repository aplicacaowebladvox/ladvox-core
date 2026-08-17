package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.SystemContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISystemContactRepository extends JpaRepository<SystemContact, Long>{}
