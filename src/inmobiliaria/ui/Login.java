package inmobiliaria.ui;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

import inmobiliaria.util.ConexionDB;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;


public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;
	public static String RolUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 463, 300);
		setLocationRelativeTo(null);//Centrar ventana
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(172, 65, 96, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("INMOBILIARIA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(158, 11, 118, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ingrese su correo: ");
		lblNewLabel_1.setBounds(10, 68, 137, 14);
		contentPane.add(lblNewLabel_1);
		
		txtContrasena = new JPasswordField();
		txtContrasena.setColumns(10);
		txtContrasena.setBounds(172, 107, 96, 20);
		contentPane.add(txtContrasena);
		
		JLabel lblNewLabel_1_1 = new JLabel("Ingrese su contraseña: ");
		lblNewLabel_1_1.setBounds(10, 110, 152, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnIngresar = new JButton("INGRESAR");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String correo = txtUsuario.getText();
				String password = new String(txtContrasena.getPassword()); 

				ConexionDB db = new ConexionDB();
				db.conectar();

				String sql = "SELECT nombre, rol FROM usuario WHERE correo = '" + correo + "' AND [password] = '" + password + "'";

				DefaultTableModel datos = db.consulta(sql);

				if (datos.getRowCount() > 0) {
					String nombre = datos.getValueAt(0, 0).toString();
					RolUsuario = datos.getValueAt(0, 1).toString();

					JOptionPane.showMessageDialog(null, "Bienvenido " + nombre);
					
					menu ventana = new menu();
					ventana.setVisible(true);
					
					txtUsuario.setText("");
					txtContrasena.setText("");
					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "Correo o contraseña incorrectos", "Aviso", JOptionPane.WARNING_MESSAGE);
					txtUsuario.setText("");
					txtContrasena.setText("");
				}

				db.cerrar();
			
			}
		});
		btnIngresar.setBounds(157, 164, 111, 46);
		contentPane.add(btnIngresar);

	}
}