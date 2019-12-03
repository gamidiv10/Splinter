/*
 * Author: Jasper Jiao
 * Contributors: Menni Prem Kumar, Sneh Jogani, Vamsi Gamidi
 * Date: 2019
 */

package com.example.splinter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class Locations extends AppCompatActivity {

  private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
  Context context = this;
  TextView user_location, currency_used;
  private FusedLocationProviderClient mFusedLocationClient;
  Geocoder geocoder;
  List<Address> addresses;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.currency_fragment);

    currency_used = findViewById(R.id.currency_used);
    user_location = findViewById(R.id.user_current_location);
    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    fetchLocation();

  }

  private void fetchLocation() {

    if (ContextCompat.checkSelfPermission(Locations.this,
            Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

      // Permission is not granted
      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(Locations.this,
              Manifest.permission.ACCESS_COARSE_LOCATION)) {
        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

        new AlertDialog.Builder(this)
                .setTitle("Required Location Permission")
                .setMessage("You have to give this permission to acess this feature")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(Locations.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                  }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                  }
                })
                .create()
                .show();
      } else {
        // Request the permission
        ActivityCompat.requestPermissions(Locations.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
      }
    } else {
      // Permission has already been granted
      mFusedLocationClient.getLastLocation()
              .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                  // Got last known location. In some rare situations this can be null.
                  if (location != null) {
                    // Logic to handle location object
                    Double latittude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    String user_loc_currency = getCompleteAddressString(latittude, longitude);
                    String[] split = user_loc_currency.split(" ");
                    String cur_symbol = user_loc_currency.replace(split[0], "");
                    currency_used.setText(cur_symbol);
                    user_location.setText(split[0]);
                  }
                }
              });
    }
  }

  private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
    String strAdd = "";
    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    try {
      List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
      String address = addresses.get(0).getAddressLine(0);
      String city = addresses.get(0).getLocality();
      String state = addresses.get(0).getAdminArea();
      String zip = addresses.get(0).getPostalCode();
      // Getting country code
      String country = addresses.get(0).getCountryName();
      String user_cur_loc = addresses.get(0).getCountryCode();
      // Currency symbol
      String symbol = Currency.getInstance(new Locale("", user_cur_loc)).getSymbol();
      if (addresses != null) {
        Address returnedAddress = addresses.get(0);
        StringBuilder strReturnedAddress = new StringBuilder("");

        for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
          strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
        }
        strAdd = country.toString()+"("+user_cur_loc+") " + symbol.toString();
        Log.w("My Current address", strReturnedAddress.toString());
      } else {
        Log.w("My Current address", "No Address returned!");
      }
    } catch (Exception e) {
      e.printStackTrace();
      Log.w("My Current address", "Canont get Address!");
    }

    return strAdd;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      } else {

      }
    }
  }
}