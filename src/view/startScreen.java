package view;

import main.Main;
import model.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import control.changePanel;

public class startScreen extends JPanel {
    private static final long serialVersionUID = 1L;
    public static String saveDataPath = System.getProperty("user.dir") +File.separator
            + "lib" + File.separator+"Pic"+File.separator;
    public JButton btnAI;
    public JButton btnHuman;

    public JButton btnMusic;

    public JButton btnRule;

    private ImageIcon imageIconOn;
    private ImageIcon imageIconOff;

    BufferedImage imgMusicOn = null;
    BufferedImage imgMusicOff = null;
    public boolean isMusic= true;
    
    private MainPanel mainPanel;
    private Main window;

    private changePanel change;

    private startScreen startScreen;
    public startScreen(Main jf){
        this.startScreen = this;
        this.window= jf;
        this.mainPanel = new MainPanel(jf);
        this.change = new changePanel(jf, mainPanel, startScreen);
        setBackground(Color.BLACK);
        setLayout(null);
        setSize(Main.WIDTH, Main.HEIGHT);
    }

    private void changeMusic(){
        if(isMusic){
            isMusic = false;
            btnMusic.setIcon(imageIconOn);
        }
        else{
            isMusic = true;
            btnMusic.setIcon(imageIconOff);
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage logo = null;
        BufferedImage imgHuman = null;
        BufferedImage imgAI = null;
        BufferedImage imgRule= null;
        try {
            logo = ImageIO.read(new File(saveDataPath+"/Title.png"));
            imgHuman = ImageIO.read(new File(saveDataPath+"/buttonVsHuman.png"));
            imgAI = ImageIO.read(new File(saveDataPath+"/buttonVsRobot.png"));
            imgMusicOn = ImageIO.read(new File(saveDataPath+"/MusicOn.png"));
            imgMusicOff = ImageIO.read(new File(saveDataPath+"/MusicOff.png"));
            imgRule = ImageIO.read(new File(saveDataPath+"/QuestionMark.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(logo, 0, 0,Main.WIDTH, Main.HEIGHT/2, null);

        btnHuman = new JButton(new ImageIcon(imgHuman));
        btnHuman.setText("Humans");
        btnHuman.setBounds(Main.WIDTH/3, Main.HEIGHT/2+50,Main.WIDTH/3,110);
        btnHuman.setBorderPainted(false);
        btnHuman.setFocusPainted(false);
        btnHuman.setContentAreaFilled(false);
        this.add(btnHuman);



        btnAI = new JButton(new ImageIcon(imgAI));
        btnAI.setBounds(Main.WIDTH/3, Main.HEIGHT-200,Main.WIDTH/3,110);
        btnAI.setBorderPainted(false);
        btnAI.setFocusPainted(false);
        btnAI.setContentAreaFilled(false);
        this.add(btnAI);

//        btnHuman.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                String button = e.getActionCommand();
//                JOptionPane.showMessageDialog(null, button);
//                //window.movePanels(startScreen, mainPanel);
//            }
//        });
        btnHuman.addActionListener(change);

        btnAI.setText("Robots");
        btnAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Button AI clicked");
            }
        });

        imageIconOn = new ImageIcon(imgMusicOn);
        imageIconOff = new ImageIcon(imgMusicOff);

        btnMusic = new JButton(imageIconOn);
        btnMusic.setBounds(30, 30,80,70);
        btnMusic.setBorderPainted(false);
        btnMusic.setFocusPainted(false);
        btnMusic.setContentAreaFilled(false);
        this.add(btnMusic);

        btnMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeMusic();
            }
        });

        btnRule = new JButton(new ImageIcon(imgRule));
        btnRule.setBounds(150, 30,70,70);
        btnRule.setBorderPainted(false);
        btnRule.setFocusPainted(false);
        btnRule.setContentAreaFilled(false);
        this.add(btnRule);
        btnRule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Button Rule clicked");
            }
        });

    }

    private JPanel getScreen(){
        return startScreen;
    }
}
