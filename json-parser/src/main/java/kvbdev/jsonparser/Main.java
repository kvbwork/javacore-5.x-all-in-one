package kvbdev.jsonparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kvbdev.common.Employee;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonStr);

        for (Object obj : jsonArray){
            JSONObject jsonObject = (JSONObject) obj;
            Employee employee = gson.fromJson(jsonObject.toJSONString(), Employee.class);
            resultList.add(employee);
        }
        return resultList;
    }

}
