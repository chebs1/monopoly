package Java.MONOPOLY;


import javax.swing.JFrame;
import javax.swing.JPanel;   
public class main extends BoardPanel{
    public static void main(String[] args){
        BoardPanel  imageBoard = new BoardPanel();
        JFrame window = new JFrame(); 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        window.setResizable(false);
        window.setSize(900, 950);
        window.getContentPane().add(new BoardPanel()); 
        window.setVisible(true);
        window.setLocationRelativeTo(null);


    }
}
