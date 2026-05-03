package inmobiliaria.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class ModificarUsuario extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtContrasenaModificar;
	private JTextField txtCorreoModificar;
	private JTextField txtNombreModificar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ModificarUsuario dialog = new ModificarUsuario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModificarUsuario() {
		setTitle("Modificar Usuario");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblRolModificar = new JLabel("Rol:");
			lblRolModificar.setBounds(21, 118, 44, 12);
			contentPanel.add(lblRolModificar);
		}
		{
			JComboBox cmbRolModificar = new JComboBox();
			cmbRolModificar.setModel(new DefaultComboBoxModel(new String[] {"Administrador", "Vendedor"}));
			cmbRolModificar.setBounds(49, 114, 124, 20);
			contentPanel.add(cmbRolModificar);
		}
		{
			txtContrasenaModificar = new JTextField();
			txtContrasenaModificar.setColumns(10);
			txtContrasenaModificar.setBounds(104, 89, 216, 18);
			contentPanel.add(txtContrasenaModificar);
		}
		{
			JLabel lblContrasenaModificar = new JLabel("Contraseña: ");
			lblContrasenaModificar.setBounds(21, 92, 75, 12);
			contentPanel.add(lblContrasenaModificar);
		}
		{
			JLabel lblCorreoModificar = new JLabel("Correo:");
			lblCorreoModificar.setBounds(21, 67, 61, 12);
			contentPanel.add(lblCorreoModificar);
		}
		{
			txtCorreoModificar = new JTextField();
			txtCorreoModificar.setColumns(10);
			txtCorreoModificar.setBounds(79, 64, 241, 18);
			contentPanel.add(txtCorreoModificar);
		}
		{
			txtNombreModificar = new JTextField();
			txtNombreModificar.setColumns(10);
			txtNombreModificar.setBounds(79, 39, 241, 18);
			contentPanel.add(txtNombreModificar);
		}
		{
			JLabel lblnombreModificar = new JLabel("Nombre: ");
			lblnombreModificar.setBounds(21, 42, 61, 12);
			contentPanel.add(lblnombreModificar);
		}
		{
			JLabel lblModificarUsuario = new JLabel("MODIFICAR USUARIO");
			lblModificarUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblModificarUsuario.setBounds(10, 10, 149, 19);
			contentPanel.add(lblModificarUsuario);
		}
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
