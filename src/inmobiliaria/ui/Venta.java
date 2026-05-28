package inmobiliaria.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

import inmobiliaria.util.ConexionDB;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Venta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;
	private JTextField txtBusqueda;
	private JComboBox cmbBusqueda;
	private JButton btnBuscar, btnRecibo, btnRegresar;
	
	public static int idUsuario = Login.IdUsuario;
	private ConexionDB conexion = new ConexionDB();

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Venta frame = new Venta();
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
	public Venta() {
		setTitle("Módulo de Ventas e Inmuebles Disponibles");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBorder(new TitledBorder(null, "Buscar Inmuebles en Venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBusqueda.setBounds(20, 20, 770, 65);
		contentPane.add(panelBusqueda);
		panelBusqueda.setLayout(null);

		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(15, 25, 280, 23);
		panelBusqueda.add(txtBusqueda);
		txtBusqueda.setColumns(10);

		cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"Ubicacion","Dimension", "Tipo" ,"Precio"}));
		cmbBusqueda.setBounds(315, 25, 160, 23);
		panelBusqueda.add(cmbBusqueda);

		//BUSCAR
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String texto = txtBusqueda.getText().trim();
				String criterio = cmbBusqueda.getSelectedItem().toString();
				
				//Filtramos por el criterio seleccionado pero obliga a que el estado siga siendo En venta
				String sql = "SELECT * FROM propiedades WHERE " + criterio + " LIKE '%" + texto + "%' AND Estado='En Venta'";
				
				conexion.conectar();
				DefaultTableModel modeloFiltrado = conexion.consulta(sql);
				actualizarDisenoTabla(modeloFiltrado);
				conexion.cerrar();
			}
		});
		btnBuscar.setBounds(500, 24, 110, 25);
		panelBusqueda.add(btnBuscar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 100, 610, 320);
		contentPane.add(scrollPane);

		tabla = new JTable();
		scrollPane.setViewportView(tabla);
		cargarDatos();

		JPanel panelAcciones = new JPanel();
		panelAcciones.setBorder(new TitledBorder(null, "Operaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAcciones.setBounds(650, 100, 140, 250);
		contentPane.add(panelAcciones);
		panelAcciones.setLayout(null);

		//GENERAR RECIBO
		btnRecibo = new JButton("Vender");
		btnRecibo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tabla.getSelectedRow();
				
				if (filaSeleccionada >= 0) {
					//Extraemos los datos de la fila seleccionada
					String idPropiedad = tabla.getModel().getValueAt(filaSeleccionada, 0).toString();
					String dimension = tabla.getModel().getValueAt(filaSeleccionada, 1).toString();
					String precio = tabla.getModel().getValueAt(filaSeleccionada, 2).toString();
					String ubicacion = tabla.getModel().getValueAt(filaSeleccionada, 3).toString();
					String tipo = tabla.getModel().getValueAt(filaSeleccionada, 5).toString();

					//Ejecutamos el Jdialog SeleccionarClienteVenta
					SeleccionarClienteVenta ventanaSeleccion = new SeleccionarClienteVenta();
					ventanaSeleccion.setModal(true); //Hace que el Jframe se congele para esperar los datos
					ventanaSeleccion.setVisible(true);

					//Si el usuario confirmó la venta en el Dialog, procedemos a guardar en la BD
					if (ventanaSeleccion.guardadoCorrecto()) {
						//Jalamos los datos usando los métodos get del JDialog
						String idCliente = ventanaSeleccion.getIdComprador();
						String clienteNombre = ventanaSeleccion.getNombreComprador();
						String clienteTelefono = ventanaSeleccion.getTelefonoComprador();
						String fecha = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());

						//Cambiamos el estado de la propiedad a Vendido en la BD y guardamos la venta
						String sentenciaEstado = "UPDATE propiedades SET Estado='Vendido' WHERE id=" + idPropiedad;
						String sentenciaVenta = "INSERT INTO venta (precio_final, id_usuario, id_propiedad, id_cliente, fecha) " + "VALUES (" + precio + "," + idUsuario + ", " + idPropiedad + ", " + idCliente + ", Now())";
						conexion.conectar();
						conexion.ejecutarSentencia(sentenciaEstado);
						conexion.ejecutarSentencia(sentenciaVenta);
						conexion.cerrar();

						//Generamos el Ticket						
						String ticket = "╔════════════════════════════════════════════════╗\n";
						ticket += "║            TICKET DE VENTA INMOBILIARIA        ║\n";
						ticket += "╠════════════════════════════════════════════════╣\n";
						ticket += "║  Fecha: " + fecha + "\n";
						ticket += "║\n";
						ticket += "║  CLIENTE COMPRADOR:\n";
						ticket += "║  " + clienteNombre.toUpperCase() + "\n";
						ticket += "║  Tel: " + clienteTelefono + "\n";
						ticket += "║\n";
						ticket += "║  DETALLES DEL INMUEBLE:\n";
						ticket += "║  Inmueble:  " + tipo + " (" + dimension + " m²)\n";
						ticket += "║  Ubicación: " + ubicacion + "\n";
						ticket += "║  Precio total: $" + precio + "\n";
						ticket += "║\n";
						ticket += "╠════════════════════════════════════════════════╣\n";
						ticket += "║         ¡PROPIEDAD ADQUIRIDA CON ÉXITO!        ║\n";
						ticket += "╚════════════════════════════════════════════════╝";
						
						JTextArea textArea = new JTextArea(ticket);
						textArea.setEditable(false);
						textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
						
						JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Comprobante de Operación", JOptionPane.INFORMATION_MESSAGE);
						
						//Recargar los datos en la tabla
						cargarDatos();
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione de la lista la propiedad que desea vender.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnRecibo.setBounds(10, 30, 120, 45);
		panelAcciones.add(btnRecibo);

		btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu ventanaMenu = new menu();
				ventanaMenu.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(660, 395, 120, 25);
		contentPane.add(btnRegresar);
	}

	public void cargarDatos() {
		conexion.conectar();
		DefaultTableModel modeloOriginal = conexion.consulta("SELECT * FROM propiedades WHERE Estado='En Venta'");
		actualizarDisenoTabla(modeloOriginal);
		conexion.cerrar();
	}


	private void actualizarDisenoTabla(DefaultTableModel nuevoModelo) {
		DefaultTableModel modeloNoEditable = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; 
			}
		};

		//Copiar las columnas de la consulta
		for (int i = 0; i < nuevoModelo.getColumnCount(); i++) {
			modeloNoEditable.addColumn(nuevoModelo.getColumnName(i));
		}
		
		//Copiar las filas de la consulta
		for (int i = 0; i < nuevoModelo.getRowCount(); i++) {
			Object[] fila = new Object[nuevoModelo.getColumnCount()];
			for (int j = 0; j < nuevoModelo.getColumnCount(); j++) {
				fila[j] = nuevoModelo.getValueAt(i, j);
			}
			modeloNoEditable.addRow(fila);
		}

		tabla.setModel(modeloNoEditable);

		//Configuración básica
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowSelectionAllowed(true);
		tabla.setFillsViewportHeight(true);

		//Ocultar el ID
		if (tabla.getColumnCount() > 0) {
			tabla.getColumnModel().getColumn(0).setMaxWidth(0);
			tabla.getColumnModel().getColumn(0).setMinWidth(0);
			tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
			tabla.getColumnModel().getColumn(0).setResizable(false);
		}
	}
}
