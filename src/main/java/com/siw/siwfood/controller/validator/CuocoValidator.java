package com.siw.siwfood.controller.validator;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.model.Cuoco;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CuocoValidator implements Validator {

   private MultipartFile fotografia;

   public MultipartFile getFotografia() {
      return this.fotografia;
   }

   public void setFotografia(MultipartFile fotografia) {
      this.fotografia = fotografia;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      //Cuoco cuoco = (Cuoco) object;
      if (this.getFotografia() == null || this.getFotografia().isEmpty()) {
         errors.reject("cuocoFotografiaEmptyFile", "File non valido o vuoto.");
      } else {
         if (this.getFotografia().getSize() > FieldSizes.IMAGE_MAX_BYTE_SIZE) {
            errors.reject("cuocoFotografiaFileSizeExceedsLimit", "La dimensione del file supera il limite di 5 KB.");
         }
         String originalFilename = this.getFotografia().getOriginalFilename();
         if (originalFilename == null || (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpeg"))) {
            errors.reject( "cuocoFotografiaUnsupportedFileType", "Tipo file non valido. Sono validi file immagine di tipo .jpeg, .jpg, .png");
         }
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Cuoco.class.equals(aClass);
   }
}
