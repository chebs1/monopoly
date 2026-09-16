package Java.MONOPOLY;


import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Settings {
    public int playerCount;
    public int startingMoney;
    public int rounds;
    public String p1;
    public String p2;
    public String p3;
    public String p4;
    
    public int startingPosition = 0;

    JLabel howManyPlayers = new JLabel("How Many Players?");
    JLabel howMuchMoney = new JLabel("How Much Starting Money?");
    JLabel howManyRounds = new JLabel("How Many Rounds?");
    JButton player1 = new JButton("1");
    JButton player2 = new JButton("2");
    JButton player3 = new JButton("3");
    JButton player4 = new JButton("4");
    JTextField playerField1 = new JTextField("Enter Name");
    JTextField playerField2 = new JTextField("Enter Name");
    JTextField playerField3 = new JTextField("Enter Name");
    JTextField playerField4 = new JTextField("Enter Name");
    JTextField moneyField = new JTextField("Enter Amount");
    JTextField roundsField = new JTextField("Enter Amount");

    final JButton applyButton = new JButton("Apply");
    
    private player player;
    private game game;

    public Settings(BoardPanel panel, game game) {
        this.game = game;

        applyButton.addActionListener(event -> {
            apply(); 
            System.out.println(game.numOfPlayers.toString());
            game.startGame = true;
            
            
        });

        player1.addActionListener(event -> {
                    playerCount = 1;
                    updatePFields();
            });
        player2.addActionListener(event -> {
                    playerCount = 2;
                    updatePFields();
            });
        player3.addActionListener(event -> {
                    playerCount = 3;
                    updatePFields();
            });
        player4.addActionListener(event -> {
                    playerCount = 4;
                    updatePFields();
            });

        playerField1.addActionListener(event -> {
                    p1 = playerField1.getText();                                
            });

        playerField2.addActionListener(event -> {
                    p2 = playerField2.getText();
            });

        playerField3.addActionListener(event -> {
                    p3 = playerField3.getText();
            });

        playerField4.addActionListener(event -> {
                    p4 = playerField4.getText();
            });

        moneyField.addActionListener(event -> {
                    startingMoney = Integer.valueOf(moneyField.getText());
            });

        roundsField.addActionListener(event -> {
                    rounds = Integer.valueOf(roundsField.getText());
            });

        howManyPlayers.setFont(new Font("Arial", Font.BOLD, 16));
        howMuchMoney.setFont(new Font("Arial", Font.BOLD, 16));
        howManyRounds.setFont(new Font("Arial", Font.BOLD, 16));

        howManyPlayers.setBounds(50, 350, 200, 40);
        howMuchMoney.setBounds(250, 350, 250, 40);
        howManyRounds.setBounds(500, 350, 250, 40);
        player1.setBounds(50, 400, 50, 50);
        player2.setBounds(50, 450, 50, 50);
        player3.setBounds(50, 500, 50, 50);
        player4.setBounds(50, 550, 50, 50);
        playerField1.setBounds(105, 400, 100, 50);
        playerField2.setBounds(105, 450, 100, 50);
        playerField3.setBounds(105, 500, 100, 50);
        playerField4.setBounds(105, 550, 100, 50);
        moneyField.setBounds(250, 400, 100, 50);
        roundsField.setBounds(500, 400, 100, 50);
        applyButton.setBounds(350, 710, 200, 40);

        panel.add(howManyPlayers);
        panel.add(howMuchMoney);
        panel.add(howManyRounds);
        panel.add(applyButton);
        panel.add(playerField1);
        panel.add(moneyField);
        panel.add(player1);
        panel.add(player2);
        panel.add(player3);
        panel.add(player4);
        panel.add(playerField2);
        panel.add(playerField3);
        panel.add(playerField4);
        panel.add(roundsField);

        howManyPlayers.setVisible(false);
        howMuchMoney.setVisible(false);
        howManyRounds.setVisible(false);
        playerField1.setVisible(false);
        moneyField.setVisible(false);
        applyButton.setVisible(false);
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        player4.setVisible(false);
        playerField1.setVisible(false);
        playerField2.setVisible(false);
        playerField3.setVisible(false);
        playerField4.setVisible(false);
        roundsField.setVisible(false);

        styleButton(player1);
        styleButton(player2);
        styleButton(player3);
        styleButton(player4);
        styleButton(applyButton);

        styleFields(playerField1);
        styleFields(playerField2);
        styleFields(playerField3);
        styleFields(playerField4);
        styleFields(moneyField);
        styleFields(roundsField);

        styleLabel(howManyPlayers);
        styleLabel(howMuchMoney);
        styleLabel(howManyRounds);

        panel.revalidate();
        panel.repaint();
    }

    public void apply() {
        startingMoney = Integer.valueOf(moneyField.getText());
        rounds = Integer.valueOf(roundsField.getText());
        switch(playerCount){
                case 1:
                    game.addPlayers(new player(p1, startingMoney, startingPosition));
                    break;
                case 2:
                    game.addPlayers(new player(p1, startingMoney, startingPosition));
                    game.addPlayers(new player(p2, startingMoney, startingPosition));
                    break;
                case 3:
                    game.addPlayers(new player(p1, startingMoney, startingPosition));
                    game.addPlayers(new player(p2, startingMoney, startingPosition));
                    game.addPlayers(new player(p3, startingMoney, startingPosition));
                    break;
                case 4:
                    game.addPlayers(new player(p1, startingMoney, startingPosition));
                    game.addPlayers(new player(p2, startingMoney, startingPosition));
                    game.addPlayers(new player(p3, startingMoney, startingPosition));
                    game.addPlayers(new player(p4, startingMoney, startingPosition));
                    break;
          }
    }

    public void updatePFields() {
        switch (playerCount) {
            case 1:
                playerField1.setVisible(true);
                playerField2.setVisible(false);
                playerField3.setVisible(false);
                playerField4.setVisible(false);
                break;
            case 2:
                playerField1.setVisible(true);
                playerField2.setVisible(true);
                playerField3.setVisible(false);
                playerField4.setVisible(false);
                break;
            case 3:
                playerField1.setVisible(true);
                playerField2.setVisible(true);
                playerField3.setVisible(true);
                playerField4.setVisible(false);
                break;
            case 4:
                playerField1.setVisible(true);
                playerField2.setVisible(true);
                playerField3.setVisible(true);
                playerField4.setVisible(true);
                break;
        }
    }

    public void getMText(){
        String mText = moneyField.getText();
    }

    public void getPText(){
        String pText = playerField1.getText();
    }
    
    

    public void styleButton(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    public void styleFields(JTextField field){
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.WHITE));

    }

    public void styleLabel(JLabel label){
        label.setForeground(Color.WHITE);
    }
}
