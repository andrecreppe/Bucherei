import java.awt.*;

public class WindowConfiguration {
    private int width, height, x, y;

    WindowConfiguration() {
        width = 800;
        height = 500;

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        x = ((int) size.getWidth() / 2) - (width / 2);
        y = ((int) size.getHeight() / 2) - (height / 2);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getCoordinateX() {
        return this.x;
    }

    public int getCoordinateY() {
        return this.y;
    }

    public Font getFont(int size, boolean bold) {
        Font text;

        if (bold)
            text = new Font("Calibri", Font.BOLD, size);
        else
            text = new Font("Calibri", Font.PLAIN, size);

        return text;
    }
}
