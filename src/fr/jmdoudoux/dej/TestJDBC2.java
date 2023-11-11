package fr.jmdoudoux.dej;

import java.sql.*;

public class TestJDBC2 {

    private static void affiche(String message) {
        System.out.println(message);
    }

    private static void arret(String message) {
        System.err.println(message);
        System.exit(99);
    }

    public static void main(java.lang.String[] args) {
        Connection con = null;
        ResultSet resultats = null;
        String requete = "";

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException e) {
            arret("Impossible de charger le pilote jdbc:odbc");
        }

        affiche("connexion a la base de données");
        try {

            String DBurl = "jdbc:odbc:testDB";
            con = DriverManager.getConnection(DBurl);
            PreparedStatement recherchePersonne =
                    con.prepareStatement("SELECT * FROM personnes WHERE nom_personne = ?");

            recherchePersonne.setString(1, "nom3");

            resultats = recherchePersonne.executeQuery();

            affiche("parcours des données retournées");

            boolean encore = resultats.next();

            while (encore) {
                System.out.print(resultats.getInt(1) + " :  "+resultats.getString(2)+" "+
                        resultats.getString(3)+"("+resultats.getDate(4)+")");
                System.out.println();
                encore = resultats.next();
            }

            resultats.close();
        } catch (SQLException e) {
            arret(e.getMessage());
        }

        affiche("fin du programme");
        System.exit(0);
    }
}