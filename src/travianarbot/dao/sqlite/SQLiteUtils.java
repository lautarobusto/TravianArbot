package travianarbot.dao.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import travianarbot.dao.DAOException;

public class SQLiteUtils {

    static void cerrar(PreparedStatement pst) throws DAOException {

        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                throw new DAOException("Error SQL", e);
            }
        }
    }

    static void cerrar(PreparedStatement pst, ResultSet rst) throws DAOException {

        if (rst != null) {
            try {
                rst.close();
            } catch (SQLException ex) {
                throw new DAOException("Error SQL", ex);
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                throw new DAOException("Error SQL", e);
            }
        }
    }

    static void cerrar(ResultSet rst) throws DAOException {

        if (rst != null) {
            try {
                rst.close();
            } catch (SQLException e) {
                throw new DAOException("Error SQL", e);
            }
        }
    }

}
