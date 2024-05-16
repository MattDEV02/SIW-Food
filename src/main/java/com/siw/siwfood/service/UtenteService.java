package com.siw.siwfood.service;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.helpers.credenziali.Roles;
import com.siw.siwfood.helpers.utente.Utils;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.repository.UtenteRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UtenteService {
   @Autowired
   private UtenteRepository utenteRepository;

   @Transactional
   public Utente saveUtente(@NotNull Utente utente) {
      Utente savedUser = this.utenteRepository.save(utente);
      savedUser.setFotografia(ProjectPaths.IMAGES + Utils.getUtenteRelativePathFotografiaDirectoryName(savedUser) + "/" + Utils.getUtenteFotografiaFileName(savedUser));
      System.out.println(savedUser.getFotografia());
      return savedUser;
   }

   public Utente getUtente(Credenziali credenziali) {
      return this.utenteRepository.findByCredenziali(credenziali);
   }

   public Set<Utente> getAllCuochi() {
      Set<Utente> result = new HashSet<Utente>();
      Iterable<Utente> utenti = this.utenteRepository.findAll();
      for(Utente utente : utenti) {
         if(utente.getCredenziali().getRole().contains(Roles.REGISTRATO_ROLE.toString())) {
            result.add(utente);
         }
      }
      return result;
   }

   @Transactional
   public void deleteCuoco(Long cuocoId) {
      this.utenteRepository.deleteById(cuocoId);
   }

   public Utente getCuoco(Long cuocoId) {
      Utente cuoco = this.utenteRepository.findById(cuocoId).orElse(null);
      if(!cuoco.getCredenziali().getRole().contains(Roles.REGISTRATO_ROLE.toString())) {
         return null;
      }
      return cuoco;
   }

   public Utente getCuoco(@NotNull Credenziali credenziali) {
      if(!credenziali.getRole().contains(Roles.REGISTRATO_ROLE.toString())) {
         return null;
      }
      return this.utenteRepository.findByCredenziali(credenziali);
   }
}
