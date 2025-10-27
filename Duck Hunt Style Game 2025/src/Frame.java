import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	//frame size
	private int screenWidth = 1000, screenHeight = 780; //resizing the frame, match background shape
	private String title = "Duck Hunt"; //can change later
	
	private int count = 3;
	private int track = 0;
	private int level = 1;
	/**
	 * Declare and instantiate (create) your objects here
	 */
	public Duck duckObject = new Duck();
	private house myHouse = new house();
	private Background myBackground = new Background();
	private Bush myBush = new Bush();
	private Carrot carrotObject = new Carrot();
	private Carrot2 carrot2 = new Carrot2();
	private basket myBasket = new basket();
	private shot3 myShot = new shot3();
	private MyCursor cursor = new MyCursor();
	private Tracker tracker = new Tracker();
	private badCarrot mybadCarrot = new badCarrot();
	
	//Music
	private Music crunchSound = new Music("crunch.wav", false);
	private Music Theme = new Music("mainTheme.wav", false);
	
	private int totalScore = 0; //need to increment when collision happens
	private int time = 30;
	
	
	public void paint(Graphics pen) {
		
		//this line of code is to force redraw the entire frame
		super.paintComponent(pen);
		//background should be draw before the objects 
		//or based on how you want to LAYER.
		myBackground.paint(pen);
		carrotObject.paint(pen);
		
		
		//call paint for the object
		//for objects, you call methods on them using the dot operator
		//methods use always involve parenthesis
		duckObject.paint(pen);
		myHouse.paint(pen);
		myBasket.paint(pen);
		carrot2.paint(pen);
		if(level >= 2) {
			mybadCarrot.paint(pen);
		}
		myShot.paint(pen);
		tracker.paint(pen);
		cursor.paint(pen);
		myBush.paint(pen);
		
		Font f = new Font("Sego UI", Font.PLAIN, 38);
		pen.setFont(f);
		pen.setColor(Color.black); //change based on background color
		pen.drawString("Score: "+ totalScore, 750, 50); //totalScore is a variable at the top
		//pen.drawString("" + time, 310, 510);
		pen.drawString("Level: "+level, 10,50);
		
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent mouse) {
	    // Runs when the mouse is clicked (pressed and released quickly).
	    // Example: You could use this to open a menu or select an object.
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
	    // Runs when the mouse enters the area of a component (like a button).
	    // Example: You could highlight the button when the mouse hovers over it.
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
	    // Runs when the mouse leaves the area of a component.
	    // Example: You could remove the highlight when the mouse moves away.
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
	    // Runs when a mouse button is pressed down.
	    // Example: You could start dragging an object here.
		//System.out.println(mouse.getX() + ":" + mouse.getY());
		
		
		boolean collision = carrotObject.checkCollision(mouse.getX(), mouse.getY());
		boolean collision2 = carrot2.checkCollision(mouse.getX(),mouse.getY());
		boolean badCollision = mybadCarrot.checkCollision(mouse.getX(), mouse.getY());
		if(collision == false && collision2 == false ||badCollision == true){
			count--;
			totalScore--;
			if(count == 2) {
				myShot.changePicture("shot2.png");
			}
			else if (count == 1){
				myShot.changePicture("1shot.png");
			}
			else {
				myShot.changePicture("gameover.png");
				myBush.changePicture("gameoverbg.png");
				myBush.setScale(1,1);
				myBush.setLocation(0, 0);
				
			}
			
		}
		
//		if(badCollision == true) {
//			totalScore--;
//		}
//		
		if(collision == true || collision2 == true) {
			track++;
			totalScore++;
			//make the music file play each time
			this.crunchSound.play();
			if(track == 1 && count > 0) {
				tracker.changePicture("onecarrot.png");
			}
			else if(track ==2 && count > 0) {
				tracker.changePicture("twocarrot.png");
			}
			else if(track == 3 && count > 0) {
				tracker.changePicture("threecarrot.png");
			}
			else if(track == 4 && count > 0) {
				tracker.changePicture("fourcarrot.png");
			}
			else if(track == 5 && count > 0) {
				tracker.changePicture("fivecarrot.png");
			}
			else if(track == 6 && count > 0) {
				tracker.changePicture("sixcarrot.png");
			}
			else if(track == 7 && count > 0) {
				tracker.changePicture("sevencarrot.png");
			}
			else if(track == 8 && count > 0) {
				tracker.changePicture("eightcarrot.png");
			}
			else if(track == 9 && count > 0) {
				tracker.changePicture("ninecarrot.png");
			}
			else if(track == 10 && count > 0) {
				tracker.changePicture("tencarrot.png");
				level++;
			}
			else {
				tracker.changePicture("nocarot.png");
			}


		}
		
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
	    // Runs when a mouse button is released.
	    // Example: You could stop dragging the object or drop it in place.
	}



	/*
	 * This method runs automatically when a key is pressed down
	 */
	public void keyPressed(KeyEvent key) {
		
		System.out.println("from keyPressed method:"+key.getKeyCode());
	
	}

	/*
	 * This method runs when a keyboard key is released from a pressed state
	 * aka when you stopped pressing it
	 */
	public void keyReleased(KeyEvent key) {
		
	}

	/**
	 * Runs when a keyboard key is pressed then released
	 */
	public void keyTyped(KeyEvent key) {
		
		
	}
	
	
	/**
	 * The Timer animation calls this method below which calls for a repaint of the JFrame.
	 * Allows for our animation since any changes to states/variables will be reflected
	 * on the screen if those variables are being used for any drawing on the screen.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
	
	/*
	 * Main method to create a Frame (the GUI that you see)
	 */
	public static void main(String[] arg) {
		new Frame();
	}
	
	
	
	public Frame() {
		this.Theme.play();
		JFrame f = new JFrame(title);
		f.setSize(new Dimension(screenWidth, screenHeight));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		
		/*cursor icon code*/
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image= toolkit.getImage("cursoragain.png");
		Cursor a = toolkit.createCustomCursor(image, new Point(this.getX(),this.getY()), "");
		this.setCursor(a);
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
