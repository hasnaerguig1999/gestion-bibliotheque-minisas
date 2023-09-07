package org.example.dao;

import org.example.model.Membre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class MembreDaoImpl implements MembreDao{
    @Override
    public List<Membre> findAll() {
        Connection con =DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        List<Membre> Membres = new LinkedList<>();

        String query = "SELECT * FROM membre";
        try (PreparedStatement preparedStatement = con.prepareStatement((query))){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Membre Membre = new Membre(resultSet.getInt("num_national"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"));
                Membres.add(Membre);
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
    public Membre findByIsbn(int num_national) {
        return null;
    }

    @Override
    public void save(Membre Membre) {
        Connection con = DBConnection.getConnection();
        if (con == null){
            return;
        }

        if(Membre.getNum_national() > 0){ // update
            String query = "UPDATE `membre` SET `num_national`=?,`Nom`=?,`prenom`=?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query);){
                preparedStatement.setInt(1,Membre.getNum_national());
                preparedStatement.setString(2,Membre.getNom());
                preparedStatement.setString(3,Membre.getPrenom());

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
        }else { //create
            String query = "INSERT INTO membre (`nom`,`prenom`,) VALUES (?,?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query);){
                preparedStatement.setString(1,Membre.getNom());
                preparedStatement.setString(2,Membre.getPrenom());

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

    @Override
    public void deleteByIsbn(int num_national) {

    }

    @Override
    public void deleteByNum_national(int num_national) {
        Connection con = DBConnection.getConnection();
        if (con == null){
            return;
        }
        String query = "DELETE FROM `membre` WHERE `num_national` = ?";
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

