package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class MyFrame extends JFrame {

    private int[][] points;
    private  int from;
    private int to;


    public MyFrame(String name, int[][] points, int from, int to){
        super(name);

        this.from = from;
        this.to = to;
        this.points = points;

        setSize(800, 800);
        setPreferredSize(getSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initFrame();
        pack();
        setVisible(true);
    }

    private void initFrame(){
        Container frameContainer = getContentPane();
        frameContainer.setLayout(new BorderLayout());
        JPanel graphPanel = new JPanel(new BorderLayout());
        graphPanel.setBackground(new Color(203, 203, 203));
        graphPanel.setPreferredSize(getSize());
        graphPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createLoweredBevelBorder()
        ));
        Graphic graphic = new Graphic(points, from, to, getSize());

        graphic.setPointsColor(new Color(14, 56, 206));

        graphPanel.add(graphic,  BorderLayout.CENTER);
        frameContainer.add(graphPanel, BorderLayout.CENTER);
    }

}

class Graphic extends JComponent {
    private double parentWidth;
    private double parentHeight;


    private int[][] points;
    private  int from;
    private int to;

    private Color pointsColor;
    private Color lineColor;


    public Graphic(int[][] points, int from, int to, Dimension parentSize){

        this.points = points;
        this.from = from;
        this.to = to;

        parentWidth = parentSize.width;
        parentHeight = parentSize.height;

        pointsColor = new Color(19, 66, 253);
        lineColor = new Color(65, 133, 65);

    }

    @Override
    public void paint(Graphics g) {
        AffineTransform affTrans = AffineTransform.getTranslateInstance(0, parentHeight);
        Graphics2D g2D = (Graphics2D) g;
        int shift = 3;

        int xPoints[] = new int[points.length];
        int yPoints[] = new int[points.length];

        for (int i = 0; i < points.length; i++){
            xPoints[i] = points[i][0]+ shift;
            yPoints[i] = points[i][1]+ shift;
        }

        Polygon basePolygon = new Polygon(new int[]{-3,15,-3,15}, new int[]{-3,-3,15,15}, 4);

        double scaleFactor = calcScaleFactor(basePolygon);
        affTrans.scale(scaleFactor, -scaleFactor);
        g2D.setTransform(affTrans);

        BasicStroke stroke = new BasicStroke((float)scaleFactor * 0.001f);
        g2D.setStroke(stroke);

        g2D.setColor(lineColor);
        g2D.drawLine(xPoints[from], yPoints[from], xPoints[to], yPoints[to]);
        g2D.setColor(pointsColor);

        for (int i = 0; i < points.length; i++){
            g2D.setStroke(new BasicStroke(stroke.getLineWidth() * 3));
            g2D.drawLine(xPoints[i], yPoints[i], xPoints[i], yPoints[i]);
            g2D.setStroke(stroke);
        }

    }

    private double calcScaleFactor(Shape shape){
        double maxX = shape.getBounds2D().getMaxX();
        double maxY = shape.getBounds2D().getMaxY();

        double factorX = parentWidth/maxX;
        double factorY = parentHeight/maxY;

        return factorX < factorY ? factorX : factorY;
    }


    public void setPointsColor(Color pointsColor) {
        this.pointsColor = pointsColor;
    }

}
