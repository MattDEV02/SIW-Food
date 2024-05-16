package com.siw.siwfood.repository;

import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {
   public Set<Ingrediente> findAllByRicetta(Ricetta ricetta);
}
