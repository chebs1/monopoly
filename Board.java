 



import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class Board{
    List<Spaces> spaces = new ArrayList<>();
    public Board(){
        createBoard();
    }

    private void createBoard(){
        spaces.add(new Spaces("GO", 0));
        spaces.add(new property("Mediterranean Ave", 1, 60, 2, new Color(139, 69, 19)));
        spaces.add(new Spaces("Community Chest", 2));
        spaces.add(new property("Baltic Avenue", 3, 60, 2, new Color(139, 69, 19)));
        spaces.add(new Spaces("Income Tax", 4));
        spaces.add(new property("Reading Railroad", 5, 200, 2, new Color(204, 227, 199)));
        spaces.add(new property("Oriental Avenue", 6, 100, 2, new Color(146, 185, 202)));
        spaces.add(new Spaces("Chance", 7));
        spaces.add(new property("Vermont Avenue", 8, 100, 2, new Color(146, 185, 202)));
        spaces.add(new property("Conneticut Avenue", 9, 100, 2, new Color(146, 185, 202)));
        spaces.add(new Spaces("Jail", 10));
        spaces.add(new property("St Charles Place", 11, 140, 2, new Color(149, 47, 87)));
        spaces.add(new Spaces("Electric Company", 12));
        spaces.add(new property("States Avenue", 13, 140, 2, new Color(149, 47, 87)));
        spaces.add(new property("Virginia Avenue", 14, 160, 2, new Color(149, 47, 87)));
        spaces.add(new property("Pennsylvania Railroad", 15, 200, 2, new Color(149, 47, 87)));
        spaces.add(new property("Little St James", 16, 180, 2, new Color(188, 98, 38)));
        spaces.add(new Spaces("Community Chest", 17));
        spaces.add(new property("Tennessee Avenue", 18, 180, 2, new Color(188, 98, 38)));
        spaces.add(new property("New York Avenue", 19, 200, 2, new Color(188, 98, 38)));
        spaces.add(new Spaces("Free Parking", 20));
        spaces.add(new property("Kentucky Avenue", 21, 220, 2, new Color(170, 47, 39)));
        spaces.add(new Spaces("Chance", 22));
        spaces.add(new property("Indiana Avenue", 23, 220, 2, new Color(170, 47, 39)));
        spaces.add(new property("Illinois Avenue", 24, 240, 2, new Color(170, 47, 39)));
        spaces.add(new property("B&O Railroad", 25, 200, 2, new Color(149, 47, 87)));
        spaces.add(new property("Atlantic Avenue", 26, 260, 2, new Color(220, 198, 35)));
        spaces.add(new property("Ventour Avenue", 27, 260, 2, new Color(220, 198, 35)));
        spaces.add(new Spaces("Water Works", 28));
        spaces.add(new property("Marvin Gardens", 29, 280, 2, new Color(220, 198, 35)));
        spaces.add(new Spaces("Go To Jail", 30));
        spaces.add(new property("Pacific Avenue", 31, 300, 2, new Color(2, 132, 45)));
        spaces.add(new property("North Carolina Avenue", 32, 300, 2, new Color(2, 132, 45)));
        spaces.add(new Spaces("Community Chest", 33));
        spaces.add(new property("Pennsylvania Avenue", 34, 320, 2, new Color(2, 132, 45)));
        spaces.add(new property("Short Line", 35, 200, 2, new Color(149, 47, 87)));
        spaces.add(new Spaces("Chance", 36));
        spaces.add(new property("Park Place", 37, 350, 2, new Color(211, 74, 25)));
        spaces.add(new Spaces("Luxury Tax", 38));
        spaces.add(new property("Boardwalk", 39, 400, 2, new Color(2, 132, 45)));
    }

    public Spaces getSpaces(int position){
        return spaces.get(position);
    }

}
