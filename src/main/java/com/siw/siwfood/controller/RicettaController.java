package com.siw.siwfood.controller;

import com.siw.siwfood.helpers.credenziali.Roles;
import com.siw.siwfood.helpers.ricetta.Utils;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.CredenzialiService;
import com.siw.siwfood.service.CuocoService;
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
@RequestMapping(value = "/ricette")
public class RicettaController {
   @Autowired
   private RicettaService ricettaService;
   @Autowired
   private UtenteService utenteService;
   @Autowired
   private CuocoService cuocoService;
   @Autowired
   private CredenzialiService credenzialiService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showAllRicette() {
      System.out.println("ppp");
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricette.html");
      modelAndView.addObject("ricette", this.ricettaService.getAllRicette());
      return modelAndView;
   }

   @GetMapping(value = {"/{ricettaId}", "/{ricettaId}/"})
   public ModelAndView showCuoco(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricetta.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      modelAndView.addObject("ricetta", ricetta);
      return modelAndView;
   }

   @GetMapping(value = {"/register", "/register/"})
   public ModelAndView showRicetteForm( @Valid @ModelAttribute("cuochiSelect") @NonNull Set<Cuoco> cuochi) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricettaForm.html");
      modelAndView.addObject("ricetta", new Ricetta());
      modelAndView.addObject("isUpdate", false);
      if(cuochi.isEmpty()) {
         modelAndView.addObject("noCuochi", true);
      }
      return modelAndView;
   }

   @PostMapping(value = {"/register", "/register/"})
   public ModelAndView registerRicetta(
                                    @Valid @ModelAttribute("loggedUser") @NonNull Utente loggedUser,
                                    @Valid @NonNull @ModelAttribute("ricetta") Ricetta ricetta,
                                    @NonNull BindingResult ricettaBindingResult,
                                    @RequestParam(value = "cuoco-ricetta", required = false) Long cuocoId,
                                    @NonNull @RequestParam("immagini-ricetta") MultipartFile[] immaginiRicetta) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricettaForm.html");
      if (!ricettaBindingResult.hasErrors()) {
         Cuoco cuoco = null;
         cuoco = loggedUser.getCredenziali().getRole().equals(Roles.REGISTRATO_ROLE.toString()) ? this.cuocoService.getCuoco(loggedUser) : this.cuocoService.getCuoco(cuocoId);
         ricetta.setCuoco(cuoco);
         final Integer numeroImmaginiRicetta = immaginiRicetta.length;
         for(Integer i = 0; i < numeroImmaginiRicetta; i++) {
            ricetta.getImmagini().add(Utils.getRicettaRelativePathImmagineDirectoryName(this.ricettaService.getRicetteCount()) + Utils.getRicettaImmagineFileName(ricetta, i));
         }
         Ricetta savedRicetta = this.ricettaService.saveRicetta(ricetta);
         if (savedRicetta != null) {
            for(Integer i = 0; i < numeroImmaginiRicetta; i++) {
               Utils.storeRicettaImmagine(savedRicetta, immaginiRicetta[i], i);
            }
            modelAndView.setViewName("redirect:/ricette/" + savedRicetta.getId());
            modelAndView.addObject("isRicettaRegistered", true);
         }
      } else {
         List<ObjectError> ricettaErrors = ricettaBindingResult.getAllErrors();
         for (ObjectError ricettaError : ricettaErrors) {
            System.out.println(ricettaError.getObjectName() + " " + ricettaError.getCode() + " " + ricettaError.getDefaultMessage());
            modelAndView.addObject(Objects.requireNonNull(ricettaError.getCode()), ricettaError.getDefaultMessage());
         }
         modelAndView.addObject("isUpdate", true);
      }
      return modelAndView;
   }

   @GetMapping(value = {"/delete/{ricettaId}", "/delete/{ricettaId}/"})
   public ModelAndView deleteRicetta(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/ricette");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      Utils.deleteRicettaImmaginiDirectory(ricetta);
      this.ricettaService.deleteRicetta(ricetta);
      modelAndView.addObject("isRicettaDeleted", true);
      return modelAndView;
   }

   @GetMapping(value = {"/cuoco/{cuocoId}", "/cuoco/{cuocoId}/"})
   public ModelAndView showRicetteCuoco(@PathVariable("cuocoId") Long cuocoId) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricette.html");
      Cuoco cuoco = this.cuocoService.getCuoco(cuocoId);
      Iterable<Ricetta> ricette = this.ricettaService.getAllRicetteCuoco(cuoco);
      modelAndView.addObject("ricette", ricette);
      return modelAndView;
   }

   @GetMapping(value = {"/searchRicette", "/searchRicette/"})
   public ModelAndView searchRicette(@NonNull HttpServletRequest request) {
      String nomeRicetta = request.getParameter("nome");
      String usernameCuoco = request.getParameter("cuoco");
      System.out.println("ccc" + usernameCuoco.isEmpty());
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricette.html");
      Iterable<Ricetta> searchedRicette = null;
      if (nomeRicetta.isEmpty() && usernameCuoco.isEmpty()) {
         searchedRicette = this.ricettaService.getAllRicette();
      } else if (usernameCuoco.isEmpty()) {
         searchedRicette = this.ricettaService.getAllRicettaByNome(nomeRicetta);
      } else {
         Credenziali credenzialiCuoco = this.credenzialiService.getCredenziali(usernameCuoco);
         Utente utenteCuoco = this.utenteService.getUtente(credenzialiCuoco);
         Cuoco cuoco = this.cuocoService.getCuoco(utenteCuoco);
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

   @GetMapping(value = {"/update/{ricettaId}", "/update/{ricettaId}"})
   public ModelAndView showUpdateRicettaForm(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricettaForm.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      modelAndView.addObject("isUpdate", true);
      modelAndView.addObject("ricetta", ricetta);
      return modelAndView;
   }

   @PostMapping(value = {"/update/{ricettaId}", "/update/ricettaId/"})
   public ModelAndView updateRicetta(@Valid @NonNull @ModelAttribute("ricetta") Ricetta ricetta,
                                     @NonNull BindingResult ricettaBindingResult,
                                     @NonNull @RequestParam("immagini-ricetta") MultipartFile[] immaginiRicetta,
                                     @RequestParam("cuoco-ricetta") Long cuocoId,
                                     @PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricettaForm.html");
      if (!ricettaBindingResult.hasErrors()) {
         ricetta.getImmagini().clear();
         Utils.deleteRicettaImmagini(ricetta);
         for(Integer i = 0; i < immaginiRicetta.length; i++) {
            ricetta.getImmagini().add(Utils.getRicettaRelativePathImmagineDirectoryName(this.ricettaService.getRicetteCount() - 1) + Utils.getRicettaImmagineFileName(ricetta, i));
         }
         if(cuocoId != null) {
            Cuoco cuoco = this.cuocoService.getCuoco(cuocoId);
            ricetta.setCuoco(cuoco);
         }
         Ricetta updatedRicetta = this.ricettaService.updateRicetta(ricettaId, ricetta);
         if (updatedRicetta != null) {
            final Integer numeroImmagini = immaginiRicetta.length;
            for(Integer i = 0; i < numeroImmagini; i++) {
               Utils.storeRicettaImmagine(updatedRicetta, immaginiRicetta[i], i);
            }
            modelAndView.setViewName("redirect:/ricette/" + updatedRicetta.getId());
            modelAndView.addObject("isRicettaUpdated", true);
         }
      } else {
         List<ObjectError> ricettaErrors = ricettaBindingResult.getAllErrors();
         for (ObjectError ricettaError : ricettaErrors) {
            System.out.println(ricettaError.getObjectName() + " " + ricettaError.getCode() + " " + ricettaError.getDefaultMessage());
            modelAndView.addObject(Objects.requireNonNull(ricettaError.getCode()), ricettaError.getDefaultMessage());
         }
      }
      return modelAndView;
   }


}
