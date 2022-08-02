package kvbdev.jsonparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kvbdev.common.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        String json = readString("new_data.json");

        List<Employee> list = jsonToList(json);

        list.forEach(System.out::println);
    }

    public static String readString(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    public static List<Employee> jsonToList(String jsonStr) throws ParseException {
        List<Employee> resultList = new ArrayList<>();

        Gson gson = new GsonBuilder().create();

        // Код ниже не является оптимальным, но следует условиям реализации задачи.
        // Альтернативный вариант:
        // return gson.fromJson(jsonStr, new TypeToken<List<Employee>>() {}.getType());

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonStr);

        for (Object obj : jsonArray){
            JSONObject jsonObject = (JSONObject) obj;
            Employee employee = gson.fromJson(jsonObject.toJSONString(), Employee.class);
            resultList.add(employee);
        }
        return resultList;
    }

    public static void writeString(String content, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        }
    }

    public static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {}.getType();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(list, listType);
    }

}
