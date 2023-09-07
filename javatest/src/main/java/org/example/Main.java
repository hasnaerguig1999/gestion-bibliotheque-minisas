package org.example;

import org.example.dao.MembreDao;
import org.example.dao.MembreDaoImpl;
import org.example.dao.LivreDao;
import org.example.dao.LivreDaoImpl;


public class Main {
    public static void main(String[] args) {
        LivreDao livreDao = new LivreDaoImpl();
        MembreDao membreDao = new MembreDaoImpl();
       // Livre livre = new Livre(0,"SEVEN","kelly","dispo" );
         //livreDao.save(livre);
        //livreDao.findAll().forEach(System.out::println);
        livreDao.deleteByIsbn(4);
        System.out.println("Done");

        //LivreDao.save(livre);
        String livreChercher = "kelly";
        livreDao.findAllByAuteurOrTitre(livreChercher).forEach(System.out::println);
        membreDao.findAll().forEach(System.out::println);


    }
}