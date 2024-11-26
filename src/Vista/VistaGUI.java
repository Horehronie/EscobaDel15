package Vista;

import Modelo.ModeloCarta;

import Modelo.ModeloJugador;
import Modelo.ModeloMesa;
import Observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaGUI extends JFrame implements Observer {
    private JPanel panelMesa;
    private JPanel panelTurno;
    private JPanel panelCartasEnMano;
    private JLabel turno;
    private int indiceCarta;
    ArrayList<ModeloCarta> combinacion = new ArrayList<>();

    public VistaGUI() {
        iniciarVentana();
    }

    private void iniciarVentana() {
        // Configuración de la ventana principal
        setTitle("La Escoba del 15");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);

        // Panel de la mesa
        panelMesa = new JPanel();
        panelMesa.setBorder(BorderFactory.createTitledBorder("Mesa"));
        add(panelMesa, BorderLayout.CENTER);

        panelCartasEnMano = new JPanel();
        panelCartasEnMano.setBorder(BorderFactory.createTitledBorder("Elija una carta para jugar: "));
        add(panelCartasEnMano, BorderLayout.SOUTH);
        setVisible(true); // Mostrar la ventana


    }


    public void setTurno(String jugador){
        if (panelTurno != null) {
            remove(panelTurno); // Remover el panel anterior
        }
        panelTurno = new JPanel();
        turno = new JLabel("Turno de: " + jugador);
        panelTurno.add(turno);
        add(panelTurno, BorderLayout.NORTH);
        revalidate();
        repaint();
    }


    @Override
    public void actualizar(String tipo, Object objeto) {
        switch (tipo) {
            case "actualizacion_mesa":
                mostrarMesa((ModeloMesa) objeto);
                break;
            case "actualizacion_jugador":
                mostrarCartasJugador((ModeloJugador) objeto);
                break;
            case "cambio_turno":
                mostrarTurno((ModeloJugador) objeto);
                break;
            case "puntos_jugadores":
                mostrarPuntos((ArrayList<ModeloJugador>) objeto);
                break;
            case "hay_ganador":
                mostrarGanador((ModeloJugador) objeto);
                break;
        }
    }

    public void mostrarCartasJugador(ModeloJugador jugador) {
        panelCartasEnMano.removeAll(); // Limpiar el panel
        int i = 0;
        for (ModeloCarta carta : jugador.getCartasEnMano()) {
            JButton boton = new JButton(carta.toString());

        }
        panelCartasEnMano.revalidate();
        panelCartasEnMano.repaint();
    }

    public void mostrarTurno(ModeloJugador jugador) {
        JOptionPane.showMessageDialog(this, "Turno de: " + jugador.toString());
    }

    private void mostrarPuntos(ArrayList<ModeloJugador> jugadores) {
        // Mostrar una ventana con los puntos de cada jugador
    }

    private void mostrarGanador(ModeloJugador jugador) {
        JOptionPane.showMessageDialog(this, "¡Ganador: " + jugador.toString() + "!");
    }


    public int solicitarCartaAJugar(ModeloJugador jugador){
        panelCartasEnMano.removeAll();
        JButton botonCarta;
        ArrayList<JButton> botones = new ArrayList<>(); // Lista para almacenar los botones

        for (int i = 0; i < jugador.getCartasEnMano().size(); i++) {
            String carta = jugador.getCartasEnMano().get(i).toString();
            botonCarta = new JButton(carta);
            botonCarta.setActionCommand(String.valueOf(i));
            botones.add(botonCarta);
            botonCarta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int indice = Integer.parseInt(e.getActionCommand());
                    indiceCarta = indice;  // Guardar el índice
                    System.out.println("Carta seleccionada: " + indiceCarta);
                    for (JButton boton : botones) {
                        boton.setEnabled(false);
                    }
                }
            });

            panelCartasEnMano.add(botonCarta);  // Añadir el botón al panel
        }

        panelCartasEnMano.revalidate();  // Actualizar la interfaz
        panelCartasEnMano.repaint();

        // Esperar o realizar alguna acción adicional para que el índice sea recuperado
        return indiceCarta;  // Devolver el índice de la carta seleccionada
    }

    public void mostrarMesa(ModeloMesa mesa){
        panelMesa.removeAll(); // Limpiar el panel

        for (ModeloCarta carta : mesa.getCartasEnMesa()) {
            JButton boton = new JButton(carta.toString());
            panelMesa.add(boton);
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        combinacion.add(carta);
                        System.out.println(carta.toString());
                        boton.setEnabled(false);
                }
            });
        }
        panelMesa.revalidate();
        panelMesa.repaint();
    }

    public ArrayList<ModeloCarta> getCombinacion(){
        return combinacion;
    }

    public void combinacionInvalida(){
        JOptionPane.showMessageDialog(this, "Combinacion inválida.");
    }


}

