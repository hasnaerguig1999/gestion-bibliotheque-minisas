package org.example.dao;

import org.example.model.Emprunte;
import org.example.model.Membre;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class MembreDaoImpl implements MembreDao {

    private Scanner scanner;

    public MembreDaoImpl(Scanner scanner){
        this.scanner = scanner;
    }
    @Override
    public List<Membre> findAll() {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        List<Membre> Membres = new LinkedList<>();

        String query = "SELECT * FROM membre";
        try (PreparedStatement preparedStatement = con.prepareStatement((query))){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Membre membre = new Membre (resultSet.getInt("num_national"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom")
                );
                Membres.add(membre);
            }

        } catch (SQLException se){
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se){
                se.printStackTrace();
            }
        }

        return Membres;
    }

    @Override
    public Membre findByNum_national(int num_national) {
        // À implémenter
        return null;
    }

    @Override
    public int save(Membre membre) {
        return 0;
    }

    @Override
        public void saveMember(Membre membre) {
        Connection con = DBConnection.getConnection();
        String query = "INSERT INTO membre(nom, prenom) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());

            int affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int save() {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return 0;
        }

        System.out.println("Entrez le numero national du membre : ");
        int num_national = scanner.nextInt();
        scanner.nextLine();

        if (num_national == 0){
        System.out.println("Entrez le nom du membre : ");
        String nom = scanner.nextLine();

        System.out.println("Entrez le prénom du membre : ");
        String prenom = scanner.nextLine();

        String query = "INSERT INTO membre(nom, prenom) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("L'insertion du membre a échoué, aucune ligne n'a été modifiée.");
            }

            // Retrieve the generated num_national (auto-incremented)
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int nouveauNumNational = -1; // Initialize to an invalid value
            if (generatedKeys.next()) {
                nouveauNumNational = generatedKeys.getInt(1);
            }

            if (nouveauNumNational != -1) {
                System.out.println("Le membre a été ajouté avec le numéro national : " + nouveauNumNational);
                System.out.println("Entrez l'ISBN du livre : ");
                int isbn = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                System.out.println("Entrez la date de retour du livre sous la forme yyyy-MM-dd : ");
                String dateRetour = scanner.nextLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = null;
                try {
                    date = dateFormat.parse(dateRetour);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                EmprunteDao emprunteDao = new EmprunteDaoImpl();
                Emprunte emprunte = new Emprunte(new Date(), date, isbn, nouveauNumNational);
                emprunteDao.emprunte(emprunte);
                System.out.println("L'emprunt a été enregistré : " + emprunte);

                // Return the newly created member's num_national
                return nouveauNumNational;
            } else {
                throw new SQLException("La récupération du numéro national généré a échoué.");
            }
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        } else {
            System.out.println("Entrez l'ISBN du livre : ");
            int isbn = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            System.out.println("Entrez la date de retour du livre sous la forme yyyy-MM-dd : ");
            String dateRetour = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = null;
            try {
                date = dateFormat.parse(dateRetour);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            EmprunteDao emprunteDao = new EmprunteDaoImpl();
            Emprunte emprunte = new Emprunte(new Date(), date, isbn, num_national);
            emprunteDao.emprunte(emprunte);
            System.out.println("L'emprunt a été enregistré : " + emprunte);

            // Return the newly created member's num_national
            return num_national;
        }
        return 0; // Return 0 in case of failure
    }

    @Override
    public void update(Membre membre) {
        Connection con = DBConnection.getConnection();
        String query = "UPDATE `membre` SET `nom`=? ,`prenom`= ? WHERE  `num_national`= ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query);){
            preparedStatement.setString(1,membre.getNom());
            preparedStatement.setString(2,membre.getPrenom());
            preparedStatement.setInt(3,membre.getNum_national());

            preparedStatement.executeUpdate();
        } catch (SQLException se){
            se.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void deleteByNum_national(int num_national) {
        Connection con = DBConnection.getConnection();
        if (con == null){
            return;
        }
        String query = "DELETE FROM membre WHERE num_national = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query);){

            preparedStatement.setInt(1,num_national);

            preparedStatement.executeUpdate();

        } catch (SQLException se){
            se.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}