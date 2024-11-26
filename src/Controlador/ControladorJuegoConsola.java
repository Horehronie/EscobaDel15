package Controlador;

import Modelo.ModeloCarta;
import Modelo.ModeloJuego;
import Modelo.ModeloJugador;
import Vista.VistaConsola;

import java.util.ArrayList;

public class ControladorJuegoConsola {
    public ModeloJuego modelo;
    private VistaConsola vista;

    public ControladorJuegoConsola(VistaConsola vista, ModeloJuego modelo){
        this.vista = vista;
        this.modelo = modelo;

        modelo.agregarObserver(vista);
    }

    public void jugarPartida(){
        while(!modelo.hayGanador()) {
            while (modelo.getMazo().getSize() >= modelo.getJugadores().size() * 3 && !modelo.hayGanador()) {//suficientes cartas en mazo para repartir a todos
                modelo.repartirCartas();
                for (int i=0; i < modelo.getJugadores().size() * 3; i++) {//itera cantidad de jugadores * cantidad de cartas veces
                    ModeloJugador jugadorActual = modelo.getJugadorActual();
                    vista.mostrarMesa(modelo.getMesa());
                    vista.mostrarCartasJugador(jugadorActual);
                    jugarTurno(jugadorActual);
                    modelo.pasarTurno();

                }
                if (vista.guardarPartidaONo()) {
                    modelo.guardarPartida();
                    return;
                }
            }
            //cuenta de puntos de la ronda
            modelo.contarPuntos();
            modelo.renovarMazo();
        }
    }

    private void jugarTurno(ModeloJugador jugador) {
        int indice = vista.solicitarCartaAJugar(jugador.getCartasEnMano().size());
        ModeloCarta carta = jugador.probarUsarCarta(indice);
        ArrayList<ArrayList<ModeloCarta>> combinacionesPosibles = modelo.buscarCombinacion15(carta);

        if (combinacionesPosibles == null) {
            modelo.agregarCartaAMesa(carta);
        } else {
            ArrayList<ModeloCarta> combinacionJugador = getCombinacionJugador();
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

    private ArrayList<ModeloCarta> getCombinacionJugador() {
        ArrayList<ModeloCarta> combinacion = new ArrayList<>();
        int contadorCartasEnMesa = 0;
        ArrayList<Integer> valoresSeleccionados = new ArrayList<Integer>();

        while ((contadorCartasEnMesa < modelo.getMesa().getCartasEnMesa().size())) {
            int seleccionIndice = vista.solicitarCarta() - 1;

            if (seleccionIndice >= 0 && seleccionIndice < modelo.getMesa().getCartasEnMesa().size()) {
                combinacion.add(modelo.getMesa().getCartasEnMesa().get(seleccionIndice));

                if (vista.continuarCombinacion().equalsIgnoreCase("N")) break;
            } else {
                vista.errorVolverAIngresar();
            }
            contadorCartasEnMesa++;
        }

        return combinacion;
    }

}
