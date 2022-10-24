import java.util.ArrayList;

/*
 * Represents a 3D space containing 3D solids. In the context of 
 * parallel projection, this class represents the world coordinate
 * system. It uses three tables to represent vertices, edges, and
 * solids.
 */
public class Stage3d {
    /** a table of the vertices in this {@link Stage3d}. */
    protected ArrayList vertexTable;

    /** a table of the edges in this {@link Stage3d}. */
    protected ArrayList edgeTable;

    /** a table of the solids in this {@link Stage3d}. */
    protected ArrayList solidTable;
}
