import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.util.List;
import java.io.*;
import javax.imageio.*;
import java.util.Random;

// Model

class zahyo {
    /*構造体としてのzahyo class */
    int x;
    int y;
}

class Trap{
    public Trap(MazeModel maze, Player pc){
        //宝箱をランダムな場所に移す
        //1. tの新しい出現座標の決定
        Random trapPlace = new Random();
        int t_y = trapPlace.nextInt(maze.MAZE_VERTICAL);
        int t_x = trapPlace.nextInt(maze.MAZE_WIDTH);
        while(maze.MazeArray[t_y][t_x] == '#' 
            || maze.MazeArray[t_y][t_x] == 'k' 
            || maze.MazeArray[t_y][t_x] == 't'){
            t_y = trapPlace.nextInt(maze.MAZE_VERTICAL);
            t_x = trapPlace.nextInt(maze.MAZE_WIDTH);
        }
        //この時点でMazeArray[t_y][t_x]は' 'を示している。
        //2. 宝箱だったところを消す
        maze.MazeArray[(pc.getPosition().y - 2)/5][(pc.getPosition().x - 2)/5] = ' ';
        
        //3. Trapの発動
        Random random = new Random();
        int RandomJudge;
        if(pc.haveakey == 1){
            RandomJudge = random.nextInt(90);
        }else{
            RandomJudge = random.nextInt(100);
        }
        if(0 <= RandomJudge && RandomJudge <= 29){
            //2. life -1
            pc.life -= 1;
            //確認のためのprint
            System.out.println("Player have "+pc.life+" lifes");
        }else if(30 <= RandomJudge && RandomJudge <= 39){
            //3. 初期位置に戻される
            pc.InitPosition(maze);
            //確認
            System.out.println("Player went to InitPosition");
        }else if(40 <= RandomJudge && RandomJudge <= 49){
            //4. ランダム場所に移動
            Random place = new Random();
            int x_RandomPrace = place.nextInt(maze.MAZE_VERTICAL*5);
            int y_RandomPrace = place.nextInt(maze.MAZE_WIDTH*5);
            while(maze.ViewArray[y_RandomPrace][x_RandomPrace] == '#'){
                x_RandomPrace = place.nextInt(maze.MAZE_VERTICAL*5);
                y_RandomPrace = place.nextInt(maze.MAZE_WIDTH*5);
            }
            pc.setPosition(x_RandomPrace, y_RandomPrace);
            //確認
            System.out.println("Player went to RandoPosition");
        }else if(50 <= RandomJudge && RandomJudge <= 69){
            //5. 懐中電灯をgetして明るさを広くする
            try {
                MazeView.img_kurayami = ImageIO.read(new File("kurayami_wide.png"));
            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }
            //確認
            System.out.println("Player got a light");
        }else if(70 <= RandomJudge && RandomJudge <= 89){
            //6. keyの位置を返す
            /*まずは上方向(配列上の添字が小さい方向)なのか下方向なのか
            (配列上の添字が大きい方向)を判定する
            pcとkeyの横と縦の差を返す*/
            if(pc.haveakey != 1){
                int dif_x;
                int dif_y;
                if(pc.x < maze.key_x){
                    if(pc.y < maze.key_y){
                        dif_x = maze.key_x - pc.x;
                        dif_y = maze.key_y - pc.y;
                        if(dif_x < dif_y){
                            System.out.println("Key: Go Down");
                            pc.compass = 'd';
                        }else{
                            System.out.println("Key: Go Right");
                            pc.compass = 'r';
                        }
                    }else{
                        dif_x = maze.key_x - pc.x;
                        dif_y = pc.y- maze.key_y;
                        if(dif_x < dif_y){
                            System.out.println("Key: Go Up");
                            pc.compass = 'u';
                        }else{
                            System.out.println("Key: Go Right");
                            pc.compass = 'r';
                        }
                    }
                }else{
                    //keyを持っていたらgoalの位置を返す
                    if(pc.y < maze.key_y){
                        dif_x = pc.x - maze.key_x;
                        dif_y = maze.key_y - pc.y;
                        if(dif_x < dif_y){
                            System.out.println("Key: Go Down");
                            pc.compass = 'd';
                        }else{
                            System.out.println("Key: Go Left");
                            pc.compass = 'l';
                        }
                    }else{
                        dif_x = pc.x - maze.key_x;
                        dif_y = pc.y- maze.key_y;
                        if(dif_x < dif_y){
                            System.out.println("Key: Go Up");
                            pc.compass = 'u';
                        }else{
                            System.out.println("Key: Go Left");
                            pc.compass = 'l';
                        }
                    }
                }
            }else{
                int dif_x;
                int dif_y;
                if(pc.x < maze.goal_x){
                    if(pc.y < maze.goal_y){
                        dif_x = maze.goal_x - pc.x;
                        dif_y = maze.goal_y - pc.y;
                        if(dif_x < dif_y){
                            System.out.println("Goal: Go Down");
                            pc.compass = 'd';
                        }else{
                            System.out.println("Goal: Go Right");
                            pc.compass = 'r';
                        }
                    }else{
                        dif_x = maze.goal_x - pc.x;
                        dif_y = pc.y- maze.goal_y;
                        if(dif_x < dif_y){
                            System.out.println("Goal: Go Up");
                            pc.compass = 'u';
                        }else{
                            System.out.println("Goal: Go Right");
                            pc.compass = 'r';
                        }
                    }
                }else{
                    if(pc.y < maze.goal_y){
                        dif_x = pc.x - maze.goal_x;
                        dif_y = maze.goal_y - pc.y;
                        if(dif_x < dif_y){
                            System.out.println("Goal: Go Down");
                            pc.compass = 'd';
                        }else{
                            System.out.println("Goal: Go Left");
                            pc.compass = 'l';
                        }
                    }else{
                        dif_x = pc.x - maze.goal_x;
                        dif_y = pc.y- maze.goal_y;
                        if(dif_x < dif_y){
                            System.out.println("Goal: Go Up");
                            pc.compass = 'u';
                        }else{
                            System.out.println("Goal: Go Left");
                            pc.compass = 'l';
                        }
                    }
                }
            }
        }else{
            if(pc.haveakey == 1){
                pc.haveakey = 0;
                System.out.println("Player lost key");
            }
        }
        //4. trap finished
        //MazeArrayに新たにtを追加する
        maze.MazeArray[t_y][t_x] = 't';
        //5. ViewArrayを新しく作る
        maze.MakeViewArray();
    }
}

