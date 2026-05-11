package inmobiliaria.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JEditorPane;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

public class Catalogo_Cliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField texCliente;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Catalogo_Cliente frame = new Catalogo_Cliente();
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
	
	//Datos_para_la_tabla
	DefaultTableModel modelo = new DefaultTableModel();
	
	public Catalogo_Cliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Catalogo de Clientes");
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 18));
		lblNewLabel.setBounds(160, 11, 177, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Buscar Cliente");
		lblNewLabel_1.setBounds(10, 48, 95, 14);
		contentPane.add(lblNewLabel_1);
		
		texCliente = new JTextField();
		texCliente.setBounds(115, 45, 86, 20);
		contentPane.add(texCliente);
		texCliente.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Direccion:");
		lblNewLabel_2.setBounds(211, 48, 71, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(396, 44, 89, 23);
		contentPane.add(btnBuscar);
		
		JComboBox BoxCategoria = new JComboBox();
		BoxCategoria.setBounds(292, 44, 83, 22);
		contentPane.add(BoxCategoria);
		
		JScrollPane scrLista = new JScrollPane();
		scrLista.setBounds(28, 84, 352, 164);
		contentPane.add(scrLista);
		
		//Columnas
				modelo.addColumn("Nombre");
				modelo.addColumn("Apellido");
				modelo.addColumn("Teléfono");
				modelo.addColumn("Dirección");
				
		
		table = new JTable(modelo);
		scrLista.setViewportView(table);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(396, 129, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(396, 315, 89, 23);
		contentPane.add(btnAgregar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(396, 200, 89, 23);
		contentPane.add(btnEliminar);
		
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.setBounds(383, 394, 102, 23);
		contentPane.add(btnRegresar);
		
		textField = new JTextField();
		textField.setBounds(89, 285, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(89, 328, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(274, 285, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(274, 328, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Nombre:");
		lblNewLabel_3.setBounds(10, 288, 69, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Apellido:");
		lblNewLabel_4.setBounds(10, 331, 55, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Telefono:");
		lblNewLabel_5.setBounds(184, 288, 64, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Dirección:");
		lblNewLabel_6.setBounds(185, 331, 71, 14);
		contentPane.add(lblNewLabel_6);

	}
}
