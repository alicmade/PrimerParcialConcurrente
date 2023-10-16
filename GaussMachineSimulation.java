package org.example;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.*;
import java.awt.*;

public class GaussMachineSimulation {
    public static void lanador(String[] args) {
        int numStations = 5; // Número de estaciones de trabajo
        int bufferSize = 10; // Tamaño del búfer compartido
        int numAssemblyThreads = 2; // Número de hilos en la línea de ensamblaje


        // Búfer compartido para componentes
        BlockingQueue<Components> buffer = new LinkedBlockingQueue<>(bufferSize);

        // Inicialización de las estaciones de trabajo (productores) con ExecutorService
        ExecutorService stationExecutor = Executors.newFixedThreadPool(numStations);
        for (int i = 0; i < numStations; i++) {
            stationExecutor.submit(new Station("Station " + i, buffer));
        }

        // Inicialización de la línea de ensamblaje (consumidor) con ExecutorService
        ExecutorService assemblyExecutor = Executors.newFixedThreadPool(numAssemblyThreads);
        for (int i = 0;  i < numAssemblyThreads; i++) {
            assemblyExecutor.submit(new AssemblyLine(buffer));
        }

        // Inicialización de las estaciones de trabajo (productores)
        Station[] stations = new Station[numStations];
        for (int i = 0; i < numStations; i++) {
            stations[i] = new Station("Station " + i, buffer);
        }

        JFrame frame = new JFrame("Simulation Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear una ventana de Swing para mostrar la gráfica
        GraphPanel graphPanel = new GraphPanel();
        frame.add(graphPanel);
        frame.pack();
        frame.setVisible(true);
        // Inicialización de la línea de ensamblaje (consumidor)
        AssemblyLine assemblyLine = new AssemblyLine(buffer);

        // Iniciar hilos de estaciones de trabajo
        for (Station station : stations) {
            station.start();
        }

        // Iniciar hilo de la línea de ensamblaje
        assemblyLine.start();

        try (FileWriter writer = new FileWriter("simulacion.txt")) {
            int componentCount = 0;
            Random random = new Random();
            while (true) {
                Components component = buffer.take();
                componentCount++;
                String log = "Component " + componentCount + " produced";
                writer.write(log + "\n");

                // Simular un tiempo de ensamblaje con variabilidad normal
                int assemblyTime = (int) (random.nextGaussian() * 50 + 200); // Mean: 200, StdDev: 50
                Thread.sleep(assemblyTime);

                component.markAsAssembled();
                log = "Component " + componentCount + " assembled by Assembly Line";
                writer.write(log + "\n");
                writer.flush();

                graphPanel.updateGraph(componentCount);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        // Realizar un seguimiento de la simulación y guardar registros en un archivo
     /*   try (FileWriter writer = new FileWriter("simulacion.txt")) {
            int componentCount = 0;
            while (true) {
                // Componente producido
                Components component = buffer.take();
                componentCount++;
                String log = "Component " + componentCount + " produced";
                writer.write(log + "\n");

                // Simular un tiempo de ensamblaje
                Thread.sleep(1000); // Puedes ajustar este valor según la velocidad deseada

                // Componente ensamblado
                component.markAsAssembled();
                log = "Component " + componentCount + " assembled by Assembly Line";
                writer.write(log + "\n");
                writer.flush();

                // Actualizar y mostrar la gráfica en la ventana de Swing
                graphPanel.updateGraph(componentCount);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    // Clase para mostrar la gráfica en una ventana de Swing
    static class GraphPanel extends JPanel {
        private int componentCount = 0;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGraph(g);
        }

        private void drawGraph(Graphics g) {
            int width = getWidth();
            int height = getHeight();
            int barWidth = 20;
            int barHeight = componentCount;
            int x = 50;
            int y = height - barHeight;

            g.setColor(Color.BLUE);
            g.fillRect(x, y, barWidth, barHeight);
        }

        public void updateGraph(int componentCount) {
            this.componentCount = componentCount;
            repaint();
        }

    }
}
