package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Interface {

	private static final String host = "localhost";
	private static final String port = "3306";
	private static final String database = "kaiju_adventure";
	private static final String username = "root";
	private static final String passwort = "";
	
	private static Connection con;
	private static ResultSet rs;
	
	public static boolean isConnect() {
		return(con == null ? false : true);
	}
	
	public static void connect() throws ClassNotFoundException {
		
		if (!isConnect()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, passwort);
				System.out.println("[MySQL] Verbunden!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ResultSet select(String qry) throws SQLException {
		
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery(qry);
		
		return rs;
	}
	
	public static void disconnect() {
		
		if (!isConnect()) {
			try {
				con.close();
				System.out.println("[MySQL] Verbindung getrennt!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void update(String qry) {
		try {
			PreparedStatement ps = con.prepareStatement(qry);
			ps.execute();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
}
