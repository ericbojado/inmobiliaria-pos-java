package inmobiliaria.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class inventario extends JFrame {

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
		
		//Registros de prueba
		modelo.addRow(new Object[] {"1", "160", "$1,850,000.00", "Colima - Residencial Esmeralda", "Vendido", "Casa"});
		modelo.addRow(new Object[] {"1", "160", "$1,850,000.00", "Colima - Residencial Esmeralda", "Vendido", "Casa"});
		modelo.addRow(new Object[] {"2", "120", "$1,200,000.00", "Villa de Álvarez - Valle Real", "Disponible", "Casa"});
		modelo.addRow(new Object[] {"3", "90", "$850,000.00", "Colima - Centro", "Disponible", "Departamento"});
		modelo.addRow(new Object[] {"4", "250", "$3,100,000.00", "Comala - Campestre", "Vendido", "Casa"});
		modelo.addRow(new Object[] {"5", "300", "$1,500,000.00", "Villa de Álvarez - Tercer Anillo", "Disponible", "Terreno"});
		modelo.addRow(new Object[] {"6", "80", "$750,000.00", "Manzanillo - Salagua", "Rentado", "Departamento"});
		modelo.addRow(new Object[] {"7", "200", "$2,450,000.00", "Colima - Lomas Verdes", "Disponible", "Casa"});
		modelo.addRow(new Object[] {"8", "450", "$4,800,000.00", "Manzanillo - Las Brisas", "Vendido", "Casa"});
		modelo.addRow(new Object[] {"9", "110", "$1,150,000.00", "Villa de Álvarez - Centro", "Disponible", "Local Comercial"});
		modelo.addRow(new Object[] {"10", "140", "$1,600,000.00", "Colima - Senderos", "Apartado", "Casa"});
		modelo.addRow(new Object[] {"11", "95", "$900,000.00", "Tecomán - Centro", "Disponible", "Casa"});
		modelo.addRow(new Object[] {"12", "500", "$2,000,000.00", "Coquimatlán - Afueras", "Disponible", "Terreno"});
		modelo.addRow(new Object[] {"13", "180", "$2,100,000.00", "Colima - Residencial Esmeralda", "Disponible", "Casa"});
		modelo.addRow(new Object[] {"14", "75", "$650,000.00", "Villa de Álvarez - La Reserva", "Rentado", "Departamento"});
		modelo.addRow(new Object[] {"15", "220", "$2,800,000.00", "Colima - Santa Bárbara", "Vendido", "Casa"});
		modelo.addRow(new Object[] {"16", "350", "$3,900,000.00", "Comala - Pueblo Mágico", "Disponible", "Casa"});
		modelo.addRow(new Object[] {"17", "130", "$1,350,000.00", "Villa de Álvarez - Senderos del Carmen", "Apartado", "Casa"});
		modelo.addRow(new Object[] {"18", "60", "$500,000.00", "Colima - Zona Sur", "Vendido", "Local Comercial"});
		modelo.addRow(new Object[] {"19", "400", "$1,800,000.00", "Cuauhtémoc - Carretera", "Disponible", "Terreno"});
		modelo.addRow(new Object[] {"20", "150", "$1,750,000.00", "Manzanillo - Valle de las Garzas", "Disponible", "Casa"});
		modelo.addRow(new Object[] {"21", "100", "$1,050,000.00", "Colima - Fovissste", "Rentado", "Casa"});
		modelo.addRow(new Object[] {"22", "85", "$950,000.00", "Villa de Álvarez - Rancho Blanco", "Disponible", "Departamento"});
		modelo.addRow(new Object[] {"23", "280", "$3,400,000.00", "Colima - Altozano", "Vendido", "Casa"});
		modelo.addRow(new Object[] {"24", "170", "$1,950,000.00", "Colima - San Ignacio", "Disponible", "Casa"});
		modelo.addRow(new Object[] {"25", "600", "$2,500,000.00", "Comala - Nogueras", "Apartado", "Terreno"});
		modelo.addRow(new Object[] {"26", "115", "$1,250,000.00", "Villa de Álvarez - Real de Minas", "Disponible", "Casa"});
		modelo.addRow(new Object[] {"27", "90", "$800,000.00", "Tecomán - Las Palmas", "Vendido", "Casa"});
		modelo.addRow(new Object[] {"28", "210", "$2,600,000.00", "Colima - Lomas de Circunvalación", "Disponible", "Casa"});
		modelo.addRow(new Object[] {"29", "125", "$1,400,000.00", "Manzanillo - Santiago", "Rentado", "Local Comercial"});
		modelo.addRow(new Object[] {"30", "190", "$2,250,000.00", "Villa de Álvarez - Puerta de Hierro", "Disponible", "Casa"});
		
		JComboBox cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"Presupuesto", "Ubicacion", "Dimensiones", "Tipo"}));
		cmbBusqueda.setBounds(426, 48, 116, 20);
		contentPane.add(cmbBusqueda);

		JButton btnBusqueda = new JButton("Buscar");
		btnBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String busquedaIngresada = txtBusqueda.getText();
				String sentenciaBusqueda = "";
				
				modelo.setRowCount(0);
				
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
				}
				
				String[] asignacionColumnas = new String[6];
				
				//PROCEDIMIENTO CON BASE DE DATOS CONECTADA
			}
		});
		btnBusqueda.setBounds(552, 48, 84, 20);
		contentPane.add(btnBusqueda);
	}
}
