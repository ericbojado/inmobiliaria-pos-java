package inmobiliaria.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarCliente extends JDialog {
	
	private String nombre;
	private String apellido;
	private String telefono;
	private String direccion;
	private boolean guardar = false;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txNombre;
	private JTextField txApelli;
	private JTextField txTelefono;
	private JTextField txDireccion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AgregarCliente dialog = new AgregarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AgregarCliente() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Agregar un cliente");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 129, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(10, 54, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txNombre = new JTextField();
		txNombre.setBounds(80, 51, 135, 20);
		contentPanel.add(txNombre);
		txNombre.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Apellido:");
		lblNewLabel_2.setBounds(10, 99, 46, 14);
		contentPanel.add(lblNewLabel_2);
		
		txApelli = new JTextField();
		txApelli.setBounds(80, 96, 135, 20);
		contentPanel.add(txApelli);
		txApelli.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Telefono:");
		lblNewLabel_3.setBounds(10, 148, 46, 14);
		contentPanel.add(lblNewLabel_3);
		
		txTelefono = new JTextField();
		txTelefono.setBounds(80, 145, 135, 20);
		contentPanel.add(txTelefono);
		txTelefono.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Dirrecion:");
		lblNewLabel_4.setBounds(10, 192, 46, 14);
		contentPanel.add(lblNewLabel_4);
		
		txDireccion = new JTextField();
		txDireccion.setBounds(80, 189, 135, 20);
		contentPanel.add(txDireccion);
		txDireccion.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						nombre = txNombre.getText().trim();//trim corta espacios en blanco para futuras validaciones
						apellido = txApelli.getText().trim();
						telefono = txTelefono.getText().trim();
						direccion = txDireccion.getText().trim();
						
						if(nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {//Si alguno de los campos esta vacio...
							JOptionPane.showMessageDialog(null, "Asegurese de ingresar todos los datos solicitados.", "Error", JOptionPane.ERROR_MESSAGE);
							return;//corta procedimiento
						}

						guardar = true;
						dispose();//Cierra jdialog
					}
				});
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
				getRootPane().setDefaultButton(btnOk);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
	//Getters para pedir datos en jframe
		public boolean guardadoCorrecto() {
			return guardar;
		}
		
		public String traerNombre() {
			return nombre;
		}
		
		public String traerApellido() {
			return apellido;
		}
		
		public String traerTelefono() {
			return telefono;
		}
		
		public String traerDireccion() {
			return direccion;
		}
}
