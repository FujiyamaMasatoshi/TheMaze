import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

//Title画面
public class Title extends JPanel implements ActionListener {
    private Window window;
    JButton b1 = new JButton
      ("<html><span style='font-size:15pt; color:black; '>START</span></html>");
    JButton b2 = new JButton
      ("<html><span style='font-size:15pt; color:black; '>EXIT</span></html>");

    public Title(Window w){
        window = w;
        setLayout(new GridLayout(1,1));
        JLabel title = new JLabel
          ("<html><span style='font-size:70pt; color:blue;'></span></html>",JLabel.CENTER);
        JPanel p = new Background("title");
        p.setLayout(null);
        title.setBounds
          (Window.WINDOW_WIDTH/4, 50, Window.WINDOW_WIDTH/2, 2*Window.WINDOW_VERTICAL/5);
        b1.setBounds(Window.WINDOW_WIDTH/2-100-200, 450, 200, 50);
        b2.setBounds(Window.WINDOW_WIDTH/2-100+200, 450, 200, 50);
        p.add(title);
        p.add(b1);
        p.add(b2);
        this.add(p);

        b1.addActionListener(this);
        b1.setActionCommand("game");
        b2.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (e.getSource() == b1){
            window.ChangePanel(cmd);    //game画面へ
        }
        else if (e.getSource() == b2){  //閉じる
            System.exit(0);
        }
    }
}