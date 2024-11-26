import Controlador.ControladorJuegoConsola;
import Modelo.ModeloJuego;
import Modelo.ModeloJugador;
import Vista.VistaConsola;
import Vista.VistaGUI;
import Serializacion.Serializador;
import Serializacion.AddableObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
                System.out.println("Bienvenido a La Escoba del 15!");
                int entrada;
                Scanner scanner = new Scanner(System.in);
                do {
                        System.out.println("Ingrese una opción para continuar:");
                        System.out.println("1: Partida nueva");
                        System.out.println("2: Top 5 jugadores");
                        System.out.println("3: Reanudar partida");
                        System.out.println("4: Partida de prueba");
                        System.out.println("Otro: Salir");
                        entrada = scanner.nextInt();

                    switch (entrada) {
                        case 1 -> {
                            ArrayList<String> jugadores = new ArrayList<>();
                            String nombre;
                            for (int i = 1; i <= 4; i++) {
                                System.out.println("Ingrese el nombre del jugador " + i + ": ");
                                nombre = scanner.next();
                                jugadores.add(nombre);
                            }
                            ControladorJuegoConsola partida = new ControladorJuegoConsola(new VistaConsola(),
                                    new ModeloJuego(jugadores.get(0), jugadores.get(1), jugadores.get(2), jugadores.get(3)));
                            partida.jugarPartida();
                        }
                        case 2 -> {
                            ArrayList<ModeloJugador> jugadores = getTopPuntos();
                            if(!jugadores.isEmpty()){
                                for (int i = 0; i < jugadores.size(); i++) {
                                    System.out.println(i+1 + ": " + jugadores.get(i).toString() + " - " + jugadores.get(i).getPuntos() + " puntos.");
                                }
                            }
                            Scanner scanner2 = new Scanner(System.in);
                            scanner2.nextLine();
                        }
                        case 3 -> {
                            cargarPartida();
                        }
                        case 4 -> juegoPrueba();
                        default -> {
                        }
                    }

                } while (entrada == 1 || entrada == 2 || entrada == 3 || entrada == 4);



        }

        private static void juegoPrueba() {
                ControladorJuegoConsola juego = new ControladorJuegoConsola(new VistaConsola(), new ModeloJuego());

                juego.jugarPartida();

        }

        private static ArrayList<ModeloJuego> getPartidas(){
                ArrayList<ModeloJuego> partidas = new ArrayList<>();
                try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("partidas.dat"))){
                        while(true){
                                ModeloJuego partidaActual = (ModeloJuego) ois.readObject();
                                partidas.add(partidaActual);
                        }
                }catch(EOFException e){

                }
                catch(Exception e){
                        e.printStackTrace();
                }

                return partidas;
        }

        private static void cargarPartida(){
            Scanner scanner = new Scanner(System.in);
            ArrayList<ModeloJuego> partidas = getPartidas();
            if (partidas.isEmpty()) {
                System.out.println("No hay partidas guardadas.");
            } else {
                System.out.println("Ingrese el indice de la partida a recuperar:");
                int i = 1;
                for (ModeloJuego partida : partidas) {
                    System.out.println(i + ": " + partida.toString());
                    i++;
                }
                int indice = scanner.nextInt();
                if(indice > 0 && indice <= partidas.size()){
                    ControladorJuegoConsola partidaRecuperada =
                            new ControladorJuegoConsola(new VistaConsola(), partidas.get(indice-1));
                    partidaRecuperada.jugarPartida();
                }
            }
        }

        private static ArrayList<ModeloJugador> getTopPuntos(){
            ArrayList<ModeloJuego> partidas = getPartidas();
            ArrayList<ModeloJugador> jugadores = new ArrayList<>();
            if(partidas.isEmpty()) System.out.println("No hay partidas cargadas");
            else{
                for(ModeloJuego partida : partidas){
                    jugadores.addAll(partida.getJugadores());


                }
                Collections.sort(jugadores, new Comparator<ModeloJugador>() {
                    @Override
                    public int compare(ModeloJugador o1, ModeloJugador o2) {
                        return Integer.compare(o2.getPuntos(), o1.getPuntos());
                    }
                });
            }
            return jugadores;
        }

}
