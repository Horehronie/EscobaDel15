package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ModeloMesa implements Serializable {
    private ArrayList<ModeloCarta> cartasEnMesa;

    public ModeloMesa(){
        this.cartasEnMesa = new ArrayList<>();
    }

    public void agregarCarta(ModeloCarta carta) {
        cartasEnMesa.add(carta);
    }

    public void removerTodasLasCartas(ArrayList<ModeloCarta> cartas) {
        cartasEnMesa.removeAll(cartas);
    }

    public void retirarCarta(int indice){
        cartasEnMesa.remove(indice);
    }

    public void retirarCarta(ModeloCarta carta){
        cartasEnMesa.remove(carta);
    }

    public ArrayList<ModeloCarta> getCartasEnMesa() {
        return cartasEnMesa;
    }

    public boolean isCartaEnMesa(ModeloCarta carta){
        return cartasEnMesa.contains(carta);
    }

    public boolean isMesaVacia(){
        return cartasEnMesa.isEmpty();
    }



}
