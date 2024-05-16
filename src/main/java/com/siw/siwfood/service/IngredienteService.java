package com.siw.siwfood.service;

import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.repository.IngredienteRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredienteService {
   @Autowired
   private IngredienteRepository ingredienteRepository;

   public Set<Ingrediente> getAllIngredienti() {
      Set<Ingrediente> result = new HashSet<Ingrediente>();
      Iterable<Ingrediente> ingredienti = this.ingredienteRepository.findAll();
      for(Ingrediente ingrediente : ingredienti) {
         result.add(ingrediente);
      }
      return result;
   }

   public Ingrediente getIngrediente(Long ingredienteId) {
      return this.ingredienteRepository.findById(ingredienteId).orElse(null);
   }

   public Set<Ingrediente> getAllIngredientiRicetta(Ricetta ricetta) {
      return this.ingredienteRepository.findAllByRicetta(ricetta);
   }
}
