package ABMs;

import java.awt.Frame;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import travianarbot.dao.DAOException;
import travianarbot.dao.ManagerDAO;
import travianarbot.dao.sqlite.SQLiteManagerDAO;
import travianarbot.gui.TravianArbotGui;
import travianarbot.modelo.Aldea;
import travianarbot.modelo.Armada;
import travianarbot.modelo.Vaca;
import travianarbot.modelo.VacaTableModel;

public class Vacas extends javax.swing.JFrame {

    private ManagerDAO manager;
    private VacaTableModel model;
    private Frame frm = new Frame();
    private List<Aldea> aldeaList = new ArrayList<Aldea>();
    private List<Armada> armadasList = new ArrayList<Armada>();
    private Vaca vaca = new Vaca();

    public Vacas(TravianArbotGui aThis, ManagerDAO manager) throws DAOException {
        initComponents();

        frm = aThis;
        frm.setEnabled(false);

        this.manager = manager;
        this.model = new VacaTableModel(manager.getVacaDAO());
        this.model.updateModel();
        this.vacaTable.setModel(model);
        this.vacaTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.model.resizeColumnWidth(vacaTable);
        this.vacaTable.setAutoResizeMode(vacaTable.AUTO_RESIZE_ALL_COLUMNS);
        this.vacaTable.getTableHeader().setReorderingAllowed(false);
        this.vacaTable.getSelectionModel().addListSelectionListener((e) -> {
            boolean seleccionValida = (vacaTable.getSelectedRow() != -1);
            editar.setEnabled(seleccionValida);
            borrar.setEnabled(seleccionValida);
            guardar.setEnabled(!seleccionValida);
            cancelar.setEnabled(seleccionValida);
            nuevo.setEnabled(!seleccionValida);
        });
        this.coordenadax.addChangeListener((e) -> {

            try {
                this.coordenadax.commitEdit();
            } catch (ParseException ex) {
                Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateKarte();
            updateDistancia();
            updateTiempo();
        });

        this.coordenaY.addChangeListener((e) -> {

            try {
                this.coordenaY.commitEdit();
            } catch (ParseException ex) {
                Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateKarte();
            updateDistancia();
            updateTiempo();
        });

        setIsEditable(false);
        getNfillAldeas();
        getNfillArmadas();
        setIsEditable(false);
    }

    public void updateKarte() {
        int karte = 320801;

        int valor = karte + ((int) coordenadax.getValue()) - (((int) coordenaY.getValue()) * 801);

        Z.setText(String.valueOf(valor));

    }

    public void updateTiempo() {
        float dist = Float.valueOf(distancia.getText());
        int vel = armadasList.get(armadas.getSelectedIndex()).getVelocidad();

        long millis = (long) ((dist / vel) * 3600000);

        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        tiempoViaje.setText(hms);

    }

    public void updateDistancia() {

        int xOrigen = aldeaList.get(aldeaOrigen.getSelectedIndex()).getCoordenada_x();
        int yOrigen = aldeaList.get(aldeaOrigen.getSelectedIndex()).getCoordenada_y();
        int xDestino = (int) coordenadax.getValue();
        int yDestino = (int) coordenaY.getValue();

        int xRes = xOrigen - xDestino;

        int yRes = yOrigen - yDestino;

        double distancias = Math.sqrt(((xRes * xRes) + (yRes * yRes)));

        BigDecimal bigDecimal = new BigDecimal(distancias);
        BigDecimal roundedWithScale = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
        distancia.setText(String.valueOf(roundedWithScale));
    }

    public void getNfillArmadas() {

        try {
            armadasList = manager.getArmadaDAO().obtenerTodos();
        } catch (DAOException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        }
        armadas.removeAllItems();
        for (Armada item : armadasList) {
            armadas.addItem(item.getNombre());
        }
    }

    public void getNfillAldeas() {

        try {
            aldeaList = manager.getAldeaDAO().obtenerTodos();
        } catch (DAOException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Aldea item : aldeaList) {
            aldeaOrigen.addItem(item.getNombre_aldea());
        }
    }

    public void setIsEditable(Boolean isEditable) {
        this.nombreVaca.setEnabled(isEditable);
        this.tipoMovimiento.setEnabled(isEditable);
        this.aldeaOrigen.setEnabled(isEditable);
        this.coordenadax.setEnabled(isEditable);
        this.coordenaY.setEnabled(isEditable);
        this.armadas.setEnabled(isEditable);
        this.addArmada.setEnabled(isEditable);
        this.activo.setEnabled(isEditable);

    }

    public void clearFields() {
        this.nombreVaca.setText("");
        this.tipoMovimiento.setSelectedIndex(2);
        this.aldeaOrigen.setSelectedIndex(0);
        this.coordenadax.setValue(0);
        this.coordenaY.setValue(0);
        this.armadas.setSelectedIndex(0);
        this.activo.setSelected(false);
        vacaTable.clearSelection();

    }

    public void loadData() {
        String aldea = null;
        String armada = null;
        if (vaca != null) {
            this.nombreVaca.setText(vaca.getNombre());
            this.tipoMovimiento.setSelectedItem(vaca.getId_movimiento());
            for (Aldea item : aldeaList) {
                if (item.getId_aldea() == vaca.getId_aldea_origen()) {
                    aldea = item.getNombre_aldea();
                }
            }
            this.aldeaOrigen.setSelectedItem(aldea);
            this.coordenadax.setValue(vaca.getCoordenada_x());
            this.coordenaY.setValue(vaca.getCoordenada_y());
            for (Armada item : armadasList) {
                if (item.getId() == vaca.getId()) {
                    armada = item.getNombre();
                }
            }
            this.armadas.setSelectedItem(armada);
            this.activo.setSelected(vaca.isActivo());
            this.Z.setText(String.valueOf(vaca.getZ()));
            vacaTable.clearSelection();

        } else {
            nombreVaca.setText("");

        }
    }

    public void SaveData() {
        if (vaca == null) {
            vaca = new Vaca();

        }
        vaca.setNombre(nombreVaca.getText());
        vaca.setId_aldea_origen(aldeaList.get(aldeaOrigen.getSelectedIndex()).getId_aldea());
        vaca.setId_movimiento(tipoMovimiento.getSelectedItem().toString());
        vaca.setId_armada_activa(armadasList.get(armadas.getSelectedIndex()).getId());
        vaca.setCoordenada_x((int) coordenadax.getValue());
        vaca.setCoordenada_y((int) coordenaY.getValue());
        vaca.setActivo(activo.isSelected());
        vaca.setZ(Integer.valueOf(Z.getText()));

    }

//@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        nuevo = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        editar = new javax.swing.JButton();
        borrar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        guardar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        vacaTable = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        nombreVaca = new javax.swing.JTextField();
        tipoMovimiento = new javax.swing.JComboBox<>();
        aldeaOrigen = new javax.swing.JComboBox<>();
        coordenadax = new javax.swing.JSpinner();
        coordenaY = new javax.swing.JSpinner();
        activo = new javax.swing.JCheckBox();
        armadas = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        addArmada = new javax.swing.JButton();
        Z = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        distancia = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        tiempoViaje = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel28 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);

        nuevo.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buttons/add-3.png"))); // NOI18N
        nuevo.setText("Nuevo");
        nuevo.setFocusable(false);
        nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nuevo.setMaximumSize(new java.awt.Dimension(60, 60));
        nuevo.setMinimumSize(new java.awt.Dimension(60, 60));
        nuevo.setPreferredSize(new java.awt.Dimension(60, 60));
        nuevo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        nuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(nuevo);
        jToolBar1.add(jSeparator1);

        editar.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buttons/edit.png"))); // NOI18N
        editar.setText("Editar");
        editar.setEnabled(false);
        editar.setFocusable(false);
        editar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editar.setMaximumSize(new java.awt.Dimension(60, 60));
        editar.setMinimumSize(new java.awt.Dimension(60, 60));
        editar.setPreferredSize(new java.awt.Dimension(60, 60));
        editar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        editar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });
        jToolBar1.add(editar);

        borrar.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        borrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buttons/garbage-2.png"))); // NOI18N
        borrar.setText("Borrar");
        borrar.setEnabled(false);
        borrar.setFocusable(false);
        borrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        borrar.setMaximumSize(new java.awt.Dimension(60, 60));
        borrar.setMinimumSize(new java.awt.Dimension(60, 60));
        borrar.setPreferredSize(new java.awt.Dimension(60, 60));
        borrar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        borrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarActionPerformed(evt);
            }
        });
        jToolBar1.add(borrar);
        jToolBar1.add(jSeparator2);

        guardar.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buttons/save.png"))); // NOI18N
        guardar.setText("Guardar");
        guardar.setEnabled(false);
        guardar.setFocusable(false);
        guardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        guardar.setMaximumSize(new java.awt.Dimension(60, 60));
        guardar.setMinimumSize(new java.awt.Dimension(60, 60));
        guardar.setPreferredSize(new java.awt.Dimension(60, 60));
        guardar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        guardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        jToolBar1.add(guardar);

        cancelar.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Buttons/error.png"))); // NOI18N
        cancelar.setText("Cancelar");
        cancelar.setEnabled(false);
        cancelar.setFocusable(false);
        cancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelar.setMaximumSize(new java.awt.Dimension(60, 60));
        cancelar.setMinimumSize(new java.awt.Dimension(60, 60));
        cancelar.setPreferredSize(new java.awt.Dimension(60, 60));
        cancelar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        cancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });
        jToolBar1.add(cancelar);

        vacaTable.setAutoCreateRowSorter(true);
        vacaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(vacaTable);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel22.setText("Nombre del Destino");
        jLabel22.setMaximumSize(new java.awt.Dimension(110, 20));
        jLabel22.setMinimumSize(new java.awt.Dimension(110, 20));
        jLabel22.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        nombreVaca.setMaximumSize(new java.awt.Dimension(100, 25));
        nombreVaca.setMinimumSize(new java.awt.Dimension(100, 25));
        nombreVaca.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel2.add(nombreVaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 140, -1));

        tipoMovimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Refuerzo", "Ataque", "Asalto" }));
        tipoMovimiento.setSelectedIndex(2);
        tipoMovimiento.setToolTipText("");
        tipoMovimiento.setMaximumSize(new java.awt.Dimension(100, 25));
        tipoMovimiento.setMinimumSize(new java.awt.Dimension(100, 25));
        tipoMovimiento.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel2.add(tipoMovimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 140, -1));

        aldeaOrigen.setMinimumSize(new java.awt.Dimension(100, 25));
        aldeaOrigen.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel2.add(aldeaOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 140, -1));

        coordenadax.setModel(new javax.swing.SpinnerNumberModel(0, -400, 400, 1));
        coordenadax.setMaximumSize(new java.awt.Dimension(60, 25));
        coordenadax.setMinimumSize(new java.awt.Dimension(60, 25));
        coordenadax.setPreferredSize(new java.awt.Dimension(60, 25));
        jPanel2.add(coordenadax, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 110, -1));

        coordenaY.setModel(new javax.swing.SpinnerNumberModel(0, -400, 400, 1));
        coordenaY.setMaximumSize(new java.awt.Dimension(60, 25));
        coordenaY.setMinimumSize(new java.awt.Dimension(60, 25));
        coordenaY.setPreferredSize(new java.awt.Dimension(60, 25));
        jPanel2.add(coordenaY, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 110, -1));

        activo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        activo.setText("Activa?");
        activo.setPreferredSize(new java.awt.Dimension(110, 20));
        activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activoActionPerformed(evt);
            }
        });
        jPanel2.add(activo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        armadas.setMaximumSize(new java.awt.Dimension(100, 25));
        armadas.setMinimumSize(new java.awt.Dimension(100, 25));
        armadas.setPreferredSize(new java.awt.Dimension(100, 25));
        armadas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                armadasItemStateChanged(evt);
            }
        });
        jPanel2.add(armadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 50, 110, -1));

        jLabel25.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel25.setText("Coordenada X");
        jLabel25.setDoubleBuffered(true);
        jLabel25.setMaximumSize(new java.awt.Dimension(110, 20));
        jLabel25.setMinimumSize(new java.awt.Dimension(110, 20));
        jLabel25.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 90, -1));

        addArmada.setIcon(new javax.swing.ImageIcon("E:\\Documentos\\NetBeansProjects\\TravianArbot\\Resources\\Icons\\add-1.png")); // NOI18N
        addArmada.setMaximumSize(new java.awt.Dimension(54, 25));
        addArmada.setMinimumSize(new java.awt.Dimension(54, 25));
        addArmada.setPreferredSize(new java.awt.Dimension(54, 25));
        addArmada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addArmadaActionPerformed(evt);
            }
        });
        jPanel2.add(addArmada, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 30, -1));

        Z.setText("336852");
        Z.setEnabled(false);
        Z.setMaximumSize(new java.awt.Dimension(100, 25));
        Z.setMinimumSize(new java.awt.Dimension(100, 25));
        Z.setPreferredSize(new java.awt.Dimension(100, 25));
        Z.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ZActionPerformed(evt);
            }
        });
        jPanel2.add(Z, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 60, -1));

        jLabel27.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel27.setText("Tiempo de viaje aprox");
        jLabel27.setMaximumSize(new java.awt.Dimension(110, 20));
        jLabel27.setMinimumSize(new java.awt.Dimension(110, 20));
        jLabel27.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 120, -1));

        distancia.setText("0");
        distancia.setEnabled(false);
        distancia.setMaximumSize(new java.awt.Dimension(100, 25));
        distancia.setMinimumSize(new java.awt.Dimension(100, 25));
        distancia.setPreferredSize(new java.awt.Dimension(100, 25));
        distancia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distanciaActionPerformed(evt);
            }
        });
        jPanel2.add(distancia, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 60, -1));

        jLabel23.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel23.setText("Tipo de Movimiento");
        jLabel23.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 110, -1));

        jLabel24.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel24.setText("Aldea de Origen");
        jLabel24.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 110, -1));

        jLabel26.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel26.setText("Coordenada Y");
        jLabel26.setMaximumSize(new java.awt.Dimension(110, 20));
        jLabel26.setMinimumSize(new java.awt.Dimension(110, 20));
        jLabel26.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 90, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("Distancia");
        jLabel2.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, -1, -1));

        jLabel29.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel29.setText("Karte");
        jLabel29.setMaximumSize(new java.awt.Dimension(110, 20));
        jLabel29.setMinimumSize(new java.awt.Dimension(110, 20));
        jLabel29.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, -1));

        tiempoViaje.setEditable(false);
        tiempoViaje.setEnabled(false);
        tiempoViaje.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel2.add(tiempoViaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, 100, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jRadioButton1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jRadioButton1.setText("Progresion");
        jPanel3.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 100, -1));

        jLabel28.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Formato de tropa a enviar");
        jLabel28.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel28.setMaximumSize(new java.awt.Dimension(110, 20));
        jLabel28.setMinimumSize(new java.awt.Dimension(110, 20));
        jLabel28.setPreferredSize(new java.awt.Dimension(75, 25));
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 30));

        jRadioButton2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jRadioButton2.setText("Armada");
        jPanel3.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 210, 90));

        jTabbedPane3.addTab("Armada", jTabbedPane4);
        jTabbedPane3.addTab("Progresion", jTabbedPane5);

        jPanel2.add(jTabbedPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 300, 90));

        jTabbedPane1.addTab("Vaca Info", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 912, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 195, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Opciones Avanzadas", jPanel1);
        jTabbedPane1.addTab("Informes de Asaltos", jTabbedPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        frm.setEnabled(true);
        frm.toFront();
        ((TravianArbotGui) frm).updateVacasActivasList();
    }//GEN-LAST:event_formWindowClosed

    private void borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarActionPerformed
        try {
            getVacaSelected();
            manager.getVacaDAO().eliminar(vaca);
            this.model = new VacaTableModel(manager.getVacaDAO());
            this.model.updateModel();
            this.vacaTable.setModel(model);
        } catch (DAOException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        }
        guardar.setEnabled(false);
        cancelar.setEnabled(false);
        editar.setEnabled(false);
        nuevo.setEnabled(true);
        vacaTable.setEnabled(true);
        setIsEditable(false);
        clearFields();


    }//GEN-LAST:event_borrarActionPerformed

    private void nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoActionPerformed
        vaca = null;
        setIsEditable(true);
        cancelar.setEnabled(true);
        guardar.setEnabled(true);
        editar.setEnabled(false);
        borrar.setEnabled(false);
        vacaTable.setEnabled(false);
        clearFields();
        nombreVaca.requestFocus();
    }//GEN-LAST:event_nuevoActionPerformed

    private void getVacaSelected() throws DAOException, SQLException {
        manager = new SQLiteManagerDAO();
        int id = (int) vacaTable.getValueAt(vacaTable.getSelectedRow(), 0);
        this.vaca = manager.getVacaDAO().obtener(id);

    }

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        try {

            getVacaSelected();
            loadData();
            guardar.setEnabled(true);
            cancelar.setEnabled(true);
            setIsEditable(true);
            vacaTable.setEnabled(false);
            nuevo.setEnabled(false);
        } catch (DAOException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editarActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        clearFields();
        setIsEditable(false);
        guardar.setEnabled(false);
        cancelar.setEnabled(false);
        vacaTable.setEnabled(true);
        nuevo.setEnabled(true);

    }//GEN-LAST:event_cancelarActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed

        try {
            coordenaY.commitEdit();
            coordenaY.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateKarte();
        updateDistancia();

        {
            SaveData();

            if (vaca.getId() == -1) {
                if (!manager.getVacaDAO().exist(Integer.valueOf(Z.getText()))) {
                    try {
                        manager.getVacaDAO().insertar(vaca);
                        this.model = new VacaTableModel(manager.getVacaDAO());
                        this.model.updateModel();
                        this.vacaTable.setModel(model);

                    } catch (DAOException ex) {
                        Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe una vaca con esas coordenadas en "
                            + "la base de datos", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                    coordenadax.requestFocus();
                }
                clearFields();
            } else {

                try {
                    manager.getVacaDAO().modificar(vaca);
                    this.model = new VacaTableModel(manager.getVacaDAO());
                    this.model.updateModel();
                    this.vacaTable.setModel(model);

                } catch (DAOException ex) {
                    Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
                }
                guardar.setEnabled(false);
                cancelar.setEnabled(false);
                editar.setEnabled(false);
                nuevo.setEnabled(true);
                vacaTable.setEnabled(true);
                setIsEditable(false);
                clearFields();

                return;
            }

        }
        clearFields();
        cancelar.setEnabled(false);
        editar.setEnabled(false);
        nuevo.setEnabled(true);
        vacaTable.setEnabled(true);
        setIsEditable(false);
        guardar.setEnabled(false);

    }//GEN-LAST:event_guardarActionPerformed

    private void addArmadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addArmadaActionPerformed
        try {
            ManagerDAO manager = new SQLiteManagerDAO();
            Armadas armadas = new Armadas(this, manager);
            armadas.setVisible(true);
//            manager.closeConection();

        } catch (DAOException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:

    }//GEN-LAST:event_addArmadaActionPerformed

    private void distanciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distanciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_distanciaActionPerformed

    private void activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_activoActionPerformed

    private void ZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ZActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ZActionPerformed

    private void armadasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_armadasItemStateChanged
        updateTiempo();
    }//GEN-LAST:event_armadasItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Z;
    private javax.swing.JCheckBox activo;
    private javax.swing.JButton addArmada;
    private javax.swing.JComboBox<String> aldeaOrigen;
    private javax.swing.JComboBox<String> armadas;
    private javax.swing.JButton borrar;
    private javax.swing.JButton cancelar;
    private javax.swing.JSpinner coordenaY;
    private javax.swing.JSpinner coordenadax;
    private javax.swing.JTextField distancia;
    private javax.swing.JButton editar;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField nombreVaca;
    private javax.swing.JButton nuevo;
    private javax.swing.JTextField tiempoViaje;
    private javax.swing.JComboBox<String> tipoMovimiento;
    private javax.swing.JTable vacaTable;
    // End of variables declaration//GEN-END:variables
}
