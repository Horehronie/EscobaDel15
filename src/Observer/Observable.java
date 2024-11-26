package Observer;

public interface Observable {
    void agregarObserver(Observer o);
    void eliminarObserver(Observer o);
    void notificarObservers(String tipoActualizacion, Object objetoAActualizar);
}
