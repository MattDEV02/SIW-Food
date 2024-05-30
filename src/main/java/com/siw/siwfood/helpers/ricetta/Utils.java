package com.siw.siwfood.helpers.ricetta;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.model.Ricetta;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Utils {
   public final static String RICETTA_IMMAGINI_DIRECTORY = "/ricette";
   public final static String RICETTA_IMMAGINI_EXTENSION = ".jpeg";

   public static @NonNull String getRicettaImmagineDirectoryName(@NonNull Ricetta ricetta) {
      return ProjectPaths.IMAGES + Utils.RICETTA_IMMAGINI_DIRECTORY + "/" + ricetta.getId().toString() + "/";
   }

   public static @NonNull String getRicettaImmagineFileName(@NotNull Integer index) {
      return String.valueOf(index + 1) + Utils.RICETTA_IMMAGINI_EXTENSION;
   }

   public static @NonNull String getRicettaImmagineRelativePath(@NonNull Ricetta ricetta, @NonNull Integer index) {
      return Utils.getRicettaImmagineDirectoryName(ricetta) + Utils.getRicettaImmagineFileName(index);
   }

   public static void storeRicettaImmagine(@NotNull Ricetta ricetta, @NonNull MultipartFile immagine, Integer index, Boolean targetFlag) {
      try {
         //  /images/ricette/{ricettaId}/{immagineRicettaIndex + 1}.jpeg
         String immagineRelativePathCorrente = ricetta.getImmagini().get(index);
         Integer immagineFileNameIndex = immagineRelativePathCorrente.indexOf(Utils.getRicettaImmagineFileName(index));
         String immagineDirectoryName = immagineRelativePathCorrente.substring(0, immagineFileNameIndex);
         String destination = targetFlag ? ProjectPaths.getTargetStaticPath() : ProjectPaths.getStaticPath();
         String destinationDirectoryName = destination + immagineDirectoryName;
         File destinationDirectory = new File(destinationDirectoryName + immagineDirectoryName);
         if(!destinationDirectory.exists()) {
            FileUtils.forceMkdir(destinationDirectory);
         }
         String immagineFileName = immagineRelativePathCorrente.substring(immagineFileNameIndex);
         Path immagineFileOutput = Paths.get(destinationDirectoryName + immagineFileName);
         Files.copy(immagine.getInputStream(), immagineFileOutput, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static void storeRicettaImmagine(@NotNull Ricetta ricetta, @NonNull MultipartFile immagine, Integer index) {
      Utils.storeRicettaImmagine(ricetta, immagine, index, false);
      Utils.storeRicettaImmagine(ricetta, immagine, index, true);
   }

   public static @NonNull String getRicettaImmaginiDirectoryNameFromImmaginiRelativePath(@NonNull Ricetta ricetta) {
      final Integer firstIndex = 0;
      String firstImmagineRicettaName = ricetta.getImmagini().get(firstIndex);
      Integer firstImmagineFileNameIndex = firstImmagineRicettaName.indexOf(Utils.getRicettaImmagineFileName(firstIndex));
      String firstImmagineDirectoryName = firstImmagineRicettaName.substring(0, firstImmagineFileNameIndex);
      return firstImmagineDirectoryName;
   }

   public static void deleteRicettaImmaginiDirectory(@NotNull Ricetta ricetta) {
      String ricettaImmagineDirectoryName = Utils.getRicettaImmaginiDirectoryNameFromImmaginiRelativePath(ricetta);
      File ricettaImmagineDirectory = new File(ProjectPaths.getStaticPath() + ricettaImmagineDirectoryName);
      File ricettaImmagineDirectoryTarget = new File(ProjectPaths.getTargetStaticPath() + ricettaImmagineDirectoryName);
      try {
         FileUtils.deleteDirectory(ricettaImmagineDirectory);
         FileUtils.deleteDirectory(ricettaImmagineDirectoryTarget);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static void deleteRicettaImmagini(@NotNull Ricetta ricetta) {
      Utils.deleteRicettaImmagini(ricetta, false);
      Utils.deleteRicettaImmagini(ricetta, true);
   }

   public static void deleteRicettaImmagini(@NotNull Ricetta ricetta, Boolean targetFlag) {
      String ricettaImmagineDirectoryName =  Utils.getRicettaImmaginiDirectoryNameFromImmaginiRelativePath(ricetta);
      Path ricettaImmagineDirectoryPath = Paths.get(targetFlag ? ProjectPaths.getTargetStaticPath() : ProjectPaths.getStaticPath() + ricettaImmagineDirectoryName);
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(ricettaImmagineDirectoryPath)) {
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
