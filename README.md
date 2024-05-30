# SIW-Food

<p align="center">
<img  title="SIW-Food Logo"  alt="SIW-Food Logo"  width="19.5%"  src="/src/main/resources/static/images/logo/logo.png">
</p>

### Progetto SIW-Food, corso Sistemi informativi su Web, CDL Ingegneria informatica, Università degli studi di Roma Tre.

## Modello di Dominio 🔢

<p align="center">
<img  title="SIW-Food Domain Model"  alt="SIW-Food Domain Model"  src="/src/main/resources/static/images/README/progettazione/Domain_Model.png"  width="100%"/>
</p>

## Funzionalità Principali ✨

- **Utilizzo:** Gli utenti possono essere di 3 tipi: 
  - Utenti occasionali: NON sono Utenti registrati sul sito, possono solo visualizzare e ricercare Cuochi, ricette e ingredienti. 
  - Utenti registrati: Utenti (Cuochi) che oltre a visualizzare e cercare, possono registrare, modificare ed eliminare le proprie ricette e relativi ingredienti.
  - Utenti amministratori: Utenti che oltre a poter svolgere tutte le precedenti operazioni possono anche cancellare Cuochi dal sito e "selezionare un Cuoco c per una ricetta r".

- **Responsive:** Il sito è responsive e user-friendly.

- **Sicurezza e controllo degli errori utente:** I dati sensibili dell'utente, come la password, vengono crittografati e memorizzati in un database molto robusto. Sono inoltre presenti controlli degli errori sia lato client che lato server.

- **Modularità:** Il progetto è diviso in vari moduli, package, fragments e directory.

- **Visualizzazione di guide tramite tooltips:** Sono presenti molti tooltips che guidano l'utente nel sito.

- **Lingua:** È disponibile solo la lingua italiana al momento.

## Screenshots 📸

## `Pagina di registrazione Utenti`

<p align="center">
<img  title="SiwFood RegScreen screenshoot 1"  alt="SiwFood RegScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/registrazione/1.png"  width="100%"/>
</p>

## `Pagina di login Utenti`

<p align="center">
	<img  title="SiwFood LoginScreen screenshoot 1"  alt="SiwFood LoginScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/login/1.png"  width="100%"/>
</p>

## `Home page`

<p align="center">
	<img  title="SiwFood HomeScreen screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%"/>
    <img  title="SiwFood HomeScreen screenshoot 2"  alt="SiwFood HomeScreen screenshoot 2"  src="/src/main/resources/static/images/README/screenshots/home/2.png"  width="100%"/>
</p>

## `Pagina Cuochi`

<p align="center">
	<img  title="SiwFood Cuochi screenshoot 1"  alt="SiwFood Cuochi screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/cuochi/1.png"  width="100%"/>
</p>

## `Pagina ricette`

<p align="center">
	<img  title="SiwFood Ricette screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/ricette/1.png"  width="100%"/>
<img  title="SiwFood Ricette screenshoot 2"  alt="SiwFood HomeScreen screenshoot 2"  src="/src/main/resources/static/images/README/screenshots/ricette/2.png"  width="100%"/>
<img  title="SiwFood Ricette screenshoot 3"  alt="SiwFood HomeScreen screenshoot 3"  src="/src/main/resources/static/images/README/screenshots/ricette/3.png"  width="100%"/>
</p>

## `Pagina ingredienti`

<p align="center">
	<img  title="SiwFood Ingredienti screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/ingredienti/1.png"  width="100%"/>
</p>

## Struttura del progetto 🏠

