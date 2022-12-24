import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PaintClone extends JFrame {

    public static void main(String[] args)
    {
        new PaintClone();
    }

    // Initializing panel to draw
    DrawPanel drawing = new DrawPanel();

    // Initializing buttons
    private JButton btnRed = new JButton("Red");
    private JButton btnGreen = new JButton("Green");
    private JButton btnBlue = new JButton("Blue");
    private JButton btnEraser = new JButton("Eraser");
    private JButton btnSmall = new JButton("Small");
    private JButton btnMedium = new JButton("Medium");
    private JButton btnLarge = new JButton("Large");
    private JButton btnClear = new JButton("Clear");
    private JButton btnSave = new JButton("Save");

    public PaintClone()
    {
        setTitle("Paint Clone");
        setLayout(new BorderLayout());

        // Adding listeners to buttons
        btnRed.addActionListener((e) -> {
            drawing.setColor(Color.red);
        });

        btnGreen.addActionListener((e) -> {
            drawing.setColor(Color.green);
        });

        btnBlue.addActionListener((e) -> {
            drawing.setColor(Color.blue);
        });

        btnEraser.addActionListener((e) -> {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            drawing.setColor(Color.white);
        });

        btnSmall.addActionListener((e) -> {
            drawing.setBrushSize(1);
        });

        btnMedium.addActionListener((e) -> {
            drawing.setBrushSize(5);
        });

        btnLarge.addActionListener((e) -> {
            drawing.setBrushSize(12);
        });

        btnClear.addActionListener((e) -> {
            drawing.clear();
        });

        btnSave.addActionListener((e) -> {
            drawing.save();
        });

        // Creating panel to add buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(100, 100));
        btnPanel.setMinimumSize(new Dimension(100, 100));
        btnPanel.setMaximumSize(new Dimension(100, 100));

        // Adding buttons to the panel
        btnPanel.add(btnBlue);
        btnPanel.add(btnRed);
        btnPanel.add(btnGreen);
        btnPanel.add(btnEraser);
        btnPanel.add(btnClear);
        btnPanel.add(btnSave);
        btnPanel.add(btnSmall);
        btnPanel.add(btnMedium);
        btnPanel.add(btnLarge);

        // Adding panel and drawing panel to frame
        add(drawing, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // Setting size of frame
        setSize(500, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

/* Panel in which drawing is created */
class DrawPanel extends JPanel {

    private Image img;
    private Graphics2D g2D;
    private int currX, currY, prevX, prevY;

    public DrawPanel() {
        // Adding mouse pressed listener
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                // keeping initial coordinate of the mouse
                prevX = event.getX();
                prevY = event.getY();
            }
        });

        // Adding mouse dragged listener
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent event) {
                currX = event.getX();
                currY = event.getY();
                if (g2D != null) {
                    // drawing line from previous coordinate to current coordinate
                    g2D.drawLine(prevX, prevY, currX, currY);
                }
                // repainting panel
                repaint();
                prevX = currX;
                prevY = currY;
            }
        });
    }

    // Drawing graphics to component
    public void paintComponent(Graphics g) {
        if (img == null) {
            img = createImage(getSize().width, getSize().height);
            g2D = (Graphics2D) img.getGraphics();
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(img, 5, 5, null);
    }

    // Method to set color of the drawing
    public void setColor(Color color) {
        g2D.setPaint(color);
        repaint();
    }

    // Method to set brush size
    public void setBrushSize(int size)
    {
        g2D.setStroke(new BasicStroke(size));
    }

    // method to clear drawing
    public void clear() {
        g2D.setPaint(Color.white);
        g2D.fillRect(0, 0, getSize().width, getSize().height);
        g2D.setPaint(Color.black);
        g2D.setStroke(new BasicStroke(1));
        repaint();
    }

    // Method to save drawing to file
    public void save() {
        BufferedImage bufferedImage = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        this.paint(g2);
        try {
            ImageIO.write(bufferedImage, "PNG", new File("image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}