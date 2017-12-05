package travianarbot.modelo;

import java.awt.Color;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import travianarbot.dao.DAOException;

import travianarbot.dao.ManagerDAO;

import travianarbot.dao.sqlite.SQLiteManagerDAO;

public class ModeloTablaVaca {

    DefaultTableModel model = new DefaultTableModel();

    public ModeloTablaVaca() {

    }

    public DefaultTableModel setTableModel(JTable tabla, JLabel label) {

        SwingWorker<DefaultTableModel, Integer> worker = new SwingWorker<DefaultTableModel, Integer>() {
            ManagerDAO manager;
            ResultSet rst;
            ResultSetMetaData metaData;
            Vector<String> columnNames = new Vector<String>();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();

            @Override
            protected DefaultTableModel doInBackground() throws Exception {

                try {
                    manager = new SQLiteManagerDAO();
                    rst = manager.getVacaDAO().obtenerTodosrst();
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
                    publish(data.size());
                } catch (SQLException ex) {
                    Logger.getLogger(ModeloTablaVaca.class.getName()).log(Level.SEVERE, null, ex);
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

            @Override
            protected void process(List<Integer> list) {
                super.process(list); //To change body of generated methods, choose Tools | Templates.

                if (list.get(0) != 0) {
                    label.setText("Hay " + list.get(0) + " vacas");
                    label.setForeground(Color.GREEN);
                } else {
                    label.setText("Parece que no hay vacas agregadas, agruegemos algunas!!");
                    label.setForeground(Color.RED);
                }
            }

        };
        worker.execute();

        return null;
    }

    public void setTableModelB(JTable tabla) {
        ManagerDAO manager = null;

        ResultSetMetaData metaData;
        Vector<String> columnNames = new Vector<String>();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        try {
            ResultSet rst;
            manager = new SQLiteManagerDAO();
            rst = manager.getVacaDAO().obtenerTodosrst();
            if (rst != null) {
                metaData = rst.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }

                do {
                    Vector<Object> vector = new Vector<Object>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        vector.add(rst.getObject(columnIndex));
                    }
                    data.add(vector);
                } while (rst.next());
                if (rst != null) {
                    rst.close();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModeloTablaVaca.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(ModeloTablaVaca.class.getName()).log(Level.SEVERE, null, ex);
        }
        manager.closeConection();
        tabla.setModel(new DefaultTableModel(data, columnNames));
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
    }
}
