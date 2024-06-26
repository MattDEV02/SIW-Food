package com.siw.siwfood.model;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.helpers.constants.GlobalValues;
import com.siw.siwfood.helpers.constants.Temporals;
import com.siw.siwfood.helpers.credenziali.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "Credenziali")
@Table(name = "Credenziali", schema = GlobalValues.SQL_SCHEMA_NAME)
public class Credenziali {
   public static Roles DEFAULT_ROLE = Roles.REGISTRATO;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", nullable = false)
   @Unsigned
   @Min(FieldSizes.ENTITY_ID_MIN_VALUE)
   private Long id;

   @NotBlank
   @Size(min = FieldSizes.USERNAME_MIN_LENGTH, max = FieldSizes.USERNAME_MAX_LENGTH)
   @Column(name = "username", unique = true, nullable = false)
   @NotBlank
   private String username;
   @NotBlank
   @Column(name = "password", nullable = false)
   private String password;

   @Column(name = "role", nullable = false)
   @NotBlank
   private String role;

   @Column(name = "inserted_at", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(pattern = Temporals.DATE_TIME_FORMAT)
   private LocalDateTime insertedAt;

   public Credenziali() {
     this.role = Credenziali.DEFAULT_ROLE.toString();
   }

   public Credenziali(String username, String password) {
      this.username = username;
      this.password = password;
      this.role = Credenziali.DEFAULT_ROLE.toString();
   }

   public Long getId() {
      return this.id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getRole() {
      return this.role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public LocalDateTime getInsertedAt() {
      return this.insertedAt;
   }

   public void setInsertedAt(LocalDateTime insertedAt) {
      this.insertedAt = insertedAt;
   }

   @PrePersist
   public void prePersist() {
      if (this.insertedAt == null) {
         this.insertedAt = LocalDateTime.now();
      }
   }

   @Override
   public String toString() {
      return "Credenziali: {" +
             // " id = " + this.getId().toString() +
              ", username = '" + this.getUsername()  +
              ", role = " + this.getRole() +
              ", password = '" + this.getPassword()  +
             // ", insertedAt = '" + this.getInsertedAt().toString()  +
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
      Credenziali that = (Credenziali) object;
      return Objects.equals(this.getId(), that.getId()) || Objects.equals(this.getUsername(), that.getUsername());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getId(), this.getUsername());
   }
}
