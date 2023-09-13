package org.example.dao;

import org.example.model.Emprunte;
import org.example.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;


public class EmprunteDaoImpl implements EmprunteDao {



    @Override
    public void emprunte(Emprunte emprunte) {
        Connection con = DBConnection.getConnection();
        if (con == null){
            return;
        }
        String query = "INSERT INTO emprunte (date_emprunte,date_retour,isbn,num_national) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)){

            preparedStatement.setDate(1, Utils.getSqlDate(emprunte.getDate_emprunte()));
            preparedStatement.setDate(2, Utils.getSqlDate(emprunte.getDate_retour()));
            preparedStatement.setInt(3,emprunte.getIsbn());
            preparedStatement.setInt(4,emprunte.getNum_national());

            preparedStatement.executeUpdate();
        } catch (SQLException se){
            se.printStackTrace();
        }
        String query2 = "UPDATE `livre` SET `available`=1 WHERE `isbn`=?";
        try (PreparedStatement preparedStatement2 = con.prepareStatement(query2);){
            preparedStatement2.setInt(1,emprunte.getIsbn());

            preparedStatement2.executeUpdate();
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
    public void retourLivre(int isbn) {
        Connection con = DBConnection.getConnection();
        if (con == null){
            return;
        }

        String query = "UPDATE `livre` SET `available`=0 WHERE `isbn`=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query);){
            preparedStatement.setInt(1,isbn);

            preparedStatement.executeUpdate();
        } catch (SQLException se){
            se.printStackTrace();
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void perduLivre(int isbn) {
        Connection con = DBConnection.getConnection();
        if (con == null){
            return;
        }
        String query = "UPDATE `livre` SET `available`=-1 WHERE `isbn`=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query);){
            preparedStatement.setInt(1,isbn);

            preparedStatement.executeUpdate();
        } catch (SQLException se){
            se.printStackTrace();
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
