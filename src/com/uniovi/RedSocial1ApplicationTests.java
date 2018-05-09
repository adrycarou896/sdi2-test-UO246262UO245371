package com.uniovi;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.uniovi.pageobjects.PO_LoginView;
import com.uniovi.pageobjects.PO_NavView;
import com.uniovi.pageobjects.PO_Properties;
import com.uniovi.pageobjects.PO_PublicationView;
import com.uniovi.pageobjects.PO_PublicationView2;
import com.uniovi.pageobjects.PO_RegisterView;
import com.uniovi.pageobjects.PO_SearchTextView;
import com.uniovi.pageobjects.PO_View;
import com.uniovi.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedSocial1ApplicationTests {

	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	// static String PathFirefox =
	// "C:\\Users\\Packard\\Desktop\\SDI\\PL-SDI-Material5\\Firefox46.win\\FirefoxPortable.exe";
	static String PathFirefox = "C:\\Firefox46Portable\\FirefoxPortable.exe";
	// En MACOSX (Debe ser la versión 46.0 y desactivar las actualizaciones
	// automáticas):
	// static String PathFirefox =
	// "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8081";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {

		driver.navigate().to(URL);

	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}
	
	// PR01_1. Registro de Usuario con datos válidos
	@Test
	public void PR01() {
		// Vamos al formulario de registro
		PO_NavView.clickOption(driver, "registrarse", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "77777778A@uniovi.es", "Josefo", "77777", "77777");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Comprobamos que entramos en la sección privada
		PO_RegisterView.checkElement(driver, "text", "Nuevo usuario registrado");
	}

	// PR01_2.Registro de Usuario con datos inválidos (repetición de contraseña
	// invalida).
	/*@Test
	public void PR02() {

		// Vamos al formulario de registro
		PO_NavView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B@uniovi.es", "Josefo", "7777", "7778");
		// COmprobamos el error de password no coincidente .
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// PR02_1 Inicio de sesión con datos válidos
	@Test
	public void PR03() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "77777778A@uniovi.es", "77777");
		// COmprobamos que entramos en la pagina privada de Alumno
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkKey(driver, "users.show.text", PO_Properties.getSPANISH());
	}

	// PR02_2 Inicio de sesión con datos inválidos (usuario no existente en la
	// aplicación).
	@Test
	public void PR04() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "ZZZZZZZZA@uniovi.es", "123456");
		// COmprobamos que no entramos en la pagina privada del Usuario
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkKey(driver, "Error.login", PO_Properties.getSPANISH());
	}

	// PR03_1 Acceso al listado de usuarios desde un usuario en sesión.
	@Test
	public void PR05() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Comprobamos que estamos en la vista lista usuarios.
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkKey(driver, "users.show.text", PO_Properties.getSPANISH());
	}

	// PR03_2 Intento de acceso con URL desde un usuario no identificado al listado
	// de
	// usuarios desde un usuario en sesión. Debe producirse un acceso no permitido a
	// vistas privadas.
	@Test
	public void PR06() {
		// Tratamos de acceder a la vista lista usuarios.
		driver.navigate().to(URL + "/user/list");
		// Comprobamos que nos redirige al login.
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkKey(driver, "login.message", PO_Properties.getSPANISH());

	}

	// PR04_1 Realizar una búsqueda valida en el listado de usuarios desde un
	// usuario en
	// sesión.
	@Test
	public void PR07() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Rellenamos el formulario de busqueda
		PO_SearchTextView.fillForm(driver, "ran");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Contamos el número de filas de usuarios y comprobamos que solo hay una fila
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
	}

	// PR04_2 Intento de acceso con URL a la búsqueda de usuarios desde un usuario
	// no
	// identificado. Debe producirse un acceso no permitido a vistas privadas.
	@Test
	public void PR08() {
		// Tratamos de acceder a la vista lista usuarios.
		driver.navigate().to(URL + "/user/list");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Comprobamos que nos redirige al login.
		PO_View.checkElement(driver, "text", "Login");
	}

	// PR05_1 Enviar una invitación de amistad a un usuario de forma valida.
	@Test
	public void PR09() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Enviamos petición de amistad
		driver.findElement(By.id("sendPetitionButton2")).click();
	}

	// PR05_2 Enviar una invitación de amistad a un usuario al que ya le habíamos
	// invitado
	// la invitación previamente. No debería dejarnos enviar la invitación, se
	// podría ocultar el botón de enviar invitación o notificar que ya había sido
	// enviada previamente.
	@Test
	public void PR10() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Comprobamos que ahora disponemos de un botón que nos permite cancelar petición.
		driver.findElement(By.id("cancelPetitionButton2")).click();
		// Comprobamos que ahora la finalidad del boton es cancelar petición.
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkKey(driver, "friend.add", PO_Properties.getSPANISH());
	}

	// PR06_1 Listar las invitaciones recibidas por un usuario, realizar la
	// comprobación
	// con una lista que al menos tenga una invitación recibida.
	@Test
	public void PR11() {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");
		// Enviamos petición de amistad
		driver.findElement(By.id("sendPetitionButton2")).click();

		// Cerramos sesión
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");

		// Juan@hotmail.com se logea
		PO_LoginView.fillForm(driver, "Juan@hotmail.com", "123456");

		// Juan@hotmail.com mira sus peticiones de amistad
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		// Sacamos la pestaña para ver las peticiones
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/petitions')]");
		// Pinchamos en la pestaña para ver las peticiones
		elementos.get(0).click();
		// PO_View.checkElement(driver, "text", "Aceptar petición");
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkKey(driver, "petition.accept", PO_Properties.getSPANISH());

	}

	// PR07_1 Aceptar una invitación recibida
	@Test
	public void PR12() {

		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");

		// Juan@hotmail.com se logea
		PO_LoginView.fillForm(driver, "Juan@hotmail.com", "123456");

		// Juan@hotmail.com mira sus peticiones de amistad
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		// Sacamos la pestaña para ver las peticiones
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/petitions')]");
		// Pinchamos en la pestaña para ver las peticiones
		elementos.get(0).click();
		// PO_View.checkElement(driver, "text", "Aceptar petición");
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkKey(driver, "petition.accept", PO_Properties.getSPANISH());

		// Damos a aceptar la petición
		driver.findElement(By.id("acceptPetitionButton1")).click();

		// Miramos que ha desparecido la invitación
		// SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Aceptar petición",
		// PO_View.getTimeout());
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkNoKey(driver, "petition.accept", PO_Properties.getSPANISH());
	}

	// PR08_1 Listar los amigos de un usuario, realizar la comprobación con una
	// lista que
	// al menos tenga un amigo
	@Test
	public void PR13() {

		// Iniciamos sesión, este ya tiene una amigo Juan@hotmail.com
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");

		// adripc2live.com entra en gestión de usuarios
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();
		// Sacamos la pestaña para ver los amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/friends')]");
		// Pinchamos en la pestaña para ver los amigos
		elementos.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 1);
		PO_RegisterView.checkKey(driver, "friends.text", PO_Properties.getSPANISH());
		PO_View.checkElement(driver, "text", "Juan@hotmail.com");

	}
	//HASTA AQUI
	
	
	
	
	
	
	
	
	
	
	*/
	//POR SI SE QUIERE UTILIZAR PA ALGO
	/*// PR09_1 Crear una publicación con datos válidos
	@Test
	public void PR14() {

		// Iniciamos sesión, este ya tiene una amigo Juan@hotmail.com
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");

		// adripc2live.com entra en Publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		// Sacamos la pestaña para crear una publicación
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/create')]");
		// Pinchamos en la pestaña para crear una punlicación
		elementos.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 1);
		// Relleno la publicación
		PO_PublicationView.fillForm(driver, "Hola", "Que tal");

		// Aparece la lista de publicciones
		PO_View.checkElement(driver, "text", "adripc@live.com");
		PO_View.checkElement(driver, "text", "Hola");
		PO_View.checkElement(driver, "text", "Que tal");

	}

	// PR10_1 Acceso al listado de publicaciones desde un usuario en sesión
	@Test
	public void PR15() {

		// Iniciamos sesión, este ya tiene una amigo Juan@hotmail.com
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "adripc@live.com", "123456");

		// adripc2live.com entra en Publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();

		// Sacamos la pestaña para ver las publicaciónes
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
		// Pinchamos en la pestaña para ver las publicaciones
		elementos.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 1);
		
		// Aparece la lista de publicciones
		PO_View.checkElement(driver, "text", "adripc@live.com");

		PO_View.checkElement(driver, "text", "Hola");
		PO_View.checkElement(driver, "text", "Que tal");

		// Se mira que exista una publicacion
		List<WebElement> publicaciones = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(publicaciones.size() == 1);

	}

	// 11.1 [LisPubAmiVal] Listar las publicaciones de un usuario amigo
	@Test
	public void PR16() {
		// Iniciamos sesión, este ya tiene una amigo Juan@hotmail.com
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "Juan@hotmail.com", "123456");

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();

		// Sacamos la pestaña para ver los amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/friends')]");
		// Pinchamos en la pestaña para ver los amigos
		elementos.get(0).click();

		// Consultamos las publiaciones con el id del amigo de Juan
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/publication/list/1')]");
		elementos.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 1);
		
		// Comprobamos que nos encontramos con el texto de la publicación que ha creado
		PO_View.checkElement(driver, "text", "adripc@live.com");
		PO_View.checkElement(driver, "text", "Hola");
		PO_View.checkElement(driver, "text", "Que tal");

	}

	// 11.2 [LisPubAmiInVal] Utilizando un acceso vía URL tratar de listar las
	// publicaciones de un usuario que
	// no sea amigo del usuario identificado en sesión.
	@Test
	public void PR17() {
		// Iniciamos sesión, con Juan@hotmail.com el cual no es amigo del usuario con id
		// 5
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "Juan@hotmail.com", "123456");

		// Navegamos a la lista de peticiones del usuario que no es amigo suyo
		driver.navigate().to(URL + "/publication/list/5");

		SeleniumUtils.esperarSegundos(driver, 1);
		// Comprobamos que nos ha redirigido a la lista de usuarios en vez de a la lista
		// de peticiones de ese usuario
		PO_RegisterView.checkKey(driver, "users.show.text", PO_Properties.getSPANISH());
	}

	// 12.1 [PubFot1Val] Crear una publicación con datos válidos y una foto adjunta.
	@Test
	public void PR18() {
		// Iniciamos sesión, este ya tiene una amigo Juan@hotmail.com
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "Juan@hotmail.com", "123456");

		// adripc2live.com entra en Publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		// Sacamos la pestaña para crear una publicación
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/create')]");
		// Pinchamos en la pestaña para crear una punlicación
		elementos.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 1);
		// Relleno la publicación
		PO_PublicationView2.fillForm(driver, "Buenas tardes", "buenas tardes, buenas tardes");

		// Aparece la lista de publicciones
		PO_View.checkElement(driver, "text", "Juan@hotmail.com");
		PO_View.checkElement(driver, "text", "Buenas tardes");
		PO_View.checkElement(driver, "text", "buenas tardes, buenas tardes");
		PO_View.checkElement(driver, "text", "Imagen");
	}

	// 12.2 [PubFot2Val] Crear una publicación con datos válidos y sin una foto
	// adjunta
	@Test
	public void PR19() {
		// Iniciamos sesión, este ya tiene una amigo Juan@hotmail.com
		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "Juan@hotmail.com", "123456");

		// adripc2live.com entra en Publicaciones
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		// Sacamos la pestaña para crear una publicación
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/create')]");
		// Pinchamos en la pestaña para crear una punlicación
		elementos.get(0).click();

		SeleniumUtils.esperarSegundos(driver, 1);
		// Relleno la publicación
		PO_PublicationView.fillForm(driver, "Me llamo Juan", "¿Han pensado alguna vez en ese tipo...");

		// Aparece la lista de publicciones
		PO_View.checkElement(driver, "text", "Juan@hotmail.com");
		PO_View.checkElement(driver, "text", "Me llamo Juan");
		PO_View.checkElement(driver, "text", "¿Han pensado alguna vez en ese tipo...");
	}

	// 13.1 [AdInVal] Inicio de sesión como administrador con datos válidos.
	@Test
	public void PR21() {
		driver.navigate().to(URL + "/admin/login");
		PO_LoginView.fillFormAdmin(driver, "adripc@live.com", "123456");

		PO_RegisterView.checkKey(driver, "users.show.text", PO_Properties.getSPANISH());
	}

	// 13.2 [AdInInVal] Inicio de sesión como administrador con datos inválidos
	// (usar los datos de un usuario
	// que no tenga perfil administrador).
	@Test
	public void PR22() {

		driver.navigate().to(URL + "/admin/login");
		PO_LoginView.fillFormAdmin(driver, "a1@live.com", "123456");

		PO_RegisterView.checkKey(driver, "Error.admin.login", PO_Properties.getSPANISH());
	}

	// 14.1 [AdLisUsrVal] Desde un usuario identificado en sesión como administrador
	// listar a todos los
	// usuarios de la aplicación
	@Test
	public void PR23() {
		driver.navigate().to(URL + "/admin/login");
		PO_LoginView.fillFormAdmin(driver, "adripc@live.com", "123456");
		
		PO_RegisterView.checkKey(driver, "users.show.text", PO_Properties.getSPANISH());
	}

	// 15.1 [AdBorUsrVal] Desde un usuario identificado en sesión como administrador
	// eliminar un usuario
	// existente en la aplicación.
	@Test
	public void PR24() {

		// Inicio sesion con admin
		driver.navigate().to(URL + "/admin/login");
		PO_LoginView.fillFormAdmin(driver, "adripc@live.com", "123456");

		// Comprobamos que francisco12@live.com existe en la lista de usuarios
		PO_View.checkElement(driver, "text", "Juan@hotmail.com");
		
		//Comprobamos que Juan@hotmail.com aparece como amigo de adripc@live.com (el usuario en sesión)
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();

		// Sacamos la pestaña para ver los amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/friends')]");
		// Pinchamos en la pestaña para ver los amigos
		elementos.get(0).click();
		
		//vemos si existe en la lista de amigos Juan@hotmail.com
		PO_View.checkElement(driver, "text", "Juan@hotmail.com");
		//--------------
		
		//Volvemos a la vista de la lista de usuarios
		driver.navigate().to(URL + "/admin/list");
		
		// Borramos el segundo usuario que es Juan@hotmail.com
		driver.findElement(By.id("deleteButton2")).click();

		// Comprobamos que Juan@hotmail.com ya no existe
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Juan@hotmail.com", PO_View.getTimeout());
		
		//Comprobamos que Juan@hotmail.com no aparece como amigo de adripc@live.com (el usuario en sesión)
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'users-menu')]/a");
		elementos.get(0).click();

		// Sacamos la pestaña para ver los amigos
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/friends')]");
		// Pinchamos en la pestaña para ver los amigos
		elementos.get(0).click();

		// vemos que no existe Juan@hotmail.com (JUAN Y ADRIAN ya no son amigos)
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Juan@hotmail.com", PO_View.getTimeout());
	}

	// 15.2 [AdBorUsrInVal] Intento de acceso vía URL al borrado de un usuario
	// existente en la aplicación.
	// Debe utilizarse un usuario identificado en sesión pero que no tenga perfil de
	// administrador.
	@Test
	public void PR25() {

		PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
		// Inicio sesión con usuario no admin
		PO_LoginView.fillForm(driver, "a1@live.com", "123456");

		// Intento borrar al segundo de la lista
		driver.navigate().to(URL + "/admin/delete/2");

		// Comprobamos que el Access is denied
		PO_View.checkElement(driver, "text", "Access is denied");
	}*/

}
