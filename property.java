 


import java.awt.Color;

public class property extends Spaces{
    private int price;
    private int rent;
    private player owner;
    private Color color;

    public property(String name, int position, int price, int rent, Color color){
        super(name, position);
        this.price = price;
        this.rent = rent;
        this.color = color;
    }

    public int getPrice(){
        return price;
    }

    public int getRent(){
        return rent;
    }

    public player getOwner(){
        return owner;
    }

    public void setOwner(player owner){
        this.owner = owner;
    }

    public Color getColor(){
        return color;
    }

    public boolean isOwned() {
        return owner != null;
    }

}
