package com.ejemplo;

public class DescuentoRepository {

    public double obtenerPorcentaje(String codigo) {
        if (codigo.equals(10)) return 0.10;
        return 0.0;
    }

}