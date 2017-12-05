package travianarbot.modelo;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import travianarbot.dao.ManagerDAO;

import travianarbot.dao.sqlite.SQLiteManagerDAO;

public class ModeloTablaAldea {

    DefaultTableModel model = new DefaultTableModel();

    public ModeloTablaAldea() {

    }

    public DefaultTableModel setTableModel(JTable tabla) {

        SwingWorker<DefaultTableModel, Void> worker = new SwingWorker<DefaultTableModel, Void>() {
            ManagerDAO manager;
            ResultSet rst;
            ResultSetMetaData metaData;
            Vector<String> columnNames = new Vector<String>();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();

            @Override
            protected DefaultTableModel doInBackground() throws Exception {

                try {
                    manager = new SQLiteManagerDAO();
                    rst = manager.getAldeaDAO().obtenerTodosrst();
                    if (rst != null) {
                        metaData = rst.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        for (int column = 1; column <= columnCount; column++) {
                            columnNames.add(metaData.getColumnName(column));
                        }

                        do {
                            Vector<Object> vector = new Vector<Object>();
                            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                                vector.add(this.rst.getObject(columnIndex));
                            }
                            data.add(vector);
                        } while (rst.next());
                        rst.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ModeloTablaAldea.class.getName()).log(Level.SEVERE, null, ex);
                }
                manager.closeConection();
                return new DefaultTableModel(data, columnNames);

            }

            @Override
            protected void done() {

                try {
                    tabla.setModel(get());
                    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    tabla.getTableHeader().setReorderingAllowed(false);
                    final TableColumnModel columnModel = tabla.getColumnModel();
                    for (int column = 0; column < tabla.getColumnCount(); column++) {
                        int width = 60; // Min width
                        for (int row = 0; row < tabla.getRowCount(); row++) {
                            TableCellRenderer renderer = tabla.getCellRenderer(row, column);
                            Component comp = tabla.prepareRenderer(renderer, row, column);
                            width = Math.max(comp.getPreferredSize().width + 1, width);
                        }
                        if (width > 300) {
                            width = 300;
                        }
                        columnModel.getColumn(column).setPreferredWidth(width);
                    }
                } catch (InterruptedException ex) {
                    //Logger.getLogger(ModeloTablaVaca.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    //Logger.getLogger(ModeloTablaVaca.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        };
        worker.execute();

        return null;
    }

}
