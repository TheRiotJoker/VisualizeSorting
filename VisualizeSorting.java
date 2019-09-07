import java.util.*;
import javax.swing.*;
import java.awt.*;
public class VisualizeSorting extends JFrame
{
    static int arrayAccesses = 0;
    static int biggestLoop = 0;
    static int avgLoops = 0;
    static int avgLoopsStep = 0;
    static int comparisons = 0;
    static int loops = 0;
    static int amountOfRuns = 0;
    static Color bgc = new Color(0,0,0);
    static VisualizeSorting gui = new VisualizeSorting();
    static int width = 1800;
    static int height = 1000;
    static int delay = 1;
    Image doubleBufferImg;
    Graphics doubleBufferGraphics;
    private static final long serialVersionUID = 1L;
    static holder[] sortMe = new holder[600];
    static int randomNumber(int max, int min)
    {
        Random random = new Random();
        return random.nextInt((max-min)+1)+min;
    }
    static void initialize()
    {
        gui.setVisible(true);
        gui.setSize(width,height);
        gui.setResizable(false);
        gui.setTitle("Bubblesort Visualized");
        for(int i = 0; i < sortMe.length; i++)
        {
            sortMe[i] = new holder();
        }
    }
    static void check() throws InterruptedException
    {
        for(int i = 0; i < sortMe.length; i++)
        {
            if(i+1 < sortMe.length)
            {
                if(sortMe[i].getHeight() < sortMe[i+1].getHeight())
                {
                    sortMe[i].setEndCheck(true);
                }
                else
                {
                    break;
                }
            }
            gui.repaint();
            Thread.sleep(delay);
        }
        for(int i = 0; i < sortMe.length; i++)
        {
            sortMe[i].setEndCheck(false);
        }
    }
    static void generate()
    {
        int x = 0;
        for(int i = 0; i < sortMe.length; i++)
        {
            sortMe[i].setHeight(randomNumber(height-100,50));
            sortMe[i].setWidth(width/sortMe.length);
            sortMe[i].setX(x);
            x = x+sortMe[i].getWidth();
            sortMe[i].setY(height);
        }
    }
    public static void main(String[] args) throws InterruptedException
    {
        Scanner scan = new Scanner(System.in);
        initialize();
        while(!(scan.next().equalsIgnoreCase("exit")))
        {
            loops = 0;
            comparisons = 0;
            generate();
            bubbleSort();
            gui.repaint();
            avgLoopsStep += loops;
            amountOfRuns++;
            avgLoops = avgLoopsStep / amountOfRuns;
            if(loops > biggestLoop)
            {
                biggestLoop = loops;
            }
        }
    }
    public static void bubbleSort() throws InterruptedException  
    {
        boolean isSorted = false;
        int savedVal = 0;
        while(isSorted == false)
        {
            isSorted = true;
            for(int i = 0; i < sortMe.length; i++)
            {
                loops++;
                if(i+1 < sortMe.length)
                {
                    if(sortMe[i+1].getHeight() < sortMe[i].getHeight())
                    {
                        comparisons++;
                        savedVal = sortMe[i+1].getHeight();
                        sortMe[i+1].setHeight(sortMe[i].getHeight());
                        sortMe[i].setHeight(savedVal);
                        sortMe[i].setChecked(true);
                        sortMe[i+1].setChecked(true);
                        arrayAccesses += 3;
                        isSorted = false;
                        gui.repaint();
                        Thread.sleep(delay);
                    }
                    sortMe[i].setChecked(false);
                    sortMe[i+1].setChecked(false);
                }
            }
            check();
        }
        doneSorting();
    }
    public static void doneSorting() throws InterruptedException
    {
        for(int i = 0; i < sortMe.length; i++)
        {
            sortMe[i].setEndCheck(true);
            gui.repaint();
            Thread.sleep(delay);
        }
        for(int i = 0; i < sortMe.length; i++)
        {
            sortMe[i].setEndCheck(false);
            gui.repaint();
            Thread.sleep(delay);
        }
    }
    public void paintComponent(Graphics g)
    {
        Color yellow = new Color(255,255,255);
        Color currentColor = new Color(222,222,222);
        g.setColor(yellow);
        g.fillRect(0,0,width,height);
        g.setColor(currentColor);
        for(int i = 0; i < sortMe.length; i++)
        {
            currentColor = null;
            if(sortMe[i].getChecked() == true)
            {
                currentColor = new Color(255,0,0);  
            }
            else
            {
                if(sortMe[i].getEndCheck() == true)
                {
                    currentColor = new Color(0,255,0);
                }
                else
                {
                    currentColor = new Color(155,155,155);
                }
            }
            g.setColor(currentColor);
            g.fillRect(sortMe[i].getX(),height-sortMe[i].getHeight(),sortMe[i].getWidth(),sortMe[i].getHeight());
            currentColor = null;
            currentColor = new Color(0,0,0);
            g.setColor(currentColor);
            g.drawRect(sortMe[i].getX(),height-sortMe[i].getHeight(),sortMe[i].getWidth(),sortMe[i].getHeight());
            g.drawString("Loops needed: "+loops,20,50);
            g.drawString("Delay "+delay+" ms",20,70);
            g.drawString("Average Amount of Loops: "+avgLoops,200,50);
            g.drawString("Comparisons: "+comparisons,200,70);
            g.drawString("Longest Sort (amount of loops): "+biggestLoop,20,90);
            g.drawString("Array Accesses: "+arrayAccesses, 400,50);
        }
    }
    @Override
    public void paint(Graphics g)
    {
        doubleBufferImg = createImage(width, height); //creates an image of the current screen
        doubleBufferGraphics = doubleBufferImg.getGraphics(); //gets the graphics of the current screen
        paintComponent(doubleBufferGraphics); //takes the graphics
        g.drawImage(doubleBufferImg,0,0,this); //draws them to the screen (i don't completely understand this either)
    }
}
class holder
{
    boolean checked = false;
    boolean endCheck = false;
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    boolean getEndCheck()
    {
        return endCheck;
    }
    void setEndCheck(boolean endCheck)
    {
        this.endCheck = endCheck;
    }
    boolean getChecked()
    {
        return checked;
    }
    void setChecked(boolean checked)
    {
        this.checked = checked;
    }
    void setX(int x)
    {
        this.x = x;
    }
    int getX()
    {
        return x;
    }
    void setY(int y)
    {
        this.y = y;
    }
    int getY()
    {
        return y;
    }
    void setWidth(int width)
    {
        this.width = width;
    }
    int getWidth()
    {
        return width;
    }
    void setHeight(int height)
    {
        this.height = height;
    }
    int getHeight()
    {
        return height;
    }
}
