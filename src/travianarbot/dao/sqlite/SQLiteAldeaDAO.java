package travianarbot.dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import travianarbot.dao.AldeaDAO;
import travianarbot.dao.DAOException;
import travianarbot.modelo.Aldea;

public class SQLiteAldeaDAO implements AldeaDAO {

    final String INSERT = "INSERT or ignore INTO Aldeas (ID, ID_Cuenta,Nombre,Tipo_Terreno,Coordenada_X,Coordenada_Y,Z) VALUES(?, ?, ?, ?, ?, ?,?)";
    final String UPDATE = "UPDATE Aldeas SET Nombre = ?, Tipo_Terreno = ?, Coordenada_X = ?, Coordenada_Y = ?, Z= ? WHERE ID = ?";
    final String DELETE = "DELETE FROM Cuentas WHERE ID = ?";
    final String GETALL = "SELECT * FROM Aldeas";
    final String GETONE = "SELECT * FROM Aldeas WHERE ID = ?";
    final String GETRST = "Select ID, Nombre as 'Nombre de Aldea', Tipo_Terreno as 'Tipo de Aldea',Coordenada_X as 'Coord X', Coordenada_Y as 'Coord Y', Z from Aldeas";

    private Connection conn;

    public SQLiteAldeaDAO(Connection conn) {
        this.conn = conn;
    }

    private Aldea convertir(ResultSet rs) throws SQLException {
        int id_aldea = rs.getInt(1);
        int id_cuenta = rs.getInt(2);

        String nombre_aldea = rs.getString(3);
        String tipo_terreno = rs.getString(4);
        int coord_x = rs.getInt(5);
        int coord_y = rs.getInt(6);
        int z = rs.getInt(7);
        Aldea aldea = new Aldea(id_aldea, id_cuenta, nombre_aldea, tipo_terreno, coord_x, coord_y, z);

        return aldea;
    }

    @Override
    public void insertar(Aldea x) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try {
            pst = conn.prepareStatement(INSERT);
            pst.setInt(1, x.getId_aldea());
            pst.setInt(2, x.getId_cuenta());
            pst.setString(3, x.getNombre_aldea());
            pst.setString(4, x.getTipo_terreno());
            pst.setInt(5, x.getCoordenada_x());
            pst.setInt(6, x.getCoordenada_y());
            pst.setInt(7, x.getZ());

            if (pst.executeUpdate() == 0) {
                throw new DAOException("Error en  en executeUpdate");
            }
            rst = pst.getGeneratedKeys();
            if (rst.next()) {
                x.setId_cuenta(rst.getInt(1));
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
    public void insertarBatch(List<Aldea> x) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        try {

            pst = conn.prepareStatement("Delete from Aldeas");
            pst.executeUpdate();

            for (Aldea a : x) {

                pst = conn.prepareStatement(INSERT);
                pst.setInt(1, a.getId_aldea());
                pst.setInt(2, a.getId_cuenta());
                pst.setString(3, a.getNombre_aldea());
                pst.setString(4, a.getTipo_terreno());
                pst.setInt(5, a.getCoordenada_x());
                pst.setInt(6, a.getCoordenada_y());
                pst.setInt(7, a.getZ());

                if (pst.executeUpdate() == 0) {
                    throw new DAOException("Error en  en executeUpdate");
                }
                rst = pst.getGeneratedKeys();
                if (rst.next()) {
                    a.setId_cuenta(rst.getInt(1));
                } else {
                    throw new DAOException("Error en  en executeUpdate");
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteAldeaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
    }

    @Override
    public void modificar(Aldea x) throws DAOException {

    }

    @Override
    public void eliminar(Aldea x) throws DAOException {

    }

    @Override
    public List<Aldea> obtenerTodos() throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        List<Aldea> aldeas = new ArrayList<>();

        try {
            pst = conn.prepareStatement(GETALL);
            rst = pst.executeQuery();
            while (rst.next()) {
                aldeas.add(convertir(rst));
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return aldeas;
    }

    @Override
    public Aldea obtener(Integer id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Aldea> obtenerTodosCondicion(Integer valor) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet obtenerTodosrst() throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        try {
            pst = conn.prepareStatement(GETRST);
            rst = pst.executeQuery();

            if (rst.next()) {
                return rst;
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        }
        return null;
    }

}
