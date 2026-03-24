package HMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	
	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String username = "root";
	private static final String password = "root";
	
	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		
		try {
			Connection connection = DriverManager.getConnection(url,username,password);
			Patient patient = new Patient(connection,scanner);
			Doctor doctor = new Doctor(connection);
			while(true) {
				System.out.println("Hospital Managemnent System:");
				System.out.println("1.Add Patient");
				System.out.println("2.View Patient");
				System.out.println("3.View Doctors");
				System.out.println("4.Book Appointments");
				System.out.println("5.Exit");
				
				System.out.println("Please Enter Your Choice:");
				
				int choice = scanner.nextInt();
				
			switch(choice) {
			case 1:
					//Add Patient
				patient.addPatient();
				System.out.println();
				break;
				
			case 2:
				//view Patient
				patient.viewPateint();
				System.out.println();
				break;
			case 3:
				//View Doctors
				doctor.viewDoctor();
				System.out.println();
				break;
			case 4:
				//Book Appointments
				bookAppointment(patient,doctor,connection,scanner);
				System.out.println();
				break;
			case 5:
				System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM");
				return;
			default:
				System.out.println("Please Enter Valid Input");
				break;
			}
			
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
public static void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner) {
		
		System.out.println("Please Enter Patient ID");
		int patientID = scanner.nextInt();
		System.out.println("Please Enter Doctor ID");
		int doctorID = scanner.nextInt();
		System.out.println("Please enter Appointment Date(YYYY-MM-DD");
		String appointmentDate = scanner.next();
		
		if(patient.getPatientByID(patientID) && doctor.getDoctorByID(doctorID)) {
			if(checkDoctorAvailability(doctorID,appointmentDate,connection)) {
				String appointmentQuery = "INSERT INTO appointments (patient_id,doctor_id,appointment_date) values (?,?,?)";
				try {
					PreparedStatement preparedstatement = connection.prepareStatement(appointmentQuery);
					preparedstatement.setInt(1, patientID);
					preparedstatement.setInt(2,doctorID);
					preparedstatement.setString(3, appointmentDate);
					int rowsAffected = preparedstatement.executeUpdate();
					if(rowsAffected > 0) {
						System.out.println("Appointment Booked");
					}
					else {
						System.out.println("Appointment could not be booked");
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Doctor not available in this date");
			}
		}
		else {
			System.out.println("Either Doctor or Patient doesn't Exist");
		}
}


public static boolean checkDoctorAvailability(int doctorID,String appointmentDate,Connection connection) {
	//Count(*): getting the rows which matches the particular criteria
	String query = "Select count(*) from appointments where doctor_id = ? and appointment_date = ?";
	try {
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setInt(1, doctorID);
		prepareStatement.setString(2, appointmentDate);
		
		ResultSet rs = prepareStatement.executeQuery();
		 if(rs.next()) {
			 int count = rs.getInt(1);
			 
			 if(count == 0) {
				 return true;
			 }
			 else {
				 return false;
			 }
		 }
		
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	return false;
}

	}
