package org.example.dao;
import org.example.model.Livre;

import java.util.List;



public interface LivreDao {
     List<Livre> findAll();
     Livre findByIsbn(int isbn);
     void save(Livre livre);
     void deleteByIsbn(int isbn);

     List<Livre> findAllByAuteurOrTitre(String AuteurTitre);
}
