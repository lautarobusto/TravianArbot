package travianarbot.dao.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import travianarbot.dao.AldeaDAO;
import travianarbot.dao.ArmadaDAO;
import travianarbot.dao.CuentaDAO;
import travianarbot.dao.InformeOfensivoDAO;
import travianarbot.dao.ManagerDAO;
import travianarbot.dao.TropaDAO;
import travianarbot.dao.VacaDAO;

import org.sqlite.SQLiteConfig;

public class SQLiteManagerDAO implements ManagerDAO {

    private Connection conn;
    private AldeaDAO aldea = null;
    private CuentaDAO cuenta = null;
    private ArmadaDAO armada = null;
    private VacaDAO vaca = null;
    private TropaDAO tropa = null;
    private InformeOfensivoDAO informe = null;
    private static final String DB_URL = "jdbc:sqlite:TravianArbotDB.sqlite3";
    private static final String DRIVER = "org.sqlite.JDBC";

    public SQLiteManagerDAO(String dbname) throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
    }

//    public SQLiteManagerDAO() throws SQLException {
//        this.conn = DriverManager.getConnection("jdbc:sqlite:TravianArbotDB.sqlite3");
//
//    }
    public SQLiteManagerDAO() throws ClassNotFoundException {
        Class.forName(DRIVER);
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(DB_URL, config.toProperties());
        } catch (SQLException ex) {
        }

    }

    @Override
    public AldeaDAO getAldeaDAO() {
        if (aldea == null) {
            aldea = new SQLiteAldeaDAO(conn);
        }
        return aldea;
    }

    @Override
    public CuentaDAO getCuentaDAO() {
        if (cuenta == null) {
            cuenta = new SQLiteCuentaDAO(conn);
        }
        return cuenta;
    }

    @Override
    public ArmadaDAO getArmadaDAO() {
        if (armada == null) {
            armada = new SQLiteArmadaDAO(conn);
        }
        return armada;
    }

    @Override
    public void closeConection() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public VacaDAO getVacaDAO() {
        if (vaca == null) {
            vaca = new SQLiteVacaDAO(conn);
        }
        return vaca;
    }

    @Override
    public TropaDAO getTropaDAO() {

        if (tropa == null) {
            tropa = new SQLiteTropaDAO(conn);
        }
        return tropa;
    }

    @Override
    public InformeOfensivoDAO getInformeOfensivoDAO() {
        if (informe == null) {
            informe = new SQLiteInformeOfensivoDAO(conn);
        }
        return informe;
    }

}
