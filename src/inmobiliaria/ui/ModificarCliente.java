package inmobiliaria.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	// Campos de texto de la interfaz
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtDireccion;

	// Variables locales para validar y empaquetar los datos modificados
	private String nombreModificado;
	private String apellidoModificado;
	private String telefonoModificado;
	private String direccionModificado;
	
	// Bandera de control
	private boolean guardarModificacion = false;

	/**
	 * Constructor SIN parámetros (Igual que en ModificarUsuario)
	 */
	public ModificarCliente() {
		// Leemos el ID desde la variable estática del catálogo para el título
		setTitle("Modificar Cliente - ID: " + CatalogoCliente.idModificar());
		setBounds(100, 100, 380, 280);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblTitulo = new JLabel("Modificar Datos del Cliente");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(20, 11, 250, 20);
		contentPanel.add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombre.setBounds(20, 50, 80, 20);
		contentPanel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(110, 50, 220, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblApellido.setBounds(20, 90, 80, 20);
		contentPanel.add(lblApellido);

		txtApellido = new JTextField();
		txtApellido.setBounds(110, 90, 220, 20);
		contentPanel.add(txtApellido);
		txtApellido.setColumns(10);

		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTelefono.setBounds(20, 130, 80, 20);
		contentPanel.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(110, 130, 220, 20);
		contentPanel.add(txtTelefono);
		txtTelefono.setColumns(10);

		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDireccion.setBounds(20, 170, 80, 20);
		contentPanel.add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(110, 170, 220, 20);
		contentPanel.add(txtDireccion);
		txtDireccion.setColumns(10);

		// OBTENCIÓN DE DATOS DESDE LAS VARIABLES ESTÁTICAS DEL CATÁLOGO
		txtNombre.setText(CatalogoCliente.nombreModificar());
		txtApellido.setText(CatalogoCliente.apellidoModificar());
		txtTelefono.setText(CatalogoCliente.telefonoModificar());
		txtDireccion.setText(CatalogoCliente.direccionModificar());

		// Panel de botones inferior
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					nombreModificado = txtNombre.getText().trim();
					apellidoModificado = txtApellido.getText().trim();
					telefonoModificado = txtTelefono.getText().trim();
					direccionModificado = txtDireccion.getText().trim();

					// Validaciones básicas
					if (nombreModificado.isEmpty() || apellidoModificado.isEmpty() || 
						telefonoModificado.isEmpty() || direccionModificado.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Campos Vacíos", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (!nombreModificado.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") || !apellidoModificado.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
						JOptionPane.showMessageDialog(null, "Los campos Nombre y Apellido solo deben contener letras.", "Datos Incorrectos", JOptionPane.ERROR_MESSAGE);
						return; 
					}

					if (!telefonoModificado.matches("[0-9]+") || telefonoModificado.length() < 8) {
						JOptionPane.showMessageDialog(null, "Ingrese un número de teléfono válido.", "Teléfono Inválido", JOptionPane.ERROR_MESSAGE);
						return;
					}

					guardarModificacion = true;
					dispose();
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					guardarModificacion = false;
					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
	}

	// Getters para que el catálogo recupere los nuevos textos ingresados
	public boolean guardadoCorrecto() { 
		return guardarModificacion; 
	}
	public String getNombreModificado() { 
		return nombreModificado; 
	}
	public String getApellidoModificado() { 
		return apellidoModificado; 
	}
	public String getTelefonoModificado() { 
		return telefonoModificado; 
	}
	public String getDireccionModificado() { 
		return direccionModificado; 
	}
}
