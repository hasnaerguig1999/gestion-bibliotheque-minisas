package org.example.dao;

import org.example.model.Membre;

import java.util.List;

public interface MembreDao {
    List<Membre> findAll();
    Membre findByIsbn(int num_national);
    void save(Membre membre);
    void deleteByIsbn(int num_national);

    void deleteByNum_national(int num_national);

    // List<Membre> findAllByAuteurOrTitre(String AuteurTitre);
}
