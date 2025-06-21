package com.example.GestionTorneos.excepcion;

public class ListaVaciaException extends RuntimeException {
    public ListaVaciaException(String message) {
        super(message);
    }
}
