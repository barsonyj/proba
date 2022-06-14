import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.Box;

public class Ablak extends JFrame {

	private JPanel contentPane;
	private JLabel lbPercent;
	private JLabel lbDb;
	
	private ImageIcon[] icon = new ImageIcon[16];
	private JLabel[][][] lt = new JLabel[2][4][4];
	private int[][][] t = new int[2][4][4];
	
	private boolean elso = true;
	private int ei=0, es=0, eo=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					Ablak frame = new Ablak();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ablak() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ablak.class.getResource("/icons/symmetry.png")));
		setTitle("Szimmetria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel pnLeft = new JPanel();
		pnLeft.setMinimumSize(new Dimension(420, 10));
		pnLeft.setPreferredSize(new Dimension(420, 420));
		pnLeft.setMaximumSize(new Dimension(420, 32767));
		pnLeft.setBorder(new LineBorder(Color.LIGHT_GRAY));
		pnLeft.setBackground(Color.WHITE);
		contentPane.add(pnLeft);
		pnLeft.setLayout(null);
		
		JPanel pnCenter = new JPanel();
		pnCenter.setMinimumSize(new Dimension(100, 10));
		pnCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnCenter.setPreferredSize(new Dimension(100, 10));
		pnCenter.setMaximumSize(new Dimension(100, 32767));
		contentPane.add(pnCenter);
		pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));
		
		JButton btnNewButton = new JButton("Kever");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i,s,ss,o,oo,cs; for (i=0; i<2; i++) for (s=0; s<4; s++) for (o=0; o<4; o++) {
					ss=(int)(Math.random()*4); oo=(int)(Math.random()*4);
					cs=t[i][s][o]; t[i][s][o]=t[i][ss][oo]; t[i][ss][oo]=cs;
					lt[i][ss][oo].setIcon(icon[t[i][ss][oo]]);
					lt[i][s][o].setIcon(icon[t[i][s][o]]);
				}
				status();
			}
		});
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnCenter.add(btnNewButton);
		
		Component verticalGlue = Box.createVerticalGlue();
		pnCenter.add(verticalGlue);
		
		lbDb = new JLabel("16 / 16");
		lbDb.setBorder(new EmptyBorder(10, 0, 10, 0));
		lbDb.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnCenter.add(lbDb);
		
		lbPercent = new JLabel("100%");
		lbPercent.setFont(new Font("Tahoma", Font.BOLD, 24));
		lbPercent.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnCenter.add(lbPercent);
		
		JPanel pnRight = new JPanel();
		pnRight.setMinimumSize(new Dimension(420, 10));
		pnRight.setPreferredSize(new Dimension(420, 420));
		pnRight.setMaximumSize(new Dimension(420, 32767));
		pnRight.setBorder(new LineBorder(Color.LIGHT_GRAY));
		pnRight.setBackground(Color.WHITE);
		contentPane.add(pnRight);
		pnRight.setLayout(null);
		
		for (int i=0; i<16; i++) {
			icon[i] = new ImageIcon(Ablak.class.getResource("/icons/kep"+String.format("%02d",i)+".png"));
		}
		
		int i,s,o; for (i=0; i<2; i++) for (s=0; s<4; s++) for (o=0; o<4; o++) {
			int ii=i, ss=s, oo=o;
			lt[i][s][o] = new JLabel("");
			lt[i][s][o].setOpaque(true);
			lt[i][s][o].setBackground(Color.WHITE);
			lt[i][s][o].setHorizontalAlignment(SwingConstants.CENTER);
			lt[i][s][o].setBorder(new LineBorder(Color.WHITE));
			lt[i][s][o].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					katt(ii, ss, oo);
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					lt[ii][ss][oo].setBorder(new LineBorder(Color.RED));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lt[ii][ss][oo].setBorder(new LineBorder(Color.WHITE));
				}
			});
			lt[i][s][o].setIcon(icon[s*4+o]); t[i][s][o]=s*4+o;
			if (i==0) {
				lt[i][s][o].setBounds(10+o*100, 10+s*100, 100, 100);
				pnLeft.add(lt[i][s][o]);
			} else {
				lt[i][s][o].setBounds(10+(3-o)*100, 10+s*100, 100, 100);
				pnRight.add(lt[i][s][o]);
			}
		}
		
		pack();
	}
	
	private void katt(int i, int s, int o) {
		if (elso) {
			lt[i][s][o].setBackground(Color.GREEN);
			ei=i; es=s; eo=o; elso=false;			
		} else {
			lt[ei][es][eo].setBackground(Color.WHITE);
			if (i==ei) {
				int cs=t[i][s][o]; t[i][s][o]=t[ei][es][eo]; t[ei][es][eo]=cs;
				lt[ei][es][eo].setIcon(icon[t[ei][es][eo]]);
				lt[i][s][o].setIcon(icon[t[i][s][o]]);
				elso=true;
				status();
			} else {
				lt[i][s][o].setBackground(Color.GREEN);
				ei=i; es=s; eo=o; elso=false;
			}
		}
	}
	
	private void status() {
		int db = 0;
		int s,o; for (s=0; s<4; s++) for (o=0; o<4; o++) {
			if (t[0][s][o]==t[1][s][o]) db++;
		}
		lbDb.setText(db+" / "+16);
		lbPercent.setText(db*100/16+"%");
	}
}
