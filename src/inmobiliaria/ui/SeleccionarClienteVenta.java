package inmobiliaria.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import inmobiliaria.util.ConexionDB;

public class SeleccionarClienteVenta extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JComboBox cmbClientes;
	private JLabel lblTelInfo;
	private JLabel lblDirInfo;
	
	private java.util.ArrayList listaIdsClientes = new java.util.ArrayList<>();
	private String idCliente;
	
	//Variables de retorno para la ventana Venta
	private String nombreComprador;
	private String telefonoComprador;
	private boolean ventaConfirmada = false;
	
	private ConexionDB conexion = new ConexionDB();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SeleccionarClienteVenta dialog = new SeleccionarClienteVenta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SeleccionarClienteVenta() {
		setTitle("Asignación de Comprador");
		setBounds(100, 100, 400, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblSeleccione = new JLabel("Seleccione el Cliente Comprador:");
		lblSeleccione.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSeleccione.setBounds(20, 15, 250, 20);
		contentPanel.add(lblSeleccione);

		cmbClientes = new JComboBox();
		cmbClientes.setBounds(20, 45, 230, 22);
		contentPanel.add(cmbClientes);

		lblTelInfo = new JLabel("Teléfono: ");
		lblTelInfo.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblTelInfo.setBounds(20, 90, 340, 20);
		contentPanel.add(lblTelInfo);

		lblDirInfo = new JLabel("Dirección: ");
		lblDirInfo.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblDirInfo.setBounds(20, 120, 340, 20);
		contentPanel.add(lblDirInfo);

		//Carga inicial de clientes
		cargarClientes();

		//Evento para cambiar la info de los labels al seleccionar un cliente diferente
		cmbClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarLabels();
			}
		});

		//AGREGAR CLIENTE
		JButton btnNuevo = new JButton("➕ Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarCliente ventanaAgregar = new AgregarCliente();
				ventanaAgregar.setModal(true);
				ventanaAgregar.setVisible(true);

				if (ventanaAgregar.guardadoCorrecto()) {
					String nom = ventanaAgregar.traerNombre();
					String ape = ventanaAgregar.traerApellido();
					String tel = ventanaAgregar.traerTelefono();
					String dir = ventanaAgregar.traerDireccion();

					String sentencia = "INSERT INTO cliente (Nombre, Apellido, Telefono, Direccion) "
										+ "VALUES ('" + nom + "', '" + ape + "', '" + tel + "', '" + dir + "')";
					
					conexion.conectar();
					conexion.ejecutarSentencia(sentencia);
					conexion.cerrar();

					JOptionPane.showMessageDialog(null, "Cliente registrado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					
					cargarClientes(); //Recargar el combo de este JDialog
					cmbClientes.setSelectedIndex(cmbClientes.getItemCount() - 1); //Seleccionar el recién creado
				}
			}
		});
		btnNuevo.setBounds(265, 44, 100, 23);
		contentPanel.add(btnNuevo);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		{
			JButton btnConfirmar = new JButton("Confirmar Venta");
			btnConfirmar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int indiceSeleccionado = cmbClientes.getSelectedIndex();
					
					if (indiceSeleccionado <= 0) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente válido para continuar.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					//Jalamos el ID de la lista usando el mismo índice del combo
					idCliente = (String) listaIdsClientes.get(indiceSeleccionado);

					//Lo demás lo sacas con tu split normal de la cadena visual
					String[] info = cmbClientes.getSelectedItem().toString().split(" \\| ");
					nombreComprador = info[0]; //Ahora el nombre vuelve a ser el índice 0
					telefonoComprador = info[1]; //El teléfono vuelve a ser el índice 1

					ventaConfirmada = true;
					dispose();
				}
			});
			buttonPane.add(btnConfirmar);
			getRootPane().setDefaultButton(btnConfirmar);
		}
		{
			JButton btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ventaConfirmada = false;
					dispose();
				}
			});
			buttonPane.add(btnCancelar);
		}
	}

	//Método para cargar los clientes de la BD en el combo box
	private void cargarClientes() {
		cmbClientes.removeAllItems();
	    listaIdsClientes.clear(); //Limpiamos la lista
	    
	    conexion.conectar();
	    DefaultTableModel modelo = conexion.consulta("SELECT id, Nombre, Apellido, Telefono, Direccion FROM cliente");
	    conexion.cerrar();

	    if (modelo.getRowCount() == 0) {
	        cmbClientes.addItem("--- No hay clientes ---");
	        listaIdsClientes.add("0"); //Posición 0 vacía
	    } else {
	        cmbClientes.addItem("--- Seleccione un Cliente ---");
	        listaIdsClientes.add("0"); //Posición 0 vacía
	        
	        for (int i = 0; i < modelo.getRowCount(); i++) {
	            //Guardamos el ID en la lista
	            listaIdsClientes.add(modelo.getValueAt(i, 0).toString());
	            
	            //Al ComboBox solo le metemos la información necesaria
	            String clientes = modelo.getValueAt(i, 1).toString() + " " +   // Nombre
	                                modelo.getValueAt(i, 2).toString() + " | " + // Apellido
	                                modelo.getValueAt(i, 3).toString() + " | " + // Teléfono
	                                modelo.getValueAt(i, 4).toString();          // Dirección
	            cmbClientes.addItem(clientes);
	        }
	    }
	}

	private void actualizarLabels() {
		if (cmbClientes.getSelectedIndex() > 0) {
			String[] datos = cmbClientes.getSelectedItem().toString().split(" \\| ");
			if (datos.length >= 3) {
				lblTelInfo.setText("Teléfono: " + datos[1]);
				lblDirInfo.setText("Dirección: " + datos[2]);
			}
		} else {
			lblTelInfo.setText("Teléfono: ");
			lblDirInfo.setText("Dirección: ");
		}
	}

	// Getters públicos para el apartado de Ventas
	public boolean guardadoCorrecto() { 
		return ventaConfirmada; 
	}
	public String getNombreComprador() { 
		return nombreComprador; 
	}
	public String getTelefonoComprador() { 
		return telefonoComprador; 
	}
	public String getIdComprador() { 
		return idCliente; 
	}
}
