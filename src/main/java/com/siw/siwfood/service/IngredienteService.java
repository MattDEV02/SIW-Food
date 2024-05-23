package com.siw.siwfood.service;

import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.repository.IngredienteRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredienteService {
   @Autowired
   private IngredienteRepository ingredienteRepository;

   public Iterable<Ingrediente> getAllIngredienti() {
      return this.ingredienteRepository.findAll();
   }
}
