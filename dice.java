package Java.MONOPOLY;



import java.util.Random;
import java.awt.Font;

public class dice {
    public int dice1;
    public int dice2;
    public int sum;
    public boolean diceRolled = false;
    public String outCome;


    public void rollDice(){
        Random random = new Random();
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        sum = dice1 + dice2;
        outCome = Integer.toString(sum);
    }
}
