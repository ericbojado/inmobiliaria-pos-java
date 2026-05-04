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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class catalogoUsuarios extends JFrame {
	
	private static String idModificar;
	private static String nombreModificar;
	private static String correoModificar;
	private static String passwordModificar;
	private static String rolModificar;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBusqueda;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					catalogoUsuarios frame = new catalogoUsuarios();
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

	public catalogoUsuarios() {
		setTitle("Catalogo de Usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setLocationRelativeTo(null);

		cargarDatos();//Metodo para rellenar table
		
		JLabel lblCatalogoUsuarios = new JLabel("Catálogo de Usuarios");
		lblCatalogoUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCatalogoUsuarios.setBounds(23, 10, 223, 25);
		contentPane.add(lblCatalogoUsuarios);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 73, 485, 303);
		contentPane.add(scrollPane);

		modelo.addColumn("id");
		modelo.addColumn("Nombre");
		modelo.addColumn("Correo");
		modelo.addColumn("Rol");
		
		table = new JTable(modelo);
		scrollPane.setViewportView(table);
		
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setResizable(false);

		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(23, 45, 283, 18);
		contentPane.add(txtBusqueda);
		txtBusqueda.setColumns(10);

		JComboBox cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"Nombre", "Rol", "Correo"}));
		cmbBusqueda.setBounds(316, 44, 98, 20);
		contentPane.add(cmbBusqueda);
		
		JButton btnBusqueda = new JButton("Buscar");
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String busqueda = txtBusqueda.getText();
				String tipoBusqueda = cmbBusqueda.getSelectedItem().toString();
				
				switch(tipoBusqueda) {
					case "Nombre":
						sentencia = "SELECT * FROM usuario WHERE nombre LIKE '%"+ busqueda +"%'";
						break;
					
					case "Correo":
						sentencia = "SELECT * FROM usuario WHERE correo LIKE '%"+ busqueda +"%'";
						break;
						
					case "Rol":
						sentencia = "SELECT * FROM usuario WHERE rol LIKE '%"+ busqueda +"%'";
						break;
				}
				
				String[] filas = new String[3];
			}
		});
		btnBusqueda.setBounds(424, 44, 84, 20);
		contentPane.add(btnBusqueda);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarUsuario agregar = new AgregarUsuario();
				agregar.setSize(350, 225);
				agregar.setLocationRelativeTo(catalogoUsuarios.this);
				agregar.setModal(true);//Bloquea jframe principal hasta cerrar jdialog agregar
				agregar.setVisible(true);
				String nombre, correo, password, rol;
				
				if(agregar.guardadoCorrecto()) {//Se confirma que se presiono OK y no algun otro boton en el jdialog
					nombre = agregar.traerNombre();//Trae todos los datos ingresados en el jdialog
					correo = agregar.traerCorreo();
					password = agregar.traerPassword();
					rol = agregar.traerRol();
					
					sentencia = "INSERT INTO usuario VALUES ('', '"+ nombre +"', '"+ correo +"', '"+ password +"', '"+ rol +"')";
					
					//Bloque de conexion y ejecucion de sentencia
					
					JOptionPane.showMessageDialog(null, "Usuario agregado con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			}
		});
		btnAgregar.setBounds(540, 109, 98, 38);
		contentPane.add(btnAgregar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				
				if(index >= 0) {
					ModificarUsuario modificar = new ModificarUsuario();
					modificar.setSize(350, 225);
					modificar.setLocationRelativeTo(catalogoUsuarios.this);
					modificar.setModal(true);
					modificar.setVisible(true);
					
					idModificar = table.getModel().getValueAt(index, 0).toString();
					nombreModificar = table.getModel().getValueAt(index, 1).toString();
					correoModificar = table.getModel().getValueAt(index, 2).toString();
					passwordModificar = table.getModel().getValueAt(index, 3).toString();
					rolModificar = table.getModel().getValueAt(index, 4).toString();
					String nombre, correo, password, rol;
					
					if(modificar.guardadoCorrecto()) {
						nombre = modificar.traerNombreModificado();
						correo = modificar.traerCorreoModificado();
						password = modificar.traerPasswordModificado();
						rol = modificar.traerRolModificado();
						
						sentencia = "UPDATE usuario SET nombre = '"+ nombre +"', correo = '"+ correo +"', password = '"+ password +"', rol = '"+ rol +"' WHERE id = '"+ idModificar +"'";
					
						//Bloque de conexion y ejecucion de sentencia.
						
						JOptionPane.showMessageDialog(null, "Usuario modificado con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Seleccione una fila antes de modificar", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnModificar.setBounds(540, 182, 98, 38);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar"); // Este es un comentario
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				
				if(index >= 0) {
					int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro seleccionado? Esta accion es permanente.", "Eliminar registro", JOptionPane.INFORMATION_MESSAGE);
					
					if(respuesta == 0) {
						String id = table.getModel().getValueAt(index, 0).toString();
						
						sentencia = "DELETE * FROM usuario WHERE id = '"+ id +"'";
						
						
					}
				}else {
						JOptionPane.showMessageDialog(null, "Seleccione una fila antes de eliminar", "Error", JOptionPane.ERROR_MESSAGE);						
				}
			}
		});
		btnEliminar.setBounds(540, 253, 98, 38);
		contentPane.add(btnEliminar);

	}
	
	public void cargarDatos() {
		sentencia = "SELECT * FROM usuario";
		String[] datos = new String[3];
		
		//Codigo que necesita conexion a bd
		//try{
			//while(resultadosSentencia){
				//datos[0] = variableConDatos.getString(1);
				//datos[1] = variableConDatos.getString(2);
				//datos[2] = variableConDatos.getString(3);
				//datos[3] = variableConDatos.getString(5);
				//modelo.addRow(datos);
			//}
		//}catch (SQLException ex){
			//Logger.getLogger(RHBaja.class.getName()).log(Level.SEVERE, null, ex);
		
		//}
		
	}
	
	public static String nombreModificar() {
		return nombreModificar;
	}
	
	public static String correoModificar() {
		return correoModificar;
	}
	
	public static String passwordModificar() {
		return passwordModificar;
	}
	
	public static String rolModificar() {
		return rolModificar;
	}
	
}
