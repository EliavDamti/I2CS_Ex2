package Ex2;


public class Index2D implements Pixel2D
{

    private int x;
    private int y;
    public Index2D(int w, int h)
    {
        this.x = w;
        this.y = h;
    }
    public Index2D(Pixel2D other)
    {
        this(other.getX(), other.getY());
    }
    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY()
    {
        return y;
    }

    @Override
    public double distance2D(Pixel2D p2)
    {
        if (p2 == null)
        {
            throw new RuntimeException("p2 is null");
        }
        int dx = this.x - p2.getX();
        int dy = this.y - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object p)
    {
        if (this == p)
        {
            return true;
        }
        if (p == null || getClass() != p.getClass())
        {
            return false;
        }
        Index2D other = (Index2D) p;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}