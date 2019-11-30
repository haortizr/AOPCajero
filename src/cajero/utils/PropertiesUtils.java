package cajero.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public class PropertiesUtils {

	
	private static Properties propiedad = new Properties();

	private PropertiesUtils() {}

	static {
		try {
			File archivo = new File(ConstantesSistema.ARCHIVO_PROPIEDADES);
			if (!archivo.exists()) {
				archivo.createNewFile();
			}
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(ConstantesSistema.ARCHIVO_PROPIEDADES))) {
				propiedad.load(bis);
				System.out.println(propiedad.stringPropertyNames());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve el valor de una propiedad. Lanza excepción si no existe la propiedad o si su valor no es true o false.
	 * 
	 * @param propertyName
	 * @return Value of property.
	 */
	public static boolean getEntrada(String nombre) {

		if (propiedad.getProperty(nombre) == null) {
			System.err.println("La entrada '"
				+ nombre + "' solicitada no existe");
		}

		return Boolean.valueOf(propiedad.getProperty(nombre));

	}

	
}
