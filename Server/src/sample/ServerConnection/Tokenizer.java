package sample.ServerConnection;

import java.util.Arrays;
import java.util.List;

public class Tokenizer {

    static private String separator = "|";

    public static String getSeparator() {
        return separator;
    }

    public static Tokenizer create(){
        return new Tokenizer();
    }

    public static void setSeparator(String separator){
        Tokenizer.separator = separator;
    }

    public String tokenize(List<String> listOfStrings){
        return listOfStrings.stream()
                .reduce((a,b)->a+separator+b)
                .get();
    }


    public List<String> unTokenize(String message){
        return Arrays.asList(message.split(separator));
    }
}
