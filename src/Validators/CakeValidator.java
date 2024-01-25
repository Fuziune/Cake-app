package Validators;

import domain.Cake;

public class CakeValidator {
    public static void  validate(Cake cake)  {
            if(cake==null)
                throw new IllegalArgumentException("cake can not be null");
            if(cake.getFlavor()==null || cake.getFlavor().isEmpty())
                throw  new IllegalArgumentException("No flavor added");
            if(cake.getPrice()<0)
                throw new IllegalArgumentException("Incorrect price");
    }
}
