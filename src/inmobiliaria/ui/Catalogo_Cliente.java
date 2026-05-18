package inmobiliaria.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import inmobiliaria.util.ConexionDB;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JEditorPane;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

public class Catalogo_Cliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField texCliente;
	private JTable table;

	//Datos_para_la_tabla
	DefaultTableModel modelo = new DefaultTableModel();
	String sentencia = "";
	ConexionDB conexion = new ConexionDB();
	
	Connection Conexion = null;
	Statement Sentenciasql = null;
	ResultSet Rs = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Catalogo_Cliente frame = new Catalogo_Cliente();
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
	
	
	public Catalogo_Cliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Catalogo de Clientes");
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
		lblNewLabel.setBounds(160, 11, 177, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Buscar Cliente");
		lblNewLabel_1.setBounds(10, 48, 95, 14);
		contentPane.add(lblNewLabel_1);
		
		texCliente = new JTextField();
		texCliente.setBounds(115, 45, 86, 20);
		contentPane.add(texCliente);
		texCliente.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Direccion:");
		lblNewLabel_2.setBounds(211, 48, 71, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(396, 44, 89, 23);
		contentPane.add(btnBuscar);
		
		JComboBox BoxCategoria = new JComboBox();
		BoxCategoria.setBounds(292, 44, 83, 22);
		contentPane.add(BoxCategoria);
		
		JScrollPane scrLista = new JScrollPane();
		scrLista.setBounds(10, 84, 475, 164);
		contentPane.add(scrLista);
		
		//Columnas
		        modelo.addColumn("id");
				modelo.addColumn("Nombre");
				modelo.addColumn("Apellido");
				modelo.addColumn("Teléfono");
				modelo.addColumn("Dirección");
				
		
		table = new JTable(modelo);
		scrLista.setViewportView(table);
		
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.setBounds(383, 394, 102, 23);
		contentPane.add(btnRegresar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Acciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 284, 475, 73);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(20, 26, 115, 23);
		panel.add(btnAgregar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(179, 26, 115, 23);
		panel.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(339, 26, 115, 23);
		panel.add(btnEliminar);
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Agregar_Clin AgregarC = new Agregar_Clin();
				AgregarC.setSize(350, 330);
				AgregarC.setLocationRelativeTo(Catalogo_Cliente.this);
				AgregarC.setModal(true);
				AgregarC.setVisible(true);
				String nombre, apellido, telefono, direccion;
				
				if(AgregarC.guardadoCorrecto()) {//Se confirma que se presiono OK y no algun otro boton en el jdialog
					nombre = AgregarC.traerNombre();//Trae todos los datos ingresados en el jdialog
					apellido = AgregarC.traerApellido();
					telefono = AgregarC.traerTelefono();
					direccion = AgregarC.traerDireccion();
					
					sentencia = "INSERT INTO cliente (nombre, apellido, telefono, direccion) VALUES ('"+ nombre +"', '"+ apellido +"', '"+ telefono +"', '"+ direccion +"')";
					
					try{
						Connection con = conexion.conectar();
						Sentenciasql = con.createStatement();
						int filas = Sentenciasql.executeUpdate(sentencia);
						
						if(filas >= 1) {
							JOptionPane.showMessageDialog(null, "Usuario agregado con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);							
						}else {
							JOptionPane.showMessageDialog(null, "Error al almacenar el usuario ", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
						modelo.setRowCount(0);
						MostrarInformacion();
						conexion.cerrar();
						
					}catch (SQLException ex){
						JOptionPane.showMessageDialog(null, "Error: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		
		MostrarInformacion();

	}
	private void MostrarInformacion() 
	{
		//Procedimientos para mostrar la informacion de la BD en la tabla
		String Valores[] = new String[5];
		//Variables
		sentencia = "SELECT * FROM cliente";
		try {
		Connection con = conexion.conectar();
		Sentenciasql = con.createStatement();
		Rs = Sentenciasql.executeQuery(sentencia);
		
		while(Rs.next()){
			//Recuperar la informacion en un arreglo
			Valores[0] = Rs.getString("id");
			Valores[1] = Rs.getString("Nombre");
			Valores[2] = Rs.getString("Apellido");
			Valores[3] = Rs.getString("Telefono");
			Valores[4] = Rs.getString("Direccion");
			
			modelo.addRow(Valores);
		}
		conexion.cerrar();
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Ocurrio el error: " + e.toString());
		}
	}
}
