import java.awt.*;
import javax.swing.*;

public class GameEvolusi extends JFrame {

    private int currentLevel = 0;
    private final String[] MAKHLUK = {"ulat", "cacing", "belut", "ular", "naga"};
    
    // Deklarasi Komponen
    private JLabel statusLabel;
    private JLabel imageLabel;
    private ChartPanel chartPanel;
    private JButton upButton; 
    private JButton downButton; 
    
    // Variabel Timer Utama
    private Timer countdownTimer;
    private final int MAX_TIME = 5; 
    private int timeRemaining = MAX_TIME; 
    private JLabel timerLabel;
    
    // Variabel Timer Tunggu Hasil (BARU)
    private int resultTimeRemaining;
    private Timer resultCountdownTimer; 
    
    // Variabel Waktu Jeda
    private final int RESULT_DELAY_MS = 5000; 
    private final int ROUND_RESET_DELAY_MS = 500; 

    public GameEvolusi() {
        setTitle("Probabilitas Evolusi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // --- Panel Status (NORTH) ---
        statusLabel = new JLabel("Level: Ulat", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        timerLabel = new JLabel("Waktu: Siap!", SwingConstants.RIGHT);
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(statusLabel, BorderLayout.CENTER);
        topPanel.add(timerLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // --- Panel Tengah (Grafik dan Gambar) - CENTER ---
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        
        chartPanel = new ChartPanel();
        centerPanel.add(chartPanel);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        centerPanel.add(imageLabel);
        
        add(centerPanel, BorderLayout.CENTER);

        // --- Panel Tombol (SOUTH) ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        upButton = new JButton("NAIK"); 
        downButton = new JButton("TURUN"); 

        upButton.addActionListener(e -> processTebakan(1));
        downButton.addActionListener(e -> processTebakan(0));
        
        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Inisiasi Game
        updateDisplay(); 
        startNewRound(); 

        setLocationRelativeTo(null);
        setVisible(true);
    } 
    
    private void startNewRound() {
        timeRemaining = MAX_TIME;
        timerLabel.setText("Waktu: " + timeRemaining);
        
        upButton.setEnabled(true);
        downButton.setEnabled(true);
        chartPanel.startChartUpdate(); 
        
        if (countdownTimer != null) {
            countdownTimer.stop();
        }

        countdownTimer = new Timer(1000, e -> {
            timeRemaining--;
            if (timeRemaining > 0) {
                timerLabel.setText("Waktu: " + timeRemaining);
            } else {
                countdownTimer.stop();
                processTebakan(-1); // Waktu habis
            }
        });
        countdownTimer.start();
    }

    // 0 = Turun, 1 = Naik, -1 = Waktu Habis
    private void processTebakan(int tebakanPemain) {
        
        countdownTimer.stop(); 
        upButton.setEnabled(false);
        downButton.setEnabled(false);
        
        if (tebakanPemain == -1) {
            JOptionPane.showMessageDialog(this, "WAKTU HABIS! Tebakan tidak sah.", "Hasil", JOptionPane.WARNING_MESSAGE);
            currentLevel = Math.max(0, currentLevel - 1); 
            updateDisplay();
            startDelayedNextRound();
            return;
        }

        // 1. Tandai Titik Keputusan (Grafik masih berjalan)
        chartPanel.markDecisionPoint(); 
        
        // --- LOGIKA TIMER TUNGGU HASIL (PERBAIKAN) ---
        resultTimeRemaining = RESULT_DELAY_MS / 1000;
        
        if (resultCountdownTimer != null) resultCountdownTimer.stop(); // Hentikan jika ada sisa
        
        resultCountdownTimer = new Timer(1000, e -> {
            resultTimeRemaining--;
            if (resultTimeRemaining >= 0) {
                timerLabel.setText("Waktu: Menunggu Hasil (" + resultTimeRemaining + "s)");
            }
        });
        resultCountdownTimer.setInitialDelay(0);
        resultCountdownTimer.start(); // Mulai hitungan mundur di label

        // 2. Mulai Jeda 5 Detik (Timer untuk memicu hasil)
        Timer resultTimer = new Timer(RESULT_DELAY_MS, e -> {
            if (resultCountdownTimer != null) resultCountdownTimer.stop(); // Hentikan hitungan mundur
            chartPanel.stopChartUpdate(); 
            displayResult(tebakanPemain); 
        });
        resultTimer.setRepeats(false);
        resultTimer.start();
    }

    private void displayResult(int tebakanPemain) {
        if (resultCountdownTimer != null) {
            resultCountdownTimer.stop(); // Pastikan berhenti
        }
        
        int finalValue = chartPanel.getLastValue(); 
        int decisionValue = chartPanel.getDecisionValue();
        
        // 1 = Naik (finalValue > decisionValue), 0 = Turun/Sama
        int actualResult = (finalValue > decisionValue) ? 1 : 0; 
        
        // Kasus spesial: Jika finalValue == decisionValue
        if (finalValue == decisionValue) {
             // Jika harga tidak bergerak, tebakan apapun dianggap salah
             actualResult = (tebakanPemain == 1) ? 0 : 1; 
        }

        String resultMessage;
        int messageType;

        if (tebakanPemain == actualResult) {
            // TEBAKAN BENAR
            if (currentLevel == 4) {
                resultMessage = "Selamat! Anda mencapai NAGA dan MENANG!";
                resetGame(); 
                messageType = JOptionPane.INFORMATION_MESSAGE;
            } else {
                currentLevel++;
                resultMessage = "Tebakan BENAR! Naik ke level " + MAKHLUK[currentLevel].toUpperCase();
                messageType = JOptionPane.INFORMATION_MESSAGE;
            }
        } else {
            // TEBAKAN SALAH
            if (currentLevel == 0) {
                resultMessage = "Anda KALAH! Ulat jatuh...";
                resetGame(); 
                messageType = JOptionPane.ERROR_MESSAGE;
            } else {
                currentLevel--;
                resultMessage = "Tebakan SALAH! Turun ke level " + MAKHLUK[currentLevel].toUpperCase();
                messageType = JOptionPane.WARNING_MESSAGE;
            }
        }
        
        updateDisplay(); 
        JOptionPane.showMessageDialog(this, resultMessage, "Hasil Tebakan", messageType);
        startDelayedNextRound(); 
    }
    
    private void updateDisplay() {
        String makhluk = MAKHLUK[currentLevel];
        statusLabel.setText("Level: " + makhluk.toUpperCase());

        try {
            // Jalur gambar dari classpath. Perlu folder 'resources/images/' di dalam src
            String imagePath = "/resources/images/" + makhluk + ".png"; 
            java.net.URL imgURL = getClass().getResource(imagePath);
            
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH); 
                imageLabel.setIcon(new ImageIcon(img));
            } else {
                imageLabel.setIcon(null); 
                imageLabel.setText("Gambar tidak ditemukan: " + makhluk + ".png");
                System.err.println("Gambar tidak ditemukan: " + imagePath);
            }
        } catch (Exception e) {
            imageLabel.setText("Error memuat gambar.");
            e.printStackTrace();
        }
    }

    private void resetGame() {
        currentLevel = 0;
        chartPanel.stopChartUpdate();
        updateDisplay();
    }
    
    private void startDelayedNextRound() {
        Timer delayTimer = new Timer(ROUND_RESET_DELAY_MS, e -> startNewRound());
        delayTimer.setRepeats(false);
        delayTimer.start();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameEvolusi());
    }
}