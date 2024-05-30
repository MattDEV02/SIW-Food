package com.siw.siwfood.helpers.constants;

import org.springframework.core.io.FileSystemResource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ProjectPaths {
   public final static String ROOT = new FileSystemResource("").getFile().getAbsolutePath();

   public final static String SRC = "/src/main";

   public final static String RESOURCES = "/resources";

   public final static String STATIC = "/static";

   public final static String IMAGES = "/images";

   @Contract(pure = true)
   public static @NotNull String getStaticPath() {
      return ProjectPaths.ROOT + ProjectPaths.SRC + ProjectPaths.RESOURCES + ProjectPaths.STATIC;
   }
}
