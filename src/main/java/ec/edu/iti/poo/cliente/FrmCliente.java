/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.iti.poo.cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FrmCliente extends JFrame {

    // ===== Datos en memoria =====
    private final ArrayList<Cliente> listaClientes = new ArrayList<>();

    // Índice del cliente encontrado/seleccionado en la lista (-1 = ninguno)
    private int indiceSeleccionado = -1;

    // ===== Controles del formulario =====
    private JTextField txtNombre, txtCedula, txtTelefono;
    private JTextArea txtDireccion;

    // ===== Búsqueda =====
    private JRadioButton rdbPorCedula, rdbPorNombre;
    private ButtonGroup grupoBusqueda;
    private JTextField txtBuscar;
    private JButton btnBuscar;

    // ===== Botones CRUD =====
    private JButton btnNuevo, btnGuardar, btnActualizar, btnEliminar, btnCancelar;

    public FrmCliente() {
        setTitle("CRUD Cliente");
        setSize(760, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        construirUI();   // Arma toda la pantalla
        estadoInicial(); // Deja botones e inputs en estado inicial
    }

    // ===================== UI =====================
    private void construirUI() {
        JPanel raiz = new JPanel(new BorderLayout(10, 10));
        raiz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ----- Panel búsqueda (NORTH) -----
        JPanel buscador = new JPanel(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(4, 4, 4, 4);
        b.anchor = GridBagConstraints.WEST;
        b.fill = GridBagConstraints.HORIZONTAL;

        rdbPorCedula = new JRadioButton("Cédula", true);
        rdbPorNombre = new JRadioButton("Nombre");
        grupoBusqueda = new ButtonGroup();
        grupoBusqueda.add(rdbPorCedula);
        grupoBusqueda.add(rdbPorNombre);

        txtBuscar = new JTextField(18);
        btnBuscar = new JButton("Buscar");

        int yb = 0;
        b.gridx = 0; b.gridy = yb; buscador.add(new JLabel("Buscar por:"), b);
        b.gridx = 1; b.gridy = yb; buscador.add(rdbPorCedula, b);
        b.gridx = 2; b.gridy = yb; buscador.add(rdbPorNombre, b);
        b.gridx = 3; b.gridy = yb; buscador.add(new JLabel("Dato:"), b);
        b.gridx = 4; b.gridy = yb; buscador.add(txtBuscar, b);
        b.gridx = 5; b.gridy = yb; buscador.add(btnBuscar, b);

        // ----- Panel formulario (CENTER) -----
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;

        txtNombre = new JTextField(22);
        txtCedula = new JTextField(22);
        txtTelefono = new JTextField(22);
        txtDireccion = new JTextArea(4, 22);
        txtDireccion.setLineWrap(true);
        txtDireccion.setWrapStyleWord(true);

        int y = 0;
        agregarFila(form, c, y++, "Nombre:", txtNombre);
        agregarFila(form, c, y++, "Cédula:", txtCedula);

        // Dirección con scroll
        c.gridx = 0; c.gridy = y; c.weightx = 0;
        form.add(new JLabel("Dirección:"), c);
        c.gridx = 1; c.gridy = y; c.weightx = 1;
        form.add(new JScrollPane(txtDireccion), c);
        y++;

        agregarFila(form, c, y++, "Teléfono:", txtTelefono);

        // ----- Panel botones (SOUTH) -----
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnCancelar = new JButton("Cancelar");

        botones.add(btnNuevo);
        botones.add(btnGuardar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnCancelar);

        // ----- Eventos de botones -----
        btnNuevo.addActionListener(e -> estadoNuevo());
        btnCancelar.addActionListener(e -> {
            indiceSeleccionado = -1;
            estadoInicial();
        });
        btnGuardar.addActionListener(e -> guardarCliente());
        btnBuscar.addActionListener(e -> buscarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());

        raiz.add(buscador, BorderLayout.NORTH);
        raiz.add(form, BorderLayout.CENTER);
        raiz.add(botones, BorderLayout.SOUTH);

        setContentPane(raiz);
    }

    private void agregarFila(JPanel form, GridBagConstraints c, int y, String etiqueta, JComponent campo) {
        c.gridx = 0; c.gridy = y; c.weightx = 0;
        form.add(new JLabel(etiqueta), c);
        c.gridx = 1; c.gridy = y; c.weightx = 1;
        form.add(campo, c);
    }

    // ===================== Estados / helpers de UI =====================
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCedula.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
    }

    private void estadoInicial() {
        limpiarFormulario();

        // Inputs deshabilitados
        txtNombre.setEnabled(false);
        txtCedula.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);

        // Botones
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(false);
    }

    private void estadoNuevo() {
        limpiarFormulario();

        // Inputs habilitados
        txtNombre.setEnabled(true);
        txtCedula.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtNombre.requestFocus();

        // Botones
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    // ===================== Helpers de validación y búsqueda =====================
    private String validarFormulario() {
        StringBuilder sb = new StringBuilder();

        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty()) sb.append("- Nombre: obligatorio.\n");
        if (cedula.isEmpty()) {
            sb.append("- Cédula: obligatoria.\n");
        } else if (!Util.validarCedula(cedula)) {
            sb.append("- Cédula: no es válida según el dígito verificador.\n");
        }
        if (direccion.isEmpty()) sb.append("- Dirección: obligatoria.\n");

        if (telefono.isEmpty()) {
            sb.append("- Teléfono: obligatorio.\n");
        } else if (!telefono.matches("\\d{7,10}")) {
            sb.append("- Teléfono: solo dígitos (7 a 10).\n");
        }

        return sb.toString();
    }

    private Cliente clienteDesdeFormulario() {
        return new Cliente(
                txtNombre.getText().trim(),
                txtCedula.getText().trim(),
                txtDireccion.getText().trim(),
                txtTelefono.getText().trim()
        );
    }

    private void llenarFormulario(Cliente c) {
        if (c == null) return;
        txtNombre.setText(c.getNombre());
        txtCedula.setText(c.getCedula());
        txtDireccion.setText(c.getDireccion());
        txtTelefono.setText(c.getTelefono());
    }

    private int buscarIndicePorCedula(String ced) {
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getCedula().equals(ced)) return i;
        }
        return -1;
    }

    private int buscarIndicePorNombre(String nombre) {
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getNombre().equalsIgnoreCase(nombre)) return i;
                // si quieres búsqueda por contiene:
                // if (listaClientes.get(i).getNombre().toLowerCase().contains(nombre.toLowerCase())) return i;
        }
        return -1;
    }

    // ===================== CRUD =====================
    private void guardarCliente() {
        String errores = validarFormulario();
        if (!errores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Corrige:\n" + errores, "Errores", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ced = txtCedula.getText().trim();
        if (buscarIndicePorCedula(ced) != -1) {
            JOptionPane.showMessageDialog(this, "Ya existe un cliente con la misma cédula.", "Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente c = clienteDesdeFormulario();
        listaClientes.add(c);

        JOptionPane.showMessageDialog(this,
                "✅ Cliente guardado.\nTotal clientes: " + listaClientes.size(),
                "Guardar", JOptionPane.INFORMATION_MESSAGE);

        estadoInicial();
    }

    private void buscarCliente() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un dato para buscar.", "Buscar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (rdbPorCedula.isSelected()) {
            indiceSeleccionado = buscarIndicePorCedula(criterio);
        } else {
            indiceSeleccionado = buscarIndicePorNombre(criterio);
        }

        if (indiceSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "No se encontró el cliente.", "Buscar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Cliente encontrado = listaClientes.get(indiceSeleccionado);
        llenarFormulario(encontrado);

        // Habilitar edición
        txtNombre.setEnabled(true);
        txtCedula.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);

        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnActualizar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }

    private void actualizarCliente() {
        if (indiceSeleccionado < 0 || indiceSeleccionado >= listaClientes.size()) {
            JOptionPane.showMessageDialog(this, "Primero busque un cliente para actualizar.", "Actualizar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String errores = validarFormulario();
        if (!errores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Corrige:\n" + errores, "Errores", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar duplicado de cédula si la cambiaron
        String nuevaCedula = txtCedula.getText().trim();
        int idxDuplicado = buscarIndicePorCedula(nuevaCedula);
        if (idxDuplicado != -1 && idxDuplicado != indiceSeleccionado) {
            JOptionPane.showMessageDialog(this, "La cédula ingresada ya pertenece a otro cliente.", "Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente edit = listaClientes.get(indiceSeleccionado);
        edit.setNombre(txtNombre.getText().trim());
        edit.setCedula(nuevaCedula);
        edit.setDireccion(txtDireccion.getText().trim());
        edit.setTelefono(txtTelefono.getText().trim());

        JOptionPane.showMessageDialog(this, "✅ Cliente actualizado.", "Actualizar", JOptionPane.INFORMATION_MESSAGE);

        indiceSeleccionado = -1;
        estadoInicial();
    }

    private void eliminarCliente() {
        if (indiceSeleccionado < 0 || indiceSeleccionado >= listaClientes.size()) {
            JOptionPane.showMessageDialog(this, "Primero busque un cliente para eliminar.", "Eliminar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el cliente seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {
            listaClientes.remove(indiceSeleccionado);
            JOptionPane.showMessageDialog(this,
                    "✅ Cliente eliminado.\nTotal clientes: " + listaClientes.size(),
                    "Eliminar", JOptionPane.INFORMATION_MESSAGE);

            indiceSeleccionado = -1;
            estadoInicial();
        }
    }

    // ===================== Main =====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmCliente().setVisible(true));
    }
}
