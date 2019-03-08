package com.mapbox.mapboxandroiddemo.examples.styles;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.mapboxandroiddemo.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.Layer;

import static com.mapbox.mapboxsdk.style.expressions.Expression.FormatOption.formatFontScale;
import static com.mapbox.mapboxsdk.style.expressions.Expression.FormatOption.formatTextFont;
import static com.mapbox.mapboxsdk.style.expressions.Expression.format;
import static com.mapbox.mapboxsdk.style.expressions.Expression.formatEntry;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;

public class TextFieldMultipleFormatsActivity extends AppCompatActivity {

  private MapView mapView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Mapbox access token is configured here. This needs to be called either in your application
    // object or in the same activity which contains the mapview.
    Mapbox.getInstance(this, getString(R.string.access_token));

    // This contains the MapView in XML and needs to be called after the access token is configured.
    setContentView(R.layout.activity_style_text_field_multiple_formats);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
          @Override
          public void onStyleLoaded(@NonNull final Style style) {

            // Set up label formatting expression
            Expression.FormatEntry bigLabel = formatEntry(
              get("name_en"),
              formatFontScale(1.5),
              formatTextFont(new String[] {"Ubuntu Medium", "Arial Unicode MS Regular"})
            );

            Expression.FormatEntry newLine = formatEntry(
              "\n",
              formatFontScale(0.5)
            );

            Expression.FormatEntry smallLabel = formatEntry(
              get("name"),
              formatTextFont(new String[] {"Caveat Regular", "Arial Unicode MS Regular"})
            );

            Expression format = format(bigLabel, newLine, smallLabel);

            // Retrieve the country label layers from the style and update them with the formatting expression
            for (Layer mapLabelLayer : style.getLayers()) {
              if (mapLabelLayer.getId().contains("country-label")) {
                // apply formatting expression
                mapLabelLayer.setProperties(textField(format));
              }
            }
          }
        });
      }
    });
  }

  // Add the mapView lifecycle to the activity's lifecycle methods
  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  protected void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }
}
