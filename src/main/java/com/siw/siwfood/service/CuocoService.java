package com.siw.siwfood.service;

import com.siw.siwfood.helpers.cuoco.FotografiaFileUtils;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.repository.CuocoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.siw.siwfood.helpers.ricetta.RicettaImmaginiFileUtils.deleteRicettaImmaginiDirectory;

@Service
public class CuocoService {
   @Autowired
   private CuocoRepository cuocoRepository;

   public Iterable<Cuoco> getAllCuochi() {
      return this.cuocoRepository.findAllByOrderByIdDesc();
   }

   @Transactional
   public Cuoco saveCuoco(@NotNull Cuoco cuoco) {
      Cuoco savedCuoco = this.cuocoRepository.save(cuoco);
      savedCuoco.setFotografia(FotografiaFileUtils.getCuocoFotografiaRelativePath(savedCuoco));
      return this.cuocoRepository.save(cuoco);
   }

   @Transactional
   public void deleteCuoco(Long cuocoId) {
      Cuoco cuoco = this.getCuoco(cuocoId);
      if(cuoco != null) {
         FotografiaFileUtils.deleteFotografiaDirectory(cuoco);
         List<Ricetta> ricette = cuoco.getRicette();
         for(Ricetta ricetta : ricette) {
            deleteRicettaImmaginiDirectory(ricetta);
         }
         this.cuocoRepository.delete(cuoco);
      }
   }

   @Transactional
   public Cuoco getCuoco(Long cuocoId) {
      return this.cuocoRepository.findById(cuocoId).orElse(null);
   }

   public Cuoco getCuoco(@NotNull Utente utente) {
      return this.cuocoRepository.findByUtente(utente).orElse(null);
   }
}
