package Java.MONOPOLY;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

public class BoardPanel extends JPanel implements ActionListener{
    int margin = 50;
    int boardsize = 792;
    public boolean playing = false;
    boolean settings = false;
    JButton startButton;
    JButton settingsButton;
    JButton backButton;
    JButton diceButton;
    JButton buyButton;
    JButton buildButton;
    JButton endTurnButton;
    JTextArea messageArea = new JTextArea();
    JScrollPane messageScroll;
    Color[] playerColors = {new Color(35, 108, 74), new Color(42, 91, 145),
        new Color(170, 48, 48), new Color(154, 105, 27)};

    private Settings settingsPanel;
    private game game;
    private dice dice;
    private Timer diceTimer;
    private property buildingInfo;
    BufferedImage bgImage;
    BufferedImage scImage;
    BufferedImage seImage;
    BufferedImage diceIMG;

    public BoardPanel(){
        setLayout(null);
        setPreferredSize(new Dimension(1200, 920));
        game = new game();
        dice = game.dice;
        settingsPanel = new Settings(this, game);

        diceTimer = new Timer(1500, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                game.moveIcons();
                updateButtons();
                repaint();
            }
        });
        diceTimer.setRepeats(false);

        try{
            loadImage();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        startButton = new JButton("Start Game");
        settingsButton = new JButton("Settings");
        backButton = new JButton("Back");
        diceButton = new JButton("Roll Dice");
        buyButton = new JButton("Buy Property");
        buildButton = new JButton("Build House");
        endTurnButton = new JButton("End Turn");

        startButton.setBounds(500, 550, 200, 60);
        settingsButton.setBounds(500, 610, 200, 60);
        backButton.setBounds(20, 20, 100, 50);
        diceButton.setBounds(235, 850, 170, 50);
        buyButton.setBounds(420, 850, 170, 50);
        buildButton.setBounds(605, 850, 170, 50);
        endTurnButton.setBounds(790, 850, 170, 50);
        styleButton(startButton);
        styleButton(settingsButton);
        styleButton(backButton);
        styleGameButton(diceButton, new Color(35, 108, 74));
        styleGameButton(buyButton, new Color(154, 105, 27));
        styleGameButton(buildButton, new Color(42, 91, 145));
        styleGameButton(endTurnButton, new Color(170, 48, 48));

        add(startButton);
        add(settingsButton);
        add(backButton);
        add(diceButton);
        add(buyButton);
        add(buildButton);
        add(endTurnButton);
        startButton.addActionListener(this);
        settingsButton.addActionListener(this);
        backButton.addActionListener(this);
        diceButton.addActionListener(this);
        buyButton.addActionListener(this);
        buildButton.addActionListener(this);
        endTurnButton.addActionListener(this);

        messageArea.setEditable(false);
        messageArea.setOpaque(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 18));
        messageArea.setForeground(new Color(35, 55, 43));
        messageScroll = new JScrollPane(messageArea);
        messageScroll.setBounds(390, 383, 420, 160);
        messageScroll.setBorder(BorderFactory.createEmptyBorder());
        messageScroll.setOpaque(false);
        messageScroll.getViewport().setOpaque(false);
        add(messageScroll);
        updateButtons();
    }

    public void loadImage() throws IOException{
        bgImage = AssetManager.loadImage(AssetEnum.BOARD);
        scImage = AssetManager.loadImage(AssetEnum.SPLASHSCREEN);
        seImage = AssetManager.loadImage(AssetEnum.SETTINGS);
        diceIMG = AssetManager.loadImage(AssetEnum.DICE);
        // Crop the empty background so the dice is easy to see.
        diceIMG = diceIMG.getSubimage(90, 225, 300, 330);
    }

    public void diceRoll(){
        if(playing && game.canRoll() && !diceTimer.isRunning()){
            buildingInfo = null;
            game.rollDice();
            game.message = "Rolling...";
            diceTimer.start();
            updateButtons();
            repaint();
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == startButton){
            playing = true;
            game.beginGame();
        }
        if(e.getSource() == settingsButton){
            settings = true;
            settingsPanel.setVisible(true);
        }
        if(e.getSource() == backButton){
            settings = false;
            settingsPanel.setVisible(false);
        }
        if(e.getSource() == diceButton){
            diceRoll();
        }
        if(e.getSource() == buyButton){
            game.buyProperty();
        }
        if(e.getSource() == buildButton){
            chooseBuilding();
        }
        if(e.getSource() == endTurnButton){
            buildingInfo = null;
            game.endTurn = true;
            game.turnSystem();
        }
        updateButtons();
        repaint();
    }

    public void updateButtons(){
        startButton.setVisible(!playing && !settings);
        settingsButton.setVisible(!playing && !settings);
        backButton.setVisible(settings);
        diceButton.setVisible(playing);
        buyButton.setVisible(playing);
        buildButton.setVisible(playing);
        endTurnButton.setVisible(playing);
        diceButton.setEnabled(playing && game.canRoll() && !diceTimer.isRunning());
        diceButton.setText("Roll Dice");
        if(game.extraRoll){
            diceButton.setText("Roll Again");
        }
        buyButton.setEnabled(game.canBuy() && !diceTimer.isRunning());
        buildButton.setEnabled(game.canBuild() && !diceTimer.isRunning());
        buildButton.setText("Build Houses");
        buildButton.setToolTipText("Own a full colour set and build evenly across its streets.");
        endTurnButton.setEnabled(game.turnOver && !game.extraRoll && !game.gameOver && !diceTimer.isRunning());
        // Hide the space details until the dice picture has finished showing.
        messageScroll.setVisible(playing && !diceTimer.isRunning() && (game.turnOver || game.gameOver));
        messageArea.setText(game.message);
        if(game.turnOver && !game.gameOver && getInfoSpace() instanceof property){
            property land = (property) getInfoSpace();
            String owner = "Unowned";
            if(land.isOwned()){
                owner = land.getOwner().getName();
            }
            messageArea.append("\nOwner: " + owner);
            if(land.isStreet()){
                if(land.hasHotel()){
                    messageArea.append("\nHotel   Rent: $" + land.getRent());
                }
                else{
                    messageArea.append("\nHouses: " + land.getHouses() + "   Rent: $" + land.getRent());
                    String building = "House";
                    if(land.getHouses() == 4){
                        building = "Hotel";
                    }
                    messageArea.append("\nNext " + building.toLowerCase() + ": $" + land.getBuildingCost()
                        + "   Rent: $" + land.getNextRent());
                    if(land.getOwner() == game.getCurrentPlayer() && !game.ownsColourSet(land)){
                        messageArea.append("\nOwn the full colour set before building.");
                    }
                }
            }
        }
        messageArea.setCaretPosition(0);
    }

    public Spaces getInfoSpace(){
        if(buildingInfo != null){
            return buildingInfo;
        }
        return game.getCurrentSpace();
    }

    public void chooseBuilding(){
        List<property> lands = new ArrayList<>();
        List<String> choices = new ArrayList<>();
        for(property land : game.getCurrentPlayer().getProperties()){
            if(game.canBuild(land)){
                lands.add(land);
                String building = "House";
                if(land.getHouses() == 4){
                    building = "Hotel";
                }
                choices.add(land.getName() + " - " + building + " $" + land.getBuildingCost()
                    + " - Rent $" + land.getNextRent());
            }
        }
        if(lands.isEmpty()){
            return;
        }
        int selected = 0;
        if(lands.size() > 1){
            String choice = (String) JOptionPane.showInputDialog(this, "Choose a street to build on:",
                "Build", JOptionPane.PLAIN_MESSAGE, null, choices.toArray(), choices.get(0));
            if(choice == null){
                return;
            }
            selected = choices.indexOf(choice);
        }
        buildingInfo = lands.get(selected);
        game.buildHouse(buildingInfo);
    }

    public void styleButton(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }

    public void styleGameButton(JButton button, Color color){
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void drawPlayerCards(Graphics g){
        // Only draw a corner card for each player in this game.
        for(int i = 0; i < game.numOfPlayers.size(); i++){
            player p = game.numOfPlayers.get(i);
            int x = 10;
            int y = 50;
            if(i % 2 == 1){
                x = getWidth() - 190;
            }
            if(i >= 2){
                y = getHeight() - 150;
            }
            boolean currentPlayer = i == game.getCurrentTurn() && !game.gameOver && !p.bankrupt;

            g.setColor(new Color(0, 0, 0, 30));
            g.fillRoundRect(x + 3, y + 4, 180, 125, 16, 16);
            g.setColor(new Color(250, 248, 238));
            if(currentPlayer){
                g.setColor(new Color(225, 239, 225));
            }
            g.fillRoundRect(x, y, 180, 125, 16, 16);
            g.setColor(playerColors[i]);
            g.drawRoundRect(x, y, 180, 125, 16, 16);
            if(currentPlayer){
                g.drawRoundRect(x + 1, y + 1, 178, 123, 14, 14);
            }
            g.fillRoundRect(x + 12, y + 12, 5, 24, 5, 5);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(p.getName(), x + 25, y + 30);
            g.setColor(new Color(35, 55, 43));
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Money: $" + p.getMoney(), x + 14, y + 58);
            g.drawString("Properties owned: " + p.getProperties().size(), x + 14, y + 82);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.setColor(playerColors[i]);
            if(p.bankrupt){
                g.drawString("BANKRUPT", x + 14, y + 108);
            }
            else if(p.inJail){
                g.drawString("IN JAIL", x + 14, y + 108);
            }
            else if(currentPlayer){
                g.drawString("CURRENT TURN", x + 14, y + 108);
            }
            g.drawString("P" + (i + 1), x + 148, y + 108);
        }
    }

    public void drawPropertyMarks(Graphics g){
        for(int i = 0; i < game.numOfPlayers.size(); i++){
            for(property land : game.numOfPlayers.get(i).getProperties()){
                Point point = game.getSpacePoint(land.getPosition());
                int badgeX;
                int badgeY;
                int buildingX;
                int buildingY;
                if(land.getPosition() < 10){
                    badgeX = point.x - 12;
                    badgeY = 743;
                    buildingX = point.x - 20;
                    buildingY = 722;
                }
                else if(land.getPosition() < 20){
                    badgeX = 132;
                    badgeY = point.y - 8;
                    buildingX = 160;
                    buildingY = point.y - 6;
                }
                else if(land.getPosition() < 30){
                    badgeX = point.x - 12;
                    badgeY = 132;
                    buildingX = point.x - 20;
                    buildingY = 158;
                }
                else{
                    badgeX = 738;
                    badgeY = point.y - 8;
                    buildingX = 690;
                    buildingY = point.y - 6;
                }

                // Match the badge to the player's corner card: P1, P2, P3 or P4.
                g.setColor(playerColors[i]);
                g.fillRoundRect(badgeX, badgeY, 24, 16, 6, 6);
                g.setColor(Color.WHITE);
                g.drawRoundRect(badgeX, badgeY, 24, 16, 6, 6);
                g.setFont(new Font("Arial", Font.BOLD, 10));
                g.drawString("P" + (i + 1), badgeX + 5, badgeY + 12);

                if(land.hasHotel()){
                    g.setColor(new Color(190, 45, 45));
                    g.fillRect(buildingX + 10, buildingY, 24, 14);
                    g.setColor(Color.WHITE);
                    g.drawString("H", buildingX + 18, buildingY + 11);
                }
                else{
                    for(int h = 0; h < land.getHouses(); h++){
                        int houseX = buildingX + h * 11;
                        g.setColor(new Color(25, 140, 65));
                        g.fillRect(houseX, buildingY + 4, 8, 7);
                        g.fillPolygon(new int[]{houseX - 1, houseX + 4, houseX + 9},
                            new int[]{buildingY + 4, buildingY, buildingY + 4}, 3);
                        g.setColor(new Color(15, 85, 40));
                        g.drawRect(houseX, buildingY + 4, 8, 7);
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(!playing){
            g.drawImage(scImage, 0, 0, getWidth(), getHeight(), this);
        }
        if(settings){
            g.drawImage(seImage, 0, 0, getWidth(), getHeight(), this);
        }
        if(playing){
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            int boardX = (getWidth() - boardsize) / 2;
            g.drawImage(bgImage, boardX, margin, boardsize, boardsize, this);
            // Move the pieces by the same amount as the board.
            Graphics pieces = g.create();
            pieces.translate(boardX - margin, 0);
            drawPropertyMarks(pieces);
            game.drawIcons(pieces);
            pieces.dispose();
            drawPlayerCards(g);

            g.setColor(new Color(35, 108, 74));
            g.fillRoundRect(460, 8, 280, 34, 14, 14);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            String roundText = "Round " + Math.min(game.currentRound, game.rounds) + " of " + game.rounds;
            g.drawString(roundText, (getWidth() - g.getFontMetrics().stringWidth(roundText)) / 2, 31);
            if(diceTimer.isRunning() || game.turnOver || game.gameOver){
                // The same card shows the dice first, then the space information.
                g.setColor(new Color(0, 0, 0, 45));
                g.fillRoundRect(376, 334, 460, 235, 20, 20);
                g.setColor(new Color(250, 248, 238));
                g.fillRoundRect(370, 328, 460, 235, 20, 20);
                g.setColor(new Color(35, 108, 74));
                g.drawRoundRect(370, 328, 460, 235, 20, 20);
                g.setFont(new Font("Arial", Font.BOLD, 22));
                String title = "Space info";
                if(diceTimer.isRunning()){
                    title = "Rolled " + dice.dice1 + " + " + dice.dice2 + " = " + dice.sum;
                }
                else if(game.gameOver){
                    title = "Game over";
                }
                else if(getInfoSpace() instanceof property){
                    title = "Property info";
                }
                g.drawString(title, 390, 363);
                if(diceTimer.isRunning()){
                    g.drawImage(diceIMG, 525, 378, 150, 165, this);
                }
            }
        }
    }
}

