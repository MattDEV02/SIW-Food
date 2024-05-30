package com.siw.siwfood.controller.validator;

import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IngredienteValidator implements Validator {
   @Autowired
   private IngredienteRepository ingredienteRepository;
   private Ricetta ricetta;

   public Ricetta getRicetta() {
      return this.ricetta;
   }

   public void setRicetta(Ricetta ricetta) {
      this.ricetta = ricetta;
   }

   @Override
   public boolean supports(Class<?> aClass) {
      return Ingrediente.class.equals(aClass);
   }

   @Override
   public void validate(Object object, Errors errors) {
      Ingrediente ingrediente = (Ingrediente) object;
      if(this.ingredienteRepository.existsByRicettaAndNome(this.ricetta, ingrediente.getNome())) {
         errors.reject("ingredienteAlrearyExists", "Ingrediente già esistente.");
      }
   }
}
