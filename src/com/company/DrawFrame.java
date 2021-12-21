package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class DrawFrame extends JFrame {

    private ArrayList<Float[]> basePolyPoints;
    private ArrayList<Float[]> points;


    public DrawFrame(String name, ArrayList<Float[]> basePolyPoints, ArrayList<Float[]> points){
        super(name);

        this.basePolyPoints = basePolyPoints;
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
        Graphic graphic = new Graphic(basePolyPoints, points, getSize());

        graphic.setBasePolyColor(new Color(168, 17, 17));
        graphic.setPointsColor(new Color(14, 56, 206));
        graphic.setOuterPolyColor(new Color(21, 111, 55));

        graphPanel.add(graphic,  BorderLayout.CENTER);
        frameContainer.add(graphPanel, BorderLayout.CENTER);
    }

}

class Graphic extends JComponent {
    private double parentWidth;
    private double parentHeight;

    private ArrayList<Float[]> basePolyPoints;
    private ArrayList<Float[]> points;

    private Color basePolyColor;
    private Color pointsColor;
    private Color outerPolyColor;

    public Graphic(ArrayList<Float[]> basePolyPoints, ArrayList<Float[]> points, Dimension parentSize){
        this.basePolyPoints = basePolyPoints;
        this.points = points;

        parentWidth = parentSize.width;
        parentHeight = parentSize.height;

        basePolyColor = new Color(222, 21, 21);
        pointsColor = new Color(19, 66, 253);
        outerPolyColor = new Color(43, 191, 6);
    }

    @Override
    public void paint(Graphics g) {
        AffineTransform affTrans = AffineTransform.getTranslateInstance(0, parentHeight);
        Graphics2D g2D = (Graphics2D) g;

        int xPoints[] = new int[basePolyPoints.size()];
        int yPoints[] = new int[basePolyPoints.size()];

        for (int i = 0; i < basePolyPoints.size(); i++){
            xPoints[i] = basePolyPoints.get(i)[0].intValue();
            yPoints[i] = basePolyPoints.get(i)[1].intValue();
        }

        Polygon basePolygon = new Polygon(xPoints, yPoints, basePolyPoints.size());

        double scaleFactor = calcScaleFactor(basePolygon);
        affTrans.scale(scaleFactor, -scaleFactor);
        g2D.setTransform(affTrans);

        BasicStroke stroke = new BasicStroke((float)scaleFactor * 0.001f);
        g2D.setStroke(stroke);

        g2D.setColor(basePolyColor);
        g2D.drawPolygon(basePolygon);


        xPoints = new int[points.size()];
        yPoints = new int[points.size()];

        for (int i = 0; i < points.size(); i++){
            xPoints[i] = points.get(i)[0].intValue();
            yPoints[i] = points.get(i)[1].intValue();
        }

        g2D.setColor(pointsColor);

        for (int i = 0; i < points.size(); i++){
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

    public void setBasePolyColor(Color basePolyColor) {
        this.basePolyColor = basePolyColor;
    }

    public void setPointsColor(Color pointsColor) {
        this.pointsColor = pointsColor;
    }

    public void setOuterPolyColor(Color outerPolyColor) {
        this.outerPolyColor = outerPolyColor;
    }
}
