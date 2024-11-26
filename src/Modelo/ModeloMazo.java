package Modelo;
import java.io.Serializable;
import java.util.Collections;
import java.util.Stack;

public class ModeloMazo implements Serializable {
    private Stack<ModeloCarta> cartas;

    public ModeloMazo() {
        this.cartas = new Stack<>();
        for(ModeloCarta.Palo palo : ModeloCarta.Palo.values()){
            for(int numero = 1; numero <= 7; numero++){
                cartas.push(new ModeloCarta(numero, palo));
            }
            for(int numero=10; numero <= 12; numero++){
                cartas.push(new ModeloCarta(numero, palo));
            }
        }

        mezclarCartas();
    }

    public void mezclarCartas(){
        Collections.shuffle(cartas);
    }

    public ModeloCarta repartirCarta(){
        if(cartas.isEmpty()) return null;
        else return cartas.pop();
    }

    public int getSize(){
        return cartas.size();
    }
}
