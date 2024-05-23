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
      Utente savedUtente = this.utenteRepository.save(utente);
      savedUtente.setFotografia(Utils.getUtenteRelativePathFotografiaDirectoryName(savedUtente) + "/" + Utils.getUtenteFotografiaFileName(savedUtente));
      return savedUtente;
   }

   public Utente getUtente(Credenziali credenziali) {
      return this.utenteRepository.findByCredenziali(credenziali).orElse(null);
   }


}