@SuppressWarnings("deprecation")
class Player extends Observable{
    //現在の配列における位置を格納する
    public int x, y; //配列
    public int MoveSpeed; /** = const */
    public int haveakey; //keyを持っているかどうかの判定
    public int life;
    //Trapが発動済みかどうかを調べるへんすうを用意する
    public int trapdone = 0;
    
    MazeModel m;
    private Window window;
    
    // ↓阿部が追加しました。
    public char direction = 's';
    public char compass;

    public Player(MazeModel m, Window w){
        this.m = m;
        this.window = w;
    }

    //Playerの初期データのセット
    public void InitData(MazeModel maze){
        InitPosition(m);
        life = 3;
        haveakey = 0;
    }

    //Judge Game over or not?
    public void JudgeDead(){
        if(this.life == 0){
            System.out.println("Life is 0 -> Game Over.");
            window.ChangePanel("gameover");
        }
    }

    //PlayerがTrapに引っかかっているかどうかの判定をPlayerが動くたびに判定する
    public void JudgeTrap(MazeModel maze){
        if(maze.ViewArray[this.y][this.x] == 't'){
            Trap trap = new Trap(m, this);
        }
    }

    //keyを持っているかどうかの判定を行う
    public void JudgeHaveKey(MazeModel maze){
        if(maze.ViewArray[this.y][this.x] == 'k'){
            this.haveakey = 1;
            maze.MazeArray[(getPosition().y - 2)/5][(getPosition().x - 2)/5] 
            = ' ';
            maze.MakeViewArray();
        }
    }

    public void JudgeGoal(MazeModel maze){
        if(maze.ViewArray[this.y][this.x] == 'g' && this.haveakey == 1){
            window.ChangePanel("clear");
        }
    }

    public void Judge(MazeModel maze){
        this.JudgeTrap(maze);
        this.JudgeDead();
        this.JudgeHaveKey(maze);
        this.JudgeGoal(maze);
    }


    public void InitPosition(MazeModel maze){
        // zahyo initposition = new zahyo();
        for(int i = 0; i < maze.MAZE_VERTICAL*5; i++){
            for(int j = 0; j < maze.MAZE_WIDTH*5; j++){
                if(maze.ViewArray[i][j] == 's'){
                    setPosition(j, i);
                    this.x = j;
                    this.y = i;
                    break;
                }
            }
        }
        // return initposition;
    }

