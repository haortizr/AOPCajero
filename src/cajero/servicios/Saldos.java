package cajero.servicios;

import java.util.Date;
import java.util.Scanner;

import cajero.modelo.Banco;
import cajero.modelo.Cuenta;
/**
 * Comando usado para consignar dinero
 */
public class Saldos implements Servicio {

	@Override
	public String getNombre() {
		return "Consultar saldo";
	}

	@SuppressWarnings("resource")
	@Override
	public void ejecutar(Banco contexto) throws Exception {
		
		System.out.println("Consulta de saldo");
		System.out.println();
		
		// la clase Console no funciona bien en Eclipse
		Scanner console = new Scanner(System.in);			
		
		// Ingresa los datos
		System.out.println("Ingrese el número de cuenta");
		String numeroDeCuenta = console.nextLine();
		
		Cuenta cuenta = contexto.buscarCuenta(numeroDeCuenta);
		if (cuenta == null) {
			throw new Exception("No existe cuenta con el número " + numeroDeCuenta);
		}
		
		System.out.println("El saldo de la cuenta No. " + numeroDeCuenta + " es " + cuenta.getSaldo());
		contexto.getLstOperaciones().add(new Date() + " : Se consulta el saldo de la cuenta "  + numeroDeCuenta );
	}

}
