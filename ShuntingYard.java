/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Issei
 */
public class ShuntingYard {
    
    private String operacion;
    private ArrayList<String> prefija;
    private ArrayList<String> tokens;
    
    public ShuntingYard(String operacion) throws ParentesisCierreException, ParentesisAperturaException{
        this.operacion = operacion;
        prefija = new ArrayList<>();
        this.tokens = new ArrayList<>();
        this.tokenizar();
        this.shuntingYard();
    }
    //Ya que hemos recibido la operacion vamos a tokenizar
    
    private void tokenizar(){
        String patron = "(?<token>[\\(]|\\d+|[-+\\*/%^]|[\\)])";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(this.operacion);
        
        String token;
        
        while(matcher.find()){
            token = matcher.group("token");
            tokens.add(token);
            
        }
        
    }
    
    private void shuntingYard() throws ParentesisCierreException, ParentesisAperturaException{
    String token;
    int contador=0;
    Stack<String> pila = new Stack();
    for(int i=0;i<tokens.size();i++){
        token = tokens.get(i);
        
        if(token.matches("\\d+")){//si el token concuerda con esta expresion regular acepta solo numeros 0-9
            prefija.add(token); //si recibimos un numero lo agregamos directamente a la cola de salida, a nuestro array prefija
        }else if(token.equals("(")){//si hay un parentesis de apertura vamos a agregarlo a la pila
            pila.push(token);
            contador++; //aumentamos en 1 el contador de parentesis
        }else if(token.matches("[-+\\*/%^]")){ //si nuestro token es un operador preguntamos si no esta vacia para ahcer la comparacion entre precedencias
            if(!pila.empty()){//si esta vacia agregamso directamente a la pila
                if(compararPresedencias(token, pila.peek())){ //comparamos la precedencia del token que esta en la expresion con la expr que esta en la pila
                    prefija.add(pila.pop());
                    pila.push(token); //vamos a hacer un intercambio, el op que tenga mas precedencia a la cola
                }else{
                    pila.push(token);
                }
            }else{
                pila.push(token);
            }
        }else if(token.equals(")")){ //si el token es igual a un parentesis de ciere
            contador--;//restamos en uno el contador del parentesis porque hemos encontrado la preja del aprentesis
            if(!pila.empty()){ //si la pila esta vacia vamos a hacer un bucle
                while(true){
                    if(!pila.empty()){
                        if(!pila.peek().equals("(")){ //si la pila es diferente de un parentesis de apertura
                            prefija.add(pila.pop()); //la cima de la pila la vamos a pasar a la cola de salida
                            
                        }else{ //si no es asi arrojamos lo que hay en la cima
                            pila.pop();
                            break; //cerramos el ciclo
                        }
                    }else{ //si esta vacia la pila terminamos el ciclo
                        break;
                    }
                }
            }else{
                throw new ParentesisCierreException("Parentesis de cierre sobrante");
            }
        }
        
        if(contador<0){
            throw new ParentesisCierreException("Parentesis de cierre sobrante");
        }
    }
    
    while(!pila.empty()){
        if(!pila.peek().equals("(")){//si la pila es diferente a un parentesis de apertura
            prefija.add(pila.pop());//vamos a agregar lo que hay en la pila a la cola de salida
        }else{// si lo que encontramos fue un parentesis de apertura
            throw new ParentesisAperturaException("Parentesis de apertura sobrante");//lanzamos una nueva excepcion de aprentesis de apertura
            
        }
    }
}
 private boolean compararPresedencias(String opExp,String opTopPila){
     return darPresedenciaOperacion(opExp)< darPresedenciaPila(opTopPila);
 }
 
 private int darPresedenciaOperacion(String op){
     switch(op){
         case "^":
            return 4;
         case "*":case "/":case "%":
            return 2;
         case "+":case "-":
             return 1;
         default: return 0;    
     }
 }
 private int darPresedenciaPila(String op){
     switch(op){
         case "^":
             return 3;
         case "*":case "/":case "%":
             return 2;
         case "+":case "-":
             return 1;
         default: return 0;    
     }
 }
 
 public ArrayList<String> getPrefija(){
     return prefija;
 }
         
         
}
