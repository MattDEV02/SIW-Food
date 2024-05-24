package com.siw.siwfood.service;

import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.repository.UtenteRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UtenteService {
   @Autowired
   private UtenteRepository utenteRepository;

   @Transactional
   public Utente saveUtente(@NotNull Utente utente) {
      return this.utenteRepository.save(utente);
   }

   public Utente getUtente(Credenziali credenziali) {
      return this.utenteRepository.findByCredenziali(credenziali).orElse(null);
   }


}
