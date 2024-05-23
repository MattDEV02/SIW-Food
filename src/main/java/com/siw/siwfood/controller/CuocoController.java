package com.siw.siwfood.controller;

import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.CuocoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/cuochi")
public class CuocoController {
   @Autowired
   private CuocoService cuocoService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showAllCuochi() {
      ModelAndView modelAndView = new ModelAndView("food/cuochi/cuochi.html");
      modelAndView.addObject("cuochi", this.cuocoService.getAllCuochi());
      return modelAndView;
   }

   @GetMapping(value = {"/register", "/register/"})
   public ModelAndView showCuocoForm() {
      ModelAndView modelAndView = new ModelAndView("food/cuochi/cuochiForm.html");
      modelAndView.addObject("cuoco", new Utente());
      modelAndView.addObject("credenziali", new Credenziali());
      return modelAndView;
   }

   @PostMapping(value = {"/register", "/register/"})
   public ModelAndView registerCuoco() {
      ModelAndView modelAndView = new ModelAndView("food/cuochi/cuochiForm.html");
      modelAndView.setViewName("redirect:/cuochi/" + "id");
      return modelAndView;
   }

   @GetMapping(value = {"/{cuocoId}", "/{cuocoId}/"})
   public ModelAndView showCuoco(@PathVariable("cuocoId") Long cuocoId) {
      ModelAndView modelAndView = new ModelAndView("food/cuochi/cuoco.html");
      Cuoco cuoco = this.cuocoService.getCuoco(cuocoId);
      modelAndView.addObject("cuoco", cuoco);
      return modelAndView;
   }

   @GetMapping(value = {"/delete/{cuocoId}", "/delete/{cuocoId}"})
   public ModelAndView deleteCuoco(@PathVariable("cuocoId") Long cuocoId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/cuochi");
      this.cuocoService.deleteCuoco(cuocoId);
      modelAndView.addObject("isCuocoDeleted", true);
      return modelAndView;
   }
}
