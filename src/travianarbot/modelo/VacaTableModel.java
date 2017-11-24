package travianarbot.modelo;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import travianarbot.dao.DAOException;
import travianarbot.dao.VacaDAO;

public class VacaTableModel extends AbstractTableModel {

    private VacaDAO vacas;
    private List<Vaca> datos = new ArrayList<>();

    public VacaTableModel(VacaDAO vacas) {
        this.vacas = vacas;
    }

    public void updateModel() throws DAOException {
        datos = vacas.obtenerTodos();
    }

    @Override
    public String getColumnName(int i) {
        switch (i) {
            case 0:
                return "ID";
            case 1:
                return "Nombre";
            case 2:
                return "Aldea";
            case 3:
                return "Movimiento";
            case 4:
                return "Armada";
            case 5:
                return "Eficiencia";
            case 6:
                return "ELO";
            case 7:
                return "Coordenada X";
            case 8:
                return "Coordenada Y";
            case 9:
                return "Activo";
            case 10:
                return "Karte";
            default:
                return "";
        }
    }

    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return 11;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vaca consulta = datos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return consulta.getId();
            case 1:
                return consulta.getNombre();
            case 2:
                return consulta.getId_aldea_origen();
            case 3:
                return consulta.getId_movimiento();
            case 4:
                return consulta.getId_armada_activa();
            case 5:
                return consulta.getEficiencia();
            case 6:
                return consulta.getPuntos_ataque();
            case 7:
                return consulta.getCoordenada_x();
            case 8:
                return consulta.getCoordenada_y();
            case 9:
                return consulta.isActivo();
            case 10:
                return consulta.getZ();

            default:
                return "";
        }
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

}
