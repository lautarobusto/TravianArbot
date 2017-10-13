package travianarbot.modelo;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import travianarbot.dao.AldeaDAO;

import travianarbot.dao.DAOException;

public class AldeasTableModel extends AbstractTableModel {

    private AldeaDAO aldeas;
    private List<Aldea> datos = new ArrayList<>();

    public AldeasTableModel(AldeaDAO aldeas) {
        this.aldeas = aldeas;
    }

    public void updateModel() throws DAOException {
        datos = aldeas.obtenerTodos();
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

    @Override
    public String getColumnName(int i) {
        switch (i) {
            case 0:
                return "ID";
            case 1:
                return "ID Cuenta";
            case 2:
                return "Aldea";
            case 3:
                return "Terreno";
            case 4:
                return "Coood X";
            case 5:
                return "Coord Y";
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
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Aldea consulta = datos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return consulta.getId_aldea();
            case 1:
                return consulta.getId_cuenta();
            case 2:
                return consulta.getNombre_aldea();
            case 3:
                return consulta.getTipo_terreno();
            case 4:
                return consulta.getCoordenada_x();
            case 5:
                return consulta.getCoordenada_y();
            default:
                return "";
        }
    }

}
