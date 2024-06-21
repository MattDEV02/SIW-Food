package com.siw.siwfood.service;

import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {
   @Autowired
   private IngredienteRepository ingredienteRepository;

   public Iterable<Ingrediente> getAllIngredienti() {
      return this.ingredienteRepository.findAllByOrderByIdDesc();
   }
}
