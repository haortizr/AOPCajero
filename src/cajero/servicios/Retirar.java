package cajero.servicios;

import java.util.Date;
import java.util.Scanner;

import cajero.modelo.Banco;
import cajero.modelo.Cuenta;

/**
 * Comando usado para retirar dinero
 */
public class Retirar implements Servicio {

	@Override
	public String getNombre() {
		return "Retirar dinero";
	}

	@SuppressWarnings("resource")
	@Override
	public void ejecutar(Banco contexto) throws Exception {
		
		System.out.println("Retiro de Dinero");
		System.out.println();
		
		// la clase Console no funciona bien en Eclipse
		Scanner console = new Scanner(System.in);			
		
		// Ingresa los datos
		System.out.println("Ingrese el numero de cuenta");
		String numeroDeCuenta = console.nextLine();
		
		Cuenta cuenta = contexto.buscarCuenta(numeroDeCuenta);
		if (cuenta == null) {
			throw new Exception("No existe cuenta con el numero " + numeroDeCuenta);
		}
		
		System.out.println("Ingrese el valor a retirar");
		String valor = console.nextLine();
	
		try {
			long valorNumerico = Long.parseLong(valor);
			cuenta.retirar(valorNumerico);
			contexto.getLstOperaciones().add(new Date() + " : Se retiran " + valorNumerico + " de la cuenta No. " + numeroDeCuenta );
		
		} catch (NumberFormatException e) {
			throw new Exception("Valor a retirar no valido : " + valor);
		}
	}

}
