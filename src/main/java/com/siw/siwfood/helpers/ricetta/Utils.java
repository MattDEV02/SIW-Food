package com.siw.siwfood.helpers.ricetta;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.model.Ricetta;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utils {
   public final static String RICETTA_IMMAGINI_DIRECTORY = "/ricette";
   public final static String RICETTA_IMMAGINI_EXTENSION = ".jpeg";

   public static @NonNull String getRicettaRelativePathImmagineDirectoryName(@NonNull Long count) {
      return ProjectPaths.IMAGES + Utils.RICETTA_IMMAGINI_DIRECTORY + "/" + (count + 1) + "/";
   }

   public static @NonNull String getRicettaImmagineFileName(@NonNull Ricetta ricetta, @NotNull Integer index) {
      return ricetta.getNome().toLowerCase() + "_" + (index + 1) + Utils.RICETTA_IMMAGINI_EXTENSION;
   }

   public static void storeRicettaImmagine(Ricetta ricetta, @NonNull MultipartFile immagine, Integer index) {
      if (!immagine.isEmpty()) {
         try {
            // /images/ricette/0/boh_1.jpeg
            System.out.println(ricetta.getImmagini());
            System.out.println(ricetta.getNome());
            String immagineCorrente = ricetta.getImmagini().get(index);
            String relativeDir = immagineCorrente.substring(0, immagineCorrente.indexOf(ricetta.getNome().toLowerCase()));
            System.out.println(relativeDir);
            String destinationDirectory = ProjectPaths.getStaticPath() + relativeDir;
            File directory = new File(destinationDirectory);
            if(!directory.exists()) {
               directory.mkdir();
            }
            System.out.println(ProjectPaths.getStaticPath() + immagineCorrente);
            File file = new File(ProjectPaths.getStaticPath() + immagineCorrente);
            immagine.transferTo(file);
         } catch (IOException iOException) {
            iOException.printStackTrace();
         }
      } else {
         System.err.println("Il file " + immagine.getName() + " è vuoto e NON puo essere storicizzato.");
      }
   }
}
