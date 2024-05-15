package com.siw.siwfood.controller;

import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "/dashboard/cuochi")
public class CuochiController {
   @Autowired
   private UtenteService utenteService;

   @GetMapping(value = {"", "/"})
   public ModelAndView showAllCuochi() {
      ModelAndView modelAndView = new ModelAndView("dashboard/cuochi/cuochi.html");
      modelAndView.addObject("cuochi", this.utenteService.getAllCuochi());
      return modelAndView;
   }

   @GetMapping(value = {"/register", "/register/"})
   public ModelAndView showCuocoForm() {
      ModelAndView modelAndView = new ModelAndView("dashboard/cuochi/cuochiForm.html");
      modelAndView.addObject("cuoco", new Utente());
      modelAndView.addObject("credenziali", new Credenziali());
      return modelAndView;
   }

   @PostMapping(value = {"/register", "/register/"})
   public ModelAndView registerCuoco() {
      ModelAndView modelAndView = new ModelAndView("dashboard/cuochi/cuochiForm.html");

      modelAndView.setViewName("redirect:/dashboard/cuochi/" + "id");
      return modelAndView;
   }

   @GetMapping(value = {"/{cuocoId}", "/{cuocoId}/"})
   public ModelAndView showCuoco(@PathVariable("cuocoId") Long cuocoId) {
      ModelAndView modelAndView = new ModelAndView("dashboard/cuochi/cuoco.html");
      Utente cuoco = this.utenteService.getCuoco(cuocoId);
      modelAndView.addObject("cuoco", cuoco);
      //List<Ricetta> ricette = cuoco.getRicette();
      modelAndView.addObject("ricette", Collections.emptyList());
      return modelAndView;
   }

   @GetMapping(value = {"/delete/{cuocoId}", "/delete/{cuocoId}"})
   public ModelAndView deleteCuoco(@PathVariable("cuocoId") Long cuocoId) {
      ModelAndView modelAndView = new ModelAndView("redirect:/dashboard/cuochi");
      this.utenteService.deleteCuoco(cuocoId);
      return modelAndView;
   }
}
