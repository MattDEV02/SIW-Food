package com.siw.siwfood.controller;

import com.siw.siwfood.helpers.ricetta.Utils;
import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.RicettaService;
import com.siw.siwfood.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/dashboard/ricette")
public class RicetteController {
   @Autowired
   private RicettaService ricettaService;
   @Autowired
   private UtenteService utenteService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showAllRicette() {
      ModelAndView modelAndView = new ModelAndView("dashboard/ricette/ricette.html");
      modelAndView.addObject("ricette", this.ricettaService.getAllRicette());
      System.out.println(this.ricettaService.getAllRicette().size());
      return modelAndView;
   }

   @GetMapping(value = {"/{ricettaId}", "/{ricettaId}/"})
   public ModelAndView showCuoco(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("dashboard/ricette/ricetta.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      modelAndView.addObject("ricetta", ricetta);
      return modelAndView;
   }

   @GetMapping(value = {"/register", "/register/"})
   public ModelAndView showRicetteForm() {
      ModelAndView modelAndView = new ModelAndView("dashboard/ricette/ricettaForm.html");
      modelAndView.addObject("ricetta", new Ricetta());
      modelAndView.addObject("cuochi", this.utenteService.getAllCuochi());
      //modelAndView.setViewName("redirect:/dashboard/ricette/" + "id");
      return modelAndView;
   }

   @PostMapping(value = {"/register", "/register/"})
   public ModelAndView registerUser(@Valid @NonNull @ModelAttribute("ricetta") Ricetta ricetta,
                                    @NonNull BindingResult ricettaBindingResult,
                                   // @Valid @NonNull @ModelAttribute("ingrediente") Ingrediente ingrediente,
                                 //   @NonNull BindingResult ingredienteBindingResult,
                                    @NonNull @RequestParam("cuoco-ricetta") Long cuocoId,
                                    @NonNull @RequestParam("immagini-ricetta") MultipartFile[] immaginiRicetta) {
      ModelAndView modelAndView = new ModelAndView("dashboard/ricette/ricettaForm.html");
      //this.credentialsValidator.setConfirmPassword(confirmPassword);
      //this.userValidator.validate(user, userBindingResult);
      //this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!ricettaBindingResult.hasErrors() && true) { // !ingredienteBindingResult.hasErrors()
         Utente cuoco = this.utenteService.getCuoco(cuocoId);
         System.out.println(cuoco);
         Ricetta savedRicetta = this.ricettaService.saveRicetta(ricetta, cuoco);
         if (savedRicetta != null) {
            for(Integer i = 0; i < immaginiRicetta.length; i++) {
               Utils.storeRicettaImmagine(savedRicetta, immaginiRicetta[i], i);
            }
            modelAndView.setViewName("redirect:/dashboard/ricette/" + savedRicetta.getId());
         }
      } else {
         List<ObjectError> ricettaGlobalErrors = ricettaBindingResult.getAllErrors();
         for (ObjectError ricettaGlobalError : ricettaGlobalErrors) {
            System.out.println(ricettaGlobalError.getObjectName() + " " + ricettaGlobalError.getCode() + " " + ricettaGlobalError.getDefaultMessage());
            modelAndView.addObject(Objects.requireNonNull(ricettaGlobalError.getCode()), ricettaGlobalError.getDefaultMessage());
         }
         /*
         List<ObjectError> ingredienteGlobalErrors = ingredienteBindingResult.getAllErrors();
         for (ObjectError ingredienteGlobalError : ingredienteGlobalErrors) {
            System.out.println(ingredienteGlobalError.getDefaultMessage());
            modelAndView.addObject(Objects.requireNonNull(ingredienteGlobalError.getCode()), ingredienteGlobalError.getDefaultMessage());
         }
         */
         modelAndView.addObject("cuochi", this.utenteService.getAllCuochi());
      }
      return modelAndView;
   }

   @GetMapping(value = {"/delete/{ricettaId}", "/delete/{ricettaId}/"})
   public ModelAndView deleteRicetta(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/dashboard/ricette");
      this.ricettaService.deleteRicetta(ricettaId);
      return modelAndView;
   }
}
