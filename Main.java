import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

public class Window extends JFrame{
    JPanel cardPanel;
    CardLayout cardlayout;

    Title titlepanel;
    MazePanel gamepanel;
    Gameover gameoverpanel;
    Clear clearpanel;
    MazeModel m = new MazeModel();
    public static int WINDOW_WIDTH = 800, WINDOW_VERTICAL = 600;

    public Window() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(WINDOW_WIDTH,WINDOW_VERTICAL);
        this.setTitle("Title");
        cardPanel = new JPanel();
        cardlayout = new CardLayout();
        cardPanel.setLayout(cardlayout);
        titlepanel = new Title(this);
        gameoverpanel = new Gameover(this);
        clearpanel = new Clear(this);
        cardPanel.add(titlepanel, "title");
        cardPanel.add(gameoverpanel, "gameover");
        cardPanel.add(clearpanel, "clear");
        this.add(cardPanel);

        //確認
        // m.PrintMazeArray();
    }

    public void ChangePanel(String cmd){
        if(cmd == "game"){
            Player p = new Player(m, this);
            MazeView mv = new MazeView(p);
            CharaView cv = new CharaView(p);
            Control c = new Control(p,m);
            gamepanel = new MazePanel(m, mv, cv, c, p);
            cardPanel.add(gamepanel, "game");
            cardlayout.show(cardPanel, cmd);
            gamepanel.requestFocusInWindow();
    }else{
        cardlayout.show(cardPanel, cmd);
    }
}

    public static void main(String[] args) {
        Window w = new Window();
        w.setVisible(true);
    }
}

//背景に画像
class Background extends JPanel {
  Image image;
  Image img;
  
  Background(String s) {
    switch(s){
      case "title" :
        try{
        img = ImageIO.read(new File("title.png"));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        break;
      case "clear" :
        try{
          img = ImageIO.read(new File("Gameclear.png"));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        break;
      case "gameover" :
        try{
          img = ImageIO.read(new File("gameover.png"));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
        break;
      default:
        System.out.println("ERROR");
    }
    image  = img.getScaledInstance
            (Window.WINDOW_WIDTH, Window.WINDOW_VERTICAL, Image.SCALE_DEFAULT);
    setOpaque(false); // 背景透明
  }
  
  public void paint(Graphics g) {
  g.drawImage(image, 0, 0, this); 
  super.paint(g); 
  }
}