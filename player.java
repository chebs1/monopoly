 


import java.util.List;
import java.util.ArrayList;

public class player
{
    private game Game;

    private int money = 0;
    private String playerName;
    private int position;
    private final List<property> properties = new ArrayList<>();
    
    
    
    
    public player(String playerName, int money, int position)
    {
        this.playerName = playerName;
        this.money = money;
        this.position = position;
    }

    public int getPlayerPosition(){
        return position;
    }

    public int getMoney(){
        return money;
    }

    public void setName(){
        this.playerName = playerName;
    }

    public String getName(){
        return playerName;
    }

    public void move(int spaces) {
        position = (position + spaces) % 40;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public boolean removeMoney(int amount) {
        if (amount > money) {
            return false;
        }
        money -= amount;
        return true;
    }

    public List<property> getProperties(){
        return properties;
    }
    
   
    
    
    public boolean buyProperty(property property) {
        if (property.isOwned()) {
            System.out.println("Property Already Owned");
            return false;
        }
        else if(getMoney() < property.getPrice()){
            System.out.println("U can not afford");
            return false;
        }
        else{
            money -= property.getPrice();
            property.setOwner(this);
            properties.add(property);
            return true;
        }
    }

}
