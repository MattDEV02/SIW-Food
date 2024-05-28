package com.siw.siwfood.controller.validator;

import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Utente;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UtenteValidator implements Validator {

   private static final String UTENTE_NOME_REGEX = "[^\\\\/:*?\"<>|]*";
   private static final Pattern UTENTE_NOME_PATTERN = Pattern.compile(UtenteValidator.UTENTE_NOME_REGEX);

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Cuoco.class.equals(aClass);
   }

   @Override
   public void validate(@NotNull Object object, @NonNull Errors errors) {
      Utente utente = (Utente) object;
      if(!UtenteValidator.UTENTE_NOME_PATTERN.matcher(utente.getNome()).matches()) {
         errors.rejectValue("nome", "utente.nome.invalidFormat");
      }
   }
}
