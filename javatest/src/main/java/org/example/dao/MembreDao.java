package org.example.dao;

import org.example.model.Membre;

import java.util.List;

public interface MembreDao {
    List<Membre> findAll();

    Membre findByNum_national(int num_national);
    int save(Membre membre);

    int save();
    void update (Membre membre);

    void deleteByNum_national(int num_national);

    void saveMember(Membre membre) ;

    // List<Membre> findAllByAuteurOrTitre(String AuteurTitre);
}
