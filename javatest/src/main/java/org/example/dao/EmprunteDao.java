package org.example.dao;

import org.example.model.Emprunte;

import java.util.Date;
import java.util.List;

public interface EmprunteDao {
    //List<Emprunte> findAll();
    //Emprunte findByIsbn(int isbn);


    /*static void emprunte(Emprunte Emprunte) {

    }
    */


    void emprunte(Emprunte emprunte);

    void retourLivre(int isbn);
    void perduLivre(int isbn);


}
