package icruzgarcia.com.clickerdog;


public class Mejoras {
    private double modificadorTotal;
    private double modificadorIdle;
    public Mejoras() {


    }

    public double aplicarMejoras(){


        return getModificadorTotal();
    }

    public void setModificadorTotal(double mod){

        this.modificadorTotal=mod;
    }

    public double getModificadorTotal(){
        return this.modificadorTotal;

    }

    public void setModificadorIdle(double mod){
        this.modificadorIdle=mod;

    }
    public double getModificadorIdle(){
        return this.modificadorIdle;

    }




}
