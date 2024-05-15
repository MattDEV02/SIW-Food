package com.siw.siwfood.controller;
import com.siw.siwfood.helpers.utente.Utils;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
   //private CredenzialiValidator credenzialiValidator;

   @GetMapping(value = {"/register", "/register/"})
   public ModelAndView showRegisterForm() {
      ModelAndView modelAndView = new ModelAndView("utenteForm.html");
      modelAndView.addObject("utente", new Utente());
      modelAndView.addObject("credenziali", new Credenziali());
      return modelAndView;
   }

   @PostMapping(value = {"/register", "/register/"})
   public ModelAndView registerUser(@Valid @NonNull @ModelAttribute("utente") Utente utente,
                                    @NonNull BindingResult utenteBindingResult,
                                    @Valid @NonNull @ModelAttribute("credenziali") Credenziali credenziali,
                                    @NonNull BindingResult credenzialiBindingResult,
                                    @NonNull @RequestParam("confirm-password") String confirmPassword,
                                    @NonNull @RequestParam("fotografia-utente") MultipartFile fotografiaUtente) {
      ModelAndView modelAndView = new ModelAndView(AuthenticationController.REGISTRATION_ERROR);
      //this.credentialsValidator.setConfirmPassword(confirmPassword);
      //this.userValidator.validate(user, userBindingResult);
      //this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!utenteBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
         String encodedPassword = passwordEncoder.encode(credenziali.getPassword());
         credenziali.setPassword(encodedPassword);
         utente.setCredenziali(credenziali);
         Utente savedUser = this.utenteService.saveUtente(utente);
         if (savedUser != null) {
            Utils.storeUtenteFotografia(savedUser, fotografiaUtente);
            modelAndView.setViewName(AuthenticationController.REGISTRATION_SUCCESSFUL);
         }
      } else {
         List<ObjectError> userGlobalErrors = utenteBindingResult.getAllErrors();
         for (ObjectError userGlobalError : userGlobalErrors) {
            System.out.println(userGlobalError.getDefaultMessage());
            modelAndView.addObject(Objects.requireNonNull(userGlobalError.getCode()), userGlobalError.getDefaultMessage());
         }
         List<ObjectError> credentialsGlobalErrors = credenzialiBindingResult.getAllErrors();
         for (ObjectError credentialGlobalErrors : credentialsGlobalErrors) {
            System.out.println(credentialGlobalErrors.getDefaultMessage());
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
