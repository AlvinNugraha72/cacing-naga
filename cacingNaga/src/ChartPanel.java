import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

class ChartPanel extends JPanel {
    
    private final List<Integer> dataPoints = new LinkedList<>();
    private final int MAX_POINTS = 50;
    private int lastValue = 50; 
    private Timer timer;
    private int currentTarget = -1; 
    
    // VARIABEL UNTUK GARIS KEPUTUSAN
    private int decisionValue = -1; 
    private boolean showDecisionMarker = false;

    public ChartPanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400, 400));
        for (int i = 0; i < MAX_POINTS; i++) {
            dataPoints.add(50);
        }
    }

    // ==========================================
    // METODE BARU UNTUK DIAKSES OLEH GameEvolusi
    // ==========================================
    public int getDecisionValue() {
        return decisionValue;
    }
    
    public int getLastValue() {
        return lastValue;
    }
    // ==========================================

    public int getCurrentTarget() {
        return currentTarget;
    }

    public void startChartUpdate() {
        this.showDecisionMarker = false; 
        this.decisionValue = -1; // Reset nilai keputusan
        
        dataPoints.clear();
        for (int i = 0; i < MAX_POINTS; i++) {
            dataPoints.add(50);
        }
        lastValue = 50;
        currentTarget = -1; 

        if (timer == null) {
            timer = new Timer(100, e -> updateChartData());
        }
        timer.start();
        repaint();
    }
    
    public void stopChartUpdate() {
        if (timer != null) {
            timer.stop();
        }
    }

    // Metode: Tandai titik keputusan saat tombol ditekan
    public void markDecisionPoint() {
        this.decisionValue = this.lastValue; // Nilai Y saat tombol ditekan
        this.showDecisionMarker = true;
        // TIDAK ADA stopChartUpdate() di sini
        repaint();
    }

    private void updateChartData() {
        int change = (int) (Math.random() * 5) - 2; 
        int newValue = lastValue + change;

        if (newValue > 90) newValue = 90;
        if (newValue < 10) newValue = 10;

        int direction = (newValue >= lastValue) ? 1 : 0; 
        
        currentTarget = direction; 
        
        lastValue = newValue;
        
        dataPoints.add(newValue);
        if (dataPoints.size() > MAX_POINTS) {
            dataPoints.remove(0); 
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // 1. Gambar Garis Grafik
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2)); 

        if (dataPoints.size() > 1) {
            for (int i = 0; i < dataPoints.size() - 1; i++) {
                int y1 = height - (int) (height * (dataPoints.get(i) / 100.0));
                int y2 = height - (int) (height * (dataPoints.get(i + 1) / 100.0));

                int x1 = (int) (width * (i / (double) (MAX_POINTS - 1)));
                int x2 = (int) (width * ((i + 1) / (double) (MAX_POINTS - 1)));

                g2d.drawLine(x1, y1, x2, y2);
            }
        }
        
        // 2. Gambar Penanda Garis Keputusan (Garis Kuning)
        if (showDecisionMarker && decisionValue != -1) {
            int yDecision = height - (int) (height * (decisionValue / 100.0));
            
            g2d.setColor(Color.YELLOW); 
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5, 5}, 0)); 
            g2d.drawLine(0, yDecision, width, yDecision);
            
            // Titik keputusan di ujung kanan (Titik Merah)
            g2d.setColor(Color.RED);
            g2d.fillOval(width - 8, yDecision - 4, 8, 8); 
        }
    }
}   