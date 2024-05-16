package com.siw.siwfood.controller;

import com.siw.siwfood.helpers.ricetta.Utils;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.CredenzialiService;
import com.siw.siwfood.service.RicettaService;
import com.siw.siwfood.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.siw.siwfood.model.Credenziali;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping(value = "/dashboard/ricette")
public class RicettaController {
   @Autowired
   private RicettaService ricettaService;
   @Autowired
   private UtenteService utenteService;
   @Autowired
   private CredenzialiService credenzialiService;

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
   public ModelAndView registerRicetta(@Valid @NonNull @ModelAttribute("ricetta") Ricetta ricetta,
                                    @NonNull BindingResult ricettaBindingResult,
                                    @NonNull @RequestParam("cuoco-ricetta") Long cuocoId,
                                    @NonNull @RequestParam("immagini-ricetta") MultipartFile[] immaginiRicetta) {
      ModelAndView modelAndView = new ModelAndView("dashboard/ricette/ricettaForm.html");
      //this.credentialsValidator.setConfirmPassword(confirmPassword);
      //this.userValidator.validate(user, userBindingResult);
      //this.credentialsValidator.validate(credentials, credentialsBindingResult);
      if (!ricettaBindingResult.hasErrors()) {
         Utente cuoco = this.utenteService.getCuoco(cuocoId);
         ricetta.setCuoco(cuoco);
         for(Integer i = 0; i < immaginiRicetta.length; i++) {
            ricetta.getImmagini().add(Utils.getRicettaRelativePathImmagineDirectoryName(this.ricettaService.getRicetteCount()) + Utils.getRicettaImmagineFileName(ricetta, i));
         }
         Ricetta savedRicetta = this.ricettaService.saveRicetta(ricetta);
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

   @GetMapping(value = {"/cuoco/{cuocoId}", "/cuoco/{cuocoId}/"})
   public ModelAndView showRicetteCuoco(@PathVariable("cuocoId") Long cuocoId) {
      ModelAndView modelAndView = new ModelAndView("dashboard/ricette/ricette.html");
      Utente cuoco = this.utenteService.getCuoco(cuocoId);
      Set<Ricetta> ricette = this.ricettaService.getAllRicetteCuoco(cuoco);
      modelAndView.addObject("ricette", ricette);
      return modelAndView;
   }

   @GetMapping(value = {"/searchRicette", "/searchRicette/"})
   public ModelAndView searchRicette(@NonNull HttpServletRequest request) {
      String nomeRicetta = request.getParameter("nome");
      String usernameCuoco = request.getParameter("cuoco");
      ModelAndView modelAndView = new ModelAndView("dashboard/ricette/ricette.html");
      Set<Ricetta> searchedRicette = null;
      if (nomeRicetta.isEmpty() && usernameCuoco.isEmpty()) {
         searchedRicette = this.ricettaService.getAllRicette();
      } else if (usernameCuoco.isEmpty()) {
         searchedRicette = this.ricettaService.getAllRicettaByNome(nomeRicetta);
      } else {
         Credenziali credenzialiCuoco = this.credenzialiService.getCredenziali(usernameCuoco);
         Utente cuoco = this.utenteService.getCuoco(credenzialiCuoco);
         if (nomeRicetta.isEmpty()) {
            searchedRicette = this.ricettaService.getAllRicetteCuoco(cuoco);
         } else {
            searchedRicette = this.ricettaService.getAllRicettaCuocoByNome(cuoco, nomeRicetta);
         }
      }
      modelAndView.addObject("ricette", searchedRicette);
     // modelAndView.addObject("searchedProductName", productName);
      return modelAndView;
   }

   @PutMapping(value = {"/update/{ricettaId}", "/update/{ricettaId}"})
   public ModelAndView updateRicetta(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("dashboard/ricette/ricetta.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      modelAndView.addObject("ricetta", ricetta);
      return modelAndView;
   }
}
