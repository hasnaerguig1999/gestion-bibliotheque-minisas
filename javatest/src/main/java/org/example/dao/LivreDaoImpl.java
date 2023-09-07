package org.example.dao;
import org.example.model.Livre;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LivreDaoImpl implements LivreDao {


    @Override
    public List<Livre> findAll() {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }
        List<Livre> livres = new LinkedList<>();
        String query ="SELECT * FROM livre";
        try(PreparedStatement preparedStatement =con.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Livre livre = new Livre(resultSet.getInt("isbn"),
                        resultSet.getString("auteur"),
                        resultSet.getString("titre"),
                        resultSet.getString("available"));
                livres.add(livre);
            }




        }catch (SQLException se){
            se.printStackTrace();
        }finally {
            try {
                con.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
        return livres;
    }

    @Override
    public Livre findByIsbn(int isbn) {
        Connection con = DBConnection.getConnection();
        if(con == null){
        return null;
    }
        String query = "SELECT * FROM `livre` WHERE `isbn` = ?";
        try(PreparedStatement preparedStatement = con.prepareStatement(query)){
            preparedStatement.setInt(1,isbn);
            ResultSet resultSet =preparedStatement.executeQuery();
            if(resultSet.next()){
                Livre livre =new Livre(resultSet.getInt("isbn"),resultSet.getString("auteur"),resultSet.getString("titre"),resultSet.getString("available"));
                return livre;
            }


        }catch (SQLException se){
            se.printStackTrace();
        }finally {
            try {
                con.close();
            }catch (SQLException se){
                se.printStackTrace();

            }
        }
        return null;
    }
    @Override
    public void save(Livre livre) {
        Connection con =DBConnection.getConnection();
        if(con == null){
            return;
        }
        if(livre.getIsbn() >0){ //update
            String query = "UPDATE livre SET auteur=?,titre=?,available=? WHERE isbn = ?";
            try(PreparedStatement preparedStatement = con.prepareStatement(query)) {

                preparedStatement.setString( 1, livre.getAuteur());
                preparedStatement.setString( 2, livre.getTitre());
                preparedStatement.setString( 3, livre.getAvailable());
                preparedStatement.setInt( 4, livre.getIsbn());


                preparedStatement.executeUpdate();
            } catch (SQLException se){
                se.printStackTrace();
            }finally {
                try {
                    con.close();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else{
            //Create
            String query = "INSERT INTO `livre`(`auteur`, `titre`, `available`) VALUES (?,?,?)";
            try(PreparedStatement preparedStatement = con.prepareStatement(query)) {

                preparedStatement.setString( 1, livre.getAuteur());
                preparedStatement.setString( 2, livre.getTitre());
                preparedStatement.setString( 3, livre.getAvailable());
                preparedStatement.executeUpdate();
            } catch (SQLException se){
                se.printStackTrace();
            }finally {
                try {
                    con.close();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                }
            }

        }




    @Override
    public void deleteByIsbn(int isbn) {
        Connection con =DBConnection.getConnection();
        if(con == null){
            return;
        }
        String query="DELETE from livre WHERE isbn=?";
        try(PreparedStatement preparedStatement = con.prepareStatement(query)){
            preparedStatement.setInt(1,isbn);
            preparedStatement.executeUpdate();

        }catch (SQLException se){
            se.printStackTrace();
        }finally {
            try {
                con.close();
            }catch (SQLException se){
                se.printStackTrace();
            }
        }

    }

    @Override
    public List<Livre> findAllByAuteurOrTitre(String AuteurTitre) {

        Connection con =DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        List<Livre> Livres = new LinkedList<>();

        String query = "SELECT * FROM livre WHERE auteur LIKE '%"+AuteurTitre+"%' OR Titre LIKE '%"+AuteurTitre+"%'";
        try (PreparedStatement preparedStatement = con.prepareStatement((query))){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Livre livre = new Livre (resultSet.getInt("isbn"),
                        resultSet.getString("auteur"),
                        resultSet.getString("titre"),
                        resultSet.getString("available"));

                Livres.add(livre);

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
        System.out.println(Livres);
        return Livres;
    }

}

