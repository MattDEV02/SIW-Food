package com.siw.siwfood.helpers.cuoco;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.model.Cuoco;
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
         //  /images/cuochi/{cuocoId}/{cuocoUtenteNome}.jpeg
         String fotografiaRelativePath = cuoco.getFotografia();
         Integer fotografiaFileNameIndex = fotografiaRelativePath.indexOf(Utils.getCuocoFotografiaFileName(cuoco));
         String fotografiaDirectoryName = fotografiaRelativePath.substring(0, fotografiaFileNameIndex);
         String destinationDirectoryName = ProjectPaths.getStaticPath() + fotografiaDirectoryName;
         File destinationDirectory = new File(destinationDirectoryName);
         FileUtils.forceMkdir(destinationDirectory);
         String fotografiaFileName = fotografiaRelativePath.substring(fotografiaFileNameIndex);
         File fotografiaFileOutput = new File(destinationDirectoryName + fotografiaFileName);
         fotografia.transferTo(fotografiaFileOutput);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static void deleteFotografiaDirectory(@NotNull Cuoco cuoco) {
      String fotografiaDirectoryName = Utils.getCuocoFotografiaDirectoryNameFromFotografiaRelativePath(cuoco);
      File fotografiaDirectory = new File(ProjectPaths.getStaticPath() + fotografiaDirectoryName);
      try {
         FileUtils.deleteDirectory(fotografiaDirectory);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static @NonNull String getCuocoFotografiaDirectoryNameFromFotografiaRelativePath(@NonNull Cuoco cuoco) {
      String fotografiaRelativePath = cuoco.getFotografia();
      Integer fotografiaFileNameIndex = fotografiaRelativePath.indexOf(Utils.getCuocoFotografiaFileName(cuoco));
      String fotografiaDirectoryName = fotografiaRelativePath.substring(0, fotografiaFileNameIndex);
      return fotografiaDirectoryName;
   }
}
