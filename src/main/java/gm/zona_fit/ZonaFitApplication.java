package gm.zona_fit;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger =
			LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("********** Inicio de app ********************");
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("********** Fin de app !!!!! *****************"+System.lineSeparator());
	}

	@Override
	public void run(String... args) throws Exception {
		ZonaFitApp();
	}

	private void ZonaFitApp(){
		logger.info(nl +nl +nl +"*** Aplicacion Zona Fid (GYM) *****"+nl +nl +nl );
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir){
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpccion(consola,opcion);
			logger.info(nl);
		}
	}



	private  int  mostrarMenu(Scanner consola){
		logger.info("""
			1- Listar Clientes
			2- Buscar Cleinte
			3- Crear Cliente
			4- Modificar Cliente
			5- Eliminar Cliente
			6- Salir
			Selecciona una opccion: \s""");
		return Integer.parseInt(consola.nextLine());
	}

	private boolean ejecutarOpccion(Scanner consola, int opcion) {
		var salir = false;
		switch (opcion){
			case 1 ->{
				logger.info(nl+"--- Lista de Cliente ---"+nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString() + nl));
			}
			case 2 ->{
				logger.info(nl+"--- Buscar Cliente Por Id ---"+nl);
				logger.info("Proporciona El Id Del Cliente a Buscar: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null)
					logger.info("Cliente Encontrado: "+ cliente + nl);
				else
					logger.info("Cliente no se encontro: "+ cliente +nl);
			}
			case 3 -> {
				logger.info(nl+"--- Agregar Cliente ---"+nl);
				logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Apellido: ");
				var apellido = consola.nextLine();
				logger.info("Membresia: ");
				var membresia = Integer.parseInt(consola.nextLine());
				var cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);
				clienteServicio.guadarCliente(cliente);
				logger.info("Cliente Agregado: "+ cliente);
			}
			case 4 ->{
				logger.info(nl+"--- Mosdificar Cliente ---"+nl);
				logger.info("Id Cliente: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null)
					logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Apellido: ");
				var apellido = consola.nextLine();
				logger.info("Membresia: ");
				var membresia = Integer.parseInt(consola.nextLine());
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);
				if (cliente != null){
					clienteServicio.guadarCliente(cliente);
					logger.info("Cliente Editado: "+ cliente + nl);
				}else{
					logger.info("Cliente No Encontrado: "+ cliente + nl);
				}
			}
			case 5 ->{
				logger.info(nl+"--- Eliminar Cliente ---"+nl);
				logger.info("Id Cliente: ");
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null){
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente Eliminado: "+ cliente + nl);
				}else{
					logger.info("Cliente No Encontrado: "+ cliente + nl);
				}
			}
			case 6 -> {
				logger.info("Hasta luego !!!!!"+nl +nl);
				salir = true;
			}
			default -> logger.info(nl+nl+"no encontrmos tu opccion!!: "+opcion +nl);
		}
		return salir;
	}
}
