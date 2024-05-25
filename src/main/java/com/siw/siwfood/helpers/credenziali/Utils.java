package com.siw.siwfood.helpers.credenziali;

import com.siw.siwfood.model.Utente;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;


public class Utils {
   public static boolean userIsLoggedIn(Authentication authentication) {
      return !(authentication instanceof AnonymousAuthenticationToken);
   }

   public static @NotNull Boolean isCuoco(Utente utente) {
      return utente != null && utente.getCredenziali().getRole().contains(Roles.REGISTRATO.toString());
   }
}

