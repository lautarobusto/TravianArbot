package travianarbot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import travianarbot.gui.TravianArbotGui;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import travianarbot.dao.DAOException;
import travianarbot.dao.sqlite.SQLiteManagerDAO;
import travianarbot.modelo.Aldea;
import travianarbot.modelo.Armada;
import travianarbot.modelo.Cuenta;
import travianarbot.modelo.InformeOfensivo;
import travianarbot.modelo.Vaca;

public class TravianArbot {

    private static SQLiteManagerDAO manager;
    private static int[] currentTroops = new int[11];

    public static void main(String[] args) throws SQLException, DAOException {
        //Inicializo archivo config
        Config con = new Config();
        con.inicializar();

        try {
            UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        }
        TravianArbotGui frm = new TravianArbotGui();
        frm.setVisible(true);
    }

    public static void InicializarWebBrowser(WebDriver driver, Cuenta cuenta) {

        driver.get(cuenta.getServidor_cuenta());
        Login login = new Login(driver);
        login.xLogin(cuenta);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("heroImageButton")));
    }

    public static void getCurrentTroops(WebDriver driver) {
        Config config = new Config();
        driver.get(config.GetPropertie("Server") + "/dorf1.php");
        WebElement tableTroops = driver.findElement(By.xpath("//*[@id=\"troops\"]/tbody"));
        List<WebElement> trs = tableTroops.findElements(By.tagName("tr"));
        for (WebElement item : trs) {
            Object[] tds = item.findElements(By.tagName("td")).toArray();
            String[] str = ((WebElement) tds[0]).findElement(By.tagName("img")).getAttribute("Class").split(" ");
            str[1] = str[1].replaceAll("[^0-9]", "");
            if (str[1].equals("")) {
                currentTroops[10] = 1;

            } else {
                currentTroops[Integer.valueOf(str[1]) - 1] = Integer.parseInt(((WebElement) tds[1]).getText());

            }

        }

    }

    public static void getAldeas(WebDriver driver) {
        Config config = new Config();
        String[] prop = {"Server"};
        String url = config.GetProperties(prop)[0] + "/dorf1.php";
        driver.get(url);
        Aldea aldea = new Aldea();
        List<Aldea> aldeas = new ArrayList<>();
        List<WebElement> aldeasMetadata = new ArrayList<>();//Contiene WebElements <a> que contiene ID nombre y coords
        List<WebElement> li = new ArrayList<>();
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

            aldeas.add(aldea);
        }

        //obtengo tipo terreno
        for (Aldea e : aldeas) {
            Cuenta cuenta = new Cuenta();
            try {
                manager = new SQLiteManagerDAO();
                cuenta = manager.getCuentaDAO().obtener(config.GetProperties(prop)[0]);
                manager.closeConection();
            } catch (SQLException ex) {
                Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DAOException ex) {
                Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
            }
            driver.get(url + "?newdid=" + e.getId_aldea() + "&");

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
            e.setId_cuenta(cuenta.getId_cuenta());

        }
        try {
            manager = new SQLiteManagerDAO();
            manager.getAldeaDAO().insertarBatch(aldeas);
            manager.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DAOException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static int getAldeaActica(WebDriver driver) {
        Config config = new Config();
        int id = 0;

//        if (!driver.getCurrentUrl().equals(config.GetPropertie("Server") + "/dorf1.php")) {
//            driver.get(config.GetPropertie("Server") + "/dorf1.php");
//        }
        Object[] aldeas = driver.findElement(By.xpath("//*[@id=\"sidebarBoxVillagelist\"]/div[2]/div[2]/ul")).findElements(By.tagName("li")).toArray();
        for (Object i : aldeas) {

            if (((WebElement) i).getAttribute("class").equals(" active")) {
                String[] split = ((WebElement) i).findElement(By.tagName("a")).getAttribute("href").split("=");
                id = Integer.valueOf(split[1].replaceAll("[^0-9]", ""));

            }
        }
        return id;
    }

    public static List<Vaca> enviarVaca(WebDriver driver, List<Vaca> vacasActivas, boolean continuo) {
        if (vacasActivas.isEmpty()) {
            return vacasActivas;
        }
        Armada armada = null;
        Config config = new Config();
        boolean seguir = true;
        List<Vaca> vacasActivasN = new ArrayList<>(vacasActivas);

        if (getAldeaActica(driver) != vacasActivas.get(0).getId_aldea()) {
            driver.get(config.GetPropertie("Server") + "/dorf1.php?newdid=" + vacasActivas.get(0).getId_aldea() + "&");

        }
        getCurrentTroops(driver);

        if (!continuo) {
            for (Vaca v : vacasActivas) {
                try {
                    manager = new SQLiteManagerDAO();
                    armada = manager.getArmadaDAO().obtener(v.getId_armada());
                } catch (SQLException ex) {
                    Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DAOException ex) {
                    Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (getAldeaActica(driver) != v.getId_aldea()) {
                    driver.get(config.GetPropertie("Server") + "/dorf1.php?newdid=" + v.getId_aldea() + "&");
                    getCurrentTroops(driver);
                }
                for (int i = 0; i < armada.toArray().length; i++) {
                    if (armada.toArray()[i] > currentTroops[i]) {
                        seguir = false;
                    }
                }
                if (seguir) {

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

                    driver.findElement(By.xpath("//*[@id=\"xCoordInput\"]")).sendKeys(String.valueOf(v.getCoordenada_x()));
                    driver.findElement(By.xpath("//*[@id=\"yCoordInput\"]")).sendKeys(String.valueOf(v.getCoordenada_y()));

                    switch (v.getId_movimiento()) {
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
                    driver.findElement(By.xpath("//*[@id=\"btn_ok\"]/div/div[2]")).click();
                    if (!driver.findElements(By.xpath("//*[@id=\"build\"]/div[2]/p")).isEmpty()) {
                        try {
                            manager.getVacaDAO().eliminar(v);
                        } catch (DAOException ex) {
                            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        if (driver.findElements(By.xpath("//*[@id=\"build\"]/div[2]/form/div[1]")).isEmpty()) {

                            driver.findElement(By.xpath("//*[@id=\"btn_ok\"]/div/div[2]")).click();
                            vacasActivasN.remove(v);
                            vacasActivasN.add(v);

                        } else {
                            try {
                                //Elimina la vaca si al momento de enviarla tira advertencia, aliada o propia
                                manager.getVacaDAO().eliminar(v);

                            } catch (DAOException ex) {
                                Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    for (int i = 0; i < armada.toArray().length; i++) {
                        currentTroops[i] = currentTroops[i] - armada.toArray()[i];
                    }
                }
            }
        } else {
            while (seguir) {

                try {
                    manager = new SQLiteManagerDAO();
                    armada = manager.getArmadaDAO().obtener(vacasActivasN.get(0).getId_armada());
                } catch (SQLException ex) {
                    Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DAOException ex) {
                    Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (getAldeaActica(driver) != vacasActivasN.get(0).getId_aldea()) {
                    driver.get(config.GetPropertie("Server") + "/dorf1.php?newdid=" + vacasActivasN.get(0).getId_aldea() + "&");
                    getCurrentTroops(driver);
                }
                for (int i = 0; i < armada.toArray().length; i++) {
                    if (armada.toArray()[i] > currentTroops[i]) {
                        seguir = false;
                    }
                }
                if (seguir) {

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
                    driver.findElement(By.xpath("//*[@id=\"btn_ok\"]/div/div[2]")).click();
                    if (!driver.findElements(By.xpath("//*[@id=\"build\"]/div[2]/p")).isEmpty()) {
                        try {
                            manager.getVacaDAO().eliminar(vacasActivasN.get(0));
                            vacasActivasN.remove(vacasActivasN.get(0));
                            if (vacasActivasN.isEmpty()) {
                                break;
                            }

                        } catch (DAOException ex) {
                            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ////*[@id="build"]/div[2]/form/div[1]
                    } else {
                        if (!driver.findElements(By.xpath("//*[@id=\"build\"]/div[2]/form/div[1]")).isEmpty()) {
                            System.out.println("No hay advertencia");
                            driver.findElement(By.xpath("//*[@id=\"btn_ok\"]/div/div[2]")).click();
                            Vaca v = vacasActivasN.get(0);
                            vacasActivasN.remove(vacasActivasN.get(0));
                            vacasActivasN.add(v);

                        } else {
                            System.out.println("Hay advertencia");
                            try {
                                //Elimina la vaca si al momento de enviarla tira advertencia, aliada o propia
                                manager.getVacaDAO().eliminar(vacasActivasN.get(0));
                                vacasActivasN.remove(vacasActivasN.get(0));
                                if (vacasActivasN.isEmpty()) {
                                    break;
                                }

                            } catch (DAOException ex) {
                                Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    for (int i = 0; i < armada.toArray().length; i++) {
                        currentTroops[i] = currentTroops[i] - armada.toArray()[i];
                    }
                }
            }
        }

        driver.get(config.GetPropertie("Server") + "/dorf1.php");

        return vacasActivasN;

    }

    public static void getEficienciaVaca(WebDriver driver, Vaca v) {

    }

    public static void getInformesOfensivos(WebDriver driver) {
        Config config = new Config();
        InformeOfensivo informe = new InformeOfensivo();

        //primero navego hasta los informes
        driver.get(config.GetPropertie("Server") + "/berichte.php?t=1&opt=AAABAAIAAwA=");
        driver.findElement(By.xpath("//*[@id=\"overview\"]/tbody/tr[1]/td[2]/div[1]/a")).click();
        informe.setId_informe(driver.getCurrentUrl().split("=")[1]);
        informe.setAsunto_informe(driver.findElement(By.xpath("//*[@id=\"subject\"]/div[2]")).getText());
        informe.setFecha_Informe(driver.findElement(By.xpath("//*[@id=\"time\"]/div[2]")).getText().split(",")[0]);
        informe.setHora_informe(driver.findElement(By.xpath("//*[@id=\"time\"]/div[2]")).getText().split(",")[1]);
        informe.setBotin_posible(Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"attacker\"]/tbody[5]/tr/td/div[3]")).getText().split("/")[1]));
        informe.setBotin_real(Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"attacker\"]/tbody[5]/tr/td/div[3]")).getText().split("/")[0]));
        informe.setId_aldea_atacante(Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"attacker\"]/thead/tr/td[2]/p/a[2]")).getAttribute("href").split("=")[1]));
        informe.setId_vaca_defensora(Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"message\"]/table[2]/thead/tr/td[2]/p/a[2]")).getAttribute("href").split("=")[1]));
        informe.setEficiencia((informe.getBotin_real() * 100) / informe.getBotin_posible());

        try {
            manager = new SQLiteManagerDAO();
            manager.getInformeOfensivoDAO().insertar(informe);
        } catch (DAOException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TravianArbot.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
