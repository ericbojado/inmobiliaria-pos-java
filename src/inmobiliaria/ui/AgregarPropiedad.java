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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarPropiedad extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtDimension;
	private JTextField txtUbicacion;
	private JTextField txtPrecio;
	private JComboBox cmbTipo;
	private JComboBox cmbEstado;

	//Variables para empaquetar y retornar los datos capturados
	private String dimension;
	private String ubicacion;
	private String precio;
	private String tipo;
	private String estado;
	
	private boolean guardar = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AgregarPropiedad dialog = new AgregarPropiedad();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarPropiedad() {
		setTitle("Agregar Nueva Propiedad");
		setBounds(100, 100, 400, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblTitulo = new JLabel("Registrar Nueva Propiedad");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(20, 11, 250, 20);
		contentPanel.add(lblTitulo);

		JLabel lblNombre = new JLabel("Dimensión:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombre.setBounds(20, 50, 80, 20);
		contentPanel.add(lblNombre);

		txtDimension = new JTextField();
		txtDimension.setBounds(110, 50, 240, 20);
		contentPanel.add(txtDimension);
		txtDimension.setColumns(10);

		JLabel lblUbicacion = new JLabel("Precio ($):");
		lblUbicacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUbicacion.setBounds(20, 90, 80, 20);
		contentPanel.add(lblUbicacion);

		txtUbicacion = new JTextField();
		txtUbicacion.setBounds(110, 132, 240, 20);
		contentPanel.add(txtUbicacion);
		txtUbicacion.setColumns(10);

		JLabel lblPrecio = new JLabel("Ubicación:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrecio.setBounds(20, 130, 80, 20);
		contentPanel.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(110, 92, 240, 20);
		contentPanel.add(txtPrecio);
		txtPrecio.setColumns(10);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTipo.setBounds(20, 170, 80, 20);
		contentPanel.add(lblTipo);

		cmbTipo = new JComboBox();
		cmbTipo.setModel(new DefaultComboBoxModel(new String[] {"Casa", "Departamento", "Terreno", "Local"}));
		cmbTipo.setBounds(110, 170, 240, 20);
		contentPanel.add(cmbTipo);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEstado.setBounds(20, 210, 80, 20);
		contentPanel.add(lblEstado);

		cmbEstado = new JComboBox();
		cmbEstado.setModel(new DefaultComboBoxModel(new String[] {"En Venta", "Vendido", "Rentado", "En Mantenimiento"}));
		cmbEstado.setBounds(110, 210, 240, 20);
		contentPanel.add(cmbEstado);

		// Panel de botones inferiores (OK y Cancel)
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Extraemos los valores limpiando los espacios en los extremos
				dimension = txtDimension.getText().trim();
				ubicacion = txtUbicacion.getText().trim();
				precio = txtPrecio.getText().trim();
				tipo = cmbTipo.getSelectedItem().toString();
				estado = cmbEstado.getSelectedItem().toString();

				//Validación de campos obligatorios vacíos
				if (dimension.isEmpty() || ubicacion.isEmpty() || precio.isEmpty()) {
					JOptionPane.showMessageDialog(null, 
							"Por favor, complete todos los campos de texto requeridos.", 
							"Campos Incompletos", JOptionPane.ERROR_MESSAGE);
					return; //Corta el flujo
				}

				//Validación de formato para el Precio, solo números enteros o decimales
				if (!precio.matches("\\d+(\\.\\d+)?")) {
					JOptionPane.showMessageDialog(null, 
							"El campo Precio debe ser un valor numérico válido (ej: 1250000). \nNo incluya letras ni signos.", 
							"Formato Inválido", JOptionPane.ERROR_MESSAGE);
					return; //Corta el flujo
				}

				//Si supera todas las restricciones, se confirma la acción
				guardar = true;
				dispose(); // Cierra el modal de forma segura
			}
		});
		btnOk.setActionCommand("OK");
		buttonPane.add(btnOk);
		getRootPane().setDefaultButton(btnOk);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardar = false;
				dispose();
			}
		});
		btnCancelar.setActionCommand("Cancel");
		buttonPane.add(btnCancelar);
	}

	// Getters para que inventario extraiga los datos capturados
	public boolean guardadoCorrecto() { 
		return guardar; 
	}
	public String traerDimension() { 
		return dimension; 
	}
	public String traerUbicacion() { 
		return ubicacion; 
	}
	public String traerPrecio() { 
		return precio;
	}
	public String traerTipo() { 
		return tipo; 
	}
	public String traerEstado() { 
		return estado; 
	}

}
