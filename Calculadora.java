/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Issei
 */
public class Calculadora {
    
    public static void main(String[] args){
        
        
        try{
            ShuntingYard sy = new ShuntingYard("(3+4)*2-4");
            
            ArrayList<String> prefija = sy.getPrefija();
            
            for(String string : prefija){
                System.out.print(string+" ");
            }
            
            RPN r = new RPN(prefija);
            System.out.println("\nResultado: "+r.rpn());
                    
        }catch(ParentesisAperturaException | ParentesisCierreException e){
            e.printStackTrace();
        
    }   catch (ElementoFaltante e) {
            e.printStackTrace();
        }
    
    }
    
}
//Para hacer el codigo me base' en un video de youtube
