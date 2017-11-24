package travianarbot.dao;

import travianarbot.modelo.Cuenta;

public interface CuentaDAO extends DAO<Cuenta, String> {

    public void insertarPrfix() throws DAOException;
}
