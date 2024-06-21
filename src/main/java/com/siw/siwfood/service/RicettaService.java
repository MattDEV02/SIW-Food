package com.siw.siwfood.service;

import com.siw.siwfood.helpers.ricetta.RicettaImmaginiFileUtils;
import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.repository.CuocoRepository;
import com.siw.siwfood.repository.IngredienteRepository;
import com.siw.siwfood.repository.RicettaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RicettaService {
   @Autowired
   private RicettaRepository ricettaRepository;
   @Autowired
   private IngredienteRepository ingredientRepository;
   @Autowired
   private CuocoRepository cuocoRepository;

   public Iterable<Ricetta> getAllRicette() {
      return this.ricettaRepository.findAllByOrderByIdDesc();
   }

   public Iterable<Ricetta> getAllRicetteCuoco(Cuoco cuoco) {
      return this.ricettaRepository.findAllByCuocoOrderByIdDesc(cuoco);
   }

   public Ricetta getRicetta(Long ricettaId) {
      return this.ricettaRepository.findById(ricettaId).orElse(null);
   }

   @Transactional
   public void deleteRicetta(Ricetta ricetta) {
      RicettaImmaginiFileUtils.deleteRicettaImmagini(ricetta);
      this.ricettaRepository.delete(ricetta);
   }

   @Transactional
   public Ricetta saveRicetta(@NotNull Ricetta ricetta, Integer numeroImmaginiRicetta) {
      Ricetta savedRicetta = this.ricettaRepository.save(ricetta);
      if(savedRicetta.getCuoco() != null) {
         this.cuocoRepository.findById(savedRicetta.getCuoco().getId())
                 .ifPresent(cuoco -> cuoco.getRicette().add(savedRicetta));
      }
      for(Integer i = 0; i < numeroImmaginiRicetta; i++) {
         savedRicetta.getImmagini().add(RicettaImmaginiFileUtils.getRicettaImmagineRelativePath(savedRicetta, i));
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
   public void makeIngrediente(@NotNull Ricetta ricetta, @NotNull Ingrediente ingrediente) {
      ingrediente.setRicetta(ricetta);
      Ingrediente savedIngrediente = this.ingredientRepository.save(ingrediente);
      ricetta.getIngredienti().add(savedIngrediente);
   }

   @Transactional
   public void destroyIngrediente(@NotNull Ricetta ricetta, @NotNull Ingrediente ingrediente) {
      ricetta.getIngredienti().remove(ingrediente);
      this.ricettaRepository.save(ricetta);
   }

   public Ingrediente findIngrediente(@NotNull Ricetta ricetta, Long ingredienteId) {
      return ricetta.getIngredienti().stream().filter(ingrediente -> ingrediente.getId().equals(ingredienteId)).findFirst().orElse(null);
   }

   public Ricetta updateRicetta(@NotNull Ricetta ricettaToUpdate, @NotNull Ricetta ricetta, Integer numeroImmaginiRicetta) {
      ricettaToUpdate.setNome(ricetta.getNome());
      ricettaToUpdate.setDescrizione(ricetta.getDescrizione());
      ricettaToUpdate.setCuoco(ricetta.getCuoco());
      if(numeroImmaginiRicetta > 0) {
         RicettaImmaginiFileUtils.deleteRicettaImmagini(ricettaToUpdate);
         List<String> immaginiRicetta = ricettaToUpdate.getImmagini();
         immaginiRicetta.clear();
         for(Integer i = 0; i < numeroImmaginiRicetta; i++) {
            immaginiRicetta.add(RicettaImmaginiFileUtils.getRicettaImmagineRelativePath(ricettaToUpdate, i));
         }
      }
      return this.ricettaRepository.save(ricettaToUpdate);
   }

   public void updateIngrediente(Ricetta ricetta, Long ingredienteId, Ingrediente ingrediente) {
      Ingrediente ingredienteToUpdate = this.findIngrediente(ricetta, ingredienteId);
      if(ingredienteToUpdate != null) {
         ingredienteToUpdate.setNome(ingrediente.getNome());
         ingredienteToUpdate.setQuantita(ingrediente.getQuantita());
         this.ingredientRepository.save(ingredienteToUpdate);
      }
   }
}
