import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {

    String url = "jdbc:postgresql://localhost:5432/wordle";
    String user = "postgres";
    String psw = "admin";

    private Connection connection;
    private static SingletonConnection datenbankInstanz;

    private SingletonConnection() throws SQLException{
        this.connection = DriverManager.getConnection(url, user, psw);
    }

    public static SingletonConnection getInstance() throws SQLException{
        if(datenbankInstanz == null || datenbankInstanz.getConnection().isClosed()){
            datenbankInstanz = new SingletonConnection();
        }
        return datenbankInstanz;
    }

    public Connection getConnection() {
        return connection;
    }
}
