package com.siw.siwfood.helpers.cuoco;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utils {
   public final static String UTENTE_FOTOGRAFIE_DIRECTORY = "/cuochi";
   public final static String UTENTE_FOTOGRAFIE_EXTENSION = ".jpeg";

   public static @NonNull String getCuocoFotografiaDirectoryName(@NonNull Cuoco cuoco) {
      return ProjectPaths.IMAGES + Utils.UTENTE_FOTOGRAFIE_DIRECTORY + "/" + cuoco.getId().toString() + "/";
   }

   public static @NonNull String getCuocoFotografiaFileName(@NonNull Cuoco cuoco) {
      return cuoco.getUtente().getNome().toLowerCase() + Utils.UTENTE_FOTOGRAFIE_EXTENSION;
   }

   public static @NonNull String getCuocoFotografiaRelativePath(@NonNull Cuoco cuoco) {
      return Utils.getCuocoFotografiaDirectoryName(cuoco) + Utils.getCuocoFotografiaFileName(cuoco);
   }

   public static void storeCuocoFotografia(@NotNull Cuoco cuoco, @NonNull MultipartFile fotografia) {
      try {
         //  /images/cuochi/{cuochiCount}/{cuocoId}.jpeg
         String fotografiaRelativePath = cuoco.getFotografia();
         Integer fileNameIndex = fotografiaRelativePath.indexOf(Utils.getCuocoFotografiaFileName(cuoco));
         String fotografiaDirectoryName = fotografiaRelativePath.substring(0, fileNameIndex);
         String destinationDirectoryName = ProjectPaths.getStaticPath() + fotografiaDirectoryName;
         File destinationDirectory = new File(destinationDirectoryName);
         if (destinationDirectory.mkdir()) {
            String fotografiaFileName = fotografiaRelativePath.substring(fileNameIndex);
            File file = new File(destinationDirectoryName + fotografiaFileName);
            fotografia.transferTo(file);
         } else {
            System.err.println("Directory per il nuovo Cuoco inserito (id = " + cuoco.getId().toString() + ")" + " NON creata, il file NON puo essere storicizzato.");
         }
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static void deleteFotografiaDirectory(@NotNull Cuoco cuoco) {
      String fotografiaDirectoryName = Utils.getCuocoFotografiaDirectoryNameFromFotografiaRelativePath(cuoco);
      File directory = new File(ProjectPaths.getStaticPath() + fotografiaDirectoryName);
      try {
         FileUtils.deleteDirectory(directory);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static @NonNull String getCuocoFotografiaDirectoryNameFromFotografiaRelativePath(@NonNull Cuoco cuoco) {
      String fotografiaRelativePath = cuoco.getFotografia();
      return fotografiaRelativePath.substring(0, fotografiaRelativePath.indexOf(Utils.getCuocoFotografiaFileName(cuoco)));
   }
}
