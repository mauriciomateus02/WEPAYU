package br.ufal.ic.p2.wepayu.utils;

public class conversorCaracter {

    public static String conversor(String str) {

        str = str.replace('.', ',');
        return str;
    }

    public static String conversorInvert(String str) {

        str = str.replace(',', '.');
        return str;
    }
}
