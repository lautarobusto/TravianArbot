package travianarbot.gui;

import ABMs.Vacas;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import travianarbot.Config;
import travianarbot.TravianArbot;
import travianarbot.dao.DAOException;
import travianarbot.dao.ManagerDAO;
import travianarbot.dao.sqlite.SQLiteManagerDAO;
import travianarbot.modelo.Aldea;
import travianarbot.modelo.AldeasTableModel;
import travianarbot.modelo.Armada;
import travianarbot.modelo.Cuenta;
import travianarbot.modelo.Vaca;
import travianarbot.modelo.VacaTableModel;

public class TravianArbotInterfaz extends javax.swing.JFrame {

    private ManagerDAO manager;
    private AldeasTableModel modelAldeas;
    private VacaTableModel modelVacas;
    private WebDriver driver;
    private List<Vaca> vacasActivas;
    private DefaultListModel lm;
    private Timer timerVaca = new Timer();
    private Vaca vaca;

    public TravianArbotInterfaz() {
        initComponents();
        inicializarComponentes();

    }

    private void inicializarComponentes() {
        fillCuentaComponents();
        updateTablaAldeas();
        resetBotonesABM();
        inicializarTablaVacas();
        setBasicoEditable(false);
        setComboAldeas();
        setComboArmadas();
        // updateVacasActivasList();
    }

    private void resetBotonesABM() {

        BotonNuevoBasico.setEnabled(true);
        BotonEditarBasico.setEnabled(false);
        BotonGuardarBasico.setEnabled(false);
        BotonBorrarBasico.setEnabled(false);
        BotonCancelarBasico.setEnabled(false);

        BotonNuevoAvanzado.setEnabled(true);
        BotonEditarAvanzado.setEnabled(false);
        BotonGuardarAvanzado.setEnabled(false);
        BotonBorrarAvanzado.setEnabled(false);
        BotonCancelarAvanzado.setEnabled(false);

    }

    private void fillCuentaComponents() {
        Config config = new Config();
        String[] properties = {"Server", "Usuario", "Password", "Raza", "Navegador", "LowRes", "RecordarCredenciales"};
        String[] values = config.GetProperties(properties);
        TextFieldServidor.setText(values[0]);
        TextFieldNombreUsuario.setText(values[1]);
        Password.setText(values[2]);
        ComboRaza.setSelectedItem(values[3]);
        ComboNavegador.setSelectedItem(values[4]);
        CheckResolusion.setSelected(Boolean.valueOf(values[5]));
        CheckCredenciales.setSelected(Boolean.valueOf(values[6]));

    }

