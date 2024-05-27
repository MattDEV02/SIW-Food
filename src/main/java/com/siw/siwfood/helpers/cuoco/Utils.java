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

   public static @NonNull String getCuocoRelativePathFotografiaDirectoryName(@NonNull Long cuochiCount) {
      return ProjectPaths.IMAGES + Utils.UTENTE_FOTOGRAFIE_DIRECTORY + "/" + String.valueOf(cuochiCount) + "/";
   }

   public static @NonNull String getCuocoFotografiaFileName(@NonNull Cuoco cuoco) {
      return cuoco.getId().toString() + Utils.UTENTE_FOTOGRAFIE_EXTENSION;
   }

   public static @NonNull String getCuocoFotografiaRelativePath(@NonNull Long cuochiCount, @NonNull Cuoco cuoco) {
      return Utils.getCuocoRelativePathFotografiaDirectoryName(cuochiCount) + Utils.getCuocoFotografiaFileName(cuoco);
   }

   public static void storeCuocoFotografia(@NotNull Cuoco cuoco, @NonNull MultipartFile fotografia) {
      try {
         //  /images/cuochi/{cuochiCount}/{cuocoId}.jpeg
         String fotografiaRelativePath = cuoco.getFotografia();
         System.out.println(fotografiaRelativePath);
         Integer fileNameIndex = fotografiaRelativePath.indexOf(String.valueOf(cuoco.getId()) + Utils.UTENTE_FOTOGRAFIE_EXTENSION);
         String destinationDirectoryName = ProjectPaths.getStaticPath() + fotografiaRelativePath.substring(0, fileNameIndex);
         File destinationDirectory = new File(destinationDirectoryName);
         if (destinationDirectory.mkdir()) {
            File file = new File(destinationDirectoryName + fotografiaRelativePath.substring(fileNameIndex));
            fotografia.transferTo(file);
         } else {
            System.err.println("Directory per il nuovo Cuoco inserito (id = " + cuoco.getId().toString() + ")" + " NON creata, il file NON puo essere storicizzato.");
         }
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static void deleteFotografiaDirectory(@NotNull Cuoco cuoco) {
      String cuocoFotografiaRelativePath = cuoco.getFotografia();
      String cuocoFotografiaDirectoryName = cuocoFotografiaRelativePath.substring(0, cuocoFotografiaRelativePath.indexOf(cuoco.getId() + Utils.UTENTE_FOTOGRAFIE_EXTENSION));
      File directory = new File(ProjectPaths.getStaticPath() + cuocoFotografiaDirectoryName);
      try {
         FileUtils.deleteDirectory(directory);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
