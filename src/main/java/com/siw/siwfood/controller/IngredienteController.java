package com.siw.siwfood.controller;

import com.siw.siwfood.model.Ingrediente;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.repository.RicettaRepository;
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

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value = "/ingredienti")
public class IngredienteController {
   @Autowired
   private IngredienteService ingredienteService;
   @Autowired
   private RicettaService ricettaService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showAllIngredienti() {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienti.html");
      modelAndView.addObject("ingredienti", this.ingredienteService.getAllIngredienti());
      return modelAndView;
   }

   @GetMapping(value = {"/register/{ricettaId}", "/register/{ricettaId}/"})
   public ModelAndView registerIngrediente(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienteForm.html");
      modelAndView.addObject("ingrediente", new Ingrediente());
      modelAndView.addObject("ricetta", this.ricettaService.getRicetta(ricettaId));
      modelAndView.addObject("isUpdate", false);
      return modelAndView;
   }

   @PostMapping(value = {"/register/{ricettaId}", "/register/{ricettaId}"})
   public ModelAndView registerIngrediente(
           @Valid @NonNull @ModelAttribute("ingrediente") Ingrediente ingrediente,
           @NonNull BindingResult ingredienteBindingResult,
           @PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingrediente.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(!ingredienteBindingResult.hasErrors() && ricetta != null) {
         Ingrediente savedIngrediente = this.ricettaService.makeIngrediente(ricetta, ingrediente);
         modelAndView.setViewName("redirect:/ingredienti/" + ricetta.getId() + "/" + savedIngrediente.getId());
         modelAndView.addObject("isIngredienteRegistered", true);
      } else {
         List<ObjectError> ingredientiErrors = ingredienteBindingResult.getAllErrors();
         for (ObjectError ingredientiError : ingredientiErrors) {
            System.out.println(ingredientiError.getObjectName() + " " + ingredientiError.getCode() + " " + ingredientiError.getDefaultMessage());
            modelAndView.addObject(Objects.requireNonNull(ingredientiError.getCode()), ingredientiError.getDefaultMessage());
         }
         modelAndView.addObject("ricetta", ricetta);
         modelAndView.addObject("isUpdate", false);
      }
      return modelAndView;
   }

   @GetMapping(value = {"/{ricettaId}", "/{ricettaId}/"})
   public ModelAndView showIngrediente(@PathVariable("ricettaId") Long ricettaId) {
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

   @GetMapping(value = {"/{ricettaId}/{ingredienteId}", "/{ricettaId}/{ingredienteId}/"})
   public ModelAndView showIngrediente(@PathVariable("ricettaId") Long ricettaId, @PathVariable("ingredienteId") Long ingredienteId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingrediente.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      Ingrediente ingrediente = this.ricettaService.findIngrediente(ricetta, ingredienteId);
      modelAndView.addObject("ricetta", ricetta);
      modelAndView.addObject("ingrediente", ingrediente);
      return modelAndView;
   }

   @GetMapping(value = {"/delete/{ricettaId}/{ingredienteId}", "/delete/{ricettaId}/{ingredienteId}"})
   public ModelAndView deleteIngrediente(@PathVariable("ricettaId") Long ricettaId, @PathVariable("ingredienteId") Long ingredienteId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/ingredienti/" + ricettaId);
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      Ingrediente ingrediente = this.ricettaService.findIngrediente(ricetta, ingredienteId);
      this.ricettaService.destroyIngrediente(ricetta, ingrediente);
      modelAndView.addObject("isIngredienteDeleted", true);
      return modelAndView;
   }

   @GetMapping(value = {"/update/{ricettaId}/{ingredienteId}", "/update/{ricettaId}/{ingredienteId}/"})
   public ModelAndView updateIngrediente(@PathVariable("ricettaId") Long ricettaId,
                                         @PathVariable("ingredienteId") Long ingredienteId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienteForm.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      Ingrediente ingrediente = this.ricettaService.findIngrediente(ricetta, ingredienteId);
      modelAndView.addObject("ingrediente", ingrediente);
      modelAndView.addObject("ricetta", ricetta);
      modelAndView.addObject("isUpdate", true);
      return modelAndView;
   }

   @PostMapping(value = {"/update/{ricettaId}/{ingredienteId}", "/update/{ricettaId}/{ingredienteId}/"})
   public ModelAndView updateIngrediente(
           @Valid @NonNull @ModelAttribute("ingrediente") Ingrediente ingrediente,
           @NonNull BindingResult ingredienteBindingResult,
           @PathVariable("ingredienteId") Long ingredienteId,
           @PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingrediente.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      if(!ingredienteBindingResult.hasErrors() && ricetta != null) {
         this.ricettaService.updateIngrediente(ricetta, ingredienteId, ingrediente);
         modelAndView.setViewName("redirect:/ingredienti/" + ricetta.getId() );
         modelAndView.addObject("isIngredienteUpdated", true);
      } else {
         List<ObjectError> ingredientiErrors = ingredienteBindingResult.getAllErrors();
         for (ObjectError ingredientiError : ingredientiErrors) {
            modelAndView.addObject(Objects.requireNonNull(ingredientiError.getCode()), ingredientiError.getDefaultMessage());
         }
      }
      return modelAndView;
   }
}
