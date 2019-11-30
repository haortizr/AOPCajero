package cajero.servicios;

import cajero.modelo.Banco;
import cajero.modelo.Cuenta;

/**
 * Comando usado para listar las cuentas 
 */
public class ListarCuentas implements Servicio {

	@Override
	public String getNombre() {
		return "Listar Cuentas";
	}

	@Override
	public void ejecutar(Banco contexto) throws Exception {
		
		System.out.println("Listado de Cuentas");
		System.out.println();
	
		for (Cuenta cuenta : contexto.getCuentas()) {
			System.out.println(cuenta.getNumero() + " : $ " + cuenta.getSaldo());
		}

	}

}
