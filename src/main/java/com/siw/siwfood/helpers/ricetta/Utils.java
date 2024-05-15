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

   public static @NonNull String getRicettaRelativePathImmagineDirectoryName(@NonNull Ricetta ricetta) {
      return ProjectPaths.IMAGES + Utils.RICETTA_IMMAGINI_DIRECTORY + "/" + ricetta.getId().toString() + "/";
   }

   public static @NonNull String getRicettaImmagineFileName(@NonNull Ricetta ricetta, @NotNull Integer index) {
      return ricetta.getNome().toLowerCase() + "_" + (index + 1) + Utils.RICETTA_IMMAGINI_EXTENSION;
   }

   public static void storeRicettaImmagine(Ricetta ricetta, @NonNull MultipartFile immagine, Integer index) {
      if (!immagine.isEmpty()) {
         try {
            String relativeDir = Utils.getRicettaRelativePathImmagineDirectoryName(ricetta);
            String destinationDirectory = ProjectPaths.getStaticPath() + relativeDir;
            File directory = new File(destinationDirectory);
            if(!directory.exists()) {
               directory.mkdir();
            }
            String fileName = Utils.getRicettaImmagineFileName(ricetta, index);
            ricetta.getImmagini().add(relativeDir + fileName);
            System.out.println(ricetta.getImmagini());
            File file = new File(destinationDirectory + fileName);
            immagine.transferTo(file);
         } catch (IOException iOException) {
            iOException.printStackTrace();
         }
      } else {
         System.err.println("Il file " + immagine.getName() + " è vuoto e NON puo essere storicizzato.");
      }
   }
}
