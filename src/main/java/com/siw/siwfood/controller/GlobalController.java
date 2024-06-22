package com.siw.siwfood.controller;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.helpers.constants.GlobalValues;
import com.siw.siwfood.helpers.constants.Temporals;
import com.siw.siwfood.helpers.credenziali.Utils;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.CredenzialiService;
import com.siw.siwfood.service.CuocoService;
import com.siw.siwfood.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalController {
   private static final Map<String, Object> GLOBAL_CONSTANTS_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> FIELD_SIZES_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> TEMPORALS_MAP = new HashMap<String, Object>();

   static {
      GlobalValues.fillGlobalMap(GlobalValues.class, GlobalController.GLOBAL_CONSTANTS_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(FieldSizes.class, GlobalController.FIELD_SIZES_MAP);
   }

   static {
      GlobalValues.fillGlobalMap(Temporals.class, GlobalController.TEMPORALS_MAP);
   }


   @Autowired
   private UtenteService utenteService;
   @Autowired
   private CuocoService cuocoService;
   @Autowired
   private CredenzialiService credenzialiService;

   @ModelAttribute("GLOBAL_CONSTANTS_MAP")
   public Map<String, Object> getGlobalConstantsMap() {
      return GlobalController.GLOBAL_CONSTANTS_MAP;
   }

   @ModelAttribute("FIELD_SIZES_MAP")
   public Map<String, Object> getFieldSizesMap() {
      return GlobalController.FIELD_SIZES_MAP;
   }

   @ModelAttribute("TEMPORALS_MAP")
   public Map<String, Object> getTemporalsMap() {
      return GlobalController.TEMPORALS_MAP;
   }

   @ModelAttribute("loggedUser")
   public Utente getLoggedUser() {
      Utente loggedUser = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (Utils.userIsLoggedIn(authentication)) {
         UserDetails userDetails = (UserDetails) (authentication.getPrincipal());
         Credenziali credenziali = this.credenzialiService.getCredenziali(userDetails.getUsername());
         loggedUser = this.utenteService.getUtente(credenziali);
      }
      return loggedUser;
   }

   @ModelAttribute("cuochiSelect")
   public Iterable<Cuoco> getCuochiSelect() {
      Iterable<Cuoco> cuochiSelect = this.cuocoService.getAllCuochi();
      return cuochiSelect;
   }

}