- **`src/`**: Questa directory contiene due sotto-directory: main/ e test/.

    - **`src/main/`**: Contiene tutti i componenti riutilizzabili dell'applicazione.

    - **`src/main/java/`**: Questa directory contiene il codice sorgente principale e le risorse per la tua applicazione, che vengono utilizzate in produzione.
      I pacchetti corrispondono alle aree di dominio o funzionalità della tua applicazione.

    - **`src/main/resources/`**: Questa directory contiene risorse non Java utilizzate dalla tua applicazione, come file di proprietà, file di configurazione XML, risorse statiche, ecc.

    - **`src/test/java/`**: Simile a src/main/java, questa directory contiene file di codice sorgente Java specificamente per scopi di test. Segue la stessa struttura dei pacchetti del codice sorgente principale.

    - **`src/main/resources/application.properties`**: File di configurazione per la tua applicazione Spring Boot. Contengono proprietà per configurare vari aspetti della tua applicazione, come impostazioni di connessione al database, porta del server, configurazione del logging, ecc.

    - **`src/main/java/com/siw/SiwFood/SiwFoodApplication.java`**: Il punto di ingresso principale dell' applicazione Spring Boot. Questo file Java contiene tipicamente il metodo principale per avviare il contesto dell'applicazione Spring.

    - **`src/main/java/com/siw/siwfood/authentication`**: Una directory (package) dove è presente la configurazione sulla sicurezza del sito.
  
    - **`src/main/java/com/siw/siwfood/controller`**: Una directory (package) dove si trovano le classi dei Controller del sito.
  
    - **`src/main/java/com/siw/siwfood/exception`**: Una directory (package) dove si trovano classi di Eccezioni personalizzate del progetto.
  
    - **`src/main/java/com/siw/siwfood/helpers`**: Una directory (package) dove si trovano helper utili del progetto con molti metodi statici.
  
    - **`src/main/java/com/siw/siwfood/model`**: Una directory (package) dove si trovano classi dei Modelli Entità del progetto.
  
    - **`src/main/java/com/siw/siwfood/repository`**: Una directory (package) dove si trovano le interfacce dei Repository del progetto.
  
    - **`src/main/java/com/siw/siwfood/service`**: Una directory (package) dove si trovano le classi dei Servizi del progetto.

- **`target/`**: Questa directory è una directory standard creata da strumenti di build come Maven o Gradle (Maven) durante il processo di build. Di solito non fa parte del repository del codice sorgente ed è generata dinamicamente. Contiene anche una documentazione e il JAR del progetto .

- **`SiwFood.sql`**: Un file di script SQL (PostgreSQL) che consente di creare il database utilizzato per questa App.

- **`pom.xml`**: Questo file è specifico per i progetti basati su Maven. Stands for "Project Object Model" ed è utilizzato da Maven per gestire la configurazione di build del progetto, le dipendenze, i plugin e altre impostazioni. Il file pom.xml è scritto in formato XML e contiene informazioni come i metadati del progetto, le dipendenze dalle librerie esterne, le istruzioni di build e i profili per diversi ambienti. È il file di configurazione centrale per i progetti Maven ed è cruciale per la costruzione, il testing e il rilascio dell'applicazione.

- **`README.md`**: Documentazione in Markdown per questo progetto.

## Tecnologie utilizzate 🧑‍💻

|     *Nome*     |   *Versione*   |
|:--------------:|:--------------:|
|      Java      |       17       |
|  Spring boot   |     3.2.5      |  
|     Maven      |     3.9.6      |
|   Hibernate    |     4.3.11     |
|   PostgreSQL   |      16.0      |
|   thymeleaf    |     3.0.14     |
|      XML       |      1.1       |
|   Bootstrap    |     5.3.3      |
|  FontAwesome   |     5.15.4     |
|      HTML      |       5        |
|      CSS       |      4.15      |
|   Javascript   |      ES6       |
|     JQuery     |     3.6.0      |
|    Markdown    |      3.6       |
|    Windows     |       11       |
|      GIT       |     2.43.0     |
|     GITHUB     |     3.12.3     |
| IntelliJ IDEA  |     2024.1     |
|     Chrome     | 124.0.6367.201 |
| Microsoft EDGE | 123.0.2420.65  |

## Autore ©️

Made with ❤️ and a lot of hard work 🏋️‍♂️ by:

