
import java.util.List;
import java.util.*;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class game{
    private Settings settings;
    private Board board;
    private player players;
    private dice dice;
    private BoardPanel bPanel;
    private Spaces spaces;

    int pieceSize = 50;
    int start = 742;
    private int movement;
    int currentTurn;

    int x;
    int y;
    int xDirection;
    int yDirection;
    int speed;
    

    String imagePath; 
    BufferedImage bgImage;
    BufferedImage scImage;
    BufferedImage seImage;
    BufferedImage p1Image;
    BufferedImage p2Image;
    BufferedImage p3Image;
    BufferedImage p4Image;
    BufferedImage diceIMG;

    public boolean startGame = false;
    public boolean turnOver = false;
    public boolean startTurn = false;
    public boolean endTurn = false;

    public List<player> numOfPlayers =   new ArrayList();
    private int currentPlayer;

    public game(){
        turnSystem();
        try
        {
            loadImage();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void loadImage() throws IOException {
        try {
            p1Image = AssetManager.loadImage(AssetEnum.PLAYER1);
            p2Image = AssetManager.loadImage(AssetEnum.PLAYER2);
            p3Image = AssetManager.loadImage(AssetEnum.PLAYER3);
            p4Image = AssetManager.loadImage(AssetEnum.PLAYER4);
        } catch (IOException e) {
            System.out.println("Error loading image. Check path and filename.");
            e.printStackTrace();
        }
    }

    public void addPlayers(player newPlayer){
        numOfPlayers.add(newPlayer);
    }

    public int getCurrentTurn(){
        return currentTurn;
    }

    public void displayTurn(){
        System.out.println("Current Player is" + getCurrentTurn());
    }

    public void turnSystem(){
        if(startGame){
            for(int i = 0; i<numOfPlayers.size();){
                currentPlayer = i;

                switch(i){
                    case 0:
                        displayTurn();
                        startTurn=true;
                        turn();
                        if(endTurn){
                            currentTurn++;
                            i++;
                        }                        
                        break;
                    case 1:
                        displayTurn();
                        startTurn=true;
                        turn();
                        if(endTurn){
                            currentTurn++;
                            i++;
                        }
                        break;
                    case 2:
                        displayTurn();
                        startTurn=true;
                        turn();
                        if(endTurn){
                            currentTurn++;
                            i++;
                        }
                        break;
                    case 3:
                        displayTurn();
                        startTurn=true;
                        turn();
                        if(endTurn){
                            currentTurn=0;
                            i = 0;
                        }
                        break;
                }
            }
        }
    }

    public void drawIcons(Graphics g){
        if(startGame){
            int count = numOfPlayers.size();
            switch(count){
                case 1:
                    if (p1Image != null){
                        g.drawImage(p1Image, start, start, pieceSize, pieceSize, null);
                    }
                    break;
                case 2:
                    if (p1Image != null){
                        g.drawImage(p1Image, start, start, pieceSize, pieceSize, null);
                    }
                    if(p2Image != null){
                        g.drawImage(p2Image, start, start+50, pieceSize, pieceSize, null);
                    }
                    break;
                case 3:
                    if (p1Image != null){
                        g.drawImage(p1Image, start, start, pieceSize, pieceSize, null);
                    }
                    if(p2Image != null){
                        g.drawImage(p2Image, start, start+50, pieceSize, pieceSize, null);
                    }
                    if(p3Image != null){
                        g.drawImage(p3Image, start+50, start, pieceSize, pieceSize, null);
                    }
                    break;
                case 4:
                    if (p1Image != null){
                        g.drawImage(p1Image, start, start, pieceSize, pieceSize, null);
                    }
                    if(p2Image != null){
                        g.drawImage(p2Image, start, start+50, pieceSize, pieceSize, null);
                    }
                    if(p3Image != null){
                        g.drawImage(p3Image, start+50, start, pieceSize, pieceSize, null);
                    }
                    if(p4Image != null){
                        g.drawImage(p4Image, start+50, start+50, pieceSize, pieceSize, null);
                    }
                    break;

            }
        }
    }

    public void turn(){
        bPanel.diceButton.setVisible(true);
        if(dice.diceRolled == true){
            bPanel.diceButton.setVisible(false);
            moveIcons();
            bPanel.endTurnButton.setVisible(true);
        }
        if(endTurn){
            bPanel.endTurnButton.setVisible(false);
        }

    }

    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void moveIcons(){
        if(dice.diceRolled == true){
            int oldPosition = players.getPlayerPosition();
            players.move(dice.sum);
            int newPosition = players.getPlayerPosition();
            System.out.println("U Moved to Position" + newPosition);
            
            switch(currentPlayer){
                case 0:
                    //g.drawImage(p1IMG,)
            }
            
            
            
            
        }
    }

}    

