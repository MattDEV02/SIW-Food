package com.siw.siwfood.service;

import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.repository.IngredienteRepository;
import com.siw.siwfood.repository.RicettaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class RicettaService {
   @Autowired
   private RicettaRepository ricettaRepository;
   @Autowired
   private IngredienteRepository ingredientRepository;

   public Set<Ricetta> getAllRicette() {
      Set<Ricetta> result = new HashSet<Ricetta>();
      Iterable<Ricetta> ricette = this.ricettaRepository.findAll();
      for(Ricetta ricetta : ricette) {
         System.out.println(ricetta);
         result.add(ricetta);
      }
      return result;
   }

   public Set<Ricetta> getAllRicetteCuoco(Utente cuoco) {
      return this.ricettaRepository.findAllByCuoco(cuoco);
   }

   public Long getRicetteCount() {
      return this.ricettaRepository.count();
   }

   public Ricetta getRicetta(Long ricettaId) {
      return this.ricettaRepository.findById(ricettaId).orElse(null);
   }

   @Transactional
   public void deleteRicetta(Long ricettaId) {
      this.ricettaRepository.deleteById(ricettaId);
   }

   @Transactional
   public Ricetta saveRicetta(@NotNull Ricetta ricetta) {
      return this.ricettaRepository.save(ricetta);
   }

   public Set<Ricetta> getAllRicettaByNome(String nome) {
      return this.ricettaRepository.findAllByNome(nome);
   }

   public Set<Ricetta> getAllRicettaCuocoByNome(Utente cuoco, String nome) {
      return this.ricettaRepository.findAllByCuocoAndNomeContainingIgnoreCaseOrdOrderByIdDesc(cuoco, nome);
   }

   @Transactional
   public Ingrediente makeIngrediente(@NotNull Ricetta ricetta, @NotNull Ingrediente ingrediente) {
      ingrediente.setRicetta(ricetta);
      Ingrediente savedIngrediente = this.ingredientRepository.save(ingrediente);
      ricetta.getIngredienti().add(savedIngrediente);
      return savedIngrediente;
   }

   @Transactional
   public void destroyIngrediente(@NotNull Ricetta ricetta, @NotNull Ingrediente ingrediente) {
      ricetta.getIngredienti().remove(ingrediente);
      this.ricettaRepository.save(ricetta);
   }
}
