package travianarbot.dao;

public interface ManagerDAO {

    AldeaDAO getAldeaDAO();

    CuentaDAO getCuentaDAO();

    ArmadaDAO getArmadaDAO();

    VacaDAO getVacaDAO();

    TropaDAO getTropaDAO();

    InformeVacaDAO getInformeVacaDAO();

    void closeConection();

}
