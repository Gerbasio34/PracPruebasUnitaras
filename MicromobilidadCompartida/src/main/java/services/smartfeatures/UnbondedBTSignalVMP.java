package services.smartfeatures;

import java.net.ConnectException;

public class UnbondedBTSignalVMP implements UnbondedBTSignal {

    //Permitiendo la conexión a cualquier dispositivo Bluetooth cercano.
    //Es el utilizado para descubrir las estaciones de vehículos por parte de la app.
    //no está restringido
    @Override
    public void BTbroadcast() throws ConnectException {

    }
}
