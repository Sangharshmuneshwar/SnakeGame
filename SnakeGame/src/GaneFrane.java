import javax.swing.*;

public class GaneFrane extends JFrame {

    GaneFrane(){
        GameBoard gameBoard = new GameBoard();
        this.add(gameBoard);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
