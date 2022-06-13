/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheroAccesoDirecto;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Morad
 */
public class Main {

    public static void main(String[] args) {

        int posicion;
        int cantidad;
        double precio, importe;

        String[] valores;

        File f = new File("productos.dat");
        File f1 = new File("ventas.txt");

        try {
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr);
            RandomAccessFile raf = new RandomAccessFile(f, "rw");

            String cadena = br.readLine();

            while (cadena != null) {
                valores = cadena.split(" ");
                posicion = (Integer.parseInt(valores[0]) - 1) * 16;
                raf.seek(posicion + 4);
                cantidad = raf.readInt();
                precio = raf.readDouble();

                if (cantidad >= Integer.parseInt(valores[1])) {
                    importe = Integer.parseInt(valores[1]) * precio;
                    cantidad = cantidad - Integer.parseInt(valores[1]);
                    raf.seek(raf.getFilePointer() - 12);
                    raf.writeInt(cantidad);
                    System.out.println("El importe de la venta es " + importe);
                    System.out.println("Le quedan " + cantidad + " unidades de producto " + valores[0]);

                } else {
                    System.out.println("no hay suficiente stock");
                }
                cadena = br.readLine();

            }
            mostrarFicheroRaf(raf);
            raf.close();

        } catch (FileNotFoundException e) {
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void mostrarFicheroRaf(RandomAccessFile raf) {
        try {
            raf.seek(0);
            try {
                while (true) {

                    System.out.println("Codigo: " + raf.readInt() + " Cantidad: " + raf.readInt() + " Precio: " + raf.readDouble());

                }

            } catch (EOFException ef) {
                System.out.println("Fin del fichero");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
