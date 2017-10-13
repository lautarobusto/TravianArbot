package travianarbot.dao;

import java.util.List;
import travianarbot.modelo.Armada;

public interface ArmadaDAO extends DAO<Armada, Integer> {

    List<Armada> obtenerPorElo() throws DAOException;

}
