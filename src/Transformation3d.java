import Jama.Matrix;

/**
 * Represents a transformation matrix that can be applied to 
 * some {@code Shape}. This class comes equipped with several 
 * simple transformation operations. 
 */
public class Transformation3d
{
    private Matrix matrix;

    /**
     * Creates a new Transformation3d instance using the 4x4 identity matrix.
     */
    public Transformation3d()
    {
        matrix = Matrix.identity(4, 4);
    }


    /**
     * Concatenates the matrix for transforming a shape {@code tX} horizontally and
     * {@code tY} vertically.
     * @param tX  how many units to scale the shape horizontally
     * @param tY  how many units to scale the shape vertically
     */
    public void basicTranslate(int tX, int tY)
    {
        double[][] basicTranslationMatrix = {
            {1, 0, 0},
            {0, 1, 0},
            {tX, tY, 1}
        };
        Matrix translationMatrix = new Matrix(basicTranslationMatrix);
        matrix = matrix.times(translationMatrix); 
    }

    /**
     * Concatenates the clockwise 2D rotation matrix for a given angle to this matrix.
     * The matrix is for a rotation that is relative to the origin.
     * @param degrees  an angle between 0 and 360 in degrees
     */
    public void basicRotateClockwise(int degrees)
    {
        double radians = Math.toRadians(degrees);
        double[][] basicRotateClockwiseMatrix = {
            {Math.cos(radians), -Math.sin(radians), 0},
            {Math.sin(radians), Math.cos(radians), 0},
            {0, 0, 1}
        };
        Matrix rotationMatrix = new Matrix(basicRotateClockwiseMatrix);
        matrix = matrix.times(rotationMatrix); 
    }

    /**
     * Concatenates the counterclockwise 2D rotation matrix for a given angle to this matrix.
     * The matrix is for a rotation that is relative to the origin.
     * @param angle an angle between 0 and 360 in degrees
     */
    public void basicRotateCounterClockwise(int angle)
    {
        basicRotateClockwise(-angle);
    }

    /**
     * Concatenates the matrix for scaling a coordinate by {@code sX} horizontally
     * and {@code sY} vertically.
     * @param sX  how much to scale the shape horizontally
     * @param sY  how much to scale the shape vertically
     */
    public void basicScale(double sX, double sY)
    {
        double[][] arrayMatrix = {
            {sX, 0, 0},
            {0, sY, 0},
            {0, 0, 1}
        };
        Matrix scaleMatrix = new Matrix(arrayMatrix);
        matrix = matrix.times(scaleMatrix); 
    }

    /**
     * Concatenates the clockwise 2D rotation matrix for a given angle to this matrix, 
     * with the matrix for a rotation bring relative to a given point: {@code (cX, cY)}.
     * @param angle  an angle between 0 and 360 in degrees
     * @param cX  the x position of the coordinate at the center of rotation
     * @param cY  the y position of the coordinate at the center of rotation
     */
    public void rotateClockwise(int angle, int cX, int cY)
    {
        basicTranslate(-cX, -cY);
        basicRotateClockwise(angle);
        basicTranslate(cX, cY);
    }

    /**
     * Concatenates the counterclockwise 2D rotation matrix for a given angle to this matrix, 
     * with the matrix for a rotation bring relative to a given point: {@code (cX, cY)}.
     * @param angle  an angle between 0 and 360 in degrees
     * @param cX  the x position of the coordinate at the center of rotation
     * @param cY  the y position of the coordinate at the center of rotation
     */
    public void rotateCounterClockwise(int angle, int cX, int cY)
    {
        basicTranslate(-cX, -cY);
        basicRotateClockwise(-angle);
        basicTranslate(cX, cY);
    }

    /**
     * Concatenates the matrix for scaling a coordinate by {@code sX} horizontally
     * and {@code sY} vertically relative to some point {@code (cX, cY)}.
     * @param sX  how many units to scale the shape horizontally
     * @param sY  how many units to scale the shape vertically
     */
    public void scale(double sX, double sY, int cX, int cY)
    {
        basicTranslate(-cX, -cY);
        basicScale(sX, sY);
        basicTranslate(cX, cY);
    }

    public Matrix getMatrix()
    {
        return matrix;
    }

    /**
     * Sets this {@link Transformation}'s {@code Matrix} to an identity matrix
     * so that when applied to a shape, this transformation will have no effect.
     */
    public void reset()
    {
        matrix = Matrix.identity(4, 4);
    }

}
