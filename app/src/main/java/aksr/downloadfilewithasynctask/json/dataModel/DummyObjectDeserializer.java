package aksr.downloadfilewithasynctask.json.dataModel;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class DummyObjectDeserializer implements JsonDeserializer<DummyObject> {

  @Override
  public DummyObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    final DummyObject dummyObject = new DummyObject();
    final JsonObject jsonObject = json.getAsJsonObject();

    if(jsonObject.has("baixar")){
      if(!jsonObject.get("baixar").toString().equals("null")){
        dummyObject.setBaixar(jsonObject.get("baixar").getAsString());
      }else{
        dummyObject.setBaixar("null");
      }
    }

    if(jsonObject.has("image")){
      if(!jsonObject.get("image").toString().equals("null")){
        dummyObject.setImage(jsonObject.get("image").getAsString());
      }else{
        dummyObject.setImage("null");
      }
    }

    return dummyObject;
  }
}
