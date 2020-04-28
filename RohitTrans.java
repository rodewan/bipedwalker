//change class name
/*
Rohit Dewan
Biped Project
This project allows the user to move a model biped walking across the floor, 
switch the camera position, and speed up/slow down the animation. The camera
can also be zoomed in and out. The keys to rotate camera are shift and control,
zoom in and out are j and k, to change the direction of the walker is left and right (hold down),
and to speed up / slow down animation are t and r
*/
//import statements
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import com.sun.opengl.util.*;

import java.awt.*;
import java.awt.event.*;
public class RohitTrans extends Frame{
	public static void main(String args[ ]) {
      //declare a new instance of the class
      new RohitTrans();
    }//end of main
 //initialize global variables to initial values
   	 //stores camera rotation angle
	 public int degrees = -90;
	 //stores camera zoom value
	 public int zoom = 10;
	 //stores directional rotation value of figure
	 public double figrot = 0;
	 //stores increment for xposition, for speeding up/slowing down animation
	 public double inc = .01;
	 public RohitTrans(){
      //make the program visible
      setVisible(true);
      //set the window size
      setSize(300,300);
      //add window listener to close window if the "x" is clicked
   	  addWindowListener(new WindowAdapter()
      {
        //if the "x" is clicked in the upper left corner, close hte program
        public void windowClosing(WindowEvent e)
        {
            System.exit(0);
        }
      });//end of WindowListener
      //listen for any keys that the user may press
       KeyListener listener = new KeyListener() {
      public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //examine the keycode of the key pressed to take appropriate action
        switch (code) {
        	//if the key j is pressed increase the zoom value of the camera
        	case KeyEvent.VK_J:
			{
				zoom++;
        		break;
			}
			//if the key k is pressed decrease the zoom value of the camera
			case KeyEvent.VK_K:
			{
				zoom--;
				break;
			}
			//if control is pressed, rotate the camera in the positive direction
        	case KeyEvent.VK_CONTROL:
        	{
        		degrees++;
        		break;
        	}
        	//if shift is pressed, rotate the camera in the negative direction
        	case KeyEvent.VK_SHIFT:
        	{
        		degrees--;
				break;
        	}
        	//if left is pressed, the direction of the figure is rotated left
        	case KeyEvent.VK_LEFT:
        	{
        		figrot++;
        		break;
        	}
        	//if right is pressed, the direction of the figure is rotated right
        	case KeyEvent.VK_RIGHT:
        	{
        		figrot--;
        		break;
        	}
        	//if t is pressed, the animation is sped up by increments of .001
        	case KeyEvent.VK_T:
        	{
        		inc+=.001;
        		break;
        	}
        	//if r is pressed, the animation is slowed down by increments of .001
        	case KeyEvent.VK_R:
        	{
        		inc-=.001;
        		break;
        	}
      	}//end of switch statement
      }//end of method key Pressed
      public void keyReleased(KeyEvent e) {
      }//end of method keyReleased

      public void keyTyped(KeyEvent e) {
      }//end of method key Typed
    };//end of KeyListener
      GLCapabilities glCaps = new GLCapabilities();
		//create a jogl canvas
		GLCanvas glCanvas = new GLCanvas(glCaps);
		glCanvas.setSize(300,300);
		//add the canvas to the awt apnel
		add(glCanvas);
		//set the canvas to be visible
		glCanvas.setVisible(true);
		//add TransGLEventListener
		glCanvas.addGLEventListener(new TransGLEventListener());
		//add KeyListener
		glCanvas.addKeyListener(listener);
		//add animator for realtime updating
		Animator animateMe = new Animator(glCanvas);
		//start animator
		animateMe.start();
		
   }//end of constructor TrivialApplication
