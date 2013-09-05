package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

public class PublisherFrame extends JFrame {
	
	private Publisher publisher;

	private JPanel contentPane;
	private JTextField serverText;
	private JTextField portText;
	private JTextField userText;
	private JTextField roleText;
	private JTextField latText;
	private JTextField longText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublisherFrame frame = new PublisherFrame();
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
	public PublisherFrame() {
		publisher = new Publisher();
		createUI();
	}
	
	private void createUI() {
		setResizable(false);
		setTitle("Publisher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 534, 304);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel bottomPanel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
				.addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(149, Short.MAX_VALUE))
		);
		
		JLabel lblTopic = new JLabel("User");
		
		JLabel lblMessage = new JLabel("Role");
		
		userText = new JTextField();
		userText.setColumns(10);
		
		roleText = new JTextField();
		roleText.setColumns(10);
		
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String user = userText.getText();
				String role = roleText.getText();
				float lattitude = Float.parseFloat(latText.getText());
				float longitude = Float.parseFloat(longText.getText());
				publisher.publish(user, role, lattitude, longitude);
			}
		});
		
		JLabel lblLattitude = new JLabel("Lattitude");
		
		latText = new JTextField();
		latText.setColumns(10);
		
		JLabel lblLongitude = new JLabel("Longitude");
		
		longText = new JTextField();
		longText.setColumns(10);
		bottomPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("63px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("443px"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("29px"),}));
		bottomPanel.add(lblTopic, "2, 2, left, center");
		bottomPanel.add(userText, "4, 2, fill, top");
		bottomPanel.add(lblMessage, "2, 4, left, center");
		bottomPanel.add(roleText, "4, 4, fill, top");
		bottomPanel.add(sendButton, "4, 10, left, top");
		bottomPanel.add(lblLattitude, "2, 6, left, center");
		bottomPanel.add(latText, "4, 6, fill, top");
		bottomPanel.add(lblLongitude, "2, 8, left, center");
		bottomPanel.add(longText, "4, 8, fill, top");
		
		JLabel lblServer = new JLabel("Server");
		
		JLabel lblPort = new JLabel("Port");
		
		serverText = new JTextField();
		serverText.setText("localhost");
		serverText.setColumns(10);
		
		portText = new JTextField();
		portText.setText("61613");
		portText.setColumns(10);
		
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String server = serverText.getText();
				int port = Integer.parseInt(portText.getText());
				publisher.connect(server, port);
			}
		});
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblServer)
						.addComponent(lblPort))
					.addGap(18)
					.addGroup(gl_topPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(serverText, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_topPanel.createSequentialGroup()
							.addComponent(portText, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(connectButton)))
					.addContainerGap())
		);
		gl_topPanel.setVerticalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblServer)
						.addComponent(serverText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_topPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPort)
						.addComponent(portText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(connectButton))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		topPanel.setLayout(gl_topPanel);
		contentPane.setLayout(gl_contentPane);
	}
}
