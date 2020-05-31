package com.online.hotel.arlear;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class UserTest {
	
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  
  @After
  public void tearDown() {
    driver.quit();
  }
  
  @Test
  
//Intenta crear un usuario sin ingresar apellido y arroja mensaje de error
  
  public void crearUsuarioSinApellido() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    {
      WebElement dropdown = driver.findElement(By.id("userType"));
      dropdown.findElement(By.xpath("//option[. = 'Contador']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("userType")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Maria Ester");
    driver.findElement(By.cssSelector("form")).click();
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("mmolina");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.cssSelector(".submitInput")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorMessage")));
    }
    assertThat(driver.findElement(By.cssSelector(".itemError")).getText(), is("-El campo apellido no puede estar vacio"));
  }
  
  @Test
  
//Intenta crear un usuario sin ingresar nombre y arroja mensaje de error
  
  public void crearUsuarioSinNombre() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    {
      WebElement dropdown = driver.findElement(By.id("userType"));
      dropdown.findElement(By.xpath("//option[. = 'Contador']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("userType")).click();
    driver.findElement(By.cssSelector("form")).click();
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Molina");
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("mmolina");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.cssSelector(".submitInput")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorMessage")));
    }
    assertThat(driver.findElement(By.cssSelector(".itemError")).getText(), is("-El campo nombre no puede estar vacio"));
  }
  
  @Test
  
//Intenta crear un usuario sin ingresar password y arroja error
  
  public void crearUsuarioSinPassword() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    {
      WebElement dropdown = driver.findElement(By.id("userType"));
      dropdown.findElement(By.xpath("//option[. = 'Contador']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("userType")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Maria Ester");
    driver.findElement(By.cssSelector("form")).click();
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Molina");
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("mmolina");
    driver.findElement(By.cssSelector(".submitInput")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorMessage")));
    }
    assertThat(driver.findElement(By.cssSelector(".itemError")).getText(), is("-Ingrese una contraseña numerica de 4 digitos"));
  }
  
  @Test
  
//Intenta crear un usuario sin ingresar username y arroja error
  
  public void crearUsuarioSinUserName() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    {
      WebElement dropdown = driver.findElement(By.id("userType"));
      dropdown.findElement(By.xpath("//option[. = 'Contador']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("userType")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Maria Ester");
    driver.findElement(By.cssSelector("form")).click();
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Molina");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.cssSelector(".submitInput")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorMessage")));
    }
    assertThat(driver.findElement(By.cssSelector(".itemError")).getText(), is("-Ingrese un nombre de usuario"));
  }
  
  @Test
  
