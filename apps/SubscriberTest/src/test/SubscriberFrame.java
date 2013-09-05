package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class SubscriberFrame extends JFrame {
	
	private Subscriber subscriber;

	private JPanel contentPane;
	private JTextField serverText;
	private JTextField portText;
	private JTextField topicText;
	private JTextArea outputText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SubscriberFrame frame = new SubscriberFrame();
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
	public SubscriberFrame() {
		subscriber = new Subscriber(this);
		createUI();
	}

	private void createUI() {
		setTitle("Subscriber");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 287);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel topPanel = new JPanel();
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblServer = new JLabel("Server");
		
		JLabel lblPort = new JLabel("Port");
		
		JLabel lblTopic = new JLabel("Topic");
		
		serverText = new JTextField();
		serverText.setText("localhost");
		serverText.setColumns(10);
		
		portText = new JTextField();
		portText.setText("61613");
		portText.setColumns(10);
		
		topicText = new JTextField();
		topicText.setColumns(10);
		
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String server = serverText.getText();
				int port = Integer.parseInt(portText.getText());
				String topic = topicText.getText();
				
				subscriber.connect(server, port);
				subscriber.subscribe(topic);
				subscriber.start();
			}
		});
		topPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("38px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("276px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("96px"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),}));
		topPanel.add(topicText, "4, 6, fill, top");
		topPanel.add(connectButton, "6, 6, left, bottom");
		topPanel.add(lblServer, "2, 2, left, center");
		topPanel.add(lblPort, "2, 4, left, center");
		topPanel.add(lblTopic, "2, 6, left, center");
		topPanel.add(portText, "4, 4, 3, 1, fill, top");
		topPanel.add(serverText, "4, 2, 3, 1, fill, top");
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("440px"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("112px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("128px"),}));
		contentPane.add(bottomPanel, "1, 4, 2, 1, fill, fill");
		
		JScrollPane scrollPane = new JScrollPane();
		bottomPanel.add(scrollPane, "1, 1, 2, 2, fill, fill");
		
		outputText = new JTextArea();
		outputText.setWrapStyleWord(true);
		scrollPane.setViewportView(outputText);
		contentPane.add(topPanel, "1, 1, 2, 2, fill, fill");
	}
	
	public void updateOutput(String text) {
		outputText.append(text);
	}
}
