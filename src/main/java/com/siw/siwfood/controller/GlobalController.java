package com.siw.siwfood.controller;

import com.siw.siwfood.helpers.constants.GlobalValues;
import com.siw.siwfood.helpers.credenziali.Roles;
import com.siw.siwfood.helpers.credenziali.Utils;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.CredenzialiService;
import com.siw.siwfood.service.UtenteService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalController {
   private static final Map<String, Object> GLOBAL_CONSTANTS_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> FIELD_SIZES_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> TEMPORALS_MAP = new HashMap<String, Object>();
   private static final Map<String, Object> API_PREFIXES_MAP = new HashMap<String, Object>();
   private static final @NotNull Map<String, Roles> ALL_ROLES_MAP = Utils.getAllRoles();

   static {
      GlobalValues.fillGlobalMap(GlobalValues.class, GlobalController.GLOBAL_CONSTANTS_MAP);
   }

   static {
     // GlobalValues.fillGlobalMap(FieldSizes.class, GlobalController.FIELD_SIZES_MAP);
   }

   static {
      //GlobalValues.fillGlobalMap(Temporals.class, GlobalController.TEMPORALS_MAP);
   }

   static {
      //GlobalValues.fillGlobalMap(APIPrefixes.class, GlobalController.API_PREFIXES_MAP);
   }

   @Autowired
   private UtenteService utenteService;
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

   @ModelAttribute("API_PREFIXES_MAP")
   public Map<String, Object> getApiPrefixesMap() {
      return GlobalController.API_PREFIXES_MAP;
   }

   @ModelAttribute("ALL_ROLES_MAP")
   public @NotNull Map<String, Roles> getAllRolesMap() {
      return GlobalController.ALL_ROLES_MAP;
   }

   @ModelAttribute("loggedUser")
   public Utente getLoggedUser(Model model) {
      UserDetails userDetails = null;
      Credenziali credenziali = null;
      Utente loggedUser = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (Utils.userIsLoggedIn(authentication)) {
         userDetails = (UserDetails) authentication.getPrincipal();
         System.out.println(userDetails);
         credenziali = this.credenzialiService.getCredenziali(userDetails.getUsername());
         System.out.println(credenziali);
         loggedUser = this.utenteService.getUtente(credenziali);
         System.out.println(loggedUser);
         model.addAttribute("loggedUser", loggedUser);
      }
      return loggedUser;
   }

   @ModelAttribute("searchedCredenziali")
   public Credenziali getSearchedCredenziali(@NotNull Model model) {
      Credenziali searchedCredenziali = new Credenziali();
      model.addAttribute("searchedCredenziali", searchedCredenziali);
      return searchedCredenziali;
   }



}