//Intenta crear un usuario sin seleccionar tipo y arroja error
  
  public void crearUsuarioSinTipo() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 767));
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Oracio");
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Dominguez");
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("odominguez");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("2345");
    driver.findElement(By.cssSelector(".submitInput")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorMessage")));
    }
    assertThat(driver.findElement(By.cssSelector(".itemError")).getText(), is("-Seleccione un tipo de usuario"));
  }
    
  @Test
  
  //Intenta crear un usuario ya creado y arroja error
  
  public void ingresarUsuarioDuplicado() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    {
      WebElement dropdown = driver.findElement(By.id("userType"));
      dropdown.findElement(By.xpath("//option[. = 'Recepcionista']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("userType")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Daniela");
    driver.findElement(By.cssSelector("form")).click();
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Perez");
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("dperez");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.cssSelector(".submitInput")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.sucessMessage")));
    }
    assertThat(driver.findElement(By.cssSelector("div.sucessMessage")).getText(), is("La creación no se pudo hacer. El usuario: Daniela Perez ya se encuentra registrado."));
  }
  
  @Test
  
  //Intenta ingresar el apelido con numeros y arroja error
  
  public void ingresarUsuarioMalApellido() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    {
      WebElement dropdown = driver.findElement(By.id("userType"));
      dropdown.findElement(By.xpath("//option[. = 'Supervisor de hotel']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("userType")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Ezequiel");
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Z$m0ra");
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("ezamora");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.cssSelector(".submitInput")).click();
    assertThat(driver.findElement(By.cssSelector("div.sucessMessage")).getText(), is("[Formato incorrecto: Apellido debe ser de tipo String.]"));
  }
  
  @Test
  
  //Intenta ingresar el nombre con numeros y arroja error
  
  public void ingresarUsuarioMalNombre() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    {
      WebElement dropdown = driver.findElement(By.id("userType"));
      dropdown.findElement(By.xpath("//option[. = 'Supervisor de hotel']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("userType")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("E53qu13l");
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Zamora");
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("ezamora");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.cssSelector(".submitInput")).click();
    assertThat(driver.findElement(By.cssSelector("div.sucessMessage")).getText(), is("[Formato incorrecto: Nombre debe ser de tipo String.]"));
  }
  
  @Test
  
  //Intenta ingresar un password corto y arroja error
  
  public void ingresarUsuarioMalPassword2() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    {
      WebElement dropdown = driver.findElement(By.id("userType"));
      dropdown.findElement(By.xpath("//option[. = 'Supervisor de hotel']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("userType")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Ezequiel");
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Zamora");
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("ezamora");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("1");
    driver.findElement(By.cssSelector(".submitInput")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorMessage")));
    }
    assertThat(driver.findElement(By.cssSelector(".itemError")).getText(), is("-Ingrese una contraseña numerica de 4 digitos"));
  }
  
  @Test
  
  //Intenta ingresar una password con letrs y arroja error 
  
  public void ingresarUsuarioMalPassword() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    {
      WebElement dropdown = driver.findElement(By.id("userType"));
      dropdown.findElement(By.xpath("//option[. = 'Supervisor de hotel']")).click();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.id("userType"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }
    driver.findElement(By.id("userType")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Ezequiel");
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Zamora");
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("ezamora");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("hola");
    driver.findElement(By.cssSelector(".submitInput")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.errorMessage")));
    }
    assertThat(driver.findElement(By.cssSelector(".itemError")).getText(), is("-Ingrese una contraseña numerica de 4 digitos"));
  }
  
  @Test
  
  //cuando se quiere listar un usuario que no  existe por nombre
  
  public void listarNombreFallido() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    driver.findElement(By.id("findname")).click();
    driver.findElement(By.id("findname")).sendKeys("Roberto");
    driver.findElement(By.id("findUsers")).click();
    assertThat(driver.findElement(By.cssSelector("#usersList > p")).getText(), is("No se encontraron resultados"));
  }
  
  @Test
  
  //cuando se lista un usuario existente por nombre
  
  public void listarNombre() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1366, 712));
    driver.findElement(By.id("findname")).click();
    driver.findElement(By.id("findname")).sendKeys("Evelin");
    driver.findElement(By.id("findUsers")).click();
    assertThat(driver.findElement(By.cssSelector("#usersList > p")).getText(), is(not("No se encontraron resultados")));
  }
  
  @Test
  
  //cuando se quiere listar por tipo pero el seleccionado no tiene ningun usuario
  
  public void listarTipoFalido() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 767));
    driver.findElement(By.id("findUserType")).click();
    {
      WebElement dropdown = driver.findElement(By.id("findUserType"));
      dropdown.findElement(By.xpath("//option[. = 'Supervisor de salon de Eventos']")).click();
    }
    driver.findElement(By.id("findUserType")).click();
    assertThat(driver.findElement(By.cssSelector("#usersList > p")).getText(), is("No se encontraron resultados"));
  }
  
  @Test
  
  //cuando se quiere listar por categoria que tiene usuario existente
  
  public void listarTipo() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 767));
    driver.findElement(By.id("findUserType")).click();
    {
      WebElement dropdown = driver.findElement(By.id("findUserType"));
      dropdown.findElement(By.xpath("//option[. = 'Recepcionista']")).click();
    }
    driver.findElement(By.id("findUserType")).click();
    assertThat(driver.findElement(By.cssSelector("#usersList > p")).getText(), is(not("No se encontraron resultados")));
  }
  
  
  @Test
  
  //Intentar modificar con campo vacio
  
  public void modificarUsernameVacio() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 712));
    driver.findElement(By.cssSelector("#UserId178 > .fa-pen")).click();
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("\"\"");
    driver.findElement(By.cssSelector(".submitInput:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".sucessMessage")).getText(), is("[El campo Nombre de Usuario está vacio.]"));
  }
  
  
  @Test
  
  //Intentar exitosamente modificar por username
  
  public void modificarUsername() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 712));
    driver.findElement(By.cssSelector("#UserId178 > .fa-pen")).click();
    driver.findElement(By.id("userName")).click();
    driver.findElement(By.id("userName")).sendKeys("jmedina");
    driver.findElement(By.cssSelector(".submitInput:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".sucessMessage")).getText(), is("Se ha modificacado correctamente el usuario Juan."));
  }
  
  @Test
  
  //Intentar modificar dejando campo password vacio
  
  public void modificarPasswordVacio() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 712));
    driver.findElement(By.cssSelector("#UserId178 > .fa-pen")).click();
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("\"\"");
    driver.findElement(By.cssSelector(".submitInput:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".sucessMessage")).getText(), is("[El campo Password está vacio.]"));
  }
  
  
  @Test
  
  //Modificar el campo password exitosamente
  
  public void modificarPassword() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 712));
    driver.findElement(By.cssSelector("#UserId178 > .fa-pen")).click();
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.cssSelector(".submitInput:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".sucessMessage")).getText(), is(not("[Formato incorrecto: Password debe ser de tipo numerico.]")));
  }
  
  @Test
  
  //Intentar modificar con letras en campo password
  
  public void modificarPasswordFallido() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 712));
    driver.findElement(By.cssSelector("#UserId178 > .fa-pen")).click();
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("fguy");
    driver.findElement(By.cssSelector(".submitInput:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".sucessMessage")).getText(), is("[Formato incorrecto: Password debe ser de tipo numerico.]"));
  }
  
  @Test
  
  //Modificar exitosamente campo nombre
  
  public void modificarNombre() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 712));
    driver.findElement(By.cssSelector("#UserId178 > .fa-pen")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Juan");
    driver.findElement(By.cssSelector(".submitInput:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".sucessMessage")).getText(), is(not("[Formato incorrecto: Nombre debe ser de tipo String.]")));
  }
  
  
  @Test
  
  //intetat modificar nombre con numeros y letras
  
  public void modificarNombreFallido() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 712));
    driver.findElement(By.cssSelector("#UserId178 > .fa-pen")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).sendKeys("Juan45");
    driver.findElement(By.cssSelector(".submitInput:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".sucessMessage")).getText(), is("[Formato incorrecto: Nombre debe ser de tipo String.]"));
  }
  
  @Test
  
  //modificar de manera correcta el apellido
  
  public void modificarApellido() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 712));
    driver.findElement(By.cssSelector("#UserId178 > .fa-pen")).click();
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Mendina");
    driver.findElement(By.cssSelector(".submitInput:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".sucessMessage")).getText(), is(not("[Formato incorrecto: Apellido debe ser de tipo String.]")));
  }
  
  @Test
  
  //intentar modificar apellido con numeros
  
  public void modificarApellidoFallido() {
    driver.get("https://online-hotel-frontend.herokuapp.com/users");
    driver.manage().window().setSize(new Dimension(1365, 712));
    driver.findElement(By.cssSelector("#UserId178 > .fa-pen")).click();
    driver.findElement(By.id("surname")).click();
    driver.findElement(By.id("surname")).sendKeys("Mendin4");
    driver.findElement(By.cssSelector(".submitInput:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".sucessMessage")).getText(), is("[Formato incorrecto: Apellido debe ser de tipo String.]"));
  }
  
    
}
