package cajero.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa el Banco. Contiene una colección de cuentas
 */
public class Banco {

	// mapa con las cuentas. la llave es el número de la cuenta
	private Map<String, Cuenta> cuentas = new HashMap<>();
	
	//Listado de operaciones
	private List<String> lstOperaciones = new ArrayList<String>();
	
	
	
	public Banco() { }
	
	
	
	public Collection<Cuenta> getCuentas() {
		return cuentas.values();
	}
	

	public Cuenta buscarCuenta(String numero) {
		return cuentas.get(numero);
	}


	public void agregarCuenta(Cuenta cuenta) {
		cuentas.put(cuenta.getNumero(), cuenta);
	}

	public List<String> getLstOperaciones() {
		return lstOperaciones;
	}


	public void setLstOperaciones(List<String> lstOperaciones) {
		this.lstOperaciones = lstOperaciones;
	}

}
