package Java.MONOPOLY;

import javax.swing.*;
import java.awt.*;

public class Settings{
    public int playerCount = 2;
    public int startingMoney = 1500;
    public int rounds = 20;
    public int startingPosition = 0;

    JLabel howManyPlayers = new JLabel("How Many Players?");
    JLabel howMuchMoney = new JLabel("How Much Starting Money?");
    JLabel howManyRounds = new JLabel("How Many Rounds?");
    JButton player1 = new JButton("1");
    JButton player2 = new JButton("2");
    JButton player3 = new JButton("3");
    JButton player4 = new JButton("4");
    JTextField playerField1 = new JTextField("Player 1");
    JTextField playerField2 = new JTextField("Player 2");
    JTextField playerField3 = new JTextField("Player 3");
    JTextField playerField4 = new JTextField("Player 4");
    JTextField moneyField = new JTextField("1500");
    JTextField roundsField = new JTextField("20");
    JButton applyButton = new JButton("Apply");

    private game game;
    private BoardPanel panel;

    public Settings(BoardPanel panel, game game){
        this.panel = panel;
        this.game = game;
        applyButton.addActionListener(event -> apply());
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

        howManyPlayers.setBounds(50, 350, 200, 40);
        howMuchMoney.setBounds(250, 350, 250, 40);
        howManyRounds.setBounds(500, 350, 250, 40);
        player1.setBounds(50, 400, 50, 50);
        player2.setBounds(50, 450, 50, 50);
        player3.setBounds(50, 500, 50, 50);
        player4.setBounds(50, 550, 50, 50);
        playerField1.setBounds(105, 400, 120, 50);
        playerField2.setBounds(105, 450, 120, 50);
        playerField3.setBounds(105, 500, 120, 50);
        playerField4.setBounds(105, 550, 120, 50);
        moneyField.setBounds(250, 400, 120, 50);
        roundsField.setBounds(500, 400, 100, 50);
        applyButton.setBounds(350, 710, 200, 40);

        Component[] controls = {howManyPlayers, howMuchMoney, howManyRounds,
            player1, player2, player3, player4, playerField1, playerField2,
            playerField3, playerField4, moneyField, roundsField, applyButton};
        for(Component control : controls){
            panel.add(control);
        }
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
        setVisible(false);
    }

    public void apply(){
        int money;
        int roundLimit;
        try{
            money = Integer.parseInt(moneyField.getText().trim());
            roundLimit = Integer.parseInt(roundsField.getText().trim());
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(panel, "Enter whole numbers for money and rounds.");
            return;
        }
        if(money < 1 || money > 1000000 || roundLimit < 1 || roundLimit > 1000){
            JOptionPane.showMessageDialog(panel, "Use money from 1 to 1000000 and rounds from 1 to 1000.");
            return;
        }

        JTextField[] names = {playerField1, playerField2, playerField3, playerField4};
        for(int i = 0; i < playerCount; i++){
            String name = names[i].getText().trim();
            if(name.isEmpty() || name.length() > 12){
                JOptionPane.showMessageDialog(panel, "Player names must be 1 to 12 characters long.");
                return;
            }
        }
        startingMoney = money;
        rounds = roundLimit;
        game.numOfPlayers.clear();
        for(int i = 0; i < playerCount; i++){
            game.addPlayers(new player(names[i].getText().trim(), startingMoney, startingPosition));
        }
        game.rounds = rounds;
        panel.settings = false;
        setVisible(false);
        panel.updateButtons();
        panel.repaint();
    }

    public void setVisible(boolean visible){
        howManyPlayers.setVisible(visible);
        howMuchMoney.setVisible(visible);
        howManyRounds.setVisible(visible);
        player1.setVisible(visible);
        player2.setVisible(visible);
        player3.setVisible(visible);
        player4.setVisible(visible);
        moneyField.setVisible(visible);
        roundsField.setVisible(visible);
        applyButton.setVisible(visible);
        playerField1.setVisible(visible && playerCount >= 1);
        playerField2.setVisible(visible && playerCount >= 2);
        playerField3.setVisible(visible && playerCount >= 3);
        playerField4.setVisible(visible && playerCount >= 4);
    }

    public void updatePFields(){
        playerField1.setVisible(playerCount >= 1);
        playerField2.setVisible(playerCount >= 2);
        playerField3.setVisible(playerCount >= 3);
        playerField4.setVisible(playerCount >= 4);
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
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    public void styleLabel(JLabel label){
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
    }
}
