package cajero.servicios;

import java.util.Date;
import java.util.Scanner;

import cajero.modelo.Banco;
import cajero.modelo.Cuenta;

/**
 * Comando usado para transferir dinero entre cuentas
 */
public class Transferir implements Servicio {

	@Override
	public String getNombre() {
		return "Transferir dinero";
	}

	@SuppressWarnings("resource")
	@Override
	public void ejecutar(Banco contexto) throws Exception {
		
		System.out.println("Transferencia de Dinero");
		System.out.println();
		
		// la clase Console no funciona bien en Eclipse
		Scanner console = new Scanner(System.in);			
		
		// Ingresa los datos
		System.out.println("Ingrese el numero de cuenta origen");
		String numeroCuentaOrigen = console.nextLine();
		
		Cuenta cuentaOrigen = contexto.buscarCuenta(numeroCuentaOrigen);
		if (cuentaOrigen == null) {
			throw new Exception("No existe cuenta con el numero " + numeroCuentaOrigen);
		}

		System.out.println("Ingrese el numero de cuenta destino");
		String numeroCuentaDestino = console.nextLine();
		
		Cuenta cuentaDestino = contexto.buscarCuenta(numeroCuentaDestino);
		if (cuentaDestino == null) {
			throw new Exception("No existe cuenta con el numero " + numeroCuentaDestino);
		}
		
		System.out.println("Ingrese el valor a transferir");
		String valor = console.nextLine();
	
		try {
			
			// se retira primero y luego se consigna
			// si no se puede retirar, no se hace la consignación
			
			long valorNumerico = Long.parseLong(valor);
			cuentaOrigen.retirar(valorNumerico);
			cuentaDestino.consignar(valorNumerico);
			contexto.getLstOperaciones().add(new Date() + " : Se transfieren " + valorNumerico + " de la cuenta No. " + numeroCuentaOrigen + " a la cuenta No. " + numeroCuentaDestino);
		} catch (NumberFormatException e) {
			throw new Exception("Valor a transferir no valido : " + valor);
		}
	}

}
