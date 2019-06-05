package sample.ServerConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Tokenizer {
    public static String getSeparator() {
        return separator;
    }

    static private String separator;

    public void setSeparator(String separator){
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
