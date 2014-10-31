import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import com.jameslow.*;
import com.jameslow.gui.*;

public class BigRedButtonWindow extends MainWindow implements MouseListener {
	private boolean runThread = false;
	private double counter = 10.0;
	private Date startTime = null;
	private JLabel counterLabel = null;
	private JTextField command = null;
	private NumberFormat formatter = new DecimalFormat("0.0");
	private Runnable mainLoop = new Runnable() {
		public void run() {
			startTime = new Date(); 
			while (runThread && counter > 0) {
				Date currentTime = new Date();
				counter = 10.0 - (currentTime.getTime() - startTime.getTime())/1000.0;
				counter = (counter > 0 ? counter : 0);
				System.out.println(formatter.format(counter));
				counterLabel.setText(formatter.format(counter));
				try {
					Thread.yield();
					Thread.sleep(50);
				} catch (Exception e){
					
				}
			}
			runThread = false;
			//We weren't interupted, run the thread
			if (counter <= 0) {
				String[] args = new String[1];
				args[0] = command.getText();
				Main.OS().executeProcess(args);
			}
		}
	};
	private Thread countDown = null;
	
	public BigRedButtonWindow() {
		super();
		getContentPane().setLayout(null);
		counterLabel = new JLabel(formatter.format(counter));
			counterLabel.setFont(new Font("Arial",Font.BOLD,64));
			counterLabel.setHorizontalAlignment(JLabel.CENTER);
			counterLabel.setBounds(100,30, 128, 50);
		add(counterLabel);
		
		try {
			ImagePane button = new ImagePane((BufferedImage) Main.AboutImage().getImage());
				button.setBounds(100, 100, 128, 128);
				button.addMouseListener(this);
			add(button);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		command = new JTextField();
			command.setBounds(14, 250, 300, 20);
		add(command);
		//this.getDefaultWindowSettings()
	}

	public void mouseClicked(MouseEvent arg0) {
		if (runThread) {
			//Stop previous thread
			runThread = false;
		} else {
			//Recreate new thread and initiate variables
			countDown = new Thread(mainLoop);
			countDown.setPriority(Thread.MIN_PRIORITY);
			counter = 10.0;
			runThread = true;
			countDown.start();
		}
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}