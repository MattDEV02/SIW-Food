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
                              .requestMatchers(HttpMethod.GET,"/cuochi/delete/cuoco/**").hasAnyAuthority(Roles.AMMINISTRATORE.toString())
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