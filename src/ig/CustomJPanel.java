package ig;

import javax.swing.JLabel;

public class CustomJPanel extends JLabel{
	
	private static final long serialVersionUID = 1L;
	private final String id;

	public CustomJPanel(String id) {
		
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
}
