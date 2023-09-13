package org.example.model;

public class Membre {

    private int num_national;
    private String nom;
    private String prenom;

    public Membre(int num_national, String nom, String prenom) {
        this.nom = nom;
        this.num_national = num_national;
        this.prenom = prenom;
    }

    public Membre() {

    }

    public int getNum_national() {
        return num_national;
    }

    public void setNum_national(int num_national) {
        this.num_national = num_national;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "num_national=" + num_national +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}

