package travianarbot.dao;

import java.sql.ResultSet;
import java.util.List;

public interface DAO<T, K> {

    void insertar(T x) throws DAOException;

    void insertarBatch(List<T> x) throws DAOException;

    void modificar(T x) throws DAOException;

    void eliminar(T x) throws DAOException;

    List<T> obtenerTodos() throws DAOException;

    List<T> obtenerTodosCondicion(K valor) throws DAOException;

    ResultSet obtenerTodosrst() throws DAOException;

    T obtener(K id) throws DAOException;
    
}
