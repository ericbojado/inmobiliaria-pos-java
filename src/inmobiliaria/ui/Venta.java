package inmobiliaria.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

import inmobiliaria.util.ConexionDB;

public class Venta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable T_info;
	private JTextField txtBusqueda;
	private JComboBox<String> cmbBusqueda;
	private JButton btnBuscar, btnRecibo, btnCerrar;
	private DefaultTableModel modeloTabla;
    private ConexionDB conexion = new ConexionDB();;
    private String propiedadSeleccionadaId;
    private String propiedadSeleccionadaUbicacion;
    private String propiedadSeleccionadaPrecio;
    private String propiedadSeleccionadaDimension;

	/**
	 * Launch the application.
	 */
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
		
		setTitle("Inmobiliaria - Área de Ventas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Se ajustó el tamaño para que quepan todos los paneles cómodamente
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --- ENCABEZADO Y BÚSQUEDA ---
		JLabel lblTitulo = new JLabel("Área de Ventas");
		lblTitulo.setFont(new Font("Tw Cen MT", Font.BOLD | Font.ITALIC, 40));
		lblTitulo.setBounds(220, 24, 263, 30);
		contentPane.add(lblTitulo);

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
		
		txtBusqueda = new JTextField();
		txtBusqueda.setBounds(20, 83, 241, 20);
		contentPane.add(txtBusqueda);
		txtBusqueda.setColumns(10);
        
        cargarTodasPropiedades();
	}
	
	private void realizarBusqueda() {
        String tipo = (String) cmbBusqueda.getSelectedItem();
        String valor = txtBusqueda.getText().trim();
        
        if (valor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor para buscar");
            return;
        }
        
        try {
            conexion.conectar();
            String sql = "";
            
            switch (tipo) {
                case "Presupuesto":
                    double presupuesto = Double.parseDouble(valor);
                    sql = "SELECT * FROM propiedades WHERE precio <= " + presupuesto + " AND estado = 'En Venta' ORDER BY precio ASC";
                    break;
                case "Dimensiones":
                    int dimension = Integer.parseInt(valor);
                    sql = "SELECT * FROM propiedades WHERE dimension <= " + dimension + " AND estado = 'En Venta' ORDER BY precio ASC";
                    break;
                case "Ubicación":
                    sql = "SELECT * FROM propiedades WHERE ubicacion LIKE '%" + valor + "%' AND estado = 'En Venta' ORDER BY precio ASC";
                    break;
            }
            
            DefaultTableModel resultado = conexion.consulta(sql);
            modeloTabla.setRowCount(0);
            
            for (int i = 0; i < resultado.getRowCount(); i++) {
                modeloTabla.addRow(new Object[]{
                    resultado.getValueAt(i, 1),
                    resultado.getValueAt(i, 2),
                    resultado.getValueAt(i, 3),
                    resultado.getValueAt(i, 4),
                    resultado.getValueAt(i, 5)
                });
                
                T_info.setValueAt(resultado.getValueAt(i, 0), i, -1); // -1 significa que no se muestra
            }
            
            if (resultado.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron propiedades");
            }
            conexion.cerrar();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido");
        }
    }
	
	private void cargarTodasPropiedades() {
        conexion.conectar();
        String sql = "SELECT * FROM propiedades WHERE estado = 'En Venta' ORDER BY precio ASC";
        DefaultTableModel resultado = conexion.consulta(sql);
        modeloTabla.setRowCount(0);
        
        propiedadesId = new String[resultado.getRowCount()];  // Crear el arreglo con el tamaño correcto
        
        int i;
        
        for (i = 0; i < resultado.getRowCount(); i++) {
            modeloTabla.addRow(new Object[]{
                resultado.getValueAt(i, 1),
                resultado.getValueAt(i, 2),
                resultado.getValueAt(i, 3),
                resultado.getValueAt(i, 4),
                resultado.getValueAt(i, 5)
            });
            
            propiedadesId[i] = resultado.getValueAt(i, 0).toString();  // Guardar ID dentro del for
    	}
        conexion.cerrar();
    }

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
                String sqlCliente = "INSERT INTO clientes (nombre, apellido, telefono, direccion) VALUES ('" +
                                    nombre + "', '" + apellido + "', '" + telefono + "', '" + ubicacion + "')";
                conexion.ejecutarSentencia(sqlCliente);
                
                // 2. Obtener ID del cliente
                String sqlGetCliente = "SELECT MAX(id) as id FROM clientes";
                var clienteResult = conexion.consulta(sqlGetCliente);
                int idCliente = Integer.parseInt(clienteResult.getValueAt(0, 0).toString());
                
                // 3. Insertar venta
                Timestamp fecha = new Timestamp(System.currentTimeMillis());
                String sqlVenta = "INSERT INTO ventas (precio_final, fecha, id_cliente, id_propiedad, id_usuario) VALUES (" +
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
}
