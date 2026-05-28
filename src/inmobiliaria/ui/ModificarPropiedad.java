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

public class ModificarPropiedad extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JTextField txtDimension;
	private JTextField txtUbicacion;
	private JTextField txtPrecio;
	private JComboBox cmbTipo;
	private JComboBox cmbEstado;

	// Variables locales para validar y empaquetar los cambios
	private String dimension;
	private String ubicacion;
	private String precio;
	private String tipo;
	private String estado;
	
	// Bandera de control para la base de datos
	private boolean guardarModificacion = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ModificarPropiedad dialog = new ModificarPropiedad();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ModificarPropiedad() {
		setTitle("Modificar Propiedad");
		setBounds(100, 100, 400, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblTitulo = new JLabel("Modificar Datos de la Propiedad");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitulo.setBounds(20, 11, 280, 20);
		contentPanel.add(lblTitulo);

		JLabel lblNombre = new JLabel("Dimensión:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNombre.setBounds(20, 50, 80, 20);
		contentPanel.add(lblNombre);

		txtDimension = new JTextField();
		txtDimension.setBounds(110, 50, 240, 20);
		contentPanel.add(txtDimension);
		txtDimension.setColumns(10);

		JLabel lblPrecio = new JLabel("Precio ($):");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrecio.setBounds(20, 90, 80, 20);
		contentPanel.add(lblPrecio);

		txtUbicacion = new JTextField();
		txtUbicacion.setBounds(110, 132, 240, 20);
		contentPanel.add(txtUbicacion);
		txtUbicacion.setColumns(10);

		JLabel lblUbicación = new JLabel("Ubicación:");
		lblUbicación.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUbicación.setBounds(20, 130, 80, 20);
		contentPanel.add(lblUbicación);

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

		//Recuperamos los valores de las variables en inventario
		txtDimension.setText(Inventario.getDimension());
		txtUbicacion.setText(Inventario.getUbicacion());
		txtPrecio.setText(Inventario.getPrecio());
		cmbTipo.setSelectedItem(Inventario.getTipo());
		cmbEstado.setSelectedItem(Inventario.getEstado());

		// Panel de controles inferiores (Botones)
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 1. Extraemos y limpiamos espacios vacíos
					dimension = txtDimension.getText().trim();
					ubicacion = txtUbicacion.getText().trim();
					precio = txtPrecio.getText().trim();
					tipo = cmbTipo.getSelectedItem().toString();
					estado = cmbEstado.getSelectedItem().toString();

					// 2. Validación de campos obligatorios
					if (dimension.isEmpty() || ubicacion.isEmpty() || precio.isEmpty()) {
						JOptionPane.showMessageDialog(null, 
								"Por favor, llene todos los campos de texto requeridos.", 
								"Campos Vacíos", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// 3. Validación de Precio (Asegurarse de que sea un número válido decimal o entero)
					// Permite números enteros o con punto decimal (ej: 1500000 o 4500.50)
					if (!precio.matches("\\d+(\\.\\d+)?")) {
						JOptionPane.showMessageDialog(null, 
								"El campo Precio debe contener únicamente un valor numérico válido.", 
								"Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Si pasa con éxito todas las validaciones académicas
					guardarModificacion = true;
					dispose(); // Cierra el modal de forma segura
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
					guardarModificacion = false; // Bandera en falso para no alterar Access
					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
	}

		// GETTERS PÚBLICOS (Conectan directo con el ActionListener del botón Modificar en inventario)
		public boolean guardadoCorrecto() { 
			return guardarModificacion; 
		}
		public String getDimensionModificado() { 
			return dimension; 
		}
		public String getUbicacionModificado() {
			return ubicacion; 
		}
		public String getPrecioModificado() { 
			return precio; 
		}
		public String getTipoModificado() { 
			return tipo; 
		}
		public String getEstadoModificado() { 
			return estado; 
		}
}
