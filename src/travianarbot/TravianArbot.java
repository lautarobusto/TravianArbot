package travianarbot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import travianarbot.gui.TravianArbotGui;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;
import travianarbot.dao.DAOException;
import travianarbot.dao.sqlite.SQLiteManagerDAO;
import travianarbot.modelo.Aldea;
import travianarbot.modelo.Armada;
import travianarbot.modelo.Cuenta;
import travianarbot.modelo.InformeVaca;
import travianarbot.modelo.Vaca;

public class TravianArbot {

    private static SQLiteManagerDAO manager;
    private static int[] currentTroops = new int[11];

    public static void main(String[] args) throws SQLException, DAOException {
        //Inicializo archivo config
        Config con = new Config();
        con.inicializar();
//        JFrame.setDefaultLookAndFeelDecorated(true);
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    UIManager.setLookAndFeel(new SubstanceGraphiteLookAndFeel());
//                } catch (Exception e) {
//                    System.out.println("Substance Graphite failed to initialize");
//                }
//
//                try {
//                    TravianArbotGui frm;
//                    frm = new TravianArbotGui();
//                    frm.setVisible(true);
//                } catch (SQLException ex) {
//                    Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (DAOException ex) {
//                    Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        });

        TravianArbotGui frm;
        frm = new TravianArbotGui();
        frm.setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc=" ${InicializarWebBrowser} ">
    public static void InicializarWebBrowser(WebDriver driver, Cuenta cuenta) {

        driver.get(cuenta.getServidor());
        Login login = new Login(driver);
        login.xLogin(cuenta);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("heroImageButton")));
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" ${getCurrentTroops} ">
    public static void getCurrentTroops(WebDriver driver) {
        Config config = new Config();

        driver.get(config.GetPropertie("Server") + "/build.php?gid=16&tt=1&filter=3");

        WebElement tableTroops = driver.findElement(By.xpath("//*[@id=\"build\"]/div[4]/table[1]/tbody[2]/tr"));

        List<WebElement> tds = tableTroops.findElements(By.tagName("td"));

        for (int i = 0; i < tds.size(); i++) {
            currentTroops[i] = Integer.valueOf(tds.get(i).getText());
        }

    }
    // <editor-fold defaultstate="collapsed" desc=" ${ObtenerAldeasLookNFeel} ">
//    public static void getAldeas(WebDriver driver) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    Aldea aldea = new Aldea();
//                    List<Aldea> aldeasDetectadas = new ArrayList<>();
//                    List<WebElement> aldeasMetadata = new ArrayList<>();//Contiene WebElements <a> que contiene ID nombre y coords
//                    List<WebElement> li = new ArrayList<>();
//                    manager = new SQLiteManagerDAO();
//                    List<Aldea> aldeasAlmacenadas = manager.getAldeaDAO().obtenerTodos();
//
//                    Config config = new Config();
//                    driver.get(config.GetPropertie("Server") + "/dorf1.php");
//
//                    //Armado de Lista AldeasDetectadas sin Terreno
//                    //----------------------------------------------------------------------------------------------------------------------
//                    li = driver.findElement(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul")).findElements(By.tagName("li"));
//                    for (WebElement element : li) {
//                        aldeasMetadata.add(element.findElement(By.tagName("a")));
//                    }
//                    for (WebElement e : aldeasMetadata) {
//                        //Obtengo Id de la aldea
//                        aldea = new Aldea();
//                        String[] split = e.getAttribute("href").split("=");
//                        aldea.setId_aldea(Integer.valueOf(split[1].replaceAll("[^0-9]", "")));
//                        //Nombre de aldea
//                        aldea.setNombre_aldea(e.findElement(By.tagName("div")).getText());
//                        //Coordenadas de aldea
//                        Object[] coords = e.findElement(By.tagName("span")).findElements(By.tagName("span")).toArray();
//                        aldea.setCoordenada_x(Integer.valueOf(((WebElement) coords[0]).getText().replaceAll("[^0-9-]", "")));
//                        aldea.setCoordenada_y(Integer.valueOf(((WebElement) coords[2]).getText().replaceAll("[^0-9-]", "")));
//                        aldeasDetectadas.add(aldea);
//                    }
//                    //FinalArmado de Lista AldeasDetectadas sin Terreno
//                    //----------------------------------------------------------------------------------------------------------------------
//                    Collections.sort(aldeasDetectadas, new Aldea.CompId());
//                    Collections.sort(aldeasAlmacenadas, new Aldea.CompId());
//
//                    if (!aldeasAlmacenadas.equals(aldeasDetectadas)) {
//                        int resp = JOptionPane.showConfirmDialog(null, "Hay cambios en la lista de aldeas. Desea actualizarla?");
//                        if (JOptionPane.OK_OPTION == resp) {
//                            for (Aldea a : aldeasDetectadas) {
//                                if (!aldeasAlmacenadas.contains(a)) {
//                                    aldeasAlmacenadas.add(a);
//                                }
//                            }
//                            List<Aldea> aux = new ArrayList<>(aldeasAlmacenadas);
//                            for (Aldea a : aux) {
//                                if (!aldeasDetectadas.contains(a)) {
//                                    aldeasAlmacenadas.remove(a);
//                                }
//                            }
//                            //Obtencion de terreno de Aldeas nuevas y Seteo de Id Cuenta
//                            //----------------------------------------------------------------------------------------------------------------------
//                            for (Aldea e : aldeasAlmacenadas) {
//                                Cuenta cuenta = new Cuenta();
//
//                                if (e.getTipo_terreno() == null) {
//                                    try {
//
//                                        cuenta = manager.getCuentaDAO().obtener(config.GetPropertie("Server"));
//
//                                    } catch (DAOException ex) {
//                                        Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                    driver.get(config.GetPropertie("Server") + "/dorf1.php?newdid=" + e.getId_aldea() + "&");
//
//                                    Object[] farms = driver.findElement(By.xpath("//*[@id=\"village_map\"]")).findElements(By.tagName("div")).toArray();
//                                    int a = 0;
//                                    int[] builds = new int[18];
//                                    for (int i = 0; i < farms.length; i += 2) {
//                                        String[] srt = ((WebElement) farms[i]).getAttribute("class").split(" ");
//                                        if (srt.length == 6) {
//                                            builds[a] = Integer.parseInt(srt[4].replaceAll("[^0-9]", ""));
//                                            a++;
//                                        } else {
//                                            builds[a] = Integer.parseInt(srt[3].replaceAll("[^0-9]", ""));
//                                            a++;
//                                        }
//                                    }
//                                    int[] terreno = new int[4];
//                                    for (int i = 0; i < 18; i++) {
//                                        if (builds[i] == 1) {
//                                            terreno[0]++;
//                                        }
//                                        if (builds[i] == 2) {
//                                            terreno[1]++;
//                                        }
//                                        if (builds[i] == 3) {
//                                            terreno[2]++;
//                                        }
//                                        if (builds[i] == 4) {
//                                            terreno[3]++;
//                                        }
//
//                                    }
//                                    String str = "";
//                                    for (int i = 0; i < terreno.length; i++) {
//                                        str += String.valueOf(terreno[i]);
//                                    }
//                                    e.setTipo_terreno(str);
//                                    e.setId_cuenta(cuenta.getId());
//                                    e.setZ(320801 + e.getCoordenada_x() - (e.getCoordenada_y() * 801));
//
//                                }
//
//                            }
//                            //Fin Obtencion de terreno de Aldeas nuevas
//                            //----------------------------------------------------------------------------------------------------------------------
//
//                        } else {
//                            JOptionPane.showMessageDialog(null, "Recuerde que es necesario matener la lista de aldeas actualizada.\n"
//                                    + "Puede actualizarla manualmente en el menu Herramientas o se le volvera\n"
//                                    + "a consulta en el proximo inicio");
//                        }
//
//                    }
//                    manager.getAldeaDAO().insertarBatch(aldeasAlmacenadas);
//                    manager.closeConection();
//                } catch (DAOException ex) {
//                    Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (SQLException ex) {
//                    Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        });
//
//    }
// </editor-fold>

