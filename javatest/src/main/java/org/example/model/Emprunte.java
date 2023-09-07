package org.example.model;

import java.util.Date;

public class Emprunte {
    private Date date_emprunte;
    private Date date_retour;
    private int isbn;
    private int num_national;

    public Date getDate_emprunte() {
        return date_emprunte;
    }

    public void setDate_emprunte(Date date_emprunte) {
        this.date_emprunte = date_emprunte;
    }

    public Date getDate_retour() {
        return date_retour;
    }

    public void setDate_retour(Date date_retour) {
        this.date_retour = date_retour;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public int getNum_national() {
        return num_national;
    }

    public void setNum_national(int num_national) {
        this.num_national = num_national;
    }

    @Override
    public String toString() {
        return "Emprunte{" +
                "date_emprunte=" + date_emprunte +
                ", date_retour=" + date_retour +
                ", isbn=" + isbn +
                ", num_national=" + num_national +
                '}';
    }

    public Emprunte() {
    }
}
