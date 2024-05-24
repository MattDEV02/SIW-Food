package com.siw.siwfood.repository;

import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import org.springframework.data.repository.CrudRepository;


public interface RicettaRepository extends CrudRepository<Ricetta, Long> {
   public Iterable<Ricetta> findAllByCuocoOrderByIdDesc(Cuoco cuoco);

   public Iterable<Ricetta> findAllByNomeContainingIgnoreCaseOrderByIdDesc(String nome);

   public Iterable<Ricetta> findAllByCuocoAndNomeContainingIgnoreCaseOrderByIdDesc(Cuoco cuoco, String nome);
}
