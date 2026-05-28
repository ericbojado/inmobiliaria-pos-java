package inmobiliaria.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.toedter.calendar.JDateChooser;
import javax.swing.JScrollPane;
import inmobiliaria.util.ConexionDB;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistroVentas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField vendedor;
	private JTable tabla;
	private JLabel resumenventas;
	private JLabel lblTotalDinero;
	
	private ConexionDB conexion = new ConexionDB();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistroVentas frame = new RegistroVentas();
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
	public RegistroVentas() {
		setTitle("Historial de Ventas y Cierre de Caja");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblTitulo = new JLabel("Cierre de caja:");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(20, 10, 300, 25);
		contentPane.add(lblTitulo);

		JPanel panelFiltros = new JPanel();
		panelFiltros.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Filtros de Búsqueda", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelFiltros.setBounds(20, 45, 740, 100);
		contentPane.add(panelFiltros);
		panelFiltros.setLayout(null);

		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVendedor.setBounds(20, 25, 80, 20);
		panelFiltros.add(lblVendedor);

		vendedor = new JTextField();
		vendedor.setBounds(100, 25, 200, 22);
		panelFiltros.add(vendedor);
		vendedor.setColumns(10);

		JLabel lblPeriodo = new JLabel("Periodo:");
		lblPeriodo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPeriodo.setBounds(20, 60, 80, 20);
		panelFiltros.add(lblPeriodo);

		JLabel lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(100, 60, 46, 20);
		panelFiltros.add(lblDesde);

		JDateChooser calendariodesde = new JDateChooser();
		calendariodesde.setDateFormatString("dd/MM/yyyy");
		calendariodesde.setBounds(150, 60, 120, 22);
		calendariodesde.setDate(new java.util.Date());
		calendariodesde.setMaxSelectableDate(new java.util.Date());
		panelFiltros.add(calendariodesde);

		JLabel lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(290, 60, 46, 20);
		panelFiltros.add(lblHasta);

		JDateChooser calendariohasta = new JDateChooser();
		calendariohasta.setDateFormatString("dd/MM/yyyy");
		calendariohasta.setBounds(340, 60, 120, 22);
		calendariohasta.setDate(new java.util.Date());
		calendariohasta.setMaxSelectableDate(new java.util.Date());
		panelFiltros.add(calendariohasta);

		//Buscar
		JButton btnBusqueda = new JButton("Buscar");
		btnBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBusqueda.setBounds(580, 55, 120, 30);
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.util.Date fechaDesde = calendariodesde.getDate();
				java.util.Date fechaHasta = calendariohasta.getDate();;
							
				//Validación para evitar que los campos de fecha estén vacíos
				if (fechaDesde == null || fechaHasta == null) {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione ambas fechas para realizar el filtrado.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//Validación para evitar que la fecha inicial sea posterior a la final
				if (fechaDesde.after(fechaHasta)) {
					JOptionPane.showMessageDialog(null, "La fecha de inicio 'Desde' no puede ser posterior a la fecha 'Hasta'.", "Rango Inválido", JOptionPane.ERROR_MESSAGE);
					return;
				}

				java.text.SimpleDateFormat formatoAccess = new java.text.SimpleDateFormat("yyyy-MM-dd");
				String Desde = formatoAccess.format(fechaDesde);
				String Hasta = formatoAccess.format(fechaHasta);

				//Sentencia con un INNER JOIN triple para traer los datos de las ID Foráneas
				String sql = "SELECT v.id, u.nombre, (c.Nombre & ' ' & c.Apellido) AS Cliente, v.precio_final, v.fecha "
				           + "FROM (venta AS v "
				           + "INNER JOIN usuario AS u ON v.id_usuario = u.id) "
				           + "INNER JOIN cliente AS c ON v.id_cliente = c.id "
				           + "WHERE v.fecha BETWEEN #" + Desde + " 00:00:00# AND #" + Hasta + " 23:59:59#";

				//Busqueda por vendedor
				String txtVendedor = vendedor.getText().trim();
				if (!txtVendedor.isEmpty()) {
					sql += " AND u.nombre LIKE '%" + txtVendedor + "%'";
				}

				conexion.conectar();
				DefaultTableModel modeloBusqueda = conexion.consulta(sql);
				actualizarDisenoTabla(modeloBusqueda);
				conexion.cerrar();
			}
		});
		panelFiltros.add(btnBusqueda);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 160, 740, 220);
		contentPane.add(scrollPane);

		tabla = new JTable();
		scrollPane.setViewportView(tabla);

		//REGRESAR
		JButton regresar = new JButton("Regresar al Menú");
		regresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu Ventana = new menu();
				Ventana.setVisible(true);
				dispose();
			}
		});
		regresar.setFont(new Font("Tahoma", Font.BOLD, 12));
		regresar.setBounds(20, 500, 160, 35);
		contentPane.add(regresar);

		//PANEL DE RESUMEN
		JPanel panelResumen = new JPanel();
		panelResumen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelResumen.setBounds(453, 400, 307, 135);
		contentPane.add(panelResumen);
		panelResumen.setLayout(null);

		JLabel lblResumenTitulo = new JLabel("Resumen de Caja");
		lblResumenTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblResumenTitulo.setBounds(10, 10, 180, 20);
		panelResumen.add(lblResumenTitulo);

		resumenventas = new JLabel("Ventas registradas: 0");
		resumenventas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		resumenventas.setBounds(10, 28, 180, 20);
		panelResumen.add(resumenventas);
				
		JLabel lblTotalTexto = new JLabel("TOTAL VENTAS:");
		lblTotalTexto.setBounds(-43, 58, 160, 30);
		panelResumen.add(lblTotalTexto);
		lblTotalTexto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalTexto.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		lblTotalDinero = new JLabel("$0.00");
		lblTotalDinero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTotalDinero.setBounds(131, 67, 166, 12);
		panelResumen.add(lblTotalDinero);
		
		cargarDatos();
	}

	//Método para cargar las ventas totales registradas
	public void cargarDatos() {
		conexion.conectar();
		String sqlOriginal = "SELECT v.id, u.nombre, (c.Nombre & ' ' & c.Apellido) AS Cliente, v.precio_final, v.fecha "
		                   + "FROM (venta AS v "
		                   + "INNER JOIN usuario AS u ON v.id_usuario = u.id) "
		                   + "INNER JOIN cliente AS c ON v.id_cliente = c.id";
		DefaultTableModel modeloDB = conexion.consulta(sqlOriginal);
		actualizarDisenoTabla(modeloDB);
		conexion.cerrar();
	}

	//Método para configurar el modelo de la tabla, ocultar IDs y realizar cálculos del resumen
	private void actualizarDisenoTabla(DefaultTableModel nuevoModelo) {
		DefaultTableModel modelo = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; 
			}
		};

		// Configuración manual de las cabeceras de columnas deseadas en tu interfaz
		modelo.addColumn("ID Venta");
		modelo.addColumn("Vendedor");
		modelo.addColumn("Cliente");
		modelo.addColumn("Monto");
		modelo.addColumn("Fecha");

		double acumuladorDinero = 0.0;
		int totalRegistros = nuevoModelo.getRowCount();

		//Recorremos las filas
		for (int i = 0; i < totalRegistros; i++) {
			Object[] fila = new Object[5];
			fila[0] = nuevoModelo.getValueAt(i, 0); //id venta
			fila[1] = nuevoModelo.getValueAt(i, 1); //nombre vendedor
			fila[2] = nuevoModelo.getValueAt(i, 2); //nombre completo cliente
			
			//Procesamos el dinero, extraemos los caracteres numéricos si es que viene guardado con "$" o comas
			String Monto = nuevoModelo.getValueAt(i, 3).toString();
			fila[3] = Monto; //Se guarda visualmente tal cual en la tabla
			
			try {
				String limpio = Monto.replace("$", "").replace(",", "").trim();
				acumuladorDinero += Double.parseDouble(limpio);
				
				//Formateamos con signo de pesos y comas para miles
			    fila[3] = String.format("$%,.2f", Double.parseDouble(Monto));
			} catch (Exception ex) {
				fila[3] = Monto; //En caso de fallo, deja el texto original
			}
			
			fila[4] = nuevoModelo.getValueAt(i, 4); //fecha de la venta
			modelo.addRow(fila);
		}

		tabla.setModel(modelo);

		//Configuraciones Básicas
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowSelectionAllowed(true);
		tabla.setFillsViewportHeight(true);

		// Ocultar la columna del Id
		if (tabla.getColumnCount() > 0) {
			tabla.getColumnModel().getColumn(0).setMaxWidth(0);
			tabla.getColumnModel().getColumn(0).setMinWidth(0);
			tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
			tabla.getColumnModel().getColumn(0).setResizable(false);
		}

		//Actualizacion de los Labels
		resumenventas.setText("Ventas registradas: " + totalRegistros);
		lblTotalDinero.setText("$" + String.format("%,.2f", acumuladorDinero));
	}
}