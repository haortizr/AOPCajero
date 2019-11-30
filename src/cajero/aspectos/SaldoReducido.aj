package cajero.aspectos;

import java.util.Date;
import java.util.Scanner;

import cajero.modelo.*;
import cajero.servicios.*;

public aspect SaldoReducido {

	pointcut metodoSaldoReducidoRetirar() : call (* cajero.servicios.ejecutar(..) throws Exception);

	pointcut metodoSaldoReducidoTransferir() : call (* cajero.servicios.ejecutar(..) throws Exception);

	void around() throws Exception : metodoSaldoReducidoRetirar() {

		Object obj = thisJoinPoint.getTarget();
		if (!(obj instanceof Retirar)) {
			proceed();
			return;
		}

		System.out.println("**** Retiro de Dinero ***");
		System.out.println();

		Object arg = null;
		if (thisJoinPoint.getArgs().length > 0) {
			arg = thisJoinPoint.getArgs()[0];
		}
		Banco contexto = (Banco) arg;

		// la clase Console no funciona bien en Eclipse
		Scanner console = new Scanner(System.in);

		// Ingresa los datos
		System.out.println("Ingrese el numero de cuenta");
		String numeroDeCuenta = console.nextLine();

		Cuenta cuenta = contexto.buscarCuenta(numeroDeCuenta);
		if (cuenta == null) {
			throw new Exception("No existe cuenta con el numero " + numeroDeCuenta);
		}

		if (cuenta.getSaldo() < 200) {
			throw new Exception("El saldo de la cuenta no puede ser menor a 200 ");
		}

		System.out.println("Ingrese el valor a retirar");
		String valor = console.nextLine();

		try {
			long valorNumerico = Long.parseLong(valor);

			if ((cuenta.getSaldo() - valorNumerico) < 200) {
				throw new Exception("El saldo de la cuenta no puede ser menor a 200 ");
			}

			cuenta.retirar(valorNumerico);
			contexto.getLstOperaciones()
					.add(new Date() + " : Se retiran " + valorNumerico + " de la cuenta No. " + numeroDeCuenta);

		} catch (NumberFormatException e) {
			throw new Exception("Valor a retirar no válido : " + valor);
		}
	}

	void around() throws Exception : metodoSaldoReducidoTransferir() {

		Object obj = thisJoinPoint.getTarget();
		if (!(obj instanceof Transferir)) {
			proceed();
			return;
		}
		System.out.println("**** Transferencia de Dinero *****");
		System.out.println();
		Object arg = null;
		if (thisJoinPoint.getArgs().length > 0) {
			arg = thisJoinPoint.getArgs()[0];
		}
		Banco contexto = (Banco) arg;

		// la clase Console no funciona bien en Eclipse
		Scanner console = new Scanner(System.in);

		// Ingresa los datos
		System.out.println("Ingrese el número de cuenta origen");
		String numeroCuentaOrigen = console.nextLine();

		Cuenta cuentaOrigen = contexto.buscarCuenta(numeroCuentaOrigen);
		if (cuentaOrigen == null) {
			throw new Exception("No existe cuenta con el número " + numeroCuentaOrigen);
		}
		if (cuentaOrigen.getSaldo() < 200) {
			throw new Exception("El saldo de la cuenta no puede ser menor a 200 ");
		}

		System.out.println("Ingrese el número de cuenta destino");
		String numeroCuentaDestino = console.nextLine();

		Cuenta cuentaDestino = contexto.buscarCuenta(numeroCuentaDestino);
		if (cuentaDestino == null) {
			throw new Exception("No existe cuenta con el número " + numeroCuentaDestino);
		}

		System.out.println("Ingrese el valor a transferir");
		String valor = console.nextLine();

		try {

			// se retira primero y luego se consigna
			// si no se puede retirar, no se hace la consignación

			long valorNumerico = Long.parseLong(valor);
			if ((cuentaOrigen.getSaldo() - valorNumerico) < 200) {
				throw new Exception("El saldo de la cuenta no puede ser menor a 200 ");
			}
			cuentaOrigen.retirar(valorNumerico);
			cuentaDestino.consignar(valorNumerico);
			contexto.getLstOperaciones().add(new Date() + " : Se transfieren " + valorNumerico + " de la cuenta No. "
					+ numeroCuentaOrigen + " a la cuenta No. " + numeroCuentaDestino);
		} catch (NumberFormatException e) {
			throw new Exception("Valor a transferir no válido : " + valor);
		}
	}

}
