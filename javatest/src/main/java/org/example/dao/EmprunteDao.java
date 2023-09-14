package org.example.dao;

import org.example.model.Emprunte;

import java.util.Date;
import java.util.List;

public interface EmprunteDao {



    void emprunte(Emprunte emprunte);

    void retourLivre(int isbn);
    void perduLivre(int isbn);


}
