package org.example;

public class Components {
    private int id;  // Identificador único del componente
    private boolean isProduced; // Indica si el componente ha sido producido
    private boolean isAssembled; // Indica si el componente ha sido ensamblado

    public Components(int id) {
        this.id = id;
        this.isProduced = false;
        this.isAssembled = false;
    }

    // Método para marcar el componente como producido
    public void markAsProduced() {
        isProduced = true;
    }

    // Método para marcar el componente como ensamblado
    public void markAsAssembled() {
        isAssembled = true;
    }

    // Método para verificar si el componente ha sido producido
    public boolean isProduced() {
        return isProduced;
    }

    // Método para verificar si el componente ha sido ensamblado
    public boolean isAssembled() {
        return isAssembled;
    }

    // Otros atributos y métodos relacionados con el componente
    public int getId() {
        return id;
    }
}

