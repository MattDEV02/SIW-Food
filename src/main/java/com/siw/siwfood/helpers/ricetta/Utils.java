package com.siw.siwfood.helpers.ricetta;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.model.Ricetta;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            //  /images/ricette/{ricettaCount}/{ricettaName}.jpeg
            System.out.println(ricetta.getImmagini());
            String immagineCorrente = ricetta.getImmagini().get(index);
            String relativeDir = immagineCorrente.substring(0, immagineCorrente.indexOf(ricetta.getNome().toLowerCase()));
            String destinationDirectory = ProjectPaths.getStaticPath() + relativeDir;
            File directory = new File(destinationDirectory);
            if(!directory.exists()) {
               directory.mkdir();
            }
            File file = new File(ProjectPaths.getStaticPath() + immagineCorrente);
            immagine.transferTo(file);
         } catch (IOException iOException) {
            iOException.printStackTrace();
         }
      } else {
         System.err.println("Il file " + immagine.getName() + " è vuoto e NON puo essere storicizzato.");
      }
   }

   public static void deleteRicettaImmaginiDirectory(@NotNull Ricetta ricetta) {
      String immagineCorrente = ricetta.getImmagini().get(0);
      String relativeDir = immagineCorrente.substring(0, immagineCorrente.indexOf(ricetta.getNome().toLowerCase()));
      File directory = new File(ProjectPaths.getStaticPath() + relativeDir);
      try {
         FileUtils.deleteDirectory(directory);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void deleteRicettaImmagini(@NotNull Ricetta ricetta) {
      String immagineCorrente = ricetta.getImmagini().get(0);
      String relativeDir = immagineCorrente.substring(0, immagineCorrente.indexOf(ricetta.getNome().toLowerCase()));
      Path directoryPath = Paths.get(ProjectPaths.getStaticPath() + relativeDir);
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {
         for (Path filePath : stream) {
            if (Files.exists(filePath)) {
               Files.delete(filePath);
            }
         }
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }
}
