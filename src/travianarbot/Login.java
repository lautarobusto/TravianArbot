/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travianarbot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import travianarbot.modelo.Cuenta;

public class Login {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"content\"]/div[1]/div[1]/form/table/tbody/tr[1]/td[2]/input")
    //@FindBy(name = "name")
    WebElement username;

    @FindBy(xpath = "//*[@id=\"content\"]/div[1]/div[1]/form/table/tbody/tr[2]/td[2]/input")
    //@FindBy(name = "password")
    WebElement password;

    @FindBy(xpath = "//*[@id=\"lowRes\"]")
    //*[@id="lowRes"]
    WebElement check;

    @FindBy(xpath = "//*[@id=\"s1\"]/div/div[2]")
    //@FindBy(name = "s1")
    WebElement button;

    public void xLogin(Cuenta cuenta) {
        username.clear();
        username.sendKeys(cuenta.getNombre());
        password.clear();
        password.sendKeys(cuenta.getContraseña());
        if (Boolean.valueOf(cuenta.getLowRes())) {
            check.click();
        }
        button.submit();

    }

    public void Login() {
        username.clear();
        username.sendKeys("xanatos");
        password.clear();
        password.sendKeys("36191727");
        check.click();
        button.submit();

    }

    public Login(WebDriver driver) {
        //initialize elements
        PageFactory.initElements(driver, this);

    }

    public void set_username(String usern) {
        username.clear();
        username.sendKeys(usern);
    }

    public void set_password(String userp) {
        password.clear();
        password.sendKeys(userp);
    }

    public void click_button() {
        button.submit();
    }

    public void login(String usern, String userp) {
        username.clear();
        username.sendKeys(usern);
        password.clear();
        password.sendKeys(userp);
        button.submit();

    }
}
