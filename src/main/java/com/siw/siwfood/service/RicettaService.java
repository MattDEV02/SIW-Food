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
      Ricetta savedRicetta = this.ricettaRepository.save(ricetta);
      if(ricetta.getCuoco() != null) {
         Cuoco cuoco = savedRicetta.getCuoco();
         cuoco.getRicette().add(savedRicetta);
      }
      return savedRicetta;
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


   public Ricetta updateRicetta(Ricetta ricettaToUpdate, @NotNull Ricetta ricetta) {
      if(ricettaToUpdate != null) {
         ricettaToUpdate.setNome(ricetta.getNome());
         ricettaToUpdate.setDescrizione(ricetta.getDescrizione());
         if(ricetta.getCuoco() != null) {
            ricettaToUpdate.setCuoco(ricetta.getCuoco());
         }
         if(!ricetta.getImmagini().isEmpty()) {
            Utils.deleteRicettaImmagini(ricetta);
            ricettaToUpdate.setImmagini(ricetta.getImmagini());
         }
         ricettaToUpdate = this.ricettaRepository.save(ricettaToUpdate);
      }
      return ricettaToUpdate;
   }

   public Ingrediente updateIngrediente(Ricetta ricetta, Long ingredienteId, Ingrediente ingrediente) {
      Ingrediente ingredienteToUpdate = this.findIngrediente(ricetta, ingredienteId);
      if(ingredienteToUpdate != null) {
         ingredienteToUpdate.setNome(ingrediente.getNome());
         ingredienteToUpdate.setQuantita(ingrediente.getQuantita());
         ingredienteToUpdate = this.ingredientRepository.save(ingredienteToUpdate);
      }
      return ingredienteToUpdate;
   }
}
