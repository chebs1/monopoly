 


import java.awt.Color;

public class property extends Spaces{
    private int price;
    private int rent;
    private player owner;
    private Color color;
    private int houses = 0;
    private boolean hotel = false;

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
        // Each building earns back one fifth of its cost per rent payment.
        int increase = getHouseCost() / 5;
        if(hotel){
            return rent + increase * 6;
        }
        return rent + houses * increase;
    }

    public int getHouses(){
        return houses;
    }

    public boolean hasHotel(){
        return hotel;
    }

    public int getBuildingLevel(){
        if(hotel){
            return 5;
        }
        return houses;
    }

    public boolean isStreet(){
        int position = getPosition();
        return position % 10 != 5 && position != 12 && position != 28;
    }

    public boolean canBuild(){
        return isOwned() && isStreet() && !hotel;
    }

    public int getHouseCost(){
        if(!isStreet()){
            return 0;
        }
        // More expensive sections of the board have higher building costs.
        if(getPosition() < 10){
            return 50;
        }
        if(getPosition() < 20){
            return 100;
        }
        if(getPosition() < 30){
            return 150;
        }
        return 200;
    }

    public int getBuildingCost(){
        if(houses == 4){
            return getHouseCost() * 2;
        }
        return getHouseCost();
    }

    public int getNextRent(){
        if(!isStreet() || hotel){
            return getRent();
        }
        return getRent() + getBuildingCost() / 5;
    }

    public int getBuildingValue(){
        if(hotel){
            // Four houses plus a hotel upgrade costing the same as two houses.
            return getHouseCost() * 6;
        }
        return houses * getHouseCost();
    }

    public void addBuilding(){
        if(!canBuild()){
            return;
        }
        if(houses == 4){
            houses = 0;
            hotel = true;
        }
        else{
            houses++;
        }
    }

    public player getOwner(){
        return owner;
    }

    public void setOwner(player owner){
        this.owner = owner;
        if(owner == null){
            houses = 0;
            hotel = false;
        }
    }

    public Color getColor(){
        return color;
    }

    public boolean isOwned() {
        return owner != null;
    }

}