    private void inicializarTablaVacas() {
        this.manager = manager;
        this.modelVacas = new VacaTableModel(manager.getVacaDAO());
        try {
            this.modelVacas.updateModel();
        } catch (DAOException ex) {
            Logger.getLogger(TravianArbotInterfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.TablaVacas.setModel(modelVacas);
        this.TablaVacas.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.modelVacas.resizeColumnWidth(TablaVacas);
        this.TablaVacas.setAutoResizeMode(TablaVacas.AUTO_RESIZE_ALL_COLUMNS);
        this.TablaVacas.getTableHeader().setReorderingAllowed(false);
        this.TablaVacas.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean seleccionValida = (TablaVacas.getSelectedRow() != -1);
                if (Basico.isShowing()) {
                    BotonEditarBasico.setEnabled(seleccionValida);
                    BotonBorrarBasico.setEnabled(seleccionValida);
                    BotonGuardarBasico.setEnabled(!seleccionValida);
                    BotonCancelarBasico.setEnabled(seleccionValida);
                    BotonNuevoBasico.setEnabled(!seleccionValida);
                }
                if (Avanzado.isShowing()) {
                    BotonEditarAvanzado.setEnabled(seleccionValida);
                    BotonBorrarAvanzado.setEnabled(seleccionValida);
                    BotonGuardarAvanzado.setEnabled(!seleccionValida);
                    BotonCancelarAvanzado.setEnabled(seleccionValida);
                    BotonNuevoAvanzado.setEnabled(!seleccionValida);
                }

            }

        });

    }

    public void updateTablaAldeas() {
        try {
            this.manager = new SQLiteManagerDAO();
            this.modelAldeas = new AldeasTableModel(manager.getAldeaDAO());
            this.modelAldeas.updateModel();
            this.TablaAldeas.setModel(modelAldeas);
            this.TablaAldeas.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            this.modelAldeas.resizeColumnWidth(TablaAldeas);
            this.TablaAldeas.getTableHeader().setReorderingAllowed(false);

        } catch (SQLException ex) {
            Logger.getLogger(TravianArbotGuiDep.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (DAOException ex) {
            Logger.getLogger(TravianArbotGuiDep.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setBasicoEditable(boolean editable) {
        TextFieldNombreVacaBasico.setEnabled(editable);
        SpinnerXBasico.setEnabled(editable);
        SpinnerYBasico.setEnabled(editable);
        CheckActivoBasico.setEnabled(editable);
        ComboAldeaOrigenBasico.setEnabled(editable);
        ComboArmadaBasico.setEnabled(editable);
        ComboTipoMovimientoBasico.setEnabled(editable);
        BotonCrearArmadaBasico.setEnabled(editable);

    }

    private void resetBasico() {
        TextFieldNombreVacaBasico.setText("");
        SpinnerXBasico.setValue(0);
        SpinnerYBasico.setValue(0);
        CheckActivoBasico.setSelected(false);
        ComboAldeaOrigenBasico.setSelectedIndex(0);
        ComboArmadaBasico.setSelectedIndex(0);

    }

    public void setComboArmadas() {

        try {
            List<Armada> listaArmadas = manager.getArmadaDAO().obtenerTodos();

            String[] armadasModel = new String[listaArmadas.size()];
            for (int i = 0; i < listaArmadas.size(); i++) {
                armadasModel[i] = listaArmadas.get(i).getNombre();
            }
            ComboArmadaBasico.setModel(new DefaultComboBoxModel(armadasModel));

        } catch (DAOException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setComboAldeas() {

        try {
            List<Aldea> listaAldeas = manager.getAldeaDAO().obtenerTodos();
            for (Aldea item : listaAldeas) {
                ComboAldeaOrigenBasico.addItem(item.getNombre_aldea());
            }
        } catch (DAOException ex) {
            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void getVacaSelected() throws DAOException, SQLException {
        manager = new SQLiteManagerDAO();
        int id = (int) TablaVacas.getValueAt(TablaVacas.getSelectedRow(), 0);
        this.vaca = manager.getVacaDAO().obtener(id);

    }

    private void setBasico() {
//        List<Armada> listaArmadas;
//        String aldea = null;
//        String armada = null;
//        if (vaca != null) {
//            TextFieldNombreVacaBasico.setText(vaca.getNombre());
//            ComboTipoMovimientoBasico.setSelectedItem(vaca.getId_movimiento());
//            for (Aldea item : aldeaList) {
//                if (item.getId_aldea() == vaca.getId_aldea_origen()) {
//                    aldea = item.getNombre_aldea();
//                }
//            }
//            this.aldeaOrigen.setSelectedItem(aldea);
//            this.coordenadax.setValue(vaca.getCoordenada_x());
//            this.coordenaY.setValue(vaca.getCoordenada_y());
//            for (Armada item : listaArmadas) {
//                if (item.getId() == vaca.getId_armada()) {
//                    armada = item.getNombre();
//                }
//            }
//            this.armadas.setSelectedItem(armada);
//            this.activo.setSelected(vaca.isActivo());
//            this.Z.setText(String.valueOf(vaca.getZ()));
//            vacaTable.clearSelection();
//
//        } else {
//            nombreVaca.setText("");
//
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        ModulosTabbed = new javax.swing.JTabbedPane();
        General = new javax.swing.JPanel();
        Cuenta = new javax.swing.JPanel();
        LabelServidor = new javax.swing.JLabel();
        TextFieldServidor = new javax.swing.JTextField();
        LabelNombreServidor = new javax.swing.JLabel();
        TextFieldNombreUsuario = new javax.swing.JTextField();
        LabelPassword = new javax.swing.JLabel();
        Password = new javax.swing.JPasswordField();
        LabelRaza = new javax.swing.JLabel();
        ComboRaza = new javax.swing.JComboBox<>();
        LabelNavegador = new javax.swing.JLabel();
        ComboNavegador = new javax.swing.JComboBox<>();
        LogoRaza = new javax.swing.JLabel();
        CheckResolusion = new javax.swing.JCheckBox();
        CheckCredenciales = new javax.swing.JCheckBox();
        BotonIniciarSesion = new javax.swing.JButton();
        LogoNavegador = new javax.swing.JLabel();
        Aldeas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaAldeas = new javax.swing.JTable();
        Actividad = new javax.swing.JPanel();
        Vacas = new javax.swing.JPanel();
        VacasTabbed = new javax.swing.JTabbedPane();
        Informacion = new javax.swing.JPanel();
        Edicion = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        EdicionTabbed = new javax.swing.JTabbedPane();
        Basico = new javax.swing.JPanel();
        PanelBotonesBasico = new javax.swing.JPanel();
        BotonNuevoBasico = new javax.swing.JButton();
        BotonEditarBasico = new javax.swing.JButton();
        BotonGuardarBasico = new javax.swing.JButton();
        BotonBorrarBasico = new javax.swing.JButton();
        BotonCancelarBasico = new javax.swing.JButton();
        PanelControlesBasico = new javax.swing.JPanel();
        LabelNombreVaca = new javax.swing.JLabel();
        TextFieldNombreVacaBasico = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        SpinnerXBasico = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        SpinnerYBasico = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        ComboAldeaOrigenBasico = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        ComboTipoMovimientoBasico = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        ComboArmadaBasico = new javax.swing.JComboBox<>();
        BotonCrearArmadaBasico = new javax.swing.JButton();
        CheckActivoBasico = new javax.swing.JCheckBox();
        Avanzado = new javax.swing.JPanel();
        PanelBotonesAvanzado = new javax.swing.JPanel();
        BotonNuevoAvanzado = new javax.swing.JButton();
        BotonEditarAvanzado = new javax.swing.JButton();
        BotonGuardarAvanzado = new javax.swing.JButton();
        BotonBorrarAvanzado = new javax.swing.JButton();
        BotonCancelarAvanzado = new javax.swing.JButton();
        PanelControlesAvanzado = new javax.swing.JPanel();
        LabelNombreVaca1 = new javax.swing.JLabel();
        TextFieldNombreVaca1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        SpinnerX1 = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        SpinnerY1 = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        ComboAldeaOrigen1 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        ComboTipoMovimiento1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        ComboArmada1 = new javax.swing.JComboBox<>();
        BotonCrearArmada1 = new javax.swing.JButton();
        CheckActivo1 = new javax.swing.JCheckBox();
        Automatico = new javax.swing.JPanel();
        ScrollTablaVacas = new javax.swing.JScrollPane();
        TablaVacas = new javax.swing.JTable();
        Armadas = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mi Sonambulo Beta V2");
        setIconImage(Toolkit.getDefaultToolkit().getImage(TravianArbotInterfaz.class.getResource("/resources/Misc/logo.png")));
        setMaximumSize(new java.awt.Dimension(1920, 1080));
        setMinimumSize(new java.awt.Dimension(600, 400));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        General.setMaximumSize(new java.awt.Dimension(700, 32767));
        General.setPreferredSize(new java.awt.Dimension(600, 451));
        General.setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        Cuenta.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuenta"));
        Cuenta.setPreferredSize(new java.awt.Dimension(200, 167));
        Cuenta.setLayout(null);

        LabelServidor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LabelServidor.setText("Servidor");
        LabelServidor.setAlignmentY(0.0F);
        LabelServidor.setFocusable(false);
        LabelServidor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LabelServidor.setPreferredSize(new java.awt.Dimension(110, 25));
        LabelServidor.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        Cuenta.add(LabelServidor);
        LabelServidor.setBounds(17, 29, 110, 25);

        TextFieldServidor.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        TextFieldServidor.setNextFocusableComponent(SpinnerXBasico);
        TextFieldServidor.setPreferredSize(new java.awt.Dimension(110, 25));
        Cuenta.add(TextFieldServidor);
        TextFieldServidor.setBounds(140, 30, 150, 25);

        LabelNombreServidor.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LabelNombreServidor.setText("Nombre de Usuario");
        LabelNombreServidor.setAlignmentY(0.0F);
        LabelNombreServidor.setFocusable(false);
        LabelNombreServidor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LabelNombreServidor.setPreferredSize(new java.awt.Dimension(110, 25));
        LabelNombreServidor.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        Cuenta.add(LabelNombreServidor);
        LabelNombreServidor.setBounds(17, 60, 110, 25);

        TextFieldNombreUsuario.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        TextFieldNombreUsuario.setNextFocusableComponent(SpinnerXBasico);
        TextFieldNombreUsuario.setPreferredSize(new java.awt.Dimension(110, 25));
        Cuenta.add(TextFieldNombreUsuario);
        TextFieldNombreUsuario.setBounds(139, 60, 150, 25);

        LabelPassword.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LabelPassword.setText("Contraseña");
        LabelPassword.setAlignmentY(0.0F);
        LabelPassword.setFocusable(false);
        LabelPassword.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LabelPassword.setPreferredSize(new java.awt.Dimension(110, 25));
        LabelPassword.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        Cuenta.add(LabelPassword);
        LabelPassword.setBounds(17, 91, 110, 25);

        Password.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        Password.setToolTipText("");
        Password.setMinimumSize(new java.awt.Dimension(110, 25));
        Password.setPreferredSize(new java.awt.Dimension(110, 25));
        Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordActionPerformed(evt);
            }
        });
        Cuenta.add(Password);
        Password.setBounds(140, 90, 150, 25);

        LabelRaza.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LabelRaza.setText("Raza");
        LabelRaza.setAlignmentY(0.0F);
        LabelRaza.setFocusable(false);
        LabelRaza.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LabelRaza.setPreferredSize(new java.awt.Dimension(110, 25));
        LabelRaza.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        Cuenta.add(LabelRaza);
        LabelRaza.setBounds(300, 30, 110, 25);

        ComboRaza.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Romanos", "Germanos", "Galos" }));
        ComboRaza.setMinimumSize(new java.awt.Dimension(100, 25));
        ComboRaza.setNextFocusableComponent(ComboTipoMovimientoBasico);
        ComboRaza.setPreferredSize(new java.awt.Dimension(110, 25));
        ComboRaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboRazaActionPerformed(evt);
            }
        });
        Cuenta.add(ComboRaza);
        ComboRaza.setBounds(420, 30, 150, 25);

        LabelNavegador.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LabelNavegador.setText("Navagador");
        LabelNavegador.setAlignmentY(0.0F);
        LabelNavegador.setFocusable(false);
        LabelNavegador.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LabelNavegador.setPreferredSize(new java.awt.Dimension(110, 25));
        LabelNavegador.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        Cuenta.add(LabelNavegador);
        LabelNavegador.setBounds(300, 60, 110, 25);

        ComboNavegador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mozilla Firefox", "Google Chrome" }));
        ComboNavegador.setMinimumSize(new java.awt.Dimension(100, 25));
        ComboNavegador.setNextFocusableComponent(ComboTipoMovimientoBasico);
        ComboNavegador.setPreferredSize(new java.awt.Dimension(110, 25));
        ComboNavegador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboNavegadorActionPerformed(evt);
            }
        });
        Cuenta.add(ComboNavegador);
        ComboNavegador.setBounds(420, 60, 150, 25);

        LogoRaza.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LogoRaza.setAlignmentY(0.0F);
        LogoRaza.setFocusable(false);
        LogoRaza.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LogoRaza.setMinimumSize(new java.awt.Dimension(27, 27));
        LogoRaza.setPreferredSize(new java.awt.Dimension(25, 25));
        LogoRaza.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        Cuenta.add(LogoRaza);
        LogoRaza.setBounds(580, 30, 27, 27);

        CheckResolusion.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        CheckResolusion.setText("Baja Resolusion");
        CheckResolusion.setMinimumSize(new java.awt.Dimension(110, 25));
        CheckResolusion.setPreferredSize(new java.awt.Dimension(110, 25));
        Cuenta.add(CheckResolusion);
        CheckResolusion.setBounds(300, 90, 120, 23);

        CheckCredenciales.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        CheckCredenciales.setText("Recordar Credenciales");
        CheckCredenciales.setMinimumSize(new java.awt.Dimension(110, 25));
        CheckCredenciales.setPreferredSize(new java.awt.Dimension(110, 25));
        Cuenta.add(CheckCredenciales);
        CheckCredenciales.setBounds(420, 90, 160, 23);

        BotonIniciarSesion.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        BotonIniciarSesion.setText("Iniciar Sesion");
        BotonIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonIniciarSesionActionPerformed(evt);
            }
        });
        Cuenta.add(BotonIniciarSesion);
        BotonIniciarSesion.setBounds(140, 120, 150, 25);

        LogoNavegador.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LogoNavegador.setAlignmentY(0.0F);
        LogoNavegador.setFocusable(false);
        LogoNavegador.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LogoNavegador.setMinimumSize(new java.awt.Dimension(27, 27));
        LogoNavegador.setPreferredSize(new java.awt.Dimension(25, 25));
        LogoNavegador.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        LogoNavegador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoNavegadorMouseClicked(evt);
            }
        });
        Cuenta.add(LogoNavegador);
        LogoNavegador.setBounds(580, 60, 27, 27);

        General.add(Cuenta);

        Aldeas.setBorder(javax.swing.BorderFactory.createTitledBorder("Aldeas"));
        Aldeas.setPreferredSize(new java.awt.Dimension(200, 444));
        Aldeas.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setHorizontalScrollBar(null);

        TablaAldeas.setPreferredSize(null);
        jScrollPane1.setViewportView(TablaAldeas);

        Aldeas.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        General.add(Aldeas);

        Actividad.setBorder(javax.swing.BorderFactory.createTitledBorder("Actividad"));
        Actividad.setPreferredSize(new java.awt.Dimension(200, 180));

        javax.swing.GroupLayout ActividadLayout = new javax.swing.GroupLayout(Actividad);
        Actividad.setLayout(ActividadLayout);
        ActividadLayout.setHorizontalGroup(
            ActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 902, Short.MAX_VALUE)
        );
        ActividadLayout.setVerticalGroup(
            ActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );

        General.add(Actividad);

        ModulosTabbed.addTab("General", General);

        Vacas.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout InformacionLayout = new javax.swing.GroupLayout(Informacion);
        Informacion.setLayout(InformacionLayout);
        InformacionLayout.setHorizontalGroup(
            InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 907, Short.MAX_VALUE)
        );
        InformacionLayout.setVerticalGroup(
            InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 463, Short.MAX_VALUE)
        );

        VacasTabbed.addTab("Informacion", Informacion);

        Edicion.setMaximumSize(new java.awt.Dimension(1920, 1080));
        Edicion.setPreferredSize(new java.awt.Dimension(0, 0));
        Edicion.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(190);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        Basico.setLayout(new java.awt.BorderLayout());

        PanelBotonesBasico.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PanelBotonesBasico.setName(""); // NOI18N
        PanelBotonesBasico.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

        BotonNuevoBasico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/add-3.png"))); // NOI18N
        BotonNuevoBasico.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonNuevoBasico.setPreferredSize(new java.awt.Dimension(45, 45));
        BotonNuevoBasico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonNuevoBasicoActionPerformed(evt);
            }
        });
        PanelBotonesBasico.add(BotonNuevoBasico);

        BotonEditarBasico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/edit.png"))); // NOI18N
        BotonEditarBasico.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonEditarBasico.setPreferredSize(new java.awt.Dimension(45, 45));
        BotonEditarBasico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEditarBasicoActionPerformed(evt);
            }
        });
        PanelBotonesBasico.add(BotonEditarBasico);

        BotonGuardarBasico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/save.png"))); // NOI18N
        BotonGuardarBasico.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonGuardarBasico.setPreferredSize(new java.awt.Dimension(45, 45));
        PanelBotonesBasico.add(BotonGuardarBasico);

        BotonBorrarBasico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/garbage-2.png"))); // NOI18N
        BotonBorrarBasico.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonBorrarBasico.setPreferredSize(new java.awt.Dimension(45, 45));
        PanelBotonesBasico.add(BotonBorrarBasico);

        BotonCancelarBasico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/error.png"))); // NOI18N
        BotonCancelarBasico.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonCancelarBasico.setPreferredSize(new java.awt.Dimension(45, 45));
        PanelBotonesBasico.add(BotonCancelarBasico);

        Basico.add(PanelBotonesBasico, java.awt.BorderLayout.PAGE_START);

        PanelControlesBasico.setToolTipText("");
        PanelControlesBasico.setMinimumSize(new java.awt.Dimension(0, 100));
        java.awt.GridBagLayout PanelControlesLayout1 = new java.awt.GridBagLayout();
        PanelControlesLayout1.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0};
        PanelControlesLayout1.rowHeights = new int[] {0, 10, 0, 10, 0};
        PanelControlesBasico.setLayout(PanelControlesLayout1);

        LabelNombreVaca.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LabelNombreVaca.setText("Nombre de Vaca");
        LabelNombreVaca.setAlignmentY(0.0F);
        LabelNombreVaca.setFocusable(false);
        LabelNombreVaca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LabelNombreVaca.setPreferredSize(new java.awt.Dimension(110, 25));
        LabelNombreVaca.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        PanelControlesBasico.add(LabelNombreVaca, gridBagConstraints);

        TextFieldNombreVacaBasico.setNextFocusableComponent(SpinnerXBasico);
        TextFieldNombreVacaBasico.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        PanelControlesBasico.add(TextFieldNombreVacaBasico, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setText("Coordenada X");
        jLabel2.setAlignmentY(0.0F);
        jLabel2.setFocusable(false);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        PanelControlesBasico.add(jLabel2, gridBagConstraints);

        SpinnerXBasico.setModel(new javax.swing.SpinnerNumberModel(0, -400, 400, 1));
        SpinnerXBasico.setAutoscrolls(true);
        SpinnerXBasico.setNextFocusableComponent(SpinnerYBasico);
        SpinnerXBasico.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        PanelControlesBasico.add(SpinnerXBasico, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setText("Coordenada Y");
        jLabel3.setAlignmentY(0.0F);
        jLabel3.setFocusable(false);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        PanelControlesBasico.add(jLabel3, gridBagConstraints);

        SpinnerYBasico.setModel(new javax.swing.SpinnerNumberModel(0, -400, 400, 1));
        SpinnerYBasico.setAutoscrolls(true);
        SpinnerYBasico.setNextFocusableComponent(ComboAldeaOrigenBasico);
        SpinnerYBasico.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        PanelControlesBasico.add(SpinnerYBasico, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setText("Aldea de Origen");
        jLabel4.setAlignmentY(0.0F);
        jLabel4.setFocusable(false);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel4.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel4.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        PanelControlesBasico.add(jLabel4, gridBagConstraints);

        ComboAldeaOrigenBasico.setMinimumSize(new java.awt.Dimension(100, 25));
        ComboAldeaOrigenBasico.setNextFocusableComponent(ComboTipoMovimientoBasico);
        ComboAldeaOrigenBasico.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        PanelControlesBasico.add(ComboAldeaOrigenBasico, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setText("Tipo de Movimiento");
        jLabel5.setAlignmentY(0.0F);
        jLabel5.setFocusable(false);
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel5.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        PanelControlesBasico.add(jLabel5, gridBagConstraints);

        ComboTipoMovimientoBasico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Refuerzo", "Ataque", "Asalto" }));
        ComboTipoMovimientoBasico.setSelectedIndex(2);
        ComboTipoMovimientoBasico.setMinimumSize(new java.awt.Dimension(100, 25));
        ComboTipoMovimientoBasico.setNextFocusableComponent(ComboArmadaBasico);
        ComboTipoMovimientoBasico.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        PanelControlesBasico.add(ComboTipoMovimientoBasico, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setText("Armada");
        jLabel6.setAlignmentY(0.0F);
        jLabel6.setFocusable(false);
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel6.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        PanelControlesBasico.add(jLabel6, gridBagConstraints);

        ComboArmadaBasico.setMinimumSize(new java.awt.Dimension(100, 25));
        ComboArmadaBasico.setNextFocusableComponent(CheckActivoBasico);
        ComboArmadaBasico.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        PanelControlesBasico.add(ComboArmadaBasico, gridBagConstraints);

        BotonCrearArmadaBasico.setText("Crear Armada");
        BotonCrearArmadaBasico.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        PanelControlesBasico.add(BotonCrearArmadaBasico, gridBagConstraints);

        CheckActivoBasico.setText("Activa");
        CheckActivoBasico.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        PanelControlesBasico.add(CheckActivoBasico, gridBagConstraints);

        Basico.add(PanelControlesBasico, java.awt.BorderLayout.CENTER);

        EdicionTabbed.addTab("Basico", Basico);

        Avanzado.setLayout(new java.awt.BorderLayout());

        PanelBotonesAvanzado.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PanelBotonesAvanzado.setName(""); // NOI18N
        PanelBotonesAvanzado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));

        BotonNuevoAvanzado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/add-3.png"))); // NOI18N
        BotonNuevoAvanzado.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonNuevoAvanzado.setPreferredSize(new java.awt.Dimension(45, 45));
        PanelBotonesAvanzado.add(BotonNuevoAvanzado);

        BotonEditarAvanzado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/edit.png"))); // NOI18N
        BotonEditarAvanzado.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonEditarAvanzado.setPreferredSize(new java.awt.Dimension(45, 45));
        PanelBotonesAvanzado.add(BotonEditarAvanzado);

        BotonGuardarAvanzado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/save.png"))); // NOI18N
        BotonGuardarAvanzado.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonGuardarAvanzado.setPreferredSize(new java.awt.Dimension(45, 45));
        PanelBotonesAvanzado.add(BotonGuardarAvanzado);

        BotonBorrarAvanzado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/garbage-2.png"))); // NOI18N
        BotonBorrarAvanzado.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonBorrarAvanzado.setPreferredSize(new java.awt.Dimension(45, 45));
        PanelBotonesAvanzado.add(BotonBorrarAvanzado);

        BotonCancelarAvanzado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Buttons/error.png"))); // NOI18N
        BotonCancelarAvanzado.setMinimumSize(new java.awt.Dimension(45, 45));
        BotonCancelarAvanzado.setPreferredSize(new java.awt.Dimension(45, 45));
        PanelBotonesAvanzado.add(BotonCancelarAvanzado);

        Avanzado.add(PanelBotonesAvanzado, java.awt.BorderLayout.PAGE_START);

        PanelControlesAvanzado.setToolTipText("");
        PanelControlesAvanzado.setMinimumSize(new java.awt.Dimension(0, 100));
        PanelControlesAvanzado.setLayout(new java.awt.GridBagLayout());

        LabelNombreVaca1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        LabelNombreVaca1.setText("Nombre de Vaca");
        LabelNombreVaca1.setAlignmentY(0.0F);
        LabelNombreVaca1.setFocusable(false);
        LabelNombreVaca1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LabelNombreVaca1.setPreferredSize(new java.awt.Dimension(110, 25));
        LabelNombreVaca1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        PanelControlesAvanzado.add(LabelNombreVaca1, gridBagConstraints);

        TextFieldNombreVaca1.setNextFocusableComponent(SpinnerXBasico);
        TextFieldNombreVaca1.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        PanelControlesAvanzado.add(TextFieldNombreVaca1, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setText("Coordenada X");
        jLabel7.setAlignmentY(0.0F);
        jLabel7.setFocusable(false);
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel7.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel7.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        PanelControlesAvanzado.add(jLabel7, gridBagConstraints);

        SpinnerX1.setModel(new javax.swing.SpinnerNumberModel(0, -400, 400, 1));
        SpinnerX1.setAutoscrolls(true);
        SpinnerX1.setNextFocusableComponent(SpinnerYBasico);
        SpinnerX1.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        PanelControlesAvanzado.add(SpinnerX1, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setText("Coordenada Y");
        jLabel8.setAlignmentY(0.0F);
        jLabel8.setFocusable(false);
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel8.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel8.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        PanelControlesAvanzado.add(jLabel8, gridBagConstraints);

        SpinnerY1.setModel(new javax.swing.SpinnerNumberModel(0, -400, 400, 1));
        SpinnerY1.setAutoscrolls(true);
        SpinnerY1.setNextFocusableComponent(ComboAldeaOrigenBasico);
        SpinnerY1.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        PanelControlesAvanzado.add(SpinnerY1, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setText("Aldea de Origen");
        jLabel9.setAlignmentY(0.0F);
        jLabel9.setFocusable(false);
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel9.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel9.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        PanelControlesAvanzado.add(jLabel9, gridBagConstraints);

        ComboAldeaOrigen1.setMinimumSize(new java.awt.Dimension(100, 25));
        ComboAldeaOrigen1.setNextFocusableComponent(ComboTipoMovimientoBasico);
        ComboAldeaOrigen1.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        PanelControlesAvanzado.add(ComboAldeaOrigen1, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel10.setText("Tipo de Movimiento");
        jLabel10.setAlignmentY(0.0F);
        jLabel10.setFocusable(false);
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel10.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel10.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        PanelControlesAvanzado.add(jLabel10, gridBagConstraints);

        ComboTipoMovimiento1.setMinimumSize(new java.awt.Dimension(100, 25));
        ComboTipoMovimiento1.setNextFocusableComponent(ComboArmadaBasico);
        ComboTipoMovimiento1.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        PanelControlesAvanzado.add(ComboTipoMovimiento1, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel11.setText("Armada");
        jLabel11.setAlignmentY(0.0F);
        jLabel11.setFocusable(false);
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel11.setPreferredSize(new java.awt.Dimension(110, 25));
        jLabel11.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        PanelControlesAvanzado.add(jLabel11, gridBagConstraints);

        ComboArmada1.setMinimumSize(new java.awt.Dimension(100, 25));
        ComboArmada1.setNextFocusableComponent(CheckActivoBasico);
        ComboArmada1.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        PanelControlesAvanzado.add(ComboArmada1, gridBagConstraints);

        BotonCrearArmada1.setText("Crear Armada");
        BotonCrearArmada1.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        PanelControlesAvanzado.add(BotonCrearArmada1, gridBagConstraints);

        CheckActivo1.setText("Activa");
        CheckActivo1.setPreferredSize(new java.awt.Dimension(110, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        PanelControlesAvanzado.add(CheckActivo1, gridBagConstraints);

        Avanzado.add(PanelControlesAvanzado, java.awt.BorderLayout.CENTER);

        EdicionTabbed.addTab("Avanzado", Avanzado);

        javax.swing.GroupLayout AutomaticoLayout = new javax.swing.GroupLayout(Automatico);
        Automatico.setLayout(AutomaticoLayout);
        AutomaticoLayout.setHorizontalGroup(
            AutomaticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        AutomaticoLayout.setVerticalGroup(
            AutomaticoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 162, Short.MAX_VALUE)
        );

        EdicionTabbed.addTab("Automatico", Automatico);

        jSplitPane1.setLeftComponent(EdicionTabbed);

        TablaVacas.setModel(new javax.swing.table.DefaultTableModel(
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
        ScrollTablaVacas.setViewportView(TablaVacas);

        jSplitPane1.setRightComponent(ScrollTablaVacas);

        Edicion.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        VacasTabbed.addTab("Edicion", Edicion);

        Vacas.add(VacasTabbed, java.awt.BorderLayout.CENTER);

        ModulosTabbed.addTab("Vacas", Vacas);

        javax.swing.GroupLayout ArmadasLayout = new javax.swing.GroupLayout(Armadas);
        Armadas.setLayout(ArmadasLayout);
        ArmadasLayout.setHorizontalGroup(
            ArmadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 912, Short.MAX_VALUE)
        );
        ArmadasLayout.setVerticalGroup(
            ArmadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        ModulosTabbed.addTab("Armadas", Armadas);

        getContentPane().add(ModulosTabbed, java.awt.BorderLayout.CENTER);

        MenuBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jMenu1.setText("File");
        MenuBar.add(jMenu1);

        jMenu2.setText("Edit");
        MenuBar.add(jMenu2);

        setJMenuBar(MenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordActionPerformed

    private void BotonIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonIniciarSesionActionPerformed
        BotonIniciarSesion.setEnabled(false);

        Cuenta cuenta = new Cuenta(TextFieldNombreUsuario.getText(), Password.getText(), TextFieldServidor.getText(), ComboRaza.getSelectedIndex() + 1, String.valueOf(CheckResolusion.isSelected()));
        if (CheckCredenciales.isSelected()) {
            Config config = new Config();
            config.editPropertie("Server", TextFieldServidor.getText());
            config.editPropertie("Usuario", TextFieldNombreUsuario.getText());
            config.editPropertie("Password", Password.getText());
            config.editPropertie("Raza", String.valueOf(ComboRaza.getSelectedItem()));
            config.editPropertie("Navegador", String.valueOf(ComboNavegador.getSelectedItem()));
            config.editPropertie("LowRes", String.valueOf(CheckResolusion.isSelected()));
            config.editPropertie("RecordarCredenciales", String.valueOf(CheckCredenciales.isSelected()));
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
            Logger.getLogger(TravianArbotGuiDep.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(TravianArbotGuiDep.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ComboNavegador.getSelectedIndex() == 0) {

            driver = new FirefoxDriver();
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {

                    travianarbot.TravianArbot.InicializarWebBrowser(driver, cuenta);
                    TravianArbot.getAldeas(driver);
                    updateTablaAldeas();
                    return null;

                }

            }.execute();

        }
        if (ComboNavegador.getSelectedIndex() == 1) {
            //System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
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

        }


    }//GEN-LAST:event_BotonIniciarSesionActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

        if (driver != null) {
            driver.quit();
        }
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (driver != null) {
            driver.quit();
        }
    }//GEN-LAST:event_formWindowClosing
       private void ComboNavegadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboNavegadorActionPerformed
           switch (ComboNavegador.getSelectedIndex()) {
               case 0:
                   LogoNavegador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/BrowserIcons/mozilla.png")));
                   break;
               case 1:

                   LogoNavegador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/BrowserIcons/chrome.png")));
                   break;

           }
    }//GEN-LAST:event_ComboNavegadorActionPerformed

    private void LogoNavegadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoNavegadorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_LogoNavegadorMouseClicked

    private void ComboRazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboRazaActionPerformed
        switch (ComboRaza.getSelectedIndex()) {

            case 0:
                LogoRaza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Misc/romanos.png")));
                break;
            case 1:
                LogoRaza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Misc/germanos.png")));
                break;
            case 2:
                LogoRaza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Misc/galos.png")));
                break;

        }
    }//GEN-LAST:event_ComboRazaActionPerformed

    private void BotonNuevoBasicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonNuevoBasicoActionPerformed
        vaca = null;
        setBasicoEditable(true);
        BotonGuardarBasico.setEnabled(true);
        BotonCancelarBasico.setEnabled(true);
        BotonEditarBasico.setEnabled(false);
        BotonNuevoBasico.setEnabled(false);
        TablaVacas.setEnabled(false);
        TextFieldNombreVacaBasico.requestFocus();
        resetBasico();
    }//GEN-LAST:event_BotonNuevoBasicoActionPerformed

    private void BotonEditarBasicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEditarBasicoActionPerformed
//        try {
//            getVacaSelected();
//            SetBasico();
//            guardar.setEnabled(true);
//            cancelar.setEnabled(true);
//            setIsEditable(true);
//            vacaTable.setEnabled(false);
//            nuevo.setEnabled(false);
//        } catch (DAOException ex) {
//            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(Vacas.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_BotonEditarBasicoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Actividad;
    private javax.swing.JPanel Aldeas;
    private javax.swing.JPanel Armadas;
    private javax.swing.JPanel Automatico;
    private javax.swing.JPanel Avanzado;
    private javax.swing.JPanel Basico;
    private javax.swing.JButton BotonBorrarAvanzado;
    private javax.swing.JButton BotonBorrarBasico;
    private javax.swing.JButton BotonCancelarAvanzado;
    private javax.swing.JButton BotonCancelarBasico;
    private javax.swing.JButton BotonCrearArmada1;
    private javax.swing.JButton BotonCrearArmadaBasico;
    private javax.swing.JButton BotonEditarAvanzado;
    private javax.swing.JButton BotonEditarBasico;
    private javax.swing.JButton BotonGuardarAvanzado;
    private javax.swing.JButton BotonGuardarBasico;
    private javax.swing.JButton BotonIniciarSesion;
    private javax.swing.JButton BotonNuevoAvanzado;
    private javax.swing.JButton BotonNuevoBasico;
    private javax.swing.JCheckBox CheckActivo1;
    private javax.swing.JCheckBox CheckActivoBasico;
    private javax.swing.JCheckBox CheckCredenciales;
    private javax.swing.JCheckBox CheckResolusion;
    private javax.swing.JComboBox<String> ComboAldeaOrigen1;
    private javax.swing.JComboBox<String> ComboAldeaOrigenBasico;
    private javax.swing.JComboBox<String> ComboArmada1;
    private javax.swing.JComboBox<String> ComboArmadaBasico;
    private javax.swing.JComboBox<String> ComboNavegador;
    private javax.swing.JComboBox<String> ComboRaza;
    private javax.swing.JComboBox<String> ComboTipoMovimiento1;
    private javax.swing.JComboBox<String> ComboTipoMovimientoBasico;
    private javax.swing.JPanel Cuenta;
    private javax.swing.JPanel Edicion;
    private javax.swing.JTabbedPane EdicionTabbed;
    private javax.swing.JPanel General;
    private javax.swing.JPanel Informacion;
    private javax.swing.JLabel LabelNavegador;
    private javax.swing.JLabel LabelNombreServidor;
    private javax.swing.JLabel LabelNombreVaca;
    private javax.swing.JLabel LabelNombreVaca1;
    private javax.swing.JLabel LabelPassword;
    private javax.swing.JLabel LabelRaza;
    private javax.swing.JLabel LabelServidor;
    private javax.swing.JLabel LogoNavegador;
    private javax.swing.JLabel LogoRaza;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JTabbedPane ModulosTabbed;
    private javax.swing.JPanel PanelBotonesAvanzado;
    private javax.swing.JPanel PanelBotonesBasico;
    private javax.swing.JPanel PanelControlesAvanzado;
    private javax.swing.JPanel PanelControlesBasico;
    private javax.swing.JPasswordField Password;
    private javax.swing.JScrollPane ScrollTablaVacas;
    private javax.swing.JSpinner SpinnerX1;
    private javax.swing.JSpinner SpinnerXBasico;
    private javax.swing.JSpinner SpinnerY1;
    private javax.swing.JSpinner SpinnerYBasico;
    private javax.swing.JTable TablaAldeas;
    private javax.swing.JTable TablaVacas;
    private javax.swing.JTextField TextFieldNombreUsuario;
    private javax.swing.JTextField TextFieldNombreVaca1;
    private javax.swing.JTextField TextFieldNombreVacaBasico;
    private javax.swing.JTextField TextFieldServidor;
    private javax.swing.JPanel Vacas;
    private javax.swing.JTabbedPane VacasTabbed;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
