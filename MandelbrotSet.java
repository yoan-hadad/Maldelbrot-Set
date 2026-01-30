/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FractalPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Yoan Hadad
 */
public class MandelbrotSet extends JPanel{
    //variables needed for the fractal
    int width, height; //width and height of the panel where the drawing happens
    int MaxIterrations; //number of iterations to run
    
    int RGB[]=new int[3]; //color
    int mouseX=0,mouseY=0; //mouse coordinates in pixels
    public static int copyMouseX=0, copyMouseY=0; //copy of the mouse coordinates (needed later in the code)
    int zoom=1; //zoom factor
    //setters
    public void setWidth(int width) {
        this.width=width;
    }

    public void setHeight(int height) {
        this.height=height;
    }

    public void setMaxIterrations(int MaxIterrations) {
        this.MaxIterrations = MaxIterrations;
    }
    //constructor
    public MandelbrotSet(int width, int height, int MaxIterrations, int RGB[], int mX, int mY, int zoom) {
        setWidth(width);
        setHeight(height);
        setMaxIterrations(MaxIterrations);
        this.RGB=RGB;
        mouseX=mX;
        mouseY=mY;
        this.zoom=zoom;
    }
    //paintComponent is overridden so the fractal doesn’t disappear when the window is resized
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
            drawMandelbrot((Graphics2D)g);   
    }
    //main method for drawing the fractal
    public void drawMandelbrot (Graphics2D g){
        double complexX, complexY;
        //mouse coordinates in the complex plane
        double centerX;
        double centerY;
        //check whether this is the first time zooming into the fractal
        if (copyMouseX==0 && copyMouseY==0){
            copyMouseX=mouseX;
            copyMouseY=mouseY;
            //converting the mouse coordinates to complex numbers and trying to
            //center the zoom
            centerX=-2+(mouseX/(double)width)*4;
            centerY=2-(mouseY/(double)height)*4;
            
        }
        else{
            //converting the mouse coordinates to complex numbers
            mouseX=(int)(copyMouseX-(double)width/(zoom*2)+(double)mouseX/(zoom));
            mouseY=(int)(copyMouseY-(double)height/(zoom*2)+(double)mouseY/(zoom));
           
            copyMouseX=mouseX;
            copyMouseY=mouseY;
            centerX=-2+(mouseX/(double)width)*4;
            centerY=2-(mouseY/(double)height)*4;
            
        }
        double range=2.0/zoom; //determines how large a portion of the complex plane to visualize
        
        double minX=centerX-range; //minimum value on the x-axis
        double maxX=centerX+range; //maximum value on the y-axis
        double minY=centerY-range; //minimum value on the x-axis
        double maxY=centerY+range; //maximum value on the y-axis


        //iterating over the panel on which the fractal is drawn
        for (int i=0; i<width; i++)
        {
            for(int j=0; j<height; j++){
                //computing the complex values of parameter C from the equation
                //Zn+1=Z^2+C
                complexX=minX+(i/(double)width)*(maxX-minX);
                complexY=maxY-(j/(double)height)*(maxY-minY);
                //storing the number of iterations needed for the pixel’s complex number to
                //escape the allowed boundary
                int number_of_iterations = magnitudeZ_numberOfIterrations(complexX, complexY);
                //check whether the number has escaped
                if (number_of_iterations==MaxIterrations)
                {
                    //if it hasn’t, it’s colored black
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(i, j, 1, 1);
                }
                else if(number_of_iterations!=0)
                {
                    //if it has, it’s colored based on how fast it escaped
                    double coeff = Math.pow((double)number_of_iterations/MaxIterrations, 0.25);
                    int r =(int)(RGB[0]*coeff);
                    int green =(int)(RGB[1]*coeff);
                    int b =(int)(RGB[2]*coeff);
                    g.setColor(new Color(r, green, b));
                    g.fillRect(i, j, 1, 1);
                }
                    
            }
        }
    }
    //method for counting the number of iterations needed for a number to escape
    private int magnitudeZ_numberOfIterrations (double  x, double y){
        int iterrations=0; //counter
        double real=0, imaginary=0; //the two parts of Zₙ₊₁
        for (int i=0; i<MaxIterrations; i++){
            //computing the i-th iteration for the number Z
            double copi_of_real=real;
            real=Math.pow(real, 2) - Math.pow(imaginary, 2) + x;
            imaginary=2*copi_of_real*imaginary+y;
            //if the square of the complex number is greater than 4, it has escaped
            if (Math.pow(real, 2) + Math.pow(imaginary, 2) > 4){
              return iterrations;  
            }
            else{
                //incrementing the iteration count if it hasn’t escaped
                iterrations++;
            }
        }
        //returning the number of iterations
        return MaxIterrations;
    }
    

}
