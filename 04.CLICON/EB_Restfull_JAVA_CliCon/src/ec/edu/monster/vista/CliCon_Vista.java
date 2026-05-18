package ec.edu.monster.vista;

import ec.edu.monster.controlador.CliCon_Controlador;
import java.util.Scanner;

public class CliCon_Vista {

    public static void main(String[] args) {

        CliCon_Controlador controlador = new CliCon_Controlador();
        Scanner scanner = new Scanner(System.in);

        int opcion;

        // ASCII ART (lo dejo igual que tú)
        System.out.println(" ███╗    ███╗ ██████╗ ███╗   ██╗██████╗███████╗███████╗██████╗ ");
        System.out.println(" ████╗ ████║██╔═══ ██╗████╗  ██║██╔═════╝ ╚══ ██╔══╝ ██╔═══════╝ ██╔══██╗ ");
        System.out.println(" ██╔████╔██║██║    ██║██╔██╗ ██║  ████╗     ██║   ████╗     █████╔╝ ");
        System.out.println(" ██║╚██╔╝ ██║██║    ██║██║╚██╗██║      ██║    ██║    ██╔══╝     ██╔══██║ ");
        System.out.println(" ██║ ╚═╝   ██║╚██████╔╝██║ ╚████║██████║    ██║    ███████╗██║   ██║ ");
        System.out.println(" ╚══╝       ╚══╝ ╚═════════╝ ╚══╝   ╚═════╝╚═════════╝   ╚═══╝    ╚═══════════╝╚══╝   ╚═══╝   ");

        System.out.println("\033[34m\n--------------------\033[0m");
        System.out.println("\033[34m     BIENVENIDO     \033[0m");
        System.out.println("\033[34m--------------------\033[0m");

        // === Login contra el servidor REST ===
        System.out.print("\nIngrese su usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        boolean acceso = controlador.login(usuario, password);

        if (acceso) {
            System.out.println("\033[32m\nAcceso exitoso\033[0m");

            do {
                System.out.println("\033[34m\n==========================\033[0m");
                System.out.println("\033[34m       MENÚ PRINCIPAL       \033[0m");
                System.out.println("\033[34m==========================\033[0m\n");
                System.out.println("1. Consultar movimientos");
                System.out.println("2. Realizar depósito");
                System.out.println("3. Realizar retiro");
                System.out.println("4. Realizar transferencia");
                System.out.println("5. Salir");
                System.out.print("\033[34m\nSeleccione una opción: \033[0m");

                while (!scanner.hasNextInt()) {
                    System.out.println("\033[31mOpción no válida. Ingrese un número.\033[0m");
                    scanner.next(); 
                }
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer

                switch (opcion) {
                    case 1:
                        System.out.println("\033[35m\n============================\033[0m");
                        System.out.println("\033[35m         MOVIMIENTOS        \033[0m");
                        System.out.println("\033[35m============================\033[0m");
                        System.out.print("\n\033[35mIngrese el número de cuenta: \033[0m");
                        String cuentaConsulta = scanner.nextLine();

                        String movimientos = controlador.obtenerMovimientos(cuentaConsulta);
                        System.out.println();
                        System.out.println(movimientos);
                        break;

                    case 2:
                        System.out.println("\033[36m\n============================\033[0m");
                        System.out.println("\033[36m         DEPÓSITO          \033[0m");
                        System.out.println("\033[36m============================\033[0m");
                        System.out.print("\033[36m\nIngrese el número de cuenta: \033[0m");
                        String cuentaDeposito = scanner.nextLine();
                        System.out.print("\033[36mIngrese el importe a depositar: \033[0m");
                        double importe = scanner.nextDouble();
                        scanner.nextLine();

                        String resultadoDeposito = controlador.registrarDeposito(cuentaDeposito, importe);
                        System.out.println("\n" + resultadoDeposito);
                        break;

                    case 3:
                        System.out.println("\033[35m\n============================\033[0m");
                        System.out.println("\033[35m         RETIRO            \033[0m");
                        System.out.println("\033[35m============================\033[0m");
                        System.out.print("\033[35m\nIngrese el número de cuenta: \033[0m");
                        String cuentaRetiro = scanner.nextLine();
                        System.out.print("\033[35mIngrese el importe a retirar: \033[0m");
                        double importeRetiro = scanner.nextDouble();
                        scanner.nextLine();

                        String resultadoRetiro = controlador.registrarRetiro(cuentaRetiro, importeRetiro);
                        System.out.println("\n" + resultadoRetiro);
                        break;

                    case 4:
                        System.out.println("\033[36m\n============================\033[0m");
                        System.out.println("\033[36m       TRANSFERENCIA        \033[0m");
                        System.out.println("\033[36m============================\033[0m");

                        System.out.print("\033[36m\nIngrese el número de cuenta origen: \033[0m");
                        String cuentaOrigen = scanner.nextLine();
                        System.out.print("\033[36mIngrese el número de cuenta destino: \033[0m");
                        String cuentaDestino = scanner.nextLine();
                        System.out.print("\033[36mIngrese el importe a transferir: \033[0m");
                        double importeTransferencia = scanner.nextDouble();
                        scanner.nextLine();

                        String resultadoTransferencia = controlador.registrarTransferencia(cuentaOrigen, cuentaDestino, importeTransferencia);
                        System.out.println("\n" + resultadoTransferencia);
                        break;

                    case 5:
                        System.out.println("\nSaliendo del sistema. ¡Gracias!");
                        break;

                    default:
                        System.out.println("\033[31mOpción no válida. Intente nuevamente.\033[0m");
                }

                System.out.println();
            } while (opcion != 5);
        } else {
            System.out.println("\033[31mAcceso denegado\033[0m");
        }

        scanner.close();
    }
}
