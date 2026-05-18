package inmobiliaria.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menu frame = new menu();
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
	public menu() {
		setTitle("Menú");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 341);
		setLocationRelativeTo(null);//Centrar ventana
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnInventario = new JButton("Inventario");
		btnInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inventario Ventana = new inventario();
		        
		        Ventana.setVisible(true);
		        dispose();
			}
		});
		btnInventario.setBounds(323, 95, 144, 21);
		contentPane.add(btnInventario);
		
		JButton btnUsuarios = new JButton("Usuarios");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				catalogoUsuarios Ventana = new catalogoUsuarios();
		        
		        Ventana.setVisible(true);
		        dispose();
			}
		});
		btnUsuarios.setBounds(323, 191, 144, 21);
		contentPane.add(btnUsuarios);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Catalogo_Cliente Ventana = new Catalogo_Cliente();
		        
		        Ventana.setVisible(true);
		        dispose();
			}
		});
		btnClientes.setBounds(323, 127, 144, 21);
		contentPane.add(btnClientes);
		
		JButton btnVenta = new JButton("Venta");
		btnVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Venta Ventana = new Venta();
		        
		        Ventana.setVisible(true);
		        dispose();
			}
		});
		btnVenta.setBounds(323, 63, 144, 21);
		contentPane.add(btnVenta);
		
		JButton btnHistorialVentas = new JButton("Historial de Ventas");
		btnHistorialVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*Ventas Ventana = new Ventas();
		        
		        Ventana.setVisible(true);
		        dispose();*/
			}
		});
		btnHistorialVentas.setBounds(323, 159, 144, 21);
		contentPane.add(btnHistorialVentas);
		
		JButton btnNewButton = new JButton("Cerrar Sesión");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Limpiamos la variable  del rol
		        Login.RolUsuario = null;
		        
		        Login ventanaLogin = new Login();
		        ventanaLogin.setVisible(true);
		        
		        dispose();
			}
		});
		btnNewButton.setBounds(23, 268, 144, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("INMOBILIARIA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(32, 32, 118, 14);
		contentPane.add(lblNewLabel);

		//Control de permisos dependiendo del Rol
		if (Login.RolUsuario.equals("Vendedor")) {
			btnUsuarios.setVisible(false);
		}
	}
}
