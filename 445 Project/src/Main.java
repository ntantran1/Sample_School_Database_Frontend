
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.DriverManager;

import java.sql.Connection;
/**
 * The main class that connect to database sql server
 * @author nhan
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		//The url to connect to sql server
		String url = "jdbc:mysql://localhost:3306/Tran_Nhan_db";
		String userName = "root";
		String password = "ntt582693";
		try {
			Connection connection = DriverManager.getConnection(url, userName, password);
			launch(connection);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Method that launch the UI
	 * @param connection
	 */
	private static void launch(Connection connection) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student window = new Student(connection);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}


}
