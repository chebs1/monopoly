package Java.MONOPOLY;



import java.util.Random;

public class dice {
    public int dice1;
    public int dice2;
    public int sum;
    public boolean diceRolled = false;
    public String outCome;


    public void rollDice(){
        Random random = new Random();
        dice1 = random.nextInt(6) + 1;
        dice2 = random.nextInt(6) + 1;
        sum = dice1 + dice2;
        outCome = Integer.toString(sum);
        diceRolled = true;
    }
}
