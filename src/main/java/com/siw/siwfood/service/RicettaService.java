package com.siw.siwfood.service;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.helpers.ricetta.Utils;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.repository.RicettaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RicettaService {
   @Autowired
   private RicettaRepository ricettaRepository;

   public Set<Ricetta> getAllRicette() {
      Set<Ricetta> result = new HashSet<Ricetta>();
      Iterable<Ricetta> ricette = this.ricettaRepository.findAll();
      for(Ricetta ricetta : ricette) {
         System.out.println(ricetta);
         result.add(ricetta);
      }
      return result;
   }

   public Ricetta getRicetta(Long ricettaId) {
      return this.ricettaRepository.findById(ricettaId).orElse(null);
   }

   public void deleteRicetta(Long ricettaId) {
      this.ricettaRepository.deleteById(ricettaId);
   }

   public Ricetta saveRicetta(@NotNull Ricetta ricetta, Utente cuoco) {
      Ricetta savedRicetta = null;
      ricetta.setCuoco(cuoco);
      savedRicetta = this.ricettaRepository.save(ricetta);
      return savedRicetta;
   }
}
