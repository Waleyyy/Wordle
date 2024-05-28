import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class SelectWord {

    String word;
    Connection connection;
    Random random;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public SelectWord() throws SQLException {
        connection = SingletonConnection.getInstance().getConnection();
        slcWord();
    }

    public void slcWord() throws SQLException {
        random = new Random();
        int wid = random.nextInt(100);
        String sql = "SELECT word FROM words WHERE wid = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,wid);

        resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            word = resultSet.getString("word");
        }

        if(word == null){
            slcWord();
        }
    }

    public String getWord() {
        return word;
    }
}
