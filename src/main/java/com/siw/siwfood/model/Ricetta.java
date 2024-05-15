package com.siw.siwfood.model;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.helpers.constants.GlobalValues;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Entity(name = "Ricetta")
@Table(name = "Ricette", schema = GlobalValues.SQL_SCHEMA_NAME)
public class Ricetta {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", nullable = false)
   @Unsigned
   @Min(FieldSizes.ENTITY_ID_MIN_VALUE)
   private Long id;

   @NotBlank
   @Size(min = FieldSizes.NOME_MIN_LENGTH, max = FieldSizes.NOME_MAX_LENGTH)
   @Column(name = "nome", nullable = false)
   private String nome;

   @NotBlank 
   @Size(min = FieldSizes.DESCRIZIONE_RICETTA_MIN_LENGTH, max = FieldSizes.DESCRIZIONE_RICETTA_MAX_LENGTH)
   @Column(name = "descrizione", nullable = false)
   private String descrizione;

   @NotNull
   @Size(min = 0)
   private List<String> immagini;

   @OneToMany(cascade = CascadeType.ALL, targetEntity = Ingrediente.class, orphanRemoval = true)
   @JoinColumn(name = "ricetta", referencedColumnName = "id", nullable = false)
   private List<Ingrediente> ingredienti;

   @OneToOne(cascade = {}, targetEntity = Utente.class, optional = false)
   private Utente cuoco;

   public Utente getCuoco() {
      return this.cuoco;
   }

   public void setCuoco(Utente cuoco) {
      this.cuoco = cuoco;
   }

   public String getDescrizione() {
      return this.descrizione;
   }

   public void setDescrizione(String descrizione) {
      this.descrizione = descrizione;
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public List<String> getImmagini() {
      return this.immagini;
   }

   public void setImmagini(List<String> immagini) {
      this.immagini = immagini;
   }

   public List<Ingrediente> getIngredienti() {
      return this.ingredienti;
   }

   public void setIngredienti(List<Ingrediente> ingredienti) {
      this.ingredienti = ingredienti;
   }

   public String getNome() {
      return this.nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public Ricetta() {
      this.immagini = new ArrayList<String>();
      this.ingredienti = new ArrayList<Ingrediente>();
   }

   @Override
   public String toString() {
      return "Ricetta: {" +
              " id = " + this.getId().toString() +
              ", nome = '" + this.getNome() + '\'' +
              ", descrizione = " + this.getDescrizione() +
              ", immagini = '" + this.getImmagini().toString() + '\'' +
              //", ingredienti = '" + this.getIngredienti().toString() + '\'' +
              " }";
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }
      Ricetta that = (Ricetta) object;
      return Objects.equals(this.getId(), that.getId()) || (Objects.equals(this.getNome(), that.getNome()) && Objects.equals(this.getDescrizione(), that.getDescrizione()) && Objects.equals(this.getIngredienti(), that.getIngredienti()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getNome(), this.getDescrizione(),this.getIngredienti());
   }
}
