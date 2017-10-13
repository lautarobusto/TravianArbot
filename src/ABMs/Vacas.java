package ABMs;

import java.awt.Frame;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
        });

        this.coordenaY.addChangeListener((e) -> {

            try {
                this.coordenaY.commitEdit();
            } catch (ParseException ex) {
                Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateKarte();
        });

        setIsEditable(false);
        getNfillAldeas();
        getNfillArmadas();
        setIsEditable(false);
    }

    public void updateKarte() {
        int karte = 320801;

        int valor = karte + ((int) coordenadax.getValue()) - (((int) coordenaY.getValue()) * 801);
        this.karte.setText(String.valueOf(valor));

    }

    public void getNfillArmadas() {

        try {
            armadasList = manager.getArmadaDAO().obtenerTodos();
        } catch (DAOException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        }
        armadas.removeAllItems();
        for (Armada item : armadasList) {
            armadas.addItem(item.getNombre_armada());
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
            this.nombreVaca.setText(vaca.getNombre_vaca());
            this.tipoMovimiento.setSelectedItem(vaca.getId_movimiento());
            for (Aldea item : aldeaList) {
                if (item.getId_aldea() == vaca.getId_aldea()) {
                    aldea = item.getNombre_aldea();
                }
            }
            this.aldeaOrigen.setSelectedItem(aldea);
            this.coordenadax.setValue(vaca.getCoordenada_x());
            this.coordenaY.setValue(vaca.getCoordenada_y());
            for (Armada item : armadasList) {
                if (item.getId_armada() == vaca.getId_armada()) {
                    armada = item.getNombre_armada();
                }
            }
            this.armadas.setSelectedItem(armada);
            this.activo.setSelected(vaca.getActivo());
            this.karte.setText(String.valueOf(vaca.getZ()));
            vacaTable.clearSelection();

        } else {
            nombreVaca.setText("");

        }
    }

    public void SaveData() {
        if (vaca == null) {
            vaca = new Vaca();

        }
        vaca.setNombre_vaca(nombreVaca.getText());
        vaca.setId_aldea(aldeaList.get(aldeaOrigen.getSelectedIndex()).getId_aldea());
        vaca.setId_movimiento(tipoMovimiento.getSelectedItem().toString());
        vaca.setId_armada(armadasList.get(armadas.getSelectedIndex()).getId_armada());
        vaca.setCoordenada_x((int) coordenadax.getValue());
        vaca.setCoordenada_y((int) coordenaY.getValue());
        vaca.setActivo(activo.isSelected());
        vaca.setZ(Integer.valueOf(karte.getText()));

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
        jLabel23 = new javax.swing.JLabel();
        aldeaOrigen = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        coordenadax = new javax.swing.JSpinner();
        jLabel26 = new javax.swing.JLabel();
        coordenaY = new javax.swing.JSpinner();
        activo = new javax.swing.JCheckBox();
        jLabel28 = new javax.swing.JLabel();
        armadas = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        addArmada = new javax.swing.JButton();
        karte = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

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

        jLabel22.setText("Nombre de la vaca");

        tipoMovimiento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Refuerzo", "Ataque", "Asalto" }));
        tipoMovimiento.setSelectedIndex(2);
        tipoMovimiento.setToolTipText("");

        jLabel23.setText("Tipo de Movimiento");

        jLabel24.setText("Aldea de Origen");

        coordenadax.setModel(new javax.swing.SpinnerNumberModel(0, -400, 400, 1));
        coordenadax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                coordenadaxKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                coordenadaxKeyReleased(evt);
            }
        });

        jLabel26.setText("Coordenada Y");

        coordenaY.setModel(new javax.swing.SpinnerNumberModel(0, -400, 400, 1));
        coordenaY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                coordenaYKeyPressed(evt);
            }
        });

        activo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        activo.setText("Activa?");

        jLabel28.setText("Armada");

        jLabel25.setText("Coordenada X");

        addArmada.setIcon(new javax.swing.ImageIcon("E:\\Documentos\\NetBeansProjects\\TravianArbot\\Resources\\Icons\\add-1.png")); // NOI18N
        addArmada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addArmadaActionPerformed(evt);
            }
        });

        karte.setText("336852");
        karte.setEnabled(false);

        jLabel27.setText("karte");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(activo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tipoMovimiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nombreVaca, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(aldeaOrigen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel28)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(addArmada, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(coordenaY, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(coordenadax)
                    .addComponent(armadas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(karte, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(327, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(aldeaOrigen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(nombreVaca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tipoMovimiento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel25))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(coordenadax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(karte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(coordenaY, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(activo))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(armadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addArmada, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel28)))))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Vaca Info", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 996, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 137, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Opciones Avanzadas", jPanel1);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
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

        {
            SaveData();

            if (vaca.getId_vaca() == -1) {
                if (!manager.getVacaDAO().exist(Integer.valueOf(karte.getText()))) {
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

    private void coordenadaxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_coordenadaxKeyPressed

    }//GEN-LAST:event_coordenadaxKeyPressed

    private void coordenaYKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_coordenaYKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_coordenaYKeyPressed

    private void coordenadaxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_coordenadaxKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_coordenadaxKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox activo;
    private javax.swing.JButton addArmada;
    private javax.swing.JComboBox<String> aldeaOrigen;
    private javax.swing.JComboBox<String> armadas;
    private javax.swing.JButton borrar;
    private javax.swing.JButton cancelar;
    private javax.swing.JSpinner coordenaY;
    private javax.swing.JSpinner coordenadax;
    private javax.swing.JButton editar;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField karte;
    private javax.swing.JTextField nombreVaca;
    private javax.swing.JButton nuevo;
    private javax.swing.JComboBox<String> tipoMovimiento;
    private javax.swing.JTable vacaTable;
    // End of variables declaration//GEN-END:variables
}
