import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RevolvingLogo extends JPanel implements ActionListener {
    private BufferedImage logoImage;
    private double angle = 0;
    private int x = 250;
    private int y = 250;
    private int dx = 2;
    private int dy = 2;
    private static final int LOGO_SIZE = 200; // Logo Size
    private static final int ROTATION_SPEED = 10; // Speed

    public RevolvingLogo() {
        try {
            // Load the PNG image
            logoImage = ImageIO.read(new File("src/loyolaimage.png"));
        } catch (IOException e) {
            System.out.println("Error cannot read file");
        }

        Timer timer = new Timer(ROTATION_SPEED, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;



        // Clear the previous drawings
        g2d.clearRect(0, 0, getWidth(), getHeight());

        // Translate the coordinate system to the logo's position
        g2d.translate(x, y);

        // Rotate the coordinate system by the current angle
        g2d.rotate(Math.toRadians(angle));

        // Draw the scaled logo image
        if (logoImage != null) {
            int scaledWidth = LOGO_SIZE;
            int scaledHeight = LOGO_SIZE * logoImage.getHeight() / logoImage.getWidth();
            g2d.drawImage(logoImage, -scaledWidth / 2, -scaledHeight / 2, scaledWidth, scaledHeight, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update rotation angle
        angle += 1;

        // Update logo position
        x += dx;
        y += dy;

        // Check for collision with window borders
        if (x <= 0 || x + LOGO_SIZE >= getWidth()) {
            dx = -dx; // Reverse x direction
        }
        if (y <= 0 || y + LOGO_SIZE >= getHeight()) {
            dy = -dy; // Reverse y direction
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Revolving Logo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.add(new RevolvingLogo());
        frame.setVisible(true);
    }
}
