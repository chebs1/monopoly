 



public class Spaces extends AbstractSpace {

    public String name;
    public int position;

    public Spaces(String name, int position){
        super(position);
        
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
