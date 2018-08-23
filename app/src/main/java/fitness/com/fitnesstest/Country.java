package fitness.com.fitnesstest;

import java.util.ArrayList;

public class Country {

    public String name;

    public String capital;

    public ArrayList<Currency> currencies = new ArrayList<>();

    public ArrayList<Language> languages = new ArrayList<>();

    public String flag;


   class Currency {

       public String name;
    }


    class Language {

       public String name;
    }
}