    public void setPosition(int x, int y){
        //MazeArrayで決定されたPlayerの位置をセットしていくメソッド
        this.x = x;
        this.y = y;
        // System.out.println("("+this.x+", "+this.y+")");
        setChanged();
        notifyObservers();
    }

    public zahyo getPosition(){
        zahyo result = new zahyo();
        result.x = this.x;
        result.y = this.y;
        return result;
    }

}

class MazeModel{
    public int BlockSize = 90; /* Viewで使うBlockの大きさ*/
    public int MAZE_VERTICAL = 41; /**Mazeのタテ大きさ */
    public int MAZE_WIDTH = 41; /**Mazeの横の大きさ */

    //keyの位置を保存
    public int key_x;
    public int key_y;

    //Goalの位置を保存
    public int goal_x;
    public int goal_y;

    //Startの位置を保存
    public int start_x;
    public int start_y;


    /*MazeModelは配列を用いる, fileからMazeの情報を読み取るための配列MazeArray*/
    public char[][] MazeArray = new char [MAZE_VERTICAL][MAZE_WIDTH];

    /**MazeArrayから実際に表示させるために使う配列を作る */
    public char[][] ViewArray = new char [MAZE_VERTICAL*5][MAZE_WIDTH*5];
    private int i = 0, j = 0; //0 <=i <= MAZE_VERTICAL-1; 0 <= j <= MAZE_WIDTH-1

    // //テキストファイルからMazeを読み込む
    // public void FileRead(){
    //     try{
    //         String filename = "Maze.txt";
    //         // String filename = "MazeArray1.txt"; //テスト用
    //         File file = new File(filename);
    //         FileReader filereader = new FileReader(file);

    //         int ch;
    //         int i = 0; int j = 0;
    //         while((ch = filereader.read()) != -1){
    //             if((char)ch != ','){
    //                 if((char)ch == '\n'){
    //                     j = 0; i++;
    //                 }else{
    //                     MazeArray[i][j] = (char)ch;
    //                     j++;
    //                 }
    //             }
    //         }
    //         filereader.close();
    //     }catch(FileNotFoundException e){
    //         System.out.println(e);
    //     }catch(IOException e){
    //         System.out.println(e);
    //     }
    //     // PrintMazeArray();
    // }

    public char WALL = '#';
    public char PATH = ' ';
    public char START = 's';
    public char KEY = 'k';
    public char TREASURE = 't';
    public char GOAL = 'g';
    Random random = new Random();

    public void FileRead(){
        

        

        // Initialize the maze
        for (int i = 0; i < MAZE_VERTICAL; i++) {
            for (int j = 0; j < MAZE_WIDTH; j++) {
                MazeArray[i][j] = WALL;
            }
        }

        // decide start position
        int startX = random.nextInt(MAZE_WIDTH / 2) * 2 + 1;
        int startY = random.nextInt(MAZE_VERTICAL / 2) * 2 + 1;
        MazeArray[startY][startX] = START; // set start pos

        // make maze
        carvePassages(startX, startY);

        //set goal position
        placeGoal(); //ゴールの設置
        // MazeArray[0][startX] = 'g';

        // set treasures
        SetTreasures(20);

        // set key
        SetKey(); 
        // MazeArray[startY][startX + 1] = 'k';
    }

