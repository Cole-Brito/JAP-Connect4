/**
 * Java Application Program - Connect Four
 * Authors:	Paul Squires
 * 			Cole Brito
 * Modified: Feb. 10, 2024
 */

package connectfour.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * Represents a JPanel that encapsulated a chat window, input field, and send button
 */
public class ChatBoxContentPane extends JPanel {
	
	/**
	 * Serial Version UID, used to compare class versions when deserializing
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The input field used to add text to the ChatHistoryTextPane
	 */
	public final JTextField inputTextField;
	
	/**
	 * The Text Pane used to display message history
	 * @see ChatHistoryTextPane#action(java.awt.Event, Object) Test
	 */
	public final ChatHistoryTextPane chatHistoryTextPane;
	
	//TODO: Move to a another class with static definitions for all UI elements
    /** Default background color medium-blue*/
	private static final Color uiBackgroundMed = new Color(190, 205, 230);

	/** Default background color light-blue*/
	private static final Color uiBackgroundLight = new Color(207, 226, 243);
	
	/**
	 * Creates a new ChatBoxContentPane, as well as the necessary sub-components.
	 * A ChatHistoryTextPane, JTextField, and JButton are created as a result.
	 */
	public ChatBoxContentPane() {
		super();
		LayoutManager layout = new BorderLayout();
		this.setLayout(layout);
		//this.setPreferredSize(new Dimension(220, 309));
				

		JPanel chatHistoryPanel = new JPanel();
		//chatHistoryPanel.setPreferredSize(new Dimension(218, 246));
		chatHistoryPanel.setPreferredSize(new Dimension(216, 246));
		chatHistoryPanel.setBackground(uiBackgroundLight);
		chatHistoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		chatHistoryTextPane = new ChatHistoryTextPane();
		JScrollPane scrollPane = new JScrollPane(chatHistoryTextPane);
		scrollPane.setPreferredSize(new Dimension(190, 234));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatHistoryPanel.add(scrollPane);
		//TODO: TextPane doesn't seem to use background color
		chatHistoryPanel.setBackground(uiBackgroundMed);
		add(chatHistoryPanel, BorderLayout.CENTER);
		
		
		//TODO: Move TextInput panel to a separate class, have chat and gameInfo panels controlled by one layout
		JPanel textInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
		//textInputPanel.setMinimumSize(new Dimension(218, 55));
		textInputPanel.setPreferredSize(new Dimension(216, 55));
		textInputPanel.setBackground(uiBackgroundLight);
		textInputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		//textInputPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		var inputFieldBox = Box.createHorizontalBox();
		inputTextField = new JTextField();
		//inputTextField.setMaximumSize(new Dimension(155, 20));
		inputTextField.setPreferredSize(new Dimension(155, 20));
		//inputTextField.setAlignmentX(0.5f);
		//inputTextField.setAlignmentY(CENTER_ALIGNMENT);
		inputTextField.setText("enter message...");
		inputFieldBox.add(inputTextField);
		//textPanelLayout.putConstraint(TOOL_TIP_TEXT_KEY, scrollPane, ABORT, TOOL_TIP_TEXT_KEY, textInputPanel);
		
		
		JButton sendButton = new JButton("Send");
		sendButton.setPreferredSize(new Dimension(40,20));
		sendButton.setMargin(new Insets(0,  0,  0,  0));
		sendButton.setFont(new Font("arial", Font.PLAIN, 12));
		//sendButton.setAlignmentX(0.5f);
		//sendButton.setAlignmentY(CENTER_ALIGNMENT);
		
		//TODO: Move action listeners to a Chat Controller/Manager
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chatHistoryTextPane.addText(inputTextField.getText(), 
						ChatHistoryTextPane.TextStyle.PLAYER1);
				inputTextField.setText("");
			}
		});
		
		inputFieldBox.add(sendButton);
		textInputPanel.add(inputFieldBox);
		add(textInputPanel, BorderLayout.SOUTH);		
	}
	
	
}
