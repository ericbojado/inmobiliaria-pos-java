package inmobiliaria.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

import inmobiliaria.util.ConexionDB;

public class inventario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBusqueda;
	private JTable table;
	
	private Statement query = null;
	private ResultSet Rs = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inventario frame = new inventario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	DefaultTableModel modelo = new DefaultTableModel();
	String sentencia = "";
	ConexionDB conexion = new ConexionDB();
	
	public inventario() {
		setTitle("Inventario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 707, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInventario = new JLabel("Inventario");
		lblInventario.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblInventario.setBounds(34, 10, 127, 35);
		contentPane.add(lblInventario);
		
		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(34, 49, 382, 18);
		contentPane.add(txtBusqueda);
		txtBusqueda.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 86, 602, 283);
		contentPane.add(scrollPane);
		
		modelo.addColumn("id");
		modelo.addColumn("Dimensiones (m^2)");
		modelo.addColumn("Precio");
		modelo.addColumn("Ubicacion");
		modelo.addColumn("Estado");
		modelo.addColumn("Tipo");
		
		table = new JTable(modelo);
		scrollPane.setViewportView(table);

		//Ajustes para que la columna id no sea visible
		table.getColumnModel().getColumn(0).setMaxWidth(0);//pone la anchura maxima de esta columna a 0
		table.getColumnModel().getColumn(0).setMinWidth(0);//pone la anchura minima de esta columna a 0
		table.getColumnModel().getColumn(0).setPreferredWidth(0);//pone la preferencia de la anchura de esta columna a 0
		table.getColumnModel().getColumn(0).setResizable(false);//hace que la columna no pueda ser reacomodada por el usuario
		
		cargarDatos();
				
		JComboBox cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"Presupuesto", "Ubicacion", "Dimensiones", "Tipo", "Estado"}));
		cmbBusqueda.setBounds(426, 48, 116, 20);
		contentPane.add(cmbBusqueda);

		JButton btnBusqueda = new JButton("Buscar");
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelo.setRowCount(0);
				String busquedaIngresada = txtBusqueda.getText();
				String tipoBusqueda = cmbBusqueda.getSelectedItem().toString();
				String sentenciaBusqueda = "";
				
				String campoBuscar = cmbBusqueda.getSelectedItem().toString();
				switch(campoBuscar) {
					case "Dimensiones":
						sentenciaBusqueda = "SELECT * FROM propiedades WHERE dimension LIKE '%" + busquedaIngresada + "%' ORDER BY id";
						break;
					
					case "Tipo":
						sentenciaBusqueda = "SELECT * FROM propiedades WHERE tipo LIKE '%" + busquedaIngresada + "%' ORDER BY id";
						break;
						
					case "Ubicacion":
						sentenciaBusqueda = "SELECT * FROM propiedades WHERE ubicacion LIKE '%" + busquedaIngresada + "%' ORDER BY id";
						break;
						
					case "Presupuesto":
						sentenciaBusqueda = "SELECT * FROM propiedades WHERE precio LIKE '%" + busquedaIngresada + "%' ORDER BY id";
						break;
						
					case "Estado":
						sentenciaBusqueda = "SELECT * FROM propiedades WHERE estado LIKE '%" + busquedaIngresada + "%' ORDER BY id";
						break;
				}
				
				String[] datos = new String[6];
				
				try{
					Connection con = conexion.conectar();
					query = con.createStatement();
					Rs = query.executeQuery(sentenciaBusqueda);
					
					while(Rs.next()){
						datos[0] = Rs.getString(1);
						datos[1] = Rs.getString(2);
						datos[2] = Rs.getString(3);
						datos[3] = Rs.getString(4);
						datos[4] = Rs.getString(5);
						datos[5] = Rs.getString(6);
						modelo.addRow(datos);
					}
					
					conexion.cerrar();
				}catch (SQLException ex){
					JOptionPane.showMessageDialog(null, "Error: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBusqueda.setBounds(552, 48, 84, 20);
		contentPane.add(btnBusqueda);
	}
	
	public void cargarDatos() {
		modelo.setRowCount(0);
		sentencia = "SELECT * FROM propiedades";
		
		
		String[] datos = new String[6];
		
		try{
			Connection con = conexion.conectar();
			query = con.createStatement();
			Rs = query.executeQuery(sentencia);
			
			while(Rs.next()){
				datos[0] = Rs.getString(1);
				datos[1] = Rs.getString(2);
				datos[2] = Rs.getString(3);
				datos[3] = Rs.getString(4);
				datos[4] = Rs.getString(5);
				datos[5] = Rs.getString(6);
				modelo.addRow(datos);
			}
			
			conexion.cerrar();
		}catch (SQLException ex){
			JOptionPane.showMessageDialog(null, "Error: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
}
