package com.siw.siwfood.repository;

import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Utente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
   Optional<Utente> findByCredenziali(Credenziali credenziali);
}
