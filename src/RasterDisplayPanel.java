import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A JPanel containing an {@code BufferedImage} designed to 
 * simulate a raster display device where each pixel in the buffer is 
 * 1 bit. The panel cannot be resized.  
 */
public class RasterDisplayPanel extends JPanel
{
    /** simulates the screen buffer of a raster scan device. */
    protected BufferedImage screenBuffer;
    
    private int width;
    private int height;
    
    
    /**
     * Creates a new RasterDisplayPanel with a screenBuffer of given width and height.
     * @param width
     * @param height
     */
    public RasterDisplayPanel(int width, int height)
    {
        this.width = width;
        this.height = height;
        screenBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * "Activates" a given pixel on the raster display by setting it's color to white. 
     * @param x  the x coordinate of the pixel
     * @param y  the 7 coordinate of the pixel
     */
    public void putPixel(int x, int y) 
    {
        try
        {
            screenBuffer.setRGB(x, y, 0xFFFFFF); // white pixel
        }
        catch (ArrayIndexOutOfBoundsException aioobe)
        {
            // just don't draw the pixel
        }
    }

    /**
     * "Deactivates" a given pixel on the raster display by setting it's color to black. 
     * @param x  the x coordinate of the pixel
     * @param y  the 7 coordinate of the pixel
     */
    public void removePixel(int x, int y) 
    {
        screenBuffer.setRGB(x, y, 0x000000); // black pixel
    }

    /*
     * Paints an image on this panel based on the {@code screenBuffer}.
     */
    public void drawPanel()
    {
        this.removeAll();
        ImageIcon icon = new ImageIcon(screenBuffer);
        JLabel iconLabel = new JLabel(icon);
        this.add(iconLabel);

        this.revalidate();
        this.repaint();
    }

    /*
     * Resets the {@code screenBuffer} so that all bits that represent pixels are set to 0.
     */
    public void clearBuffer()
    {
        this.screenBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /*
        public long drawShape(Shape shape)
        {
            ArrayList<Pair<Integer, Integer>> pointList = shape.getPointList();
            ArrayList<Pair<Integer, Integer>> lineList = shape.getLineList();
            int x0, y0, x1, y1;
            long runTime = 0;

            for (Pair<Integer,Integer> pair : lineList) {
                int startPointIndex = pair.getKey();
                int endPointIndex = pair.getValue();
                Pair<Integer,Integer> startPoint = pointList.get(startPointIndex);
                Pair<Integer,Integer> endPoint = pointList.get(endPointIndex);
                x0 = startPoint.getKey();
                y0 = startPoint.getValue();
                x1 = endPoint.getKey();
                y1 = endPoint.getValue();
                runTime += drawLine(x0, y0, x1, y1);
            }
            return runTime;

        }
    */

   /**
     * Draws a line with endpoints roughly at (x0,y0) and (y0,y1) using Brensenham's 
     * algorithm and returns an execution time in nanoseconds. This method is heavily
     * based on the implementation provided on the Wikipedia Article for Bresenham's
     * line algorithm. See the Wikipedia implementation 
     * <a href="https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm">here.</a>
     * Note that the execution time includes only the time spent in the critical loop
     * of the algorithm.
     * @param x0  the x-value of the starting point of the line.
     * @param x1  the x-value of the ending point of the line.
     * @param y0  the y-value of the starting point of the line.
     * @param y1  the y-value of the ending point of the line.
     */
    public long drawLine(int x0, int y0, int x1, int y1) {
        
        int deltaX = x1 - x0;
        int deltaY = y1 - y0;

        if (Math.abs(deltaY) < Math.abs(deltaX)) {
            // shallow line
            if (x0 > x1)
            {
                // swap so that the (x0,y0) is to the left of (x1,y1)
                return drawLineShallow(x1, y1, x0, y0);
            }
            else
            {
                return drawLineShallow(x0, y0, x1, y1);
            }
        }
        else
        {
            // steep line
            if (y0 > y1)
            {
                // swap so that the (x0,y0) is to the north of (x1,y1)
                return drawLineSteep(x1, y1, x0, y0);
            }
            else
            {
                return drawLineSteep(x0, y0, x1, y1);
            }
        }
    }

    /**
     * Draws a line with endpoints roughly at (x0,y0) and (y0,y1), given that the slope
     * of the line between these points is between 0 an -1. Returns an execution time 
     * in nanoseconds. Note that the execution time includes only the time spent in the
     * critical loop of the algorithm.
     * @param x0  the x-value of the starting point of the line.
     * @param x1  the x-value of the ending point of the line.
     * @param y0  the y-value of the starting point of the line.
     * @param y1  the y-value of the ending point of the line.
     */
    private long drawLineShallow(int x0, int y0, int x1, int y1)
    {
        // slope is between 0 and -1
        int deltaX = x1 - x0;
        int deltaY = y1 - y0;
        int yi = 1;
        // reverse direction
        if (deltaY < 0) {
            yi = -1;
            deltaY = -deltaY;
        }
        int D = (2 * deltaY) - deltaX;
        int y = y0;

        long startTime = System.nanoTime();
        for (int i = x0; i <= x1; i++)
        {
            putPixel(i, y);
            if (D > 0)
            {
                y = y + yi;
                D = D + (2 * (deltaY - deltaX));
            }
            else
            {
                D = D + (2 * deltaY);
            }
        }
        return System.nanoTime() - startTime;
    }

    /**
     * Draws a line with endpoints roughly at (x0,y0) and (y0,y1), given that the slope
     * of the line between these points is less than -1. Returns an execution time 
     * in nanoseconds. Note that the execution time includes only the time spent in the
     * critical loop of the algorithm.
     * @param x0  the x-value of the starting point of the line.
     * @param x1  the x-value of the ending point of the line.
     * @param y0  the y-value of the starting point of the line.
     * @param y1  the y-value of the ending point of the line.
     */
    private long drawLineSteep(int x0, int y0, int x1, int y1)
    {
        // slope is between 0 and -1
        int deltaX = x1 - x0;
        int deltaY = y1 - y0;
        int xi = 1;
        // reverse direction
        if (deltaX < 0) {
            xi = -1;
            deltaX = -deltaX;
        }
        int D = (2 * deltaX) - deltaY;
        int x = x0;
        long startTime = System.nanoTime();
        for (int i = y0; i <= y1; i++)
        {
            putPixel(x, i);
            if (D > 0)
            {
                x = x + xi;
                D = D + (2 * (deltaX - deltaY));
            }
            else
            {
                D = D + (2 * deltaX);
            }
        }
        return System.nanoTime() - startTime;
    }

} // RasterDisplayPanel
