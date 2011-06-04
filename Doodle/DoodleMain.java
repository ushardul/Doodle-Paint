/*<applet code=DoodleMain.class width=603 height=507> 
</applet> 
*/ 
/*                      Class: ICS 3M1
			Made by: Shardul Upadhyay
			Teacher: Ms.Karasinska
			Due: January 19th, 2009
			The Doodle Program

			The Doodle program allows the user to paint an image with a variety of tools and colors.
			The user can also create their own color for the program and save their creation to a drive.

			Variable dictionary (only for variables with scope of entire DoodleMain class):

			start_point - holds the starting point of various shapes
			end_point - holds the end point of various shapes
			point - holds objects from a class and used to draw shapes back at another time
			mouse_x - holds the x value of the mouse
			mouse_y - holds the y value of the mouse
			mousex_prev - holds the previous x value of the mouse
			mousey_prev - holds the previous y value of the mouse
			btn - holds the state of the buttons on the mouse
			width - holds the width of the applet as a constant
			height - holds the height of the applet as a constant
			drawing - used to see if user is still drawing the shape or it is finished
			finish - used to see if the shape object is finished
			current - holds the current color
			fill - holds the state of filling in shapes
			stage - holds what part of the program to run
			substage - stages inside the main stage of the program
			orange - creates the color orange
			red - holds the red value for the custom color
			green - holds the green value for the custom color
			blue - holds the blue value for the custom color
			alpha - holds the alpha value for the custom color
			custom_color - holds the custom color created
			col - holds the color value until it is convert to integer
			eraser_size - holds the eraser size
			dir - holds the directory of the file
			f - file for creating the image
			backbuffer - image for drawing back to program
			export - image for exporting
			backg - graphics object for drawing to screen
			t - thread object for running concurrent calculations
			pencil - holds the pencil button image when it is not selected
			pencil_selected - holds the pencil button image when it is selected
			line - holds the line button image when it is not selected
			line_selected - holds the line button image when it is selected
			rectangle - holds the rectangle button image when it is not selected
			rectangle_selected - holds the rectangle button image when it is selected
			oval - holds the oval button image when it is not selected
			oval_selected - holds the oval button image when it is selected
			color - holds the color creator button image
			clear - holds the clear button image
			eraser - holds the eraser button image when it is not selected
			eraser_selected - holds the eraser button image when it is selected
			fill_on - holds the fill button image when fill is on
			fill_off - holds the fill button image when fill is off
			pencilcursor_image - holds the pencil cursor image
			pencil_center - holds the point for the center of the image
			pencil_cursor - holds the pencil image as a cursor
			targetcursor_image - holds the target cursor image
			center - holds the point for the center of the image
			target_cursor - holds the target image as a cursor
			instructions - holds the instructions image
			smiley - holds the smiley image
			erasercursor_image - holds the eraser cursor image
			eraser_cursor - holds the eraser image as a cursor
*/

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class DoodleMain extends Applet implements MouseListener, MouseMotionListener, KeyListener, Runnable
{
    //Holds the start and end points of the various shapes
    public Point start_point = new Point (-1, -1);
    public Point end_point = new Point (-1, -1);
    //Holds objects in a vector that can be resized
    public Vector point = new Vector ();
    //Holds mouse co-ordinates and button states
    public int mouse_x, mouse_y, mouse_prevx, mouse_prevy;
    public boolean btn = false;
    //Holds width and height
    final int width = 603, height = 507;
    //Holds the current tool that is selected
    public int tool = 0;
    //For checking if user is currently drawing
    public boolean drawing = false;
    //For checking if current shape is finished
    public boolean finish = false;
    //Holds current color
    public Color current = Color.black;
    //Holds the indication to fill the object or not
    public boolean fill = false;
    //What stage the program is in
    public int stage = 0;
    public int substage = 0;
    //Creates the color orange
    public Color orange = new Color (255, 128, 0);
    //Variables for creating and holding custom color
    public int red = 255, green = 255, blue = 255, alpha = 200;
    public Color custom_color = new Color (red, green, blue, alpha);
    public String col = "";
    //Size of eraser
    public int eraser_size = 11;
    //Holds the path of the directory
    public String dir = "";
    public boolean change = false;
    File f;
    //For drawing to screen
    public Image backbuffer;
    public BufferedImage export;
    public Graphics backg;
    //Creates thread
    public Thread t;
    //Images for the buttons
    public Image pencil = Toolkit.getDefaultToolkit ().getImage ("doodle_pencil.gif");
    public Image pencil_selected = Toolkit.getDefaultToolkit ().getImage ("doodle_pencil_selected.gif");
    public Image line = Toolkit.getDefaultToolkit ().getImage ("doodle_line.gif");
    public Image line_selected = Toolkit.getDefaultToolkit ().getImage ("doodle_line_selected.gif");
    public Image rectangle = Toolkit.getDefaultToolkit ().getImage ("doodle_rectangle.gif");
    public Image rectangle_selected = Toolkit.getDefaultToolkit ().getImage ("doodle_rectangle_selected.gif");
    public Image oval = Toolkit.getDefaultToolkit ().getImage ("doodle_oval.gif");
    public Image oval_selected = Toolkit.getDefaultToolkit ().getImage ("doodle_oval_selected.gif");
    public Image color = Toolkit.getDefaultToolkit ().getImage ("doodle_color.gif");
    public Image clear = Toolkit.getDefaultToolkit ().getImage ("doodle_clear.gif");
    public Image eraser = Toolkit.getDefaultToolkit ().getImage ("doodle_eraser.gif");
    public Image eraser_selected = Toolkit.getDefaultToolkit ().getImage ("doodle_eraser_selected.gif");
    public Image fill_on = Toolkit.getDefaultToolkit ().getImage ("doodle_fillon.gif");
    public Image fill_off = Toolkit.getDefaultToolkit ().getImage ("doodle_filloff.gif");
    //Images for cursors
    public Image pencilcursor_image = Toolkit.getDefaultToolkit ().getImage ("doodle_cursor_pencil.gif");
    public Point pencil_center = new Point (0, 31);
    public Cursor pencil_cursor = Toolkit.getDefaultToolkit ().createCustomCursor (pencilcursor_image, pencil_center, "Cursor");
    public Image targetcursor_image = Toolkit.getDefaultToolkit ().getImage ("doodle_cursor_target.gif");
    public Point center = new Point (16, 16);
    public Cursor target_cursor = Toolkit.getDefaultToolkit ().createCustomCursor (targetcursor_image, center, "Cursor target");
    public Image instructions = Toolkit.getDefaultToolkit ().getImage ("doodle_instructions.gif");
    public Image erasercursor_image = Toolkit.getDefaultToolkit ().getImage ("doodle_cursor_eraser.gif");
    public Cursor eraser_cursor = Toolkit.getDefaultToolkit ().createCustomCursor (erasercursor_image, center, "Cursor eraser");
    public Image smiley = Toolkit.getDefaultToolkit ().getImage ("Smiley.gif");

    public void init ()
    {
	   //Registers MouseListener, MouseMotionListener, and KeyListener to receive input in 'this' interface
	   addMouseListener (this);
	   addMouseMotionListener (this);
	   addKeyListener (this);
	   //Changes size of applet
	   setSize (width, height);
	   //Starts the thread
	   t = new Thread (this);
	   t.start ();
	   //Creates image for exporting and drawing graphics
	   backbuffer = createImage (width, height);
	   export = new BufferedImage (500, 500, BufferedImage.TYPE_INT_RGB);
	   backg = backbuffer.getGraphics ();
    }


    public void run ()
    {
	   try
	   {
		  do
		  {
			 //Stage for instructions
			 while (stage == 0)
			 {
				t.sleep (33);
				repaint ();
			 }
			 //Stage for drawing
			 while (stage == 1)
			 {
				//Sets cursor to pencil
				if (tool == 0)
				{
				    setCursor (pencil_cursor);
				}
				//Sets cursor to target
				else if (tool == 1 || tool == 2 || tool == 3 || tool == 4)
				{
				    setCursor (target_cursor);
				}
				//Sets tool to pencil
				if (MouseInside (width - 85, 11, 34, 34) == true && btn == true && drawing == false)
				{
				    tool = 0;
				    start_point.x = 0;
				    start_point.y = 0;
				    end_point.x = 0;
				    end_point.y = 0;
				}
				//Sets tool to line
				else if (MouseInside (width - 45, 11, 34, 34) == true && btn == true && drawing == false)
				{
				    tool = 3;
				    start_point.x = 0;
				    start_point.y = 0;
				    end_point.x = 0;
				    end_point.y = 0;
				}
				//Sets tool to rectangle
				else if (MouseInside (width - 85, 53, 34, 34) == true && btn == true && drawing == false)
				{
				    tool = 1;
				    start_point.x = 0;
				    start_point.y = 0;
				    end_point.x = 0;
				    end_point.y = 0;
				}
				//Sets tool to oval
				else if (MouseInside (width - 45, 53, 34, 34) == true && btn == true && drawing == false)
				{
				    tool = 2;
				    start_point.x = 0;
				    start_point.y = 0;
				    end_point.x = 0;
				    end_point.y = 0;
				}
				//Sets tool to eraser
				else if (MouseInside (width - 85, 94, 34, 34) == true && btn == true && drawing == false)
				{
				    tool = 4;
				    start_point.x = 0;
				    start_point.y = 0;
				    end_point.x = 0;
				    end_point.y = 0;
				}
				//Sets color to black
				if (MouseInside (width - 85, 155, 34, 34) == true && btn == true && drawing == false)
				{
				    current = Color.black;
				}
				//Sets color to white
				else if (MouseInside (width - 45, 155, 34, 34) == true && btn == true && drawing == false)
				{
				    current = Color.white;
				}
				//Sets color to gray
				else if (MouseInside (width - 85, 196, 34, 34) == true && btn == true && drawing == false)
				{
				    current = Color.gray;
				}
				//Sets color to light gray
				else if (MouseInside (width - 45, 196, 34, 34) == true && btn == true && drawing == false)
				{
				    current = Color.lightGray;
				}
				//Sets color to red
				else if (MouseInside (width - 85, 237, 34, 34) == true && btn == true && drawing == false)
				{
				    current = Color.red;
				}
				//Sets color to yellow
				else if (MouseInside (width - 45, 237, 34, 34) == true && btn == true && drawing == false)
				{
				    current = Color.yellow;
				}
				//Sets color to cyan
				else if (MouseInside (width - 85, 278, 34, 34) == true && btn == true && drawing == false)
				{
				    current = Color.cyan;
				}
				//Sets color to blue
				else if (MouseInside (width - 45, 278, 34, 34) == true && btn == true && drawing == false)
				{
				    current = Color.blue;
				}
				//Sets color to orange
				else if (MouseInside (width - 85, 319, 34, 34) == true && btn == true && drawing == false)
				{
				    current = orange;
				}
				//Sets color to green
				else if (MouseInside (width - 45, 319, 34, 34) == true && btn == true && drawing == false)
				{
				    current = Color.green;
				}
				//Sets color to custom color created
				else if (MouseInside (width - 85, 360, 74, 34) == true && btn == true && drawing == false)
				{
				    current = custom_color;
				}
				//Takes user to custom color creator
				if (MouseInside (width - 85, 401, 74, 34) == true && btn == true && drawing == false)
				{
				    stage = 2;
				}
				//Clears the screen if clicked
				if (MouseInside (width - 45, 94, 34, 34) == true && btn == true && drawing == false)
				{
				    point = new Vector ();
				    start_point = new Point ();
				    end_point = new Point ();
				}
				//Holds execution of thread and calls paint method
				t.sleep (33);
				repaint ();
			 }
			 //Stage for custom color creator
			 while (stage == 2)
			 {
				mouse_x = 0;
				mouse_y = 0;
				t.sleep (33);
				repaint ();
			 }
			 //Stage for saving file
			 while (stage == 3)
			 {
				mouse_x = 0;
				mouse_y = 0;
				t.sleep (33);
				repaint ();
			 }
			 repaint ();
		  }
		  while (stage != 4);
	   }
	   catch (InterruptedException e)
	   {
	   }
    }


    /*      Variable Dictionary (for variables with scope only of keyTyped method):
	    c - holds the key that is pressed on the keyboard
    */

    public void keyTyped (KeyEvent e)
    {
	   //Key input events in custom color creator
	   if (stage == 2)
	   {
		  //For color creator choosing red
		  if (substage == 0)
		  {
			 char c = e.getKeyChar ();
			 //Gets input while doing checks
			 if (c != KeyEvent.CHAR_UNDEFINED && col.length () < 3 && Character.isDigit (c) == true && c != e.VK_BACK_SPACE && c != e.VK_ENTER)
			 {
				col = col + c;
			 }
			 //Allows deletion of string entered
			 if (c == e.VK_BACK_SPACE && col.length () > 0)
			 {
				col = col.substring (0, col.length () - 1);
			 }
			 //Lets them enter the final value
			 if (c == e.VK_ENTER && !col.equals (""))
			 {
				//Converts it to red number
				red = Integer.parseInt (col);
				substage = 1;
				col = "";
				//Checks if it is outside 0 - 255
				if (red > 255)
				{
				    red = 255;
				}
				else if (red < 0)
				{
				    red = 0;
				}
				return;
			 }
		  }
		  else if (substage == 1)
		  {
			 char c = e.getKeyChar ();
			 //Gets input while doing checks
			 if (c != KeyEvent.CHAR_UNDEFINED && col.length () < 3 && Character.isDigit (c) == true && c != e.VK_BACK_SPACE && c != e.VK_ENTER)
			 {
				col = col + c;
			 }
			 //Allows deletion of string entered
			 if (c == e.VK_BACK_SPACE && col.length () > 0)
			 {
				col = col.substring (0, col.length () - 1);
			 }
			 //Lets them enter the final value
			 if (c == e.VK_ENTER && !col.equals (""))
			 {
				//Converts it to green number
				green = Integer.parseInt (col);
				substage = 2;
				col = "";
				//Checks if it is outside 0 - 255
				if (green > 255)
				{
				    green = 255;
				}
				else if (green < 0)
				{
				    green = 0;
				}
				return;
			 }
		  }
		  else if (substage == 2)
		  {
			 char c = e.getKeyChar ();
			 //Gets input while doing checks
			 if (c != KeyEvent.CHAR_UNDEFINED && col.length () < 3 && Character.isDigit (c) == true && c != e.VK_BACK_SPACE && c != e.VK_ENTER)
			 {
				col = col + c;
			 }
			 //Allows deletion of string entered
			 if (c == e.VK_BACK_SPACE && col.length () > 0)
			 {
				col = col.substring (0, col.length () - 1);
			 }
			 //Lets them enter the final value
			 if (c == e.VK_ENTER && !col.equals (""))
			 {
				//Converts it to blue number
				blue = Integer.parseInt (col);
				substage = 3;
				col = "";
				//Checks if it is outside 0 - 255
				if (blue > 255)
				{
				    blue = 255;
				}
				else if (blue < 0)
				{
				    blue = 0;
				}
				return;
			 }
		  }
		  else if (substage == 3)
		  {
			 char c = e.getKeyChar ();
			 //Gets input while doing checks
			 if (c != KeyEvent.CHAR_UNDEFINED && col.length () < 3 && Character.isDigit (c) == true && c != e.VK_BACK_SPACE && c != e.VK_ENTER)
			 {
				col = col + c;
			 }
			 //Allows deletion of string entered
			 if (c == e.VK_BACK_SPACE && col.length () > 0)
			 {
				col = col.substring (0, col.length () - 1);
			 }
			 //Lets them enter the final value
			 if (c == e.VK_ENTER && !col.equals (""))
			 {
				//Converts it to alpha number
				alpha = Integer.parseInt (col);
				substage = 0;
				//Creates new color
				stage = 1;
				col = "";
				//Checks if it is outside 0 - 255
				if (alpha > 255)
				{
				    alpha = 255;
				}
				else if (alpha < 0)
				{
				    alpha = 0;
				}
				custom_color = new Color (red, green, blue, alpha);
				return;
			 }
		  }
	   }
	   //Checks to see if save has been pressed
	   else if (stage == 3)
	   {
		  if (substage == 0)
		  {
			 char c = e.getKeyChar ();
			 //Gets key input and checks to make sure no illegal characters have been entered
			 if (c != e.VK_ENTER && dir.length () < 60 && c != e.VK_BACK_SPACE && (Character.isLetterOrDigit (c) || c == '.' || c == ' ' || c == '/' || c == ':' || c == '\\') && c != e.CHAR_UNDEFINED)
			 {
				dir += c;
			 }
			 //Allows deletion of entered string
			 else if (c == e.VK_BACK_SPACE && dir.length () > 0)
			 {
				dir = dir.substring (0, dir.length () - 1);
			 }
		  }
		  if (change == true)
		  {
			 change = false;
			 dir = "";
		  }
	   }
    }


    public void keyPressed (KeyEvent e)
    {
	   //Checks if s has been pressed and goes to saving prompt
	   if (e.getKeyCode () == e.VK_S && stage == 1)
	   {
		  stage = 3;
		  substage = 0;
		  change = true;
	   }
	   //Checks to see if q has been pressed and quits the program
	   if (e.getKeyCode () == e.VK_Q && stage == 1)
	   {
		  stage = 4;
		  resize (552, 500);
	   }
	   //Waits until enter is pressed and returns user to paint program
	   if (stage == 3 && substage == 2 && e.getKeyCode () == e.VK_ENTER)
	   {
		  stage = 1;
		  substage = 0;
	   }
    }


    public void keyReleased (KeyEvent e)
    {
	   //Starts painting program when a key is pressed
	   if (stage == 0)
	   {
		  stage = 1;
	   }
	   //Change eraser size
	   if (stage == 1 && e.getKeyCode () == 107 && eraser_size < 50)
	   {
		  eraser_size += 1;
	   }
	   if (stage == 1 && e.getKeyCode () == 109 && eraser_size > 6)
	   {
		  eraser_size -= 1;
	   }
	   //If it is saving prompt checks to see if enter has been pressed
	   if (e.getKeyCode () == e.VK_ENTER && substage == 0 && stage == 3)
	   {
		  //Changes path
		  if (dir.length () < 4)
		  {
			 dir += "image.png";
		  }
		  else if (!dir.endsWith (".png"))
		  {
			 dir += ".png";
		  }
		  //Goes to creating file
		  f = new File (dir);
		  //Creates directories if needed
		  f.mkdirs ();
		  substage = 1;
		  //Set backg to receive graphics again
		  backg = export.getGraphics ();
		  //Same as paint method vector loop
		  backg.setColor (Color.white);
		  backg.fillRect (-5, -5, 600, 600);
		  DoodleDrawShapes s;
		  for (int i = 0 ; i < point.size () ; i++)
		  {
			 s = (DoodleDrawShapes) point.elementAt (i);
			 if (s.shape.equals ("pencil"))
			 {
				backg.setColor (s.col);
				backg.drawLine (s.x1 - 3, s.y1 - 3, s.x2 - 3, s.y2 - 3);
			 }
			 if (s.shape.equals ("rectangle"))
			 {
				backg.setColor (s.col);
				if (s.fill == false)
				{
				    backg.drawRect (s.x1 - 3, s.y1 - 3, s.x2, s.y2);
				}
				else
				{
				    backg.fillRect (s.x1 - 3, s.y1 - 3, s.x2, s.y2);
				}
			 }
			 if (s.shape.equals ("oval"))
			 {
				backg.setColor (s.col);
				if (s.fill == false)
				{
				    backg.drawOval (s.x1 - 3, s.y1 - 3, s.x2, s.y2);
				}
				else
				{
				    backg.fillOval (s.x1 - 3, s.y1 - 3, s.x2, s.y2);
				}
			 }
			 if (s.shape.equals ("line"))
			 {
				backg.setColor (s.col);
				backg.drawLine (s.x1 - 3, s.y1 - 3, s.x2 - 3, s.y2 - 3);
			 }
			 if (s.shape.equals ("eraser"))
			 {
				backg.setColor (Color.white);
				backg.fillRect (s.x1 - 3, s.y1 - 3, s.x2, s.y2);
			 }
		  }
		  try
		  {
			 ImageIO.write (export, "png", f);
			 //Set stage to painting stage
			 stage = 1;
			 substage = 0;
			 backg = backbuffer.getGraphics ();
		  }
		  catch (Exception ex)
		  {
			 substage = 2;
		  }
	   }
    }


    public void mouseDragged (MouseEvent e)
    {
	   //If it is painting stage
	   if (stage == 1)
	   {
		  //Gets mouse coordinates
		  mouse_x = e.getX ();
		  mouse_y = e.getY ();
		  //If pencil tool is selected
		  if (tool == 0)
		  {
			 //If mouse is inside canvas, store line coordinates
			 if (mouse_x > 3 && mouse_x < 503 && mouse_y > 3 && mouse_y < 503)
			 {
				point.addElement (new DoodleDrawShapes (mouse_x, mouse_y, mouse_prevx, mouse_prevy, current, "pencil", fill));
			 }
			 //Stores mouse values as previous mouse values
			 mouse_prevx = mouse_x;
			 mouse_prevy = mouse_y;
			 drawing = true;
			 //Makes sure drawing is inside canvas
			 if (end_point.x < 3)
			 {
				end_point.x = 3;
			 }
			 if (end_point.x > 503)
			 {
				end_point.x = 503;
			 }
			 if (end_point.y < 3)
			 {
				end_point.y = 3;
			 }
			 if (end_point.y > 503)
			 {
				end_point.y = 503;
			 }
		  }
		  //If rectangle tool is selected and mouse is inside canvas
		  if (tool == 1 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //Save mouse point as end point
			 end_point.x = e.getX ();
			 end_point.y = e.getY ();
			 drawing = true;
		  }
		  //If oval tool is selected and mouse is inside canvas
		  if (tool == 2 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //Save mouse point as end point
			 end_point.x = e.getX ();
			 end_point.y = e.getY ();
			 drawing = true;
		  }
		  //If line tool is selected and mouse is inside canvas
		  if (tool == 3 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //Save mouse point as end point
			 end_point.x = e.getX ();
			 end_point.y = e.getY ();
			 drawing = true;
		  }
		  //If eraser tool is selected and mouse is inside canvas
		  if (tool == 4 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //Adds object with eraser specification into the vector
			 point.addElement (new DoodleDrawShapes (mouse_x - (eraser_size / 2), mouse_y - (eraser_size / 2), eraser_size, eraser_size, Color.white, "eraser", fill));
		  }
	   }
    }


    public void mousePressed (MouseEvent e)
    {
	   //If it is painting stage
	   if (stage == 1)
	   {
		  //Gets mouse coordinates
		  mouse_x = e.getX ();
		  mouse_y = e.getY ();
		  btn = true;
		  //If pencil tool is selected and mouse is inside canvas
		  if (tool == 0 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 mouse_prevx = mouse_x;
			 mouse_prevy = mouse_y;
			 drawing = true;
		  }
		  else
		  {
			 drawing = false;
		  }
		  //If rectangle tool is selected and mouse is inside canvas
		  if (tool == 1 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //Start point is mouse position
			 start_point.x = e.getX ();
			 start_point.y = e.getY ();
			 //End point is mouse position
			 end_point.x = e.getX ();
			 end_point.y = e.getY ();
			 drawing = true;
		  }
		  //If oval tool is selected and mouse is inside canvas
		  if (tool == 2 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //Start point is mouse position
			 start_point.x = e.getX ();
			 start_point.y = e.getY ();
			 //End point is mouse position
			 end_point.x = e.getX ();
			 end_point.y = e.getY ();
			 drawing = true;
		  }
		  //If line tool is selected and mouse is inside canvas
		  if (tool == 3 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //Start point is mouse position
			 start_point.x = e.getX ();
			 start_point.y = e.getY ();
			 //End point is mouse position
			 end_point.x = e.getX ();
			 end_point.y = e.getY ();
			 drawing = true;
		  }
		  //If eraser tool is selected and mouse is inside canvas
		  if (tool == 4 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //Adds object with eraser specification into the vector
			 point.addElement (new DoodleDrawShapes (mouse_x - (eraser_size / 2), mouse_y - (eraser_size / 2), eraser_size, eraser_size, Color.white, "eraser", fill));
		  }
		  //If mouse is on fill button set fill to true
		  if (MouseInside (width - 85, 442, 74, 34) == true && btn == true && drawing == false && fill == false)
		  {
			 fill = true;
		  }
		  //or else set to false
		  else if (MouseInside (width - 85, 442, 74, 34) == true && btn == true && drawing == false && fill == true)
		  {
			 fill = false;
		  }
		  finish = false;
	   }
    }


    public void mouseReleased (MouseEvent e)
    {
	   //If painting stage
	   if (stage == 1)
	   {
		  //Gets mouse coordinates
		  mouse_x = e.getX ();
		  mouse_y = e.getY ();
		  btn = false;
		  //If pencil tool is selected and mouse is inside canvas
		  if (tool == 0 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 mouse_prevx = mouse_x;
			 mouse_prevy = mouse_y;
			 drawing = false;
		  }
		  //If rectangle tool is selected and mouse is inside canvas
		  if (tool == 1 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //End point is current mouse point
			 end_point.x = e.getX ();
			 end_point.y = e.getY ();
			 //Checks to see where rectangle started since it cannot have negative width and height values and adds the object into the vector with rectangle specification
			 if (end_point.x > start_point.x && end_point.y < start_point.y)
			 {
				point.addElement (new DoodleDrawShapes (start_point.x, end_point.y, end_point.x - start_point.x, start_point.y - end_point.y, current, "rectangle", fill));
			 }
			 else if (end_point.x < start_point.x && end_point.y < start_point.y)
			 {
				point.addElement (new DoodleDrawShapes (end_point.x, end_point.y, start_point.x - end_point.x, start_point.y - end_point.y, current, "rectangle", fill));
			 }
			 else if (end_point.x < start_point.x && end_point.y > start_point.y)
			 {
				point.addElement (new DoodleDrawShapes (end_point.x, start_point.y, start_point.x - end_point.x, end_point.y - start_point.y, current, "rectangle", fill));
			 }
			 else
			 {
				point.addElement (new DoodleDrawShapes (start_point.x, start_point.y, end_point.x - start_point.x, end_point.y - start_point.y, current, "rectangle", fill));
			 }
			 drawing = false;
		  }
		  //If oval tool is selected and mouse is inside canvas
		  if (tool == 2 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //End point is current mouse point
			 end_point.x = e.getX ();
			 end_point.y = e.getY ();
			 //Checks to see where oval started since it cannot have negative width and height values and adds the object into the vector with oval specification
			 if (end_point.x > start_point.x && end_point.y < start_point.y)
			 {
				point.addElement (new DoodleDrawShapes (start_point.x, end_point.y, end_point.x - start_point.x, start_point.y - end_point.y, current, "oval", fill));
			 }
			 else if (end_point.x < start_point.x && end_point.y < start_point.y)
			 {
				point.addElement (new DoodleDrawShapes (end_point.x, end_point.y, start_point.x - end_point.x, start_point.y - end_point.y, current, "oval", fill));
			 }
			 else if (end_point.x < start_point.x && end_point.y > start_point.y)
			 {
				point.addElement (new DoodleDrawShapes (end_point.x, start_point.y, start_point.x - end_point.x, end_point.y - start_point.y, current, "oval", fill));
			 }
			 else
			 {
				point.addElement (new DoodleDrawShapes (start_point.x, start_point.y, end_point.x - start_point.x, end_point.y - start_point.y, current, "oval", fill));
			 }
			 drawing = false;
		  }
		  //If line tool is selected and mouse is inside canvas
		  if (tool == 3 && mouse_x > 3 && mouse_y > 3 && mouse_x < 503 && mouse_y < 503)
		  {
			 //End point is current mouse point
			 end_point.x = e.getX ();
			 end_point.y = e.getY ();
			 //Adds object with line specification into the vector
			 point.addElement (new DoodleDrawShapes (start_point.x, start_point.y, end_point.x, end_point.y, current, "line", fill));
			 drawing = false;
		  }
		  finish = true;
		  repaint ();
	   }
    }


    //Method to check if mouse is inside a box shape
    public boolean MouseInside (int x, int y, int width, int height)
    {
	   if (mouse_x > x && mouse_y > y && mouse_x < x + width && mouse_y < y + height)
	   {
		  return true;
	   }


	   return false;
    }


    public void update (Graphics g)
    {
	   //If instruction stage
	   if (stage == 0)
	   {
		  //draws instruction image
		  g.drawImage (instructions, 0, 0, this);
	   }


	   else if (stage == 1)
	   {
		  //Creates object for the class
		  DoodleDrawShapes s;
		  //For loop to access vector
		  for (int i = 0 ; i < point.size () ; i++)
		  {
			 //Sets object to equal element in vector
			 s = (DoodleDrawShapes) point.elementAt (i);
			 //If the object's shape variable is pencil then draw a line from x1,y1 to x2,y2
			 if (s.shape.equals ("pencil"))
			 {
				//Set color to equal objects given color
				backg.setColor (s.col);
				backg.drawLine (s.x1, s.y1, s.x2, s.y2);
			 }
			 //If the object's shape variable is rectangle then draw a rectangle from x1,y1, with x2 as width and y2 as height
			 if (s.shape.equals ("rectangle"))
			 {
				//Set color to equal objects given color
				backg.setColor (s.col);
				//If object has been asked not to fill draw it not filled
				if (s.fill == false)
				{
				    backg.drawRect (s.x1, s.y1, s.x2, s.y2);
				}
				//Else draws it filled in
				else
				{
				    backg.fillRect (s.x1, s.y1, s.x2, s.y2);
				}
			 }
			 //If the object's shape variable is oval then draw an oval from x1,y1, with x2 as width and y2 as height
			 if (s.shape.equals ("oval"))
			 {
				//Set color to equal objects given color
				backg.setColor (s.col);
				//If object has been asked not to fill draw it not filled
				if (s.fill == false)
				{
				    backg.drawOval (s.x1, s.y1, s.x2, s.y2);
				}
				//Else draws it filled in
				else
				{
				    backg.fillOval (s.x1, s.y1, s.x2, s.y2);
				}
			 }
			 //If the object's shape variable is line then draw a line from x1,y1, to x2,y2
			 if (s.shape.equals ("line"))
			 {
				//Set color to equal objects given color
				backg.setColor (s.col);
				backg.drawLine (s.x1, s.y1, s.x2, s.y2);
			 }
			 //If the object's shape variable is eraser then draw a eraser from x1,y1, with x2 as width and y2 as width
			 if (s.shape.equals ("eraser"))
			 {
				backg.setColor (Color.white);
				backg.fillRect (s.x1, s.y1, s.x2, s.y2);
			 }
		  }
		  //If rectangle tool is chosen
		  if (tool == 1)
		  {
			 if (finish == false && drawing == true)
			 {
				//Sets the color to current color chosen
				backg.setColor (current);
				//If fill has been chosen as false
				if (fill == false)
				{
				    //Draw rectangle based on start point and end point without fill
				    if (end_point.x > start_point.x && end_point.y < start_point.y)
				    {
					   backg.drawRect (start_point.x, end_point.y, end_point.x - start_point.x, start_point.y - end_point.y);
				    }
				    else if (end_point.x < start_point.x && end_point.y < start_point.y)
				    {
					   backg.drawRect (end_point.x, end_point.y, start_point.x - end_point.x, start_point.y - end_point.y);
				    }
				    else if (end_point.x < start_point.x && end_point.y > start_point.y)
				    {
					   backg.drawRect (end_point.x, start_point.y, start_point.x - end_point.x, end_point.y - start_point.y);
				    }
				    else
				    {
					   backg.drawRect (start_point.x, start_point.y, end_point.x - start_point.x, end_point.y - start_point.y);
				    }
				}
				//If fill has been chosen as true
				else
				{
				    //Draw rectangle based on start point and end point with fill
				    if (end_point.x > start_point.x && end_point.y < start_point.y)
				    {
					   backg.fillRect (start_point.x, end_point.y, end_point.x - start_point.x, start_point.y - end_point.y);
				    }
				    else if (end_point.x < start_point.x && end_point.y < start_point.y)
				    {
					   backg.fillRect (end_point.x, end_point.y, start_point.x - end_point.x, start_point.y - end_point.y);
				    }
				    else if (end_point.x < start_point.x && end_point.y > start_point.y)
				    {
					   backg.fillRect (end_point.x, start_point.y, start_point.x - end_point.x, end_point.y - start_point.y);
				    }
				    else
				    {
					   backg.fillRect (start_point.x, start_point.y, end_point.x - start_point.x, end_point.y - start_point.y);
				    }
				}
			 }
		  }
		  //If oval tool is chosen
		  if (tool == 2)
		  {
			 if (finish == false && drawing == true)
			 {
				//Sets the color to current color chosen
				backg.setColor (current);
				//If fill has been chosen as false
				if (fill == false)
				{
				    //Draw oval based on start point and end point without fill
				    if (end_point.x > start_point.x && end_point.y < start_point.y)
				    {
					   backg.drawOval (start_point.x, end_point.y, end_point.x - start_point.x, start_point.y - end_point.y);
				    }
				    else if (end_point.x < start_point.x && end_point.y < start_point.y)
				    {
					   backg.drawOval (end_point.x, end_point.y, start_point.x - end_point.x, start_point.y - end_point.y);
				    }
				    else if (end_point.x < start_point.x && end_point.y > start_point.y)
				    {
					   backg.drawOval (end_point.x, start_point.y, start_point.x - end_point.x, end_point.y - start_point.y);
				    }
				    else
				    {
					   backg.drawOval (start_point.x, start_point.y, end_point.x - start_point.x, end_point.y - start_point.y);
				    }
				}
				//If fill has been chosen as true
				else
				{
				    //Draw oval based on start point and end point with fill
				    if (end_point.x > start_point.x && end_point.y < start_point.y)
				    {
					   backg.fillOval (start_point.x, end_point.y, end_point.x - start_point.x, start_point.y - end_point.y);
				    }
				    else if (end_point.x < start_point.x && end_point.y < start_point.y)
				    {
					   backg.fillOval (end_point.x, end_point.y, start_point.x - end_point.x, start_point.y - end_point.y);
				    }
				    else if (end_point.x < start_point.x && end_point.y > start_point.y)
				    {
					   backg.fillOval (end_point.x, start_point.y, start_point.x - end_point.x, end_point.y - start_point.y);
				    }
				    else
				    {
					   backg.fillOval (start_point.x, start_point.y, end_point.x - start_point.x, end_point.y - start_point.y);
				    }
				}
			 }
		  }
		  //If line tool as been selected
		  if (tool == 3)
		  {
			 if (finish == false && drawing == true)
			 {
				//Set color to current color chosen
				backg.setColor (current);
				//Draw line from start_point to end_point
				backg.drawLine (start_point.x, start_point.y, end_point.x, end_point.y);
			 }
		  }
		  //Draws GUI
		  backg.setColor (Color.red);
		  backg.drawRect (width - 96, 0, 95, height - 1);
		  backg.drawRect (width - 95, 1, 93, height - 3);
		  backg.drawRect (width - 94, 2, 91, height - 5);
		  backg.setColor (new Color (142, 142, 142));
		  backg.fillRect (width - 93, 3, 90, height - 6);
		  backg.setColor (Color.black);
		  backg.drawRect (0, 0, width - 97, height - 1);
		  backg.drawRect (1, 1, width - 99, height - 3);
		  backg.drawRect (2, 2, width - 101, height - 5);
		  DrawBoxes (85, 155, 34, 34, Color.black);
		  DrawBoxes (45, 155, 34, 34, Color.white);
		  DrawBoxes (85, 196, 34, 34, Color.gray);
		  DrawBoxes (45, 196, 34, 34, Color.lightGray);
		  DrawBoxes (85, 237, 34, 34, Color.red);
		  DrawBoxes (45, 237, 34, 34, Color.yellow);
		  DrawBoxes (85, 278, 34, 34, Color.cyan);
		  DrawBoxes (45, 278, 34, 34, Color.blue);
		  DrawBoxes (85, 319, 34, 34, orange);
		  DrawBoxes (45, 319, 34, 34, Color.green);
		  //Draws custom color selection
		  if (current == custom_color)
		  {
			 backg.setColor (Color.white);
			 backg.fillRect (width - 83, 362, 71, 31);
			 backg.setColor (custom_color);
			 backg.fillRect (width - 83, 362, 71, 31);
			 backg.setColor (Color.magenta);
			 backg.drawRect (width - 85, 360, 74, 34);
			 backg.drawRect (width - 84, 361, 72, 32);
		  }
		  else
		  {
			 backg.setColor (Color.white);
			 backg.fillRect (width - 83, 362, 71, 31);
			 backg.setColor (custom_color);
			 backg.fillRect (width - 83, 362, 71, 31);
			 backg.setColor (Color.black);
			 backg.drawRect (width - 85, 360, 74, 34);
			 backg.drawRect (width - 84, 361, 72, 32);
		  }
		  //Draws tool images for GUI
		  if (tool != 0)
		  {
			 backg.drawImage (pencil, width - 85, 11, this);
		  }
		  else if (tool == 0)
		  {
			 backg.drawImage (pencil_selected, width - 85, 11, this);
		  }
		  if (tool != 3)
		  {
			 backg.drawImage (line, width - 45, 11, this);
		  }
		  else if (tool == 3)
		  {
			 backg.drawImage (line_selected, width - 45, 11, this);
		  }
		  if (tool != 1)
		  {
			 backg.drawImage (rectangle, width - 85, 53, this);
		  }
		  else if (tool == 1)
		  {
			 backg.drawImage (rectangle_selected, width - 85, 53, this);
		  }
		  if (tool != 2)
		  {
			 backg.drawImage (oval, width - 45, 53, this);
		  }
		  else if (tool == 2)
		  {
			 backg.drawImage (oval_selected, width - 45, 53, this);
		  }
		  if (tool != 4)
		  {
			 backg.drawImage (eraser, width - 85, 94, this);
		  }
		  else if (tool == 4)
		  {
			 backg.drawImage (eraser_selected, width - 85, 94, this);
		  }
		  if (fill == false)
		  {
			 backg.drawImage (fill_off, width - 85, 442, this);
		  }
		  else if (fill == true)
		  {
			 backg.drawImage (fill_on, width - 85, 442, this);
		  }
		  //Draws color creator button
		  backg.drawImage (color, width - 85, 401, this);
		  //Draws clear canvas button
		  backg.drawImage (clear, width - 45, 94, this);
		  //Draws the image from backbuffer
		  g.drawImage (backbuffer, 0, 0, this);
		  //Clears the screen
		  backg.clearRect (0, 0, width, height);
	   }


	   //If stage is color creator
	   else if (stage == 2)
	   {
		  //If they are currently entering the red value
		  if (substage == 0)
		  {
			 backg.drawString ("Enter red value (between 0 and 255): ", 200, 200);
			 backg.drawString (col, 400, 200);
		  }
		  //If they are currently entering the green value
		  if (substage == 1)
		  {
			 backg.drawString ("Enter green value (between 0 and 255): ", 200, 200);
			 backg.drawString (col, 414, 200);
		  }
		  //If they are currently entering the blue value
		  if (substage == 2)
		  {
			 backg.drawString ("Enter blue value (between 0 and 255): ", 200, 200);
			 backg.drawString (col, 406, 200);
		  }
		  //If they are currently entering the alpha value
		  if (substage == 3)
		  {
			 backg.drawString ("Enter alpha value (between 0 and 255): ", 200, 200);
			 backg.drawString (col, 414, 200);
		  }
		  //Draws the image from backbuffer
		  g.drawImage (backbuffer, 0, 0, this);
		  //Clears the screen
		  backg.clearRect (0, 0, width, height);
	   }


	   //If stage is saving stage
	   else if (stage == 3)
	   {
		  //If they are entering saving path
		  if (substage == 0)
		  {
			 backg.drawString ("Enter directory, press enter when you are ready (file will be saved as image.png): ", 100, 200);
			 backg.drawString ("Example: C:/TEST/Painting   OR    C:\\TEST\\Painting", 100, 225);
			 backg.drawString ("Directory path: " + dir, 100, 250);
			 //Draws the image from backbuffer
			 g.drawImage (backbuffer, 0, 0, this);
			 //Clear the screen
			 backg.clearRect (0, 0, width, height);
		  }
		  //If directory is not found
		  else if (substage == 2)
		  {
			 backg.drawString ("Incorrect input. Press Enter to continue", 100, 200);
			 //Draw contents of backbuffer to screen
			 g.drawImage (backbuffer, 0, 0, this);
			 //Clear screen
			 backg.clearRect (0, 0, width, height);
		  }
	   }
	   //If user has quit
	   else if (stage == 4)
	   {
		  backg.drawImage (smiley, 0, 0, this);
		  backg.setColor (Color.red);
		  //Change font
		  backg.setFont (new Font ("Calibri", Font.BOLD, 40));
		  backg.drawString ("GOODBYE", 100, 200);
		  //Draw backbuffer to screen
		  g.drawImage (backbuffer, 0, 0, this);
		  //Clear screen
		  backg.clearRect (0, 0, width, height);
	   }
    }


    public void paint (Graphics g)
    {
	   //Calls update method
	   update (g);
    }


    /*          Method Dictionary - method draws the color boxes for the program
		x - gets the x value of the box
		y - gets the y value of the box
		get_width - gets the width of the box
		get_height - gets the height of the box
		col - gets the color of the box
    */
    //Method to draw color boxes
    public void DrawBoxes (int x, int y, int get_width, int get_height, Color col)
    {
	   //Makes outline if it is current color
	   if (current == col)
	   {
		  backg.setColor (col);
		  backg.fillRect (width - (x - 2), y + 2, get_width - 3, get_height - 3);
		  backg.setColor (Color.magenta);
		  backg.drawRect (width - x, y, get_width, get_height);
		  backg.drawRect (width - (x - 1), y + 1, get_width - 2, get_height - 2);
	   }


	   //Otherwise outline is black
	   else
	   {
		  backg.setColor (col);
		  backg.fillRect (width - (x - 2), y + 2, get_width - 3, get_height - 3);
		  backg.setColor (Color.black);
		  backg.drawRect (width - x, y, get_width, get_height);
		  backg.drawRect (width - (x - 1), y + 1, get_width - 2, get_height - 2);
	   }
    }


    //Some unused methods required by MouseListener and MouseMotionListener to be implemented
    public void mouseEntered (MouseEvent e)
    {
    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void mouseClicked (MouseEvent e)
    {
    }


    public void mouseMoved (MouseEvent e)
    {
    }
}

//Class for shapes

/*          Variable Dictionary (for variables only with scope of DoodleDrawShapes):
	    x1 - holds the first x value of the shape
	    y1 - holds the first y value of the shape
	    x2 - determines the second x value of the shape
	    y2 - determines the second y value of the shape
	    col - holds the color of the shape
	    shape - holds with type of shape it is
	    fill - holds whether to fill the shape or not

	    Constructor Dictionary (for methods in the DoodleDrawShapes class):
	    DoodleDrawShapes (int get_x1, int get_y1, int get_x2, int get_y2, Color get_col, String get_shape, boolean get_fill)
	    constructor to get all information for the object when it is created
		get_x1 - gets the first x value of the shape
		get_y1 - gets the first y value of the shape
		get_x2 - gets the second x value of the shape
		get_y2 - gets the second y value of the shape
		get_col - gets the color of the shape
		get_shape - gets which shape the object will be
		get_fill - gets if the shape is filled or not

*/
class DoodleDrawShapes
{
    //Holds x and y values of shape
    public int x1, y1, x2, y2;
    //Holds color of shape
    public Color col;
    //Which shape it is
    public String shape = "";
    //If shape it to be filled
    public boolean fill = false;
    //Constructor for shapes
    public DoodleDrawShapes (int get_x1, int get_y1, int get_x2, int get_y2, Color get_col, String get_shape, boolean get_fill)
    {
	   //Initializing values
	   x1 = get_x1;
	   y1 = get_y1;
	   x2 = get_x2;
	   y2 = get_y2;
	   col = get_col;
	   shape = get_shape;
	   fill = get_fill;
    }
}
