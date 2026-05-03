package inmobiliaria.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnInventario = new JButton("Inventario");
		btnInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnInventario.setBounds(323, 65, 144, 21);
		contentPane.add(btnInventario);
		
		JButton btnUsuarios = new JButton("Usuarios");
		btnUsuarios.setBounds(323, 96, 144, 21);
		contentPane.add(btnUsuarios);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.setBounds(323, 127, 144, 21);
		contentPane.add(btnClientes);
		
		JButton btnVenta = new JButton("Venta");
		btnVenta.setBounds(323, 162, 144, 21);
		contentPane.add(btnVenta);
		
		JButton btnHistorialVentas = new JButton("Historial de Ventas");
		btnHistorialVentas.setBounds(323, 193, 144, 21);
		contentPane.add(btnHistorialVentas);

	}
}
