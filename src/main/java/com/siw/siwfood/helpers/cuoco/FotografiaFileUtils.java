package com.siw.siwfood.helpers.cuoco;

import com.siw.siwfood.helpers.constants.ProjectPaths;
import com.siw.siwfood.model.Cuoco;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FotografiaFileUtils {
   public final static String UTENTE_FOTOGRAFIE_DIRECTORY = "/cuochi";
   public final static String UTENTE_FOTOGRAFIE_EXTENSION = ".jpeg";

   public static @NonNull String getCuocoFotografiaDirectoryName(@NonNull Cuoco cuoco) {
      return ProjectPaths.IMAGES + FotografiaFileUtils.UTENTE_FOTOGRAFIE_DIRECTORY + "/" + cuoco.getId().toString() + "/";
   }

   public static @NonNull String getCuocoFotografiaFileName(@NonNull Cuoco cuoco) {
      return cuoco.getUtente().getNome().toLowerCase() + FotografiaFileUtils.UTENTE_FOTOGRAFIE_EXTENSION;
   }

   public static @NonNull String getCuocoFotografiaRelativePath(@NonNull Cuoco cuoco) {
      return FotografiaFileUtils.getCuocoFotografiaDirectoryName(cuoco) + FotografiaFileUtils.getCuocoFotografiaFileName(cuoco);
   }

   public static void storecuocoImage(@NonNull Cuoco cuoco, @NonNull MultipartFile cuocoImage, Boolean targetFlag) {
      try {
         String cuocoImageRelativePath = cuoco.getFotografia();
         Integer indexOfcuocoImageFileName = cuocoImageRelativePath.indexOf(FotografiaFileUtils.getCuocoFotografiaFileName(cuoco));
         String cuocoImageRelativePathDirectory = cuocoImageRelativePath.substring(0, indexOfcuocoImageFileName);
         String staticDestinationName = targetFlag ? ProjectPaths.getTargetStaticPath() : ProjectPaths.getStaticPath();
         String destinationDirectoryName = staticDestinationName + cuocoImageRelativePathDirectory;
         File destinationDirectory = new File(destinationDirectoryName);
         FileUtils.forceMkdir(destinationDirectory);
         String destinationFileName = cuocoImageRelativePath.substring(indexOfcuocoImageFileName);
         Path fileOutput = Paths.get(destinationDirectoryName + destinationFileName);
         Files.copy(cuocoImage.getInputStream(), fileOutput, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }
   
   public static void storeCuocoFotografia(@NotNull Cuoco cuoco, @NonNull MultipartFile fotografia) {
      FotografiaFileUtils.storecuocoImage(cuoco, fotografia, false);
      FotografiaFileUtils.storecuocoImage(cuoco, fotografia, true);
   }

   public static void deleteFotografiaDirectory(@NotNull Cuoco cuoco) {
      String fotografiaDirectoryName = FotografiaFileUtils.getCuocoFotografiaDirectoryNameFromFotografiaRelativePath(cuoco);
      File fotografiaDirectory = new File(ProjectPaths.getStaticPath() + fotografiaDirectoryName);
      File productImageDirectoryTarget = new File(ProjectPaths.getTargetStaticPath() + fotografiaDirectoryName);
      try {
         FileUtils.deleteDirectory(fotografiaDirectory);
         FileUtils.deleteDirectory(productImageDirectoryTarget);
      } catch (IOException iOException) {
         iOException.printStackTrace();
      }
   }

   public static @NonNull String getCuocoFotografiaDirectoryNameFromFotografiaRelativePath(@NonNull Cuoco cuoco) {
      String fotografiaRelativePath = cuoco.getFotografia();
      Integer fotografiaFileNameIndex = fotografiaRelativePath.indexOf(FotografiaFileUtils.getCuocoFotografiaFileName(cuoco));
      String fotografiaDirectoryName = fotografiaRelativePath.substring(0, fotografiaFileNameIndex);
      return fotografiaDirectoryName;
   }
}
