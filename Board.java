 



import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class Board{
    List<Spaces> spaces = new ArrayList<>();
    
    public Board(){
        createBoard();
        
    }

    private void createBoard(){
        // These are the base rents. Buildings increase the rent on streets.
        spaces.add(new Spaces("GO", 0));
        spaces.add(new property("Mediterranean Ave", 1, 60, 2, new Color(139, 69, 19)));
        spaces.add(new Spaces("Community Chest", 2));
        spaces.add(new property("Baltic Avenue", 3, 60, 4, new Color(139, 69, 19)));
        spaces.add(new Spaces("Income Tax", 4));
        spaces.add(new property("Reading Railroad", 5, 200, 25, Color.BLACK));
        spaces.add(new property("Oriental Avenue", 6, 100, 6, new Color(146, 185, 202)));
        spaces.add(new Spaces("Chance", 7));
        spaces.add(new property("Vermont Avenue", 8, 100, 6, new Color(146, 185, 202)));
        spaces.add(new property("Connecticut Avenue", 9, 120, 8, new Color(146, 185, 202)));
        spaces.add(new Spaces("Jail", 10));
        spaces.add(new property("St Charles Place", 11, 140, 10, new Color(149, 47, 87)));
        spaces.add(new property("Electric Company", 12, 150, 10, Color.GRAY));
        spaces.add(new property("States Avenue", 13, 140, 10, new Color(149, 47, 87)));
        spaces.add(new property("Virginia Avenue", 14, 160, 12, new Color(149, 47, 87)));
        spaces.add(new property("Pennsylvania Railroad", 15, 200, 25, Color.BLACK));
        spaces.add(new property("St James Place", 16, 180, 14, new Color(188, 98, 38)));
        spaces.add(new Spaces("Community Chest", 17));
        spaces.add(new property("Tennessee Avenue", 18, 180, 14, new Color(188, 98, 38)));
        spaces.add(new property("New York Avenue", 19, 200, 16, new Color(188, 98, 38)));
        spaces.add(new Spaces("Free Parking", 20));
        spaces.add(new property("Kentucky Avenue", 21, 220, 18, new Color(170, 47, 39)));
        spaces.add(new Spaces("Chance", 22));
        spaces.add(new property("Indiana Avenue", 23, 220, 18, new Color(170, 47, 39)));
        spaces.add(new property("Illinois Avenue", 24, 240, 20, new Color(170, 47, 39)));
        spaces.add(new property("B&O Railroad", 25, 200, 25, Color.BLACK));
        spaces.add(new property("Atlantic Avenue", 26, 260, 22, new Color(220, 198, 35)));
        spaces.add(new property("Ventnor Avenue", 27, 260, 22, new Color(220, 198, 35)));
        spaces.add(new property("Water Works", 28, 150, 10, Color.GRAY));
        spaces.add(new property("Marvin Gardens", 29, 280, 24, new Color(220, 198, 35)));
        spaces.add(new Spaces("Go To Jail", 30));
        spaces.add(new property("Pacific Avenue", 31, 300, 26, new Color(2, 132, 45)));
        spaces.add(new property("North Carolina Avenue", 32, 300, 26, new Color(2, 132, 45)));
        spaces.add(new Spaces("Community Chest", 33));
        spaces.add(new property("Pennsylvania Avenue", 34, 320, 28, new Color(2, 132, 45)));
        spaces.add(new property("Short Line", 35, 200, 25, Color.BLACK));
        spaces.add(new Spaces("Chance", 36));
        spaces.add(new property("Park Place", 37, 350, 35, new Color(25, 50, 150)));
        spaces.add(new Spaces("Luxury Tax", 38));
        spaces.add(new property("Boardwalk", 39, 400, 50, new Color(25, 50, 150)));
    }
    
    

    public Spaces getSpaces(int position){
        return spaces.get(position);
    }
    
}
