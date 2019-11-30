package cajero.aspectos;

public aspect Auditoria {

	pointcut controlServicios() : call( * cajero.servicios..*(..));

	before(): controlServicios() {

		System.out.println(" ****** Ejecutando ******");
		System.out.println("\t Objeto     : " + thisJoinPoint.getTarget());
		System.out.println("\t Metodo     : " + thisJoinPoint.getSignature());
		System.out.println("\t Argumentos : " + thisJoinPoint.getArgs());
	}

	after() returning(Object resultado): controlServicios() {
		System.out.println("****** Retornando *******");
		System.out.println("\t Resultado  : " + resultado);
	}

	after() throwing(Exception e): controlServicios() {
		System.out.println("***** Retornando con Excepcion ******");
		System.out.println("\t Excepcion : " + e.getMessage());
	}
}
