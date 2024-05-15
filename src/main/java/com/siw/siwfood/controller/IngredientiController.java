package com.siw.siwfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/dashboard/ingredienti")
public class IngredientiController {
   @GetMapping(value = {"", "/"})
   public ModelAndView showAllIngredienti() {
      ModelAndView modelAndView = new ModelAndView("dashboard/ingredienti.html");
      return modelAndView;
   }

   @PostMapping(value = {"/register", "/register/"})
   public ModelAndView registerIngrediente() {
      ModelAndView modelAndView = new ModelAndView("dashboard/ingredienti.html");
      return modelAndView;
   }

   @DeleteMapping(value = {"/delete", "/delete/"})
   public ModelAndView deleteIngrediente() {
      ModelAndView modelAndView = new ModelAndView("dashboard/ingredienti.html");
      return modelAndView;
   }

   @PutMapping(value = {"/modify", "/modify/"})
   public ModelAndView modifyIngrediente() {
      ModelAndView modelAndView = new ModelAndView("dashboard/ingredienti.html");
      return modelAndView;
   }
}
