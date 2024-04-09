package control;

import main.Main;
import model.Board;
import model.BoardHelper;
import view.MainPanel;
import view.startScreen;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;

public class Mouse extends MouseAdapter {
    public int mx, my;
    public boolean pressed = false;

    public startScreen startScreen;

    public Board board;
    public MainPanel gamePanel;

    public Main main;

    public static int testClick =0;

    public Mouse(Board board1, MainPanel game, Main jf){
        main = jf;
        board= board1;
        gamePanel = game;
       // startScreen = new startScreen(main);
    }
    @Override
    public void mouseClicked(MouseEvent e) {//nhan tha

        mx= e.getX();
        my= e.getY();
        if(board.isGameOn()&& board.isGameContinue()){
            if(mx> MainPanel.BOARD_WIDTH+80 && mx<MainPanel.BOARD_WIDTH+80+220){
                //pause
                if(my>400&& my<470){
                    //JOptionPane.showMessageDialog(null, "Click pause");
                    if(board.isBlack||board.isWhite) {
                        board.isBlack = false;
                        board.isWhite = false;
                    }else{
                        if(!board.WhiteContinue) board.isBlack= true;
                        else board.isWhite= true;
                    }
                    board.setGameContinue(false);
                }
                //exit
                if(my>500&&my<570){
                    //gamePanel.addAncestorListener();
                    board.setGameOn(false);
                }
            }
        }
        else if (!board.isGameOn()) {
            if(mx>400&&mx<700){
                if (my>400&&my<500) {
                    startScreen = new startScreen(main);
                    main.movePanels(gamePanel, startScreen);
                }
            }
        } else if (!board.isGameContinue()) {
            if(mx>400&&mx<700){
                //exit
                if (my>520&&my<600) {
                    //JOptionPane.showMessageDialog(null, "Click exit");
                    startScreen = new startScreen(main);
                    main.movePanels(gamePanel, startScreen);
                }
                //replay
                if (my>390&&my<470) {
                    board.reset();
                    gamePanel.timeWhite.setStartTime(System.nanoTime());
                    gamePanel.timeBlack.setStartTime(System.nanoTime());
                }
                //resume
                if (my>260&&my<330) {
                    board.setGameContinue(true);
                }
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed= true;
        mx= e.getX();
        my= e.getY();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed= false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseMoved(MouseEvent e) {
        mx= e.getX();
        my= e.getY();
    }
    @Override
    public void mouseDragged(MouseEvent e) {//nhan keo
        mx= e.getX();
        my= e.getY();
    }
}
