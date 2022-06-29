import lombok.Data;
import lombok.extern.flogger.Flogger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

@Data
public class Properties {
    private static Properties instance;
    private Mongo mongo;

    public static void initialize(){
        try{
            Yaml yaml = new Yaml();
            instance = yaml.loadAs(new FileInputStream("conf/application.yml"), Properties.class);
        }catch (FileNotFoundException e){
            System.out.println("error:"+e);
        }
    }
    public static Properties getInstance() {
        return instance;
    }

    @Data
    public static class Mongo {
        private String host;
        private int port;
        private String db;
    }
    @Data
    public static class Date{
        private int start;
        private int end;
    }
}