//this class conducts the main opengl operations based on user input through keypresses
public class TransGLEventListener implements GLEventListener {
	//in the init method the gl, glu objects are initialized, the perspective is defined, and the matrixmode is changed to modelview
	//holds positions of 5 periods of walking
	double pos = 0.0;
	//holds scaled translated positions according to the variable pos
	double xpos = 0.0;
	//holds the "time", a variable that is incremented every time display is called, it depends on the computers speed, but can be 
	//adjusted accordingly to suit the situation with the increment keys for the animation
	float time = 0.0f;
	//gl and glu objects are obtained and the perspective is defined in the init method
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, 1.0f, 1.0f, 15.0f);
		gl.glMatrixMode(GL.GL_MODELVIEW);
	}//end of init
	
	// This function is called repeatedly by the Animator. The operations chosen by the user are carried out
	public void display(GLAutoDrawable gld) {
		//initialize hip, knee, and ankleangle's for both legs
		double hipangle=0.0;
		double hipangle2=0.0;
		double kneangle=0.0;
		double kneangle2=0.0;
		double ankangle=0.0;
		double ankangle2 = 0.0;
		//define the hip angle's for both legs
		if ((time>=0.0 && time <=5.0)|| (time>=10.0 && time <=15.0)||(time>=20.0 && time <=25.0)){
			hipangle = Math.sin(Math.PI*2*time/10.0)*28.0;
			hipangle2 = Math.sin(Math.PI*2*(time+5.0)/10.0)*15.0;
		}
		else if ((time>=5.0 && time<=10.0)||(time>=15.0 && time<=20.0)||(time>=25.0 && time<=30.0)){
			hipangle = Math.sin(Math.PI*2*time/10.0)*15.0;
			hipangle2 = Math.sin(Math.PI*2*(time+5.0)/10.0)*28.0;
		}//end of if statement
		//define the ankle angle's for both legs
		if ((time >=0.0 && time <=4.0)||(time >=10.0 && time <=12.5)||(time >=20.0 && time <=22.5))
			ankangle = Math.sin(Math.PI*2*(time-4.0)/8.0)*35.0;
		else if ((time>=4.0 && time<=5.0)||(time>=12.5 && time<=15.0)||(time>=22.5 && time<=25.0))
			ankangle = 0.0;
		else if ((time>=5.0 && time <=9.0)||(time>=15.0 && time <=17.5)||(time>=25.0 && time <=27.5))
			ankangle2 = Math.sin(Math.PI*2*(time-9.0)/8.0)*35.0;
		else if ((time>=9.0 && time<=10.0)||(time>=17.5 && time<=20.0)||(time>=27.5 && time<=30.0))
			ankangle2 = 0;
		//end of if statment
		if (time>=0.0&&time<=5.0)
			ankangle2 = Math.sin(Math.PI*2*time/4.0*15);
		else if (time>=5.0&&time<=10.0)
			ankangle = Math.sin(Math.PI*2*(time-5.0)/4.0*15);
		//end of if statement
		//define knee angle's for both legs
		if ((time>=0.0 && time <=4.0)||(time >=10.0 && time <=13.0)||(time >=20.0 && time <=23.0)){
			kneangle2 = -Math.sin(Math.PI*2*(time-4.0)/8.0)*25.0;
		}
		else if ((time>=4.0 && time <=6.0)||(time >=13.0 && time <=17.0)||(time >=23.0 && time <=27.0)){
			kneangle2 =0;
		}
		else if ((time>=6.0 && time <=10.0)||(time >=17.0 && time <=20.0)||(time >=27.0 && time <=30.0)){
			kneangle2 = -Math.sin(Math.PI*2*(time-10.0)/8.0)*15.0;
		}
		//end of if statement
		if ((time>=0.0 && time <=1.0)||(time >=10.0 && time <=12)||(time >=20.0 && time <=22)){
			kneangle = 0;
		}
		else if ((time>=1.0 && time <=5.0)||(time >=12 && time <=15.0)||(time >=22 && time <=25.0)){
			kneangle = -Math.sin(Math.PI*2*((time-2.5)-2.5)/8.0)*15.0;
		}
		else if ((time>=5.0 && time <=9.0)||(time >=15.0 && time <=18.0)||(time >=25.0 && time <=28)){
			kneangle = -Math.sin(Math.PI*2*((time)-9.0)/8.0)*25.0;
		}
		else if ((time>=9.0 && time <=10.0)||(time >=18.0 && time <=20.0)||(time >=28.0 && time <=30.0)){
			kneangle = 0;
		}//end of if statement
		//obtain gl, glu, and glut variables for use in drawing
		GL gl = gld.getGL();
		GLU glu = new GLU();
		GLUT glut = new GLUT();
		gl.glMatrixMode(GL.GL_MODELVIEW);
		//the Default matrix is loaded
		gl.glLoadIdentity();
		//make the floor color red
		gl.glColor3f(1.0f,0.0f,0.0f);
		//the camera view is defined according to the position, which can be changed by the user
		glu.gluLookAt(zoom*Math.cos(-degrees*Math.PI/180.0),0.0,zoom*Math.sin(-degrees*Math.PI/180.0),0.0,0.0,0.0,0.0,1.0,0.0);
		//clear the screen
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		//enable depth buffer
		gl.glEnable(GL.GL_DEPTH_TEST);
		//define array for floor vertices
		float floor[][] = {{-5.0f,-1.15f,5.0f},{-5.0f,-1.15f,-5.0f},{5.0f,-1.15f,-5.0f},{5.0f,-1.15f,5.0f}};
		//draw the floor
		gl.glBegin(GL.GL_POLYGON);
					gl.glVertex3fv(floor[0],0);
			gl.glVertex3fv(floor[1],0);
			gl.glVertex3fv(floor[2],0);
			gl.glVertex3fv(floor[3],0);
		gl.glEnd();
		//change the color to green for drawing the figure
		gl.glColor3f(0.0f,1.0f,0.0f);
		//push a new matrix to draw the right leg
		gl.glPushMatrix();
		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity();
		//make camera angle the same as for the other matrix
		glu.gluLookAt(zoom*Math.cos(-degrees*Math.PI/180.0),0.0,zoom*Math.sin(-degrees*Math.PI/180.0),0,0,0,0,1,0);
	 	//following code rotates the axes as per the user's input for the direction of the figure, then draws the 
		//right leg, hte sphere in the middle of the two legs, and hte rest of the upper body of the figure
		gl.glRotatef((float)figrot, 0.0f,1.0f,0.0f);
	 	gl.glTranslatef((float)xpos,1.0f,0f);
	 	gl.glColor3f(0.0f,0.0f,1.0f);
	 	glu.gluSphere(glu.gluNewQuadric(),.5f,32,32);
	 	gl.glColor3f(0.0f,1.0f,0.0f);
	 	gl.glTranslatef(0.0f,0.5f,0.0f);
	 	gl.glRotatef(-90,1.0f,.0f,0.0f);
		glu.gluCylinder(glu.gluNewQuadric(), .1, .1, 1.0, 25, 25);
	 	gl.glRotatef(90,1.0f,.0f,0.0f);
	 	gl.glTranslatef(0.0f,0.5f,0.0f);
		glu.gluCylinder(glu.gluNewQuadric(), .1, .1, 1.0, 25, 25);
	 	gl.glRotatef(180,0.0f,1.0f,0.0f);
		glu.gluCylinder(glu.gluNewQuadric(), .1, .1, 1.0, 25, 25);
	 	gl.glRotatef(-180,0.0f,1.0f,0.0f);
		gl.glTranslatef(0.0f,0.4f,0.0f);
	 	glu.gluSphere(glu.gluNewQuadric(),.2f,32,32);
	 	gl.glTranslatef(0.0f,-1.25f,0.0f);
	 	gl.glTranslatef(0.0f,0.0f,0.5f);
	 	gl.glRotatef(-270, 1.0f,0.0f,0.0f);
	 	gl.glRotatef((float)hipangle2, 0.0f,1.0f,0.0f);
	 	glu.gluCylinder(glu.gluNewQuadric(), .1, .1, 1.0, 25, 25);
		gl.glTranslatef(0.0f,0.0f,1.0f);
		gl.glRotatef(-(float)kneangle, 0.0f,1.0f,0.0f);
	 	glu.gluCylinder(glu.gluNewQuadric(), .1, .1, 1.0, 25, 25);
		gl.glTranslatef(0.0f,0.0f,1.0f);
		gl.glRotatef(-90, 1.0f,0.0f,0.0f);
		gl.glRotatef(90, 0.0f,1.0f,0.0f);
	 	gl.glRotatef((float)ankangle2, 1.0f,0.0f,0.0f);
 	 	glu.gluCylinder(glu.gluNewQuadric(), .1, .1, .25, 25, 25);
 		//the matrix for drawing the right leg and the top part of the figure is popped
 	 	gl.glPopMatrix();
 	 	//a new matrix is pushed for drawing the left leg
 	 	gl.glPushMatrix();
 	 	gl.glMatrixMode(gl.GL_MODELVIEW);
 	 	gl.glLoadIdentity();
 	 	//make the camera angle the same as for hte starting matrix
	 	glu.gluLookAt(zoom*Math.cos(-degrees*Math.PI/180.0),0.0,zoom*Math.sin(-degrees*Math.PI/180.0),0,0,0,0,1,0);
	 	//following code rotates the axes as per the user's input for the direction of the figure, then draws the left leg
	 	gl.glRotatef((float)figrot, 0.0f,1.0f,0.0f);
	 	gl.glTranslatef((float)xpos,1.0f,-.5f);
	 	gl.glRotatef(-270, 1.0f,0.0f,0.0f);
	 	gl.glRotatef((float)hipangle, 0.0f,1.0f,0.0f);
	 	glu.gluCylinder(glu.gluNewQuadric(), .1, .1, 1.0, 25, 25);
	 	gl.glTranslatef(0.0f,0.0f,1.0f);
	 	gl.glRotatef(-(float)kneangle2, 0.0f,1.0f,0.0f);
	 	glu.gluCylinder(glu.gluNewQuadric(), .1, .1, 1.0, 25, 25);
		gl.glTranslatef(0.0f,0.0f,1.0f);
		gl.glRotatef(-90, 1.0f,0.0f,0.0f);
		gl.glRotatef(90, 0.0f,1.0f,0.0f);
	 	gl.glRotatef((float)ankangle, 1.0f,0.0f,0.0f);
	 	glu.gluCylinder(glu.gluNewQuadric(), .1, .1, .25, 25, 25);
	 	
	 	gl.glPopMatrix();
	 	//increment the time and pos variables by inc
		time+=(float)inc;
		pos +=(float)inc;
		//scale xpos from pos
		xpos = (1.0/5.0)*pos-5.0;
		//time and pos are cyclic, with pos containing five periods of time
		if (time>10.0f)
			time = 0.0f;
		if (pos>50.0f)
			pos = 0.0f;	
			
	}//end of display method
		// This function is only called when the window is reshaped. You probably won't need this. 
	public void reshape(GLDrawable drawable, int x, int y, int width, int height) {}
	
	// This function is only called when the display settings are changed. Again, not often used.
	public void displayChanged(GLDrawable drawable, boolean a, boolean b) {}


	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	

	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		// TODO Auto-generated method stub
		
	}
}//end of TransGLEventListener
}//end of RohitTrans



  
