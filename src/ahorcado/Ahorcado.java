/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahorcado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author arturv
 */
public class Ahorcado {

    static String[] palabras = new String[10];
    /**
     * @param args the command line arguments
     */
    
    //Muestra los datos de la array de palabras
    private static void mostrarpalabras()
    {
        for(String dato:palabras)
        {
            //Omite los elementos no rellenados o inicializados en null
            //Puedo utilizar null sin problemas ya que no hay opción de eliminar palabras
            if(dato!=null)
            {
                System.out.println(dato);
            }
        }
    }
    
    //Comprueba si existe un elemento en la array
    private static boolean existe(String palabra)
    {
        boolean sal=false,encontrado=false;
        int cont=0;
        while(!sal)
        {
            if(cont<palabras.length)
            {
                //Omite los elementos no inicializados
                if(palabras[cont]!=null)
                {
                    //Pasa a mayúsculas todo para no diferenciar
                    if(palabras[cont].toUpperCase().equals(palabra.toUpperCase()))
                    { //No se distinguen mayúsculas y minúsculas
                        encontrado=true;
                        sal=true;
                        //Si se encuentra la palabra sale del bucle y devuelve true
                    }
                    else
                    {
                        cont++;
                    }
                } 
                else
                {//Cuando encuentra un elemento nulo para de buscar porque nunca hay un elemento lleno después de uno nulo en este programa
                    sal=true;
                }
            }
            else
            {
                sal=true;
            }          
        }
       
        return encontrado;
    }
    
    //Detecta la primera posición libre del array o si está llena devuelve -1
    private static int posicionnueva()
    {
        boolean sal=false, encontrado=false;
        int cont =0, posicion;
        while(!sal)
        {
           if(cont<palabras.length)
           {
               if(palabras[cont]==null)
               {
                   //Encuentra la primera posición en null y sale del bucle
                   encontrado=true;
                   sal=true;
               }
               else
               {
                   cont++;
               }
           }
           else
           {
               //Ha recorrido toa la array sin encontrar nulls
               sal=true;
           }
        }
        if(encontrado)
        {
            //Si se ha encontrado un null se devuelve su posición
            posicion=cont;
        }
        else
        {
            //Si no se ha encontrado un null se devuelve -1 La array está llena
            posicion=-1;
        }
        return posicion;
    }
    
    //Detecta si un String contiene solo letras
    private static boolean sonletras(String palabra)
    {
        char caracter;
        boolean sal=false,encontrado=false;
        int cont=0;
        while(!sal)
        {
            //Se recorre el String
            if(cont<palabra.length())
            {
                caracter=palabra.charAt(cont);
                if ((caracter >= 'a' && caracter <= 'z') || (caracter >= 'A' && caracter <= 'Z'))
                {
                    //Si se encuentra una letra del alfabeto se continua recorriendo el string.
                    cont++; 
                }
                else
                {
                    //Si se encuentra una letra que no es del alfabeto se para de recorrer el string y se devuelve false
                    encontrado=true;
                    sal=true;
                }
            }
            else
            {
                sal=true;
            }
        }
        //Se devuelve el inverso se encontrado
        //Si se ha encontrado un caracter que no es una letra se devuelve false
        //Si no se ha encontrado un caracter que no es una letra se devuelve true
        return !encontrado;     
    }
   
    //Añade una palabra a la array pidiéndosela al usuario
    private static void anadirpalabra()
    {
        System.out.println("Introduce la palabra que deseas añadir.");
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        String palabra;
        int posicion = posicionnueva(); //Se obtiene la posición del primer elemento null
        if(posicion==-1)
        {
            //Si no hay elementos null la array está llena
            System.out.println("La array está llena no se pueden añadir más palabras.");
        }
        else
        {
            //Si se obtiene una posición en null se procede
            try
            {
                palabra = lector.readLine(); //El usuario introduce la palabra
                if(palabra.length()>=3 && palabra.length()<=10)
                {
                    //Se procede si la longitud de la palabra es entre 3 y 10
                    if(sonletras(palabra))
                    {
                        //se procede si la palabra contiene solo letras del alfabeto
                        if(existe(palabra))
                        {
                            //Si la palabra existía en la array se avisa y no se añade
                            System.out.println("La palabra ya estaba en la lista.");
                        }
                        else
                        {
                            //si la palabra no estaba en la array se añade
                            palabras[posicion]=palabra;
                            System.out.println("Palabra añadida.");
                        }
                    }
                    else
                    {
                        System.out.println("La palabra contiene caracteres inválidos.");
                    }
                }
                else
                {
                    System.out.println("Longitud de palabra incorrecta.");
                }
            }
            catch(Exception e){
                System.out.println("Error."); 
            }
        }
    }
    
    //Elige una de las palabras existentes al azar y devuelve la posición
    private static int eligenombre()
    {
        int limite = posicionnueva();
        //Obtiene la posición del primer elemento null o si no hay
        if (limite==-1)
        {
            //Si no hay elementos null el limite de los elementos es el final del array
            limite = palabras.length-1;
        }
        else
        {
            //Si hay elementos null el limite de los elementos es el anterior del null
            limite--;
        }
        
        if (limite>0)
        {
            //Si hay elementos se produce un aleatorio de la posición de uno
            Random rn = new Random();
            int pos = rn.nextInt((limite +1));
            return pos;
        }
        else
        {
            //Si no había elementos en la array se devuelve -1. Nunca se da este caso porque la array viene llena.
            return -1;
        }   
    }
    
