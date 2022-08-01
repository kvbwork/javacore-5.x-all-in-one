package kvbdev.csv2json;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import kvbdev.common.Employee;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import static kvbdev.common.JsonUtils.listToJson;
import static kvbdev.common.JsonUtils.writeString;

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

        try (Reader reader = new FileReader(fileName)) {
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            return csvToBean.parse();
        }
    }

}