    public static void getAldeas(WebDriver driver) {
        try {
            Aldea aldea = new Aldea();
            List<Aldea> aldeasDetectadas = new ArrayList<>();
            List<WebElement> aldeasMetadata = new ArrayList<>();//Contiene WebElements <a> que contiene ID nombre y coords
            List<WebElement> li = new ArrayList<>();
            manager = new SQLiteManagerDAO();
            List<Aldea> aldeasAlmacenadas = manager.getAldeaDAO().obtenerTodos();

            Config config = new Config();
            driver.get(config.GetPropertie("Server") + "/dorf1.php");

            //Armado de Lista AldeasDetectadas sin Terreno
            //----------------------------------------------------------------------------------------------------------------------
            li = driver.findElement(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul")).findElements(By.tagName("li"));
            for (WebElement element : li) {
                aldeasMetadata.add(element.findElement(By.tagName("a")));
            }
            for (WebElement e : aldeasMetadata) {
                //Obtengo Id de la aldea
                aldea = new Aldea();
                String[] split = e.getAttribute("href").split("=");
                aldea.setId_aldea(Integer.valueOf(split[1].replaceAll("[^0-9]", "")));
                //Nombre de aldea
                aldea.setNombre_aldea(e.findElement(By.tagName("div")).getText());
                //Coordenadas de aldea
                Object[] coords = e.findElement(By.tagName("span")).findElements(By.tagName("span")).toArray();
                aldea.setCoordenada_x(Integer.valueOf(((WebElement) coords[0]).getText().replaceAll("[^0-9-]", "")));
                aldea.setCoordenada_y(Integer.valueOf(((WebElement) coords[2]).getText().replaceAll("[^0-9-]", "")));
                aldeasDetectadas.add(aldea);
            }
            //FinalArmado de Lista AldeasDetectadas sin Terreno
            //----------------------------------------------------------------------------------------------------------------------
            Collections.sort(aldeasDetectadas, new Aldea.CompId());
            Collections.sort(aldeasAlmacenadas, new Aldea.CompId());

            if (!aldeasAlmacenadas.equals(aldeasDetectadas)) {
                int resp = JOptionPane.showConfirmDialog(null, "Hay cambios en la lista de aldeas. Desea actualizarla?");
                if (JOptionPane.OK_OPTION == resp) {
                    for (Aldea a : aldeasDetectadas) {
                        if (!aldeasAlmacenadas.contains(a)) {
                            aldeasAlmacenadas.add(a);
                        }
                    }
                    List<Aldea> aux = new ArrayList<>(aldeasAlmacenadas);
                    for (Aldea a : aux) {
                        if (!aldeasDetectadas.contains(a)) {
                            aldeasAlmacenadas.remove(a);
                        }
                    }
                    //Obtencion de terreno de Aldeas nuevas y Seteo de Id Cuenta
                    //----------------------------------------------------------------------------------------------------------------------
                    for (Aldea e : aldeasAlmacenadas) {
                        Cuenta cuenta = new Cuenta();

                        if (e.getTipo_terreno() == null) {
                            try {

                                cuenta = manager.getCuentaDAO().obtener(config.GetPropertie("Server"));

                            } catch (DAOException ex) {
                                Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            driver.get(config.GetPropertie("Server") + "/dorf1.php?newdid=" + e.getId_aldea() + "&");

                            Object[] farms = driver.findElement(By.xpath("//*[@id=\"village_map\"]")).findElements(By.tagName("div")).toArray();
                            int a = 0;
                            int[] builds = new int[18];
                            for (int i = 0; i < farms.length; i += 2) {
                                String[] srt = ((WebElement) farms[i]).getAttribute("class").split(" ");
                                if (srt.length == 6) {
                                    builds[a] = Integer.parseInt(srt[4].replaceAll("[^0-9]", ""));
                                    a++;
                                } else {
                                    builds[a] = Integer.parseInt(srt[3].replaceAll("[^0-9]", ""));
                                    a++;
                                }
                            }
                            int[] terreno = new int[4];
                            for (int i = 0; i < 18; i++) {
                                if (builds[i] == 1) {
                                    terreno[0]++;
                                }
                                if (builds[i] == 2) {
                                    terreno[1]++;
                                }
                                if (builds[i] == 3) {
                                    terreno[2]++;
                                }
                                if (builds[i] == 4) {
                                    terreno[3]++;
                                }

                            }
                            String str = "";
                            for (int i = 0; i < terreno.length; i++) {
                                str += String.valueOf(terreno[i]);
                            }
                            e.setTipo_terreno(str);
                            e.setId_cuenta(cuenta.getId());
                            e.setZ(320801 + e.getCoordenada_x() - (e.getCoordenada_y() * 801));

                        }

                    }
                    //Fin Obtencion de terreno de Aldeas nuevas
                    //----------------------------------------------------------------------------------------------------------------------

                    manager.getAldeaDAO().insertarBatch(aldeasAlmacenadas);
                    manager.closeConection();
                } else {
                    JOptionPane.showMessageDialog(null, "Recuerde que es necesario matener la lista de aldeas actualizada.\n"
                            + "Puede actualizarla manualmente en el menu Herramientas o se le volvera\n"
                            + "a consulta en el proximo inicio");
                }

            }

        } catch (DAOException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc=" ${getAldeaActica} ">
    public static int getAldeaActica(WebDriver driver) {
        Config config = new Config();
        int id = 0;
        Object[] aldeas = driver.findElement(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul")).findElements(By.tagName("li")).toArray();
        for (Object i : aldeas) {

            if (((WebElement) i).getAttribute("class").equals(" active")) {
                String[] split = ((WebElement) i).findElement(By.tagName("a")).getAttribute("href").split("=");
                id = Integer.valueOf(split[1].replaceAll("[^0-9]", ""));

            }
        }
        return id;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" ${enviarVaca} ">
    public static List<Vaca> enviarVaca(WebDriver driver, List<Vaca> vacasActivas, boolean continuo) {
        if (vacasActivas.isEmpty()) {
            return vacasActivas;
        }
        Armada armada = null;
        Config config = new Config();
        boolean hayTropas = true;
        List<Vaca> vacasActivasN = new ArrayList<>(vacasActivas);

        if (getAldeaActica(driver) != vacasActivas.get(0).getId_aldea_origen()) {
            driver.get(config.GetPropertie("Server") + "/dorf1.php?newdid=" + vacasActivas.get(0).getId_aldea_origen() + "&");

        }
        getCurrentTroops(driver);

        if (continuo) {
            while (hayTropas) {
                if (vacasActivasN.isEmpty()) {
                    break;
                }
                envioVacaGenerico(driver, vacasActivasN, armada, config, hayTropas);
            }
        } else {
            for (int s = 0; s < vacasActivasN.size(); s++) {
                if (vacasActivasN.isEmpty()) {
                    break;
                }
                envioVacaGenerico(driver, vacasActivasN, armada, config, hayTropas);
            }

        }

        driver.get(config.GetPropertie("Server") + "/dorf1.php");

        return vacasActivasN;

    }
    // </editor-fold>

    private static void envioVacaGenerico(WebDriver driver, List<Vaca> vacasActivasN, Armada armada, Config config, Boolean hayTropas) {

        try {
            manager = new SQLiteManagerDAO();
            armada = manager.getArmadaDAO().obtener(vacasActivasN.get(0).getId_armada());

            if (getAldeaActica(driver) != vacasActivasN.get(0).getId_aldea_origen()) {
                driver.get(config.GetPropertie("Server") + "/dorf1.php?newdid=" + vacasActivasN.get(0).getId_aldea_origen() + "&");
                getCurrentTroops(driver);
            }
            for (int i = 0; i < 11; i++) {
                if (armada.toArray()[i] > currentTroops[i]) {
                    hayTropas = false;
                }
            }
            if (hayTropas) {

                driver.get(config.GetPropertie("Server") + "/build.php?tt=2&id=39");
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[1]/td[1]/input ")).sendKeys(String.valueOf(armada.toArray()[0]));
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[2]/td[1]/input ")).sendKeys(String.valueOf(armada.toArray()[1]));
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[3]/td[1]/input ")).sendKeys(String.valueOf(armada.toArray()[2]));
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[1]/td[2]/input ")).sendKeys(String.valueOf(armada.toArray()[3]));
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[2]/td[2]/input ")).sendKeys(String.valueOf(armada.toArray()[4]));
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[3]/td[2]/input ")).sendKeys(String.valueOf(armada.toArray()[5]));
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[1]/td[3]/input ")).sendKeys(String.valueOf(armada.toArray()[6]));
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[2]/td[3]/input ")).sendKeys(String.valueOf(armada.toArray()[7]));
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[1]/td[4]/input ")).sendKeys(String.valueOf(armada.toArray()[8]));
                driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[2]/td[4]/input ")).sendKeys(String.valueOf(armada.toArray()[9]));
                if (!driver.findElements(By.xpath("//*[@id=\"troops\"]/tbody/tr[3]/td[4]/input")).isEmpty()) {
                    driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody/tr[3]/td[4]/input ")).sendKeys(String.valueOf(armada.toArray()[10]));
                }
                driver.findElement(By.xpath("//*[@id=\"xCoordInput\"]")).sendKeys(String.valueOf(vacasActivasN.get(0).getCoordenada_x()));
                driver.findElement(By.xpath("//*[@id=\"yCoordInput\"]")).sendKeys(String.valueOf(vacasActivasN.get(0).getCoordenada_y()));

                switch (vacasActivasN.get(0).getId_movimiento()) {
                    case "Refuerzo":
                        driver.findElement(By.xpath("//*[@id=\"build\"]/div[2]/form/div[2]/label[1]/input")).click();
                        break;
                    case "Ataque":
                        driver.findElement(By.xpath("//*[@id=\"build\"]/div[2]/form/div[2]/label[2]/input")).click();
                        break;
                    case "Asalto":
                        driver.findElement(By.xpath("//*[@id=\"build\"]/div[2]/form/div[2]/label[3]/input")).click();
                        break;

                }
                driver.findElement(By.xpath("//*[@id=\"btn_ok\"]/div/div[2]")).click();//boton enviar
                //primera advertencia Aldea propia y Aliado primer tag. No hay aldea segundo Tag.

                WebElement classContainer = driver.findElement(By.className("contentContainer"));
                boolean alert = classContainer.findElements(By.className("alert")).isEmpty();
                boolean error = classContainer.findElements(By.className("error")).isEmpty();
                if (classContainer.findElements(By.className("alert")).isEmpty()
                        && classContainer.findElements(By.className("error")).isEmpty()) {
                    //No hay advertencia de Ataque aliado, propio, o destino vacio

                    driver.findElement(By.xpath("//*[@id=\"btn_ok\"]/div/div[2]")).click();
                    Vaca v = vacasActivasN.get(0);
                    vacasActivasN.remove(vacasActivasN.get(0));
                    vacasActivasN.add(v);

                    for (int i = 0; i < 11; i++) {
                        currentTroops[i] = currentTroops[i] - armada.toArray()[i];
                    }

                } else {
                    //Hay advertencia de Ataque aliado, propio, o destino vacio
                    manager.getVacaDAO().eliminar(vacasActivasN.get(0));
                    vacasActivasN.remove(vacasActivasN.get(0));

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc=" ${getInformesOfensivos} ">
    public static void getInformesVacas(WebDriver driver, List<Vaca> vacas) {
        Config config = new Config();
        try {
            manager = new SQLiteManagerDAO();
        } catch (SQLException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        }
        InformeVaca informe = new InformeVaca();
        Vaca vaca = new Vaca();
        Object[] tropas = null, perdidas = null, prisioneros = null;
        int tropasInt, perdidasInt, prisionerosInt;
        driver.get(config.GetPropertie("Server") + "/berichte.php?t=1&opt=AAABAAIAAwA=");

        for (int i = 0; i < 1; i++) {
            driver.findElement(By.xpath("//*[@id=\"overview\"]/tbody/tr[1]/td[2]/div[1]/a")).click();
            for (int j = 1; j <= 10; j++) {
                //Primero determino si el informe pertenece a una vaca de mi lista y que exista
                tropasInt = 0;
                perdidasInt = 0;
                prisionerosInt = 0;
                if (!driver.findElements(By.xpath("//*[@id=\"message\"]/table[2]/thead/tr/td[2]/p/a[2]")).isEmpty()) {
                    vaca.setZ(Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"message\"]/table[2]/thead/tr/td[2]/p/a[2]")).getAttribute("href").split("=")[1]));
                    if (vacas.contains(vaca)) {

                        informe.setId(driver.getCurrentUrl().split("=")[1]);
                        informe.setAsunto(driver.findElement(By.xpath("//*[@id=\"subject\"]/div[2]")).getText());
                        informe.setFecha(driver.findElement(By.xpath("//*[@id=\"time\"]/div[2]")).getText().split(",")[0]);
                        informe.setHora(driver.findElement(By.xpath("//*[@id=\"time\"]/div[2]")).getText().split(",")[1]);
                        informe.setZ_aldea_atacante(Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"attacker\"]/thead/tr/td[2]/p/a[2]")).getAttribute("href").split("=")[1]));
                        informe.setZ_aldea_defensora(Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"message\"]/table[2]/thead/tr/td[2]/p/a[2]")).getAttribute("href").split("=")[1]));

                        //obtengo tropas, perdidas y si hay prisioneros.
                        tropas = driver.findElement(By.xpath("//*[@id=\"attacker\"]/tbody[2]/tr")).findElements(By.tagName("td")).toArray();
                        //tropas = driver.findElements(By.className("units")).get(1).findElements(By.tagName("td")).toArray();

                        perdidas = driver.findElement(By.xpath("//*[@id=\"attacker\"]/tbody[3]/tr")).findElements(By.tagName("td")).toArray();
                        //perdidas = driver.findElements(By.className("units last")).get(0).findElements(By.tagName("td")).toArray();
                        if (!driver.findElements(By.xpath("//*[@id=\"attacker\"]/tbody[4]/tr")).isEmpty()) {
                            prisioneros = driver.findElement(By.xpath("//*[@id=\"attacker\"]/tbody[4]/tr")).findElements(By.tagName("td")).toArray();
                        }
                        for (int k = 0; k < tropas.length; k++) {
                            tropasInt += Integer.parseInt(((WebElement) tropas[k]).getText());
                            perdidasInt += Integer.parseInt(((WebElement) perdidas[k]).getText());
                            if (prisioneros.length != 1) {
                                prisionerosInt += Integer.parseInt(((WebElement) prisioneros[k]).getText());
                            }
                        }
                        informe.setPerdidas((100 * (perdidasInt + prisionerosInt)) / tropasInt);

                        //obtengo eficiencia si aplica sino es cero
                        //si hay botin  hay clase "goods" sino hay goods no hay botin y la eficiencia es cero
                        if (!driver.findElements(By.className("goods")).isEmpty()) {

                            int posible = Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"attacker\"]/tbody[5]/tr/td/div[3]")).getText().split("/")[1].replaceAll("[^0-9]", ""));
                            int obtenido = Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"attacker\"]/tbody[5]/tr/td/div[3]")).getText().split("/")[0].replaceAll("[^0-9]", ""));
                            informe.setEficiencia((obtenido * 100) / posible);
                        } else {
                            informe.setEficiencia(0);

                        }

                        try {
                            manager.getInformeVacaDAO().insertar(informe);
                        } catch (DAOException ex) {
                            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                //*[@id="content"]/div[2]/a[1]
                driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/a[1]")).click();
            }
            driver.get(config.GetPropertie("Server") + "/berichte.php?t=1");

//            for (int l = 1; l <= 10; l++) {
//                if (driver.findElement(By.xpath("//*[@id=\"overview\"]/tbody/tr[" + i + "]/td[2]/a[1]/img")).getAttribute("class").equals("messageStatus messageStatusRead")) {
//                    driver.findElement(By.xpath("//*[@id=\"overview\"]/tbody/tr[" + i + "]/td[1]/input")).click();
//                }
//            }
//            //*[@id="reportsForm"]/div[2]/div[2]/a[3]
//            driver.findElement(By.xpath("//*[@id=\"del\"]/div/div[2]")).click();
//            //driver.findElement(By.xpath("//*[@id=\"reportsForm\"]/div[1]/div[2]/a[3]/img")).click();
        }

    }
    // </editor-fold>

    public static void getEficienciaVaca(WebDriver driver, Vaca v) {

    }

}
