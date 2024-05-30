package com.siw.siwfood.repository;

import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import org.springframework.data.repository.CrudRepository;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
   public Boolean existsByRicettaAndNome(Ricetta ricetta, String nome);
}
