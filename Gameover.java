import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

//ゲームオーバー画面
public class Gameover extends JPanel implements ActionListener {
    private Window window;
    JButton b = new JButton
      ("<html><span style='font-size:15pt; color:black; '>Title</span></html>");
    
    public Gameover(Window w) {
        window = w;
        setLayout(new GridLayout(1,1));
        JLabel gameover = new JLabel
          ("<html><span style='font-size:60pt; color:red;'></span></html>",JLabel.CENTER);
        JPanel go = new Background("gameover");
        go.setLayout(null);
        gameover.setBounds
          (Window.WINDOW_WIDTH/4, 50, Window.WINDOW_WIDTH/2, 2*Window.WINDOW_VERTICAL/5);
        b.setBounds(Window.WINDOW_WIDTH/2-100, 500, 200, 50);
        go.add(gameover);
        go.add(b);
        this.add(go);

        b.addActionListener(this);
        b.setActionCommand("title");
    }
    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        window.ChangePanel(cmd);           //title画面へ
    }
}