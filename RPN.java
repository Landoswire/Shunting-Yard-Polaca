/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Issei
 */
public class RPN {
    private ArrayList<String> prefija;
    private Stack<String> pila;
    
    public RPN(ArrayList<String> prefija){
        this.prefija = prefija;
        this.pila = new Stack();
        
    }
    
    public double rpn() throws ElementoFaltante{
        String eleDer,eleIzq;
        
        for(String token : prefija){ //leemos el token
            if(esOperador(token)){ //si es operador va a recibir al token
                eleDer=pila.pop();//saca al elemento derecho de la pila
                if(pila.isEmpty()){
                    throw new ElementoFaltante("Error, elemento faltante ");
                    
                }
                eleIzq = pila.pop();
                double resultado = operar(eleIzq,token, eleDer);
                pila.push(""+resultado);
            }else{
                pila.push(token);
            }
        }
        
        return Double.parseDouble(pila.pop());
    }

    private boolean esOperador(String token) { //este metodo regresara verdadero si coincide con algunos de estos operadores  a
        return token.equals("+") || token.equals("-") || token.equals("*")
                || token.equals("/") || token.equals("%") || token.equals("^");
    }

    private double operar(String eleIzq, String operador, String eleDer) {
        double a = Double.parseDouble(eleIzq);
        double b = Double.parseDouble(eleDer);
        
        switch(operador){
            case "+": return a+b;
            case "-": return a-b;
            case "*": return a*b;
            case "/": return a/b;
            case "%": return a%b;
            case "^": return Math.pow(a, b);
            default: return -1;
        }
    }
}
