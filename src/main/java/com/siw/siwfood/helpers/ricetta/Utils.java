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

   public static @NonNull String getRicettaImmagineDirectoryName(@NonNull Ricetta ricetta) {
      return ProjectPaths.IMAGES + Utils.RICETTA_IMMAGINI_DIRECTORY + "/" + ricetta.getId().toString() + "/";
   }

   public static @NonNull String getRicettaImmagineFileName(@NotNull Integer index) {
      return String.valueOf(index + 1) + Utils.RICETTA_IMMAGINI_EXTENSION;
   }

   public static @NonNull String getRicettaImmagineRelativePath(@NonNull Ricetta ricetta, @NonNull Integer index) {
      return Utils.getRicettaImmagineDirectoryName(ricetta) + Utils.getRicettaImmagineFileName(index);
   }

   public static void storeRicettaImmagine(@NotNull Ricetta ricetta, @NonNull MultipartFile immagine, Integer index) {
      try {
         //  /images/ricette/{ricettaId}/{immagineRicettaIndex + 1}.jpeg
         String immagineRelativePathCorrente = ricetta.getImmagini().get(index);
         Integer immagineFileNameIndex = immagineRelativePathCorrente.indexOf(Utils.getRicettaImmagineFileName(index));
         String immagineDirectoryName = immagineRelativePathCorrente.substring(0, immagineFileNameIndex);
         String destinationDirectoryName = ProjectPaths.getStaticPath() + immagineDirectoryName;
         File destinationDirectory = new File(destinationDirectoryName);
         if(!destinationDirectory.exists()) {
            FileUtils.forceMkdir(destinationDirectory);
         }
         String immagineFileName = immagineRelativePathCorrente.substring(immagineFileNameIndex);
         File immagineFileOutput = new File(destinationDirectoryName + immagineFileName);
         immagine.transferTo(immagineFileOutput);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
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
      File  ricettaImmagineDirectory = new File(ProjectPaths.getStaticPath() + ricettaImmagineDirectoryName);
      try {
         FileUtils.deleteDirectory(ricettaImmagineDirectory);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void deleteRicettaImmagini(@NotNull Ricetta ricetta) {
      String ricettaImmagineDirectoryName =  Utils.getRicettaImmaginiDirectoryNameFromImmaginiRelativePath(ricetta);
      Path ricettaImmagineDirectoryPath = Paths.get(ProjectPaths.getStaticPath() + ricettaImmagineDirectoryName);
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
