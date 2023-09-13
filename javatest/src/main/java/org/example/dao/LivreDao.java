package org.example.dao;
import org.example.model.Livre;

import java.util.Date;
import java.util.List;



public interface LivreDao {
     List<Livre> findAll();
     Livre findByIsbn(int isbn);
     void save(Livre livre);
     void deleteByIsbn(int isbn);

     void checkAvailable();

     List<Livre> findAllByAuteurOrTitre(String AuteurTitre);

     List<Livre> findAllDisponible();

     List<Livre> findAllEmprunte();

     int statistique(int available);

     void perduBook(String ISBN);
}
