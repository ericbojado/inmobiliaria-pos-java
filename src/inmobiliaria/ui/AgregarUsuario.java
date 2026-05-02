package inmobiliaria.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AgregarUsuario extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombreAgregar;
	private JTextField txtCorreoAgregar;
	private JTextField txtContrasenaAgregar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AgregarUsuario dialog = new AgregarUsuario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarUsuario() {
		setTitle("Agregar Usuario");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblAgregarUsuario = new JLabel("AGREGAR USUARIO");
		lblAgregarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAgregarUsuario.setBounds(10, 10, 149, 19);
		contentPanel.add(lblAgregarUsuario);
		
		JLabel lblnombreAgregar = new JLabel("Nombre: ");
		lblnombreAgregar.setBounds(21, 42, 61, 12);
		contentPanel.add(lblnombreAgregar);
		
		txtNombreAgregar = new JTextField();
		txtNombreAgregar.setBounds(79, 39, 241, 18);
		contentPanel.add(txtNombreAgregar);
		txtNombreAgregar.setColumns(10);
		
		JLabel lblCorreoAgregar = new JLabel("Correo:");
		lblCorreoAgregar.setBounds(21, 67, 61, 12);
		contentPanel.add(lblCorreoAgregar);
		
		txtCorreoAgregar = new JTextField();
		txtCorreoAgregar.setColumns(10);
		txtCorreoAgregar.setBounds(79, 64, 241, 18);
		contentPanel.add(txtCorreoAgregar);
		
		JLabel lblContrasenaAgregar = new JLabel("Contraseña: ");
		lblContrasenaAgregar.setBounds(21, 92, 75, 12);
		contentPanel.add(lblContrasenaAgregar);
		
		txtContrasenaAgregar = new JTextField();
		txtContrasenaAgregar.setColumns(10);
		txtContrasenaAgregar.setBounds(104, 89, 216, 18);
		contentPanel.add(txtContrasenaAgregar);
		
		JLabel lblRolAgregar = new JLabel("Rol:");
		lblRolAgregar.setBounds(21, 118, 44, 12);
		contentPanel.add(lblRolAgregar);
		
		JComboBox cmbRolAgregar = new JComboBox();
		cmbRolAgregar.setModel(new DefaultComboBoxModel(new String[] {"Administrador", "Vendedor"}));
		cmbRolAgregar.setBounds(49, 114, 124, 20);
		contentPanel.add(cmbRolAgregar);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
