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
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class registro_ventas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField vendedor;
	private JTable tabla;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					registro_ventas frame = new registro_ventas();
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
	public registro_ventas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Se aumentó el tamaño de la ventana para acomodar el nuevo diseño
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// --- TÍTULO O ETIQUETA PRINCIPAL ---
		JLabel lblTitulo = new JLabel("Cierre de caja:");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(20, 10, 300, 25);
		contentPane.add(lblTitulo);

		// --- PANEL DE FILTROS ---
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

		// AQUÍ ESTÁ TU CALENDARIO (Ajustado como "Desde")
		JDateChooser calendariodesde = new JDateChooser();
		calendariodesde.setDateFormatString("dd/MM/yyyy");
		calendariodesde.setBounds(150, 60, 120, 22);
		
		// --- NUEVO: Poner fecha de hoy y bloquear fechas futuras ---
		calendariodesde.setDate(new java.util.Date());
		calendariodesde.setMaxSelectableDate(new java.util.Date());
		
		panelFiltros.add(calendariodesde);

		JLabel lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(290, 60, 46, 20);
		panelFiltros.add(lblHasta);

		// SEGUNDO CALENDARIO (Para el rango "Hasta")
		JDateChooser calendariohasta = new JDateChooser();
		calendariohasta.setDateFormatString("dd/MM/yyyy");
		calendariohasta.setBounds(340, 60, 120, 22);
		
		// --- NUEVO: Poner fecha de hoy y bloquear fechas futuras ---
		calendariohasta.setDate(new java.util.Date());
		calendariohasta.setMaxSelectableDate(new java.util.Date());
		
		panelFiltros.add(calendariohasta);

		JButton consulta = new JButton("Consultar");
		consulta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		consulta.setBounds(580, 55, 120, 30);
		
		// --- NUEVO: Acción para leer los calendarios al presionar el botón ---
		consulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.util.Date fecha1 = calendariodesde.getDate();
				java.util.Date fecha2 = calendariohasta.getDate();
				
				if(fecha1 != null && fecha2 != null) {
					// Formateamos para que se vea legible en la consola (o para tu Base de Datos)
					java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd/MM/yyyy");
					String desdeStr = formato.format(fecha1);
					String hastaStr = formato.format(fecha2);
					
					System.out.println("--- NUEVA CONSULTA ---");
					System.out.println("Vendedor escrito: " + vendedor.getText());
					System.out.println("Buscando ventas desde: " + desdeStr + " hasta: " + hastaStr);
				} else {
					System.out.println("Error: Faltan fechas por seleccionar.");
				}
			}
		});
		
		panelFiltros.add(consulta);

		// --- TABLA DE DATOS ---
		JScrollPane panel1 = new JScrollPane();
		panel1.setBounds(20, 160, 740, 220);
		contentPane.add(panel1);

		tabla = new JTable();
		tabla.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"ID Venta", "Vendedor", "Cliente", "Monto", "Fecha"
			}
		));
		panel1.setViewportView(tabla);

		// --- TOTALES Y BOTÓN REGRESAR ---
		JButton regresar = new JButton("Regresar al Menú");
		regresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		regresar.setFont(new Font("Tahoma", Font.BOLD, 12));
		regresar.setBounds(20, 500, 160, 35);
		contentPane.add(regresar);

		// --- PANEL DE RESUMEN ---
		JPanel panelResumen = new JPanel();
		panelResumen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelResumen.setBounds(560, 400, 200, 135);
		contentPane.add(panelResumen);
		panelResumen.setLayout(null);

		JLabel lblResumenTitulo = new JLabel("Resumen de Caja");
		lblResumenTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblResumenTitulo.setBounds(10, 10, 180, 20);
		panelResumen.add(lblResumenTitulo);

		JLabel resumenventas = new JLabel("Ventas registradas: 0");
		resumenventas.setFont(new Font("Tahoma", Font.PLAIN, 13));
		resumenventas.setBounds(10, 28, 180, 20);
		panelResumen.add(resumenventas);
				
						JLabel lblTotalTexto = new JLabel("TOTAL VENTAS:");
						lblTotalTexto.setBounds(-43, 58, 160, 30);
						panelResumen.add(lblTotalTexto);
						lblTotalTexto.setHorizontalAlignment(SwingConstants.RIGHT);
						lblTotalTexto.setFont(new Font("Tahoma", Font.BOLD, 13));
						
						JLabel lblNewLabel = new JLabel("0:00");
						lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
						lblNewLabel.setBounds(131, 67, 44, 12);
						panelResumen.add(lblNewLabel);
	}
}