package com.siw.siwfood.service;

import com.siw.siwfood.model.*;
import com.siw.siwfood.repository.*;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class RicettaServiceTests {
   
   @Autowired
   private RicettaService ricettaService;

   @Autowired
   private CuocoRepository cuocoRepository;

   @Autowired
   private UtenteRepository utenteRepository;

   @Autowired
   private CredenzialiRepository credenzialiRepository;

   private Cuoco cuoco;

   @BeforeEach
   public void setUp() {
      Credenziali credenziali = new Credenziali("USERNAME", "PASSWORD");
      this.credenzialiRepository.save(credenziali);
      Utente utente = new Utente("Matteo", "Lambertucci", new Date(), credenziali);
      this.utenteRepository.save(utente);
      this.cuoco = new Cuoco(utente);
      this.cuocoRepository.save(cuoco);
   }

   @After
   public void tearDown() {

   }

   @Test
   @Transactional
   public void testCascadePersist() {
      Ricetta ricetta = new Ricetta(cuoco);
      ricetta.setNome("Pasta");
      ricetta.setDescrizione("Delicious pasta.");

      Ingrediente ingrediente1 = new Ingrediente(ricetta);
      ingrediente1.setNome("Pomodoro");
      ingrediente1.setQuantita(2);

      Ingrediente ingrediente2 = new Ingrediente(ricetta);
      ingrediente2.setNome("Basilico");
      ingrediente2.setQuantita(1);

      ricetta.setIngredienti(Arrays.asList(ingrediente1, ingrediente2));

      Ricetta savedRicetta = ricettaService.saveRicetta(ricetta, 0);
      assertNotNull(savedRicetta);
      assertNotNull(savedRicetta.getId());
      assertNotNull(ingrediente1.getId());
      assertNotNull(ingrediente2.getId());

      assertTrue(this.ricettaService.existsIngredienteById(ingrediente1.getId()));
      assertTrue(this.ricettaService.existsIngredienteById(ingrediente2.getId()));
      assertTrue(this.ricettaService.existsRicettaById(savedRicetta.getId()));
   }

   @Test
   @Transactional
   public void testCascadeMerge() {
      Ricetta ricetta = new Ricetta(cuoco);
      ricetta.setNome("Pasta");
      ricetta.setDescrizione("Delicious pasta.");

      Ingrediente ingrediente1 = new Ingrediente(ricetta);
      ingrediente1.setNome("Pomodoro");
      ingrediente1.setQuantita(2);

      Ingrediente ingrediente2 = new Ingrediente(ricetta);
      ingrediente2.setNome("Basilico");
      ingrediente2.setQuantita(1);

      ricetta.setIngredienti(Arrays.asList(ingrediente1, ingrediente2));

      Ricetta savedRicetta = ricettaService.saveRicetta(ricetta, 0);
      assertNotNull(savedRicetta);
      assertNotNull(savedRicetta.getId());
      assertNotNull(ingrediente1.getId());
      assertNotNull(ingrediente2.getId());
      Ricetta nuovaRicetta = new Ricetta();
      nuovaRicetta.setNome("Nuova pasta");
      nuovaRicetta.setDescrizione("Nuova delicious pasta.");
      nuovaRicetta.setCuoco(this.cuoco);
      Ricetta updatedRicetta = ricettaService.updateRicetta(ricetta, nuovaRicetta, 0);
      assertNotNull(updatedRicetta);
      assertNotNull(updatedRicetta.getId());
      assertNotNull(ingrediente1.getId());
      assertNotNull(ingrediente2.getId());

      assertTrue(this.ricettaService.existsIngredienteById(ingrediente1.getId()));
      assertTrue(this.ricettaService.existsIngredienteById(ingrediente2.getId()));
      assertTrue(this.ricettaService.existsRicettaById(updatedRicetta.getId()));
   }

   @Test
   @Transactional
   public void testCascadeDelete() {
      Ricetta ricetta = new Ricetta(cuoco);
      ricetta.setNome("Pasta");
      ricetta.setDescrizione("Delicious pasta");

      Ingrediente ingrediente1 = new Ingrediente(ricetta);
      ingrediente1.setNome("Pomodoro");
      ingrediente1.setQuantita(2);

      Ingrediente ingrediente2 = new Ingrediente(ricetta);
      ingrediente2.setNome("Basilico");
      ingrediente2.setQuantita(1);

      ricetta.setIngredienti(Arrays.asList(ingrediente1, ingrediente2));

      Ricetta savedRicetta = ricettaService.saveRicetta(ricetta, 0);

      assertNotNull(savedRicetta);

      assertNotNull(savedRicetta.getId());
      assertNotNull(ingrediente1.getId());
      assertNotNull(ingrediente2.getId());

      ricettaService.deleteRicetta(savedRicetta);

      assertFalse(this.ricettaService.existsIngredienteById(ingrediente1.getId()));
      assertFalse(this.ricettaService.existsIngredienteById(ingrediente2.getId()));
      assertFalse(this.ricettaService.existsRicettaById(savedRicetta.getId()));
   }
}
