package com.siw.siwfood.repository;

import com.siw.siwfood.model.Credenziali;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {
   public Optional<Credenziali> findByUsername(String username);

   public Boolean existsByUsername(String username);
}
