package com.siw.siwfood.service;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.helpers.credenziali.Roles;
import com.siw.siwfood.helpers.utente.Utils;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.repository.CuocoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Service
public class CuocoService {
   @Autowired
   private CuocoRepository cuocoRepository;

   public Set<Cuoco> getAllCuochi() {
      Set<Cuoco> result = new HashSet<Cuoco>();
      Iterable<Cuoco> cuochi = this.cuocoRepository.findAll();
      for(Cuoco cuoco : cuochi) {
         result.add(cuoco);
      }
      return result;
   }

   @Transactional
   public Cuoco saveCuoco(@NotNull Cuoco cuoco) {
      return this.cuocoRepository.save(cuoco);
   }

   @Transactional
   public void deleteCuoco(Long cuocoId) {
      this.cuocoRepository.deleteById(cuocoId);
   }

   public Cuoco getCuoco(Long cuocoId) {
      return this.cuocoRepository.findById(cuocoId).orElse(null);
   }

   public Cuoco getCuoco(@NotNull Utente utente) {
      return this.cuocoRepository.findByUtente(utente).orElse(null);
   }
}
