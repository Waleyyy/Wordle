import javax.swing.*;
import java.sql.SQLException;

public class GameFrame extends JFrame {

    GamePanel gp;

    public GameFrame() throws SQLException {

        this.setTitle("Wordle");
        this.setSize(500,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        gp = new GamePanel();
        gp.setBounds(0,0,600,800);
        this.add(gp);
        gp.setVisible(true);

        this.setVisible(true);
    }
}
