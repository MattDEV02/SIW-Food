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

   public static @NonNull String getRicettaRelativePathImmagineDirectoryName(@NonNull Long ricetteCount) {
      return ProjectPaths.IMAGES + Utils.RICETTA_IMMAGINI_DIRECTORY + "/" + String.valueOf(ricetteCount + 1) + "/";
   }

   public static @NonNull String getRicettaImmagineFileName(@NotNull Integer index) {
      return String.valueOf(index + 1) + Utils.RICETTA_IMMAGINI_EXTENSION;
   }

   public static @NonNull String getRicettaImmagineRelativePath(@NonNull Long ricetteCount, @NonNull Integer index) {
      return Utils.getRicettaRelativePathImmagineDirectoryName(ricetteCount) + Utils.getRicettaImmagineFileName(index);
   }

   public static @NonNull String getRicettaImmagineRelativePath(@NonNull Ricetta ricetta, @NonNull Integer index) {
      return Utils.getRicettaImmagineRelativePathDirectory(ricetta) + Utils.getRicettaImmagineFileName(index);
   }

   public static void storeRicettaImmagine(@NotNull Ricetta ricetta, @NonNull MultipartFile immagine, Integer index) {
      try {
         //  /images/ricette/{ricettaCount}/{immagineIndex}.jpeg
         String immagineRelativePathCorrente = ricetta.getImmagini().get(index);
         Integer fileNameIndex = immagineRelativePathCorrente.indexOf(String.valueOf(index + 1) + Utils.RICETTA_IMMAGINI_EXTENSION);
         String destinationDirectoryName = ProjectPaths.getStaticPath() + immagineRelativePathCorrente.substring(0, fileNameIndex);
         File destinationDirectory = new File(destinationDirectoryName);
         if(!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
         }
         File file = new File(destinationDirectoryName + immagineRelativePathCorrente.substring(fileNameIndex));
         immagine.transferTo(file);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static @NonNull String getRicettaImmagineRelativePathDirectory(@NonNull Ricetta ricetta) {
      String firstImmagineRicettaName = ricetta.getImmagini().get(0);
      return firstImmagineRicettaName.substring(0, firstImmagineRicettaName.indexOf("1" + Utils.RICETTA_IMMAGINI_EXTENSION));
   }

   public static void deleteRicettaImmaginiDirectory(@NotNull Ricetta ricetta) {
      String ricettaImmagineDirectoryName = Utils.getRicettaImmagineRelativePathDirectory(ricetta);
      File directory = new File(ProjectPaths.getStaticPath() + ricettaImmagineDirectoryName);
      try {
         FileUtils.deleteDirectory(directory);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void deleteRicettaImmagini(@NotNull Ricetta ricetta) {
      String ricettaImmagineDirectoryName =  Utils.getRicettaImmagineRelativePathDirectory(ricetta);
      Path directoryPath = Paths.get(ProjectPaths.getStaticPath() + ricettaImmagineDirectoryName);
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
