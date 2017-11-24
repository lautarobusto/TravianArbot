package ABMs;

import travianarbot.modelo.ArmadaTableModel;
import java.awt.Frame;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import travianarbot.Config;
import travianarbot.dao.DAOException;
import travianarbot.dao.ManagerDAO;
import travianarbot.dao.sqlite.SQLiteManagerDAO;
import travianarbot.gui.TravianArbotGui;
import travianarbot.modelo.Armada;
import travianarbot.modelo.Tropa;

public class Armadas extends javax.swing.JFrame {

    private ManagerDAO manager;
    private ArmadaTableModel model;
    private Frame frm = new Frame();
    private Armada armada;
    private List<Tropa> tropas = new ArrayList<>();

    public Armadas(TravianArbotGui aThis, ManagerDAO manager) throws DAOException {
        initComponents();
        setIcons();
        frm = aThis;
        frm.setEnabled(false);
        this.manager = manager;
        this.model = new ArmadaTableModel(manager.getArmadaDAO());
        this.model.updateModel();
        this.armadaTable.setModel(model);
        this.armadaTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.model.resizeColumnWidth(armadaTable);
        this.armadaTable.setAutoResizeMode(armadaTable.AUTO_RESIZE_ALL_COLUMNS);
        this.armadaTable.getTableHeader().setReorderingAllowed(false);
        this.armadaTable.getSelectionModel().addListSelectionListener((e) -> {
            boolean seleccionValida = (armadaTable.getSelectedRow() != -1);
            editar.setEnabled(seleccionValida);
            borrar.setEnabled(seleccionValida);
            guardar.setEnabled(false);
        });
        setIsEditable(false);
        Config config = new Config();
        try {

            tropas = manager.getTropaDAO().obtenerPorRaza(config.GetPropertie("Raza"));
        } catch (DAOException ex) {
            Logger.getLogger(Armadas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Armadas(Vacas aThis, ManagerDAO manager) throws DAOException {
        initComponents();
        setIcons();
        frm = aThis;
        frm.setEnabled(false);
        this.manager = manager;
        this.model = new ArmadaTableModel(manager.getArmadaDAO());
        this.model.updateModel();
        this.armadaTable.setModel(model);
        this.armadaTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.model.resizeColumnWidth(armadaTable);
        this.armadaTable.setAutoResizeMode(armadaTable.AUTO_RESIZE_ALL_COLUMNS);
        this.armadaTable.getTableHeader().setReorderingAllowed(false);
        this.armadaTable.getSelectionModel().addListSelectionListener((e) -> {
            boolean seleccionValida = (armadaTable.getSelectedRow() != -1);
            editar.setEnabled(seleccionValida);
            borrar.setEnabled(seleccionValida);
            guardar.setEnabled(false);
        });
        setIsEditable(false);
        Config config = new Config();
        try {
            tropas = manager.getTropaDAO().obtenerPorRaza(config.GetPropertie("Raza"));
        } catch (DAOException ex) {
            Logger.getLogger(Armadas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIsEditable(Boolean isEditable) {
        this.nombreArmada.setEnabled(isEditable);
        this.tropa1.setEnabled(isEditable);
        this.tropa2.setEnabled(isEditable);
        this.tropa3.setEnabled(isEditable);
        this.tropa4.setEnabled(isEditable);
        this.tropa5.setEnabled(isEditable);
        this.tropa6.setEnabled(isEditable);
        this.tropa7.setEnabled(isEditable);
        this.tropa8.setEnabled(isEditable);
        this.tropa9.setEnabled(isEditable);
        this.tropa10.setEnabled(isEditable);
        this.tropa11.setEnabled(isEditable);
    }

    public void clearFields() {
        nombreArmada.setText("");
        tropa1.setValue(0);
        tropa2.setValue(0);
        tropa3.setValue(0);
        tropa4.setValue(0);
        tropa5.setValue(0);
        tropa6.setValue(0);
        tropa7.setValue(0);
        tropa8.setValue(0);
        tropa9.setValue(0);
        tropa10.setValue(0);
        tropa11.setValue(0);
        armadaTable.clearSelection();

    }

    public void loadData() {
        if (armada != null) {
            nombreArmada.setText(armada.getNombre());
            tropa1.setValue(armada.getT1());
            tropa2.setValue(armada.getT2());
            tropa3.setValue(armada.getT3());
            tropa4.setValue(armada.getT4());
            tropa5.setValue(armada.getT5());
            tropa6.setValue(armada.getT6());
            tropa7.setValue(armada.getT7());
            tropa8.setValue(armada.getT8());
            tropa9.setValue(armada.getT9());
            tropa10.setValue(armada.getT10());
            tropa11.setValue(armada.getT11());
            elo.setText(String.valueOf(armada.getPuntos_ataque()));
            velocidad.setText(String.valueOf(armada.getVelocidad()));

        } else {
            nombreArmada.setText("");
            tropa1.setValue(0);
            tropa2.setValue(0);
            tropa3.setValue(0);
            tropa4.setValue(0);
            tropa5.setValue(0);
            tropa6.setValue(0);
            tropa7.setValue(0);
            tropa8.setValue(0);
            tropa9.setValue(0);
            tropa10.setValue(0);
            tropa11.setValue(0);
            elo.setText(String.valueOf(0));
            velocidad.setText(String.valueOf(0));

        }
    }

    public void SaveData() {
        if (armada == null) {
            armada = new Armada();
        }
        armada.setNombre(nombreArmada.getText());
        armada.setT1((Integer) tropa1.getValue());
        armada.setT2((Integer) tropa2.getValue());
        armada.setT3((Integer) tropa3.getValue());
        armada.setT4((Integer) tropa4.getValue());
        armada.setT5((Integer) tropa5.getValue());
        armada.setT6((Integer) tropa6.getValue());
        armada.setT7((Integer) tropa7.getValue());
        armada.setT8((Integer) tropa8.getValue());
        armada.setT9((Integer) tropa9.getValue());
        armada.setT10((Integer) tropa10.getValue());
        armada.setT11((Integer) tropa11.getValue());
        armada.setPuntos_ataque(Integer.valueOf(elo.getText()));
        armada.setVelocidad(Integer.valueOf(velocidad.getText()));
    }

    public void getElo() {
        int elo = 0;
        elo += (Integer) tropa1.getValue() * tropas.get(0).getAtaque_tropa();
        elo += (Integer) tropa2.getValue() * tropas.get(1).getAtaque_tropa();
        elo += (Integer) tropa3.getValue() * tropas.get(2).getAtaque_tropa();
        elo += (Integer) tropa4.getValue() * tropas.get(3).getAtaque_tropa();
        elo += (Integer) tropa5.getValue() * tropas.get(4).getAtaque_tropa();
        elo += (Integer) tropa6.getValue() * tropas.get(5).getAtaque_tropa();
        elo += (Integer) tropa7.getValue() * tropas.get(6).getAtaque_tropa();
        elo += (Integer) tropa8.getValue() * tropas.get(7).getAtaque_tropa();
        elo += (Integer) tropa9.getValue() * tropas.get(8).getAtaque_tropa();
        elo += (Integer) tropa10.getValue() * tropas.get(9).getAtaque_tropa();
        this.elo.setText(String.valueOf(elo));
    }

    public void getVelocidad() {
        List<Integer> velocidad = new ArrayList<>();

        if ((Integer) tropa1.getValue() != 0) {
            velocidad.add(tropas.get(0).getVelocidad_tropa());
        }
        if ((Integer) tropa2.getValue() != 0) {
            velocidad.add(tropas.get(1).getVelocidad_tropa());
        }

        if ((Integer) tropa3.getValue() != 0) {
            velocidad.add(tropas.get(2).getVelocidad_tropa());
        }

        if ((Integer) tropa4.getValue() != 0) {
            velocidad.add(tropas.get(3).getVelocidad_tropa());
        }

        if ((Integer) tropa5.getValue() != 0) {
            velocidad.add(tropas.get(4).getVelocidad_tropa());
        }

        if ((Integer) tropa6.getValue() != 0) {
            velocidad.add(tropas.get(5).getVelocidad_tropa());
        }

        if ((Integer) tropa7.getValue() != 0) {
            velocidad.add(tropas.get(6).getVelocidad_tropa());
        }

        if ((Integer) tropa8.getValue() != 0) {
            velocidad.add(tropas.get(7).getVelocidad_tropa());
        }

        if ((Integer) tropa9.getValue() != 0) {
            velocidad.add(tropas.get(8).getVelocidad_tropa());
        }
        if ((Integer) tropa10.getValue() != 0) {
            velocidad.add(tropas.get(9).getVelocidad_tropa());
        }

        if ((Integer) tropa11.getValue() != 0) {
            velocidad.add(14);
        }
        Collections.sort(velocidad);
        if (!velocidad.isEmpty()) {
            this.velocidad.setText(String.valueOf(velocidad.get(0)));

        } else {
            this.velocidad.setText(String.valueOf(0));
        }

    }

//@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        nuevo = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        editar = new javax.swing.JButton();
        borrar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        guardar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        icon1 = new javax.swing.JLabel();
        tropa1 = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        nombreArmada = new javax.swing.JTextField();
        tropa2 = new javax.swing.JSpinner();
        icon2 = new javax.swing.JLabel();
        tropa3 = new javax.swing.JSpinner();
        icon3 = new javax.swing.JLabel();
        tropa4 = new javax.swing.JSpinner();
        icon4 = new javax.swing.JLabel();
        tropa5 = new javax.swing.JSpinner();
        icon5 = new javax.swing.JLabel();
        tropa6 = new javax.swing.JSpinner();
        icon6 = new javax.swing.JLabel();
        tropa7 = new javax.swing.JSpinner();
        icon7 = new javax.swing.JLabel();
        tropa8 = new javax.swing.JSpinner();
        icon8 = new javax.swing.JLabel();
        tropa9 = new javax.swing.JSpinner();
        icon9 = new javax.swing.JLabel();
        tropa10 = new javax.swing.JSpinner();
        icon10 = new javax.swing.JLabel();
        tropa11 = new javax.swing.JSpinner();
        icon11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        armadaTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        elo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        velocidad = new javax.swing.JTextField();

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

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

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        tropa1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        jLabel22.setText("Nombre de la armada");

        tropa2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        tropa3.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        tropa4.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        tropa5.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        tropa6.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/h.png"))); // NOI18N

        tropa7.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        tropa8.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        tropa9.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        tropa10.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999999, 1));
        tropa10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        tropa11.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));
        tropa11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tropa1FocusLost(evt);
            }
        });

        icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icon2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icon3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icon4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icon5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nombreArmada, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(icon6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icon7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icon8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icon9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icon10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(icon11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tropa11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(icon4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tropa4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(nombreArmada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(icon1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tropa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(icon2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tropa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(icon3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tropa3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(icon5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tropa5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(icon7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tropa7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icon8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tropa8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icon9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tropa9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icon10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tropa10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icon11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tropa11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(icon6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tropa6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        armadaTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(armadaTable);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("Puntos de Off toal:");
        jLabel2.setMaximumSize(new java.awt.Dimension(100, 25));
        jLabel2.setMinimumSize(new java.awt.Dimension(100, 25));
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, -1));

        elo.setEditable(false);
        elo.setEnabled(false);
        elo.setPreferredSize(new java.awt.Dimension(100, 25));
        elo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eloActionPerformed(evt);
            }
        });
        jPanel3.add(elo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        jLabel1.setText("Velocidad de Armada");
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 120, -1));

        velocidad.setEditable(false);
        velocidad.setEnabled(false);
        velocidad.setPreferredSize(new java.awt.Dimension(100, 25));
        jPanel3.add(velocidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        frm.setEnabled(true);
        frm.toFront();
        try {
            ((Vacas) frm).getNfillArmadas();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowClosed

    private void borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarActionPerformed
        try {
            getArmadaSelected();

            manager.getArmadaDAO().eliminar(armada);
            this.model = new ArmadaTableModel(manager.getArmadaDAO());
            this.model.updateModel();
            this.armadaTable.setModel(model);
        } catch (DAOException ex) {
            Logger.getLogger(Armadas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Armadas.class.getName()).log(Level.SEVERE, null, ex);
        }
        guardar.setEnabled(false);
        cancelar.setEnabled(false);
        editar.setEnabled(false);
        nuevo.setEnabled(true);
        armadaTable.setEnabled(true);
        setIsEditable(false);
        clearFields();


    }//GEN-LAST:event_borrarActionPerformed

    private void nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoActionPerformed
        armada = null;
        setIsEditable(true);
        cancelar.setEnabled(true);
        guardar.setEnabled(true);
        editar.setEnabled(false);
        borrar.setEnabled(false);
        armadaTable.setEnabled(false);
        clearFields();
        nombreArmada.requestFocus();
    }//GEN-LAST:event_nuevoActionPerformed

    private void getArmadaSelected() throws DAOException, SQLException {
        manager = new SQLiteManagerDAO();
        int id = (int) armadaTable.getValueAt(armadaTable.getSelectedRow(), 0);
        this.armada = manager.getArmadaDAO().obtener(id);

    }

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        try {

            getArmadaSelected();
            loadData();
            guardar.setEnabled(true);
            cancelar.setEnabled(true);
            setIsEditable(true);
            armadaTable.setEnabled(false);
            nuevo.setEnabled(false);
        } catch (DAOException ex) {
            Logger.getLogger(Armadas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Armadas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editarActionPerformed

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        clearFields();
        setIsEditable(false);
        guardar.setEnabled(false);
        cancelar.setEnabled(false);
        armadaTable.setEnabled(true);
        nuevo.setEnabled(true);

    }//GEN-LAST:event_cancelarActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        getElo();
        getVelocidad();
        SaveData();
        if (armada.getId() == -1) {
            try {
                manager.getArmadaDAO().insertar(armada);
                this.model = new ArmadaTableModel(manager.getArmadaDAO());
                this.model.updateModel();
                this.armadaTable.setModel(model);

            } catch (DAOException ex) {
                Logger.getLogger(Armadas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                manager.getArmadaDAO().modificar(armada);
                this.model = new ArmadaTableModel(manager.getArmadaDAO());
                this.model.updateModel();
                this.armadaTable.setModel(model);

            } catch (DAOException ex) {
                Logger.getLogger(Armadas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        guardar.setEnabled(false);
        cancelar.setEnabled(false);
        editar.setEnabled(false);
        nuevo.setEnabled(true);
        armadaTable.setEnabled(true);
        setIsEditable(false);
        clearFields();

    }//GEN-LAST:event_guardarActionPerformed

    private void tropa1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tropa1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tropa1FocusLost

    private void eloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eloActionPerformed

    private void setIcons() {
        Config config = new Config();

        String value = config.GetPropertie("Raza");

        switch (value) {
            case "Romanos":
                icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1.gif")));
                icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/2.gif")));
                icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/3.gif")));
                icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/4.gif")));
                icon5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/5.gif")));
                icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/6.gif")));
                icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/7.gif")));
                icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/8.gif")));
                icon9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/9.gif")));
                icon10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/10.gif")));
                icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/h.png")));
                break;
            case "Germanos":
                icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/11.gif")));
                icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/12.gif")));
                icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/13.gif")));
                icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/14.gif")));
                icon5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/15.gif")));
                icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/16.gif")));
                icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/17.gif")));
                icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/18.gif")));
                icon9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/19.gif")));
                icon10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/20.gif")));
                icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/h.png")));
                break;
            case "Galos":
                icon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/21.gif")));
                icon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/22.gif")));
                icon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/23.gif")));
                icon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/24.gif")));
                icon5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/25.gif")));
                icon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/26.gif")));
                icon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/27.gif")));
                icon8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/28.gif")));
                icon9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/29.gif")));
                icon10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/30.gif")));
                icon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/h.png")));
                break;

        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable armadaTable;
    private javax.swing.JButton borrar;
    private javax.swing.JButton cancelar;
    private javax.swing.JButton editar;
    private javax.swing.JTextField elo;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel icon1;
    private javax.swing.JLabel icon10;
    private javax.swing.JLabel icon11;
    private javax.swing.JLabel icon2;
    private javax.swing.JLabel icon3;
    private javax.swing.JLabel icon4;
    private javax.swing.JLabel icon5;
    private javax.swing.JLabel icon6;
    private javax.swing.JLabel icon7;
    private javax.swing.JLabel icon8;
    private javax.swing.JLabel icon9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField nombreArmada;
    private javax.swing.JButton nuevo;
    private javax.swing.JSpinner tropa1;
    private javax.swing.JSpinner tropa10;
    private javax.swing.JSpinner tropa11;
    private javax.swing.JSpinner tropa2;
    private javax.swing.JSpinner tropa3;
    private javax.swing.JSpinner tropa4;
    private javax.swing.JSpinner tropa5;
    private javax.swing.JSpinner tropa6;
    private javax.swing.JSpinner tropa7;
    private javax.swing.JSpinner tropa8;
    private javax.swing.JSpinner tropa9;
    private javax.swing.JTextField velocidad;
    // End of variables declaration//GEN-END:variables
}
