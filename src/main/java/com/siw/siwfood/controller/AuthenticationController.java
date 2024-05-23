package com.siw.siwfood.controller;
import com.siw.siwfood.controller.validator.CredenzialiValidator;
import com.siw.siwfood.controller.validator.UtenteValidator;
import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.helpers.utente.Utils;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.CuocoService;
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
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private CuocoService cuocoService;
   @Autowired
   private UtenteService utenteService;
   @Autowired
   private UtenteValidator utenteValidator;
   @Autowired
   private CredenzialiValidator credenzialiValidator;

   @GetMapping(value = {"/register", "/register/"})
   public ModelAndView showRegisterForm() {
      ModelAndView modelAndView = new ModelAndView("utenteForm.html");
      modelAndView.addObject("utente", new Utente());
      modelAndView.addObject("credenziali", new Credenziali());
      return modelAndView;
   }

   @PostMapping(value = {"/register", "/register/"})
   public ModelAndView registerUser(
                                    @Valid @NonNull @ModelAttribute("utente") Utente utente,
                                    @NonNull BindingResult utenteBindingResult,
                                    @Valid @NonNull @ModelAttribute("credenziali") Credenziali credenziali,
                                    @NonNull BindingResult credenzialiBindingResult,
                                    @NonNull @RequestParam("confirm-password") String confirmPassword,
                                    @NonNull @RequestParam("fotografia-utente") MultipartFile fotografiaUtente) {
      ModelAndView modelAndView = new ModelAndView("utenteForm.html");
      this.utenteValidator.setFotografia(fotografiaUtente);
      this.credenzialiValidator.setConfirmPassword(confirmPassword);
      this.utenteValidator.validate(utente, utenteBindingResult);
      this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
      if (!utenteBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
         String encodedPassword = passwordEncoder.encode(credenziali.getPassword());
         credenziali.setPassword(encodedPassword);
         utente.setCredenziali(credenziali);
         Utente savedUtente = this.utenteService.saveUtente(utente);
         if (savedUtente != null) {
            Utils.storeUtenteFotografia(savedUtente, fotografiaUtente);
            Cuoco cuoco = new Cuoco(savedUtente);
            this.cuocoService.saveCuoco(cuoco);
            modelAndView.setViewName("redirect:/login");
            modelAndView.addObject("isUtenteRegistered", true);
         }
      } else {
         List<ObjectError> userErrors = utenteBindingResult.getAllErrors();
         for (ObjectError userError : userErrors) {
            System.out.println(userError.getDefaultMessage());
            modelAndView.addObject(Objects.requireNonNull(userError.getCode()), userError.getDefaultMessage());
         }
         List<ObjectError> credentialsErrors = credenzialiBindingResult.getAllErrors();
         for (ObjectError credentialErrors : credentialsErrors) {
            System.out.println(credentialErrors.getDefaultMessage());
            modelAndView.addObject(Objects.requireNonNull(credentialErrors.getCode()), credentialErrors.getDefaultMessage());
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

   @GetMapping(value = {"", "/"})
   public ModelAndView showDashBoard() {
      return new ModelAndView("index.html");
   }

}
