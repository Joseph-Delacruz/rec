package Anthony.De_La_Cruz.view;

import Anthony.De_La_Cruz.controller.ComputerController;
import Anthony.De_La_Cruz.model.Computer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

public class MainFrame extends JFrame {

    private JTextField tfCodigo, tfMarca, tfModelo, tfSO, tfRAM, tfAlmacenamiento;
    private JComboBox<String> cbTipo;
    private JRadioButton rbActivo, rbInactivo;
    private JCheckBox chkMantenimiento;
    private JButton btnCrear, btnActualizar, btnEliminar, btnLimpiar, btnRefrescar;
    private JTable table;
    private DefaultTableModel tableModel;
    private ComputerController controller = new ComputerController();
    private Integer selectedId = null;

    public MainFrame() {
        setTitle("Inventario de Computadoras - Valle Grande");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadTable();
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        tfCodigo = new JTextField();
        tfMarca = new JTextField();
        tfModelo = new JTextField();
        tfSO = new JTextField();
        tfRAM = new JTextField();
        tfAlmacenamiento = new JTextField();

        cbTipo = new JComboBox<>(new String[]{"PC Escritorio", "Laptop", "Servidor", "All-in-One"});

        rbActivo = new JRadioButton("Activo");
        rbInactivo = new JRadioButton("Inactivo");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbActivo);
        bg.add(rbInactivo);
        rbActivo.setSelected(true);

        chkMantenimiento = new JCheckBox("Incluye fecha de mantenimiento");

        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnRefrescar = new JButton("Refrescar");

        int row = 0;
        c.gridx = 0; c.gridy = row; form.add(new JLabel("Código:"), c);
        c.gridx = 1; form.add(tfCodigo, c); row++;

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Tipo:"), c);
        c.gridx = 1; form.add(cbTipo, c); row++;

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Marca:"), c);
        c.gridx = 1; form.add(tfMarca, c); row++;

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Modelo:"), c);
        c.gridx = 1; form.add(tfModelo, c); row++;

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Sistema Operativo:"), c);
        c.gridx = 1; form.add(tfSO, c); row++;

        c.gridx = 0; c.gridy = row; form.add(new JLabel("RAM:"), c);
        c.gridx = 1; form.add(tfRAM, c); row++;

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Almacenamiento (GB):"), c);
        c.gridx = 1; form.add(tfAlmacenamiento, c); row++;

        c.gridx = 0; c.gridy = row; form.add(chkMantenimiento, c); row++;

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Estado:"), c);
        c.gridx = 1;
        JPanel pEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pEstado.add(rbActivo);
        pEstado.add(rbInactivo);
        form.add(pEstado, c); row++;

        c.gridx = 0; c.gridy = row; form.add(btnCrear, c);
        c.gridx = 1; form.add(btnActualizar, c); row++;

        c.gridx = 0; c.gridy = row; form.add(btnEliminar, c);
        c.gridx = 1; form.add(btnLimpiar, c); row++;

        c.gridx = 0; c.gridy = row; form.add(btnRefrescar, c);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Código", "Tipo", "Marca", "Modelo", "SO", "RAM", "Alm", "F. Mant.", "F. Reg.", "Estado"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, form, sp);
        split.setDividerLocation(360);
        add(split, BorderLayout.CENTER);

        btnCrear.addActionListener(e -> doCreate());
        btnActualizar.addActionListener(e -> doUpdate());
        btnEliminar.addActionListener(e -> doDelete());
        btnLimpiar.addActionListener(e -> clearForm());
        btnRefrescar.addActionListener(e -> loadTable());

        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                if (r >= 0) loadSelectedToForm(r);
            }
        });
    }

    private void loadTable() {
        try {
            List<Computer> list = controller.readAll();
            tableModel.setRowCount(0);
            for (Computer c : list) {
                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getCodigo(),
                        c.getTipoEquipo(),
                        c.getMarca(),
                        c.getModelo(),
                        c.getSistema(),
                        c.getRam(),
                        c.getAlmacenamiento(),
                        c.getFechaMantenimiento(),
                        c.getFechaRegistro(),
                        c.getEstado()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void doCreate() {
        try {
            Computer c = readForm();
            controller.create(c);
            loadTable();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void doUpdate() {
        if (selectedId == null) return;
        try {
            Computer c = readForm();
            c.setId(selectedId);
            controller.update(c);
            loadTable();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void doDelete() {
        if (selectedId == null) return;
        try {
            controller.delete(selectedId);
            loadTable();
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private Computer readForm() {
        Computer c = new Computer();
        c.setCodigo(tfCodigo.getText());
        c.setTipoEquipo((String) cbTipo.getSelectedItem());
        c.setMarca(tfMarca.getText());
        c.setModelo(tfModelo.getText());
        c.setSistema(tfSO.getText());
        c.setRam(tfRAM.getText());
        c.setAlmacenamiento(tfAlmacenamiento.getText());
        c.setEstado(rbActivo.isSelected() ? "activo" : "inactivo");
        c.setFechaRegistro(LocalDate.now());
        if (chkMantenimiento.isSelected()) c.setFechaMantenimiento(LocalDate.now());
        return c;
    }

    private void loadSelectedToForm(int r) {
        selectedId = (Integer) tableModel.getValueAt(r, 0);
        tfCodigo.setText(String.valueOf(tableModel.getValueAt(r, 1)));
        cbTipo.setSelectedItem(tableModel.getValueAt(r, 2));
        tfMarca.setText(String.valueOf(tableModel.getValueAt(r, 3)));
        tfModelo.setText(String.valueOf(tableModel.getValueAt(r, 4)));
        tfSO.setText(String.valueOf(tableModel.getValueAt(r, 5)));
        tfRAM.setText(String.valueOf(tableModel.getValueAt(r, 6)));
        tfAlmacenamiento.setText(String.valueOf(tableModel.getValueAt(r, 7)));
    }

    private void clearForm() {
        selectedId = null;
        tfCodigo.setText("");
        tfMarca.setText("");
        tfModelo.setText("");
        tfSO.setText("");
        tfRAM.setText("");
        tfAlmacenamiento.setText("");
        chkMantenimiento.setSelected(false);
        rbActivo.setSelected(true);
        cbTipo.setSelectedIndex(0);
    }
}
