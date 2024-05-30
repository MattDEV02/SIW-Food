package com.siw.siwfood.controller;

import com.siw.siwfood.controller.validator.IngredienteValidator;
import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.IngredienteService;
import com.siw.siwfood.service.RicettaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.siw.siwfood.helpers.credenziali.Utils;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/ingredienti")
public class IngredienteController {
   @Autowired
   private IngredienteService ingredienteService;
   @Autowired
   private RicettaService ricettaService;
   @Autowired
   private IngredienteValidator ingredienteValidator;

   @GetMapping(value ="")
   public ModelAndView showAllIngredienti() {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienti.html");
      modelAndView.addObject("ingredienti", this.ingredienteService.getAllIngredienti());
      return modelAndView;
   }

   @GetMapping(value ="/register/ricetta/{ricettaId}")
   public ModelAndView showRegisterIngredienteForm(@PathVariable("ricettaId") Long ricettaId,  @Valid @ModelAttribute("loggedUser") @NonNull Utente loggedUser) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienteForm.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(ricetta != null) {
         if(Utils.utenteIsCuoco(loggedUser) && !ricetta.getCuoco().getUtente().equals(loggedUser)) {
            modelAndView.setViewName("redirect:/ricette");
            modelAndView.addObject("ricettaNonTua", true);
         } else {
            modelAndView.addObject("ingrediente", new Ingrediente());
            modelAndView.addObject("ricetta", ricetta);
            modelAndView.addObject("isUpdate", false);
         }
      } else {
         modelAndView.setViewName("redirect:/ricette");
         modelAndView.addObject("ricettaNotFound", true);
      }
      return modelAndView;
   }

   @PostMapping(value ="/register/ricetta/{ricettaId}")
   public ModelAndView registerIngrediente(
           @Valid @NonNull @ModelAttribute("ingrediente") Ingrediente ingrediente,
           @NonNull BindingResult ingredienteBindingResult,
           @PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienteForm.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(ricetta != null) {
         this.ingredienteValidator.setRicetta(ricetta);
         this.ingredienteValidator.validate(ingrediente, ingredienteBindingResult);
         if(!ingredienteBindingResult.hasErrors()) {
            this.ricettaService.makeIngrediente(ricetta, ingrediente);
            modelAndView.setViewName("redirect:/ingredienti/ricetta/" + ricetta.getId());
            modelAndView.addObject("isIngredienteRegistered", true);
         } else {
            List<ObjectError> ingredientiErrors = ingredienteBindingResult.getAllErrors();
            for (ObjectError ingredientiError : ingredientiErrors) {
               modelAndView.addObject(Objects.requireNonNull(ingredientiError.getCode()), ingredientiError.getDefaultMessage());
            }
            modelAndView.addObject("ricetta", ricetta);
            modelAndView.addObject("isUpdate", false);
         }
      }
      return modelAndView;
   }

   @GetMapping(value ="/ricetta/{ricettaId}")
   public ModelAndView showIngredientiRicetta(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienti.html");
      List<Ingrediente> ingredienti = null;
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(ricetta != null) {
         modelAndView.addObject("nomeRicetta", ricetta.getNome());
         ingredienti = ricetta.getIngredienti();
      }
      modelAndView.addObject("ingredienti", ingredienti);
      return modelAndView;
   }

   @GetMapping(value ="/ricetta/{ricettaId}/ingrediente/{ingredienteId}")
   public ModelAndView showIngrediente(@PathVariable("ricettaId") Long ricettaId, @PathVariable("ingredienteId") Long ingredienteId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingrediente.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(ricetta != null) {
         Ingrediente ingrediente = this.ricettaService.findIngrediente(ricetta, ingredienteId);
         modelAndView.addObject("ricetta", ricetta);
         modelAndView.addObject("ingrediente", ingrediente);
      } else {
         modelAndView.setViewName("redirect:/ingredienti" );
         modelAndView.addObject("ingredienteNotFound", true);
      }
      return modelAndView;
   }

   @GetMapping(value ="/delete/ricetta/{ricettaId}/ingrediente/{ingredienteId}")
   public ModelAndView deleteIngrediente(@PathVariable("ricettaId") Long ricettaId, @PathVariable("ingredienteId") Long ingredienteId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/ingredienti/ricetta/" + ricettaId);
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(ricetta != null) {
         Ingrediente ingrediente = this.ricettaService.findIngrediente(ricetta, ingredienteId);
         this.ricettaService.destroyIngrediente(ricetta, ingrediente);
         modelAndView.addObject("isIngredienteDeleted", true);
      } else {
         modelAndView.setViewName("redirect:/ingredienti" );
         modelAndView.addObject("ingredienteNotFound", true);
      }
      return modelAndView;
   }

   @GetMapping(value ="/update/ricetta/{ricettaId}/ingrediente/{ingredienteId}")
   public ModelAndView showUpdateIngredienteForm(
            @PathVariable("ricettaId") Long ricettaId,
            @PathVariable("ingredienteId") Long ingredienteId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienteForm.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(ricetta != null) {
         Ingrediente ingrediente = this.ricettaService.findIngrediente(ricetta, ingredienteId);
         modelAndView.addObject("ingrediente", ingrediente);
         modelAndView.addObject("ricetta", ricetta);
         modelAndView.addObject("isUpdate", true);
      } else {
         modelAndView.setViewName("redirect:/ingredienti" );
         modelAndView.addObject("ingredienteNotFound", true);
      }
      return modelAndView;
   }

   @PostMapping(value ="/update/ricetta/{ricettaId}/ingrediente/{ingredienteId}")
   public ModelAndView updateIngrediente(
           @Valid @NonNull @ModelAttribute("ingrediente") Ingrediente ingrediente,
           @NonNull BindingResult ingredienteBindingResult,
           @PathVariable("ingredienteId") Long ingredienteId,
           @PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienteForm.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(ricetta != null) {
         this.ingredienteValidator.setRicetta(ricetta);
         this.ingredienteValidator.validate(ingrediente, ingredienteBindingResult);
         if(!ingredienteBindingResult.hasErrors()) {
            this.ricettaService.updateIngrediente(ricetta, ingredienteId, ingrediente);
            modelAndView.setViewName("redirect:/ingredienti/ricetta/" + ricetta.getId() );
            modelAndView.addObject("isIngredienteUpdated", true);
         } else {
            List<ObjectError> ingredientiErrors = ingredienteBindingResult.getAllErrors();
            for (ObjectError ingredientiError : ingredientiErrors) {
               modelAndView.addObject(Objects.requireNonNull(ingredientiError.getCode()), ingredientiError.getDefaultMessage());
            }
            modelAndView.addObject("ingrediente", this.ricettaService.findIngrediente(ricetta, ingredienteId));
            modelAndView.addObject("ricetta", ricetta);
            modelAndView.addObject("isUpdate", true);
         }
      }
      return modelAndView;
   }
}
