package inmobiliaria.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

import inmobiliaria.util.ConexionDB;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Venta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;
	private JTextField txtBusqueda;
	private JComboBox cmbBusqueda;
	private JButton btnBuscar, btnRecibo, btnRegresar;
	
	public static int idUsuario = Login.IdUsuario;
	private ConexionDB conexion = new ConexionDB();

	/**
	 * Launch the application.
	 */
<<<<<<< HEAD
    private Statement query = null;
	private ResultSet Rs = null;
=======
>>>>>>> 72672565077a379555435bd0cd542d62b2d4eb0a

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Venta frame = new Venta();
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
	public Venta() {
		setTitle("Módulo de Ventas e Inmuebles Disponibles");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBorder(new TitledBorder(null, "Buscar Inmuebles en Venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBusqueda.setBounds(20, 20, 770, 65);
		contentPane.add(panelBusqueda);
		panelBusqueda.setLayout(null);

<<<<<<< HEAD
		JLabel lblBusqueda = new JLabel("Búsqueda por:");
		lblBusqueda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBusqueda.setBounds(283, 82, 88, 20);
		contentPane.add(lblBusqueda);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(542, 81, 100, 25);
		btnBuscar.addActionListener(e -> realizarBusqueda());
		contentPane.add(btnBuscar);

		// --- BOTONES INFERIORES ---
		JButton btnVender = new JButton("Vender");
		btnVender.setBounds(401, 510, 100, 30);
		btnVender.addActionListener(e -> abrirDialogoVenta());
		contentPane.add(btnVender);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(530, 510, 100, 30);
		btnCerrar.addActionListener(e -> dispose());
		contentPane.add(btnCerrar);
		
		cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"Presupuesto", "Dimesiones", "Ubicación"}));
		cmbBusqueda.setBounds(381, 82, 151, 22);
		contentPane.add(cmbBusqueda);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 124, 638, 367);
		contentPane.add(scrollPane);
		
		modeloTabla = new DefaultTableModel(
				new Object[][]{},
	            new String[]{"Dimensión (m²)", "Precio", "Ubicación", "Estado", "Tipo"}
	    );
		
		T_info = new JTable(modeloTabla);
		T_info.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = T_info.getSelectedRow();
                if (selectedRow >= 0) {
                    propiedadSeleccionadaDimension = modeloTabla.getValueAt(selectedRow, 0).toString();
                    propiedadSeleccionadaPrecio = modeloTabla.getValueAt(selectedRow, 1).toString();
                    propiedadSeleccionadaUbicacion = modeloTabla.getValueAt(selectedRow, 2).toString();
                }
            }
        });
		scrollPane.setViewportView(T_info);
		
=======
>>>>>>> 72672565077a379555435bd0cd542d62b2d4eb0a
		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(15, 25, 280, 23);
		panelBusqueda.add(txtBusqueda);
		txtBusqueda.setColumns(10);
<<<<<<< HEAD
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cargarTodasPropiedades();
			}
		});
		btnLimpiar.setBounds(542, 36, 100, 25);
		contentPane.add(btnLimpiar);
        
        cargarTodasPropiedades();
