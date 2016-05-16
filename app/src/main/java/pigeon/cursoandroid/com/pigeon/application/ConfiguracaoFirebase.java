package pigeon.cursoandroid.com.pigeon.application;

import com.firebase.client.Firebase;

/**
 * Created by leandromarino on 15/05/16.
 */
public final class ConfiguracaoFirebase {

    private static Firebase firebase;
    private static final String URL_FIREBASE = "https://whatsappcourse.firebaseio.com/";

    public static Firebase getFirebase() {

        if(firebase == null) {
            firebase = new Firebase(URL_FIREBASE);
        }

        return firebase;
    }


}
