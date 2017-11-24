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

    final String INSERT = "INSERT or ignore INTO Vacas (Nombre, ID_Aldea_Origen,ID_Movimiento,ID_Armada_Activa,Eficiencia,Puntos_Ataque,Coordenada_X,Coordenada_Y,Activo,Z,Modificador_Armada,Estado_Armada,Info,ID_Progresion,Progresion_Activa) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE = "UPDATE Vacas SET Nombre = ?, ID_Aldea_Origen = ?,ID_Movimiento = ?,ID_Armada_Activa = ?,Eficiencia = ?,Puntos_Ataque = ?, Coordenada_X = ?, Coordenada_Y = ?,Activo= ?,Z= ?,Modificador_Armada = ?,Estado_Armada = ?,Info = ?, ID_Progresion= ?, Progresion_Activa= ?"
            + " WHERE ID = ?";
    final String UPDATEEFI = "UPDATE Vacas SET Eficiencia =? WHERE ID=?";
    final String DELETE = "DELETE FROM Vacas WHERE ID = ?";
    final String GETALL = "SELECT * FROM Vacas";
    final String GETONE = "SELECT * FROM Vacas WHERE ID = ?";
    final String GETACTIVE = "SELECT * FROM Vacas WHERE Activo = ?";
    final String GETEXIST = "SELECT * FROM Vacas WHERE Z = ?";
    private Connection conn;

    public SQLiteVacaDAO(Connection conn) {
        this.conn = conn;
    }

    private Vaca convertir(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String nombre = rs.getString(2);
        int id_aldea_origen = rs.getInt(3);
        String id_movimiento = rs.getString(4);
        int id_armada_activa = rs.getInt(5);
        float eficiencia = rs.getFloat(6);
        float puntos_ataque = rs.getFloat(7);
        int coordenada_x = rs.getInt(8);
        int coordenada_y = rs.getInt(9);
        boolean activo = Boolean.valueOf(rs.getString(10));
        int z = rs.getInt(11);
        int modificador_armada = rs.getInt(12);
        int estado_armada = rs.getInt(13);
        String info = rs.getString(14);
        int id_progresion = rs.getInt(15);
        boolean usa_progresion = rs.getBoolean(16);

        Vaca vaca = new Vaca(id, nombre, id_aldea_origen, id_movimiento, coordenada_x, coordenada_y, estado_armada, id_armada_activa, usa_progresion, id_progresion, estado_armada, modificador_armada, z, activo, info);
        return vaca;
    }

    @Override
    public void insertar(Vaca x) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try {
            pst = conn.prepareStatement(INSERT);
            pst.setString(1, x.getNombre());
            pst.setInt(2, x.getId_aldea_origen());
            pst.setString(3, x.getId_movimiento());
            pst.setInt(4, x.getId_armada_activa());
            pst.setFloat(5, x.getEficiencia());
            pst.setFloat(6, x.getPuntos_ataque());
            pst.setInt(7, x.getCoordenada_x());
            pst.setInt(8, x.getCoordenada_y());
            pst.setString(9, String.valueOf(x.isActivo()));
            pst.setInt(10, x.getZ());
            pst.setInt(11, x.getModificador_armada());
            pst.setInt(12, x.getEstado_armada());
            pst.setString(13, x.getInfo());
            pst.setInt(14, x.getId_progresion());
            pst.setBoolean(15, x.isProgresion_activa());

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

    @Override
    public void insertarBatch(List<Vaca> x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Vaca x) throws DAOException {
        PreparedStatement pst = null;
        try {
            pst.setString(1, x.getNombre());
            pst.setInt(2, x.getId_aldea_origen());
            pst.setString(3, x.getId_movimiento());
            pst.setInt(4, x.getId_armada_activa());
            pst.setFloat(5, x.getEficiencia());
            pst.setFloat(6, x.getPuntos_ataque());
            pst.setInt(7, x.getCoordenada_x());
            pst.setInt(8, x.getCoordenada_y());
            pst.setString(9, String.valueOf(x.isActivo()));
            pst.setInt(10, x.getZ());
            pst.setInt(11, x.getModificador_armada());
            pst.setInt(12, x.getEstado_armada());
            pst.setString(13, x.getInfo());
            pst.setInt(14, x.getId_progresion());
            pst.setBoolean(15, x.isProgresion_activa());
            pst.setInt(16, x.getId());

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
            pst.setInt(1, x.getId());
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

    @Override
    public void actualizarEficiencia(Integer id, Float eficiencia) throws DAOException {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(UPDATE);
            pst.setFloat(1, eficiencia);
            pst.setInt(2, id);

            if (pst.executeUpdate() == 0) {
                throw new DAOException("No se Actualizo el elemento");

            }
        } catch (SQLException e) {
            throw new DAOException("Erro SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst);
        }
    }

}
