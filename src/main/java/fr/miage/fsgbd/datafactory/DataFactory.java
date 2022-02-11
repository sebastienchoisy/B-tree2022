package fr.miage.fsgbd.datafactory;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Random;

public class DataFactory {
    Faker faker = new Faker();
    ArrayList<Integer> generatedValues = new ArrayList<>();

    public String generateName(){
        return faker.name().lastName();
    }

    public String generateSurname(){
        return faker.name().lastName();
    }

    public String generatePhoneNumber(){
        return faker.phoneNumber().cellPhone();
    }

    public String generateAddress(){
        return faker.address().cityName();
    }

    public ArrayList<Integer> getGeneratedValues() {
        return generatedValues;
    }

    // Un numéro de sécurité social classique étant trop long, on génère un nombre simple entre 0 et 50000

    public Integer generateSocialSecurityNumber() {
        Random r = new Random();
        Integer number;
        number = r.nextInt(50000);
        if(generatedValues.size() > 0) {
            while(generatedValues.contains(number)){
                number = r.nextInt(50000);
            }
        }
        generatedValues.add(number);
        return number;
    }
}
