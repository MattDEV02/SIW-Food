package com.siw.siwfood.controller;
import com.siw.siwfood.controller.validator.CredenzialiValidator;
import com.siw.siwfood.controller.validator.CuocoValidator;
import com.siw.siwfood.helpers.cuoco.Utils;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.CuocoService;
import com.siw.siwfood.service.RicettaService;
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

import static com.siw.siwfood.helpers.credenziali.Utils.isCuoco;

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
   private CuocoValidator cuocoValidator;
   @Autowired
   private CredenzialiValidator credenzialiValidator;
   @Autowired
   private RicettaService ricettaService;

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
                                    @NonNull @RequestParam("fotografia-cuoco") MultipartFile fotografiaCuoco
   ) {
      ModelAndView modelAndView = new ModelAndView("utenteForm.html");
      this.cuocoValidator.setFotografia(fotografiaCuoco);
      this.credenzialiValidator.setConfirmPassword(confirmPassword);
      this.cuocoValidator.validate(utente, utenteBindingResult);
      this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
      if (!utenteBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
         String encodedPassword = passwordEncoder.encode(credenziali.getPassword());
         credenziali.setPassword(encodedPassword);
         utente.setCredenziali(credenziali);
         Utente savedUtente = this.utenteService.saveUtente(utente);
         if (savedUtente != null) {
            Cuoco cuoco = new Cuoco(savedUtente);
            Cuoco savedCuoco = this.cuocoService.saveCuoco(cuoco);
            if(savedCuoco != null) {
               Utils.storeCuocoFotografia(cuoco, fotografiaCuoco);
            }
            modelAndView.setViewName("redirect:/login");
            modelAndView.addObject("isUtenteRegistered", true);
         }
      } else {
         List<ObjectError> userErrors = utenteBindingResult.getAllErrors();
         for (ObjectError userError : userErrors) {
            modelAndView.addObject(Objects.requireNonNull(userError.getCode()), userError.getDefaultMessage());
         }
         List<ObjectError> credentialsErrors = credenzialiBindingResult.getAllErrors();
         for (ObjectError credentialErrors : credentialsErrors) {
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
   public ModelAndView showDashBoard(@ModelAttribute("loggedUser") Utente loggedUser) {
      ModelAndView modelAndView = new ModelAndView("index.html");
      Iterable<Ricetta> ricette = null;
      if(isCuoco(loggedUser)) {
         Cuoco cuoco = this.cuocoService.getCuoco(loggedUser);
         ricette = this.ricettaService.getAllRicetteCuoco(cuoco);
      }
      modelAndView.addObject("ricette", ricette);
      return modelAndView;
   }

}