    public void carvePassages(int x, int y){
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, 2});
        directions.add(new int[]{2, 0});
        directions.add(new int[]{0, -2});
        directions.add(new int[]{-2, 0});
        Collections.shuffle(directions);

        for (int[] direction : directions) {
            int nx = x + direction[0];
            int ny = y + direction[1];

            if (nx > 0 && ny > 0 && nx < MAZE_WIDTH - 1 && ny < MAZE_VERTICAL - 1 && MazeArray[ny][nx] == WALL) {
                MazeArray[ny][nx] = PATH;
                MazeArray[y + direction[1] / 2][x + direction[0] / 2] = PATH;
                carvePassages(nx, ny);
            }
        }
    }

    public void placeGoal() {
        List<int[]> goalPositions = new ArrayList<>();
        for (int x = 1; x < MAZE_WIDTH - 1; x++) {
            if (MazeArray[1][x] == PATH) {
                goalPositions.add(new int[]{x, 0});
            }
            if (MazeArray[MAZE_VERTICAL - 2][x] == PATH) {
                goalPositions.add(new int[]{x, MAZE_VERTICAL - 1});
            }
        }
        for (int y = 1; y < MAZE_VERTICAL - 1; y++) {
            if (MazeArray[y][1] == PATH) {
                goalPositions.add(new int[]{0, y});
            }
            if (MazeArray[y][MAZE_WIDTH - 2] == PATH) {
                goalPositions.add(new int[]{MAZE_WIDTH - 1, y});
            }
        }

        int[] goal = goalPositions.get(random.nextInt(goalPositions.size()));
        MazeArray[goal[1]][goal[0]] = GOAL;
    }

    public void SetTreasures(int n_treasures){
        // ランダムに座標を決定
        int n_set = 0;
        while (n_set<n_treasures){
            int i = random.nextInt(MAZE_VERTICAL);
            int j = random.nextInt(MAZE_WIDTH);
            if (MazeArray[i][j] == ' '){
                MazeArray[i][j] = 't';
                n_set++;
            }
        }
    }

    public void SetKey(){
        while (true){
            int i = random.nextInt(MAZE_VERTICAL);
            int j = random.nextInt(MAZE_WIDTH);
            if(MazeArray[i][j] == ' '){
                MazeArray[i][j] = 'k';
                break;
            }
        }
        
    }


    //MazeArrayを出力する
    public void PrintMazeArray (){
        for (i = 0; i < MAZE_VERTICAL; i++){
            for(j = 0; j < MAZE_WIDTH; j++){
                System.out.print(MazeArray[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    //MazeArray --> ViewArrayへの変換
    public void MakeViewArray(){
        int VerticalLength = 0;
        int WidthLength = 0;
        for(int i = 0; i < MAZE_VERTICAL; i++){
            for(int j = 0; j < MAZE_WIDTH; j++){
                // char comp = MazeArray[i][j];
                if(MazeArray[i][j] == 's'){
                    for(int k = 0; k < 5; k++){
                        for(int l = 0; l < 5; l++){
                            if(l == 2 && k == 2){
                                ViewArray[5*i+k][5*j+l] = MazeArray[i][j];
                                this.start_y = 5*i+k;
                                this.start_x = 5*j+l;
                            }else{
                                ViewArray[5*i+k][5*j+l] = ' ';
                            }
                        }
                    }
                }else if(MazeArray[i][j] == 'g'){
                    for(int k = 0; k < 5; k++){
                        for(int l = 0; l < 5; l++){
                            if(l == 2 && k == 2){
                                ViewArray[5*i+k][5*j+l] = MazeArray[i][j];
                                this.goal_y = 5*i+k;
                                this.goal_x = 5*j+l;
                            }else{
                                ViewArray[5*i+k][5*j+l] = ' ';
                            }
                        }
                    }
                }else if(MazeArray[i][j] == 'k'){
                    for(int k = 0; k < 5; k++){
                        for(int l = 0; l < 5; l++){
                            if(l == 2 && k == 2){
                                ViewArray[5*i+k][5*j+l] = MazeArray[i][j];
                                this.key_y = 5*i+k;
                                this.key_x = 5*j+l;
                            }else{
                                ViewArray[5*i+k][5*j+l] = ' ';
                            }
                        }
                    }
                }else if(MazeArray[i][j] == 't'){
                    for(int k = 0; k < 5; k++){
                        for(int l = 0; l < 5; l++){
                            if(l == 2 && k == 2){
                                ViewArray[5*i+k][5*j+l] = 't';
                            }else{
                                ViewArray[5*i+k][5*j+l] = ' ';
                            }
                        }
                    }
                }else{
                    for(int k = 0; k < 5; k++){
                        for(int l = 0; l < 5; l++){
                            ViewArray[5*i+k][5*j+l] = MazeArray[i][j];
                        }
                    }
                }
            }
        }
    }

    public void PrintViewArray(){
        int i = 0, j = 0;
        for(i = 0; i < MAZE_VERTICAL*5; i++){
            for(j = 0; j < MAZE_WIDTH*5; j++){
                System.out.print(ViewArray[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void MakeSureArray(){
        int i, j;
        for(i = 0; i < MAZE_VERTICAL*5; i++){
            for(j = 0; j < MAZE_WIDTH*5; j++){
                System.out.print(ViewArray[i][j]+",");
            }
        }
        System.out.println();
    }

}


//#########################################################################
// View 


//迷路の表示をするクラスMazeView
class MazeView extends JPanel{
    private int x, y;
    private char[][] map;
    private int i, j;
    private int b, windowJ, windowI;
    private int count = 3;
    Player p;
    MazeModel maze = new MazeModel();
    Graphics g;
    static Image img_kurayami;
    Image img_kabe, img_yuka, img_takara, img_door;
    Image img_mapkey, img_key, img_heart, img_nonheart;
    Image img_up, img_down, img_left, img_right;
    public MazeView(Player p){
        this.p = p;
        setPreferredSize(new Dimension(WIDTH,HEIGHT));

        // 画像ファイルの読み込み
        try {
          img_kabe = ImageIO.read(new File("kabe.png"));
          img_yuka = ImageIO.read(new File("yuka.png"));
          img_kurayami = ImageIO.read(new File("kurayami.png"));
          img_takara = ImageIO.read(new File("takara.png"));
          img_door = ImageIO.read(new File("door.png"));
          img_mapkey = ImageIO.read(new File("mapkey.png"));
          img_key = ImageIO.read(new File("key.png"));
          img_heart = ImageIO.read(new File("heart.png"));
          img_nonheart = ImageIO.read(new File("nonheart.png"));
          img_up = ImageIO.read(new File("up.png"));
          img_down = ImageIO.read(new File("down.png"));
          img_left = ImageIO.read(new File("left.png"));
          img_right = ImageIO.read(new File("right.png"));
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    
    //キャラクター中心の座標に変換するメソッド
    public int windowX(int x){
        return x*b - p.x*(b/5) + Window.WINDOW_WIDTH/2 -b/10;
    }
    public int windowY(int y){
        return y*b - p.y*(b/5) + Window.WINDOW_VERTICAL/2 -b/10;
    }

    //迷路の表示をここでしている
    public void MazeDraw(MazeModel m, Graphics g){
        map = m.MazeArray;
        x = m.MAZE_VERTICAL;
        y = m.MAZE_WIDTH;
        b = m.BlockSize;

        // 迷路の表示
        for(i=0; i<x; i++){
            for(j=0; j<y; j++){
                char c = map[i][j];
                windowJ = windowX(j);
                windowI = windowY(i);
                if(c == '#'){
                    g.drawImage(img_kabe,windowJ,windowI,b,b,null);
                }else if(c == 't'){
                    g.drawImage(img_takara,windowJ,windowI,b,b,null);
                }else if(c == 'k'){
                    g.drawImage(img_mapkey,windowJ,windowI,b,b,null);
                }else if(c == 'g'){
                    g.drawImage(img_door,windowJ,windowI,b,b,null);
                } else {
                    g.drawImage(img_yuka,windowJ,windowI,b,b,null);
                }
            }
        } 

        //暗くなるエフェクト
        g.drawImage(img_kurayami,0,0,Window.WINDOW_WIDTH,Window.WINDOW_VERTICAL,null); 

        //体力の表示
        for(i=1;i<=3;i++){
            if(i<=p.life){
                g.drawImage(img_heart,Window.WINDOW_WIDTH-50*i-40,10,50,50,null); 
            } else {
                g.drawImage(img_nonheart,Window.WINDOW_WIDTH-50*i-40,10,50,50,null);
            }
            
        }

        //鍵の有無
        if(p.haveakey==1){
            g.drawImage(img_key,40,10,50,50,null);
        }

        // Trap 方向の表示
        if(p.compass != 'n'){
            if(p.compass == 'u'){
                g.drawImage(img_up,0,0,Window.WINDOW_WIDTH,Window.WINDOW_VERTICAL,null); 
            }else if(p.compass == 'l'){
                g.drawImage(img_left,0,0,Window.WINDOW_WIDTH,Window.WINDOW_VERTICAL,null); 
            }else if(p.compass == 'd'){
                g.drawImage(img_down,0,0,Window.WINDOW_WIDTH,Window.WINDOW_VERTICAL,null); 
            }else if(p.compass == 'r'){
                g.drawImage(img_right,0,0,Window.WINDOW_WIDTH,Window.WINDOW_VERTICAL,null); 
            }
            if(--count==0){
                count = 3;
                p.compass = 'n';
            }
        }
    }
}

//キャラクターの表示をするクラスCharaView
class CharaView {
  Player p;
  Image w1, w2, a1, a2, s1, s2, d1, d2;
  private int count = 1;
  public CharaView(Player p){
      this.p = p;

      // 画像ファイルの読み込み
      try {
        w1 = ImageIO.read(new File("fox_w1.png"));
        w2 = ImageIO.read(new File("fox_w2.png"));
        a1 = ImageIO.read(new File("fox_a1.png"));
        a2 = ImageIO.read(new File("fox_a2.png"));
        s1 = ImageIO.read(new File("fox_s1.png"));
        s2 = ImageIO.read(new File("fox_s2.png"));
        d1 = ImageIO.read(new File("fox_d1.png"));
        d2 = ImageIO.read(new File("fox_d2.png"));
      } catch (Exception e) {
          System.out.println(e);
          System.exit(0);
      }

  }

  // クラスControlで入力したキーによって表示させる画像を変え、
  // 二つの画像を交互に表示することでキャラクターの動きを作っている。
  public void CharaDraw(Graphics g){
    if(p.direction == 'w'){  //上に移動
      if(count==1){
        g.drawImage(w1,Window.WINDOW_WIDTH/2-27, Window.WINDOW_VERTICAL/2-27, 54, 54, null);
        count=2;
      }else{
        g.drawImage(w2,Window.WINDOW_WIDTH/2-27, Window.WINDOW_VERTICAL/2-27, 54, 54, null);
        count=1;
      }
    }else if(p.direction == 'a'){  //左に移動
      if(count==1){
        g.drawImage(a1,Window.WINDOW_WIDTH/2-27, Window.WINDOW_VERTICAL/2-27, 54, 54, null);
        count=2;
      }else{
        g.drawImage(a2,Window.WINDOW_WIDTH/2-27, Window.WINDOW_VERTICAL/2-27, 54, 54, null);
        count=1;
      }
    }else if(p.direction == 's'){  //下に移動
      if(count==1){
        g.drawImage(s1,Window.WINDOW_WIDTH/2-27, Window.WINDOW_VERTICAL/2-27, 54, 54, null);
        count=2;
      }else{
        g.drawImage(s2,Window.WINDOW_WIDTH/2-27, Window.WINDOW_VERTICAL/2-27, 54, 54, null);
        count=1;
      }
    }else if(p.direction == 'd'){  //右に移動
      if(count==1){
        g.drawImage(d1,Window.WINDOW_WIDTH/2-27, Window.WINDOW_VERTICAL/2-27, 54, 54, null);
        count=2;
      }else{
        g.drawImage(d2,Window.WINDOW_WIDTH/2-27, Window.WINDOW_VERTICAL/2-27, 54, 54, null);
        count=1;
      }
    }
  }
} 

// GUIで表示させるためのクラスMazePanel
// Observerを用いて、キャラクターの位置が移動するたびにupdateを呼び出している
@SuppressWarnings("deprecation")
class MazePanel extends JPanel implements Observer{
    MazeView mv;
    MazeModel m;
    CharaView cv;
    Control c;
    Player p;

    public MazePanel(MazeModel m, MazeView mv, CharaView cv, Control c, Player p) {
        this.m = m;
        this.mv = mv;
        this.cv = cv;
        this.c = c;
        this.p = p;
        p.addObserver(this);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(c);
    }

    // ここでまとめて表示している
    public void paintComponent(Graphics g){  
        super.paintComponent(g);
        mv.MazeDraw(m,g);
        cv.CharaDraw(g);
    }

    // 移動先で起こる事(鍵、トラップ、ゴール)の処理をしてからrepaintをしている。
    public void update(Observable o,Object arg){
        p.Judge(m);
        repaint();
    }
}

//###############################################################################


class Control implements KeyListener {
  private Player player;
  private MazeModel maze;
  private zahyo p = new zahyo();
  private char Array[][];


  public Control (Player a, MazeModel b){
    player = a;
    maze = b;
    maze.FileRead();
    // maze.generateMaze();
    maze.MakeViewArray();
    player.InitData(maze);
    p = player.getPosition();
    Array = maze.ViewArray;
  }

  public void keyTyped(KeyEvent e){
  switch(e.getKeyChar()){   
    case 'w' : 
      if(player.y > 0 && Array[player.y-2][player.x] != '#') { player.y--;} 
      player.direction = 'w';
      break;
    case 's' :
      if(player.y < maze.MAZE_VERTICAL*5 && Array[player.y+2][player.x] != '#') {player.y++;}
      player.direction = 's';
      break;
    case 'a' :
      if(player.x > 0 && Array[player.y][player.x-2] != '#') {player.x--;} 
      player.direction = 'a';
      break;
    case 'd' : 
      if(player.x < maze.MAZE_WIDTH*5 && Array[player.y][player.x+2] != '#') {player.x++;}
      player.direction = 'd';
      break;
    }
    player.setPosition(player.x, player.y);
  }
  
  public void keyPressed(KeyEvent e) { }
  public void keyReleased(KeyEvent e) { }
}