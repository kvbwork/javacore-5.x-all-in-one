package kvbdev.csv2json;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import kvbdev.common.Employee;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static kvbdev.jsonparser.Main.listToJson;
import static kvbdev.jsonparser.Main.writeString;


public class Main {

    public static void main(String[] args) throws Exception {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        String outFileName = "data.json";

        List<Employee> list = parseCSV(columnMapping, fileName);

        String json = listToJson(list);
        writeString(json, outFileName);
        System.out.println(outFileName + " created");
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) throws IOException {
        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Employee.class);
        strategy.setColumnMapping(columnMapping);

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .withSeparator(',')
                    .build();

            return csvToBean.parse();
        }
    }

}
