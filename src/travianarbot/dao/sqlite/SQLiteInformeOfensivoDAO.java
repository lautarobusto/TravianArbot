package travianarbot.dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import travianarbot.dao.DAOException;
import travianarbot.dao.InformeOfensivoDAO;
import travianarbot.modelo.InformeOfensivo;
public class SQLiteInformeOfensivoDAO implements InformeOfensivoDAO {

    final String INSERT = "INSERT INTO InformesOfensivos (ID, Asunto,Fecha,Hora,Botin_Posible,Botin_Real,Eficiencia,ID_Aldea_Atacante,ID_Vaca_Defensora) VALUES(?,?,?,?,?,?,?,?,?)";
    final String UPDATE = "Update InformesOfensivos SET Asunto=? ,Fecha= ?,Hora= ?,Botin_Posible= ?,Botin_Real= ?,Eficiencia= ?,ID_Aldea_Atacante= ?,ID_Vaca_Defensora= ? where ID = ?";
    final String DELETE = "DELETE FROM InformesOfensivos WHERE ID = ?";
    final String GETALL = "SELECT * FROM InformesOfensivos";
    final String GETONE = "SELECT * FROM InformesOfensivos WHERE ID = ?";
    final String GETBYDATE = "select * from InformesOfensivos order by Fecha, Hora";
    private Connection conn;

    public SQLiteInformeOfensivoDAO(Connection conn) {
        this.conn = conn;
    }

    private InformeOfensivo convertir(ResultSet rs) throws SQLException {
        String id_informe = rs.getString(1);
        String asunto_informe = rs.getString(2);
        String fecha_Informe = rs.getString(3);
        String hora_informe = rs.getString(4);
        int botin_posible = rs.getInt(5);
        int botin_real = rs.getInt(6);
        float eficiencia = rs.getFloat(7);
        int id_aldea_atacante = rs.getInt(8);
        int id_vaca_defensora = rs.getInt(9);

        InformeOfensivo informe = new InformeOfensivo(id_informe, asunto_informe, fecha_Informe, hora_informe, botin_posible, botin_real, eficiencia, id_aldea_atacante, id_vaca_defensora);
        return informe;
    }

    @Override
    public void insertar(InformeOfensivo x) throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;

        try {
            pst = conn.prepareStatement(INSERT);
            pst.setString(1, x.getId_informe());
            pst.setString(2, x.getAsunto_informe());
            pst.setString(3, x.getFecha_Informe());
            pst.setString(4, x.getHora_informe());
            pst.setInt(5, x.getBotin_posible());
            pst.setInt(6, x.getBotin_real());
            pst.setFloat(7, x.getEficiencia());
            pst.setInt(8, x.getId_aldea_atacante());
            pst.setInt(9, x.getId_vaca_defensora());

            if (pst.executeUpdate() == 0) {
                throw new DAOException("Error en  en executeUpdate");
            }
            rst = pst.getGeneratedKeys();
            if (rst.next()) {
                //.setId_armada(rst.getInt(1));
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
    public void modificar(InformeOfensivo x) throws DAOException {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(UPDATE);
            pst.setString(1, x.getAsunto_informe());
            pst.setString(2, x.getFecha_Informe());
            pst.setString(3, x.getHora_informe());
            pst.setInt(4, x.getBotin_posible());
            pst.setInt(5, x.getBotin_real());
            pst.setFloat(6, x.getEficiencia());
            pst.setInt(7, x.getId_aldea_atacante());
            pst.setInt(8, x.getId_vaca_defensora());
            pst.setString(9, x.getId_informe());

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
    public void eliminar(InformeOfensivo x) throws DAOException {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(DELETE);
            pst.setString(1, x.getId_informe());
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
    public List<InformeOfensivo> obtenerTodos() throws DAOException {
        PreparedStatement pst = null;
        ResultSet rst = null;
        List<InformeOfensivo> informes = new ArrayList<>();

        try {
            pst = conn.prepareStatement(GETALL);
            rst = pst.executeQuery();

            while (rst.next()) {
                informes.add(convertir(rst));
            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);
        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return informes;
    }

    @Override
    public InformeOfensivo obtener(String id) throws DAOException {

        PreparedStatement pst = null;
        ResultSet rst = null;
        InformeOfensivo informe;

        try {
            pst = conn.prepareStatement(GETONE);
            pst.setString(1, id);
            rst = pst.executeQuery();

            if (rst.next()) {

                informe = convertir(rst);

            } else {

                throw new DAOException("No hay cuenta con ese id");

            }

        } catch (SQLException e) {
            throw new DAOException("Error SQL", e);

        } finally {
            SQLiteUtils.cerrar(pst, rst);
        }
        return informe;
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
    public List<InformeOfensivo> obtenerTodosCondicion(String valor) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertarBatch(List<InformeOfensivo> x) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
