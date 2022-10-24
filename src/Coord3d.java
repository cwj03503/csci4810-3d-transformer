public class Coord3d {
    private double x;
    private double y;
    private double z;

    public Coord3d(double x, double y, double z) 
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * returns this coordinate's x-values.
     * @return this {@code Coord3d}'s x-value.
     */
    public double getX()
    {
        return x;
    }

    /**
     * returns this coordinate's y-values.
     * @return this {@code Coord3d}'s y-value.
     */
    public double getY()
    {
        return y;
    }

    /**
     * returns this coordinate's z-values.
     * @return this {@code Coord3d}'s z-value.
     */
    public double getZ()
    {
        return z;
    }

    /**
     * Sets the value of this {@code Coord3d}'s x-value to the given number.
     * @param x  the value to set this {@code Coord3d}'s x-value to
     * @return  the previous value of x
     */
    public double setX(double x)
    {
        double old = this.x;
        this.x = x;
        return old;
    }
    
    /**
     * Sets the value of this {@code Coord3d}'s y-value to the given number.
     * @param y  the value to set this {@code Coord3d}'s y-value to
     * @return  the previous value of y
     */
    public double setY(double y)
    {
        double old = this.y;
        this.y = y;
        return old;
    }

    /**
     * Sets the value of this {@code Coord3d}'s z-value to the given number.
     * @param z  the value to set this {@code Coord3d}'s z-value to
     * @return  the previous value of z
     */
    public double setZ(double z)
    {
        double old = this.z;
        this.z = z;
        return old;
    }
}
