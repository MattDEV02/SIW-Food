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
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
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
   public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/**")
              .addResourceLocations(AuthConfiguration.CLASSPATH_RESOURCE_LOCATIONS)
      //.setCachePeriod(0)
      ;
   }


   @Autowired
   public void configureGlobal(@NonNull AuthenticationManagerBuilder auth)
           throws Exception {
      auth.jdbcAuthentication()
              //use the autowired datasource to access the saved credentials
              .dataSource(this.dataSource)
              //retrieve username and role
              .authoritiesByUsernameQuery("SELECT username, role FROM Credenziali WHERE username = ?")
              //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
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
                      auth -> auth
                              .requestMatchers(HttpMethod.GET, "/", "/register", "/login", "/logout", "/css/**", "/js/**", "/images/**").permitAll()
                              .requestMatchers(HttpMethod.POST, "/register").permitAll()
                              .requestMatchers(new AntPathRequestMatcher("/dashboard/cuochi/register", null)).hasAnyAuthority(Roles.AMMINISTRATORE_ROLE.toString())
                              .requestMatchers(new AntPathRequestMatcher("/dashboard/cuochi/delete/**", null)).hasAnyAuthority(Roles.AMMINISTRATORE_ROLE.toString())
                              .requestMatchers(new AntPathRequestMatcher("/dashboard/cuochi", null)).permitAll()
                              .requestMatchers(new AntPathRequestMatcher("/dashboard/ricette/register", null)).hasAnyAuthority(Roles.AMMINISTRATORE_ROLE.toString(), Roles.REGISTRATO_ROLE.toString())
                              .requestMatchers(new AntPathRequestMatcher("/dashboard/ricette/delete/**", null)).hasAnyAuthority(Roles.AMMINISTRATORE_ROLE.toString(), Roles.REGISTRATO_ROLE.toString())
                              .requestMatchers(new AntPathRequestMatcher("/dashboard/ricette", null)).permitAll()
                              .requestMatchers(new AntPathRequestMatcher("/dashboard/ingredienti/register", null)).hasAnyAuthority(Roles.AMMINISTRATORE_ROLE.toString(), Roles.REGISTRATO_ROLE.toString())
                              .requestMatchers(new AntPathRequestMatcher("/dashboard/ingredienti/delete/**", null)).hasAnyAuthority(Roles.AMMINISTRATORE_ROLE.toString(), Roles.REGISTRATO_ROLE.toString())
                              .requestMatchers(new AntPathRequestMatcher("/dashboard/ingredienti", null)).permitAll()
                              .requestMatchers(HttpMethod.GET, "/dashboard/**").authenticated()
                              .requestMatchers(HttpMethod.POST, "/dashboard/**").authenticated()
                              .anyRequest().authenticated()
              )
              .formLogin(formLogin -> formLogin
                      .loginPage("/login")
                      .defaultSuccessUrl("/dashboard", true)
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