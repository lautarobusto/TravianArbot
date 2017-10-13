package travianarbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    private Properties prop;
    private OutputStream output;
    private InputStream input = null;

    public Config() {
        this.prop = new Properties();
        this.output = null;
        this.input = null;

    }

    public void inicializar() {

        if (!new File("config.properties").exists()) {
            try {
                System.out.println("Se inicializo archivo Config");
                output = new FileOutputStream("config.properties");
                // set the properties value
                prop.setProperty("Server", "");
                prop.setProperty("Usuario", "");
                prop.setProperty("Password", "");
                prop.setProperty("Raza", "");
                prop.setProperty("LowRes", "");
                prop.setProperty("Navegador", "");
                prop.setProperty("RecordarCredenciales", "");
                prop.setProperty("IDCuentaActiva", "");
                prop.setProperty("TimerListaVacas", "5");
                // save properties to project root folder
                prop.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        } else {
            System.out.println("Archivo Config ya existia");

        }
    }

    public void addProperties(String[] properties, String[] values) {

        try {
            input = new FileInputStream("config.properties");

            this.prop.load(input);
            // set the properties value
            for (int i = 0; i < properties.length; i++) {

                if (!this.prop.containsKey(properties[i])) {
                    this.prop.setProperty(properties[i], values[i]);
                    System.out.println("La propiedad se agrego");
                } else {
                    System.out.println("La propiedad: --" + properties[i] + "-- ya existe");
                }

            }

            // save properties to project root folder
            output = new FileOutputStream("config.properties");
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void editPropertie(String propertie, String value) {
        try {
            input = new FileInputStream("config.properties");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        try {
            /*
            Lee archivo config, edita la propiedad recibida por parametro
            y guarda el archivo config nuevamente
             */
            this.prop.load(input);
            this.prop.setProperty(propertie, value);
            output = new FileOutputStream("config.properties");
            this.prop.store(output, null);

        } catch (IOException ex) {
            Logger.getLogger(Config.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public String[] GetProperties(String[] properties) {
        String[] values = new String[properties.length];
        try {

            input = new FileInputStream("config.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        try {

            this.prop.load(input);

            for (int i = 0; i < values.length; i++) {
                values[i] = this.prop.getProperty(properties[i]);
            }

        } catch (IOException ex) {
            Logger.getLogger(Config.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();

            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return values;
    }

    public String GetPropertie(String propertie) {
        String value = null;
        try {

            input = new FileInputStream("config.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        try {

            this.prop.load(input);

            value = this.prop.getProperty(propertie);

        } catch (IOException ex) {
            Logger.getLogger(Config.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();

            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return value;
    }

}
