package com.siw.siwfood.helpers.constants;

import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Field;

import java.util.Map;

public class GlobalValues {
   public final static String APP_NAME = "SIW-Food";

   public final static String CHARSET = "UTF-8";

   public final static String LANG = "it-IT";

   public final static String[] AUTHORS = {"Matteo Lambertucci, matteolambertucci3@gmail.com, mat.lambertucci@stud.uniroma3.it"};

   public final static String APP_REPO = "https://github.com/MattDEV02/SIW-Food.git";

   public final static String DESCRIPTION = GlobalValues.APP_NAME + " site.";

   public final static String SQL_DB_NAME = "siwfood";

   public final static String SQL_SCHEMA_NAME = "public";

   public final static String ROOT_PATH = ProjectPaths.ROOT;

   public final static String TEMPLATES_XMLNS = "http://www.thymeleaf.org";

   public static void fillGlobalMap(@NotNull Class<?> _class, Map<String, Object> GLOBAL_MAP) {
      Field[] fields = _class.getDeclaredFields();
      for (Field field : fields) {
         try {
            String name = field.getName();
            Object value = field.get(null);
            GLOBAL_MAP.put(name, value);
         } catch (IllegalAccessException | IllegalArgumentException illegalException) {
            System.err.println("Impossible to access at this field: " + field.getName() + " in this class: " + _class.toString());
         }
      }
   }
}
