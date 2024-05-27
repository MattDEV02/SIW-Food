package com.siw.siwfood.model;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.helpers.constants.GlobalValues;
import com.siw.siwfood.helpers.constants.Temporals;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity(name = "Utente")
@Table(name = "Utenti", schema = GlobalValues.SQL_SCHEMA_NAME)
public class Utente {
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
   @Size(min = FieldSizes.COGNOME_MIN_LENGTH, max = FieldSizes.COGNOME_MAX_LENGTH)
   @Column(name = "cognome", nullable = false)
   private String cognome;

   @DateTimeFormat(pattern = Temporals.DATE_FORMAT)
   @Past
   @Column(name = "datanascita", nullable = false)
   @Temporal(TemporalType.DATE)
   @NotNull
   private Date dataNascita;

   @OneToOne(cascade = CascadeType.ALL, targetEntity = Credenziali.class, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
   @JoinColumn(name = "credenziali_id", referencedColumnName = "id", nullable = false, unique = true)
   private Credenziali credenziali;


   public Utente() {

   }

   public Utente(Credenziali credenziali) {
      this.credenziali = credenziali;
   }

   public String getCognome() {
      return this.cognome;
   }

   public void setCognome(String cognome) {
      this.cognome = cognome;
   }

   public Date getDataNascita() {
      return this.dataNascita;
   }

   public void setDataNascita(Date dataNascita) {
      this.dataNascita = dataNascita;
   }

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

   public Credenziali getCredenziali() {
      return this.credenziali;
   }

   public void setCredenziali(Credenziali credenziali) {
      this.credenziali = credenziali;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      }
      if (object == null || this.getClass() != object.getClass()) {
         return false;
      }
      Utente that = (Utente) object;
      return Objects.equals(this.getId(), that.getId()) || Objects.equals(this.getCredenziali(), that.getCredenziali());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getCredenziali());
   }

   @Override
   public String toString() {
      return "Utente: {" +
              "id = " + this.getId().toString() +
              ", nome = '" + this.getNome() + '\'' +
              ", cognome = '" + this.getCognome() + '\'' +
              ", dataNascita = " + this.getDataNascita().toString() +
              ", credenziali = " + this.getCredenziali().toString() +
              " }";
   }
}
