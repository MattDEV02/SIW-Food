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
   @Autowired
   private RicettaRepository ricettaRepository;

   @GetMapping(value = {"", "/"})
   public ModelAndView showAllIngredienti() {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienti.html");
      modelAndView.addObject("ingredienti", this.ingredienteService.getAllIngredienti());
      return modelAndView;
   }

   @GetMapping(value = {"/{ricettaId}/register", "/{ricettaId}/register/"})
   public ModelAndView registerIngrediente(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienteForm.html");
      modelAndView.addObject("ingrediente", new Ingrediente());
      modelAndView.addObject("ricetta", this.ricettaService.getRicetta(ricettaId));
      return modelAndView;
   }

   @PostMapping(value = {"/{ricettaId}/register", "/{ricettaId}/register/"})
   public ModelAndView registerIngrediente(
           @Valid @NonNull @ModelAttribute("ingrediente") Ingrediente ingrediente,
           @NonNull BindingResult ingredienteBindingResult,
           @PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingrediente.html");
      Ricetta ricetta = this.ricettaRepository.findById(ricettaId).orElse(null);
      if(!ingredienteBindingResult.hasErrors() && ricetta != null) {
         Ingrediente savedIngrediente = this.ricettaService.makeIngrediente(ricetta, ingrediente);
         modelAndView.setViewName("redirect:/ingredienti/" + ricetta.getId() + "/" + savedIngrediente.getId());
         modelAndView.addObject("isIngredienteRegistered", true);
      } else {
         List<ObjectError> ingredientiGlobalErrors = ingredienteBindingResult.getAllErrors();
         for (ObjectError ingredientiGlobalError : ingredientiGlobalErrors) {
            System.out.println(ingredientiGlobalError.getObjectName() + " " + ingredientiGlobalError.getCode() + " " + ingredientiGlobalError.getDefaultMessage());
            modelAndView.addObject(Objects.requireNonNull(ingredientiGlobalError.getCode()), ingredientiGlobalError.getDefaultMessage());
         }
         modelAndView.addObject("ricetta", ricetta);
      }
      return modelAndView;
   }

   @GetMapping(value = {"/{ricettaId}", "/{ricettaId}/"})
   public ModelAndView showIngrediente(@PathVariable("ricettaId") Long ricettaId) {
      ModelAndView modelAndView = new ModelAndView("food/ingredienti/ingredienti.html");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      List<Ingrediente> ingredienti = ricetta.getIngredienti();
      modelAndView.addObject("ricetta", ricetta);
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
      ModelAndView modelAndView = new ModelAndView("redirect:/ingredienti");
      Ricetta ricetta = this.ricettaService.getRicetta(ricettaId);
      Ingrediente ingrediente = this.ricettaService.findIngrediente(ricetta, ingredienteId);
      this.ricettaService.destroyIngrediente(ricetta, ingrediente);
      modelAndView.addObject("isIngredienteDeleted", true);
      return modelAndView;
   }

}
