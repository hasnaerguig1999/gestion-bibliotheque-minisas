package org.example.model;

public class Livre {
    private int isbn;
    private String titre;
    private String auteur;

    private String available;

    public Livre() {
    }

    public Livre(int isbn, String titre, String auteur,  String available) {
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



    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Livre{isbn=" + isbn +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", available='" + available + '\'' +
                '}';
    }
}
