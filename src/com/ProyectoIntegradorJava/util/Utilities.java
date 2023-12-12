package com.ProyectoIntegradorJava.util;

import java.util.List;

public class Utilities {
    public static <T> void printElements(List<T> list){
        for (T element: list) {
            System.out.println(element);
            System.out.println("|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|");
        }
    };
}
