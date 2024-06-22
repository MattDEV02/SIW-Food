package com.siw.siwfood.controller;

import com.siw.siwfood.controller.validator.RicettaValidator;
import com.siw.siwfood.helpers.ricetta.RicettaImmaginiFileUtils;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.CredenzialiService;
import com.siw.siwfood.service.CuocoService;
import com.siw.siwfood.service.RicettaService;
import com.siw.siwfood.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.siw.siwfood.model.Credenziali;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.siw.siwfood.helpers.credenziali.Utils.utenteIsCuoco;

@Controller
@RequestMapping(value = "/ricette")
public class RicettaController {
   private static final Logger LOGGER = LoggerFactory.getLogger(RicettaController.class);
   @Autowired
   private RicettaService ricettaService;
   @Autowired
   private UtenteService utenteService;
   @Autowired
   private CuocoService cuocoService;
   @Autowired
   private CredenzialiService credenzialiService;
   @Autowired
   private RicettaValidator ricettaValidator;

   @GetMapping(value ="")
   public ModelAndView showAllRicette() {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricette.html");
      modelAndView.addObject("ricette", this.ricettaService.getAllRicette());
      return modelAndView;
   }

   @GetMapping(value = "/ricetta/{ricettaId}")
   public ModelAndView showRicetta(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricetta.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      modelAndView.addObject("ricetta", ricetta);
      return modelAndView;
   }

   @GetMapping(value ="/register")
   public ModelAndView showRegisterRicettaForm() {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricettaForm.html");
      modelAndView.addObject("ricetta", new Ricetta());
      modelAndView.addObject("isUpdate", false);
      return modelAndView;
   }

