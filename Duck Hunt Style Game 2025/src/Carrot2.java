import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

// The Duck class represents a picture of a duck that can be drawn on the screen.
public class Carrot2 {
    // Instance variables (data that belongs to each Duck object)
    private Image img;               // Stores the picture of the duck
    private AffineTransform tx;      // Used to move (translate) and resize (scale) the image

    
    	private Image normal; //normal look
    	private Image chopped;
    	

    // Variables to control the size (scale) of the duck image
    private double scaleX;           
    private double scaleY;           

    // Variables to control the location (x and y position) of the duck
    private double x;                
    private double y;        
    
    //variables for speed
    private int vx;
    private int vy;
    
    public boolean debugging = true;
    private boolean hasFallen = false;

    // Constructor: runs when you make a new Duck object
    public Carrot2() {
        normal = getImage("/imgs/carrot.png"); // Load the image file
        chopped = getImage("/imgs/carrotbasket.png");
        
        //img will point to current state object for image
        img = normal;
        
        
        
        
        tx = AffineTransform.getTranslateInstance(0, 0); // Start with image at (0,0)
        
        // Default values
        scaleX = 3.0;
        scaleY = 3.0;
        x = 0;
        y = 0;
        
        vx = 2;
        vy = 5;

        init(x, y); // Set up the starting location and size
    }
    
    //2nd constructor to initialize location and scale!
    public Carrot2(int x, int y, int scaleX, int scaleY) {
    	this();
    	this.x 		= x;
    	this.y 		= y;
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	init(x,y);
    }
    
    //2nd constructor to initialize location and scale!
    public Carrot2(int x, int y, int scaleX, int scaleY, int vx, int vy) {
    	this();
    	this.x 		= x;
    	this.y 		= y;
    	this.scaleX = scaleX;
    	this.scaleY = scaleY;
    	this.vx 	= vx; 
    	this.vy 	= vy;
    	init(x,y);
    }
    
    public void setVelocityVariables(int vx, int vy) {
    	this.vx = vx;
    	this.vy = vy;
    }
    
    
    // Changes the picture to a new image file
    public void changePicture(String imageFileName) {
        img = getImage("/imgs/"+imageFileName);
        init(x, y); // keep same location when changing image
    }
    
    //update any variables for the object such as x, y, vx, vy
//    public void update() {
//    	x += vx;
//    	y += vy;
//    	
//    	if(x >= 700) {
//    		vx *= -1;
//    	}
//    	if(x <= 0) {
//    		vx *= -1;
//    	}
//
//    	if(y <= 0) {
//    		vy *= -1;
//    	}
//    	
//    	if(y >= 680 && vx == 0 && vy > 0) {
//    		if(y >= 680) {
//    			vy = -(int)(Math.random()*8+3);
//    			vx = (int)(Math.random()*8+3);
//    			
//    			
//    			//50% of the time, vx i negative
//    			if(Math.random()<0.5) {
//    				vx*= -1;
//    			}
//    			 hasFallen = false;
//    		}
//    	}
//    	
//    	//regular behavior - regular bouncing from the bottom
//    	if(y >= 500 && vx != 0) {
//    		vy *= -1;
//    	}
//    	
//    }
    
    public void update() {
        x += vx; //bounce off sides
        if(x>= 900 || x<=0) {
        	vx *= -1;
        }
        
        y += vy;
        
        if (y <= 0) {
            vy *= -1;
            y = 0;
        }
        
        if(hasFallen && vx == 0&& vy>0 && y >= 780) {
        	if(y>= 780) {
        		vy = -(int)(Math.random()*8+3); //random velocities
        		vx = -(int)(Math.random()*8+3);
        		
        		if(Math.random()<0.5) {
        			vx *= -1;
        			
        		}
        		
        		hasFallen = false;
        		System.out.println("Bounce BACK!");
        		img = normal;
        	}
        	
        }
        
        if(!hasFallen && y>= 780 && vy > 0 && vx != 0) {
        	vy *= -1;
        }
        //System.out.println("y = " + y + ", vy = " + vy);
        
        }
    
//
//        // Wall bounce (left/right)
//        if (x >= 700 || x <= 0) {
//            vx *= -1;
//        }
//
//        // Ceiling bounce
//        if (y <= 0) {
//            vy *= -1;
//        }
//
//        // FALLING MODE BOUNCE BACK
//        if (hasFallen && vx == 0 && vy > 0 && y >= 680) {
//            vy = -(int)(Math.random() * 8 + 3);
//            vx = (int)(Math.random() * 8 + 3);
//            if (Math.random() < 0.5) {
//                vx *= -1;
//            }
//
//            hasFallen = false; // Reset collision state
//            System.out.println("BOUNCE BACK UP!");
//        }
//
//        // NORMAL bouncing from the floor
//        if (!hasFallen && y >= 500 && vx != 0) {
//            vy *= -1;
//        }
//        System.out.println("y = " + y + ", vy = " + vy);
  

    
    
    
    
    // Draws the duck on the screen
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;   // Graphics2D lets us draw images
        g2.drawImage(img, tx, null);      // Actually draw the duck image
        update();
        init(x,y);
        
        //create a green hitbox
        if(debugging) {
        	g.setColor(Color.green);
        	g.drawRect((int)x, (int)y, 50, 50);
        }
    }
    
    // Setup method: places the duck at (a, b) and scales it
    private void init(double a, double b) {
        tx.setToTranslation(a, b);        // Move the image to position (a, b)
        tx.scale(scaleX, scaleY);         // Resize the image using the scale variables
    }

    // Loads an image from the given file path
    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = Carrot2.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }

    // NEW: Method to set scale
    public void setScale(double sx, double sy) {
        scaleX = sx;
        scaleY = sy;
        init(x, y);  // Keep current location
    }

    // NEW: Method to set location
    public void setLocation(double newX, double newY) {
        x = newX;
        y = newY;
        init(x, y);  // Keep current scale
    }
    
    //collision and collision logic
    public boolean checkCollision(int mX, int mY) {
    	
    	//represent the mouse as a rectangle
    	Rectangle mouse = new Rectangle (mX, mY, 10, 10);
    	
    	//Represent this object as a Rectangle
    	Rectangle thisObject = new Rectangle((int) x,(int) y, 100, 100);
    	
    	//use built-oin method for Rectangle to check if they interact
    	if(mouse.intersects(thisObject)&& !hasFallen){
    		//logic if colliding
    		
    		vx = 0; //turn off vx to fall from the sky
    		vy = 9; //all y - gravity
    		hasFallen = true;
    		img = chopped;
    		return true;
    		
    		
    		
    		
    	} 
    		return false;
    	}
}


