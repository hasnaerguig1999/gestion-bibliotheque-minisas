package org.example.dao;
import org.example.model.Livre;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.example.dao.DBConnection.getConnection;

public class LivreDaoImpl implements LivreDao {


    @Override
    public List<Livre> findAll() {
        Connection con = getConnection();
        if (con == null) {
            return null;
        }
        List<Livre> livres = new LinkedList<>();
        String query = "SELECT * FROM livre";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Livre livre = new Livre(resultSet.getInt("isbn"),
                        resultSet.getString("auteur"),
                        resultSet.getString("titre"),
                        resultSet.getInt("available"));
                livres.add(livre);
            }


        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return livres;
    }

    @Override
    public Livre findByIsbn(int isbn) {
        Connection con = getConnection();
        if (con == null) {
            return null;
        }
        String query = "SELECT * FROM `livre` WHERE `isbn` = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Livre livre = new Livre(resultSet.getInt("isbn"),
                        resultSet.getString("auteur"),
                        resultSet.getString("titre"),
                        resultSet.getInt("available"));
                return livre;
            }


        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();

            }
        }
        return null;
    }

    @Override
    public void save(Livre livre) {

        Connection con = getConnection();
        if (con == null) {
            return;
        }
        if (livre.getIsbn() > 0) { //update
            String query = "UPDATE livre SET auteur=?,titre=?,available=? WHERE isbn = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

                preparedStatement.setString(1, livre.getAuteur());
                preparedStatement.setString(2, livre.getTitre());
                preparedStatement.setInt(3, livre.getAvailable());
                preparedStatement.setInt(4, livre.getIsbn());


                preparedStatement.executeUpdate();
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else {
            //Create
            String query = "INSERT INTO `livre`(`titre`,`auteur`,`available`) VALUES (?,?,?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

                preparedStatement.setString(1, livre.getTitre());
                preparedStatement.setString(2, livre.getAuteur());
                preparedStatement.setInt(3, livre.getAvailable());
                preparedStatement.executeUpdate();
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteByIsbn(int isbn) {
        Connection con = getConnection();
        if (con == null) {
            return;
        }
        String query = "DELETE from livre WHERE isbn=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, isbn);
            preparedStatement.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    @Override
    public void checkAvailable() {
        Date date_aujourdHui = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date_aujourdHui);
        int anneeAujourdHui = calendar.get(Calendar.YEAR);
        int moisAujourdHui = calendar.get(Calendar.MONTH) + 1;
        int jourAujourdHui = calendar.get(Calendar.DAY_OF_MONTH);

        String query = "SELECT * FROM emprunte e, livre l WHERE e.isbn = l.isbn AND l.available = 1 ORDER BY e.date_retour DESC";

        try (Connection con = getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int isbn = resultSet.getInt("isbn");
                String date_retour = String.valueOf(resultSet.getDate("date_retour"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(date_retour);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(date);
                int anneeRetour = calendar2.get(Calendar.YEAR);
                int moisRetour = calendar2.get(Calendar.MONTH) + 1;
                int jourRetour = calendar2.get(Calendar.DAY_OF_MONTH);

                if (anneeRetour > anneeAujourdHui) {
                    return;
                }
                if (anneeAujourdHui > anneeRetour) {
                    perduBook(isbn);
                    return;
                }
                if (anneeAujourdHui == anneeRetour) {
                    if (moisRetour > moisAujourdHui) {
                        return;
                    }
                    if (moisRetour < moisAujourdHui) {
                        perduBook(isbn);
                        return;
                    }
                    if (moisRetour == moisAujourdHui) {
                        if (jourRetour > jourAujourdHui) {
                            return;
                        }
                        if (jourRetour < jourAujourdHui) {
                            perduBook(isbn);
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Livre> findAllByAuteurOrTitre(String AuteurTitre) {

        Connection con = getConnection();
        if (con == null) {
            return null;
        }

        List<Livre> Livres = new LinkedList<>();

        String query = "SELECT * FROM livre WHERE auteur LIKE '%" + AuteurTitre + "%' OR Titre LIKE '%" + AuteurTitre + "%'";
        try (PreparedStatement preparedStatement = con.prepareStatement((query))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Livre livre = new Livre(resultSet.getInt("isbn"),
                        resultSet.getString("auteur"),
                        resultSet.getString("titre"),
                        resultSet.getInt("available"));

                Livres.add(livre);

            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return Livres;
    }

    @Override
    public List<Livre> findAllDisponible() {
        Connection con = getConnection();
        if (con == null) {
            return null;
        }

        List<Livre> Livres = new LinkedList<>();

        String query = "SELECT * FROM livre WHERE available = 0";
        try (PreparedStatement preparedStatement = con.prepareStatement((query))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Livre livre = new Livre(resultSet.getInt("isbn"),
                        resultSet.getString("auteur"),
                        resultSet.getString("titre"),
                        resultSet.getInt("available"));
                Livres.add(livre);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return Livres;
    }

    @Override
    public List<Livre> findAllEmprunte() {
        Connection con = getConnection();
        if (con == null) {
            return null;
        }

        List<Livre> Livres = new LinkedList<>();

        String query = "SELECT * FROM livre WHERE available = 1";
        try (PreparedStatement preparedStatement = con.prepareStatement((query))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Livre livre = new Livre(resultSet.getInt("isbn"),
                        resultSet.getString("auteur"),
                        resultSet.getString("titre"),
                        resultSet.getInt("available"));
                Livres.add(livre);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return Livres;
    }

    @Override
    public int statistique(int available) {
        int Statistique = 0;
        Connection con = getConnection();
        if (con == null) {
            return 0;
        }
        if (available == 2){
            String query = "SELECT COUNT(*) AS Statistique FROM livre WHERE available = -1 OR available = 0 OR available = 1";
            try (PreparedStatement preparedStatement = con.prepareStatement(query);) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Statistique = resultSet.getInt("Statistique");
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
        } else{
            String query = "SELECT COUNT(*) AS Statistique FROM livre WHERE available = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query);) {
                preparedStatement.setInt(1, available);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Statistique = resultSet.getInt("Statistique");
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
        }
        return Statistique;
    }

    @Override
    public void perduBook(String ISBN) {

    }


    public void perduBook(int isbn) {
        Connection con = getConnection();
        if (con == null){
            return;
        }
        String query = "UPDATE livre SET available=-1 WHERE isbn=?";
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


