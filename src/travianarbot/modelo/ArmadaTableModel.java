package travianarbot.modelo;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import travianarbot.dao.ArmadaDAO;
import travianarbot.dao.DAOException;
import travianarbot.modelo.Armada;

public class ArmadaTableModel extends AbstractTableModel {

    private ArmadaDAO armadas;
    private List<Armada> datos = new ArrayList<>();

    public ArmadaTableModel(ArmadaDAO armadas) {
        this.armadas = armadas;
    }

    public void updateModel() throws DAOException {
        datos = armadas.obtenerTodos();
    }

    @Override
    public String getColumnName(int i) {
        switch (i) {
            case 0:
                return "ID";
            case 1:
                return "Nombre";
            case 2:
                return "T1";
            case 3:
                return "T2";
            case 4:
                return "T3";
            case 5:
                return "T4";
            case 6:
                return "T5";
            case 7:
                return "T6";
            case 8:
                return "T7";
            case 9:
                return "T8";
            case 10:
                return "T9";
            case 11:
                return "T10";
            case 12:
                return "T11";

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
        return 13;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Armada consulta = datos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return consulta.getId();
            case 1:
                return consulta.getNombre();
            case 2:
                return consulta.getT1();
            case 3:
                return consulta.getT2();
            case 4:
                return consulta.getT3();
            case 5:
                return consulta.getT4();
            case 6:
                return consulta.getT5();
            case 7:
                return consulta.getT6();
            case 8:
                return consulta.getT7();
            case 9:
                return consulta.getT8();
            case 10:
                return consulta.getT9();
            case 11:
                return consulta.getT10();
            case 12:
                return consulta.getT11();
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
