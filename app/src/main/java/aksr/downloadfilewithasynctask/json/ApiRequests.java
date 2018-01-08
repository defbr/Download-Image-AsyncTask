package aksr.downloadfilewithasynctask.json;


import android.support.annotation.NonNull;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import aksr.downloadfilewithasynctask.json.dataModel.DummyObject;
import aksr.downloadfilewithasynctask.json.dataModel.DummyObjectDeserializer;

public class ApiRequests {
  private static final Gson gson = new GsonBuilder()
      .registerTypeAdapter(DummyObject.class, new DummyObjectDeserializer())
      .create();

  public static GsonGetRequest<DummyObject> getDummyObjectOBJECT
      (@NonNull final Response.Listener<DummyObject> listener,
          @NonNull final Response.ErrorListener errorListener
      ) {
      final String url = "url";//OBJECT
    return new GsonGetRequest<>
        (
            url,
            new TypeToken<DummyObject>() {
            }.getType(),
            gson,
            listener,
            errorListener
        );
  }

    public static GsonGetRequest<ArrayList<DummyObject>> getDummyObjectARRAY
            (@NonNull final Response.Listener<ArrayList<DummyObject>> listener,
             @NonNull final Response.ErrorListener errorListener
            ) {

        final String url ="https://script.google.com/macros/s/AKfycbzSfFoKjJXduXjJFVc36ZGXpD0601EuUjqCiFbqURZj0ZS6PH0/exec";//ARRAY
        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<ArrayList<DummyObject>>() {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

}
