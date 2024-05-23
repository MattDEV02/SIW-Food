package com.siw.siwfood.service;

import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.repository.IngredienteRepository;
import com.siw.siwfood.repository.RicettaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siw.siwfood.helpers.ricetta.Utils;

import java.util.HashSet;
import java.util.Set;

@Service
public class RicettaService {
   @Autowired
   private RicettaRepository ricettaRepository;
   @Autowired
   private IngredienteRepository ingredientRepository;

   public Iterable<Ricetta> getAllRicette() {
      return this.ricettaRepository.findAll();
   }

   public Iterable<Ricetta> getAllRicetteCuoco(Cuoco cuoco) {
      return this.ricettaRepository.findAllByCuocoOrderByIdDesc(cuoco);
   }

   public Long getRicetteCount() {
      return this.ricettaRepository.count();
   }

   public Ricetta getRicetta(Long ricettaId) {
      return this.ricettaRepository.findById(ricettaId).orElse(null);
   }

   @Transactional
   public void deleteRicetta(Ricetta ricetta) {
      this.ricettaRepository.delete(ricetta);
   }

   @Transactional
   public Ricetta saveRicetta(@NotNull Ricetta ricetta) {
      return this.ricettaRepository.save(ricetta);
   }

   public Iterable<Ricetta> getAllRicettaByNome(String nome) {
      return this.ricettaRepository.findAllByNomeContainingIgnoreCaseOrderByIdDesc(nome);
   }

   public Iterable<Ricetta> getAllRicettaCuocoByNome(Cuoco cuoco, String nome) {
      return this.ricettaRepository.findAllByCuocoAndNomeContainingIgnoreCaseOrderByIdDesc(cuoco, nome);
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

   public Ingrediente findIngrediente(@NotNull Ricetta ricetta, Long ingredienteId) {
      return ricetta.getIngredienti().stream().filter(ingrediente -> ingrediente.getId().equals(ingredienteId)).findFirst().orElse(null);
   }


   public Ricetta updateRicetta(Long ricettaId, Ricetta ricetta) {
      Ricetta ricettaToUpdate = null;
      ricettaToUpdate = this.ricettaRepository.findById(ricettaId).orElse(null);
      if(ricettaToUpdate != null) {
         ricettaToUpdate.setNome(ricetta.getNome());
         ricettaToUpdate.setDescrizione(ricetta.getDescrizione());
         ricettaToUpdate.setImmagini(ricetta.getImmagini());
         ricettaToUpdate = this.ricettaRepository.save(ricettaToUpdate);
      }
      return ricettaToUpdate;
   }
}
