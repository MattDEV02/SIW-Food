package com.siw.siwfood.helpers.utente;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.model.Utente;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utils {
   public final static String UTENTE_FOTOGRAFIE_DIRECTORY = "/utenti";
   public final static String UTENTE_FOTOGRAFIE_EXTENSION = ".jpeg";

   public static @NonNull String getUtenteRelativePathFotografiaDirectoryName(@NonNull Utente utente) {
      return Utils.UTENTE_FOTOGRAFIE_DIRECTORY + '/' + utente.getId().toString();
   }

   public static @NonNull String getUtenteFotografiaFileName(@NonNull Utente utente) {
      return utente.getNome().toLowerCase() + Utils.UTENTE_FOTOGRAFIE_EXTENSION;
   }

   public static void storeUtenteFotografia(Utente utente, @NonNull MultipartFile fotografia) {
      if (!fotografia.isEmpty()) {
         try {
            String destinationDirectory = ProjectPaths.getStaticPath() + utente.getFotografia().replace(utente.getCredenziali().getUsername().toLowerCase() + Utils.UTENTE_FOTOGRAFIE_EXTENSION, "");
            File directory = new File(destinationDirectory);
            if (directory.mkdir()) {
               File file = new File(destinationDirectory + utente.getNome().toLowerCase() + Utils.UTENTE_FOTOGRAFIE_EXTENSION);
               fotografia.transferTo(file);
            } else {
               System.err.println("Directory per il nuovo Utente inserito (id = " + utente.getId().toString() + ")" + " NON creata, il file NON puo essere storicizzato.");
            }
         } catch (IOException iOException) {
            iOException.printStackTrace();
         }
      } else {
         System.err.println("Il file " + fotografia.getName() + " è vuoto e NON puo essere storicizzato.");
      }
   }
}
