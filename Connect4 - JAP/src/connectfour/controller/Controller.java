package connectfour.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

	/**
	 * @param e - 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String input = e.getActionCommand();
        String[] vars = input.split(",");
        System.out.println("Cols: "+vars[0]);
        System.out.println("Rows: "+vars[1]);
		
	}

}
