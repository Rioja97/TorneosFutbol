package com.example.GestionTorneos.excepcion;

public class SinEstadisticasException extends RuntimeException {
    public SinEstadisticasException(String message) {
        super(message);
    }
}
