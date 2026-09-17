 


import java.awt.*; 
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class BoardPanel extends JPanel implements ActionListener{
    int margin = 50;
    int cornerSize = 162;
    int boardsize = 792;
    int propertyHeight = cornerSize;
    int propertyWidth = (boardsize - (2*cornerSize))/9;;
    int pieceSize = 50;
    int start = 742;
    public boolean playing = false;
    boolean settings = false;
    JButton startButton;
    JButton settingsButton;
    JButton backButton;
    JButton diceButton;
    JButton endTurnButton;

    public boolean roll;

    public boolean showDice = false;

    private Board board;
    private player player;
    private Settings settingsPanel;
    private dice dice;
    private game game;
    
    private Timer diceTimer;

    String imagePath; 
    BufferedImage bgImage;
    BufferedImage scImage;
    BufferedImage seImage;
    BufferedImage p1Image;
    BufferedImage p2Image;
    BufferedImage p3Image;
    BufferedImage p4Image;
    BufferedImage diceIMG;

    public BoardPanel() {
        setLayout(null);
        board = new Board();
        game = new game();
        settingsPanel = new Settings(this, game);
        setFont(new Font("Arial", Font.BOLD, 24));
    
        dice = new dice();
        diceTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDice = true;
                repaint();
            }
        });
        diceTimer.setRepeats(false);
        
        try
        {
            loadImage();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        startButton = new JButton("Start Game"); 
        add(startButton); 
        startButton.addActionListener(this); 
        settingsButton = new JButton("Settings");
        add(settingsButton);
        settingsButton.addActionListener(this);
        backButton = new JButton("\u21D0");
        add(backButton);
        backButton.addActionListener(this);
        diceButton = new JButton("Roll Dice");
        add(diceButton);
        diceButton.addActionListener(this);
        endTurnButton = new JButton("End Turn");
        add(endTurnButton);
        endTurnButton.addActionListener(this);
    }

    public void diceRoll() {
        if (roll = true){
            dice.rollDice();
            diceTimer.start();
        }
    }

    public void loadImage() throws IOException {
        try {
            bgImage = AssetManager.loadImage(AssetEnum.BOARD);
            scImage = AssetManager.loadImage(AssetEnum.SPLASHSCREEN);
            seImage = AssetManager.loadImage(AssetEnum.SETTINGS);            
            p1Image = AssetManager.loadImage(AssetEnum.PLAYER1);
            p2Image = AssetManager.loadImage(AssetEnum.PLAYER2);
            p3Image = AssetManager.loadImage(AssetEnum.PLAYER3);
            p4Image = AssetManager.loadImage(AssetEnum.PLAYER4);
            diceIMG = AssetManager.loadImage(AssetEnum.DICE);
        } catch (IOException e) {
            System.out.println("Error loading image. Check path and filename.");
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) 
    { 
        if(e.getSource() == startButton){
            playing = true;  
            game.startGame = true;
            endTurnButton.setVisible(false);
            diceButton.setVisible(false);
        }
        if(e.getSource() == settingsButton){
            settings = true;
            settingsPanel.moneyField.setVisible(true);
            settingsPanel.applyButton.setVisible(true);
            settingsPanel.howManyPlayers.setVisible(true);
            settingsPanel.howMuchMoney.setVisible(true);
            settingsPanel.howManyRounds.setVisible(true);
            settingsPanel.roundsField.setVisible(true);
            settingsPanel.player1.setVisible(true);
            settingsPanel.player2.setVisible(true);
            settingsPanel.player3.setVisible(true);
            settingsPanel.player4.setVisible(true);
            endTurnButton.setVisible(false);
            diceButton.setVisible(false);
        }
        if(e.getSource() == backButton) {
            settings = false;
            settingsPanel.howManyPlayers.setVisible(false);
            settingsPanel.howMuchMoney.setVisible(false);
            settingsPanel.howManyRounds.setVisible(false);
            settingsPanel.roundsField.setVisible(false);
            settingsPanel.playerField1.setVisible(false);
            settingsPanel.playerField2.setVisible(false);
            settingsPanel.playerField3.setVisible(false);
            settingsPanel.playerField4.setVisible(false);
            settingsPanel.moneyField.setVisible(false);
            settingsPanel.applyButton.setVisible(false);
            settingsPanel.player1.setVisible(false);
            settingsPanel.player2.setVisible(false);
            settingsPanel.player3.setVisible(false);
            settingsPanel.player4.setVisible(false);
            endTurnButton.setVisible(false);
            diceButton.setVisible(false);
        }
        if(e.getSource() == diceButton) {
            roll = true;
            diceRoll();
        }
        if(e.getSource() == endTurnButton){
            game.endTurn = true;
        }
        
        repaint();
    } 

    public void styleButton(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    }
    
    

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //board
        startButton.setVisible(!playing);
        settingsButton.setVisible(!playing);
        diceButton.setVisible(playing);

        backButton.setVisible(settings);

        if(!playing){
            if (scImage != null) {
                g.drawImage(scImage, 0, 0, 900, 900, this);
            }

            startButton.setLocation(350,550); 
            startButton.setSize(200,60); 
            styleButton(startButton);

            settingsButton.setLocation(350, 610);
            settingsButton.setSize(200, 60);
            styleButton(settingsButton);

            backButton.setLocation(0, 0);
            backButton.setSize(100, 100);
            styleButton(backButton);
            backButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 50));
            
            endTurnButton.setLocation(350, 850);
            endTurnButton.setSize(200, 60);
            styleButton(endTurnButton);
            
            diceButton.setLocation(350, 850);
            diceButton.setSize(200, 60);
            diceButton.setBackground(Color.GREEN);

        }
        if(settings){
            g.drawImage(seImage, 0, 0, 900, 900, this);
            startButton.setVisible(false);
            settingsButton.setVisible(false);
            diceButton.setVisible(false);
        }
        if(playing){
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());

            if (bgImage != null) {
                g.drawImage(bgImage, margin, margin, boardsize, boardsize, this);
            }
            

            if (diceTimer.isRunning()) {
                if (diceIMG != null){
                    g.drawImage(diceIMG,300, 400, 300, 150, this);
                    System.out.println("You Rolled " + dice.outCome);
                    g.setColor(Color.BLACK);
                    g.drawString("You Rolled  " + dice.outCome, 400, 485);
                }
            }

        }
        game.drawIcons(g);
        
        
    }

    }


