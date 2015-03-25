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
 */
public class FragmentoPrincipal extends Fragment {
    View rootView;

    ImageButton doggy; //EL boton principal del juego. Al darle conseguiremos experiencia que podremos utilizar para adquirir mejoras
    ImageButton mejoras, logros, opciones; //Los botones complementarios. Mejoras permitirá mejorar nuestra obtención de experiencia, los logros nos avisarán de nuestras victorias y objetivos conseguidos, opciones permitirá guardar, sonido, etc.
    TextView experiencia; //El texto cambiante o mórfico que nos indicará cuanta experiencia llevamos acumulada actualmente
    long experienciaTotal, experienciaClick = 1; // experienciaTotal será la experencia que el textView tenga. El click será la cantidad de experiencia que sumamos por cada click.
    double modificador = 1.0; // modificador será por cuanto se multiplica la experiencia que obtenemos con cada click.
    double modIdle, experienciaIdle=1; //AUN ESTÁ POR VER SI SE VAN A UTILIZAR. Idle es la experiencia obtenida cuando no hacemos ningún click durante 30 segundos o cuando cerramos el juego.
    //public FragmentoPrincipal() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        clickarDoggy();
        //idleIzar();
        recogerMejoras();
        return rootView;
    }

    public void clickarDoggy() {
        doggy = (ImageButton) rootView.findViewById(R.id.perro);
        experiencia = (TextView) rootView.findViewById(R.id.cuentaTotal);
        doggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experienciaTotal = experienciaTotal + (long) (experienciaClick /** aplicarModificadores()*/);
                experiencia.setText(Long.toString(experienciaTotal));
            }
        });


    }

    public void recogerMejoras(){
        Mejoras mjrs=new Mejoras(rootView.getContext());
        try {
            mjrs.abrirBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Mejora mejora=mjrs.recogerModificador(1);
       experiencia.setText(mejora.getNombre());

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