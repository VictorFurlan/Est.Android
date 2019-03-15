package projectfragments.com.krampus.myapplication;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Habilite armazenamento local.
    Parse.enableLocalDatastore(this);

    // Codigo de configuração do App
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("JG3ntMRnPxICbSFQco2jzdsfgQBpj2m4xLzABe1j")
            .clientKey("nZXeS7CZqsgoBXkNFAcqLpzCqcQwrp2KBRDed7eK")
            .server("https://parseapi.back4app.com")
    .build()
    );

     // ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    defaultACL.setPublicReadAccess(true);
    //ParseACL.setDefaultACL(defaultACL, true);
  }
}
