package Vista;

import Modelo.ModeloCarta;
import Modelo.ModeloJugador;
import Modelo.ModeloMesa;
import Observer.Observer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class VistaConsola implements Observer, Serializable {

    //observer
    public void actualizar(String tipo, Object o){
        switch(tipo){
            case "actualizacion_mesa":
                mostrarMesa((ModeloMesa) o);
                break;
            case "actualizacion_jugador":
                mostrarCartasJugador((ModeloJugador) o);
                mostrarCartasJuntadas((ModeloJugador) o);
                break;
            case "cambio_turno":
                pausa();
                limpiarConsola();
                System.out.println("Turno de: " + ((ModeloJugador) o).toString());
                break;
            case "puntos_jugadores":
                System.out.println("Cantidad actual de puntos: ");
                for(ModeloJugador jugador : ((ArrayList<ModeloJugador>) o)){
                    System.out.print(jugador.toString() + ": "  + jugador.getPuntos() + "\n");
                }
                pausa();
                break;
            case "hay_ganador":
                System.out.println("Ha ganado el jugador: " + ((ModeloJugador)o).toString() +
                        " con " + ((ModeloJugador)o).getPuntos() + " puntos.");
                System.out.println("Presiona enter para continuar.");
                pausa();
                break;
        }
    }
    //observer

    public int solicitarCarta() {
        Scanner scanner = new Scanner(System.in);
        int valor = 0;
        do {
            System.out.println("Ingrese el índice de la carta a retirar: ");
            try{
                valor = scanner.nextInt();
            }
            catch(Exception e){
                System.out.println("Ingreso inválido.");
                scanner.next();
            }
        } while (valor > 12 || valor < 1);
        return valor;
    }

    public int solicitarCartaAJugar(int cantidadCartasJugador){
        Scanner scanner = new Scanner(System.in);
        int valor = 0;
        do {
            System.out.println("Ingresar indice de carta a jugar: ");
            try{
                valor = scanner.nextInt();
                if(valor > cantidadCartasJugador || valor < 1) System.out.println("Ingreso inválido.");
            }
            catch(Exception e){
                System.out.println("Ingreso inválido.");
                scanner.next();
            }
        } while (valor > cantidadCartasJugador || valor < 1);
        return valor;
    }

    public String continuarCombinacion(){
        String respuesta;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("¿Desea seguir seleccionando cartas? (S/N)");
            respuesta = scanner.nextLine();
        } while (!respuesta.equalsIgnoreCase("N") && !respuesta.equalsIgnoreCase("S"));
        return respuesta;
    }

    public void errorVolverAIngresar(){
        System.out.println("Ingreso inválido.");
    }

    public void combinacionInvalida(){
        System.out.println("La combinación no es válida.");
    }

    public void pausa(){
        System.out.println("Presiona enter para continuar.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void mostrarMesa(ModeloMesa mesa){
        System.out.println("Mesa actual:");
        int i = 1;
        for(ModeloCarta carta : mesa.getCartasEnMesa()){
            System.out.println("-" + i + " " + carta.toString());
            i++;
        }
        System.out.println();
    }

    public void mostrarCartasJugador(ModeloJugador jugador){
        int i = 1;
        System.out.println("Cartas en mano de " + jugador.toString() + ":");
        for(ModeloCarta carta : jugador.getCartasEnMano()){
            System.out.println("-" + i + " " + carta.toString());
            i++;
        }
        System.out.println();
    }

    public void mostrarCartasJuntadas(ModeloJugador jugador){
        if(!jugador.getCartasJuntadas().isEmpty()) {
            System.out.println("Cartas juntadas por " + jugador.toString());
            for (ModeloCarta carta : jugador.getCartasJuntadas()) {
                System.out.println(carta.toString());
            }
        }
        else System.out.println("El jugador no juntó ninguna carta.");
        System.out.println();
    }

    public void limpiarConsola() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public Boolean guardarPartidaONo(){
        String respuesta;
        Boolean guarda = false;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("¿Desea cerrar y guardar la partida? (S/N)");
            respuesta = scanner.nextLine();
        } while (!respuesta.equalsIgnoreCase("N") && !respuesta.equalsIgnoreCase("S"));

        if(respuesta.equalsIgnoreCase("S")) guarda = true;

        return guarda;
    }


}
