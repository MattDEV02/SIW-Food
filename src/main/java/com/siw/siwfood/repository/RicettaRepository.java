package com.siw.siwfood.repository;

import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RicettaRepository extends CrudRepository<Ricetta, Long> {
   public Iterable<Ricetta> findAllByCuocoOrderByIdDesc(Cuoco cuoco);

   public Iterable<Ricetta> findAllByNomeContainingIgnoreCaseOrderByIdDesc(String nome);

   public Iterable<Ricetta> findAllByCuocoAndNomeContainingIgnoreCaseOrderByIdDesc(Cuoco cuoco, String nome);
}
