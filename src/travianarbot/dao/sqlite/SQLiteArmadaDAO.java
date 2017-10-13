package travianarbot.dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import travianarbot.dao.ArmadaDAO;
import travianarbot.dao.DAOException;
import travianarbot.modelo.Armada;

public class SQLiteArmadaDAO implements ArmadaDAO {

    final String INSERT = "INSERT INTO Armadas (Nombre_Armada, T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,Elo_Armada) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE = "Update Armadas SET Nombre_Armada=? ,T1= ?,T2= ?,T3= ?,T4= ?,T5= ?,T6= ?,T7= ?,T8= ?,T9= ?,T10= ?,T11= ?,Elo_Armada =? where ID_Armada = ?";
    final String DELETE = "DELETE FROM Armadas WHERE ID_Armada = ?";
    final String GETALL = "SELECT * FROM Armadas";
    final String GETONE = "SELECT * FROM Armadas WHERE ID_Armada = ?";
    final String GETBYELO = "SELECT * FROM Armadas ORDER BY ELO DESC";
    private Connection conn;

    public SQLiteArmadaDAO(Connection conn) {
        this.conn = conn;
    }

    private Armada convertir(ResultSet rs) throws SQLException {
        int id_armada = rs.getInt(1);
        String nombre_armada = rs.getString(2);
        int t1 = rs.getInt(3);
        int t2 = rs.getInt(4);
        int t3 = rs.getInt(5);
        int t4 = rs.getInt(6);
        int t5 = rs.getInt(7);
        int t6 = rs.getInt(8);
        int t7 = rs.getInt(9);
        int t8 = rs.getInt(10);
        int t9 = rs.getInt(11);
        int t10 = rs.getInt(12);
        int t11 = rs.getInt(13);
        int elo = rs.getInt(14);
        Armada armada = new Armada(id_armada, nombre_armada, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, elo);
        return armada;
    }

    @Override
    public void insertar(Armada x) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try {
            pst = conn.prepareStatement(INSERT);
            pst.setString(1, x.getNombre_armada());
            pst.setInt(2, x.getT1());
            pst.setInt(3, x.getT2());
            pst.setInt(4, x.getT3());
            pst.setInt(5, x.getT4());
            pst.setInt(6, x.getT5());
            pst.setInt(7, x.getT6());
            pst.setInt(8, x.getT7());
            pst.setInt(9, x.getT8());
            pst.setInt(10, x.getT9());
            pst.setInt(11, x.getT10());
            pst.setInt(12, x.getT11());
            pst.setInt(13, x.getElo());

            if (pst.executeUpdate() == 0) {
                throw new DAOException("Error en  en executeUpdate");
            }
            rst = pst.getGeneratedKeys();
            if (rst.next()) {
                x.setId_armada(rst.getInt(1));
            } else {
                throw new DAOException("Error en  en executeUpdate");
            }

        } catch (SQLException ex) {
            throw new DAOException("Error en  SQL", ex);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
    }

    @Override
    public void insertarBatch(List<Armada> x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Armada x) throws DAOException {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(UPDATE);
            pst.setString(1, x.getNombre_armada());
            pst.setInt(2, x.getT1());
            pst.setInt(3, x.getT2());
            pst.setInt(4, x.getT3());
            pst.setInt(5, x.getT4());
            pst.setInt(6, x.getT5());
            pst.setInt(7, x.getT6());
            pst.setInt(8, x.getT7());
            pst.setInt(9, x.getT8());
            pst.setInt(10, x.getT9());
            pst.setInt(11, x.getT10());
            pst.setInt(12, x.getT11());
            pst.setInt(13, x.getElo());
            pst.setInt(14, x.getId_armada());
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
    public void eliminar(Armada x) throws DAOException {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(DELETE);
            pst.setInt(1, x.getId_armada());
            if (pst.executeUpdate() == -1) {
                throw new DAOException("No se pudo modificar revisar ID");
            }
        } catch (SQLException e) {
            throw new DAOException("Erro SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst);
        }
    }

    @Override
    public List<Armada> obtenerTodos() throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        List<Armada> armadas = new ArrayList<>();

        try {
            pst = conn.prepareStatement(GETALL);
            rst = pst.executeQuery();

            while (rst.next()) {
                armadas.add(convertir(rst));
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return armadas;
    }

    @Override
    public Armada obtener(Integer id) throws DAOException {

        PreparedStatement pst = null;
        ResultSet rst = null;
        Armada armada;

        try {
            pst = conn.prepareStatement(GETONE);
            pst.setInt(1, id);
            rst = pst.executeQuery();

            if (rst.next()) {

                armada = convertir(rst);

            } else {

                throw new DAOException("No hay cuenta con ese id");

            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);

        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return armada;
    }

    @Override
    public ResultSet obtenerTodosrst() throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try {
            pst = conn.prepareStatement(GETALL);
            rst = pst.executeQuery();

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return rst;
    }

    @Override
    public List<Armada> obtenerTodosCondicion(Integer valor) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Armada> obtenerPorElo() throws DAOException {

        PreparedStatement pst = null;
        ResultSet rst = null;
        List<Armada> armadas = new ArrayList<>();

        try {
            pst = conn.prepareStatement(GETBYELO);
            rst = pst.executeQuery();

            while (rst.next()) {
                armadas.add(convertir(rst));
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return armadas;

    }

}