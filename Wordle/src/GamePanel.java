import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    JTextField row1letter1, row1letter2, row1letter3, row1letter4, row1letter5, row2letter1, row2letter2, row2letter3, row2letter4, row2letter5, row3letter1, row3letter2, row3letter3, row3letter4, row3letter5, row4letter1, row4letter2, row4letter3, row4letter4, row4letter5, row5letter1, row5letter2, row5letter3, row5letter4, row5letter5;
    ArrayList<JTextField> textFieldList = new ArrayList<>();
    JButton btn_submit, btn_newGame;
    SelectWord selectWord;
    ArrayList<JTextField> usedTxtFields = new ArrayList<>();
    boolean isWordCorrect = false;
    int tries = 0;

    String word;

    public GamePanel() throws SQLException {
        this.setLayout(null);
        this.setBackground(new Color(0x121213));

        selectWord = new SelectWord();

        word = selectWord.getWord();

        addTxtFieldsToList();
        makeTxtFields();
        disableTxTFields();

        btn_submit = new JButton("Submit");
        btn_submit.setBounds(302, 410, 100, 50);
        this.add(btn_submit);

        btn_newGame = new JButton("New Game");
        btn_newGame.setBounds(82, 410, 100, 50);
        this.add(btn_newGame);

        btn_submit.addActionListener(this);
        btn_submit.addKeyListener(this);
        btn_newGame.addActionListener(this);
        btn_newGame.addKeyListener(this);
    }

    public void makeTxtFields(){
        int counter = 0;
        int x = 82;
        int y = 70;
        int width = 60;
        int height = 60;

        for(JTextField txtfield : textFieldList){
            txtfield.setBackground(new Color(0x121213));
            txtfield.setForeground(Color.WHITE);
            txtfield.setBounds(x,y,width,height);
            txtfield.setFont(new Font("Sanserif", 0, 30));
            txtfield.setHorizontalAlignment(JTextField.CENTER);
            txtfield.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            txtfield.setDocument(new JTextFieldLimit(1));
            txtfield.getDocument().addDocumentListener(new MoveFocusForwardHandler(1));
            txtfield.addKeyListener(this);
            this.add(txtfield);

            x += 65;
            counter++;

            if(counter >= 5){
                x = 82;
                y += 65;
                counter = 0;
            }
        }
    }

    public void disableTxTFields(){
        int counter = 0;

        for(JTextField txtfield : textFieldList){
            txtfield.setEditable(true);
            if(txtfield.getText().equalsIgnoreCase("")){
                counter++;
            }else{
                txtfield.setEditable(false);
            }

            if(counter > 5){
                txtfield.setEditable(false);
            }
        }
    }

    public void checkIfWordCorrect(){
        String userWord = "";
        int counter = 0;

        for(JTextField textField : textFieldList){
            if(textField.isEditable() && !isTxtFieldUsed(textField)){
                userWord = userWord + textField.getText();

                if(checkCorrectLetters(textField.getText().charAt(0))){
                    textField.setBackground(Color.ORANGE);
                }else{
                    textField.setBackground(Color.GRAY);
                }

                if(checkIfSamePostion(textField.getText().charAt(0), counter)){
                    textField.setBackground(Color.GREEN);
                }else{
                    textField.setBackground(Color.GRAY);
                }

                counter++;
                usedTxtFields.add(textField);
                textField.setEditable(false);
            }
        }

        if(userWord.equalsIgnoreCase(word)){
            isWordCorrect = true;
            System.out.println("NICE");
        }

        //System.out.println(userWord);
        //System.out.println(word);
    }

    public boolean checkIfInputCorrect(){
        int counter = 0;
        for(JTextField txtField : textFieldList){
            if(!isTxtFieldUsed(txtField)){
                if(!txtField.getText().equalsIgnoreCase("")){
                    counter++;
                }
            }
        }
        if(counter >= 5) {
            return true;
        }else{
            return false;
        }
    }

    public boolean checkCorrectLetters(char x){
        char[] charlist = new char[word.length()];

        for(int i = 0, j = 0; i < word.length(); i++){
            charlist[i] = word.charAt(i);
        }

        for(int i = 0; i < word.length(); i++){
            if(charlist[i] == x){
                return true;
            }
        }
        return false;
    }

    public boolean checkIfSamePostion(char x, int index){
        for(int i = 0; i < word.length(); i++){
            if(word.charAt(index) == x){
                return true;
            }
        }
        return false;
    }

    public boolean isTxtFieldUsed(JTextField txtField){
        for(JTextField txtField1 : usedTxtFields){
            if(txtField == txtField1){
                return true;
            }
        }
        return false;
    }

    public void everythingUneditable(){
        for(JTextField txtField : textFieldList){
            txtField.setEditable(false);
        }
    }

    public void resetTxtFields(){
        for(JTextField txtField : textFieldList){
            txtField.setText("");
            txtField.setEditable(true);
            txtField.setBackground(new Color(0x121213));
        }
    }

    public boolean lost(){
        if(textFieldList.size() == usedTxtFields.size()){
            everythingUneditable();
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_submit){
            if(checkIfInputCorrect()) {
                checkIfWordCorrect();
                disableTxTFields();

                if (isWordCorrect) {
                    JOptionPane.showMessageDialog(null, "Correct word! The word was: " + word);
                    everythingUneditable();
                }
                if (lost()) {
                    JOptionPane.showMessageDialog(null, "You lost! The word was: " + word);
                }
            }else{
                System.out.println("Wrong input");
            }
        }

        if(e.getSource() == btn_newGame){
            try {
                selectWord = new SelectWord();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            word = selectWord.getWord();
            isWordCorrect = false;
            resetTxtFields();
            disableTxTFields();
            usedTxtFields.clear();
            tries = 0;
        }
    }

    public void addTxtFieldsToList(){
        row1letter1 = new JTextField();
        textFieldList.add(row1letter1);

        row1letter2 = new JTextField();
        textFieldList.add(row1letter2);

        row1letter3 = new JTextField();
        textFieldList.add(row1letter3);

        row1letter4 = new JTextField();
        textFieldList.add(row1letter4);

        row1letter5 = new JTextField();
        textFieldList.add(row1letter5);

        row2letter1 = new JTextField();
        textFieldList.add(row2letter1);

        row2letter2 = new JTextField();
        textFieldList.add(row2letter2);

        row2letter3 = new JTextField();
        textFieldList.add(row2letter3);

        row2letter4 = new JTextField();
        textFieldList.add(row2letter4);

        row2letter5 = new JTextField();
        textFieldList.add(row2letter5);

        row3letter1 = new JTextField();
        textFieldList.add(row3letter1);

        row3letter2 = new JTextField();
        textFieldList.add(row3letter2);

        row3letter3 = new JTextField();
        textFieldList.add(row3letter3);

        row3letter4 = new JTextField();
        textFieldList.add(row3letter4);

        row3letter5 = new JTextField();
        textFieldList.add(row3letter5);

        row4letter1 = new JTextField();
        textFieldList.add(row4letter1);

        row4letter2 = new JTextField();
        textFieldList.add(row4letter2);

        row4letter3 = new JTextField();
        textFieldList.add(row4letter3);

        row4letter4 = new JTextField();
        textFieldList.add(row4letter4);

        row4letter5 = new JTextField();
        textFieldList.add(row4letter5);

        row5letter1 = new JTextField();
        textFieldList.add(row5letter1);

        row5letter2 = new JTextField();
        textFieldList.add(row5letter2);

        row5letter3 = new JTextField();
        textFieldList.add(row5letter3);

        row5letter4 = new JTextField();
        textFieldList.add(row5letter4);

        row5letter5 = new JTextField();
        textFieldList.add(row5letter5);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(checkIfInputCorrect()) {
                checkIfWordCorrect();
                disableTxTFields();

                if (isWordCorrect) {
                    JOptionPane.showMessageDialog(null, "Correct word! The word was: " + word);
                    everythingUneditable();
                }
                if (lost()) {
                    JOptionPane.showMessageDialog(null, "You lost! The word was: " + word);
                }
            }else{
                System.out.println("Wrong input");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
