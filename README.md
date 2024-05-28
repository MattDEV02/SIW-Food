# SIW-Food

<p align="center">
<img  title="SIW-Food Logo"  alt="SIW-Food Logo"  width="19.5%"  src="/src/main/resources/static/images/logo/logo.png">
</p>

### Progetto SIW-Food, corso Sistemi informativi su Web, CDL Ingegneria informatica, Università degli studi di Roma 3.

## Modello di Dominio 🔢

<p align="center">
<img  title="SIW-Food Domain Model"  alt="SIW-Food Domain Model"  src="/src/main/resources/static/images/README/Domain_Model.png"  width="100%">
</p>

## Screenshots 📸

## `Pagina di registrazione Utenti`

<p align="center">
<img  title="SiwFood RegScreen screenshoot 1"  alt="SiwFood RegScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/registration/1.png"  width="100%">
</p>

## `Pagina di login Utenti`

<p align="center">
	<img  title="SiwFood LoginScreen screenshoot 1"  alt="SiwFood LoginScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/login/1.png"  width="100%">
</p>

## `Home page`

<p align="center">
	<img  title="SiwFood HomeScreen screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%">
<img  title="SiwFood HomeScreen screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%">
</p>

## `Pagina Cuochi`

<p align="center">
	<img  title="SiwFood HomeScreen screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%">
</p>

## `Pagina ricette`

<p align="center">
	<img  title="SiwFood HomeScreen screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%">
</p>

## `Pagina ricette ricercate`

<p align="center">
	<img  title="SiwFood HomeScreen screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%">
</p>

## `Pagina ingredienti`

<p align="center">
	<img  title="SiwFood HomeScreen screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%">
</p>

## `Pagina form ricetta`

<p align="center">
	<img  title="SiwFood HomeScreen screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%">
</p>

## `Pagina form ingrediente`

<p align="center">
	<img  title="SiwFood HomeScreen screenshoot 1"  alt="SiwFood HomeScreen screenshoot 1"  src="/src/main/resources/static/images/README/screenshots/home/1.png"  width="100%">
</p>

## Funzionalità Principali ✨

- **Utilizzo:** Gli utenti possono essere di 3 tipi, occasionali: pubblicare i loro prodotti, aggiungere prodotti al carrello, acquistare altri prodotti, cercare prodotti in una vasta gamma di categorie e altro ancora.

- **Responsive:** Il sito è responsive e user-friendly.

- **Sicurezza e controllo degli errori utente:** I dati sensibili dell'utente, come la password, vengono crittografati e memorizzati in un database molto robusto. Sono inoltre presenti controlli degli errori sia lato client che lato server.

- **Modularità:** Il progetto è diviso in vari moduli, package, fragments e directory.

- **Visualizzazione di guide tramite tooltips:** Sono presenti molti tooltips che guidano l'utente nel sito.

- **Lingua:** È disponibile solo la lingua italiana al momento.


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

## Installazione 🚀 e utilizzo ⚡

### Requisiti

- Java 17.
- Maven.
- PostgreSQL.

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