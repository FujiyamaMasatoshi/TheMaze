import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

//クリア画面
public class Clear extends JPanel implements ActionListener {
    private Window window;
    JButton b = new JButton
      ("<html><span style='font-size:15pt; color:black; '>Title</span></html>");
    
    public Clear(Window w) {
        window = w;
        setLayout(new GridLayout(1,1));
        JLabel clear = new JLabel
          ("<html><span style='font-size:60pt; color:red;'></span></html>",JLabel.CENTER);
        JPanel c = new Background("clear");
        c.setLayout(null);
        clear.setBounds
          (Window.WINDOW_WIDTH/4, 50, Window.WINDOW_WIDTH/2, 2*Window.WINDOW_VERTICAL/5);
        b.setBounds
          (Window.WINDOW_WIDTH/2-100, 450, 200, 50);
        c.add(clear);
        c.add(b);
        this.add(c);

        b.addActionListener(this);
        b.setActionCommand("title");
    }
    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        window.ChangePanel(cmd);           //title画面へ
    }
}