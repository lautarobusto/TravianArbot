package travianarbot.gui;

import ABMs.Armadas;
import ABMs.Vacas;
import java.awt.Frame;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import travianarbot.Config;
import travianarbot.TravianArbot;
import travianarbot.dao.DAOException;
import travianarbot.dao.ManagerDAO;
import travianarbot.dao.sqlite.SQLiteManagerDAO;
import travianarbot.modelo.AldeasTableModel;
import travianarbot.modelo.Cuenta;
import travianarbot.modelo.Vaca;

public class TravianArbotGui extends javax.swing.JFrame {

    private ManagerDAO manager;
    private AldeasTableModel model;
    private WebDriver driver;
    private List<Vaca> vacasActivas;
    private DefaultListModel lm;
    private Timer timerVaca = new Timer();

    public TravianArbotGui() throws SQLException, DAOException {

        initComponents();
        initMyComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu2 = new javax.swing.JMenu();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        vacaTab = new javax.swing.JPanel();
        Cuenta = new javax.swing.JPanel();
        lowres = new javax.swing.JCheckBox();
        recordarCredenciales = new javax.swing.JCheckBox();
        iniciarSesion = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        raza = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        navegador = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        servidor = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Aldeas = new javax.swing.JTable();
        geralTab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        VacasActivasJlist = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        continuoRadioButton = new javax.swing.JRadioButton();
        vueltaRadioButton = new javax.swing.JRadioButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        Inicio = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        Edit = new javax.swing.JMenu();
        Ataques = new javax.swing.JMenu();
        Vacas = new javax.swing.JMenu();
        ActivarTimerVacas = new javax.swing.JCheckBoxMenuItem();
        editarvacas = new javax.swing.JMenuItem();
        editarArmada = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        Configuracion = new javax.swing.JMenu();
        Temporizadores = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenu2.setText("jMenu2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mi Sonambulo -Beta- v1"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Cuenta.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Cuenta.setPreferredSize(new java.awt.Dimension(300, 300));

        lowres.setText("Baja Resolucion");

        recordarCredenciales.setText("Recordar Credenciales");

        iniciarSesion.setText("Iniciar Sesion");
        iniciarSesion.setMargin(new java.awt.Insets(2, 15, 2, 14));
        iniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarSesionActionPerformed(evt);
            }
        });

        jLabel5.setText("Raza");

        raza.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Romanos", "Germanos", "Galos" }));
        raza.setPreferredSize(new java.awt.Dimension(14, 24));

        jLabel3.setText("Contraseña");

        jLabel6.setText("Navegador");

        navegador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chrome", "Firefox" }));
        navegador.setEnabled(false);
        navegador.setPreferredSize(new java.awt.Dimension(14, 24));

        jLabel4.setText("Usuario");

        jLabel7.setText("Servidor");

        servidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servidorActionPerformed(evt);
            }
        });

        password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CuentaLayout = new javax.swing.GroupLayout(Cuenta);
        Cuenta.setLayout(CuentaLayout);
        CuentaLayout.setHorizontalGroup(
            CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CuentaLayout.createSequentialGroup()
                .addComponent(iniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(CuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CuentaLayout.createSequentialGroup()
                        .addGroup(CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(CuentaLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(servidor)
                                    .addComponent(usuario)
                                    .addComponent(password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addGroup(CuentaLayout.createSequentialGroup()
                        .addComponent(lowres)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recordarCredenciales)
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addGroup(CuentaLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(navegador, 0, 89, Short.MAX_VALUE)
                    .addComponent(raza, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        CuentaLayout.setVerticalGroup(
            CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CuentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(raza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(servidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(navegador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowres)
                    .addComponent(recordarCredenciales))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iniciarSesion)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 300));

        Aldeas.setModel(new javax.swing.table.DefaultTableModel(
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
        Aldeas.setEnabled(false);
        jScrollPane1.setViewportView(Aldeas);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout vacaTabLayout = new javax.swing.GroupLayout(vacaTab);
        vacaTab.setLayout(vacaTabLayout);
        vacaTabLayout.setHorizontalGroup(
            vacaTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vacaTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Cuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );
        vacaTabLayout.setVerticalGroup(
            vacaTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vacaTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vacaTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Cuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("General", vacaTab);

        geralTab.setLayout(null);

        jScrollPane2.setViewportView(VacasActivasJlist);

        geralTab.add(jScrollPane2);
        jScrollPane2.setBounds(10, 10, 260, 200);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(null);

        continuoRadioButton.setText("Enviar hasta usar todas las tropas");
        continuoRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuoRadioButtonActionPerformed(evt);
            }
        });
        jPanel1.add(continuoRadioButton);
        continuoRadioButton.setBounds(10, 45, 200, 23);

        vueltaRadioButton.setSelected(true);
        vueltaRadioButton.setText("Enviar 1 vez por vuelta");
        vueltaRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vueltaRadioButtonActionPerformed(evt);
            }
        });
        jPanel1.add(vueltaRadioButton);
        vueltaRadioButton.setBounds(10, 68, 200, 23);

        jButton2.setText("Actualizar lista");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(130, 10, 105, 25);

        jButton1.setText("Enviar Vacas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(20, 10, 99, 25);

        geralTab.add(jPanel1);
        jPanel1.setBounds(290, 10, 250, 200);

        jTabbedPane1.addTab("Vacas", geralTab);

        Inicio.setText("Inicio");
        Inicio.setEnabled(false);

        jMenuItem1.setText("Abrir Browser");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        Inicio.add(jMenuItem1);
        jMenuItem1.getAccessibleContext().setAccessibleDescription("");

        jMenuBar1.add(Inicio);

        Edit.setText("Edit");
        Edit.setEnabled(false);
        jMenuBar1.add(Edit);

        Ataques.setText("Ataques");
        Ataques.setEnabled(false);
        jMenuBar1.add(Ataques);

        Vacas.setText("Vacas");

        ActivarTimerVacas.setText("Activar/Desactivar lista de vacas");
        ActivarTimerVacas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivarTimerVacasActionPerformed(evt);
            }
        });
        Vacas.add(ActivarTimerVacas);
        ActivarTimerVacas.getAccessibleContext().setAccessibleName("Activar/Desactivar");

        editarvacas.setText("Editar Vacas");
        editarvacas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarvacasActionPerformed(evt);
            }
        });
        Vacas.add(editarvacas);

        editarArmada.setText("Editar Armadas");
        editarArmada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarArmadaActionPerformed(evt);
            }
        });
        Vacas.add(editarArmada);

        jMenuItem2.setText("enviar vaca");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        Vacas.add(jMenuItem2);

        jMenuBar1.add(Vacas);
        Vacas.getAccessibleContext().setAccessibleDescription("");

        Configuracion.setText("Configuracion");

        Temporizadores.setText("Temporizadores");
        Temporizadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TemporizadoresActionPerformed(evt);
            }
        });
        Configuracion.add(Temporizadores);

        jMenuBar1.add(Configuracion);

        jMenu1.setText("Informes");

        jMenuItem3.setText("Leer Informes Ofensivos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Herramientas");

        jMenuItem4.setText("Actualizar Aldeas");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (driver != null) {
            driver.quit();
        }
    }//GEN-LAST:event_formWindowClosing

    private void editarvacasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarvacasActionPerformed

        try {
            ManagerDAO manager = new SQLiteManagerDAO();
            Vacas frm = new Vacas(this, manager);
            frm.setVisible(true);
//            manager.closeConection();

        } catch (DAOException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editarvacasActionPerformed

    private void editarArmadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarArmadaActionPerformed
        try {
            ManagerDAO manager = new SQLiteManagerDAO();
            Armadas frm = new Armadas(this, manager);
            frm.setVisible(true);
//            manager.closeConection();

        } catch (DAOException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_editarArmadaActionPerformed
    public Timer timerVaca() {
        Timer timer = new Timer();
        Config config = new Config();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!vacasActivas.isEmpty()) {
                    updateVacasActivasList();
                    vacasActivas = new ArrayList<>(TravianArbot.enviarVaca(driver, vacasActivas, getContinuo()));
                    updateVacasActivasList();
                } else {
                    System.out.println("test");
                    ActivarTimerVacas.setSelected(false);
                    timer.cancel();

                }

                //System.out.println("Se estan enviadno las vacas");
                //System.out.println(LocalDateTime.now());
            }
        }, 10000, Integer.valueOf(config.GetPropertie("TimerListaVacas")) * 60 * 1000);
        return timer;

    }

    private void TemporizadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TemporizadoresActionPerformed

        TravianArbotConfigTimers frm = new TravianArbotConfigTimers(this);
        frm.setVisible(true);


    }//GEN-LAST:event_TemporizadoresActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        try {
            List<Vaca> unica = new ArrayList<>();
            unica.add(manager.getVacaDAO().obtener(131));
            TravianArbot.enviarVaca(driver, unica, true);
        } catch (DAOException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void ActivarTimerVacasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActivarTimerVacasActionPerformed
        if (ActivarTimerVacas.isSelected()) {
            timerVaca = timerVaca();

        } else {
            //timerVaca.cancel();
            System.out.println("Se cancelo el envio");

        }
    }//GEN-LAST:event_ActivarTimerVacasActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        TravianArbot.getInformesVacas(driver, vacasActivas);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        TravianArbot.getAldeas(driver);
        updateTablaAldeas();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        updateVacasActivasList();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        vacasActivas = new ArrayList<>(TravianArbot.enviarVaca(driver, vacasActivas, getContinuo()));
        updateVacasActivasList();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordActionPerformed

    private void servidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servidorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_servidorActionPerformed

    private void iniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarSesionActionPerformed
        // TODO add your handling code here:
        iniciarSesion.setEnabled(false);
        Cuenta cuenta = new Cuenta(usuario.getText(), password.getText(), servidor.getText(), raza.getSelectedIndex() + 1, String.valueOf(lowres.isSelected()));
        if (recordarCredenciales.isSelected()) {
            Config config = new Config();
            config.editPropertie("Server", servidor.getText());
            config.editPropertie("Usuario", usuario.getText());
            config.editPropertie("Password", password.getText());
            config.editPropertie("Raza", String.valueOf(raza.getSelectedItem()));
            config.editPropertie("Navegador", String.valueOf(navegador.getSelectedItem()));
            config.editPropertie("LowRes", String.valueOf(lowres.isSelected()));
            config.editPropertie("RecordarCredenciales", String.valueOf(recordarCredenciales.isSelected()));
        } else {
            Config config = new Config();
            config.editPropertie("Server", "");
            config.editPropertie("Usuario", "");
            config.editPropertie("Password", "");
            config.editPropertie("Raza", "");
            config.editPropertie("Navegador", "");
            config.editPropertie("LowRes", "");
            config.editPropertie("RecordarCredenciales", "");
        }
        try {
            this.manager = new SQLiteManagerDAO();
            if (this.manager.getCuentaDAO().obtener(cuenta.getServidor()) == null) {
                this.manager.getCuentaDAO().insertar(cuenta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                travianarbot.TravianArbot.InicializarWebBrowser(driver, cuenta);
                TravianArbot.getAldeas(driver);
                updateTablaAldeas();

                return null;

            }

        }.execute();

    }//GEN-LAST:event_iniciarSesionActionPerformed

    private void continuoRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuoRadioButtonActionPerformed
        if (vueltaRadioButton.isSelected()) {
            vueltaRadioButton.setSelected(false);
        }
    }//GEN-LAST:event_continuoRadioButtonActionPerformed

    private void vueltaRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vueltaRadioButtonActionPerformed
        if (continuoRadioButton.isSelected()) {
            continuoRadioButton.setSelected(false);
        }
    }//GEN-LAST:event_vueltaRadioButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem ActivarTimerVacas;
    private javax.swing.JTable Aldeas;
    private javax.swing.JMenu Ataques;
    private javax.swing.JMenu Configuracion;
    private javax.swing.JPanel Cuenta;
    private javax.swing.JMenu Edit;
    private javax.swing.JMenu Inicio;
    private javax.swing.JMenuItem Temporizadores;
    private javax.swing.JMenu Vacas;
    private javax.swing.JList<String> VacasActivasJlist;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton continuoRadioButton;
    private javax.swing.JMenuItem editarArmada;
    private javax.swing.JMenuItem editarvacas;
    private javax.swing.JPanel geralTab;
    private javax.swing.JButton iniciarSesion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBox lowres;
    private javax.swing.JComboBox<String> navegador;
    private javax.swing.JPasswordField password;
    private javax.swing.JComboBox<String> raza;
    private javax.swing.JCheckBox recordarCredenciales;
    private javax.swing.JTextField servidor;
    private javax.swing.JTextField usuario;
    private javax.swing.JPanel vacaTab;
    private javax.swing.JRadioButton vueltaRadioButton;
    // End of variables declaration//GEN-END:variables

    private void initMyComponents() throws SQLException, DAOException {
        Config config = new Config();
        String[] properties = {"Server", "Usuario", "Password", "Raza", "Navegador", "LowRes", "RecordarCredenciales"};
        String[] values = config.GetProperties(properties);
        servidor.setText(values[0]);
        usuario.setText(values[1]);
        password.setText(values[2]);
        raza.setSelectedItem(values[3]);
        navegador.setSelectedItem(values[4]);
        lowres.setSelected(Boolean.valueOf(values[5]));
        recordarCredenciales.setSelected(Boolean.valueOf(values[6]));
        updateTablaAldeas();
        updateVacasActivasList();

    }

    public void updateVacasActivasList() {
        List<Vaca> vacasActivasAux = null;

        if (this.vacasActivas == null) {
            try {
                this.manager = new SQLiteManagerDAO();
                this.vacasActivas = manager.getVacaDAO().obtenerTodosCondicion(1);
            } catch (SQLException ex) {
                Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DAOException ex) {
                Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                this.manager = new SQLiteManagerDAO();
                vacasActivasAux = manager.getVacaDAO().obtenerTodosCondicion(1);
            } catch (SQLException ex) {
                Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DAOException ex) {
                Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (vacasActivasAux.size() >= this.vacasActivas.size()) {

                for (Vaca aux : vacasActivasAux) {
                    boolean existe = false;
                    for (Vaca act : this.vacasActivas) {
                        if (aux.getId() == act.getId()) {
                            existe = true;
                        }
                    }
                    if (!existe) {
                        this.vacasActivas.add(aux);
                    }
                }

            } else {
                List<Vaca> vacaActivasAux2 = new ArrayList<>(this.vacasActivas);
                for (Vaca act : vacaActivasAux2) {
                    boolean existe = false;
                    for (Vaca aux : vacasActivasAux) {
                        if (aux.getId() == act.getId()) {
                            existe = true;
                        }
                    }
                    if (!existe) {
                        this.vacasActivas.remove(act);
                    }
                }

            }

        }

        lm = new DefaultListModel();
        for (Vaca item : this.vacasActivas) {
            lm.addElement(item.getNombre());

        }

        this.VacasActivasJlist.setModel(lm);

    }

    private boolean getContinuo() {

        if (continuoRadioButton.isSelected()) {
            return true;
        } else {
            return false;
        }

    }

    public void updateTablaAldeas() {
        try {
            this.manager = new SQLiteManagerDAO();
            this.model = new AldeasTableModel(manager.getAldeaDAO());
            this.model.updateModel();
            this.Aldeas.setModel(model);
            this.Aldeas.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            this.model.resizeColumnWidth(Aldeas);
            this.Aldeas.getTableHeader().setReorderingAllowed(false);
        } catch (SQLException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(TravianArbotGui.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
