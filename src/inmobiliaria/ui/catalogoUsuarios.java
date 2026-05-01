package inmobiliaria.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;

public class catalogoUsuarios extends JFrame {

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
	public catalogoUsuarios() {
		setTitle("Catalogo de usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCatalogoUsuarios = new JLabel("Catálogo de Usuarios");
		lblCatalogoUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCatalogoUsuarios.setBounds(23, 10, 223, 25);
		contentPane.add(lblCatalogoUsuarios);
		
		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(23, 45, 283, 18);
		contentPane.add(txtBusqueda);
		txtBusqueda.setColumns(10);
		
		JButton btnBusqueda = new JButton("Buscar");
		btnBusqueda.setBounds(424, 44, 84, 20);
		contentPane.add(btnBusqueda);
		
		JComboBox cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"Nombre", "Rol", "Correo"}));
		cmbBusqueda.setBounds(316, 44, 98, 20);
		contentPane.add(cmbBusqueda);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 73, 485, 303);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(540, 109, 98, 38);
		contentPane.add(btnAgregar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(540, 182, 98, 38);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(540, 253, 98, 38);
		contentPane.add(btnEliminar);

	}
}
