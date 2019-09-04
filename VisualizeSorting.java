import java.util.*;
import javax.swing.*;
import java.awt.*;
public class VisualizeSorting extends JFrame
{
    static Color bgc = new Color(0,0,0);
    static VisualizeSorting gui = new VisualizeSorting();
    static int width = 600;
    static int height = 900;
    Image doubleBufferImg;
    Graphics doubleBufferGraphics;
    private static final long serialVersionUID = 1L;
    static holder[] sortMe = new holder[50];
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
    static void generate()
    {
        int x = 0;
        for(int i = 0; i < sortMe.length; i++)
        {
            sortMe[i].setHeight(randomNumber(height,50));
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
            generate();
            bubbleSort();
            gui.repaint();
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
                if(i+1 < sortMe.length)
                {
                    if(sortMe[i+1].getHeight() < sortMe[i].getHeight())
                    {
                        savedVal = sortMe[i+1].getHeight();
                        sortMe[i+1].setHeight(sortMe[i].getHeight());
                        sortMe[i].setHeight(savedVal);
                        sortMe[i].setChecked(true);
                        sortMe[i+1].setChecked(true);
                        isSorted = false;
                        gui.repaint();
                        Thread.sleep(15);
                    }
                    sortMe[i].setChecked(false);
                    sortMe[i+1].setChecked(false);
                }
            }
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
                currentColor = new Color(155,155,155);
            }
            g.setColor(currentColor);
            g.fillRect(sortMe[i].getX(),height-sortMe[i].getHeight(),sortMe[i].getWidth(),sortMe[i].getHeight());
            currentColor = null;
            currentColor = new Color(0,0,0);
            g.setColor(currentColor);
            g.drawRect(sortMe[i].getX(),height-sortMe[i].getHeight(),sortMe[i].getWidth(),sortMe[i].getHeight());
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
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
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
