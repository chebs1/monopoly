package Java.MONOPOLY;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class main{
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Monopoly");
            window.getContentPane().add(new BoardPanel());
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
