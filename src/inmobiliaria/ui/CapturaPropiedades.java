package inmobiliaria.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import inmobiliaria.util.ConexionDB;

import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CapturaPropiedades extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField TXtDireccion;
	private JTextField TxtPrecio;
	private JTextField TxtDimensiones;
	private JTable TablaRegistros;
	private JTextField TxtVendedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CapturaPropiedades frame = new CapturaPropiedades();
					frame.setLocationRelativeTo(null);
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
	public void mostrarDatosEnTabla() {
	        ConexionDB bd = new ConexionDB();
	        bd.conectar();
	        TablaRegistros.setModel(bd.consulta("SELECT * FROM propiedades"));
	        bd.cerrar();
	}
	public CapturaPropiedades() {
		setResizable(false);
		
		setTitle("Captura de Propiedades");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Agregar Propiedad");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 10, 200, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Direccion:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 40, 56, 13);
		contentPane.add(lblNewLabel_1);
		
		TXtDireccion = new JTextField();
		TXtDireccion.setBounds(86, 38, 200, 19);
		contentPane.add(TXtDireccion);
		TXtDireccion.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Precio:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(10, 66, 56, 13);
		contentPane.add(lblNewLabel_1_1);
		
		TxtPrecio = new JTextField();
		TxtPrecio.setColumns(10);
		TxtPrecio.setBounds(86, 66, 200, 19);
		contentPane.add(TxtPrecio);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("TIpo:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1_1.setBounds(10, 89, 56, 13);
		contentPane.add(lblNewLabel_1_1_1);
		
		JComboBox CmbTipo = new JComboBox();
		CmbTipo.setModel(new DefaultComboBoxModel(new String[] {"Casa", "Departamento", "Terreno"}));
		CmbTipo.setBounds(86, 92, 200, 21);
		contentPane.add(CmbTipo);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Dimensiones:");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1_2.setBounds(10, 145, 100, 13);
		contentPane.add(lblNewLabel_1_1_2);
		
		TxtDimensiones = new JTextField();
		TxtDimensiones.setColumns(10);
		TxtDimensiones.setBounds(86, 143, 200, 19);
		contentPane.add(TxtDimensiones);
		
		JButton BtnGuardar = new JButton("Guardar");
		BtnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String direccion = TXtDireccion.getText();
		        String precio = TxtPrecio.getText();
		        String tipo = CmbTipo.getSelectedItem().toString();
		        String dimensiones = TxtDimensiones.getText();
		        String vendedor = TxtVendedor.getText();
		        
		        String sql = "INSERT INTO propiedades(ubicacion, precio, tipo, dimension, estado)"+"VALUES('" + direccion + "', '" + precio + "', '" + tipo + "', '" + dimensiones + "', 'En Venta')";
		        ConexionDB bd = new ConexionDB();
		        bd.conectar();
		        bd.ejecutarSentencia(sql);
		        bd.cerrar();
		        
		        JOptionPane.showMessageDialog(null, "¡Propiedad guardada con éxito!");
		        mostrarDatosEnTabla();
		        TXtDireccion.setText("");
		        TxtPrecio.setText("");
		        TxtDimensiones.setText("");
		        TxtVendedor.setText("");
		        CmbTipo.setSelectedIndex(0);
			}
		});
		BtnGuardar.setBounds(10, 265, 85, 31);
		contentPane.add(BtnGuardar);
		
		JButton BtnLimpiar = new JButton("Limpiar");
		BtnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TXtDireccion.setText("");      
				TxtPrecio.setText("");         
				TxtDimensiones.setText("");    
				TxtVendedor.setText("");       
				CmbTipo.setSelectedIndex(0);   
			}
		});
		BtnLimpiar.setBounds(105, 265, 85, 31);
		contentPane.add(BtnLimpiar);
		
		JButton BtnSalir = new JButton("Salir");
		BtnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu Ventana = new menu();
		        
		        Ventana.setVisible(true);
		        dispose();
			}
		});
		BtnSalir.setBounds(200, 265, 85, 31);
		contentPane.add(BtnSalir);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(312, 28, 475, 268);
		contentPane.add(scrollPane);
		TablaRegistros = new JTable();
		scrollPane.setViewportView(TablaRegistros);
		
		// e
		String[] columnas = {"Dirección", "Precio", "Tipo", "Vendedor"};
		DefaultTableModel modelo = new DefaultTableModel(null, columnas);
		TablaRegistros.setModel(modelo);
		
		JLabel lblInventario = new JLabel("Inventario");
		lblInventario.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblInventario.setBounds(313, 2, 200, 20);
		contentPane.add(lblInventario);
		
		TxtVendedor = new JTextField();
		TxtVendedor.setColumns(10);
		TxtVendedor.setBounds(86, 172, 200, 19);
		contentPane.add(TxtVendedor);
		
		JLabel lblNewLabel_1_2 = new JLabel("Vendedor:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2.setBounds(10, 174, 85, 13);
		contentPane.add(lblNewLabel_1_2);
		mostrarDatosEnTabla();
		

	}
}
