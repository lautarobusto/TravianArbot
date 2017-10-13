package travianarbot.dao;

import java.util.List;
import travianarbot.modelo.Tropa;

public interface TropaDAO extends DAO<Tropa, Integer> {

    List<Tropa> obtenerPorRaza(String raza) throws DAOException;
}
