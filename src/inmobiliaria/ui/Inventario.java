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

public class Inventario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBusqueda;
	private JTable table;

	// Variables de utilidad global
	private ConexionDB conexion = new ConexionDB();
	
	// VARIABLES ESTÁTICAS GLOBALIZADAS (Para comunicar con ModificarPropiedad)
	private static String idModificar;
	private static String dimensionModificar;
	private static String ubicacionModificar;
	private static String precioModificar;
	private static String tipoModificar;
	private static String estadoModificar;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inventario frame = new Inventario();
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
	
	public Inventario() {
		setTitle("Inventario de Propiedades");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 485);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblTitulo = new JLabel("Inventario de Propiedades");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setBounds(20, 15, 300, 25);
		contentPane.add(lblTitulo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 110, 590, 310);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		cargarDatos();

		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBorder(new TitledBorder(null, "Búsqueda de Propiedades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBusqueda.setBounds(20, 50, 590, 50);
		contentPane.add(panelBusqueda);
		panelBusqueda.setLayout(null);

		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(10, 20, 260, 20);
		panelBusqueda.add(txtBusqueda);
		txtBusqueda.setColumns(10);

		JComboBox comboCriterio = new JComboBox();
		comboCriterio.setModel(new DefaultComboBoxModel(new String[] {"Ubicacion", "Dimension", "Tipo", "Estado"}));
		comboCriterio.setBounds(290, 20, 150, 20);
		panelBusqueda.add(comboCriterio);

		//BUSCAR
		JButton btnBusqueda = new JButton("Buscar");
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String criterio = txtBusqueda.getText().trim();
				String columnaBusqueda = comboCriterio.getSelectedItem().toString();
				
				//Sentencia
				String sentencia = "SELECT * FROM propiedades WHERE " + columnaBusqueda + " LIKE '%" + criterio + "%'";
				
				conexion.conectar();
				DefaultTableModel modeloBusqueda = conexion.consulta(sentencia);
				actualizarDisenoTabla(modeloBusqueda);
				conexion.cerrar();
			}
		});
		btnBusqueda.setBounds(465, 19, 110, 23);
		panelBusqueda.add(btnBusqueda);

		JPanel panelAcciones = new JPanel();
		panelAcciones.setBorder(new TitledBorder(null, "Acciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAcciones.setBounds(635, 50, 145, 315);
		contentPane.add(panelAcciones);
		panelAcciones.setLayout(null);

		//AGREGAR
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarPropiedad ventanaAgregar = new AgregarPropiedad();
				ventanaAgregar.setModal(true);
				ventanaAgregar.setVisible(true);
				
				//Validar que se hayan agredado correctamente los datos en el JDialog, y guardar lso datos en la BD.
				if(ventanaAgregar.guardadoCorrecto()) {
					String dimension = ventanaAgregar.traerDimension();
					String ubicacion = ventanaAgregar.traerUbicacion();
					String precio = ventanaAgregar.traerPrecio();
					String tipo = ventanaAgregar.traerTipo();
					String estado = ventanaAgregar.traerEstado();
					
					String sentencia = "INSERT INTO propiedades (Dimension, Ubicacion, Precio, Tipo, Estado) "
									 + "VALUES ('" + dimension + "', '" + ubicacion + "', '" + precio + "', '" + tipo + "', '" + estado + "')";
					
					conexion.conectar();
					conexion.ejecutarSentencia(sentencia);
					conexion.cerrar();
					
					JOptionPane.showMessageDialog(null, "Propiedad registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					cargarDatos(); //Recargar JTable
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
					//Guardamos los datos actuales en las variables globales
					idModificar = table.getModel().getValueAt(filaSeleccionada, 0).toString();
					dimensionModificar = table.getModel().getValueAt(filaSeleccionada, 1).toString();
					precioModificar = table.getModel().getValueAt(filaSeleccionada, 2).toString();
					ubicacionModificar = table.getModel().getValueAt(filaSeleccionada, 3).toString();
					estadoModificar = table.getModel().getValueAt(filaSeleccionada, 4).toString();
					tipoModificar = table.getModel().getValueAt(filaSeleccionada, 5).toString();

					//Abrimos el JDialog
					ModificarPropiedad ventanaModificar = new ModificarPropiedad();
					ventanaModificar.setModal(true);
					ventanaModificar.setVisible(true);
					
					if (ventanaModificar.guardadoCorrecto()) {
						String nuevaDim = ventanaModificar.getDimensionModificado();
						String nuevaUbi = ventanaModificar.getUbicacionModificado();
						String nuevoPre = ventanaModificar.getPrecioModificado();
						String nuevoTip = ventanaModificar.getTipoModificado();
						String nuevoEst = ventanaModificar.getEstadoModificado();
						
						String sentencia = "UPDATE propiedades SET Dimension='" + nuevaDim + "', Ubicacion='" + nuevaUbi + "', "
										 + "Precio='" + nuevoPre + "', Tipo='" + nuevoTip + "', Estado='" + nuevoEst + "' WHERE id=" + idModificar;
						
						conexion.conectar();
						conexion.ejecutarSentencia(sentencia);
						conexion.cerrar();
						
						JOptionPane.showMessageDialog(null, "Datos de la propiedad actualizados.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						cargarDatos();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione una propiedad de la tabla para modificar.", "Atención", JOptionPane.WARNING_MESSAGE);
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
				String[] opc = {"Sí", "No"};//Esto es para que los botones del JOptionPane estén en español.
				
				if(filaSeleccionada >= 0) {
					int confirmar = JOptionPane.showOptionDialog(null, 
							"¿Está seguro de eliminar esta propiedad?\nEsta acción eliminará el registro de la base de datos.", 
							"Eliminar Propiedad", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, opc, opc[0]);
					
					if(confirmar == JOptionPane.YES_OPTION) {
						String id = table.getModel().getValueAt(filaSeleccionada, 0).toString();
						String sentencia = "DELETE FROM propiedades WHERE id = " + id;
						
						conexion.conectar();
						conexion.ejecutarSentencia(sentencia);
						conexion.cerrar();
						
						JOptionPane.showMessageDialog(null, "Propiedad eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						cargarDatos();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione una propiedad para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnEliminar.setBounds(15, 170, 115, 40);
		panelAcciones.add(btnEliminar);

		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu ventanaMenu = new menu();
				ventanaMenu.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(650, 397, 115, 23);
		contentPane.add(btnRegresar);
		
		//Validación, si es vendedor no puede Ag, Md, o El
		if (Login.RolUsuario.equals("Vendedor")) {
			btnAgregar.setVisible(false);
			btnEliminar.setVisible(false);
			btnModificar.setVisible(false);
		}
	}

	//Método para cargar los datos de la tabla
	public void cargarDatos() {
		conexion.conectar();
		DefaultTableModel modeloDB = conexion.consulta("SELECT * FROM propiedades");
		actualizarDisenoTabla(modeloDB);
		conexion.cerrar();
	}

	//Método para aplicar configuraziones a la tabla
	private void actualizarDisenoTabla(DefaultTableModel nuevoModelo) {
		DefaultTableModel modeloNoEditable = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; //Evita modificaciones directas en celdas
			}
		};

		//Clonación de columnas
		for (int i = 0; i < nuevoModelo.getColumnCount(); i++) {
			modeloNoEditable.addColumn(nuevoModelo.getColumnName(i));
		}
		
		//Clonación de filas
		for (int i = 0; i < nuevoModelo.getRowCount(); i++) {
			Object[] fila = new Object[nuevoModelo.getColumnCount()];
			for (int j = 0; j < nuevoModelo.getColumnCount(); j++) {
				fila[j] = nuevoModelo.getValueAt(i, j);
			}
			modeloNoEditable.addRow(fila);
		}

		table.setModel(modeloNoEditable);

		//Configuración Básicas
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

	//Getters para que los copie la ventana de modificar
	public static String getId() { 
		return idModificar; 
	}
	public static String getDimension() { 
		return dimensionModificar; 
	}
	public static String getUbicacion() { 
		return ubicacionModificar; 
	}
	public static String getPrecio() { 
		return precioModificar; 
	}
	public static String getTipo() { 
		return tipoModificar; 
	}
	public static String getEstado() { 
		return estadoModificar;
	}
}
