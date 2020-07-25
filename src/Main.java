import com.rgalex.jsnake.*;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.GridLayout;

class Main{

    public static void main(String[] args){
        System.out.println("Running JSnake.");

        JSnake jsnake = new JSnake(40, 40);
        JSnakePanel panel = new JSnakePanel(jsnake);

        JFrame window = new JFrame("JSnake");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBounds(100, 100, 800, 600);
        window.setLayout(new GridLayout(1, 1));
        window.add(panel);
        window.setVisible(true);

        jsnake.start();
    }

}
