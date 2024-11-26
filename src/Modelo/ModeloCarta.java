package Modelo;

import java.io.Serializable;

public class ModeloCarta implements Serializable {

    public enum Palo {
        ORO("Oro"),
        COPA("Copa"),
        BASTO("Basto"),
        ESPADA("Espada");

        private String nombre;

        Palo(String nombre) {
            this.nombre = nombre;
        }

        public String getString() {
            return nombre;
        }
    }

    private int numero;
    private Palo palo;

    public ModeloCarta(int numero, Palo palo){
        this.numero = numero;
        this.palo = palo;
    }

    public Palo getPalo(){
        return this.palo;
    }

    public int getNumero(){
        return this.numero;
    }

    public String toString(){
        return numero + " de " + palo.getString();
    }

}
