package travianarbot.dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import travianarbot.dao.AldeaDAO;
import travianarbot.dao.DAOException;
import travianarbot.modelo.Aldea;
import travianarbot.modelo.Cuenta;

public class SQLiteAldeaDAO implements AldeaDAO {

    final String INSERT = "INSERT or ignore INTO Aldeas (ID_Aldea, ID_Cuenta,Nombre_Aldea,Tipo_Terreno,Coordenada_X,Coordenada_Y) VALUES(?, ?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Aldeas SET Nombre_Aldea = ?, Tipo_Terreno = ?, Coordenada_X = ?, Coordenada_Y = ? WHERE ID_Aldea = ?";
    final String DELETE = "DELETE FROM Cuentas WHERE ID_Aldea = ?";
    final String GETALL = "SELECT * FROM Aldeas";
    final String GETONE = "SELECT * FROM Aldeas WHERE ID_Aldea = ?";
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

        Aldea aldea = new Aldea(id_aldea, id_cuenta, nombre_aldea, tipo_terreno, coord_x, coord_y);
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

        for (Aldea a : x) {
            System.out.println(a.toString());
            try {
                pst = conn.prepareStatement(INSERT);
                pst.setInt(1, a.getId_aldea());
                pst.setInt(2, a.getId_cuenta());
                pst.setString(3, a.getNombre_aldea());
                pst.setString(4, a.getTipo_terreno());
                pst.setInt(5, a.getCoordenada_x());
                pst.setInt(6, a.getCoordenada_y());

                if (pst.executeUpdate() == 0) {
                    throw new DAOException("Error en  en executeUpdate");
                }
                rst = pst.getGeneratedKeys();
                if (rst.next()) {
                    a.setId_cuenta(rst.getInt(1));
                } else {
                    throw new DAOException("Error en  en executeUpdate");
                }

            } catch (SQLException ex) {
                throw new DAOException("Error en  SQL", ex);
            } finally {
                SQLiteUtils.cerrar(pst, rst);
            }
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
    public ResultSet obtenerTodosrst() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Aldea> obtenerTodosCondicion(Integer valor) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
