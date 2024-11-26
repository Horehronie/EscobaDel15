package Modelo;

import Observer.Observable;
import Observer.Observer;
import Serializacion.AddableObjectOutputStream;

import java.io.*;
import java.util.ArrayList;

public class ModeloJuego implements Observable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private ArrayList<Observer> observers = new ArrayList<>();

    private ArrayList<ModeloJugador> jugadores;
    private ModeloMazo mazo;
    private int turnoActual;
    private ModeloMesa mesa;
    private final int puntosParaGanar = 21;

    public ModeloJuego(String jugador1, String jugador2, String jugador3, String jugador4){
        jugadores = new ArrayList<>();
        mesa = new ModeloMesa();
        jugadores.add(new ModeloJugador(jugador1));
        jugadores.add(new ModeloJugador(jugador2));
        jugadores.add(new ModeloJugador(jugador3));
        jugadores.add(new ModeloJugador(jugador4));

        mazo = new ModeloMazo();
        turnoActual = 0;

        iniciarRonda();
    }

    public ModeloJuego(){ //para pruebas
        jugadores = new ArrayList<>();
        mesa = new ModeloMesa();

        for(int i=1; i <= 4; i++){
            jugadores.add(new ModeloJugador("Jugador " + i));
        }

        mazo = new ModeloMazo();
        turnoActual = 0;

        iniciarRonda();
    }

    //observer
    public void agregarObserver(Observer o){
        observers.add(o);
    }

    public void eliminarObserver(Observer o){
        observers.remove(o);
    }

    public void notificarObservers(String tipoActualizacion, Object objetoAActualizar){
        for(Observer o : observers){
            o.actualizar(tipoActualizacion, objetoAActualizar);
        }
    }
    //observer


    public void repartirCartas() {
        if(mazo.getSize() >= jugadores.size() * 3) {
            for (ModeloJugador jugador : jugadores) {
                for (int i = 0; i < 3; i++) {
                    jugador.agregarCarta(mazo.repartirCarta());
                }
            }
        }
    }

    private void iniciarRonda(){
        for(int i = 0; i < 4; i++){
            mesa.agregarCarta(mazo.repartirCarta());
        }
    }

    public ModeloMesa getMesa(){
        return mesa;
    }


    public ArrayList<ArrayList<ModeloCarta>> buscarCombinacion15(ModeloCarta cartaJugador) {
        ArrayList<ArrayList<ModeloCarta>> combinacionesValidas = new ArrayList<>();

        for (int i = 1; i <= mesa.getCartasEnMesa().size(); i++) {
            ArrayList<ArrayList<ModeloCarta>> combinaciones = generarCombinaciones(mesa.getCartasEnMesa(), i);
            for (ArrayList<ModeloCarta> combinacion : combinaciones) {
                int suma = cartaJugador.getNumero();
                for (ModeloCarta c : combinacion) {
                    suma += c.getNumero();
                }
                if (suma == 15) {
                    combinacionesValidas.add(combinacion);
                }
            }
        }

        return combinacionesValidas.isEmpty() ? null : combinacionesValidas;
    }

    private ArrayList<ArrayList<ModeloCarta>> generarCombinaciones(ArrayList<ModeloCarta> cartas, int n) {
        ArrayList<ArrayList<ModeloCarta>> combinaciones = new ArrayList<>();
        generarCombinacionesRecursivo(cartas, n, 0, new ArrayList<>(), combinaciones);
        return combinaciones;
    }

    private void generarCombinacionesRecursivo(ArrayList<ModeloCarta> cartas, int n, int start, ArrayList<ModeloCarta> temp, ArrayList<ArrayList<ModeloCarta>> combinaciones) {
        if (temp.size() == n) {
            combinaciones.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i < cartas.size(); i++) {
            temp.add(cartas.get(i));
            generarCombinacionesRecursivo(cartas, n, i + 1, temp, combinaciones);
            temp.remove(temp.size() - 1);
        }
    }

    public ModeloJugador getJugadorActual(){
        return jugadores.get(turnoActual);
    }

    public ArrayList<ModeloJugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<ModeloJugador> jugadores) {
        this.jugadores = jugadores;
    }

    public ModeloMazo getMazo() {
        return mazo;
    }

    public void setMazo(ModeloMazo mazo) {
        this.mazo = mazo;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }

    public void setMesa(ModeloMesa mesa) {
        this.mesa = mesa;
    }

    public void agregarCartaAMesa(ModeloCarta carta) {
        mesa.agregarCarta(carta);
        getJugadorActual().usarCarta(carta);
        notificarObservers("actualizacion_mesa", mesa);
        notificarObservers("actualizacion_jugador", getJugadorActual());
    }

    public void juntarCartas(ModeloCarta carta, ArrayList<ModeloCarta> combinacion) {
        for (ModeloCarta cartaActual : combinacion) {
            getJugadorActual().juntarCarta(cartaActual);
            getMesa().retirarCarta(cartaActual);
        }
        getJugadorActual().juntarCarta(carta);
        getJugadorActual().usarCarta(carta);

        notificarObservers("actualizacion_mesa", mesa);
        notificarObservers("actualizacion_jugador", getJugadorActual());
    }

    public void pasarTurno(){
        this.turnoActual = (turnoActual+1) % this.jugadores.size();
        notificarObservers("cambio_turno", getJugadorActual());
    }


    public boolean validarCombinacion(ArrayList<ModeloCarta> combinacionJugador,
                                      ArrayList<ArrayList<ModeloCarta>> combinacionesPosibles){

        for(ArrayList<ModeloCarta> combinacion : combinacionesPosibles){
            if(combinacion.containsAll(combinacionJugador) && combinacionJugador.containsAll(combinacion)) return true;
        }
        return false;
    }



    public void contarPuntos(){
        ModeloJugador tieneMasCartas = jugadores.get(0);
        ModeloJugador tieneMasSietes = jugadores.get(0);
        ModeloJugador tieneMasOros = jugadores.get(0);

        ModeloJugador jugadorAnterior = jugadores.get(0);

        for(ModeloJugador jugador : jugadores){
            if(jugador.tieneElSieteDeOro()) jugador.sumarPunto();

            if(jugador.getCantidadDeCartas() > tieneMasCartas.getCantidadDeCartas()) tieneMasCartas = jugador;

            if(jugador.getCantidadDeSietes() > tieneMasSietes.getCantidadDeSietes()) tieneMasSietes = jugador;

            if(jugador.getCantidadDeOros() > tieneMasOros.getCantidadDeOros()) tieneMasOros = jugador;

        }

        tieneMasCartas.sumarPunto();

        if(tieneMasSietes.tieneAlgunSiete()) tieneMasSietes.sumarPunto();

        if(tieneMasOros.tieneAlgunOro()) tieneMasOros.sumarPunto();

        if(tieneMasSietes.tieneTodosLosSietesDeLaBaraja()) tieneMasSietes.sumarPunto();

        if(tieneMasOros.tieneTodosLosOrosDeLaBaraja()) tieneMasOros.sumarPuntos(2);

        notificarObservers("puntos_jugadores", jugadores);
    }

    public boolean hayGanador(){
        boolean hay = false;
        for(ModeloJugador jugador : jugadores){
            if(jugador.getPuntos() >= puntosParaGanar){
                hay = true;
                notificarObservers("hay_ganador", jugador);
                break;
            }
        }
        return hay;
    }


    public void renovarMazo(){
        mazo = new ModeloMazo();
    }

    public String toString(){
        return "Partida de " + getJugadores().toString();
    }

    public void guardarPartida(){
        try{
            File file = new File("partidas.dat");
            boolean agregar = file.exists() && file.length() > 0;

            ObjectOutputStream oos = agregar
                    ? new AddableObjectOutputStream(new FileOutputStream(file, true))
                    : new ObjectOutputStream(new FileOutputStream(file));

            oos.writeObject(this);
            oos.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }





}
