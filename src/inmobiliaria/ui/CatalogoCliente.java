package inmobiliaria.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import inmobiliaria.util.ConexionDB;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class CatalogoCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCliente;
	private JTable table;
	
	private static String idModificar;
	private static String nombreModificar;
	private static String apellidoModificar;
	private static String telefonoModificar;
	private static String direccionModificar;

	private ConexionDB conexion = new ConexionDB();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CatalogoCliente frame = new CatalogoCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CatalogoCliente() {
		setTitle("Catálogo de Clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 755, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblCatalogoClientes = new JLabel("Catálogo de Clientes");
		lblCatalogoClientes.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCatalogoClientes.setBounds(20, 15, 250, 25);
		contentPane.add(lblCatalogoClientes);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 110, 530, 310);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		cargarDatos();

		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBorder(new TitledBorder(null, "Búsqueda de Registro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBusqueda.setBounds(20, 50, 530, 50);
		contentPane.add(panelBusqueda);
		panelBusqueda.setLayout(null);

		txtCliente = new JTextField();
		txtCliente.setBounds(10, 20, 230, 20);
		panelBusqueda.add(txtCliente);
		txtCliente.setColumns(10);

		JComboBox cmbCliente = new JComboBox();
		cmbCliente.setModel(new DefaultComboBoxModel(new String[] {"Nombre", "Apellido", "Telefono", "Direccion"}));
		cmbCliente.setBounds(250, 20, 140, 20);
		panelBusqueda.add(cmbCliente);

		//BUSCAR
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String busqueda = txtCliente.getText().trim();
				String tipoBusqueda = cmbCliente.getSelectedItem().toString();
				
				String sentencia = "SELECT * FROM cliente WHERE " + tipoBusqueda + " LIKE '%" + busqueda + "%'";
				
				conexion.conectar();
				DefaultTableModel modeloBusqueda = conexion.consulta(sentencia);
				actualizarDisenoTabla(modeloBusqueda);
				conexion.cerrar();
			}
		});
		btnBuscar.setBounds(410, 19, 100, 23);
		panelBusqueda.add(btnBuscar);

		JPanel panelAcciones = new JPanel();
		panelAcciones.setBorder(new TitledBorder(null, "Acciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAcciones.setBounds(570, 50, 145, 315);
		contentPane.add(panelAcciones);
		panelAcciones.setLayout(null);

		//AGREGAR
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarCliente ventana = new AgregarCliente();
				ventana.setVisible(true);
				
				if(ventana.guardadoCorrecto()) {
					String nombre = ventana.traerNombre();
					String apellido = ventana.traerApellido();
					String telefono = ventana.traerTelefono();
					String direccion = ventana.traerDireccion();
					
					String sentencia = "INSERT INTO cliente (Nombre, Apellido, Telefono, Direccion) "
							 + "VALUES ('" + nombre + "', '" + apellido + "', '" + telefono + "', '" + direccion + "')";
					
					conexion.conectar();
					conexion.ejecutarSentencia(sentencia);
					conexion.cerrar();
					
					JOptionPane.showMessageDialog(null, "Cliente guardado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					cargarDatos(); // Recargar la JTable
				}
			}
		});
		btnAgregar.setBounds(15, 30, 115, 40);
		panelAcciones.add(btnAgregar);

		//MODIFICAR
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = table.getSelectedRow();

				if (filaSeleccionada >= 0) {
				    //Guardamos los datos de la JTable en las variables estáticas globales
				    idModificar = table.getModel().getValueAt(filaSeleccionada, 0).toString();
				    nombreModificar = table.getModel().getValueAt(filaSeleccionada, 1).toString();
				    apellidoModificar = table.getModel().getValueAt(filaSeleccionada, 2).toString();
				    telefonoModificar = table.getModel().getValueAt(filaSeleccionada, 3).toString();
				    direccionModificar = table.getModel().getValueAt(filaSeleccionada, 4).toString();

				    ModificarCliente ventanaModificar = new ModificarCliente();
				    ventanaModificar.setVisible(true);
				    
				    if (ventanaModificar.guardadoCorrecto()) {
				        String nuevoNom = ventanaModificar.getNombreModificado();
				        String nuevoApe = ventanaModificar.getApellidoModificado();
				        String nuevoTel = ventanaModificar.getTelefonoModificado();
				        String nuevaDir = ventanaModificar.getDireccionModificado();
				        
				        String sentencia = "UPDATE cliente SET Nombre='" + nuevoNom + "', Apellido='" + nuevoApe + "', "
				                         + "Telefono='" + nuevoTel + "', Direccion='" + nuevaDir + "' WHERE id=" + idModificar;
				        
				        conexion.conectar();
				        conexion.ejecutarSentencia(sentencia);
				        conexion.cerrar();
				        
				        JOptionPane.showMessageDialog(null, "Datos del cliente actualizados.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				        cargarDatos();
				    }
				} else {
				    JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla para modificar.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(15, 100, 115, 40);
		panelAcciones.add(btnModificar);

		//ELIMINAR
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = table.getSelectedRow();
				
				if(filaSeleccionada >= 0) {
					int confirmar = JOptionPane.showConfirmDialog(null, 
							"¿Está seguro de eliminar este cliente? Esta acción es irreversible.", 
							"Eliminar Cliente", JOptionPane.YES_NO_OPTION);
					
					if(confirmar == JOptionPane.YES_OPTION) {
						String id = table.getModel().getValueAt(filaSeleccionada, 0).toString();
						String sentencia = "DELETE FROM cliente WHERE id = " + id;
						
						conexion.conectar();
						conexion.ejecutarSentencia(sentencia);
						conexion.cerrar();
						
						JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						cargarDatos();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione un cliente para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEliminar.setBounds(15, 170, 115, 40);
		panelAcciones.add(btnEliminar);

		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu ventana = new menu();
				ventana.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(585, 397, 115, 23);
		contentPane.add(btnRegresar);
	}

	//Método para cargar los datos de la tabla
	public void cargarDatos() {
		conexion.conectar();
		DefaultTableModel modeloDB = conexion.consulta("SELECT * FROM cliente");
		actualizarDisenoTabla(modeloDB);
		conexion.cerrar();
	}

	//Método para aplicar configuraziones a la tabla
	private void actualizarDisenoTabla(DefaultTableModel nuevoModelo) {
		DefaultTableModel modelo = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; //Evita modificaciones directas en celdas
			}
		};

		//Clonar columnas
		for (int i = 0; i < nuevoModelo.getColumnCount(); i++) {
			modelo.addColumn(nuevoModelo.getColumnName(i));
		}
		
		//Clonar registros
		for (int i = 0; i < nuevoModelo.getRowCount(); i++) {
			Object[] fila = new Object[nuevoModelo.getColumnCount()];
			for (int j = 0; j < nuevoModelo.getColumnCount(); j++) {
				fila[j] = nuevoModelo.getValueAt(i, j);
			}
			modelo.addRow(fila);
		}

		table.setModel(modelo);

		//Configuración Básica
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);

		//Ocultar la columna ID
		if (table.getColumnCount() > 0) {
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.getColumnModel().getColumn(0).setResizable(false);
		}
	}
	// Getters estáticos para conectar con ModificarCliente
	public static String idModificar() { 
		return idModificar; 
	}
	public static String nombreModificar() { 
		return nombreModificar; 
	}
	public static String apellidoModificar() { 
		return apellidoModificar; 
	}
	public static String telefonoModificar() { 
		return telefonoModificar; 
	}
	public static String direccionModificar() { 
		return direccionModificar; 
	}
}
