package com.siw.siwfood.repository;

import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Utente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuocoRepository extends CrudRepository<Cuoco, Long> {
   public Optional<Cuoco> findByUtente(Utente utente);
   public Iterable<Cuoco> findAllByOrderByIdDesc();

}