- **Matteo Lambertucci (matricola 578219, Roma TRE)**

    - [Profilo GitHub (MattDEV02)](https://github.com/MattDEV02)

    - [Profile Linkedin](https://www.linkedin.com/in/matteo-lambertucci-134073211)

    - [Profile Instagram (_matte.02_)](https://www.instagram.com/_matte.02_/)

    - [Profile Moodle](https://ingegneriacivileinformaticatecnologieaeronautiche.el.uniroma3.it/user/profile.php?id=5522)

    - [mat.lambertucci@stud.uniroma3.it](mat.lambertucci@stud.uniroma3.it)

    - [matteolambertucci3@gmail.com](matteolambertucci3@gmail.com)

## Installazione 🚀 ed utilizzo ⚡

### Requisiti

- Java 17
- Maven 3.9
- PostgreSQL 16.0

### Istruzioni di installazione

1. Clona il repository:

```bash
git clone https://github.com/MattDEV02/SiwFood.git
```

2. Naviga sulla directory del progetto:

```bash
cd SiwFood
```

3. Installa le dipendenze:

```bash
mvnw install

# or using gradle

# gradle install
```

4. Builda il Java code:

```bash
mvnw compile

# or using gradle

# gradle compileJava
```

5. Effettua il Packaging nel file JAR:

```bash
mvnw package

# or using gradle

# gradle assemble
```

6. Execute the JAR file:

```bash
java -jar target/SIW-Food-0.0.1-SNAPSHOT.jar
```

## Alcuni esempi di codice 🤖

### `SiwFoodApplication.java` -> `com.siw.siwFood.SiwFoodApplication`

```java
package com.siw.siwfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@SpringBootApplication
public class SiwFoodApplication {

   public static void main(String[] args) {
      SpringApplication.run(SiwFoodApplication.class, args);
   }

}
```

### `AuthConfiguration.java` -> `com.market.marketnexus.authentication.AuthConfiguration`

```java
package com.siw.siwfood.authentication;

import com.siw.siwfood.helpers.credenziali.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.siw.siwfood.helpers.constants.ProjectPaths;
import javax.sql.DataSource;

@Configuration
//@EnableWebMvc
public class AuthConfiguration implements WebMvcConfigurer {

   private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:" + ProjectPaths.STATIC + "/"};
   @Autowired
   private DataSource dataSource;

   @Override
   public void addResourceHandlers(@NonNull ResourceHandlerRegistry resourceHandlerRegistry) {
      resourceHandlerRegistry.addResourceHandler("/**")
              .addResourceLocations(AuthConfiguration.CLASSPATH_RESOURCE_LOCATIONS)
      //.setCachePeriod(0)
      ;
   }


   @Autowired
   public void configureGlobal(@NonNull AuthenticationManagerBuilder authenticationManagerBuilder)
           throws Exception {
      authenticationManagerBuilder.jdbcAuthentication()
              .dataSource(this.dataSource)
              .authoritiesByUsernameQuery("SELECT username, role FROM Credenziali WHERE username = ?")
              .usersByUsernameQuery("SELECT username, password, TRUE AS enabled FROM Credenziali WHERE username = ?");
   }


   @Bean
   public PasswordEncoder passwordEncoder() { // Bcrypt algorithm
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(@NonNull AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   protected SecurityFilterChain configure(final @NonNull HttpSecurity httpSecurity) throws Exception {
      httpSecurity
              .cors(AbstractHttpConfigurer::disable)
              .csrf(AbstractHttpConfigurer::disable)
              .authorizeHttpRequests(
                      authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                              .requestMatchers(HttpMethod.GET,
                                      "/", "/register", "/login", "/logout",
                                      "/cuochi","/cuochi/cuoco/{cuocoId}",
                                      "/ricette", "/ricette/ricetta/{ricettaId}", "/ricette/cuoco/{cuocoId}", "/ricette/searchRicette",
                                      "/ingredienti",  "/ingredienti/ricetta/{ricettaId}/ingrediente/{ingredienteId}", "/ingredienti/ricetta/{ricettaId}",
                                      "/css/**", "/js/**", "/images/**", "/webfonts/**").permitAll()
                              .requestMatchers(HttpMethod.POST, "/register").permitAll()
                              .requestMatchers("/cuochi/register").hasAnyAuthority(Roles.AMMINISTRATORE.toString())
                              .requestMatchers(HttpMethod.GET,"/cuochi/delete/**").hasAnyAuthority(Roles.AMMINISTRATORE.toString())
                              .requestMatchers("/cuochi/update/**").hasAnyAuthority(Roles.AMMINISTRATORE.toString())
                              .requestMatchers("/ricette/register").hasAnyAuthority(Roles.AMMINISTRATORE.toString(), Roles.REGISTRATO.toString())
                              .requestMatchers(HttpMethod.GET,"/ricette/delete/**").hasAnyAuthority(Roles.AMMINISTRATORE.toString(), Roles.REGISTRATO.toString())
                              .requestMatchers("/ricette/update/**").hasAnyAuthority(Roles.AMMINISTRATORE.toString(), Roles.REGISTRATO.toString())
                              .requestMatchers("/ingredienti/register/ricetta/{ricettaId}").hasAnyAuthority(Roles.AMMINISTRATORE.toString(), Roles.REGISTRATO.toString())
                              .requestMatchers(HttpMethod.GET,"/ingredienti/delete/ricetta/{ricettaId}/ingrediente/{ingredienteId}").hasAnyAuthority(Roles.AMMINISTRATORE.toString(), Roles.REGISTRATO.toString())
                              .requestMatchers("/ingredienti/update/ricetta/{ricettaId}/ingrediente/{ingredienteId}").hasAnyAuthority(Roles.AMMINISTRATORE.toString(), Roles.REGISTRATO.toString())
                              .requestMatchers(HttpMethod.DELETE).denyAll()
                              .anyRequest().authenticated()
              )
              .formLogin(formLogin -> formLogin
                      .loginPage("/login")
                      .defaultSuccessUrl("/", true)
                      .failureUrl("/login?invalidCredentials=true")
                      .usernameParameter("username")
                      .passwordParameter("password")
                      .permitAll()
              )
              .logout(logout -> logout
                      .logoutUrl("/logout")
                      .logoutSuccessUrl("/login?logoutSuccessful=true")
                      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                      .invalidateHttpSession(true)
                      .clearAuthentication(true)
                      .deleteCookies("JSESSIONID")
                      .permitAll());
      return httpSecurity.build();
   }
}
```

### `AuthenticationController.java` -> `com.siw.siwfood.controller.AuthenticationController`

```java
package com.siw.siwfood.controller;

import com.siw.siwfood.controller.validator.CredenzialiValidator;
import com.siw.siwfood.controller.validator.CuocoValidator;
import com.siw.siwfood.controller.validator.UtenteValidator;
import com.siw.siwfood.helpers.cuoco.Utils;
import com.siw.siwfood.model.Credenziali;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.service.CuocoService;
import com.siw.siwfood.service.RicettaService;
import com.siw.siwfood.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import static com.siw.siwfood.helpers.credenziali.Utils.utenteIsCuoco;

import java.util.List;
import java.util.Objects;

@Controller
public class AuthenticationController {
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private CuocoService cuocoService;
  @Autowired
  private UtenteService utenteService;
  @Autowired
  private CuocoValidator cuocoValidator;
  @Autowired
  private UtenteValidator utenteValidator;
  @Autowired
  private CredenzialiValidator credenzialiValidator;
  @Autowired
  private RicettaService ricettaService;

  @GetMapping(value = "/register")
  public ModelAndView showRegisterUserForm() {
    ModelAndView modelAndView = new ModelAndView("utenteForm.html");
    modelAndView.addObject("utente", new Utente());
    modelAndView.addObject("credenziali", new Credenziali());
    return modelAndView;
  }

  @PostMapping(value = "/register")
  public ModelAndView registerUser(
          @Valid @NonNull @ModelAttribute("utente") Utente utente,
          @NonNull BindingResult utenteBindingResult,
          @Valid @NonNull @ModelAttribute("credenziali") Credenziali credenziali,
          @NonNull BindingResult credenzialiBindingResult,
          @NonNull @RequestParam("confirm-password") String confirmPassword,
          @NonNull @RequestParam("fotografia-cuoco") MultipartFile fotografiaCuoco
  ) {
    ModelAndView modelAndView = new ModelAndView("utenteForm.html");
    this.cuocoValidator.setFotografia(fotografiaCuoco);
    this.credenzialiValidator.setConfirmPassword(confirmPassword);
    this.utenteValidator.validate(utente, utenteBindingResult);
    this.cuocoValidator.validate(utente, utenteBindingResult);
    this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
    if (!utenteBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
      String encodedPassword = passwordEncoder.encode(credenziali.getPassword());
      credenziali.setPassword(encodedPassword);
      utente.setCredenziali(credenziali);
      Utente savedUtente = this.utenteService.saveUtente(utente);
      if (savedUtente != null) {
        Cuoco cuoco = new Cuoco(savedUtente);
        Cuoco savedCuoco = this.cuocoService.saveCuoco(cuoco);
        if (savedCuoco != null) {
          Utils.storeCuocoFotografia(savedCuoco, fotografiaCuoco);
        }
        modelAndView.setViewName("redirect:/login");
        modelAndView.addObject("isUtenteRegistered", true);
      }
    } else {
      List<ObjectError> userErrors = utenteBindingResult.getAllErrors();
      for (ObjectError userError : userErrors) {
        modelAndView.addObject(Objects.requireNonNull(userError.getCode()), userError.getDefaultMessage());
      }
      List<ObjectError> credentialsErrors = credenzialiBindingResult.getAllErrors();
      for (ObjectError credentialErrors : credentialsErrors) {
        modelAndView.addObject(Objects.requireNonNull(credentialErrors.getCode()), credentialErrors.getDefaultMessage());
      }
    }
    return modelAndView;
  }

  @GetMapping(value = "/login")
  public ModelAndView showUserLoginForm() {
    ModelAndView modelAndView = new ModelAndView("login.html");
    modelAndView.addObject("credenziali", new Credenziali());
    return modelAndView;
  }

  @GetMapping(value = {"", "/"})
  public ModelAndView showHomePage(@ModelAttribute("loggedUser") Utente loggedUser) {
    ModelAndView modelAndView = new ModelAndView("index.html");
    Iterable<Ricetta> ricette = null;
    if (utenteIsCuoco(loggedUser)) {
      Cuoco cuoco = this.cuocoService.getCuoco(loggedUser);
      ricette = this.ricettaService.getAllRicetteCuoco(cuoco);
    }
    modelAndView.addObject("ricette", ricette);
    return modelAndView;
  }

}

```

### `CuocoService.java` -> `com.siw.siwfood.service.CuocoService`

```java
package com.siw.siwfood.service;

import com.siw.siwfood.helpers.cuoco.Utils;
import com.siw.siwfood.model.Cuoco;
import com.siw.siwfood.model.Ricetta;
import com.siw.siwfood.model.Utente;
import com.siw.siwfood.repository.CuocoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.siw.siwfood.helpers.ricetta.Utils.deleteRicettaImmaginiDirectory;

@Service
public class CuocoService {
  @Autowired
  private CuocoRepository cuocoRepository;

  public Iterable<Cuoco> getAllCuochi() {
    return this.cuocoRepository.findAll();
  }

  @Transactional
  public Cuoco saveCuoco(@NotNull Cuoco cuoco) {
    Cuoco savedCuoco = this.cuocoRepository.save(cuoco);
    savedCuoco.setFotografia(Utils.getCuocoFotografiaRelativePath(savedCuoco));
    return this.cuocoRepository.save(cuoco);
  }

  @Transactional
  public void deleteCuoco(Long cuocoId) {
    Cuoco cuoco = this.getCuoco(cuocoId);
    if(cuoco != null) {
      Utils.deleteFotografiaDirectory(cuoco);
      List<Ricetta> ricette = cuoco.getRicette();
      for(Ricetta ricetta : ricette) {
        deleteRicettaImmaginiDirectory(ricetta);
      }
      this.cuocoRepository.delete(cuoco);
    }
  }

  @Transactional
  public Cuoco getCuoco(Long cuocoId) {
    return this.cuocoRepository.findById(cuocoId).orElse(null);
  }

  public Cuoco getCuoco(@NotNull Utente utente) {
    return this.cuocoRepository.findByUtente(utente).orElse(null);
  }
}

```

### `CredenzialiRepository.java` -> `com.siw.siwfood.repository.CredenzialiRepository`

```java
package com.siw.siwfood.repository;

import com.siw.siwfood.model.Credenziali;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {
  public Optional<Credenziali> findByUsername(String username);

  public Boolean existsByUsername(String username);
}
```

### `Ricetta.java` -> `com.siw.siwfood.model.Ricetta`

```java
package com.siw.siwfood.model;

import com.siw.siwfood.helpers.constants.FieldSizes;
import com.siw.siwfood.helpers.constants.GlobalValues;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Unsigned;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Entity(name = "Ricetta")
@Table(name = "Ricette", schema = GlobalValues.SQL_SCHEMA_NAME, uniqueConstraints = @UniqueConstraint(name = "riccete_nome_cuoco_unique", columnNames = {"nome", "cuoco_id"}))
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

  @Column(name = "immagini", nullable = false, columnDefinition = "TEXT[] NOT NULL")
  private List<String> immagini;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.EAGER, targetEntity = Ingrediente.class, orphanRemoval = true, mappedBy = "ricetta")
  private List<Ingrediente> ingredienti;

  @ManyToOne(targetEntity = Cuoco.class, optional = false)
  private Cuoco cuoco;

  public Cuoco getCuoco() {
    return this.cuoco;
  }

  public void setCuoco(Cuoco cuoco) {
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

  public Ricetta(Cuoco cuoco) {
    this.cuoco = cuoco;
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
            ", ingredienti = '" + this.getIngredienti().toString() + '\'' +
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
    return Objects.equals(this.getId(), that.getId()) || (Objects.equals(this.getNome(), that.getNome()) && Objects.equals(this.getCuoco(), that.getCuoco()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId(), this.getNome(), this.getCuoco(), this.getIngredienti());
  }
}

```

### `/food/ricette/ricette.html`

```XHTML
<!DOCTYPE html>
<html th:lang="${GLOBAL_CONSTANTS_MAP.get('LANG')}" th:xmlns:th="${GLOBAL_CONSTANTS_MAP.get('TEMPLATES_XMLNS')}">

<head th:replace="~{fragments/shared/head.html :: head(title = 'Ricette')}">

</head>

<body>
<div th:replace="~{fragments/shared/pagination/header.html :: header()}">
</div>
<main>
  <div class="container" th:with="areRicetteValid = ${ricette != null && !#lists.isEmpty(ricette)}, isRicettaDeleted = ${param.isRicettaDeleted}, ricettaNotFound = ${param.ricettaNotFound}, cuocoNotFound = ${param.cuocoNotFound}, ricettaNonTua = ${param.ricettaNonTua}">
    <div class="row justify-content-center">
      <div class="col-12 mt-5">
        <div class="row text-center">
          <h1 th:if="${hasSearchedRicette}" th:text="'Ricette ' + ${GLOBAL_CONSTANTS_MAP.get('APP_NAME') + ' ricercate'} + ' 🥐'"></h1>
          <h1 th:unless="${hasSearchedRicette}" th:text="'Ricette ' + ${#strings.isEmpty(usernameCuoco) ? GLOBAL_CONSTANTS_MAP.get('APP_NAME') : usernameCuoco} + ' 🥐'"></h1>
        </div>
      </div>
      <div th:replace="~{fragments/shared/message/success/successMessage.html :: successMessage(text = 'Ricetta eliminata con successo.', condition = ${isRicettaDeleted})}"></div>
      <div th:replace="~{fragments/shared/message/error/errorMessage.html :: errorMessage(text = 'Ricetta non esistente.', condition = ${ricettaNotFound})}"></div>
      <div th:replace="~{fragments/shared/message/error/errorMessage.html :: errorMessage(text = 'Cuoco non esistente.', condition = ${cuocoNotFound})}"></div>
      <div th:replace="~{fragments/shared/message/error/errorMessage.html :: errorMessage(text = 'Non puoi aggiungere ingredienti alle ricette degli altri Cuochi.', condition = ${ricettaNonTua})}"></div>
      <div class="col-12 mt-4" th:if="${#authentication.getPrincipal() != 'anonymousUser' && (#strings.contains(loggedUser.credenziali.role, 'AMMINISTRATORE') || #strings.contains(loggedUser.credenziali.role, 'REGISTRATO'))}">
        <div class="row text-center">
          <a th:href="@{/ricette/register}" class="fs-5">Inserisci una nuova ricetta</a>
        </div>
      </div>
      <div class="col-12 mb-5" th:if="${areRicetteValid}">
        <div class="row">
          <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4 col-xxl-4" th:each="ricetta : ${ricette}">
            <div th:replace="~{fragments/shared/food/ricetta.html :: ricetta(ricetta = ${ricetta})}"></div>
          </div>
        </div>
      </div>
      <div th:unless="${areRicetteValid}">
        <div th:replace="~{fragments/shared/food/notFound/ricetteNotFound.html :: ricetteNotFound()}"></div>
      </div>
    </div>
  </div>
</main>
<div th:replace="~{fragments/shared/pagination/footer.html :: footer()}">
</div>
<script th:src="@{/js/jquery/jquery.min.js}"></script>
<script th:src="@{/js/bootstrap/bootstrap.js}"></script>
</body>

</html>
```


### `/js/shared/showPassword.js`

```javascript
const togglePasswordVisibilityButton = document.getElementById("toggle-password-visibility");

const passwordInput = document.getElementById("password");

togglePasswordVisibilityButton.addEventListener("click", () => {
  // Cambia il tipo di input da password a text o viceversa
  const eyeIcon = document.getElementById("eye-icon");
  if (passwordInput.type === "password") {
    eyeIcon.classList.remove("fa-eye");
    eyeIcon.classList.add("fa-eye-slash");
    passwordInput.type = "text";
  } else {
    eyeIcon.classList.remove("fa-eye-slash");
    eyeIcon.classList.add("fa-eye");
    passwordInput.type = "password";
  }
});
```