   @PostMapping(value = "/register")
   public ModelAndView registerRicetta(
                                    @ModelAttribute("loggedUser") @NonNull Utente loggedUser,
                                    @Valid @NonNull @ModelAttribute("ricetta") Ricetta ricetta,
                                    @NonNull BindingResult ricettaBindingResult,
                                    @NonNull @RequestParam("immagini-ricetta") MultipartFile[] immaginiRicetta) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricettaForm.html");
      Cuoco cuoco = utenteIsCuoco(loggedUser) ? this.cuocoService.getCuoco(loggedUser) : ricetta.getCuoco();
      ricetta.setCuoco(cuoco);
      this.ricettaValidator.setImmagini(immaginiRicetta);
      this.ricettaValidator.validate(ricetta, ricettaBindingResult);
      if (!ricettaBindingResult.hasErrors()) {
         final Integer numeroImmaginiRicetta = immaginiRicetta.length;
         Ricetta savedRicetta = this.ricettaService.saveRicetta(ricetta, numeroImmaginiRicetta);
         RicettaController.LOGGER.info("Registrata Ricetta con questo ID: {}", savedRicetta.getId());
         for(Integer i = 0; i < numeroImmaginiRicetta; i++) {
            RicettaImmaginiFileUtils.storeRicettaImmagine(savedRicetta, immaginiRicetta[i], i);
         }
         modelAndView.setViewName("redirect:/ricette/ricetta/" + savedRicetta.getId());
         modelAndView.addObject("isRicettaRegistered", true);
      } else {
         List<ObjectError> ricettaErrors = ricettaBindingResult.getAllErrors();
         for (ObjectError ricettaError : ricettaErrors) {
            modelAndView.addObject(Objects.requireNonNull(ricettaError.getCode()), ricettaError.getDefaultMessage());
         }
         modelAndView.addObject("isUpdate", false);
      }
      return modelAndView;
   }

   @GetMapping(value ="/delete/ricetta/{ricettaId}")
   public ModelAndView deleteRicetta(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/ricette");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(ricetta != null) {
         RicettaImmaginiFileUtils.deleteRicettaImmaginiDirectory(ricetta);
         this.ricettaService.deleteRicetta(ricetta);
         RicettaController.LOGGER.info("Cancellata Ricetta con questo ID: {}", ricetta.getId());
         modelAndView.addObject("isRicettaDeleted", true);
      } else {
         modelAndView.setViewName("redirect:/ricette");
         modelAndView.addObject("ricettaNotFound", true);
      }
      return modelAndView;
   }

   @GetMapping(value ="/cuoco/{cuocoId}")
   public ModelAndView showRicetteCuoco(@PathVariable("cuocoId") Long cuocoId) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricette.html");
      Cuoco cuoco = this.cuocoService.getCuoco(cuocoId);
      if(cuoco != null) {
         Iterable<Ricetta> ricette = this.ricettaService.getAllRicetteCuoco(cuoco);
         modelAndView.addObject("ricette", ricette);
         modelAndView.addObject("usernameCuoco", cuoco.getUtente().getCredenziali().getUsername());
      } else {
         modelAndView.setViewName("redirect:/ricette");
         modelAndView.addObject("cuocoNotFound", true);
      }
      return modelAndView;
   }

   @PostMapping(value ="/searchRicette")
   public ModelAndView searchRicette(@NonNull HttpServletRequest request) {
      String nomeRicetta = request.getParameter("nome-ricetta");
      String usernameCuoco = request.getParameter("username-cuoco-ricetta");
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
         searchedRicette = nomeRicetta.isEmpty() ? this.ricettaService.getAllRicetteCuoco(cuoco) : this.ricettaService.getAllRicettaCuocoByNome(cuoco, nomeRicetta);
      }
      modelAndView.addObject("ricette", searchedRicette);
      modelAndView.addObject("hasSearchedRicette", true);
      return modelAndView;
   }

   @GetMapping(value ="/update/ricetta/{ricettaId}")
   public ModelAndView showUpdateRicettaForm(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricettaForm.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(ricetta != null) {
         modelAndView.addObject("isUpdate", true);
         modelAndView.addObject("ricetta", ricetta);
      } else {
         modelAndView.setViewName("redirect:/ricette");
         modelAndView.addObject("ricettaNotFound", true);
      }
      return modelAndView;
   }

   @PostMapping(value ="/update/ricetta/{ricettaId}")
   public ModelAndView updateRicetta(
                                     @ModelAttribute("loggedUser") @NonNull Utente loggedUser,
                                     @Valid @NonNull @ModelAttribute("ricetta") Ricetta ricetta,
                                     @NonNull BindingResult ricettaBindingResult,
                                     @RequestParam(name = "immagini-ricetta", required = false) MultipartFile[] immaginiRicetta,
                                     @PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ricette/ricettaForm.html");
      Cuoco cuoco = utenteIsCuoco(loggedUser) ? this.cuocoService.getCuoco(loggedUser) : ricetta.getCuoco();
      ricetta.setCuoco(cuoco);
      this.ricettaValidator.setIsUpdate(true);
      this.ricettaValidator.validate(ricetta, ricettaBindingResult);
      if (!ricettaBindingResult.hasErrors()) {
         Ricetta ricettaToUpdate = this.ricettaService.getRicetta(ricettaId);
         ricetta.setIngredienti(ricettaToUpdate.getIngredienti());
         MultipartFile[] notEmptyImmaginiRicetta = Arrays.stream(immaginiRicetta).filter(immagineRicetta -> !immagineRicetta.isEmpty()).toArray(MultipartFile[]::new);
         final Integer numeroNotEmptyImmaginiRicetta = notEmptyImmaginiRicetta.length;
         Ricetta updatedRicetta = this.ricettaService.updateRicetta(ricettaToUpdate, ricetta, numeroNotEmptyImmaginiRicetta);
         RicettaController.LOGGER.info("Aggiornata Ricetta con questo ID: {}", updatedRicetta.getId());
         for(Integer i = 0; i < numeroNotEmptyImmaginiRicetta; i++) {
            RicettaImmaginiFileUtils.storeRicettaImmagine(updatedRicetta, notEmptyImmaginiRicetta[i], i);
         }
         modelAndView.setViewName("redirect:/ricette/ricetta/" + updatedRicetta.getId());
         modelAndView.addObject("isRicettaUpdated", true);
      } else {
         ricetta.setId(ricettaId);
         modelAndView.addObject("ricetta", ricetta);
         modelAndView.addObject("isUpdate", true);
         List<ObjectError> ricettaErrors = ricettaBindingResult.getAllErrors();
         for (ObjectError ricettaError : ricettaErrors) {
            modelAndView.addObject(Objects.requireNonNull(ricettaError.getCode()), ricettaError.getDefaultMessage());
         }
      }
      return modelAndView;
   }
}
