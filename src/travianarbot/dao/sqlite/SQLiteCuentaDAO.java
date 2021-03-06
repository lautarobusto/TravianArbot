package travianarbot.dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import travianarbot.dao.CuentaDAO;
import travianarbot.dao.DAOException;
import travianarbot.modelo.Cuenta;

public class SQLiteCuentaDAO implements CuentaDAO {

    final String INSERT = "INSERT or ignore INTO Cuentas (Nombre, Contraseña, Servidor, ID_Raza, LowRes) VALUES(?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Cuentas SET Nombre = ?, Contraseña = ?, Servidor = ?, ID_Raza = ?, LowRes= ? WHERE ID = ?";
    final String DELETE = "DELETE FROM Cuentas WHERE ID = ?";
    final String GETALL = "SELECT * FROM Cuentas";
    final String GETONE = "SELECT * FROM Cuentas WHERE Servidor = ?";
    private Connection conn;

    public SQLiteCuentaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Cuenta x) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        System.out.println(x.toString());
        try {
            pst = conn.prepareStatement(INSERT);
            pst.setString(1, x.getNombre());
            pst.setString(2, x.getContraseña());
            pst.setString(3, x.getServidor());
            pst.setInt(4, x.getId_raza());
            pst.setString(5, x.getLowRes());

            if (pst.executeUpdate() == 0) {
                throw new DAOException("Error en  en executeUpdate");
            }
            rst = pst.getGeneratedKeys();
            if (rst.next()) {
                x.setId(rst.getInt(1));
            } else {
                throw new DAOException("Error en  en executeUpdate");
            }

        } catch (SQLException ex) {
            throw new DAOException("Error en  SQL", ex);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
    }

    public void insertarPrfix() throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try {
            pst = conn.prepareStatement(INSERT);
            pst.setString(1, "xanatos");
            pst.setString(2, "36191727");
            pst.setString(3, "https://ts5.travian.cl");
            pst.setInt(4, 1);
            pst.setString(5, "true");

            if (pst.executeUpdate() == 0) {
                throw new DAOException("Error en  en executeUpdate");
            }

        } catch (SQLException ex) {
            throw new DAOException("Error en  SQL", ex);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
    }

    @Override
    public void modificar(Cuenta x) throws DAOException {
        PreparedStatement pst = null;

        try {
            pst = conn.prepareStatement(UPDATE);
            pst.setString(1, x.getNombre());
            pst.setString(2, x.getContraseña());
            pst.setString(3, x.getServidor());
            pst.setInt(4, x.getId_raza());
            pst.setString(5, x.getLowRes());
            pst.setInt(6, x.getId());
            if (pst.executeUpdate() == 0) {
                throw new DAOException("No se Actualizo el elemento");

            }
        } catch (SQLException e) {
            throw new DAOException("Erro SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst);
        }
    }

    @Override
    public void eliminar(Cuenta x) throws DAOException {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(DELETE);
            pst.setInt(1, x.getId());
            if (pst.executeUpdate() == 0) {
                throw new DAOException("No se pudo modificar revisar ID");
            }
        } catch (SQLException e) {
            throw new DAOException("Erro SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst);
        }
    }

    private Cuenta convertir(ResultSet rs) throws SQLException {
        int id_cuenta = rs.getInt(1);
        String nombre_cuenta = rs.getString(2);
        String contraseña_cuenta = rs.getString(3);
        String servidor_cuenta = rs.getString(4);
        int id_raza = rs.getInt(5);
        String lowRes = rs.getString(6);
        Cuenta cuenta = new Cuenta(id_cuenta, nombre_cuenta, contraseña_cuenta, servidor_cuenta, id_raza, lowRes);

        return cuenta;
    }

    @Override
    public List<Cuenta> obtenerTodos() throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        List<Cuenta> cuentas = new ArrayList<>();

        try {
            pst = conn.prepareStatement(GETALL);
            rst = pst.executeQuery();
            while (rst.next()) {
                cuentas.add(convertir(rst));
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return cuentas;
    }

    @Override
    public Cuenta obtener(String servidor) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        Cuenta cuenta;

        try {
            pst = conn.prepareStatement(GETONE);
            pst.setString(1, servidor);
            rst = pst.executeQuery();

            if (rst.next()) {

                cuenta = convertir(rst);
            } else {
                return null;
                //throw new DAOException("No hay cuenta con ese id");

            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return cuenta;
    }

    @Override
    public ResultSet obtenerTodosrst() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertarBatch(List<Cuenta> x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cuenta> obtenerTodosCondicion(String valor) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
