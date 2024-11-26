package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ModeloJugador implements Serializable {
    private String nombre;
    private ArrayList<ModeloCarta> cartasEnMano;
    private ArrayList<ModeloCarta> cartasJuntadas;
    private int puntos;
    

    public ModeloJugador(String nombre){
        this.nombre = nombre;
        this.cartasEnMano = new ArrayList<>();
        this.cartasJuntadas = new ArrayList<>();
        this.puntos = 0;
    }

    public void agregarCarta(ModeloCarta carta){
        cartasEnMano.add(carta);
    }

    public ModeloCarta usarCarta(ModeloCarta carta){
        cartasEnMano.remove(carta);
        return carta;
    }

    public ModeloCarta usarCarta(int indice){
        ModeloCarta carta = cartasEnMano.remove(indice-1);
        if(carta != null) return carta;
        throw new IllegalArgumentException("El jugador no posee la carta especificada.");
    }

    public ModeloCarta probarUsarCarta(int indice){
        ModeloCarta carta = cartasEnMano.get(indice-1);
        if(carta != null) return carta;
        throw new IllegalArgumentException("El jugador no posee la carta especificada.");
    }

    public void sumarPunto(){
        this.puntos+=1;
    }

    public int getPuntos(){
        return this.puntos;
    }

    public ArrayList<ModeloCarta> getCartasEnMano(){
        return this.cartasEnMano;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCartasEnMano(ArrayList<ModeloCarta> cartasEnMano) {
        this.cartasEnMano = cartasEnMano;
    }

    public ArrayList<ModeloCarta> getCartasJuntadas() {
        return cartasJuntadas;
    }

    public void setCartasJuntadas(ArrayList<ModeloCarta> cartasJuntadas) {
        this.cartasJuntadas = cartasJuntadas;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void sumarPuntos(int cantidad){
        this.puntos += cantidad;
    }

    public void juntarCarta(ModeloCarta carta){
        cartasJuntadas.add(carta);
    }

    public String toString(){
        return nombre;
    }

    public int getCantidadDeCartas(){
        return cartasJuntadas.size();
    }

    public int getCantidadDeSietes(){
        int cantidad = 0;
        for(ModeloCarta carta : cartasJuntadas){
            if(carta.getNumero() == 7) cantidad+= 1;
        }
        return cantidad;
    }

    public int getCantidadDeOros(){
        int cantidad = 0;
        for(ModeloCarta carta : cartasJuntadas){
            if(carta.getPalo() == ModeloCarta.Palo.ORO) cantidad+= 1;
        }
        return cantidad;
    }

    public boolean tieneTodosLosSietesDeLaBaraja(){
        return getCantidadDeSietes() == 4; //un 7 por cada palo
    }

    public boolean tieneTodosLosOrosDeLaBaraja(){
        return getCantidadDeOros() == 10; // 1 al 7, 10, 11, 12 de oro

    }

    public boolean tieneElSieteDeOro(){
        boolean tiene = false;
        for(ModeloCarta carta : cartasJuntadas){
            if (carta.getNumero() == 7 && carta.getPalo() == ModeloCarta.Palo.ORO) {
                tiene = true;
                break;
            }
        }
        return tiene;
    }

    public boolean tieneAlgunSiete(){
        boolean tiene = false;
        for(ModeloCarta carta : cartasJuntadas){
            if (carta.getNumero() == 7) {
                tiene = true;
                break;
            }
        }
        return tiene;
    }

    public boolean tieneAlgunOro(){
        boolean tiene = false;
        for(ModeloCarta carta : cartasJuntadas){
            if (carta.getPalo() == ModeloCarta.Palo.ORO) {
                tiene = true;
                break;
            }
        }
        return tiene;
    }

}
