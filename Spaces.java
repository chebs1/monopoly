package Java.MONOPOLY;



public class Spaces {

    private String name;
    private int position;

    public Spaces(String name, int position){
        this.name = name;
        this.position = position;
    }

    public String getName(){
        return name;
    }

    public int getPosition(){
        return position;
    }

}
