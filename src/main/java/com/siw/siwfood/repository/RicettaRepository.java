package com.siw.siwfood.repository;

import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RicettaRepository extends CrudRepository<Ricetta, Long> {
   public Set<Ricetta> findAllByCuoco(Utente cuoco);

   public Set<Ricetta> findAllByNome(String nome);

   public Set<Ricetta> findAllByCuocoAndNomeContainingIgnoreCaseOrdOrderByIdDesc(Utente cuoco, String nome);
}
