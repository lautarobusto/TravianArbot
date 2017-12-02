package travianarbot.dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import travianarbot.dao.DAOException;
import travianarbot.dao.InformeVacaDAO;
import travianarbot.modelo.InformeVaca;

public class SQLiteInformeVacaDAO implements InformeVacaDAO {

    final String INSERT = "INSERT INTO InformesVacas (ID, Asunto,Fecha,Hora,Eficiencia,Perdidas,Z_Aldea_Ofensiva,Z_Aldea_Defensora) VALUES(?,?,?,?,?,?,?,?)";
    final String UPDATE = "UPDATE InformesVacas SET Asunto = ?, Fecha = ?, Hora = ?, Eficiencia = ?, Perdidas= ?, Z_Aldea_Ofensiva= ?, Z_Aldea_Defensora= ? WHERE ID = ?";
    final String DELETE = "DELETE FROM InformesVacas WHERE ID = ?";
    final String GETALL = "SELECT * FROM InformesVacas";
    final String GETALLBYVACA = "SELECT * FROM InformesVacas WHERE Z_Aldea_Defensora = ?";
    final String GETLAST = "select ID from InformesVacas order by Fecha and Hora limit 1";
    private Connection conn;

    public SQLiteInformeVacaDAO(Connection conn) {
        this.conn = conn;
    }

    private InformeVaca convertir(ResultSet rs) throws SQLException {

        String id = rs.getString(1);
        String asunto = rs.getString(1);
        String fecha = rs.getString(3);
        String hora = rs.getString(4);
        float eficiencia = rs.getFloat(6);
        float perdidas = rs.getFloat(7);
        int z_Ofensiva = rs.getInt(8);
        int z_Defensivo = rs.getInt(9);

        InformeVaca informe = new InformeVaca(id, asunto, fecha, hora, eficiencia, perdidas, z_Defensivo, z_Defensivo);
        return informe;
    }

    @Override
    public void insertar(InformeVaca x) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try {
            pst = conn.prepareStatement(INSERT);
            pst.setString(1, x.getId());
            pst.setString(2, x.getAsunto());
            pst.setString(3, x.getFecha());
            pst.setString(4, x.getHora());
            pst.setFloat(5, x.getEficiencia());
            pst.setFloat(6, x.getPerdidas());
            pst.setInt(7, x.getZ_aldea_atacante());
            pst.setInt(8, x.getZ_aldea_defensora());

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
    public void insertarBatch(List<InformeVaca> x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(InformeVaca x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(InformeVaca x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InformeVaca> obtenerTodos() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<InformeVaca> obtenerTodosCondicion(String valor) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResultSet obtenerTodosrst() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InformeVaca obtener(String id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtenerUltimo() throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try {
            rst = conn.prepareStatement(GETLAST).executeQuery();

            if (rst.next()) {

                return rst.getString(1);
            } else {
                return null;
                //throw new DAOException("No hay cuenta con ese id");

            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }

    }
}
