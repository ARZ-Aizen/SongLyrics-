package SongOne;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class FitterkamaKanibalismo implements ActionListener {

	private final long[] lyricTimings = {
			0, 7000, 10000, 16000, 23000, 30000, 35000,
			46000, 51000, 57000, 63000, 69000, 74000, 80000, 83000
	};

	private final String[] lyrics = {
			"Ibabalik kita nang buong-buo",
			"Pangako 'yon sa 'yo",
			"Sa 'yo lang ang puso ko",
			"Kahit kainin mo",
			"At hahalik ka nang may lipstick na dugo",
			"Sa labi kong punong puno",
			"Ng panlasa ko sa 'yo",
			"Kanibalismo, 'di ka matiis",
			"Kapag inalis mo, ika'y mami-miss",
			"Di nagmamalinis",
			"Oh, ika'y mami-miss",
			"'Di ka matitiis",
			"Tatlo na sais",
			"Pag-ibig mong kay tamis",
			"♥"
	};

	private JLabel lyricLabel;
	private Timer timer;
	private Timer pulseTimer;
	private long startTime;
	private int currentLyricIndex = 0;
	private Clip audioClip;
	private boolean isPlaying = false;
	private float pulseAlpha = 1.0f;
	private boolean pulseDirection = false;

	public FitterkamaKanibalismo() {
		JFrame frame = new JFrame();
		
		// Custom gradient panel with smooth gradient
		JPanel mainPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				// Multi-stop gradient for depth
				int h = getHeight();
				GradientPaint gp = new GradientPaint(
					0, 0, new Color(15, 0, 15), 
					0, h, new Color(40, 0, 20)
				);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				
				// Add subtle vignette effect
				int vSize = 100;
				GradientPaint vignette = new GradientPaint(
					0, 0, new Color(0, 0, 0, 50),
					0, vSize, new Color(0, 0, 0, 0)
				);
				g2d.setPaint(vignette);
				g2d.fillRect(0, 0, getWidth(), vSize);
				
				GradientPaint vignetteBottom = new GradientPaint(
					0, h - vSize, new Color(0, 0, 0, 0),
					0, h, new Color(0, 0, 0, 50)
				);
				g2d.setPaint(vignetteBottom);
				g2d.fillRect(0, h - vSize, getWidth(), vSize);
			}
		};
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

		// Lyric label with glow effect
		lyricLabel = new JLabel("<html><div style='text-align: center; line-height: 1.5;'>" +
								"Click to begin...</div></html>", 
								SwingConstants.CENTER);
		lyricLabel.setFont(new Font("SansSerif", Font.PLAIN, 42));
		lyricLabel.setForeground(new Color(255, 240, 245));
		lyricLabel.setPreferredSize(new Dimension(900, 300));
		lyricLabel.setVerticalAlignment(JLabel.CENTER);
		
		mainPanel.add(lyricLabel, BorderLayout.CENTER);

		frame.add(mainPanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("♥");
		frame.setSize(1000, 450);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		timer = new Timer(100, this);
		
		// Subtle pulse effect
		pulseTimer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPlaying) {
					if (pulseDirection) {
						pulseAlpha += 0.01f;
						if (pulseAlpha >= 1.0f) {
							pulseAlpha = 1.0f;
							pulseDirection = false;
						}
					} else {
						pulseAlpha -= 0.01f;
						if (pulseAlpha <= 0.85f) {
							pulseAlpha = 0.85f;
							pulseDirection = true;
						}
					}
					int alpha = (int)(pulseAlpha * 255);
					lyricLabel.setForeground(new Color(255, 240, 245, alpha));
				}
			}
		});
		pulseTimer.start();

		frame.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (!timer.isRunning() && !isPlaying) {
					startTime = System.currentTimeMillis();
					timer.start();
					isPlaying = true;
					playAudio("D:\\Programming\\Java\\LyricsSong\\fitterkarma - Pag-Ibig ay Kanibalismo II (OFFICIAL MUKBANG VIDEO) (mp3cut.net).wav");
				}
			}
		});
		
		// Smooth cursor change on hover
		frame.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	}

	private void playAudio(String audioFilePath) {
		try {
			File audioFile = new File(audioFilePath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			audioClip = AudioSystem.getClip();
			audioClip.open(audioStream);
			audioClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.err.println("Error playing audio: " + e.getMessage());
			lyricLabel.setText("<html><div style='text-align: center; color: #ff6b6b;'>" +
							  "Could not load audio</div></html>");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (currentLyricIndex < lyricTimings.length) {
			long elapsedTime = System.currentTimeMillis() - startTime;

			if (elapsedTime >= lyricTimings[currentLyricIndex]) {
				String lyric = lyrics[currentLyricIndex];
				
				// Format lyrics with proper spacing
				lyricLabel.setText("<html><div style='text-align: center; line-height: 1.6; " +
								  "letter-spacing: 1px;'>" + lyric + "</div></html>");
				
				currentLyricIndex++;
			}
		} else {
			timer.stop();
			pulseTimer.stop();
			lyricLabel.setText("<html><div style='text-align: center; font-size: 48px;'>♥</div></html>");
			lyricLabel.setForeground(new Color(220, 20, 60));
		}
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			new FitterkamaKanibalismo();
		});
	}
}