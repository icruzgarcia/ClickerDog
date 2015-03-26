package icruzgarcia.com.clickerdog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;

import icruzgarcia.com.clickerdog.R;

/**
 * A placeholder fragment containing a simple view.
 *
 * @args doggy El boton principal del juego. Al darle conseguiremos experiencia que podremos utilizar para adquirir mejoras
 * @args mejoras Boton complementario.Mejoras permitirá mejorar nuestra obtención de experiencia.
 * @args logros Boton complementario. Los logros nos avisarán de nuestras victorias y objetivos conseguidos.
 * @args opciones Boton complementario. Opciones permitirá guardar, sonido, etc.
 * @args experiencia El texto cambiante o mórfico que nos indicará cuanta experiencia llevamos acumulada actualmente
 * @args experienciaTotal La experencia que el textView va a recibir
 * @args experienciaClick La cantidad de experiencia que se consigue al darle a Doggy un click.
 * @args modificador La cuantía por la cual se multiplica la experiencia en cada click.
 * @args experienciaIdle La experiencia cuando no realizamos ningún click.
 * @args modIdle El modificador por el que se realizará el producto con la experienciaIdle
 * @args rootView La imagen de la actividad principal.
 *
 */


public class FragmentoPrincipal extends Fragment {
    View rootView;



    ImageButton doggy;
    ImageButton mejoras, logros, opciones;
    TextView experiencia;
    long experienciaTotal, experienciaClick = 1;
    double modificador = 1.0;
    double modIdle, experienciaIdle=1 ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        clickarDoggy();
        idleIzar();

        mejoras=(ImageButton)rootView.findViewById(R.id.mejoras);
        mejoras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                recogerMejoras();
            }
        });
        recogerMejoras();
        return rootView;
    }

    /*
    * Este método es la que determina cuanta experiencia se obtendrá cada vez que se haga click a Doggy
    * */

    public void clickarDoggy() {
        doggy = (ImageButton) rootView.findViewById(R.id.perro);
        experiencia = (TextView) rootView.findViewById(R.id.cuentaTotal);
        doggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experienciaTotal = experienciaTotal + (long) (experienciaClick*modificador /** aplicarModificadores()*/);
                experiencia.setText(Long.toString(experienciaTotal));
            }
        });


    }
        /*
        *  Este método permite recoger las mejoras de la base de datos.
        * */
    public void recogerMejoras(){
        Mejoras mjrs=new Mejoras(rootView.getContext());
        try {
            mjrs.abrirBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Mejora mejora=mjrs.recogerModificador(1);
            if (mejora.getId()==1){
                modificador=modificador+1.0;


            }

    }

    /*
    * El método añadirá experiencia por segundo dependiendo de las mejoras obtenidas.
    * */
    public void idleIzar(){

           Thread hiloIdle=new Thread(){
               @Override
               public void run(){
                   try{
                        while(!isInterrupted()){
                            Thread.sleep(1);
                            getActivity().runOnUiThread(new Runnable(){
                                @Override public void run(){
                                    experienciaTotal=experienciaTotal+(long)(experienciaIdle/**aplicarModIdle()*/);
                                    experiencia.setText(Long.toString(experienciaTotal));
                                }
                            });


                        }


                       }catch(InterruptedException e){

                   }

               }

           };
        hiloIdle.start();

    }



}