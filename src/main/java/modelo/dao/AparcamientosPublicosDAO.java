package modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import modelo.dao.ConsultaOrcl;
import modelo.vo.AparcamientosPublicosVO;
import modelo.vo.Punto;

import javax.annotation.*;

@ManagedBean
@SessionScoped
public class AparcamientosPublicosDAO {
 
	public static Logger logger = Logger.getLogger(AparcamientosPublicosDAO.class.getName());	
	private DataSource ds;
	
	public AparcamientosPublicosDAO(){
	try {
		Context ctx = new InitialContext();
		ds = (DataSource)ctx.lookup("java:comp/env/jdbc/myoracle");
	} catch (NamingException e) {
		e.printStackTrace();
	}

}
	// Listado de aparcamientos

	public List<AparcamientosPublicosVO> getListadoAparcamientos() throws SQLException, ClassNotFoundException, NamingException{
		List<AparcamientosPublicosVO> aparcamientos = new ArrayList<AparcamientosPublicosVO>();
		
		if(ds==null){
			throw new SQLException("No se alcanza el DataSource");
 
		}else{	
			String query = "select ID,LASTUPDATED,ICON,TITLE,HORARIO,ACCESOPEATON,ACCESOS,ACCESOVEHICULO,COORDX,COORDY"
					+ " from EQ4_APARCA";
			Statement st = null;
			ResultSet rs = null;
		
			ConsultaOrcl co = new ConsultaOrcl();
			Connection conection = co.conexion();
			
			if(conection == null){
				throw new SQLException("No se alcanza la conexion con la BBDD");
			}else{
				st = conection.createStatement();
				rs = st.executeQuery(query);
				Punto punto = null;
	
				while (rs.next()) {
	
					int id = rs.getInt(1);
					Date fecha = rs.getDate(2);
					String icon = rs.getString(3);
					String title = rs.getString(4);
					String horario = rs.getString(5);
					String accesoPeaton = rs.getString(6);
					String accesos = rs.getString(7);
					String accesoVehiculo = rs.getString(8);
	
					int x = rs.getInt(9);
					int y = rs.getInt(10);
	
					punto = new Punto(x, y);
	
					AparcamientosPublicosVO aparcamiento = new AparcamientosPublicosVO(
							punto, horario, title, icon, accesos, fecha,
							accesoPeaton, accesoVehiculo, id);
	
					aparcamientos.add(aparcamiento);
				}
			}	
		}
	return aparcamientos;
	}	
}
