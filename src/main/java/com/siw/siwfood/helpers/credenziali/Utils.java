package com.siw.siwfood.helpers.credenziali;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

public class Utils {
   public static boolean userIsLoggedIn(Authentication authentication) {
      return !(authentication instanceof AnonymousAuthenticationToken);
   }

   public static @NotNull Map<String, Roles> getAllRoles() {
      Map<String, Roles> result = new HashMap<String, Roles>();
      Roles[] allRoles = Roles.values();
      for (Roles role : allRoles) {
         result.put(role.name().replace("_ROLE", ""), role);
      }
      return result;
   }
}
