package travianarbot.dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import travianarbot.dao.DAOException;
import travianarbot.dao.VacaDAO;
import travianarbot.modelo.Vaca;

public class SQLiteVacaDAO implements VacaDAO {

    final String INSERT = "INSERT or ignore INTO Vacas (Nombre_Vaca, ID_Aldea,ID_Movimiento,ID_Armada,Eficiencia_Vaca,Elo_Vaca,Coordenada_X,Coordenada_Y,Activo,Z) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Vacas SET Nombre_Vaca = ?, ID_Aldea = ?,ID_Movimiento = ?,ID_Armada = ?,Eficiencia_Vaca = ?,Elo_Vaca = ?, Coordenada_X = ?, Coordenada_Y = ?,Activo= ?,Z= ? WHERE ID_Vaca = ?";
    final String DELETE = "DELETE FROM Vacas WHERE ID_Vaca = ?";
    final String GETALL = "SELECT * FROM Vacas";
    final String GETONE = "SELECT * FROM Vacas WHERE ID_Vaca = ?";
    final String GETACTIVE = "SELECT * FROM Vacas WHERE Activo = ?";
    final String GETEXIST = "SELECT * FROM Vacas WHERE Z = ?";
    private Connection conn;

    public SQLiteVacaDAO(Connection conn) {
        this.conn = conn;
    }

    private Vaca convertir(ResultSet rs) throws SQLException {
        int id_vaca = rs.getInt(1);
        String nombre_vaca = rs.getString(2);
        int id_aldea = rs.getInt(3);
        String id_movimiento = rs.getString(4);
        int id_armada = rs.getInt(5);
        float eficiencia = rs.getFloat(6);
        float elo = rs.getFloat(7);
        int coordenada_x = rs.getInt(8);
        int coordenada_y = rs.getInt(9);
        boolean activo = Boolean.valueOf(rs.getString(10));
        int z = rs.getInt(11);

        Vaca vaca = new Vaca(id_vaca, nombre_vaca, id_aldea, id_movimiento, coordenada_x, coordenada_y, eficiencia, elo, id_armada, z, activo);
        return vaca;
    }

    @Override
    public void insertar(Vaca x) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try {
            pst = conn.prepareStatement(INSERT);
            pst.setString(1, x.getNombre_vaca());
            pst.setInt(2, x.getId_aldea());
            pst.setString(3, x.getId_movimiento());
            pst.setInt(4, x.getId_armada());
            pst.setFloat(5, x.getEficiencia());
            pst.setFloat(6, x.getElo());
            pst.setInt(7, x.getCoordenada_x());
            pst.setInt(8, x.getCoordenada_y());
            pst.setString(9, String.valueOf(x.getActivo()));
            pst.setInt(10, x.getZ());

            if (pst.executeUpdate() == 0) {
                throw new DAOException("Error en  en executeUpdate");
            }
            rst = pst.getGeneratedKeys();
            if (rst.next()) {
                x.setId_vaca(rst.getInt(1));
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
    public void insertarBatch(List<Vaca> x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Vaca x) throws DAOException {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(UPDATE);
            pst.setString(1, x.getNombre_vaca());
            pst.setInt(2, x.getId_aldea());
            pst.setString(3, x.getId_movimiento());
            pst.setInt(4, x.getId_armada());
            pst.setFloat(5, x.getEficiencia());
            pst.setFloat(6, x.getElo());
            pst.setInt(7, x.getCoordenada_x());
            pst.setInt(8, x.getCoordenada_y());
            pst.setString(9, String.valueOf(x.getActivo()));
            pst.setInt(10, x.getZ());
            pst.setInt(11, x.getId_vaca());

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
    public void eliminar(Vaca x) throws DAOException {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(DELETE);
            pst.setInt(1, x.getId_vaca());
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
    public List<Vaca> obtenerTodos() throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        List<Vaca> vacas = new ArrayList<>();

        try {
            pst = conn.prepareStatement(GETALL);
            rst = pst.executeQuery();

            while (rst.next()) {
                vacas.add(convertir(rst));
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return vacas;
    }

    @Override
    public ResultSet obtenerTodosrst() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vaca obtener(Integer id) throws DAOException {

        PreparedStatement pst = null;
        ResultSet rst = null;
        Vaca armada;

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
    public List<Vaca> obtenerTodosCondicion(Integer valor) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        List<Vaca> vacas = new ArrayList<>();

        try {
            pst = conn.prepareStatement(GETACTIVE);
            switch (valor) {
                case 1:
                    pst.setString(1, "true");
                    rst = pst.executeQuery();
                    break;
                default:
                    pst.setString(1, "false");
                    rst = pst.executeQuery();

            }

            while (rst.next()) {
                vacas.add(convertir(rst));
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return vacas;
    }

    @Override
    public boolean exist(Integer z) {
        PreparedStatement pst = null;
        ResultSet rst = null;
        List<Vaca> vacas = new ArrayList<>();

        try {
            pst = conn.prepareStatement(GETEXIST);
            pst.setInt(1, z);
            rst = pst.executeQuery();

            while (rst.next()) {
                vacas.add(convertir(rst));
            }

        } catch (SQLException ex) {
            Logger.getLogger(SQLiteVacaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                SQLiteUtils.cerrar(pst, rst);
            } catch (DAOException ex) {
                Logger.getLogger(SQLiteVacaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (vacas.size() != 0) {
            return true;
        }
        return false;
    }

}
