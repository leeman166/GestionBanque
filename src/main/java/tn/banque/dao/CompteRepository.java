package tn.banque.dao;

import tn.banque.Entities.Compte;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, String>{

}
