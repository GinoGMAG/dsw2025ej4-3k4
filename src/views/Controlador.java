package views;

import data.Persistencia;
import domain.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controlador {
    public static TipoAlimentacion[] getTiposAlimentacion(){
        return  TipoAlimentacion.values();
    }
    public static ArrayList<Especie> getEspecies(){
        return Persistencia.getEspecies();
    }
    public static ArrayList<Sector> getSectores(){
        return Persistencia.getSectores();
    }
    public static ArrayList<Pais> getPais(){
        return Persistencia.getPaises();
    }
    public static javax.swing.JFrame view;

    public static JFrame getView() {
        return view;
    }

    public static void setView(JFrame view) {
        Controlador.view = view;
    }
    
    
    
    public static ArrayList<AnimalViewModel> getAnimales(){
        ArrayList<AnimalViewModel> animales = new ArrayList<>();
        for(Mamifero animal : Persistencia.getAnimales()){
            animales.add(new AnimalViewModel(animal));
        }
        return animales;
    }
    
    public static ComidaViewModel  calcularComida(){
        double totalCarnivoros = Persistencia.getTotalComida(TipoAlimentacion.CARNIVORO);
        double totalHerbivoros = Persistencia.getTotalComida(TipoAlimentacion.HERBIVORO);
        return new ComidaViewModel(totalCarnivoros, totalHerbivoros);
    }
    
    public static void CargarAnimales(Especie especie,Pais pais, Sector sector , String edad,String peso, String valorFijo){
        try{
            if(especie.getTipoAlimentacion().esCarnivoro()){
                Mamifero carnivoro = new Carnivoro(Integer.parseInt(edad),Double.parseDouble(peso),especie,sector,pais);
                Persistencia.CargarAnimales(carnivoro);
                JOptionPane.showMessageDialog(null,"SE CARGO UN ANIMAL CARNIVORO");
            }else if (especie.getTipoAlimentacion().esHerbivoro()){
                Mamifero herbivoro = new Herbivoro(Integer.parseInt(edad),Double.parseDouble(peso),especie,sector,Double.parseDouble(valorFijo),pais);
                Persistencia.CargarAnimales(herbivoro);
                JOptionPane.showMessageDialog(null, "Se Cargo Un Animal Herbivoro" );
            }else{
                
            }
            
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
                
    }
    
    public static void VerficarCargaDeDatos(String especie,String pais, String sector , String edad,String peso, String valorFijo){
        Especie especieEncontrada = null;
        Pais paisEncontrado = null;
        Sector sectorEncontrado = null;
        
        ArrayList<Especie> especies = Controlador.getEspecies();
        for(Especie especieFor : especies){
            if(especieFor.getNombre().equals(especie)){
                especieEncontrada = especieFor;
            }
        }
        ArrayList<Pais> paises = Controlador.getPais();
        for(Pais paisFor : paises){
            if (paisFor.getNombre().equals(pais)){
                paisEncontrado = paisFor;
            }
        }
        ArrayList<Sector> sectores = Controlador.getSectores();
        for(Sector sectorFor : sectores){
            if (sectorFor.getNumero() == Integer.parseInt(sector)){
                sectorEncontrado = sectorFor;
            }
        }
        boolean bandera = true;
        try {
        if (especieEncontrada == null){
            JOptionPane.showMessageDialog(null,"Seleccione una especie Valida");
            bandera = false;
        }
        if (paisEncontrado == null){
            JOptionPane.showMessageDialog(null,"Seleccione un Pais Valido");
            bandera = false;
        }
        if (sectorEncontrado == null){
            JOptionPane.showMessageDialog(null,"Seleccione un sector Valido");
            bandera = false;
        }
        if (edad == null || Integer.parseInt(edad) < -1 || Integer.parseInt(edad) > 150 ){
            JOptionPane.showMessageDialog(null,"Introdusca una edad valida");
            bandera = false;
        }
        if (peso == null || Integer.parseInt(peso) < 0 ){
            JOptionPane.showMessageDialog(null,"Introdusca un peso valido");
            bandera = false;
        }
        if ( especieEncontrada.getTipoAlimentacion().esHerbivoro() == true ){
            if (valorFijo == null || Double.parseDouble(valorFijo) < -1 ){
                JOptionPane.showMessageDialog(null,"Introdusca un ValorFijo valido");
                bandera = false;
            }
        }
        if (bandera){
            Controlador.CargarAnimales(especieEncontrada,paisEncontrado,sectorEncontrado,edad,peso,valorFijo);
        }
    }  catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }   
}
