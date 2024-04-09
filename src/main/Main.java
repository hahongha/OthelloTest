package main;

import view.MainPanel;
import view.startScreen;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static int WIDTH= 1100;
    public static int HEIGHT= 750;
    private MainPanel game;
    private startScreen start;
    private  JFrame window;


    public Main(){
        window = new JFrame("Othello");
        window.setSize(1100,750);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);// chan khong cho thay doi kich thuoc
        window.setBackground(Color.GRAY);

        //add game
        //game = new MainPanel(this);
        start = new startScreen(this);
        window.add(start);
        //window.add(game);
        //window.pack();//chinh kich thuoc vua voi bang

        window.setLocationRelativeTo(null);// can giua
        window.setVisible(true);// hien thi

        //game.lauchGame();
    }

        public void movePanels(JPanel panel1, JPanel panel2) {
        window.remove(panel1);
        window.add(panel2, BorderLayout.CENTER);
        panel2.requestFocusInWindow();
        window.validate();
    }
    public static void main(String[] args) {
        new Main();
    }
}
