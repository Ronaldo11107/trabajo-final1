package pe.edu.upeu;

import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Producto> prod=new ArrayList<>();
        prod.add(new Producto("P001","Televisor",4000,20));
        prod.add(new Producto("P002","Parlantes",2000));
        var px= (new Producto("P003","Celular",3500));
        var cantidad = 54.5;
        prod.add(px);
        for ( Producto p: prod) {
            System.out.println(p.nombre+"\t"+p.precio+"\t"+p.stock+
                    "\t"+p.igv);
            System.out.println(px.getClass());
            System.out.println(cantidad);
            System.out.println(cantidad instanceof Double);
        }





    }
}