package com.siw.siwfood.controller;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
public class AuthenticationController {
   public final static String REGISTRATION_SUCCESSFUL = "redirect:/login?registrationSuccessful=true";
   public final static String REGISTRATION_ERROR = "registration.html";
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private UtenteService utenteService;
   //@Autowired
   //private UserValidator userValidator;
   //@Autowired
   //private CredenzialiValidator credentzialiValidator;

   @GetMapping(value = {"/registration", "/registration/"})
   public ModelAndView showRegisterForm() {
      ModelAndView modelAndView = new ModelAndView("registration.html");
      modelAndView.addObject("Utente", new Utente());
      modelAndView.addObject("credenziali", new Credenziali());
      return modelAndView;
   }

   @PostMapping(value = {"/registerNewUser", "/registerNewUser/"})
   public ModelAndView registerUser(@Valid @NonNull @ModelAttribute("utente") Utente utente,
                                    @NonNull BindingResult utenteBindingResult,
                                    @Valid @NonNull @ModelAttribute("credenziali") Credenziali credenziali,
                                    @NonNull BindingResult credenzialiBindingResult,
                                    @NonNull @RequestParam("confirm-password") String confirmPassword) {
      ModelAndView modelAndView = new ModelAndView(AuthenticationController.REGISTRATION_ERROR);
      //this.credentialsValidator.setConfirmPassword(confirmPassword);
      //this.userValidator.validate(user, userBindingResult);
      //this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!utenteBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
         //Utils.cryptAndSaveUserCredentialsPassword(credenziali, passwordEncoder);
        // utente.setCredenziali(credenziali);
         //User savedUser = this.utenteService.saveUser(utente);
        // if (savedUser != null) {
          //  modelAndView.setViewName(AuthenticationController.REGISTRATION_SUCCESSFUL);
         //}
      } else {
         List<ObjectError> userGlobalErrors = utenteBindingResult.getGlobalErrors();
         for (ObjectError userGlobalError : userGlobalErrors) {
            modelAndView.addObject(Objects.requireNonNull(userGlobalError.getCode()), userGlobalError.getDefaultMessage());
         }
         List<ObjectError> credentialsGlobalErrors = credenzialiBindingResult.getGlobalErrors();
         for (ObjectError credentialGlobalErrors : credentialsGlobalErrors) {
            modelAndView.addObject(Objects.requireNonNull(credentialGlobalErrors.getCode()), credentialGlobalErrors.getDefaultMessage());
         }
      }
      return modelAndView;
   }

   @GetMapping(value = {"/login", "/login/"})
   public ModelAndView showLoginForm() {
      ModelAndView modelAndView = new ModelAndView("login.html");
      modelAndView.addObject("credenziali", new Credenziali());
      return modelAndView;
   }

}
