
import javax.swing.JFrame;

import javax.swing.JTextField;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JInternalFrame;

/**
 * A class that create the UI for Student App
 * @author nhan
 *
 */
public class Student {
	/**Temporary variables to store student Info*/
	private static String studentID;
	private static String fName;
	private static String lName;
	private static String month;
	private static String day;
	private static String year;
	private static String phone;
	private static String email;
	
	private static boolean value = true;
	
	
	static Connection connection;
	
	/** The UI Variable For registration */
	JFrame frame;
	private JTextField firstName_text;
	private JTextField lastName_text;
	private JTextField DOB_Month_text;
	private JTextField DOB_Day_text;
	private JTextField DOB_Year_text;
	private JLabel lblNewLabel_4;
	private JTextField phone_text;
	private JTextField email_text;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JTextField studentID_text;
	private JLabel lblStudentId;
	private JButton findStudent;
	private JTextField findStudentID_text;
	private JLabel lblNewLabel_7;
	private JTextField studentEnroll_text;
	private JTextField courseID_text;
	private JInternalFrame internalFrame_2;
	private JTextField stuIDPark_text;
	private JTextField parkingID_text;
	private JButton btnRefundPass;



	/**
	 * Create the application.
	 */
	public Student(Connection c) {
		connection = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("The Student App");
		frame.setBounds(100, 100, 1107, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/** Internal Frame for student Info*/
		JInternalFrame internalFrame = new JInternalFrame("Student Info");
		//A loop to make the frame unmovable
		internalFrame.setResizable(false);
		
		//Set Location for the internam Frame
		internalFrame.setBounds(211, 11, 282, 141);
		frame.getContentPane().add(internalFrame);
		internalFrame.getContentPane().setLayout(null);
		
		lblNewLabel_7 = new JLabel("Enter Student ID Below");        //Label for the text box
		lblNewLabel_7.setBounds(77, 11, 117, 23);
		internalFrame.getContentPane().add(lblNewLabel_7);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		findStudentID_text = new JTextField();
		findStudentID_text.setBounds(77, 29, 96, 20);
		internalFrame.getContentPane().add(findStudentID_text);
		findStudentID_text.setColumns(10);
		
		findStudent = new JButton("Find Student InFo  ");
		findStudent.setBounds(10, 63, 120, 23);
		internalFrame.getContentPane().add(findStudent);
		findStudent.setHorizontalAlignment(SwingConstants.TRAILING);
		findStudent.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		JButton btnCourseRegitered = new JButton("Grade Report");
		btnCourseRegitered.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCourseRegitered.setBounds(140, 60, 120, 23);
		internalFrame.getContentPane().add(btnCourseRegitered);
		

		
		//Register button
		btnCourseRegitered.addActionListener(new ActionListener() {
			/** Action Listenter for Register Button */
			public void actionPerformed(ActionEvent e) {
				double temp;
				double gpa = 0.0;
				//SQL Statement to get the info from the Enrollment table in database
				String query = "SELECT * FROM Enrollment WHERE Enrollment.studentID = "
						+ findStudentID_text.getText();
				try {
					//Statement to excute the sql statement
					Statement statement = connection.createStatement();
					//Result Set after the statement executed
					ResultSet rs = statement.executeQuery(query);
					
					//String builder to print out the information
					StringBuilder sb = new StringBuilder("My course:");
					boolean v = false;
					int count = 0;
					
					//A loop that loop through the result set of the Enrollment Info
					while(rs.next()) {
						temp = rs.getDouble("grade");
						gpa += temp;
						count++;
						sb.append("\n(");
						sb.append("Course ID: " + rs.getInt("courseID") + ", ");
						sb.append("GPA: " + temp);
						sb.append(")");
						v = true;
					}
					
					gpa = Math.round((gpa / count) * 100.0) / 100.0;
					sb.append("\nAverage GPA: " + gpa);
					
					//If statement to check if there any info in the Enrollment Table
					if(v) {
						JOptionPane.showMessageDialog(null, sb.toString());
					} else {
						JOptionPane.showMessageDialog(null, "Student ID does not exist"
								+ "\n          or"
								+ "\nStudent have not been taking any course yet"
								+ "\n\n** Click on \"Find Student Info\" to check if student exist");
						
					}
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Student ID does not exist");
				}
			}
		});
		
		//The button to get the student info and check if student is exist in the system or not
		findStudent.addActionListener(new ActionListener() {
			/** Action Listenter that execute sql statemtn to retrieve info from Student Table*/
			public void actionPerformed(ActionEvent e) {
				String tempID = findStudentID_text.getText();
				
				//Sql Statement
				String query = "SELECT * FROM Student, StudentEmail, "
						+ "StudentPhone WHERE Student.studentID"
						+ " = " + tempID
						+ " AND StudentEmail.studentID = " + tempID
						+ " AND StudentPhone.studentID = " + tempID;
				
				
				try {
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(query);
					boolean t = rs.next();
					//If statement to check if the student exist or not
					if(!t) {	
						JOptionPane.showMessageDialog(null, "Student ID does not exist");
					}
					
					//A Loop that loop through the result set of the Student Table
					while(t) {
						JOptionPane.showMessageDialog(null, "First Name: " +
										rs.getString("firstName")
										+ "\nLast Name: "
										+ rs.getString("lastName")
										+ "\nBirthDate: "
										+ rs.getInt("dobMonth") + "-"
										+ rs.getInt("dobDay") + "-" + rs.getString("dobYear")
						                + "\nEmail: " + rs.getString("email")
						                + "\nPhone: " + rs.getString("phoneNo"));
						t = false;
					}
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Student ID does not exist");
					
				
				}
			}
		});
		
		//Internal Frame For Enrollment 
		JInternalFrame internalFrame_1 = new JInternalFrame("Enrollment");
		//Loop to make the internal unmovable
//		for(MouseListener listener : ((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrame_1.getUI()).getNorthPane().getMouseListeners()){
//			((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrame_1.getUI()).getNorthPane().removeMouseListener(listener);
//			}
		
		internalFrame_1.setBounds(207, 164, 300, 218);
		frame.getContentPane().add(internalFrame_1);
		internalFrame_1.getContentPane().setLayout(null);
		
		//Button to check for all the course that are available
		JButton btnAvalaibleCourses = new JButton("Avalaible Courses");
		btnAvalaibleCourses.setBounds(74, 144, 119, 23);
		internalFrame_1.getContentPane().add(btnAvalaibleCourses);
		btnAvalaibleCourses.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		//Button to enroll in a course
		JButton btnEnroll = new JButton("Enroll");
		btnEnroll.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEnroll.setBounds(50, 110, 89, 23);
		internalFrame_1.getContentPane().add(btnEnroll);
		
		//texfield student ID in enrollment
		studentEnroll_text = new JTextField();
		studentEnroll_text.setBounds(74, 37, 119, 20);
		internalFrame_1.getContentPane().add(studentEnroll_text);
		studentEnroll_text.setColumns(10);
		
		JLabel labelstudentid = new JLabel("Course ID");
		labelstudentid.setBounds(103, 65, 67, 14);
		internalFrame_1.getContentPane().add(labelstudentid);
		
		//Texfield for courseID in Enrollment
		courseID_text = new JTextField();
		courseID_text.setBounds(74, 79, 119, 20);
		internalFrame_1.getContentPane().add(courseID_text);
		courseID_text.setColumns(10);
		
		JLabel lblCourseId = new JLabel("Student ID");
		lblCourseId.setBounds(103, 23, 66, 14);
		internalFrame_1.getContentPane().add(lblCourseId);
		
		//Buttoon to drop a class
		JButton btnNewButton = new JButton("Drop");
		btnNewButton.addActionListener(new ActionListener() {
			/**Action Listener function to excute a query to drop the course from enrollment*/
			public void actionPerformed(ActionEvent e) {
				//The sql statement to delete a course from enrollment table
				String query = "DELETE FROM Enrollment WHERE "
						+ "Enrollment.studentID = " + studentEnroll_text.getText()
						+ " AND Enrollment.courseID = " + courseID_text.getText();

				try {
					Statement statement = connection.createStatement();
					int row = statement.executeUpdate(query);
					//If statement to check if statement gas been execute or not
					if (row > 0) {
						JOptionPane.showMessageDialog(null, "Course ID: " 
								+ courseID_text.getText() + " has been drop!");

					} else {
						JOptionPane.showMessageDialog(null, "Drop Fail! \nInvalid Course ID or Student ID"
								+ "\n     or\nCourse have not beenn enroll by the student yet"
								+ "\n\n** You can check \"Grade Report\" in Student Info"
								+ "\nto see what class you have been enroll in");
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Drop Fail! \nInvalid Course ID or Student ID"
							+ "\n     or\nCourse have not beenn enroll by the student yet"
							+ "\n\n** You can check \"Grade Report\" in Student Info"
							+ "\nto see what class you have been enroll in");
							
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(145, 110, 89, 23);
		internalFrame_1.getContentPane().add(btnNewButton);
		
		//Internal Frame for Registration
		internalFrame_2 = new JInternalFrame("Registration");
		//A loop that disable all the mouse listener for the Registration frame
//		for(MouseListener listener : ((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrame_2.getUI()).getNorthPane().getMouseListeners()){
//			((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrame_2.getUI()).getNorthPane().removeMouseListener(listener);
//			}
		internalFrame_2.setBounds(0, 4, 214, 378);
		frame.getContentPane().add(internalFrame_2);
		internalFrame_2.getContentPane().setLayout(null);
		
		//Button to register the student info to Student Table
		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRegister.setBounds(10, 306, 89, 23);
		internalFrame_2.getContentPane().add(btnRegister);
		
		//Text Field for email
		email_text = new JTextField();
		email_text.setBounds(85, 243, 96, 20);
		internalFrame_2.getContentPane().add(email_text);
		

		email_text.setEditable(false);
		email_text.setColumns(10);
		
		lblNewLabel_6 = new JLabel("Email");
		lblNewLabel_6.setBounds(10, 246, 67, 14);
		internalFrame_2.getContentPane().add(lblNewLabel_6);
		
		//TextField for phone number
		phone_text = new JTextField();
		phone_text.setBounds(85, 212, 96, 20);
		internalFrame_2.getContentPane().add(phone_text);
		

		phone_text.setEditable(false);
		phone_text.setColumns(10);
		
		//TextField for DOB Year
		DOB_Year_text = new JTextField();
		DOB_Year_text.setBounds(85, 181, 96, 20);
		internalFrame_2.getContentPane().add(DOB_Year_text);
				
				//TextField for Year
				DOB_Year_text.setEditable(false);
				DOB_Year_text.setColumns(10);
				
				//TextField for Day
				DOB_Day_text = new JTextField();
				DOB_Day_text.setBounds(85, 150, 96, 20);
				internalFrame_2.getContentPane().add(DOB_Day_text);
				
						DOB_Day_text.setEditable(false);
						DOB_Day_text.setColumns(10);
						
						DOB_Month_text = new JTextField();
						DOB_Month_text.setBounds(85, 119, 96, 20);
						internalFrame_2.getContentPane().add(DOB_Month_text);
								
								//TextField For DOB month
								DOB_Month_text.setEditable(false);
								DOB_Month_text.setColumns(10);
								
								lastName_text = new JTextField();
								lastName_text.setBounds(85, 88, 96, 20);
								internalFrame_2.getContentPane().add(lastName_text);
								
										lastName_text.setEditable(false);
										lastName_text.setColumns(10);
										
										firstName_text = new JTextField();
										firstName_text.setBounds(85, 57, 96, 20);
										internalFrame_2.getContentPane().add(firstName_text);
										
										//Action Performed for first name textfield
										firstName_text.addActionListener(new ActionListener() {
											/** An action perform that get the text from firstname 
											 * textfield and set it to temp first name variable
											 * */
											public void actionPerformed(ActionEvent e) {
												//An if statement to check there textfield is empty or not
												if(!firstName_text.getText().isBlank()) {
													fName = firstName_text.getText();
													lastName_text.setEditable(true);
													lastName_text.requestFocus(); 
												} else {
													JOptionPane.showMessageDialog(null, "Must Enter First Name");
												}
											}
										});
										firstName_text.setEditable(false);
										firstName_text.setColumns(10);
										
										studentID_text = new JTextField();
										studentID_text.setBounds(85, 26, 96, 20);
										internalFrame_2.getContentPane().add(studentID_text);
										
	
										studentID_text.setEditable(false);
										studentID_text.setColumns(10);
										
										lblNewLabel_5 = new JLabel("Phone #");
										lblNewLabel_5.setBounds(10, 215, 67, 14);
										internalFrame_2.getContentPane().add(lblNewLabel_5);
										
										lblNewLabel_4 = new JLabel("DOB Year");
										lblNewLabel_4.setBounds(10, 184, 67, 14);
										internalFrame_2.getContentPane().add(lblNewLabel_4);
										
										JLabel lblNewLabel_3 = new JLabel("DOB Day");
										lblNewLabel_3.setBounds(10, 153, 67, 14);
										internalFrame_2.getContentPane().add(lblNewLabel_3);
										
										JLabel lblNewLabel_2 = new JLabel("DOB Month");
										lblNewLabel_2.setBounds(10, 122, 67, 14);
										internalFrame_2.getContentPane().add(lblNewLabel_2);
										
										JLabel lblNewLabel_1 = new JLabel("Last Name");
										lblNewLabel_1.setBounds(10, 91, 67, 14);
										internalFrame_2.getContentPane().add(lblNewLabel_1);
										
										JLabel lblNewLabel = new JLabel("First Name");
										lblNewLabel.setBounds(10, 60, 67, 14);
										internalFrame_2.getContentPane().add(lblNewLabel);
										
										lblStudentId = new JLabel("Student ID");
										lblStudentId.setBounds(10, 29, 67, 14);
										internalFrame_2.getContentPane().add(lblStudentId);
										//Cancel Button to cancel and discard for the registration info
										JButton btnCancel = new JButton("Cancel");
										btnCancel.setFont(new Font("Tahoma", Font.BOLD, 11));
										//Add action Listener for the cancel button
										btnCancel.addActionListener(new ActionListener() {
											/**Action listener to set the registration textfiled to blank and disable them */
											public void actionPerformed(ActionEvent e) {
												studentID_text.setEditable(false);
												studentID_text.setText("");
												
												firstName_text.setEditable(false);
												firstName_text.setText("");
												
												lastName_text.setEditable(false);
												lastName_text.setText("");
												
												DOB_Month_text.setEditable(false);
												DOB_Month_text.setText("");
												
												DOB_Day_text.setEditable(false);
												DOB_Day_text.setText("");
												
												DOB_Year_text.setEditable(false);
												DOB_Year_text.setText("");
												
												phone_text.setEditable(false);
												phone_text.setText("");
												
												email_text.setEditable(false);
												email_text.setText("");
												
												value = true;
												
												btnRegister.setText("Register");
											}
										});
										btnCancel.setBounds(92, 306, 89, 23);
										internalFrame_2.getContentPane().add(btnCancel);
										
										//Internal Frame for Parking Pass
										JInternalFrame internalFrame_3 = new JInternalFrame("Parking Pass");
										//A loop to make the frame unmovale
//										for(MouseListener listener : ((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrame_3.getUI()).getNorthPane().getMouseListeners()){
//											((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrame_3.getUI()).getNorthPane().removeMouseListener(listener);
//										}
										internalFrame_3.setBounds(675, 0, 282, 163);
										frame.getContentPane().add(internalFrame_3);
										internalFrame_3.getContentPane().setLayout(null);
										
										//Student ID textfield for Parking Pass function
										stuIDPark_text = new JTextField();
										stuIDPark_text.setBounds(10, 11, 96, 20);
										internalFrame_3.getContentPane().add(stuIDPark_text);
										stuIDPark_text.setColumns(10);
										
										//Parking Id textfield
										parkingID_text = new JTextField();
										parkingID_text.setBounds(125, 11, 96, 20);
										internalFrame_3.getContentPane().add(parkingID_text);
										parkingID_text.setColumns(10);
										
										JLabel lblStudentId_1 = new JLabel("Student ID");
										lblStudentId_1.setBounds(20, 37, 67, 14);
										internalFrame_3.getContentPane().add(lblStudentId_1);
										
										JLabel lblParkingId = new JLabel("Parking ID");
										lblParkingId.setBounds(135, 37, 65, 14);
										internalFrame_3.getContentPane().add(lblParkingId);
										
										//A button to perform the parking purchase
										JButton btnPurchase = new JButton("Purchase");
										//Action Listener to purchase the parking pass
										btnPurchase.addActionListener(new ActionListener() {
											/** Function to excute the update query for parking pass */
											public void actionPerformed(ActionEvent e) {
												//Sql Statement to update the ParkingPass Table
												String query = "UPDATE ParkingPass SET " + 
														"ParkingPass.studentID = " + stuIDPark_text.getText() +
														" WHERE ParkingID = " + parkingID_text.getText()
														+ " AND ParkingPass.studentID IS NUll";
												
												try {
						
													Statement statement = connection.createStatement();
													int row = statement.executeUpdate(query);
													//If statement to check there are any updates to the table
													if (row > 0) {
														JOptionPane.showMessageDialog(null, "Purchase Successfully!");
		
													} else {
														JOptionPane.showMessageDialog(null, "Parking Pass is not available");
													}
				
													
												} catch (SQLException e1) {
													System.out.println(e1);
													JOptionPane.showMessageDialog(null, "Student ID does not exist"
															+ "\n              or"
															+ "\nInvalid Parking ID");
												
												}
											}
										});
										btnPurchase.setBounds(10, 62, 89, 23);
										internalFrame_3.getContentPane().add(btnPurchase);
										
										//A button to check what are the available parking pass for purchase
										JButton btnPassAvalaible = new JButton("Pass Avalaible");
										btnPassAvalaible.setFont(new Font("Tahoma", Font.BOLD, 10));
										//Listener for Pass Available button
										btnPassAvalaible.addActionListener(new ActionListener() {
											/**Function to get all the avalable parking pass from the parking pass table*/
											public void actionPerformed(ActionEvent e) {
												//SQL statement to get the info from Parking Pass Table
												String query = "SELECT * FROM ParkingPass " + 
														"Where ParkingPass.studentID IS NULL";
												//String builder to print out all the info
												StringBuilder sb = new StringBuilder("Avalaible Parking:\n"
														+ "(Parking ID, Location, School Year, Quarter, Price)");
												try {
													Statement statement = connection.createStatement();
													ResultSet rs = statement.executeQuery(query);
													//A loop that loop the result set after statement has been executed
													while(rs.next()) {
														sb.append("\n(");
														sb.append(rs.getInt("parkingID") + ", ");
														sb.append(rs.getString("parkingLocation") + ", ");
														sb.append(rs.getInt("schoolYear") + ", ");
														sb.append(rs.getString("quarters") + ", ");
														sb.append(rs.getDouble("price"));
														sb.append(")");
													}
												
													JOptionPane.showMessageDialog(null, sb.toString());
		
												} catch (SQLException e1) {
		
												}
												
											}
										});
										btnPassAvalaible.setBounds(109, 62, 121, 23);
				
										internalFrame_3.getContentPane().add(btnPassAvalaible);
										//Button that to check the parking pass has been purchase for specific student
										JButton btnMyParking = new JButton("My Parking");
										//Action Listener to get the info from parking pass table to see if parking has been purchase
										btnMyParking.addActionListener(new ActionListener() {
											/** Function to excute the sql statement to retrieve info from Parking Pass table*/
											public void actionPerformed(ActionEvent e) {
												//SQL Statement
												String query = "SELECT * FROM ParkingPass "
														+ "WHERE ParkingPass.studentID = "
														+ stuIDPark_text.getText();
												//String builder to keep track of the info of parking pass
												StringBuilder sb = new StringBuilder("Student ID: " + stuIDPark_text.getText() 
														+ "\nMy Parking:\n"
														+ "(Parking ID, Location, School Year, Quarter, Price)");
												try {
													Statement statement = connection.createStatement();
													ResultSet rs = statement.executeQuery(query);
													boolean v = false;
													//A while loop the loop the result set of the execute sql stament
													while(rs.next()) {
														sb.append("\n(");
														sb.append(rs.getInt("parkingID") + ", ");
														sb.append(rs.getString("parkingLocation") + ", ");
														sb.append(rs.getInt("schoolYear") + ", ");
														sb.append(rs.getString("quarters") + ", ");
														sb.append(rs.getDouble("price"));
														sb.append(")");
														v = true;
													}
													//An if statement to check there are any rows effect by the executed statement
													if(v) {
														rs.next();
			
														JOptionPane.showMessageDialog(null, sb.toString());
													} else {
														JOptionPane.showMessageDialog(null, "Student ID does not exist"
																+ "\n                 or "
																+ "\nThe student have not purchase any parking yet");
														
													}
												} catch (SQLException e1) {
													JOptionPane.showMessageDialog(null, "Student ID does not exist");
												}
												
											}
										});
										btnMyParking.setBounds(10, 96, 105, 23);
										internalFrame_3.getContentPane().add(btnMyParking);
										//A button that refund the parking pass or cancel the pass
										btnRefundPass = new JButton("Refund Pass");
										//Listner that execute the update sql statement for the Parking Pass table
										btnRefundPass.addActionListener(new ActionListener() {
											/** A function that execute the sql stament to update the refund for parking pass*/
											public void actionPerformed(ActionEvent e) {
												//* SQL Statement
												String query = "UPDATE ParkingPass SET " + 
														"ParkingPass.studentID = " + "NULL " +
														" WHERE ParkingID = " + parkingID_text.getText()
														+ " AND ParkingPass.studentID = " + stuIDPark_text.getText();
												
												try {
													Statement statement = connection.createStatement();
													int row = statement.executeUpdate(query);
													//An if statement to check if there are anyrow effected by the execution of the execute statement
													if (row > 0) {
														JOptionPane.showMessageDialog(null, "Refund Successfully!");
		
													} else {
														JOptionPane.showMessageDialog(null, "Refund Fail!"
																+ "\nParking Pass is not available"
																+ "\n               or"
																+ "\nStudent ID does not exist"
																+ "\n               or"
																+ "\nStudent have not purchase this parking yet");
													}
				
													
												} catch (SQLException e1) {
													JOptionPane.showMessageDialog(null, "Student ID does not exist");
												
												}
											}
										});
										btnRefundPass.setBounds(119, 96, 111, 23);
										internalFrame_3.getContentPane().add(btnRefundPass);
										
										//The internal fram for analitycal information
										JInternalFrame internalFrame_4 = new JInternalFrame("Analytical Info");
										//A loop to disable all the mouse listener for the internal frame
//										for(MouseListener listener : ((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrame_4.getUI()).getNorthPane().getMouseListeners()){
//											((javax.swing.plaf.basic.BasicInternalFrameUI) internalFrame_4.getUI()).getNorthPane().removeMouseListener(listener);
//										}
										internalFrame_4.setBounds(517, 164, 571, 218);
										frame.getContentPane().add(internalFrame_4);
										internalFrame_4.getContentPane().setLayout(null);
										//Button to get the analytical info for the average parking price for spring quarter
										JButton btnNewButton_1 = new JButton("Find the average Parking Price for spring quarter 2020");
										//Listener to exectute the sql statement to find the average price for parking pass spring 2020
										btnNewButton_1.addActionListener(new ActionListener() {
											/** Function to execute the sql statement retrieve the info from parking pass table*/
											public void actionPerformed(ActionEvent e) {
												String query = "SELECT AVG(price) Average_Parking_Price_For_Spring_2020\r\n" + 
														"FROM ParkingPass\r\n" + 
														"WHERE ParkingPass.quarters = 'spring' AND ParkingPass.schoolYear = '2020'";
												try {
													Statement statement = connection.createStatement();
													ResultSet rs = statement.executeQuery(query);
													//If statement to check if the info is avalaible in the parking pass table
													if(rs.next()) {
														JOptionPane.showMessageDialog(null, "Average Parking Price For Spring 2020:"
																+ "\n                    $" 
																+ rs.getDouble("Average_Parking_Price_For_Spring_2020"));
													}else {
														JOptionPane.showMessageDialog(null, "There is no parking pass for Spring 2020");
														
													}
												} catch (SQLException e1) {
													JOptionPane.showMessageDialog(null, "There is no parking pass for Spring 2020");
												}
											}
										});
										btnNewButton_1.setBounds(0, 11, 564, 23);
										internalFrame_4.getContentPane().add(btnNewButton_1);
										
										//The button to find the course with highest average gpa
										JButton btnNewButton_2 = new JButton("Find the course with highest Average GPA");
										btnNewButton_2.addActionListener(new ActionListener() {
											//Function to acquire the info for the highest average gpa course
											public void actionPerformed(ActionEvent e) {
												//sql statement
												String query = "SELECT courseID, AVG(grade) Highest_Average_Grade\r\n" + 
														"FROM Enrollment\r\n" + 
														"GROUP BY courseID\r\n" + 
														"ORDER BY AVG(grade) desc";
												try {
													Statement statement = connection.createStatement();
													ResultSet rs = statement.executeQuery(query);
													//If statement to check if the info is avalaible
													if(rs.next()) {
														JOptionPane.showMessageDialog(null, "The course with highest Average GPA:"
																+ "\nCourse ID: " + rs.getInt("courseID")
																+ "\nHighest Average Grade: " 
																+ rs.getDouble("Highest_Average_Grade"));
							
													}
												} catch (SQLException e1) {
													e1.printStackTrace();
													JOptionPane.showMessageDialog(null, "The Info is not available at the moment");
												}
											}
										});
										btnNewButton_2.setBounds(0, 45, 564, 23);
										internalFrame_4.getContentPane().add(btnNewButton_2);
										
										//The button has the function to find the most popular course
										JButton btnFindMostPopular = new JButton("Find most popular Course(course taken by many student)");
										btnFindMostPopular.addActionListener(new ActionListener() {
											/** Function to execute the query get the most popular course*/
											public void actionPerformed(ActionEvent e) {
												//SQL Statement
												String query = "SELECT courseID Most_Taken_CourseID, COUNT(courseID) Number_Of_Enrollments\r\n" + 
														"								  FROM Enrollment\r\n" + 
														"								  GROUP BY courseID\r\n" + 
														"								  ORDER BY COUNT(courseID) desc";
												try {
													Statement statement = connection.createStatement();
													ResultSet rs = statement.executeQuery(query);
													//An if statement to check if the requested info is avalaible
													if(rs.next()) {
														JOptionPane.showMessageDialog(null, "Most Popular Course:"
																+ "\nCourse ID: " + rs.getInt("Most_Taken_CourseID")
																+ "\nNumber of enrollments: " 
																+ rs.getInt("Number_Of_Enrollments"));
							
													}
												} catch (SQLException e1) {
													JOptionPane.showMessageDialog(null, "The Info is not available at the moment");
												}
											}
										});
										btnFindMostPopular.setBounds(0, 79, 564, 23);
										internalFrame_4.getContentPane().add(btnFindMostPopular);
										//A button that has the function to find the number of student who have average gpa higher than 3.0
										JButton btnNewButton_3 = new JButton("Find the number of student who have the average gpa higher than 3.0");
										btnNewButton_3.addActionListener(new ActionListener() {
											/** Function to execute the sql statement to get the info from Enrollment table*/
											public void actionPerformed(ActionEvent e) {
												String query = "SELECT COUNT(Highest_Average_Grade) Number_Of_Student_With_GPA_Higher_Than_Three_Point_ZERO \r\n" + 
														"FROM (SELECT AVG(grade) Highest_Average_Grade\r\n" + 
														"FROM Enrollment\r\n" + 
														"GROUP BY studentID\r\n" + 
														"HAVING AVG(grade) > 3.0) AS asfd";
												try {
													Statement statement = connection.createStatement();
													ResultSet rs = statement.executeQuery(query);
													//An if statement to check if the info is avalaible
													if(rs.next()) {
														JOptionPane.showMessageDialog(null, "There are " 
																	+ rs.getInt("Number_Of_Student_With_GPA_Higher_Than_Three_Point_ZERO")
																	+" students who have average gpa higher than 3.0");
							
													}
												} catch (SQLException e1) {
													JOptionPane.showMessageDialog(null, "The Info is not available at the moment");
												}
											}
										});
										btnNewButton_3.setBounds(0, 113, 564, 23);
										internalFrame_4.getContentPane().add(btnNewButton_3);
										//Button that has the function to find number of parking pass that the price lower 200 dollar
										// and avalaible for purchase
										JButton btnNewButton_4 = new JButton("Find number of Parking Pass that have the price lower than 200 and available for purchase");
										btnNewButton_4.addActionListener(new ActionListener() {
											/**Functions to execute the sql statement to find requested info*/
											public void actionPerformed(ActionEvent e) {
												//SQL Statement
												String query = "SELECT COUNT(parkingID) Number_Of_Available_Parking_Pass_Under_Two_Hundreds_Dollars\r\n" + 
														"FROM ParkingPass\r\n" + 
														"WHERE ParkingPass.price < 200.00 AND ParkingPass.studentID is NULL";
												try {
								
													Statement statement = connection.createStatement();
													ResultSet rs = statement.executeQuery(query);
													// A if statement to check if the requested info is available
													if(rs.next()) {
														JOptionPane.showMessageDialog(null, "There are " 
																	+ rs.getInt("Number_Of_Available_Parking_Pass_Under_Two_Hundreds_Dollars")
																	+" parking passes under 200 dollars that are avalaible for purchase");
							
													}
												} catch (SQLException e1) {
													JOptionPane.showMessageDialog(null, "The Info is not available at the moment");
												}
											}
										});
										btnNewButton_4.setBounds(0, 146, 564, 23);
										internalFrame_4.getContentPane().add(btnNewButton_4);
										internalFrame_4.setVisible(true);
										internalFrame_3.setVisible(true);
		//Register Button to insert the student info the student table
		btnRegister.addActionListener(new ActionListener() {
			/** Function that enter the student info*/
			public void actionPerformed(ActionEvent e) {
				//If statment to check the button has press or not
				if(value) {
					studentID_text.setEditable(true);				
					firstName_text.setEditable(true);
					lastName_text.setEditable(true);
					DOB_Month_text.setEditable(true);
					DOB_Day_text.setEditable(true);
					DOB_Year_text.setEditable(true);
					phone_text.setEditable(true);
					email_text.setEditable(true);
					studentID_text.setEditable(true);
					btnRegister.setText("Submit");
					value = false;
				} else {
					if(!firstName_text.getText().isBlank()) {
						fName = firstName_text.getText();			 
					} else {
						value = true;
						JOptionPane.showMessageDialog(null, "Must Enter First Name");
					}
					
					if(!studentID_text.getText().isBlank()) {
						studentID = studentID_text.getText(); 
					} else {
						value = true;
						JOptionPane.showMessageDialog(null, "Must Enter Student ID");
					}
					
					if(!lastName_text.getText().isBlank()) {
						lName = lastName_text.getText();
					} else {
						value = true;
						JOptionPane.showMessageDialog(null, "Must Enter Last Name");
					}
					
					if(!DOB_Month_text.getText().isBlank()) {
						month = DOB_Month_text.getText();

					} else {
						value = true;
						JOptionPane.showMessageDialog(null, "Must Enter DOB Month");
					}
					
					if(!DOB_Day_text.getText().isBlank()) {
						day = DOB_Day_text.getText();
					} else {
						value = true;
						JOptionPane.showMessageDialog(null, "Must Enter DOB Day");
					}
					if(!DOB_Year_text.getText().isBlank()) {
						year = DOB_Year_text.getText();
					} else {
						value = true;
						JOptionPane.showMessageDialog(null, "Must Enter DOB Year");
					}
					
					if(!phone_text.getText().isBlank()) {
						phone = phone_text.getText();
					} else {
						value = true;
						JOptionPane.showMessageDialog(null, "Must Enter Phone Number");
					}
					if(!email_text.getText().isBlank()) {
						email = email_text.getText();
					} else {
						value = true;
						JOptionPane.showMessageDialog(null, "Must Enter Email");
					}
					if(!value) {
						
						submit(btnRegister);
					}
					//If statement to check if the button has been press to disable the textfield
					if(!value) {
						studentID_text.setEditable(false);
						studentID_text.setText("");
						
						firstName_text.setEditable(false);
						firstName_text.setText("");
						
						lastName_text.setEditable(false);
						lastName_text.setText("");
						
						DOB_Month_text.setEditable(false);
						DOB_Month_text.setText("");
						
						DOB_Day_text.setEditable(false);
						DOB_Day_text.setText("");
						
						DOB_Year_text.setEditable(false);
						DOB_Year_text.setText("");
						
						phone_text.setEditable(false);
						phone_text.setText("");
						
						email_text.setEditable(false);
						email_text.setText("");
						
						value = true;
						
						btnRegister.setText("Register");
						return;
					}
				}
				value = false;
			}
		});
		internalFrame_2.setVisible(true);
		//Button to enroll in a course
		btnEnroll.addActionListener(new ActionListener() {
			/** Function to enroll the course */
			public void actionPerformed(ActionEvent e) {
				//Calling the enroll function
				enroll(studentEnroll_text.getText(), courseID_text.getText(), grade(),
						btnEnroll);
				//Set the textfield to blank
				studentEnroll_text.setText("");
				courseID_text.setText("");
					
					
					
				
			}
		});
		//Button to check the avalaible course for register
		btnAvalaibleCourses.addActionListener(new ActionListener() {
			/** Function to execute the sql statement for Course table */
			public void actionPerformed(ActionEvent e) {
				//SQL statement
				String queryCourse = "SELECT * FROM Course";
				try {
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(queryCourse);
					//String Builder to keep track the info for the Course table
					StringBuilder sb = new StringBuilder("(Course ID, Course Name, Credits)");
					//Loop that loop through the result set after the statement execute
					while(rs.next()) {
						sb.append("\n(");
						sb.append(rs.getInt("courseID") + ", ");
						sb.append(rs.getString("title") + ", ");
						sb.append(rs.getInt("credit"));
						sb.append(")");
					}
					
					JOptionPane.showMessageDialog(null, sb.toString());
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Student ID does not exist");
					
				
				}
				
			}
		});
		internalFrame_1.setVisible(true);
		internalFrame.setVisible(true);
	}
	
	/** A function that submit all the student info to the Student Table*/
	private static void submit(JButton btnRegister) {
		//Sql statement
		String sqlStudent = "INSERT INTO Student VALUES "
				+ "(" + studentID + ", " + "\'" 
				+ lName + "\'" + ", " + "\'" + fName + "\'" + ", " + month + ", "
				+ day + ", " + year + ")";
		String sqlStudentEmail = "INSERT INTO StudentEmail VALUES "
				+ "(" + studentID + ", " + "\'" 
				+ email + "\'" + ")";
		String sqlStudentPhone = "INSERT INTO StudentPhone VALUES "
				+ "(" + studentID + ", " + "\'" 
				+ phone + "\'" + ")";
		
		try {
			Statement statement = connection.createStatement();
			statement.execute(sqlStudent);
			statement.execute(sqlStudentPhone);
			statement.execute(sqlStudentEmail);
			JOptionPane.showMessageDialog(null, "Register Successfully");
			
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, "Register Failed!"
					+ "\nMake Sure:"
					+ "\n-Student ID must be Integer"
					+ "\n-DOB Month must be at most 2 digits"
					+ "\n-DOB Day must be numbers at most 2 digits"
					+ "\n-DOB Year must be numbers at most 2 digits"
					+ "\n-Phone # must be numbers at most 10 digits"
					+ "\n Pick a different Student ID if all other "
					+ "infomation met the \nrequirements above");
			value = true;
		}
	}
	
	/** A function to generate random gpa for enrollment */
	private double grade() {
		Random rand = new Random(); //instance of random class
		double gr = rand.nextFloat() * (4.0 - 0.0) + 0.0;
		double rounded = Math.round(gr * 10) / 10.0;
		return rounded;	
	}
	/** Afunction to enroll the course and student into Enrollment table */
	private static void enroll(String stuID, String coID, double g, JButton b) {
		//SQL Statement
		String sqlEnroll = "INSERT INTO Enrollment VALUES "
				+ "(" + stuID + ", " +  coID + ", " + g + ")";
		try {
			Statement statement = connection.createStatement();
			statement.execute(sqlEnroll);
			JOptionPane.showMessageDialog(null, "Enroll Successfully");
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Enroll Failed!"
					+ "\nMake Sure:"
					+ "\n-Student ID is valid"
					+ "\n-Course ID is valid"
					+ "\n-The course have not been register by the student");
		}
	}
}
