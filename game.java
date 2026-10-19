package Java.MONOPOLY;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class game{
    private Board board = new Board();
    public dice dice = new dice();
    public List<player> numOfPlayers = new ArrayList<>();

    int currentTurn = 0;
    int currentRound = 1;
    int rounds = 20;
    int doublesCount = 0;
    public boolean extraRoll = false;
    public boolean startGame = false;
    public boolean turnOver = false;
    public boolean endTurn = false;
    public boolean gameOver = false;
    public String message = "";

    BufferedImage p1Image;
    BufferedImage p2Image;
    BufferedImage p3Image;
    BufferedImage p4Image;

    public game(){
        try{
            loadImage();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadImage() throws IOException{
        p1Image = AssetManager.loadImage(AssetEnum.PLAYER1);
        p2Image = AssetManager.loadImage(AssetEnum.PLAYER2);
        p3Image = AssetManager.loadImage(AssetEnum.PLAYER3);
        p4Image = AssetManager.loadImage(AssetEnum.PLAYER4);
    }

    public void addPlayers(player newPlayer){
        numOfPlayers.add(newPlayer);
    }

    public int getCurrentTurn(){
        return currentTurn;
    }

    public player getCurrentPlayer(){
        return numOfPlayers.get(currentTurn);
    }

    public Spaces getCurrentSpace(){
        return board.getSpaces(getCurrentPlayer().getPlayerPosition());
    }

    public void beginGame(){
        if(numOfPlayers.isEmpty()){
            addPlayers(new player("Player 1", 1500, 0));
            addPlayers(new player("Player 2", 1500, 0));
        }
        startGame = true;
        message = getCurrentPlayer().getName() + ", roll the dice.";
    }

    // Each click finishes one turn, so this does not need an endless loop.
    public void turnSystem(){
        if(!startGame || !endTurn || !turnOver || extraRoll || gameOver){
            return;
        }
        do{
            currentTurn++;
            if(currentTurn == numOfPlayers.size()){
                currentTurn = 0;
                currentRound++;
            }
        }while(numOfPlayers.get(currentTurn).bankrupt);

        endTurn = false;
        turnOver = false;
        dice.diceRolled = false;
        doublesCount = 0;
        if(currentRound > rounds){
            finishGame();
        }
        else{
            message = getCurrentPlayer().getName() + ", roll the dice.";
        }
    }

    public void moveIcons(){
        if(!startGame || gameOver || (turnOver && !extraRoll) || !dice.diceRolled){
            return;
        }
        player currentPlayer = getCurrentPlayer();
        turnOver = true;
        dice.diceRolled = false;
        extraRoll = false;
        boolean doubles = dice.dice1 > 0 && dice.dice1 == dice.dice2;
        message = "You rolled " + dice.dice1 + " + " + dice.dice2 + " = " + dice.sum + ".\n";

        if(currentPlayer.inJail){
            if(doubles){
                currentPlayer.leaveJail();
                message += "Doubles! You left jail.\n";
            }
            else{
                currentPlayer.jailTurns++;
                if(currentPlayer.jailTurns < 3){
                    message += "Still in jail. Try for doubles next turn.";
                    return;
                }
                message += "Pay $50 to leave jail.\n";
                payMoney(50, null);
                if(currentPlayer.bankrupt){
                    return;
                }
                currentPlayer.leaveJail();
            }
        }
        else{
            if(doubles){
                doublesCount++;
                if(doublesCount == 3){
                    currentPlayer.sendToJail();
                    doublesCount = 0;
                    message += "Three doubles! Go straight to jail.";
                    return;
                }
                extraRoll = true;
            }
            else{
                doublesCount = 0;
            }
        }
        if(currentPlayer.getPlayerPosition() + dice.sum >= 40){
            currentPlayer.addMoney(200);
            message += "Passed GO: collect $200.\n";
        }
        currentPlayer.move(dice.sum);
        landOnSpace();
        if(extraRoll && !gameOver){
            message += "\nDoubles! Roll again before ending your turn.";
        }
    }

    public boolean canRoll(){
        return startGame && !gameOver && !dice.diceRolled
            && (!turnOver || extraRoll) && !getCurrentPlayer().bankrupt;
    }

    public void rollDice(){
        if(canRoll()){
            turnOver = false;
            extraRoll = false;
            endTurn = false;
            dice.rollDice();
        }
    }

    public void landOnSpace(){
        player currentPlayer = getCurrentPlayer();
        Spaces space = board.getSpaces(currentPlayer.getPlayerPosition());
        message += space.getName() + "\n";

        if(space instanceof property){
            property land = (property) space;
            if(!land.isOwned()){
                message += "Price: $" + land.getPrice() + "   Rent: $" + land.getRent();
            }
            else if(land.getOwner() != currentPlayer){
                message += "Pay $" + land.getRent() + " rent to " + land.getOwner().getName();
                payMoney(land.getRent(), land.getOwner());
            }
            else{
                message += "You own this property.";
            }
        }
        else if(space.getPosition() == 4){
            message += "Pay $200 income tax.";
            payMoney(200, null);
        }
        else if(space.getPosition() == 38){
            message += "Pay $100 luxury tax.";
            payMoney(100, null);
        }
        else if(space.getPosition() == 30){
            currentPlayer.sendToJail();
            extraRoll = false;
            doublesCount = 0;
            message += "Go straight to jail. Roll doubles to leave.";
        }
        else if(space.getName().equals("Chance") || space.getName().equals("Community Chest")){
            // Simple cards: either collect $100 or pay $50.
            Random random = new Random();
            if(random.nextBoolean()){
                currentPlayer.addMoney(100);
                message += "Collect $100 from the bank.";
            }
            else{
                message += "Pay $50 to the bank.";
                payMoney(50, null);
            }
        }
    }

    public boolean canBuy(){
        if(!startGame || gameOver || !turnOver || getCurrentPlayer().bankrupt){
            return false;
        }
        Spaces space = board.getSpaces(getCurrentPlayer().getPlayerPosition());
        if(space instanceof property){
            property land = (property) space;
            return !land.isOwned() && getCurrentPlayer().getMoney() >= land.getPrice();
        }
        return false;
    }

    public void buyProperty(){
        if(canBuy()){
            property land = (property) board.getSpaces(getCurrentPlayer().getPlayerPosition());
            getCurrentPlayer().buyProperty(land);
            message = "You bought " + land.getName() + " for $" + land.getPrice() + ".";
        }
    }

    public boolean canBuild(){
        if(!startGame){
            return false;
        }
        for(property land : getCurrentPlayer().getProperties()){
            if(canBuild(land)){
                return true;
            }
        }
        return false;
    }

    public boolean ownsColourSet(property land){
        if(!land.isStreet() || !land.isOwned()){
            return false;
        }
        for(Spaces space : board.spaces){
            if(space instanceof property){
                property other = (property) space;
                if(other.isStreet() && other.getColor().equals(land.getColor())
                    && other.getOwner() != land.getOwner()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canBuild(property land){
        if(!startGame || gameOver || !turnOver || getCurrentPlayer().bankrupt){
            return false;
        }
        if(land.getOwner() != getCurrentPlayer() || !land.canBuild() || !ownsColourSet(land)
            || getCurrentPlayer().getMoney() < land.getBuildingCost()){
            return false;
        }
        // Build on the least developed streets in the colour set first.
        for(property other : getCurrentPlayer().getProperties()){
            if(other.isStreet() && other.getColor().equals(land.getColor())
                && other.getBuildingLevel() < land.getBuildingLevel()){
                return false;
            }
        }
        return true;
    }

    public void buildHouse(){
        if(getCurrentSpace() instanceof property){
            buildHouse((property) getCurrentSpace());
        }
    }

    public void buildHouse(property land){
        if(canBuild(land)){
            int cost = land.getBuildingCost();
            getCurrentPlayer().removeMoney(cost);
            land.addBuilding();
            String building = "a house";
            if(land.hasHotel()){
                building = "a hotel";
            }
            message = "You built " + building + " on " + land.getName() + " for $" + cost
                + ".\nRent is now $" + land.getRent() + ".";
        }
    }

    public void payMoney(int amount, player owner){
        player currentPlayer = getCurrentPlayer();
        int payment = Math.min(amount, currentPlayer.getMoney());
        currentPlayer.removeMoney(payment);
        if(owner != null){
            owner.addMoney(payment);
        }
        if(payment < amount){
            currentPlayer.bankrupt = true;
            extraRoll = false;
            doublesCount = 0;
            for(property land : currentPlayer.getProperties()){
                land.setOwner(null);
            }
            currentPlayer.getProperties().clear();
            message += "\n" + currentPlayer.getName() + " is bankrupt.";

            int remaining = 0;
            for(player p : numOfPlayers){
                if(!p.bankrupt){
                    remaining++;
                }
            }
            if(remaining <= 1){
                finishGame();
            }
        }
    }

    public void finishGame(){
        // Compare money plus the value of properties and their buildings.
        gameOver = true;
        int bestScore = -1;
        String winners = "";
        for(player p : numOfPlayers){
            if(!p.bankrupt){
                int score = p.getMoney();
                for(property land : p.getProperties()){
                    score += land.getPrice() + land.getBuildingValue();
                }
                if(score > bestScore){
                    bestScore = score;
                    winners = p.getName();
                }
                else if(score == bestScore){
                    winners += " and " + p.getName();
                }
            }
        }
        if(bestScore == -1){
            message = "Game over. All players are bankrupt.";
        }
        else{
            message = "Game over! Winner: " + winners + "\nTotal money, property and buildings: $" + bestScore;
        }
    }

    public void drawIcons(Graphics g){
        if(!startGame){
            return;
        }
        BufferedImage[] images = {p1Image, p2Image, p3Image, p4Image};
        for(int i = 0; i < numOfPlayers.size(); i++){
            player p = numOfPlayers.get(i);
            if(p.bankrupt){
                continue;
            }
            Point point = getSpacePoint(p.getPlayerPosition());
            int x = point.x + (i % 2) * 24 - 24;
            int y = point.y + (i / 2) * 24 - 24;
            g.drawImage(images[i], x, y, 24, 24, null);
        }
    }

    public Point getSpacePoint(int position){
        int x = 790;
        int y = 790;
        // Use the same coordinates for pieces, ownership badges and buildings.
        if(position > 0 && position < 10){
            x = 50 + 104 + (int)((9.5 - position) * 584 / 9);
        }
        else if(position == 10){
            x = 102;
        }
        else if(position > 10 && position < 20){
            x = 102;
            y = 50 + 104 + (int)((19.5 - position) * 584 / 9);
        }
        else if(position == 20){
            x = 102;
            y = 102;
        }
        else if(position > 20 && position < 30){
            x = 50 + 104 + (int)((position - 20.5) * 584 / 9);
            y = 102;
        }
        else if(position == 30){
            y = 102;
        }
        else if(position > 30){
            y = 50 + 104 + (int)((position - 30.5) * 584 / 9);
        }
        return new Point(x, y);
    }
}
