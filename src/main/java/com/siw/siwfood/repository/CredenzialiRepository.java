package com.siw.siwfood.repository;

import com.siw.siwfood.model.Credenziali;
import org.springframework.data.repository.CrudRepository;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {
   Credenziali findByUsername(String username);

}
