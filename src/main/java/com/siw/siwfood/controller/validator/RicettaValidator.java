package com.siw.siwfood.controller.validator;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;


@Component
public class RicettaValidator implements Validator {
   @Autowired
   private RicettaRepository ricettaRepository;
   private Cuoco cuoco;
   private MultipartFile[] immagini;

   public Cuoco getCuoco() {
      return this.cuoco;
   }

   public void setCuoco(Cuoco cuoco) {
      this.cuoco = cuoco;
   }

   public MultipartFile[] getImmagini() {
      return this.immagini;
   }

   public void setImmagini(MultipartFile[] immagini) {
      this.immagini = immagini;
   }

   @Override
   public void validate(@NonNull Object object, @NonNull Errors errors) {
      Ricetta ricetta = (Ricetta) object;
      MultipartFile[] immagini = this.getImmagini();
      if(this.ricettaRepository.existsByCuocoAndNome(this.cuoco, ricetta.getNome())) {
         errors.reject("ricettaAlrearyExists", "Ricetta già esistente.");
      }
      if (immagini == null || immagini.length == 0 || immagini[0].isEmpty()) {
         errors.reject("ricettaImmaginiEmpty", "File non presenti.");
      } else {
         for(MultipartFile immagine : immagini) {
            if(immagine == null) {
               errors.reject("ricettaImmagineEmptyFile", "File non valid o vuoto.");
            } else {
               if (immagine.getSize() > FieldSizes.IMAGE_MAX_BYTE_SIZE) {
                  errors.reject("ricettaImmagineFileSizeExceedsLimit", "La dimensione del file supera il limite di 5 KB.");
               }
               String originalFilename = immagine.getOriginalFilename();
               if (originalFilename == null || (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpeg"))) {
                  errors.reject( "ricettaImmagineUnsupportedFileType", "Tipo file non valido. Sono validi file immagine di tipo .jpeg, .jpg, .png");
               }
            }
         }
      }
   }

   @Override
   public boolean supports(@NonNull Class<?> aClass) {
      return Ricetta.class.equals(aClass);
   }

}
