package org.example;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

class AssemblyLine extends Thread {
    private BlockingQueue<Components> buffer;

    public AssemblyLine(BlockingQueue<Components> buffer) {
        this.buffer = buffer;
    }

    public AssemblyLine() {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Components component = buffer.take();
                System.out.println("La línea de montaje ensambló un componente.");
                // Simular un tiempo de ensamblaje con variabilidad normal
                int assemblyTime = new Random().nextInt(100) + 100; // Mean: 200, StdDev: 100
                Thread.sleep(assemblyTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
    /*
    @Override
    public void run() {
        // Lógica de ensamblaje de componentes
        while (true) {
            try {
                Components component = buffer.take();
                System.out.println("Assembly Line assembled a component.");
                // Simular un tiempo de ensamblaje
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }*/


