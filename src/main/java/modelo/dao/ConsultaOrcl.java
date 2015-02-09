package modelo.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConsultaOrcl {

	private Connection con = null;
	private Statement stmt;
	public ResultSet resultado;
	private PrintWriter pw = null;
	private StringWriter sw = null;

	// Esta sirve para conectarse a la base de Datos
	public Connection conexion() throws ClassNotFoundException, NamingException, SQLException {
		/*try {
			String driver = "oracle.jdbc.driver.OracleDriver";

			String host = "54.154.192.80";
			String puerto = "1521";
			String sid = "xe";

			String user = "test";
			String password = "test";

			String url = "jdbc:oracle:thin:" + user + "/" + password + "@"
					+ host + ":" + puerto + ":" + sid;
			Class.forName(driver);
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
		}*/
		
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
		Connection con = ds.getConnection();

		return con;
		

	}

	public ResultSet consulta(Connection con, String consulta) {
		ResultSet resultado = null;
		try {
			stmt = (Statement) con.createStatement();
			resultado = (ResultSet) stmt.executeQuery(consulta);
		} catch (SQLException e) {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
		}
		return resultado;
	}

	public void cerrarConexion(Connection con) {
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			// return "NO FUNCIONA" + sw.toString() + "rn";
		}
	}

}