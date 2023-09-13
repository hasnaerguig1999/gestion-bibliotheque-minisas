package org.example.model;

import java.util.Date;

public class Livre {
    private int isbn;
    private String titre;
    private String auteur;

    private int available;

    public Livre() {
    }

    public Livre(int isbn, String titre, String auteur, int available) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.available = available;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "isbn=" + isbn +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", available=" + available +
                '}';
    }
}
