package control;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.*;

import main.Main;
import view.MainPanel;

public class changePanel implements ActionListener {
	private Main jf;
	private MainPanel jpanel1;
	private JPanel jpanel2;

	public changePanel(Main jf,MainPanel jpanel1, JPanel jpanel2) {
		super();
		this.jf = jf;
		this.jpanel1 = jpanel1;
		this.jpanel2 = jpanel2;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String button = e.getActionCommand();
		if (button.equals("Humans")) {
			jf.movePanels(jpanel2, jpanel1);
			jpanel1.lauchGame();
		} else if (button.equals("Robots")) {
			jf.movePanels(jpanel2, jpanel1);
			jpanel1.lauchGame();
		}
	}
}
