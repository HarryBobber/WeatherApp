package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    JSONObject weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        button.setOnClickListener(view -> new GetWeatherInfo().execute(new BackgroundParameters(this, editText.getText().toString(), weatherInfo)));
    }

    private static class GetWeatherInfo extends AsyncTask<BackgroundParameters, Void, BackgroundParameters> {

        @Override
        protected BackgroundParameters doInBackground(BackgroundParameters... backgroundParameters) {
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip="+backgroundParameters[0].getZipcode()+",US&appid=c369ddf5a20a93d5e858bf817eee16b8");
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String string = bufferedReader.readLine();
                return new BackgroundParameters(backgroundParameters[0].getMainActivity(), backgroundParameters[0].getZipcode(), new JSONObject(string));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                backgroundParameters[0].getMainActivity().runOnUiThread(() -> {
                    Toast toast = Toast.makeText(backgroundParameters[0].getMainActivity(), "Invalid ZipCode", Toast.LENGTH_SHORT);
                    toast.show();
                });
            }
            return new BackgroundParameters(backgroundParameters[0].getMainActivity(), backgroundParameters[0].getZipcode(), backgroundParameters[0].getWeatherInfo());
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(BackgroundParameters backgroundParameters) {
            MainActivity currentMain = backgroundParameters.getMainActivity();
            JSONObject currentWeatherInfo = backgroundParameters.getWeatherInfo();
            currentMain.runOnUiThread(() -> {
                //FindDisplays
                TextView quote = currentMain.findViewById(R.id.quote);
                TextView mondayDate = currentMain.findViewById(R.id.MondayDate);
                TextView mondayHigh = currentMain.findViewById(R.id.MondayHigh);
                ImageView mondayIMG = currentMain.findViewById(R.id.MondayImg);
                TextView mondayLow = currentMain.findViewById(R.id.MondayLow);
                TextView tuesdayDate = currentMain.findViewById(R.id.TuesdayDate);
                TextView tuesdayHigh = currentMain.findViewById(R.id.TuesdayHigh);
                ImageView tuesdayIMG = currentMain.findViewById(R.id.TuesdayImg);
                TextView tuesdayLow = currentMain.findViewById(R.id.TuesdayLow);
                TextView wednesdayDate = currentMain.findViewById(R.id.WednesdayDate);
                TextView wednesdayHigh = currentMain.findViewById(R.id.WednesdayHigh);
                ImageView wednesdayIMG = currentMain.findViewById(R.id.WednesdayImg);
                TextView wednesdayLow = currentMain.findViewById(R.id.WednesdayLow);
                TextView thursdayDate = currentMain.findViewById(R.id.ThursdayDate);
                TextView thursdayHigh = currentMain.findViewById(R.id.ThursdayHigh);
                ImageView thursdayIMG = currentMain.findViewById(R.id.ThursdayImg);
                TextView thursdayLow = currentMain.findViewById(R.id.ThursdayLow);
                TextView fridayDate = currentMain.findViewById(R.id.FridayDate);
                TextView fridayHigh = currentMain.findViewById(R.id.FridayHigh);
                ImageView fridayIMG = currentMain.findViewById(R.id.FridayImg);
                TextView fridayLow = currentMain.findViewById(R.id.FridayLow);
                //FIndDisplays
                try {
                    quote.setText(getQuote(currentWeatherInfo.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main")));
                    mondayDate.setText(convertTime(currentWeatherInfo.getJSONArray("list").getJSONObject(0).getInt("dt")));
                    mondayHigh.setText("High: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp_max"))+"°");
                    mondayIMG.setImageResource(getImage(currentWeatherInfo.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main")));
                    mondayLow.setText("Low: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp_min"))+"°");
                    tuesdayDate.setText(convertTime(currentWeatherInfo.getJSONArray("list").getJSONObject(1).getInt("dt")));
                    tuesdayHigh.setText("High: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(1).getJSONObject("main").getString("temp_max"))+"°");
                    tuesdayIMG.setImageResource(getImage(currentWeatherInfo.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main")));
                    tuesdayLow.setText("Low: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(1).getJSONObject("main").getString("temp_min"))+"°");
                    wednesdayDate.setText(convertTime(currentWeatherInfo.getJSONArray("list").getJSONObject(2).getInt("dt")));
                    wednesdayHigh.setText("High: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(2).getJSONObject("main").getString("temp_max"))+"°");
                    wednesdayIMG.setImageResource(getImage(currentWeatherInfo.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main")));
                    wednesdayLow.setText("Low: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(2).getJSONObject("main").getString("temp_min"))+"°");
                    thursdayDate.setText(convertTime(currentWeatherInfo.getJSONArray("list").getJSONObject(3).getInt("dt")));
                    thursdayHigh.setText("High: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(3).getJSONObject("main").getString("temp_max"))+"°");
                    thursdayIMG.setImageResource(getImage(currentWeatherInfo.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main")));
                    thursdayLow.setText("Low: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(3).getJSONObject("main").getString("temp_min"))+"°");
                    fridayDate.setText(convertTime(currentWeatherInfo.getJSONArray("list").getJSONObject(4).getInt("dt")));
                    fridayHigh.setText("High: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(4).getJSONObject("main").getString("temp_max"))+"°");
                    fridayIMG.setImageResource(getImage(currentWeatherInfo.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main")));
                    fridayLow.setText("Low: " + convertToFahrenheit(currentWeatherInfo.getJSONArray("list").getJSONObject(4).getJSONObject("main").getString("temp_min"))+"°");
                } catch (Exception e) {
                    e.printStackTrace();
                    currentMain.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(currentMain, "Invalid ZipCode", Toast.LENGTH_SHORT);
                        toast.show();
                    });
                }
            });
        }
        public String convertToFahrenheit(String kelvin){
            int temp = (int)Double.parseDouble(kelvin);
            temp = (int)((temp-273.15)*1.8 + 32);
            return String.valueOf(temp);
        }
        public int getImage(String weather){
            int imageID = R.color.teal_700;
            if(weather.equals("Thunderstorm"))
                imageID = R.drawable.thunderstorm;
            if(weather.equals("Drizzle"))
                imageID = R.drawable.partlyrainy;
            if(weather.equals("Rain"))
                imageID = R.drawable.rainy;
            if(weather.equals("Snow"))
                imageID = R.drawable.snowy;
            if(weather.equals("Mist")||weather.equals("Smoke")||weather.equals("Haze")||weather.equals("Dust")||weather.equals("Fog")||weather.equals("Sand")||weather.equals("Ash")||weather.equals("Squall")||weather.equals("Tornado"))
                imageID = R.drawable.hazy;
            if(weather.equals("Clear"))
                imageID = R.drawable.sunny;
            if(weather.equals("Clouds"))
                imageID = R.drawable.cloudy;
            return imageID;
        }
        public String convertTime(int timeStamp){
            java.util.Date date = new Date(timeStamp*1000L);
            java.util.Date finalDate = new Date(timeStamp*1000L-date.getTimezoneOffset()*1000L);
            int finalTime = finalDate.getHours();
            if(finalTime==0)
                return 12 + "AM";
            else if(finalTime<12)
                return finalTime + "AM";
            else if(finalTime==12)
                return finalTime + "PM";
            else
                return finalTime-12 + "PM";
        }
        public String getQuote(String weather){
            String quote = "";
            if(weather.equals("Thunderstorm"))
                quote = "Electrozzz";
            if(weather.equals("Drizzle"))
                quote = "Fish Slap";
            if(weather.equals("Rain"))
                quote = "Fish Punch";
            if(weather.equals("Snow"))
                quote = "Augusta";
            if(weather.equals("Mist")||weather.equals("Smoke")||weather.equals("Haze")||weather.equals("Dust")||weather.equals("Fog")||weather.equals("Sand")||weather.equals("Ash")||weather.equals("Squall")||weather.equals("Tornado"))
                quote = "Spooky Town";
            if(weather.equals("Clear"))
                quote = "Fireball";
            if(weather.equals("Clouds"))
                quote = "Cloudy(Couldn't Find Clash Royale Reference)";
            return quote;
        }
    }

    public static class BackgroundParameters{
        MainActivity mainActivity;
        String zipcode;
        JSONObject weatherInfo;
        public BackgroundParameters(MainActivity mainActivity, String zipcode, JSONObject weatherInfo){
            this.mainActivity = mainActivity;
            this.zipcode = zipcode;
            this.weatherInfo = weatherInfo;
        }
        public JSONObject getWeatherInfo(){
            return  weatherInfo;
        }
        public MainActivity getMainActivity(){
            return mainActivity;
        }
        public String getZipcode(){
            return zipcode;
        }
    }
}