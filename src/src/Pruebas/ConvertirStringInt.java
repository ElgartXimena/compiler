package Pruebas;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertirStringInt {
    public static ArrayList<Integer> convertir(String input){
        ArrayList<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String numberStr = matcher.group();
            int number = Integer.parseInt(numberStr);
            numbers.add(number);
        }
        return numbers;
    }
}
