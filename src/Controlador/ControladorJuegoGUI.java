package Controlador;

import Modelo.ModeloCarta;
import Modelo.ModeloJuego;
import Modelo.ModeloJugador;
import Vista.VistaGUI;

import java.util.ArrayList;

public class ControladorJuegoGUI {
    private ModeloJuego modelo;
    private VistaGUI vista;

    public ControladorJuegoGUI(ModeloJuego modelo, VistaGUI vista) {
        this.modelo = modelo;
        this.vista = vista;
        modelo.agregarObserver(vista);
    }

    public void jugarPartida(){
        while(!modelo.hayGanador()) {
            while (modelo.getMazo().getSize() >= modelo.getJugadores().size() * 3 && !modelo.hayGanador()) {//suficientes cartas en mazo para repartir a todos
                modelo.repartirCartas();
                for (int i=0; i < modelo.getJugadores().size() * 3; i++) {//itera cantidad de jugadores * cantidad de cartas veces
                    ModeloJugador jugadorActual = modelo.getJugadorActual();
                    vista.mostrarTurno(jugadorActual);
                    vista.setTurno(jugadorActual.toString());
                    vista.mostrarCartasJugador(jugadorActual);
                    vista.mostrarMesa(modelo.getMesa());
                    jugarTurno(jugadorActual);
                    modelo.pasarTurno();
                }
            }
            //cuenta de puntos de la ronda
            modelo.contarPuntos();
            modelo.renovarMazo();
        }
    }

    private void jugarTurno(ModeloJugador jugador){
        int indice = vista.solicitarCartaAJugar(jugador);
        ModeloCarta carta = jugador.probarUsarCarta(indice);
        ArrayList<ArrayList<ModeloCarta>> combinacionesPosibles = modelo.buscarCombinacion15(carta);

        if (combinacionesPosibles == null) {
            modelo.agregarCartaAMesa(carta);
        } else {
            ArrayList<ModeloCarta> combinacionJugador = vista.getCombinacion();
            int sumaDeCombinacion = carta.getNumero();
            for(ModeloCarta cartaActual : combinacionJugador){
                sumaDeCombinacion+=cartaActual.getNumero();
            }
            if(sumaDeCombinacion == 15){
                modelo.juntarCartas(carta, combinacionJugador);
            }
            else{
                vista.combinacionInvalida();
                jugarTurno(jugador);
            }
        }

        if(modelo.getMesa().isMesaVacia()) modelo.getJugadorActual().sumarPunto(); //si no quedan cartas es escoba = 1 punto

    }
}