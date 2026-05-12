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
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class Venta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtBusqueda;
	private JTable table;
	private JTextField txtNombreCliente;
	private JTextField txtIdPropiedad;
	private JTextField txtMonto;

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
		setTitle("Inmobiliaria Guzmán - Área de Ventas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Se ajustó el tamaño para que quepan todos los paneles cómodamente
		setBounds(100, 100, 920, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --- ENCABEZADO Y BÚSQUEDA ---
		JLabel lblTitulo = new JLabel("Área de Ventas");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTitulo.setBounds(20, 20, 200, 30);
		contentPane.add(lblTitulo);

		JLabel lblBusqueda = new JLabel("Búsqueda:");
		lblBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBusqueda.setBounds(540, 25, 70, 20);
		contentPane.add(lblBusqueda);

		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(610, 25, 150, 22);
		contentPane.add(txtBusqueda);
		txtBusqueda.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(770, 24, 100, 25);
		contentPane.add(btnBuscar);

		// --- TABLA IZQUIERDA ---
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 70, 500, 420);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", "11 may 2026", "Guzmán", "Propiedad 1", "5,000.00", "Privado"},
				{"2", "11 may 2026", "Manda", "Propiedad 2", "14,000.00", "Privado"},
				{"3", "11 may 2026", "Cliente", "Propiedad 3", "6,000.00", "Ejemplo"},
			},
			new String[] {
				"ID", "Fecha", "Cliente", "Propiedad", "Monto", "Estado"
			}
		));
		scrollPane.setViewportView(table);

		// --- PANEL PRINCIPAL DERECHO (DETALLES) ---
		JPanel panelDetalles = new JPanel();
		panelDetalles.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Detalles de Venta Actual", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDetalles.setBounds(540, 70, 330, 420);
		contentPane.add(panelDetalles);
		panelDetalles.setLayout(null);

		// 1. Sub-panel Cliente
		JPanel panelCliente = new JPanel();
		panelCliente.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCliente.setBounds(15, 25, 300, 95);
		panelDetalles.add(panelCliente);
		panelCliente.setLayout(null);

		JLabel lblNombreCliente = new JLabel("Nombre Cliente:");
		lblNombreCliente.setBounds(10, 25, 100, 20);
		panelCliente.add(lblNombreCliente);

		txtNombreCliente = new JTextField();
		txtNombreCliente.setBounds(110, 25, 175, 22);
		panelCliente.add(txtNombreCliente);
		txtNombreCliente.setColumns(10);

		JLabel lblClienteCombo = new JLabel("Cliente:");
		lblClienteCombo.setBounds(10, 60, 100, 20);
		panelCliente.add(lblClienteCombo);

		JComboBox<String> cbCliente = new JComboBox<String>();
		cbCliente.setBounds(110, 60, 175, 22);
		panelCliente.add(cbCliente);

		// 2. Sub-panel Propiedad
		JPanel panelPropiedad = new JPanel();
		panelPropiedad.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Propiedad", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPropiedad.setBounds(15, 130, 300, 95);
		panelDetalles.add(panelPropiedad);
		panelPropiedad.setLayout(null);

		JLabel lblIdPropiedad = new JLabel("ID Propiedad:");
		lblIdPropiedad.setBounds(10, 25, 100, 20);
		panelPropiedad.add(lblIdPropiedad);

		txtIdPropiedad = new JTextField();
		txtIdPropiedad.setBounds(110, 25, 175, 22);
		panelPropiedad.add(txtIdPropiedad);
		txtIdPropiedad.setColumns(10);

		JLabel lblPropiedadCombo = new JLabel("Propiedad:");
		lblPropiedadCombo.setBounds(10, 60, 100, 20);
		panelPropiedad.add(lblPropiedadCombo);

		JComboBox<String> cbPropiedad = new JComboBox<String>();
		cbPropiedad.setBounds(110, 60, 175, 22);
		panelPropiedad.add(cbPropiedad);

		// 3. Sub-panel Venta
		JPanel panelVenta = new JPanel();
		panelVenta.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Venta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelVenta.setBounds(15, 235, 300, 170);
		panelDetalles.add(panelVenta);
		panelVenta.setLayout(null);

		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setBounds(10, 60, 100, 20);
		panelVenta.add(lblMonto);

		txtMonto = new JTextField();
		txtMonto.setBounds(110, 60, 175, 22);
		panelVenta.add(txtMonto);
		txtMonto.setColumns(10);

		JButton btnNuevaVenta = new JButton("Nueva Venta");
		btnNuevaVenta.setBounds(10, 95, 130, 25);
		panelVenta.add(btnNuevaVenta);

		JButton btnGuardarVenta = new JButton("Guardar Venta");
		btnGuardarVenta.setBounds(155, 95, 130, 25);
		panelVenta.add(btnGuardarVenta);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(10, 130, 275, 25);
		panelVenta.add(btnLimpiar);

		// --- BOTONES INFERIORES ---
		JButton btnImprimirRecibo = new JButton("Imprimir Recibo");
		btnImprimirRecibo.setBounds(630, 510, 130, 30);
		contentPane.add(btnImprimirRecibo);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(770, 510, 100, 30);
		contentPane.add(btnCerrar);
	}
}