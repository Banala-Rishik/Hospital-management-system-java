package HMS;

import java.sql.*;
import java.util.Scanner;

public class Patient {
	
	private Connection connection;
	private Scanner scanner;
	
	public Patient(Connection connection,Scanner scanner)
	{
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public void addPatient() {
		System.out.println("Enter Pateints Name");
		String name = scanner.next();
		System.out.println("Enter Pateint's Age");
		int age = scanner.nextInt();
		System.out.println("Enter Pateint's Gender");
		String gender = scanner.next();
		
		try {
			String Query = "Insert into patients(name,age,gender) values (?,?,?)";
			PreparedStatement preparedStatement  = connection.prepareStatement(Query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2,age);
			preparedStatement.setString(3,gender);
			
			int affectedRows = preparedStatement.executeUpdate();
			
			if(affectedRows > 0) {
				System.out.println("Pateint Added Succesfully");
			}
			else {
				System.out.println("Failed to add Pateint");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewPateint() {
		String query = "select * from patients";
		
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			ResultSet rs = preparedstatement.executeQuery();
			System.out.println("Patients:");
			System.out.println("+------------+------------------------------+----------+------------+");
			System.out.println("| Patient ID| Name                         |  Age     |   Gender   |");
			System.out.println("+------------+------------------------------+----------+------------+");
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				
				System.out.printf("|%-13s|%-21s|%-6s|%-10s|\n",id,name,age,gender);
				System.out.println("+------------+-------------------------------+----------+------------");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientByID(int id){
		String query = "select * from patients where ID = ?";
		
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setInt(1,id);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
