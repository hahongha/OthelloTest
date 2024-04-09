package view;

import control.Mouse;
import control.TimeControl;
import main.Main;
import model.BoardHelper;
import model.Piece;
import model.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class MainPanel extends JPanel implements Runnable{

    public static String saveDataPath = System.getProperty("user.dir") +File.separator
            + "lib" + File.separator+"Pic"+File.separator;
    public static final int SQUARE_SIZE= 80;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE/2;
    public static final int SPACING = SQUARE_SIZE/10;// khoảng cách giữa các ô
    public static final int ARC = 10;
    public static int BOARD_WIDTH = (Board.COLS + 1) * SPACING + Board.COLS * SQUARE_SIZE;
    public static int BOARD_HEIGHT = (Board.ROWS + 1) * SPACING + Board.ROWS * SQUARE_SIZE;
    public static Font fontString = new Font("Monospaced", Font.BOLD, 40);
    public static Font fontScore = new Font("Monospaced", Font.BOLD, 80);

    private BufferedImage clock= null;
    private BufferedImage imgMusicOn = null;
    private BufferedImage imgMusicOff = null;
    public boolean isMusic= true;

    public Board board;

    private Thread game;

    int FPS=60;
    public boolean running;

    Mouse mouse;

    private JButton btnPause;

    private JButton btnExit;
    public TimeControl timeWhite;
    public TimeControl timeBlack;

    private boolean start;

    public BoardHelper boardHelper;

    public ArrayList<Piece> dudoan;
    Main main;

    public MainPanel(Main jf){
        main= jf;
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(new Color(160,160,160));
        board = new Board();
        dudoan = new ArrayList<>();
        boardHelper = new BoardHelper(board.getBoard(),dudoan);
        dudoan= boardHelper.dudoanvitri(board.getBoard(), 1);
        mouse= new Mouse(board, this, main);
        start = true;
        timeWhite = new TimeControl();
        timeBlack = new TimeControl();

//        btnPause = new JButton("pause");
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
    }

    public void update(){

        if(board.isGameOn()&& board.isGameContinue()) {
            int col = getPoint(mouse.mx);
            int row = getPoint(mouse.my);
            if (row <= Board.ROWS && col <= Board.COLS) {
                if (mouse.pressed) {
                    board.update(row, col);
                    boardHelper= new BoardHelper(board.getBoard(), new ArrayList<>());
                    int value=0;
                    if(board.isBlack) value = 1;
                    if (board.isWhite) value= 2;
                    dudoan = boardHelper.dudoanvitri(board.getBoard(), value);
                    mouse.pressed = false;
                    if (board.WhiteContinue) timeWhite.setStartTime(System.nanoTime());
                    //board.printBoard();
                }
            }
            timeBlack.timeRun(board.isBlack);
            timeWhite.timeRun(board.isWhite);
        }
    };

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(board.isGameOn()){
            if (board.isGameContinue()) renderplay(g);
            else renderContinue(g);
        }else renderEnd(g);
        //renderEnd(g);
       g.dispose();
    }

    public void renderplay(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,MainPanel.BOARD_WIDTH, MainPanel.BOARD_HEIGHT);
        //ve bang
        for (int j = 0; j < Board.COLS; j++) {
            for (int i = 0; i < Board.ROWS; i++) {
                g.setColor(new Color(0x3C6255));
                g.drawRoundRect(getTileX(j), getTileY(i), SQUARE_SIZE, SQUARE_SIZE, ARC, ARC);
                g.fillRoundRect(getTileX(j), getTileY(i), SQUARE_SIZE, SQUARE_SIZE, ARC, ARC);
            }
        }
        for (int j = 0; j < Board.COLS; j++) {
            for (int i = 0; i < Board.ROWS; i++) {
                Piece current = board.getBoard()[i][j];
                g.setColor(current.setBackGround());
                g.drawOval(getTileX(j)+2, getTileY(i)+2, SQUARE_SIZE-5, SQUARE_SIZE-5);
                g.fillOval(getTileX(j)+2, getTileY(i)+2, SQUARE_SIZE-5, SQUARE_SIZE-5);
            }
        }

        //ve du doan
        int value=0;
        if(board.isBlack){
            value=1;
        }else if(board.isWhite) value=2;
        for (Piece piece: dudoan){
            g.setColor(Color.BLUE);
            g.fillRoundRect(getTileX(piece.getCol()), getTileY(piece.getRow()), SQUARE_SIZE, SQUARE_SIZE, ARC, ARC);
        }


        //ve thoi gian
        try {
            clock = ImageIO.read(new File(startScreen.saveDataPath + "/alarm.png"));
        }catch (Exception e){
            e.printStackTrace();
        }

        //thoi gian cua mau trang
        g.drawImage(clock,MainPanel.BOARD_WIDTH+30, 20, 50,50,null);

        g.setColor(Color.BLACK);
        g.setFont(fontString);
        g.drawString(timeWhite.getFormatedTime(), MainPanel.BOARD_WIDTH+90, 60);


        //thoi gian cua mau den
        g.drawImage(clock,Main.WIDTH-100, Main.HEIGHT-120, 50,50,null);
        g.setColor(Color.BLACK);
        g.setFont(fontString);
        g.drawString(timeBlack.getFormatedTime(), Main.WIDTH-230, Main.HEIGHT-80);


        //ve bang diem
        g.setColor(new Color(0x3C6255));
        g.fillRoundRect(MainPanel.BOARD_WIDTH+50, 120, 280,250,MainPanel.ARC, MainPanel.ARC);
            //ve 2 o diem
        g.setColor(Color.BLACK);
        g.fillRoundRect(MainPanel.BOARD_WIDTH+70, 140, 100,100,MainPanel.ARC, MainPanel.ARC);
        g.fillRoundRect(MainPanel.BOARD_WIDTH+210, 140, 100,100,MainPanel.ARC, MainPanel.ARC);
            //ve 2 quan co
        g.fillOval(MainPanel.BOARD_WIDTH+80, 260, 80,80);
        g.setColor(Color.white);
        g.fillOval(MainPanel.BOARD_WIDTH+220, 260, 80,80);

        //ve nut Pause
        g.setColor(new Color(0x3C6255));
        g.fillRect(MainPanel.BOARD_WIDTH+80, 400, 220,70);
        g.setFont(fontString);
        g.setColor(Color.white);
        g.drawString("Pause", MainPanel.BOARD_WIDTH+120, 450);

        //ve nut Exit
        g.setColor(Color.red);
        g.fillRect(MainPanel.BOARD_WIDTH+80, 500, 220,70);
        g.setFont(fontString);
        g.setColor(Color.white);
        g.drawString("Exit", MainPanel.BOARD_WIDTH+120, 550);

        // ve diem
        g.setFont(fontScore);
        g.setColor(Color.WHITE);
        g.drawString(""+board.getScore(1),MainPanel.BOARD_WIDTH+80, 220);
        g.drawString(""+board.getScore(2),MainPanel.BOARD_WIDTH+220, 220);




    };

    public void renderEnd(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Main.WIDTH, Main.HEIGHT);

        g.setColor(new Color(0x3C6255));
        g.fillRoundRect(350,100,400,550,20,20);

        g.setColor(Color.WHITE);
        g.setFont(fontString);
        String s="";
        int score=0;
        if(board.getScore(1) > board.getScore(2)){
            s= "Black";
            score= board.getScore(1);
        }else{
            s= "White";
            score= board.getScore(2);
        }
        s+= " is Win";
        g.drawString(s,400,200);
        g.drawString("Score:"+ String.valueOf(score) , 400,300);

        g.setFont(fontScore);
        g.setColor(Color.red);
        g.fillRoundRect(400,400,300,100,20,20);
        g.setColor(Color.WHITE);
        g.drawString("Exit", 450,460);

    };

    public void renderContinue(Graphics g){
//        setBackground(new Color(0xFDF9BC));
//        setOpaque(false);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Main.WIDTH, Main.HEIGHT);

        g.setColor(new Color(0x3C6255));
        g.fillRoundRect(350,100,400,550,20,20);

        g.setColor(new Color(0xFDF9BC));
        g.fillRoundRect(400,520, 300,80,20,20);
        g.fillRoundRect(400,390, 300,80,20,20);
        g.fillRoundRect(400,260, 300,80,20,20);

        g.setColor(Color.BLACK);
        g.setFont(fontString);
        g.drawString("EXIT", 500, 565);
        g.drawString("REPLAY", 490, 435);
        g.drawString("RESUME", 490, 305);

        try {
            imgMusicOn = ImageIO.read(new File(saveDataPath + "/MusicOn.png"));
            imgMusicOff = ImageIO.read(new File(saveDataPath + "/MusicOff.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        if(isMusic) {
            g.drawImage(imgMusicOn, Main.WIDTH/2-40, 150, 80, 70, null);
        }else g.drawImage(imgMusicOff, 400, 150, 80, 70, null);

    }
    private int getTileY(int row) {
        return SPACING + row * SQUARE_SIZE+ row * SPACING;
    }

    private void paintButton(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(MainPanel.BOARD_WIDTH+80, 400, 220,70);
        g.setFont(fontString);
        g.setColor(Color.white);
        g.drawString("Continue", MainPanel.BOARD_WIDTH+120, 450);
    }
    private int getTileX(int col) {
        return SPACING + col * SQUARE_SIZE + col * SPACING;
    }

    private int getPoint(int x){
        return (int)((x-MainPanel.SPACING)/(MainPanel.SQUARE_SIZE+ MainPanel.SPACING));
    }



    public void lauchGame(){
        game = new Thread(this);
        game.start();
    }

    @Override
    public void run() {
        //game loop
        double drawInterval = 1000000000/FPS;
        double delta =0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (game!= null){
            currentTime = System.nanoTime();
            delta += (currentTime- lastTime)/drawInterval;
            lastTime= currentTime;

            if(delta>= -1){
                update();
                repaint();
                delta--;
            }
        }


    }

    public Board getBoard() {
        return board;
    }
}
