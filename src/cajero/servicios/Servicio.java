package cajero.servicios;

import cajero.modelo.Banco;

public interface Servicio {

	public String getNombre();
	public void ejecutar(Banco banco) throws Exception;
}