=======

		cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"Ubicacion","Dimension", "Tipo" ,"Precio"}));
		cmbBusqueda.setBounds(315, 25, 160, 23);
		panelBusqueda.add(cmbBusqueda);

		//BUSCAR
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String texto = txtBusqueda.getText().trim();
				String criterio = cmbBusqueda.getSelectedItem().toString();
				
				//Filtramos por el criterio seleccionado pero obliga a que el estado siga siendo En venta
				String sql = "SELECT * FROM propiedades WHERE " + criterio + " LIKE '%" + texto + "%' AND Estado='En Venta'";
				
				conexion.conectar();
				DefaultTableModel modeloFiltrado = conexion.consulta(sql);
				actualizarDisenoTabla(modeloFiltrado);
				conexion.cerrar();
			}
		});
		btnBuscar.setBounds(500, 24, 110, 25);
		panelBusqueda.add(btnBuscar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 100, 610, 320);
		contentPane.add(scrollPane);

		tabla = new JTable();
		scrollPane.setViewportView(tabla);
		cargarDatos();

		JPanel panelAcciones = new JPanel();
		panelAcciones.setBorder(new TitledBorder(null, "Operaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelAcciones.setBounds(650, 100, 140, 250);
		contentPane.add(panelAcciones);
		panelAcciones.setLayout(null);

		//GENERAR RECIBO
		btnRecibo = new JButton("Vender");
		btnRecibo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tabla.getSelectedRow();
				
				if (filaSeleccionada >= 0) {
					//Extraemos los datos de la fila seleccionada
					String idPropiedad = tabla.getModel().getValueAt(filaSeleccionada, 0).toString();
					String dimension = tabla.getModel().getValueAt(filaSeleccionada, 1).toString();
					String precio = tabla.getModel().getValueAt(filaSeleccionada, 2).toString();
					String ubicacion = tabla.getModel().getValueAt(filaSeleccionada, 3).toString();
					String tipo = tabla.getModel().getValueAt(filaSeleccionada, 5).toString();

					//Ejecutamos el Jdialog SeleccionarClienteVenta
					SeleccionarClienteVenta ventanaSeleccion = new SeleccionarClienteVenta();
					ventanaSeleccion.setModal(true); //Hace que el Jframe se congele para esperar los datos
					ventanaSeleccion.setVisible(true);

					//Si el usuario confirmó la venta en el Dialog, procedemos a guardar en la BD
					if (ventanaSeleccion.guardadoCorrecto()) {
						//Jalamos los datos usando los métodos get del JDialog
						String idCliente = ventanaSeleccion.getIdComprador();
						String clienteNombre = ventanaSeleccion.getNombreComprador();
						String clienteTelefono = ventanaSeleccion.getTelefonoComprador();
						String fecha = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());

						//Cambiamos el estado de la propiedad a Vendido en la BD y guardamos la venta
						String sentenciaEstado = "UPDATE propiedades SET Estado='Vendido' WHERE id=" + idPropiedad;
						String sentenciaVenta = "INSERT INTO venta (precio_final, id_usuario, id_propiedad, id_cliente, fecha) " + "VALUES (" + precio + "," + idUsuario + ", " + idPropiedad + ", " + idCliente + ", Now())";
						conexion.conectar();
						conexion.ejecutarSentencia(sentenciaEstado);
						conexion.ejecutarSentencia(sentenciaVenta);
						conexion.cerrar();

						//Generamos el Ticket						
						String ticket = "╔════════════════════════════════════════════════╗\n";
						ticket += "║            TICKET DE VENTA INMOBILIARIA        ║\n";
						ticket += "╠════════════════════════════════════════════════╣\n";
						ticket += "║  Fecha: " + fecha + "\n";
						ticket += "║\n";
						ticket += "║  CLIENTE COMPRADOR:\n";
						ticket += "║  " + clienteNombre.toUpperCase() + "\n";
						ticket += "║  Tel: " + clienteTelefono + "\n";
						ticket += "║\n";
						ticket += "║  DETALLES DEL INMUEBLE:\n";
						ticket += "║  Inmueble:  " + tipo + " (" + dimension + " m²)\n";
						ticket += "║  Ubicación: " + ubicacion + "\n";
						ticket += "║  Precio total: $" + precio + "\n";
						ticket += "║\n";
						ticket += "╠════════════════════════════════════════════════╣\n";
						ticket += "║         ¡PROPIEDAD ADQUIRIDA CON ÉXITO!        ║\n";
						ticket += "╚════════════════════════════════════════════════╝";
						
						JTextArea textArea = new JTextArea(ticket);
						textArea.setEditable(false);
						textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
						
						JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Comprobante de Operación", JOptionPane.INFORMATION_MESSAGE);
						
						//Recargar los datos en la tabla
						cargarDatos();
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione de la lista la propiedad que desea vender.", "Atención", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnRecibo.setBounds(10, 30, 120, 45);
		panelAcciones.add(btnRecibo);

		btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu ventanaMenu = new menu();
				ventanaMenu.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(660, 395, 120, 25);
		contentPane.add(btnRegresar);
>>>>>>> 72672565077a379555435bd0cd542d62b2d4eb0a
	}

<<<<<<< HEAD
	private String[] propiedadesId;
	
	private void actualizarTablaYIds(DefaultTableModel resultado) {
        modeloTabla.setRowCount(0);
        propiedadesId = new String[resultado.getRowCount()];
        
        for (int i = 0; i < resultado.getRowCount(); i++) {
            modeloTabla.addRow(new Object[]{
                resultado.getValueAt(i, 1),  // Dimensión
                resultado.getValueAt(i, 2),  // Precio
                resultado.getValueAt(i, 3),  // Ubicación
                resultado.getValueAt(i, 4),  // Estado
                resultado.getValueAt(i, 5)   // Tipo
            });
            propiedadesId[i] = resultado.getValueAt(i, 0).toString();
        }
    }
	
	public void cargarDatos() {
		modeloTabla.setRowCount(0);
		String sentencia = "SELECT * FROM propiedades";
		
		
		String[] datos = new String[5];
		
		try{
			Connection con = conexion.conectar();
			query = con.createStatement();
			Rs = query.executeQuery(sentencia);
			
			while(Rs.next()){
				datos[0] = Rs.getString(1);
				datos[1] = Rs.getString(2);
				datos[2] = Rs.getString(3);
				datos[3] = Rs.getString(4);
				datos[4] = Rs.getString(5);
				modeloTabla.addRow(datos);
			}
			
			conexion.cerrar();
		}catch (SQLException ex){
			JOptionPane.showMessageDialog(null, "Error: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
    
	private void abrirDialogoVenta() {
		int selectedRow = T_info.getSelectedRow();
        
		if (selectedRow == -1) {
	        JOptionPane.showMessageDialog(this, "Seleccione una propiedad de la tabla");
	        return;
	    }
        
     // Obtener el ID del arreglo paralelo
        propiedadSeleccionadaId = propiedadesId[selectedRow];
        propiedadSeleccionadaDimension = modeloTabla.getValueAt(selectedRow, 0).toString();
        propiedadSeleccionadaPrecio = modeloTabla.getValueAt(selectedRow, 1).toString();
        propiedadSeleccionadaUbicacion = modeloTabla.getValueAt(selectedRow, 2).toString();
        
        DialogoVenta dialogo = new DialogoVenta(this, 
            propiedadSeleccionadaId,
            propiedadSeleccionadaUbicacion,
            propiedadSeleccionadaDimension,
            propiedadSeleccionadaPrecio);
        dialogo.setVisible(true);
        
        // Recargar la tabla después de la venta
        cargarTodasPropiedades();
        propiedadSeleccionadaId = null;
    }
	
// ==================== CLASE INTERNA DialogoVenta ====================
    
    private class DialogoVenta extends JDialog {
        
        private static final long serialVersionUID = 1L;
        private JPanel contentPane;
        private JTextField txtNombre, txtApellido, txtTelefono;
        private String idPropiedad;
        private String ubicacion;
        private String dimension;
        private String precio;
        private ConexionDB conexion;

        public DialogoVenta(JFrame parent, String idPropiedad, String ubicacion, String dimension, String precio) {
            super(parent, "Registrar Venta", true);
            this.idPropiedad = idPropiedad;
            this.ubicacion = ubicacion;
            this.dimension = dimension;
            this.precio = precio;
            this.conexion = new ConexionDB();
            
            setBounds(100, 100, 400, 350);
            setLocationRelativeTo(parent);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
            setContentPane(contentPane);
            contentPane.setLayout(null);
            
            // Título
            JLabel lblTitulo = new JLabel("DATOS DEL CLIENTE");
            lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
            lblTitulo.setBounds(120, 10, 200, 25);
            contentPane.add(lblTitulo);
            
            // Información de la propiedad
            JLabel lblInfoProp = new JLabel("Propiedad: " + ubicacion + " - " + dimension + " m²");
            lblInfoProp.setFont(new Font("Tahoma", Font.PLAIN, 11));
            lblInfoProp.setBounds(20, 45, 350, 20);
            contentPane.add(lblInfoProp);
            
            JLabel lblPrecio = new JLabel("Precio: $" + precio);
            lblPrecio.setFont(new Font("Tahoma", Font.BOLD, 12));
            lblPrecio.setBounds(20, 65, 200, 20);
            contentPane.add(lblPrecio);
            
            // Campos de cliente
            JLabel lblNombre = new JLabel("Nombre:");
            lblNombre.setBounds(20, 105, 80, 20);
            contentPane.add(lblNombre);
            
            txtNombre = new JTextField();
            txtNombre.setBounds(100, 105, 250, 22);
            contentPane.add(txtNombre);
            
            JLabel lblApellido = new JLabel("Apellido:");
            lblApellido.setBounds(20, 140, 80, 20);
            contentPane.add(lblApellido);
            
            txtApellido = new JTextField();
            txtApellido.setBounds(100, 140, 250, 22);
            contentPane.add(txtApellido);
            
            JLabel lblTelefono = new JLabel("Teléfono:");
            lblTelefono.setBounds(20, 175, 80, 20);
            contentPane.add(lblTelefono);
            
            txtTelefono = new JTextField();
            txtTelefono.setBounds(100, 175, 250, 22);
            contentPane.add(txtTelefono);
            
            // Botones
            JButton btnGenerarRecibo = new JButton("Generar Recibo");
            btnGenerarRecibo.setBounds(50, 230, 130, 35);
            btnGenerarRecibo.setBackground(new Color(50, 150, 50));
            btnGenerarRecibo.setForeground(Color.WHITE);
            btnGenerarRecibo.addActionListener(e -> realizarVenta());
            contentPane.add(btnGenerarRecibo);
            
            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.setBounds(200, 230, 100, 35);
            btnCancelar.addActionListener(e -> dispose());
            contentPane.add(btnCancelar);
        }
        
        private void realizarVenta() {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();
            
            if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }
            
            try {
                conexion.conectar();
                
                // 1. Insertar cliente
                String sqlCliente = "INSERT INTO cliente (nombre, apellido, telefono, direccion) VALUES ('" +
                                    nombre + "', '" + apellido + "', '" + telefono + "', '" + ubicacion + "')";
                conexion.ejecutarSentencia(sqlCliente);
                
                // 2. Obtener ID del cliente
                String sqlGetCliente = "SELECT MAX(id) as id FROM cliente";
                var clienteResult = conexion.consulta(sqlGetCliente);
                int idCliente = Integer.parseInt(clienteResult.getValueAt(0, 0).toString());
                
                // 3. Insertar venta
                Timestamp fecha = new Timestamp(System.currentTimeMillis());
                String sqlVenta = "INSERT INTO venta (precio_final, fecha, id_cliente, id_propiedad, id_usuario) VALUES (" +
                                  precio + ", #" + fecha + "#, " + idCliente + ", " + idPropiedad + ", 1)";
                conexion.ejecutarSentencia(sqlVenta);
                
                // 4. Actualizar estado de propiedad
                String sqlUpdate = "UPDATE propiedades SET estado = 'Vendido' WHERE id = " + idPropiedad;
                conexion.ejecutarSentencia(sqlUpdate);
                
                conexion.cerrar();
                
                // 5. Mostrar ticket
                mostrarTicket(nombre, apellido, telefono);
                
                JOptionPane.showMessageDialog(this, "¡Venta realizada con éxito!");
                dispose();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        private void mostrarTicket(String nombre, String apellido, String telefono) {
            String fecha = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
            
            String ticket = "╔════════════════════════════════════════════════╗\n";
            ticket += "║            TICKET DE VENTA                       ║\n";
            ticket += "╠════════════════════════════════════════════════════╣\n";
            ticket += "║  Fecha: " + fecha + "\n";
            ticket += "║\n";
            ticket += "║  CLIENTE:\n";
            ticket += "║  " + nombre + " " + apellido + "\n";
            ticket += "║  Tel: " + telefono + "\n";
            ticket += "║\n";
            ticket += "║  PROPIEDAD:\n";
            ticket += "║  Ubicación: " + ubicacion + "\n";
            ticket += "║  Dimensión: " + dimension + " m²\n";
            ticket += "║  Precio: $" + precio + "\n";
            ticket += "║\n";
            ticket += "╠════════════════════════════════════════════════════╣\n";
            ticket += "║         ¡GRACIAS POR SU COMPRA!                    ║\n";
            ticket += "╚════════════════════════════════════════════════════╝";
            
            JTextArea textArea = new JTextArea(ticket);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
            
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Ticket de Venta", JOptionPane.INFORMATION_MESSAGE);
        }
    }
=======
	public void cargarDatos() {
		conexion.conectar();
		DefaultTableModel modeloOriginal = conexion.consulta("SELECT * FROM propiedades WHERE Estado='En Venta'");
		actualizarDisenoTabla(modeloOriginal);
		conexion.cerrar();
	}


	private void actualizarDisenoTabla(DefaultTableModel nuevoModelo) {
		DefaultTableModel modeloNoEditable = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; 
			}
		};

		//Copiar las columnas de la consulta
		for (int i = 0; i < nuevoModelo.getColumnCount(); i++) {
			modeloNoEditable.addColumn(nuevoModelo.getColumnName(i));
		}
		
		//Copiar las filas de la consulta
		for (int i = 0; i < nuevoModelo.getRowCount(); i++) {
			Object[] fila = new Object[nuevoModelo.getColumnCount()];
			for (int j = 0; j < nuevoModelo.getColumnCount(); j++) {
				fila[j] = nuevoModelo.getValueAt(i, j);
			}
			modeloNoEditable.addRow(fila);
		}

		tabla.setModel(modeloNoEditable);

		//Configuración básica
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowSelectionAllowed(true);
		tabla.setFillsViewportHeight(true);

		//Ocultar el ID
		if (tabla.getColumnCount() > 0) {
			tabla.getColumnModel().getColumn(0).setMaxWidth(0);
			tabla.getColumnModel().getColumn(0).setMinWidth(0);
			tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
			tabla.getColumnModel().getColumn(0).setResizable(false);
		}
	}
>>>>>>> 72672565077a379555435bd0cd542d62b2d4eb0a
}
