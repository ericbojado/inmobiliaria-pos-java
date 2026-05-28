package inmobiliaria.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import inmobiliaria.util.ConexionDB;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CatalogoUsuarios extends JFrame {
	
	private static String idModificar;
	private static String nombreModificar;
	private static String correoModificar;
	private static String passwordModificar;
	private static String rolModificar;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBusqueda;
	private JTable table;
	
	private ConexionDB conexion = new ConexionDB();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CatalogoUsuarios frame = new CatalogoUsuarios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CatalogoUsuarios() {
		setTitle("Catalogo de Usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblCatalogoUsuarios = new JLabel("Catálogo de Usuarios");
		lblCatalogoUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCatalogoUsuarios.setBounds(23, 10, 223, 25);
		contentPane.add(lblCatalogoUsuarios);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 73, 485, 303);
		contentPane.add(scrollPane);

		//Iniciamos la tabla llamando al método cargarDatos()
		table = new JTable();
		cargarDatos(); 
		
		scrollPane.setViewportView(table);
		
		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(23, 45, 283, 18);
		contentPane.add(txtBusqueda);
		txtBusqueda.setColumns(10);

		JComboBox cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"Nombre", "Rol", "Correo"}));
		cmbBusqueda.setBounds(316, 44, 98, 20);
		contentPane.add(cmbBusqueda);
		
		//BUSCAR
		JButton btnBusqueda = new JButton("Buscar");
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String busqueda = txtBusqueda.getText();
				String tipoBusqueda = cmbBusqueda.getSelectedItem().toString().toLowerCase(); // nombre, correo, rol
				
				String sentencia = "SELECT * FROM usuario WHERE " + tipoBusqueda + " LIKE '%" + busqueda + "%'";
				
				conexion.conectar();
				DefaultTableModel modeloBusqueda = conexion.consulta(sentencia);
				actualizarDisenoTabla(modeloBusqueda);
				conexion.cerrar();
			}
		});
		btnBusqueda.setBounds(424, 44, 84, 20);
		contentPane.add(btnBusqueda);
		
		//AGREGAR
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarUsuario agregar = new AgregarUsuario();
				agregar.setModal(true);
				agregar.setVisible(true);
				
				if(agregar.guardadoCorrecto()) {
					String nombre = agregar.traerNombre();
					String correo = agregar.traerCorreo();
					String password = agregar.traerPassword();
					String rol = agregar.traerRol();
					
					String sentencia = "INSERT INTO usuario (nombre, correo, password, rol) VALUES ('"+ nombre +"', '"+ correo +"', '"+ password +"', '"+ rol +"')";
					
					conexion.conectar();
					conexion.ejecutarSentencia(sentencia);
					conexion.cerrar();
					
					JOptionPane.showMessageDialog(null, "Usuario Agregado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					cargarDatos(); //Recargar los cambios en la Tabla
				}
			}
		});
		btnAgregar.setBounds(540, 109, 98, 38);
		contentPane.add(btnAgregar);
		
		//MODIFICAR
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				
				if(index >= 0) {
					idModificar = table.getModel().getValueAt(index, 0).toString();
					nombreModificar = table.getModel().getValueAt(index, 1).toString();
					correoModificar = table.getModel().getValueAt(index, 2).toString();
					passwordModificar = table.getModel().getValueAt(index, 3).toString();
					rolModificar = table.getModel().getValueAt(index, 4).toString();

					ModificarUsuario modificar = new ModificarUsuario();
					modificar.setModal(true);
					modificar.setVisible(true);
					
					if(modificar.guardadoCorrecto()) {
						String nombre = modificar.traerNombreModificado();
						String correo = modificar.traerCorreoModificado();
						String password = modificar.traerPasswordModificado();
						String rol = modificar.traerRolModificado();
						
						String sentencia = "UPDATE usuario SET nombre = '"+ nombre +"', correo = '"+ correo +"', password = '"+ password +"', rol = '"+ rol +"' WHERE id = '"+ idModificar +"'";
					
						conexion.conectar();
						conexion.ejecutarSentencia(sentencia);
						conexion.cerrar();
						
						JOptionPane.showMessageDialog(null, "Usuario modificado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						cargarDatos();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione una fila antes de modificar", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(540, 182, 98, 38);
		contentPane.add(btnModificar);
		
		//ELIMINAR
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				
				if(index >= 0) {
					int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro seleccionado? Esta acción es permanente.", "Eliminar registro", JOptionPane.YES_NO_OPTION);
					
					if(respuesta == JOptionPane.YES_OPTION) {
						String id = table.getModel().getValueAt(index, 0).toString();
						String sentencia = "DELETE FROM usuario WHERE id = '"+ id +"'";
						
						conexion.conectar();
						conexion.ejecutarSentencia(sentencia);
						conexion.cerrar();
						
						JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						cargarDatos();
					}
				} else {
						JOptionPane.showMessageDialog(null, "Seleccione una fila antes de eliminar", "Error", JOptionPane.ERROR_MESSAGE);						
				}
			}
		});
		btnEliminar.setBounds(540, 253, 98, 38);
		contentPane.add(btnEliminar);
		
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu Ventana = new menu();
		        Ventana.setVisible(true);
		        dispose();
			}
		});
		btnRegresar.setBounds(536, 353, 102, 23);
		contentPane.add(btnRegresar);
	}
	
	//Método principal para consultar datos usando ConexionDB
	public void cargarDatos() {
		conexion.conectar();
		//Cargamos la tabla
		DefaultTableModel modeloDB = conexion.consulta("SELECT * FROM usuario");
		//Configuramos la Tabla
		actualizarDisenoTabla(modeloDB);
		conexion.cerrar();
	}
	
	//Método para aplicar la configuración visual al modelo que traigas de ConexionDB
	private void actualizarDisenoTabla(DefaultTableModel nuevoModelo) {
		//Nuevo modelo para heredar la restricción de que no sea editable
		DefaultTableModel modelo = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//Clonamos columnas y filas del modelo que generó ConexionDB
		for (int i = 0; i < nuevoModelo.getColumnCount(); i++) {
			modelo.addColumn(nuevoModelo.getColumnName(i));
		}
		for (int i = 0; i < nuevoModelo.getRowCount(); i++) {
			Object[] fila = new Object[nuevoModelo.getColumnCount()];
			for (int j = 0; j < nuevoModelo.getColumnCount(); j++) {
				fila[j] = nuevoModelo.getValueAt(i, j);
			}
			modelo.addRow(fila);
		}
		
		table.setModel(modelo);
		
		//Configuraciones
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		
		//Ocultar ID
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setResizable(false);
		
		//Ocultar Contraseña
		if(table.getColumnCount() > 3) {
			table.getColumnModel().getColumn(3).setMaxWidth(0);
			table.getColumnModel().getColumn(3).setMinWidth(0);
			table.getColumnModel().getColumn(3).setPreferredWidth(0);
			table.getColumnModel().getColumn(3).setResizable(false);
		}
	}
	
	//Getters estáticos para usarlos en ModificarUsuario
	public static String getNombreModificar() { 
		return nombreModificar; 
	}
	public static String getCorreoModificar() { 
		return correoModificar; 
	}
	public static String getPasswordModificar() { 
		return passwordModificar; 
	}
	public static String getRolModificar() { 
		return rolModificar;
	}
}
