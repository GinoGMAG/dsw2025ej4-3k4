package views;

import data.Persistencia;
import domain.TipoAlimentacion;

import javax.swing.*;
import java.util.InvalidPropertiesFormatException;

public class Program {

    public static void main(String[] args) throws IllegalArgumentException, InvalidPropertiesFormatException {
        Persistencia.inicializar();
        //ListarAnimalesView view = new ListarAnimalesView();
        //CargarAnimalesView view = new CargarAnimalesView();
        //view.setVisible(true);
        Controlador.view = new MenuView();
        Controlador.MostrarVistaMenu();
    }
}
