package com.siw.siwfood.controller;

import com.siw.siwfood.model.Cuoco;
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

   @GetMapping(value ="")
   public ModelAndView showAllCuochi() {
      ModelAndView modelAndView = new ModelAndView("food/cuochi/cuochi.html");
      modelAndView.addObject("cuochi", this.cuocoService.getAllCuochi());
      return modelAndView;
   }

   @GetMapping(value ="/cuoco/{cuocoId}")
   public ModelAndView showCuoco(@PathVariable("cuocoId") Long cuocoId) {
      ModelAndView modelAndView = new ModelAndView("food/cuochi/cuoco.html");
      Cuoco cuoco = this.cuocoService.getCuoco(cuocoId);
      modelAndView.addObject("cuoco", cuoco);
      return modelAndView;
   }

   @GetMapping(value ="/delete/cuoco/{cuocoId}")
   public ModelAndView deleteCuoco(@PathVariable("cuocoId") Long cuocoId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/cuochi");
      Cuoco cuoco = this.cuocoService.getCuoco(cuocoId);
      if(cuoco != null) {
         this.cuocoService.deleteCuoco(cuoco.getId());
         modelAndView.addObject("isCuocoDeleted", true);
      } else {
         modelAndView.setViewName("redirect:/cuochi");
         modelAndView.addObject("cuocoNotFound", true);
      }
      return modelAndView;
   }
}
