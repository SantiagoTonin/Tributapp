package com.egg.tributapp.servicios;

import java.util.Comparator;

import com.egg.tributapp.entidades.Comentario;

public class Comparadores {
  public static Comparator<Comentario> ordenAsc = new Comparator<Comentario>() {
    @Override
    public int compare(Comentario co1,Comentario co2){
        return co2.getFecha().compareTo(co1.getFecha());
    }
};
}
