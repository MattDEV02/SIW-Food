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

public class RicettaImmaginiFileUtils {
   public final static String RICETTA_IMMAGINI_DIRECTORY = "/ricette";
   public final static String RICETTA_IMMAGINI_EXTENSION = ".jpeg";

   public static @NonNull String getRicettaImmagineDirectoryName(@NonNull Ricetta ricetta) {
      return ProjectPaths.IMAGES + RicettaImmaginiFileUtils.RICETTA_IMMAGINI_DIRECTORY + "/" + ricetta.getId().toString() + "/";
   }

   public static @NonNull String getRicettaImmagineFileName(@NotNull Integer index) {
      return String.valueOf(index + 1) + RicettaImmaginiFileUtils.RICETTA_IMMAGINI_EXTENSION;
   }

   public static @NonNull String getRicettaImmagineRelativePath(@NonNull Ricetta ricetta, @NonNull Integer index) {
      return RicettaImmaginiFileUtils.getRicettaImmagineDirectoryName(ricetta) + RicettaImmaginiFileUtils.getRicettaImmagineFileName(index);
   }

   public static void storeRicettaImmagine(@NotNull Ricetta ricetta, @NonNull MultipartFile immagine, Integer index, Boolean targetFlag) {
      try {
         //  /images/ricette/{ricettaId}/{immagineRicettaIndex + 1}.jpeg
         String immagineRelativePathCorrente = ricetta.getImmagini().get(index);
         Integer immagineFileNameIndex = immagineRelativePathCorrente.indexOf(RicettaImmaginiFileUtils.getRicettaImmagineFileName(index));
         String immagineDirectoryName = immagineRelativePathCorrente.substring(0, immagineFileNameIndex);
         String staticDestinationName = targetFlag ? ProjectPaths.getTargetStaticPath() : ProjectPaths.getStaticPath();
         String destinationDirectoryName = staticDestinationName + immagineDirectoryName;
         File destinationDirectory = new File(destinationDirectoryName);
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
      RicettaImmaginiFileUtils.storeRicettaImmagine(ricetta, immagine, index, false);
      RicettaImmaginiFileUtils.storeRicettaImmagine(ricetta, immagine, index, true);
   }

   public static @NonNull String getRicettaImmaginiDirectoryNameFromImmaginiRelativePath(@NonNull Ricetta ricetta) {
      final Integer firstIndex = 0;
      String firstImmagineRicettaName = ricetta.getImmagini().get(firstIndex);
      Integer firstImmagineFileNameIndex = firstImmagineRicettaName.indexOf(RicettaImmaginiFileUtils.getRicettaImmagineFileName(firstIndex));
      String firstImmagineDirectoryName = firstImmagineRicettaName.substring(0, firstImmagineFileNameIndex);
      return firstImmagineDirectoryName;
   }

   public static void deleteRicettaImmaginiDirectory(@NotNull Ricetta ricetta) {
      String ricettaImmagineDirectoryName = RicettaImmaginiFileUtils.getRicettaImmaginiDirectoryNameFromImmaginiRelativePath(ricetta);
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
      RicettaImmaginiFileUtils.deleteRicettaImmagini(ricetta, false);
      RicettaImmaginiFileUtils.deleteRicettaImmagini(ricetta, true);
   }

   public static void deleteRicettaImmagini(@NotNull Ricetta ricetta, Boolean targetFlag) {
      String ricettaImmagineDirectoryName =  RicettaImmaginiFileUtils.getRicettaImmaginiDirectoryNameFromImmaginiRelativePath(ricetta);
      String staticDestinationName = targetFlag ? ProjectPaths.getTargetStaticPath() : ProjectPaths.getStaticPath();
      Path ricettaImmagineDirectoryPath = Paths.get(staticDestinationName + ricettaImmagineDirectoryName);
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