    //Descubre letras en la palabra a adivinar.
    //El StringBuilder se pasa como parámetro pero devuelve el valor, es como si se pasase por referencia
    private static boolean ponletra(String palabra,StringBuilder adivina, char letra)
    {
        boolean encontrado=false;
        //Se recorre toda la palabra letra por letra
        for(int cont=0;cont<palabra.length();cont++)
        {
            if(palabra.charAt(cont)==letra)
            {
                //Si una letra coincide con la que ha introducido el usuario se descubre en el StringBuilder adivina
                adivina.setCharAt(cont, letra);
                encontrado=true;
                //Se recuerda que se ha cambiado la letra para poder devolver el boolean y saber si ha habido letras cambiadas
            }
        }
        return encontrado;
    }
    
    //La partida
    private static int jugar()
    {
        //primero elijo una de las palabras al azar.
        int elegido = eligenombre(),cont=0,puntos=20, intentos=15;
        char letra,perdido;
        boolean sal=false;
        ArrayList<Character> letras = new ArrayList<Character>();
        //Guardo las letras que el usuario ha introducido en un arraylist de char
        StringBuilder adivina=new StringBuilder();
        //La palabra que se va descubriendo es un StringBuilder
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            //Lleno el Strigbuilder de guiones tantos como letras tiene la palabra
            for(cont=0;cont<palabras[elegido].length();cont++)
            {
                adivina.append("-");
            }
            //Bucle de la partida jugadas
            while(!sal)
            {
                if(intentos>0)
                {
                    //Si quedan intentos se juega
                    System.out.println(adivina);
                    //Se muestra el Stringbuilder con guiones y caracteres descubiertos
                    System.out.println("Intentos restantes:" + intentos);
                    System.out.println("Puntuación:" + puntos);
                    //Se muestran intentos y puntuación
                    System.out.println("Introduce una letra.");
                    //El usuario introduce una letra
                    letra=(char)lector.read();
                    perdido=(char)lector.read(); 
                    //Hago otra lectura sin dato para el salto de linea
                    
                    if(letras.contains(letra))
                    {
                        //Si ya se había introducido esta letra guardada en el arraylist
                        System.out.println("Ya habías introducido esta letra.");
                    }
                    else
                    {
                        //Si la letra no se había introducido se procede
                        if((letra>='a' && letra<='z') || (letra>='A' && letra<='Z'))
                        {
                            //Se comprueba que la letra es del alfabeto
                            //Se procede con la jugada
                            letra=Character.toLowerCase(letra);
                            //Se pasa la letra a minúsculas
                            intentos--;
                            puntos--;
                            //Se restan intentos y puntos
                            letras.add(letra);
                            //Se añade la letra al arraylist de las introducidas
                            //Se intenta descubrir esa letra de la palabra
                            if(ponletra(palabras[elegido],adivina, letra))
                            {
                                //Si se han descubierto caracteres
                                System.out.println(adivina);
                                if(adivina.indexOf("-")==-1)
                                {
                                    //Caso en que se ha averiguado la palabra porque no hay guiones
                                    //Se sale del bucle
                                    sal=true;
                                    System.out.println("Se ha averiguado la palabra.");
                                }
                            }
                            else
                            {
                                System.out.println("La letra no está en la palabra.");
                            }
                        }
                        else
                        {
                            System.out.println("Introduce caracteres del alfabeto.");
                        }  
                    }
                }
                else
                {
                    //Si no quedan intentos se sale del bucle
                    System.out.println("Se han acabado los intentos.");
                    System.out.println("La palabra es:" + palabras[elegido]);
                    
                    sal=true;
                }
            }
            System.out.println("Puntuación:" + puntos);
        }
        catch(Exception e){
            System.out.println("Error."); 
            puntos=-1;
        }
       return puntos; 
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        int opcion,puntuacion = -1, puntuacionnueva;
        palabras[0]="gato";
        palabras[1]="perro";
        palabras[2]="persona";
        palabras[3]="tierra";
        palabras[4]="aire";
        //Se introducen las palabras
        //Todas son en minúsculas
        
        Scanner entrada=new Scanner(System.in);
        try
        {
            do
            {
                System.out.println("1-Mostrar palabras.");
                System.out.println("2-Añadir palabras.");
                System.out.println("3-Nueva partida.");
                System.out.println("0-Salir.");
                //Se muestra el menú
                opcion=entrada.nextInt();
                //Se pide una opción al usuario
                switch(opcion)
                {
                    case 1:
                        mostrarpalabras();
                        break;
                    case 2:
                        anadirpalabra();
                        break;
                    case 3:
                        //Si hay puntuación anterior se muestra
                        if(puntuacion!=-1)
                        {
                            System.out.println("Puntuación anterior:"+ puntuacion);
                        }
                        //Se inicia la partida y se recoge la puntuación cuando termine
                        puntuacionnueva=jugar();
                        if(puntuacionnueva==-1)
                        {
                            System.out.println("Error.");
                        }
                        else
                        {
                            puntuacion=puntuacionnueva;
                            System.out.println("Puntuación guardada:" + puntuacion);
                        }
                        break;
                }
            }while(opcion!=0);
        }
        catch(Exception e){
            System.out.println("Error."); 
        }
        
    }
    
}
