package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DbContext {

    
    private String url = "jdbc:postgresql://db.qmorknugujislhmfrnlm.supabase.co:5432/postgres?user=postgres&password=edTyG9jx4mWBKzpz";
    private String usuario = "postgres";
    private String senha = "edTyG9jx4mWBKzpz";

    // Objeto de conexão com o banco de dados
    public Connection connection = null;

    public DbContext() {
    }

    // Método para realizar a conexão com o banco de dados
    public void connectbank() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.usuario, this.senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para desconectar do banco de dados
    public void disconnectbank() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para executar uma consulta SQL que requer um retorno (SELECT)
    public ResultSet executeQuerySql(String query) {
        try {
            Statement stmt = this.connection.createStatement();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para executar uma consulta SQL que não requer um retorno (INSERT/UPDATE/DELETE)
    public boolean executeUpdateSql(String query) {
        try {
            Statement stmt = this.connection.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para preparar uma instrução SQL
    public PreparedStatement prepareStatement(String query) {
        connectbank();
        try {
            return connection.prepareStatement(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}