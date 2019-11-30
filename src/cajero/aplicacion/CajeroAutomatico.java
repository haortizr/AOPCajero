package cajero.aplicacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cajero.modelo.*;
import cajero.servicios.*;
import cajero.utils.*;

public class CajeroAutomatico {

	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		// crea el banco
		Banco banco = new Banco();
		
		
		// crea unas cuentas, para la prueba
		banco.agregarCuenta(new Cuenta("1", "clave", 1000000));
		banco.agregarCuenta(new Cuenta("2", "clave", 2000000));
		banco.agregarCuenta(new Cuenta("3", "clave", 3000000));
		
		// crea los comandos que se van a usar en la aplicación
		List<Servicio> servicios = cargaServicios();
		
		
		// Ciclo del Programa
		// ==================

		System.out.println("Cajero Automatico");
		System.out.println("=================");
		System.out.println();
		
		boolean fin = false;
		do {
			
			// muestra los nombres de los comandos
			muestraMenuConServicios(servicios);
			System.out.println("X.- Salir");
			
			// la clase Console no funciona bien en Eclipse
			Scanner console = new Scanner(System.in);			
			String valorIngresado = console.nextLine();
			
			// obtiene el comando a ejecutar
			Servicio servicioSeleccionado = retornaServicioSeleccionado(servicios, valorIngresado);
			if (servicioSeleccionado != null) {
				
				// intenta ejecutar el comando
				try {
					servicioSeleccionado.ejecutar(banco);										
				} catch (Exception e) {
					// si hay una excepción, muestra el mensaje
					System.err.println(e.getMessage());
				}
				
			} 
			// si no se obtuvo un comando, puede ser la opción de salir
			else if (valorIngresado.equalsIgnoreCase("X")) {
				fin = true;
			}
			
			System.out.println();
		} while ( !fin );
		
		System.out.println("Vuelva Pronto.");
	}
	
	
	// Manejo de los comandos de la aplicación
	// =======================================
	
	// carga los comandos usados en el programa
	private static List<Servicio> cargaServicios() {
		
		// crea los comandos que se van a usar en la aplicación
		List<Servicio> servicios = new ArrayList<>();
		
		servicios.add(new ListarCuentas());
		servicios.add(new Saldos());
		servicios.add(new Retirar());
		servicios.add(new ListarOperaciones());
		
		if (PropertiesUtils.getEntrada(ConstantesSistema.CONSIGNACION)) {
			servicios.add(new Consignar());
		}
		if (PropertiesUtils.getEntrada(ConstantesSistema.TRANSFERENCIA)) {
			servicios.add(new Transferir());
		}
		
		
		

		return servicios;
	}
	
	
	// Muestra el listado de comandos, para mostrar un menú al usuario
	// muestra el índice en el arreglo de comandos y el nombre del comando
	private static void muestraMenuConServicios(List<Servicio> servicios) {
		
		// muestra los nombres de los comandos 
		for (int i=0; i < servicios.size(); i++) {
			System.out.println(i + ".-" + servicios.get(i).getNombre());
		}
	}
	
	
	// dado el texto ingresado por el usuario, retorna el comando correspondiente
	// retorna null si el texto no es un número o no existe ningún comando con ese índice
	private static Servicio retornaServicioSeleccionado(List<Servicio> servicios, String valorIngresado) {
		
		Servicio servicioSeleccionado = null;
		
		// el valorIngresado es un numero ?
		if (valorIngresado.matches("[0-9]")) {			
			int valorSeleccionado = Integer.valueOf(valorIngresado);
			// es un índice válido para la lista de comandos
			if (valorSeleccionado < servicios.size()) {
				servicioSeleccionado = servicios.get(valorSeleccionado);
			}
		}
		
		return servicioSeleccionado;
	}

}
