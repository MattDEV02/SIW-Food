package com.siw.siwfood.model;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.helpers.constants.GlobalValues;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jdk.jfr.Unsigned;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Cuoco")
@Table(name = "Cuochi", schema = GlobalValues.SQL_SCHEMA_NAME)
public class Cuoco {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", nullable = false)
   @Unsigned
   @Min(FieldSizes.ENTITY_ID_MIN_VALUE)
   private Long id;

   @OneToOne(cascade = { CascadeType.DETACH, CascadeType.REMOVE }, targetEntity = Utente.class, fetch = FetchType.EAGER, optional = false, orphanRemoval = true)
   @JoinColumn(name = "utente_id", referencedColumnName = "id", nullable = false, unique = true)
   private Utente utente;

   @OneToMany(cascade = { CascadeType.DETACH, CascadeType.REMOVE }, targetEntity = Ricetta.class, orphanRemoval = true,  mappedBy = "cuoco")
   private List<Ricetta> ricette;

   public Cuoco() {
      this.ricette = new ArrayList<Ricetta>();
   }

   public Cuoco(Utente utente) {
      this.utente = utente;
      this.ricette = new ArrayList<Ricetta>();
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Utente getUtente() {
      return this.utente;
   }

   public void setUtente(Utente utente) {
      this.utente = utente;
   }

   public List<Ricetta> getRicette() {
      return this.ricette;
   }

   public void setRicette(List<Ricetta> ricette) {
      this.ricette = ricette;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }
      Cuoco that = (Cuoco) object;
      return Objects.equals(this.getId(), that.getId()) || Objects.equals(this.getUtente(), that.getUtente());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUtente());
   }

   @Override
   public String toString() {
      return "Utente: {" +
              "id = " + this.getId().toString() +
              ", utente: = '" + this.getUtente().toString() + '\'' +
              // ", ricette = " + this.getRicette().toString() +
              " }";
   }
}
