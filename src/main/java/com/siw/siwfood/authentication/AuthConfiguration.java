package com.siw.siwfood.authentication;

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

   private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:" + ProjectPaths._STATIC + "/"};
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
              .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username = ?")
              //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
              .usersByUsernameQuery("SELECT username, password, TRUE AS enabled FROM credentials WHERE username = ?");
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
                              .requestMatchers(HttpMethod.GET, "/", "/registration", "/login", "/forgotUsername", "/logout", "/FAQs", "/css/**", "/js/**", "/images/**").permitAll()
                              .requestMatchers(HttpMethod.POST, "/registerNewUser", "/sendForgotUsernameEmail").permitAll()
                           //   .requestMatchers(new RegexRequestMatcher(".*newSale.*", null)).hasAnyAuthority(Roles.SELLER_AND_BUYER_ROLE.toString(), Roles.SELLER_ROLE.toString())
                             // .requestMatchers(new RegexRequestMatcher(".*cart.*", null)).hasAnyAuthority(Roles.SELLER_AND_BUYER_ROLE.toString(), Roles.BUYER_ROLE.toString())
                              //.requestMatchers(new RegexRequestMatcher(".*order.*", null)).hasAnyAuthority(Roles.SELLER_AND_BUYER_ROLE.toString(), Roles.BUYER_ROLE.toString())
                              .requestMatchers(HttpMethod.DELETE).denyAll()
                           //   .requestMatchers(HttpMethod.GET, "/" + APIPrefixes.DASHBOARD + "/**").authenticated()
                             // .requestMatchers(HttpMethod.POST, "/" + APIPrefixes.DASHBOARD + "/**").authenticated()
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
                      .invalidateHttpSession(true)
                      .clearAuthentication(true)
                      .deleteCookies("JSESSIONID")
                      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                      .clearAuthentication(true)
                      .permitAll());
      return httpSecurity.build();
   }

}