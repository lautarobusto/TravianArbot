package travianarbot.dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import travianarbot.dao.DAOException;
import travianarbot.dao.TropaDAO;
import travianarbot.modelo.Aldea;
import travianarbot.modelo.Tropa;

public class SQLiteTropaDAO implements TropaDAO {

    final String INSERT = "INSERT or ignore INTO Aldeas (ID_Aldea, ID_Cuenta,Nombre_Aldea,Tipo_Terreno,Coordenada_X,Coordenada_Y) VALUES(?, ?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE Aldeas SET Nombre_Aldea = ?, Tipo_Terreno = ?, Coordenada_X = ?, Coordenada_Y = ? WHERE ID_Aldea = ?";
    final String DELETE = "DELETE FROM Cuentas WHERE ID_Aldea = ?";
    final String GETALL = "SELECT * FROM Aldeas";
    final String GETONE = "SELECT * FROM Tropas WHERE ID_Tropa = ?";
    final String GETXRAZA = "SELECT * FROM Tropas WHERE Id_Raza_Tropa = (SELECT Id_Raza FROM Razas WHERE Nombre_Raza = ?)";
    private Connection conn;

    public SQLiteTropaDAO(Connection conn) {
        this.conn = conn;
    }

    private Tropa convertir(ResultSet rs) throws SQLException {
        int id_tropa = rs.getInt(1);
        String nombre_tropa = rs.getString(2);
        int ataque_tropa = rs.getInt(3);
        int defensa_infanteria_tropa = rs.getInt(4);
        int defensa_caballeria_tropa = rs.getInt(5);
        int velocidad_tropa = rs.getInt(6);
        int transporte_tropa = rs.getInt(7);
        int id_raza = rs.getInt(8);
        int manutencion_tropa = rs.getInt(9);

        Tropa tropa = new Tropa(id_tropa, nombre_tropa, ataque_tropa, defensa_infanteria_tropa, defensa_caballeria_tropa, velocidad_tropa, transporte_tropa, id_raza, manutencion_tropa);
        return tropa;
    }

    @Override
    public void insertar(Tropa x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertarBatch(List<Tropa> x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Tropa x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(Tropa x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tropa> obtenerTodos() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tropa> obtenerTodosCondicion(Integer valor) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet obtenerTodosrst() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tropa obtener(Integer id) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        Tropa tropa;

        try {
            pst = conn.prepareStatement(GETONE);
            pst.setInt(1, id);
            rst = pst.executeQuery();

            if (rst.next()) {

                tropa = convertir(rst);

            } else {

                throw new DAOException("No hay cuenta con ese id");

            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);

        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return tropa;
    }

    @Override
    public List<Tropa> obtenerPorRaza(String raza) throws DAOException {

        PreparedStatement pst = null;
        ResultSet rst = null;
        List<Tropa> tropas = new ArrayList<>();

        try {
            pst = conn.prepareStatement(GETXRAZA);
            pst.setString(1, raza);
            rst = pst.executeQuery();
            while (rst.next()) {
                tropas.add(convertir(rst));
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return tropas;
    }

}
