package com.siw.siwfood.repository;

import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Utente;
import org.springframework.data.repository.CrudRepository;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
   Utente findByCredenziali(Credenziali credenziali);
}
