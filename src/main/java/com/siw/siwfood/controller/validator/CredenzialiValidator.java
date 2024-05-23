package com.siw.siwfood.controller.validator;

import com.siw.siwfood.helpers.credenziali.Utils;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.repository.CredenzialiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class CredenzialiValidator implements Validator {
   private String confirmPassword;
   private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8}$";
   private static final Pattern PASSWORD_PATTERN = Pattern.compile(CredenzialiValidator.PASSWORD_REGEX);
   @Autowired
   private CredenzialiRepository credenzialiRepository;

   public String getConfirmPassword() {
      return this.confirmPassword;
   }

   public void setConfirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      Credenziali credenziali = (Credenziali) object;
      if (this.credenzialiRepository.existsByUsername(credenziali.getUsername())) {
         errors.rejectValue("username", "credenziali.username.unique");
      }
      if (!CredenzialiValidator.PASSWORD_PATTERN.matcher(credenziali.getPassword()).matches()) {
         errors.rejectValue("password", "credenziali.password.invalidFormat");
      }
      if (this.getConfirmPassword() == null || !credenziali.getPassword().equals(this.getConfirmPassword())) {
         errors.rejectValue("password", "credenziali.password.passwordDifferentFromConfirmPasswordError");
      }
      /*
      if (!Utils.existsRole(credenziali.getRole())) {
         errors.rejectValue("role", "credenziali.role.notExists");
      }
      */
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Credenziali.class.equals(aClass);
   }
}
