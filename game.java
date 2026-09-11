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
    int movement;
    int currentTurn;

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

    public List<player> numOfPlayers = new ArrayList();

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

    public void turnSystem(){
        if(startGame){
            for(int i = 0; i<numOfPlayers.size();){
                switch(i){
                    case 0:
                        startTurn=true;
                        if(endTurn){
                            currentTurn++;
                            i++;
                        }
                        break;
                    case 1:
                        startTurn=true;
                        if(endTurn){
                            currentTurn++;
                            i++;
                        }
                        break;
                    case 2:
                        startTurn=true;
                        if(endTurn){
                            currentTurn++;
                            i++;
                        }
                        break;
                    case 3:
                        startTurn=true;
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
            for(int o=0; o<numOfPlayers.size();o++){
                for (int i = 0; i < numOfPlayers.size();){
                    switch(i){
                        case 0:
                            if (p1Image != null){
                                g.drawImage(p1Image, start, start, pieceSize, pieceSize, null);
                            }
                            break;
                        case 1:
                            if(p2Image != null){
                                g.drawImage(p2Image, start*o, start, pieceSize, pieceSize, null);
                            }
                            break;
                        case 2:
                            if(p3Image != null){
                                g.drawImage(p3Image, start, start*o, pieceSize, pieceSize, null);
                            }
                            break;
                        case 4:
                            if(p4Image != null){
                                g.drawImage(p4Image, start*o, start*o, pieceSize, pieceSize, null);
                            }
                            break;

                    }
                }
            }
        }
    }

    public void moveIcons(){
        if(dice.diceRolled == true){
            movement = dice.sum;
            Spaces.getPosition() = Spaces.getPosition() + movement;
        }
    }

}    

