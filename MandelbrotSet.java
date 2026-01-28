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
    //променливи нужни за фрактала
    int width, height; //дължина и височина на панела в който се рисева
    int MaxIterrations; //прой на итерации за изпълнине
    
    int RGB[]=new int[3]; //цвят
    int mouseX=0,mouseY=0; //координати на мишката в пиксели
    public static int copyMouseX=0, copyMouseY=0; //копие на координати в мишката(нужни са надолу в кода)
    int zoom=1; //приближение
    //сетъри
    public void setWidth(int width) {
        this.width=width;
    }

    public void setHeight(int height) {
        this.height=height;
    }

    public void setMaxIterrations(int MaxIterrations) {
        this.MaxIterrations = MaxIterrations;
    }
    //конструктор
    public MandelbrotSet(int width, int height, int MaxIterrations, int RGB[], int mX, int mY, int zoom) {
        setWidth(width);
        setHeight(height);
        setMaxIterrations(MaxIterrations);
        this.RGB=RGB;
        mouseX=mX;
        mouseY=mY;
        this.zoom=zoom;
    }
    //пренаписва се paintComponent за да изчезва фрактала при промяна на размера на прозореца
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
            drawMandelbrot((Graphics2D)g);   
    }
    //основен метод за рисуване на фрактал
    public void drawMandelbrot (Graphics2D g){
        double complexX, complexY;
        //координати на мишката в комплексната равнина
        double centerX;
        double centerY;
        //проверка дали е първото увеличаване на фрактала
        if (copyMouseX==0 && copyMouseY==0){
            copyMouseX=mouseX;
            copyMouseY=mouseY;
            //превръщане на координатите на мишката в комплексни числа и опит да
            //се центрира увеличението
            centerX=-2+(mouseX/(double)width)*4;
            centerY=2-(mouseY/(double)height)*4;
            
        }
        else{
            //превръщане на координатите на мишката в комплексни числа
            mouseX=(int)(copyMouseX-(double)width/(zoom*2)+(double)mouseX/(zoom));
            mouseY=(int)(copyMouseY-(double)height/(zoom*2)+(double)mouseY/(zoom));
           
            copyMouseX=mouseX;
            copyMouseY=mouseY;
            centerX=-2+(mouseX/(double)width)*4;
            centerY=2-(mouseY/(double)height)*4;
            
        }
        double range=2.0/zoom; //Определя колко голяма част от комплексната равнина да се визуализира
        
        double minX=centerX-range; //Минимална стойност по абциса
        double maxX=centerX+range; //Максимална стойност по ордината
        double minY=centerY-range; //Минимална стойност по абциса
        double maxY=centerY+range; //Максимална стойност по ордината


        //обхождане на панела на който се рисува фракталът
        for (int i=0; i<width; i++)
        {
            for(int j=0; j<height; j++){
                //пресмятат се комплексните стойности на параметъра C от уравнението
                //Zn+1=Z^2+C
                complexX=minX+(i/(double)width)*(maxX-minX);
                complexY=maxY-(j/(double)height)*(maxY-minY);
                //запазва се номера на итерации нужни на комплексното число на 
                //пиксела да избяга от допустимата граница
                int number_of_iterations = magnitudeZ_numberOfIterrations(complexX, complexY);
                //проверка дали числото е избягало
                if (number_of_iterations==MaxIterrations)
                {
                    //ако не се оцветява в черно
                    g.setColor(new Color(0, 0, 0));
                    g.fillRect(i, j, 1, 1);
                }
                else if(number_of_iterations!=0)
                {
                    //ако е се оцветява спрямо бързината на избягване
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
    //метод за преброяване на броя итерации нужни на едно число да избяга
    private int magnitudeZ_numberOfIterrations (double  x, double y){
        int iterrations=0; //брояч
        double real=0, imaginary=0; //двете части на Zn+1
        for (int i=0; i<MaxIterrations; i++){
            //пресмятане на i-та итерация за числото Z
            double copi_of_real=real;
            real=Math.pow(real, 2) - Math.pow(imaginary, 2) + x;
            imaginary=2*copi_of_real*imaginary+y;
            //ако квадрата на комплексното число е по-голям от 4, то то е избягало
            if (Math.pow(real, 2) + Math.pow(imaginary, 2) > 4){
              return iterrations;  
            }
            else{
                //увеличават се итерациите ако не е избягало
                iterrations++;
            }
        }
        //връща се броя на итерациите
        return MaxIterrations;
    }
    

}
