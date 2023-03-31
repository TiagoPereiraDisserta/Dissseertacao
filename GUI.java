import java.awt.*;   


public class GUI extends Frame{

	GUI(){
		TextField HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION = new TextField("HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION");
		HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION.setBounds(50, 150, 200, 30);
		add(HOST_UNDER_UTILIZATION_THRESHOLD_FOR_VM_MIGRATION);
		Button b= new Button("ok");
		b.setBounds(30,100,80,30);
		b.addActionListener(null);
		add(b);
		setSize(300,300);
		setTitle("Painel Conrtolo da imigração");
		System.out.println(". ");
		setLayout(null);
		setVisible(true);
		
		
	}
	
	public static void main(String args[]) {
		
		GUI gui= new GUI();
		 
		
	}
}
