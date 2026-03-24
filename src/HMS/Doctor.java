package HMS;

import java.sql.*;

public class Doctor {
	
	private Connection connection;

	public Doctor(Connection connection) {
		this.connection = connection;
	}
	
	public void viewDoctor(){
			String query = "select * from doctors";
			
			try {
				PreparedStatement preparedstatement = connection.prepareStatement(query);
				ResultSet rs = preparedstatement.executeQuery();
				System.out.println("Doctors:");
				System.out.println("+------------+------------------------------+-----------------------+");
				System.out.println("| Doctor ID  | Name                         |  Specialization       |");
				System.out.println("+------------+------------------------------+-----------------------+");
				
				while(rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String specialization = rs.getString("specialization");
					
					System.out.printf("|%-15s|%-20s|%-18s|\n",id,name,specialization);
					System.out.println("+------------+-------------------------------+-----------------------");
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	public boolean getDoctorByID(int id){
      String query = "select * from doctors where ID = ?";
		
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
