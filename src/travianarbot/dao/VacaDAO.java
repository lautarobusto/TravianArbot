package travianarbot.dao;

import travianarbot.modelo.Vaca;

public interface VacaDAO extends DAO<Vaca, Integer> {

    boolean exist(Integer z);

    void actualizarEficiencia(Integer id, Float eficiencia) throws DAOException;

}
