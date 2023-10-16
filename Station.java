package org.example;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

class Station extends Thread {
    private String name;
    private BlockingQueue<Components> buffer;
    private int componentCounter; // Contador para asignar un ID único a cada componente

    public Station(String name, BlockingQueue<Components> buffer) {
        this.name = name;
        this.buffer = buffer;
        this.componentCounter = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Components component = new Components(componentCounter++);
                buffer.put(component);
                System.out.println(name + " componente producido con id: " + component.getId());
                // Simular un tiempo de producción con variabilidad normal
                int productionTime = new Random().nextInt(50) + 50; // Mean: 100, StdDev: 50
                Thread.sleep(productionTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}







/*@Override
    public void run() {
        // Lógica de producción de componentes
        while (true) {
            try {
                Components component = new Components(componentCounter++);
                buffer.put(component);
                System.out.println(name + " componente producido con id: " + component.getId());
                // Simular un tiempo de producción
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}*/

