package travianarbot.dao;

import travianarbot.modelo.InformeVaca;

public interface InformeVacaDAO extends DAO<InformeVaca, String> {

    public String obtenerUltimo()throws DAOException;

}
