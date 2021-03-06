package FermeEscritoriodb;

import java.io.IOException;
import java.sql.*;

public class Conexion {

    public static Connection getConexion() throws IOException {

        Connection conexion = null;
        String pass = new configInput().recuperarDato("pass");
        String user = new configInput().recuperarDato("user");

        try {
            String DriverClassName = "oracle.jdbc.driver.OracleDriver";
            String driverUrl = "jdbc:oracle:thin:@fermebd.cay6gjof12jf.us-east-2.rds.amazonaws.com:1521:FERMEBD";
            Class.forName(DriverClassName);
            conexion = DriverManager.getConnection(driverUrl, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error de conexion" + e.getMessage());
        }
        return conexion;
    }
}
