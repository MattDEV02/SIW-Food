package com.siw.siwfood.model;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.helpers.constants.GlobalValues;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jdk.jfr.Unsigned;

import java.util.Objects;

@Entity(name = "Ingrediente")
@Table(name = "Ingredienti", schema = GlobalValues.SQL_SCHEMA_NAME)
public class Ingrediente {
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

   @NotNull
   @Min(FieldSizes.QUANTITA_INGREDIENTE_MIN_VALUE)
   @Max(FieldSizes.QUANTITA_INGREDIENTE_MAX_VALUE)
   @Column(name = "quantita", nullable = false)
   private Integer quantita;

   @ManyToOne(targetEntity = Ricetta.class, optional = false)
   private Ricetta ricetta;

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getNome() {
      return this.nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public Integer getQuantita() {
      return this.quantita;
   }

   public void setQuantita(Integer quantita) {
      this.quantita = quantita;
   }

   public Ricetta getRicetta() {
      return this.ricetta;
   }

   public void setRicetta(Ricetta ricetta) {
      this.ricetta = ricetta;
   }

   public Ingrediente() {

   }

   @Override
   public String toString() {
      return "Ingrediente: {" +
              " id = " + this.getId().toString() +
              ", nome = '" + this.getNome() + '\'' +
              ", quantita = " + this.getQuantita() +
              ", ricetta = " + this.getRicetta() +
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
      Ingrediente that = (Ingrediente) object;
      return Objects.equals(this.getId(), that.getId()) || (Objects.equals(this.getRicetta(), that.getRicetta()) && Objects.equals(this.getNome(), that.getNome()) && Objects.equals(this.getQuantita(), that.getQuantita()));
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getNome(), this.getQuantita());
   }
}
