package inmobiliaria.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class Venta extends JFrame {

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
		setTitle("Venta de propiedades");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 653, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(350, 75, 159, 18);
		contentPane.add(txtBusqueda);
		txtBusqueda.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Venta propiedades");
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 40));
		lblNewLabel.setBounds(166, 10, 323, 42);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Búsqueda por:");
		lblNewLabel_1.setFont(new Font("Tw Cen MT", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 78, 106, 12);
		contentPane.add(lblNewLabel_1);
		
		JComboBox cmbBusqueda = new JComboBox();
		cmbBusqueda.setFont(new Font("Tw Cen MT", Font.PLAIN, 14));
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"Presupuesto", "Ubicación", "Dimensiones"}));
		cmbBusqueda.setBounds(111, 74, 116, 20);
		contentPane.add(cmbBusqueda);
		
		JLabel lblNewLabel_2 = new JLabel("Esto cambia");
		lblNewLabel_2.setFont(new Font("Tw Cen MT", Font.BOLD, 14));
		lblNewLabel_2.setBounds(251, 78, 89, 12);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 120, 594, 241);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tw Cen MT", Font.BOLD, 14));
		btnBuscar.setBounds(531, 65, 85, 29);
		contentPane.add(btnBuscar);
		
		JButton btnAñadir = new JButton("Añadir");
		btnAñadir.setFont(new Font("Tw Cen MT", Font.BOLD, 14));
		btnAñadir.setBounds(502, 373, 84, 29);
		contentPane.add(btnAñadir);

	}
}
