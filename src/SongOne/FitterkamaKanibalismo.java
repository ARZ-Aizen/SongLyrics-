package SongOne;

import java.awt.BorderLayout;
import java.awt.Color; 
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants; 

public class FitterkamaKanibalismo implements ActionListener {
	
	
	private final long[] lyricTimings = {
			0, //
			7000, //
			10000, //
			16000, //
			23000, //
			30000, //
			35000, //
			46000, //
			51000, //
			57000, //
			63000, //
			69000, //
			74000, //
			80000,
			86000
	};
	
	private final String[] lyrics = {
			"Ibabalik kita nang buong-buo", //
			"Pangako 'yon sa 'yo", //
			"Sa 'yo lang ang puso ko", //
			"Kahit kainin mo", //
			"At hahalik ka nang may lipstick na dugo", //
			"Sa labi kong punong puno", //
			"Ng panlasa ko sa 'yo", //
			"Kanibalismo, 'di ka matiis", //
			"Kapag inalis mo, ika'y mami-miss", //
			"Di nagmamalinis", //
			"Oh, ika'y mami-miss", //
			"'Di ka matitiis",
			"Tatlo na sais",
			"Pag-ibig mong kay tamis",
			"----------------------"
	};
	
	private JLabel lyricLabel;
	private Timer timer;
	private long startTime;
	private int currentLyricIndex = 0;
	
	public FitterkamaKanibalismo () {
		
		JFrame frame = new JFrame();
		
		lyricLabel = new JLabel("Click to start....", SwingConstants.CENTER);
        
        lyricLabel.setVerticalAlignment(JLabel.TOP);
        
		lyricLabel.setFont(new Font("SansSerif", Font.BOLD, 28)); 
        lyricLabel.setForeground(Color.WHITE); 
		
		
			JPanel panel = new JPanel();
            panel.setBackground(Color.BLACK); 
            
			panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 50, 10)); 
            
			panel.setLayout(new BorderLayout());
		    panel.add(lyricLabel, BorderLayout.NORTH); 
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Pag Ibig ay kanibalismo");
		
        frame.setSize(800, 300); 
		frame.setVisible(true);
		
	timer = new Timer(100, this);
	
	frame.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
			if (!timer.isRunning()) {
				startTime = System.currentTimeMillis();
				timer.start();
			}
		}
	});
	}
 	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (currentLyricIndex < lyricTimings.length) {
			
			long elapsedTime = System.currentTimeMillis() - startTime;
			
			if (elapsedTime >= lyricTimings[currentLyricIndex]) {
				
				lyricLabel.setText(lyrics[currentLyricIndex]);
				
				currentLyricIndex++;
			}
		} else {
			timer.stop();
			lyricLabel.setText("...");
		}
	}

	
	public static void main(String[] args ) {
		
		// The required lambda arrow (->) is here
		javax.swing.SwingUtilities.invokeLater(() -> {
			new FitterkamaKanibalismo();
		});
	}
	
	
}