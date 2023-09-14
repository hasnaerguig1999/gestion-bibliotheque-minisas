package org.example;

import org.example.dao.*;
import org.example.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int isbn;
    static String titre;
    static String auteur;
    static String AuteurTitre;
    static int Num_national;
    static String Nom;
    static String Prenom;
    static String date_retour;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LivreDao LivreDao = new LivreDaoImpl();
        EmprunteDao EmprunteDao = new EmprunteDaoImpl();
        MembreDao MembreDao = new MembreDaoImpl(scanner);

        int choix;

        // Application
        do {
            LivreDao.checkAvailable();
            System.out.println("Menu");
            System.out.println("1.Ajouter un livre");
            System.out.println("2.Afficher les livres disponible");
            System.out.println("3.Rechercher les livres");
            System.out.println("4.Emprunter un livre");
            System.out.println("5.Retourner livre");
            System.out.println("6.Afficher les livres empruntés");
            System.out.println("7.Modifier les livres");
            System.out.println("8.Voir les statistiques");
            System.out.println("9.Autres fonctions");
            System.out.println("0.Quit");

            System.out.print("Enterez votre choix (0-9): ");
            choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix) {
                case 1:

                    System.out.println("Ajouter un livre");
                    System.out.println("Enterez le titre du livre: ");
                    titre = scanner.nextLine();
                    System.out.println("Enterez nom de l'auteur du livre: ");
                    auteur = scanner.nextLine();
                    Livre livre = new Livre(0,titre,auteur,0);
                    LivreDao.save(livre);

                    break;
                case 2:
                    System.out.println("Afficher les livres disponibles");
                    LivreDao.findAllDisponible().forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Enterez nom de l'auteur du livre ou le titre de livre: ");
                    AuteurTitre = scanner.nextLine();
                    System.out.println("Nom de l'auteur du livre ou le titre de livre: "+AuteurTitre);
                    LivreDao.findAllByAuteurOrTitre(AuteurTitre).forEach(System.out::println);
                    break;
                case 4:
                    MembreDaoImpl membre = new MembreDaoImpl(scanner);
                    int  A = membre.save();
                    System.out.println(A);
                    break;

                case 5:
                    System.out.println("Enterez isbn de livre: ");
                    isbn = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.println("Entrez la date de retour du livre sous la forme yyyy-MM-dd : ");
                    date_retour = scanner.nextLine();
                    System.out.println(date_retour);
                    EmprunteDao.retourLivre(isbn);
                    break;
                case 6:
                    LivreDao.findAllEmprunte().forEach(System.out::println);
                    break;
                case 7:
                    System.out.println("Modifier un livre");
                    System.out.println("Enterez isbn du livre: ");
                    isbn= scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enterez le titre du livre: ");
                    titre = scanner.nextLine();
                    System.out.println("Enterez auteur du livre: ");
                    auteur = scanner.nextLine();
                    Livre livres = new Livre(isbn,titre,auteur,0);
                    LivreDao.save(livres);


                    break;
                case 8:
                    int totalBook = LivreDao.statistique(2);
                    int totalBookDisponible = LivreDao.statistique(0);
                    int totalBookEmprunte = LivreDao.statistique(1);
                    int totalBookPerdue = LivreDao.statistique(-1);
                    System.out.println("Voir les statistiques");
                    System.out.println("Totale Des Livres Disponibles: " + totalBookDisponible + " livres");
                    System.out.println("Totale Des Livres Empruntes: " + totalBookEmprunte + " livres");
                    System.out.println("Totale Des Livres Perdues: "+ totalBookPerdue + " livres");
                    System.out.println("Totale Des Livres: "+ totalBook + " livres");
                    break;

                case 9:
                    System.out.println("1.Afficher les membres");
                    System.out.println("2.Ajouter un membre");
                    System.out.println("3.Modifier un membre");
                    System.out.println("4.Supprimer un membre");
                    System.out.println("5.Supprimer livres par ISBN");
                    System.out.println("0.Quit");
                    System.out.print("Enterez votre choix (0-5): ");
                    choix = scanner.nextInt();
                    scanner.nextLine();
                    switch (choix){
                        case 1:

                            System.out.println("Afficher les membres");
                            MembreDao.findAll().forEach(System.out::println);
                            break;
                        case 2:
                            System.out.println("Ajoutez membre");
                            System.out.println("Enterez le nom de membre: ");
                            Nom= scanner.nextLine();
                            System.out.println("Enterez le prenom de membre: ");
                            Prenom= scanner.nextLine();
                            Membre Membre = new Membre(0,Nom,Prenom);
                            MembreDao.saveMember(Membre);
                            break;
                        case 3:
                            System.out.println("Modifier membre");
                            System.out.println("Enterez num national de membre: ");
                            Num_national = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Enterez le nouvelle nom  de membre ");
                            Nom = scanner.nextLine();
                            System.out.println("Enterez le nouvelle prenom  de membre ");
                            Prenom = scanner.nextLine();
                            Membre Membre2 = new Membre(Num_national,Nom,Prenom);
                            MembreDao.update(Membre2);




                            break;
                        case 4:
                            System.out.println("Supprimer membre");
                            System.out.println("Enterez num national de membre: ");
                            Num_national = scanner.nextInt();
                            MembreDao.deleteByNum_national(Num_national);
                            break;
                        case 5:
                            System.out.println("Entrez ISBN du livre: .");
                            isbn = scanner.nextInt();
                          LivreDao.deleteByIsbn(isbn);
                            System.out.println("Le Livre est Supprime.");
                            break;
                        case 0:
                            System.out.println("Fermeture du programme.");
                            break;
                        default:
                            System.out.println("Choix invalide. Selectionnez un choix valide.");
                    }
                    break;
                case 0:
                    System.out.println("Fermeture du programme.");
                    break;
                default:
                    System.out.println("Choix invalide. Selectionnez un choix valide.");
            }

            LivreDao.checkAvailable();
            System.out.print("Voulez-vous continuer? (1 = Oui, 0 = Non): ");

           // 2023-09-14


        } while (scanner.nextInt() != 0);

        System.out.println("Soyez les bienvenus !");
    }

}