package com.siw.siwfood.service;

import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.repository.CredenzialiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredenzialiService {
   @Autowired
   private CredenzialiRepository credenzialiRepository;

   public Credenziali getCredenziali(String username) {
      return this.credenzialiRepository.findByUsername(username);
   }
}
