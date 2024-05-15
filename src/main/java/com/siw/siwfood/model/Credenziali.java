package com.siw.siwfood.model;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.helpers.constants.GlobalValues;
import com.siw.siwfood.helpers.credenziali.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Entity(name = "Credenziali")
@Table(name = "Credenziali", schema = GlobalValues.SQL_SCHEMA_NAME)
public class Credenziali {
   public static Roles DEFAULT_ROLE = Roles.OCCASIONALE_ROLE;

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

   public Credenziali() {
     this.role = Credenziali.DEFAULT_ROLE.toString();
   }

   public Credenziali(String username, String password) {
      this.username = username;
      this.password = password;
      this.role = Credenziali.DEFAULT_ROLE.toString();
   }


   public Credenziali(String username, String password, @NotNull Roles role) {
      this.username = username;
      this.password = password;
      this.role = role.toString();
   }

   public Credenziali(String username, String password, String role) {
      this.username = username;
      this.password = password;
      this.role = role;
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

   @Override
   public String toString() {
      return "Credenziali: {" +
              " id = " + this.getId().toString() +
              ", username = '" + this.getUsername() + '\'' +
              ", role = " + this.getRole() +
              ", password = '" + this.getPassword() + '\'' +
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
