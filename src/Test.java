import java.util.Random;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        createData();
        
    }//end method main
    
    public static void createData(){
        //create Random object
        Random rnd = new Random();
        //create arrayList
        ArrayList<Address> list1 = new ArrayList<>();
        
        String[] firstNames = {"Aaron",  "Elisus",  "Aiyoub",  "Ingrycht",  
            "Jan",  "Daragh",  "James",  "Agnieszka",  "Marcella",  "Mary",  
            "Jonathan",  "Jade",  "Pauline",  "Michael",  "Sharon",  "Sean",  
            "Marzena",  "Niall",  "Paula",  "Trevor",  "Aimee",  "Ednedson", 
            "Marc",  "Graham",  "Collette",  "Andrew",  "Kevin",  "Rosanne",  
            "Michael",  "John",  "Ciaran",  "Fidelis",  "David",  "Declan",  
            "Piotr",  "Zara",  "Ollie",  "Gary",  "Marion",  "Barry",  "Adam",  
            "Jeffrey",  "James",  "Keith",  "Achile",  "Bushra",  "Stephen",  
            "Dale",  "Eoin",  "Gerard",  "Rosealeen",  "Sobia",  "Peter",  
            "Mateusz",  "Kim",  "Dermot",  "Joao",  "Davitt",  "Lauren",  
            "Tomasz",  "Aaron",  "Darren",  "Vivienne",  "Brandon",  "Morgan",  
            "Nick",  "Courtney",  "Don",  "Kira",  "Karim",  "Alain",  "Leanne",  
            "Anthony",  "Shane",  "Tamas",  "Donatas",  "Tamara",  "Christian", 
            "Akintayo",  "Ola",  "Padraig",  "Bezhan",  "Dwayne",  "Gayatriben", 
            "Patrick",  "AJAY",  "Conor",  "Jennifer",  "Deborah",  "Phyllis",  
            "Omar",  "Anthony",  "Jamie",  "Shannon",  "Gavin",  "Adnan",  
            "Katarzyna",  "Thomas",  "George",  "Aoife",  "Paul",  "Michal"};


        String[] lastNames = { "Lott",  "Malone",  "Okolo",  "Goszczynski",  
            "Tamerji",  "Bachowska",  "Croke",  "Almeer",  "RAWAT",  "Ogundimu", 
            "MENJADEU",  "Deans",  "Murphy",  "Scanlon",  "Walsh",  "Gormley", 
            "Pantsulaya",  "Matthews",  "Jabbar",  "O'Connor",  "Duffy",  "Daly", 
            "Rochford",  "McGuinness",  "Kennedy",  "Anderson",  "Malone",  
            "Elliott",  "Maher",  "Ryan",  "Farrell",  "Magaharan",  "McNally", 
            "Norkus",  "Patel",  "Da Silva",  "Agulanne",  "Kenny",  "Elegbogun", 
            "Fogarty",  "Soady",  "Patel",  "Walker",  "Laheen",  "Dalton", 
            "Alves Antunes",  "Brennan",  "Atkinson",  "Herbert",  "Sheehy",  
            "Amort",  "Hanlon",  "Roe",  "Magalhaes",  "Doyle",  "Barrett",  
            "Md Nishat",  "Trela-Bacela",  "Hurley Lynch",  "Dennis",  "Logan",  
            "McNally",  "Mahon",  "Mooney",  "Dagohoy",  "Barber",  "Gorman",  
            "Barr",  "Cieslak",  "Lewkowicz",  "Byrne",  "Donovan",  "Wloka",  
            "McCormick",  "Connolly",  "McDonald",  "Omowunmi",  "Fitzgerald", 
            "Said",  "O'Reilly",  "Rafferty",  "Williamson",  "Corcoran",  
            "Broughall",  "Leonard",  "Ryan",  "Farrell",  "Dobbyn",  "Larkin", 
            "Campbell",  "Mythen",  "Smith",  "Byrne",  "Latif",  "Keeley", 
            "O' Connell",  "Nagy",  "Valentine",  "Maksim",  "Crammond",  
            "Masterson",  "Farrell"};
        
        String[] streets = {"street", "Road", "Lane", "View", "Avenue", "Park"};
        
        String[] areas = {"Church", "Lake", "Mountain", "Glen", "Sea", "Forest"};
        
        String[] towns = {"Naas", "Newbridge", "Kilcullen", "Clane", "Maynooth"};
        
        //create array to generate phoneNumbers
        String[] phoneOps = {"083", "085", "086", "087", "089"};
        
        //create emails
        String[] emails = {"@gmail.com", "@outlook.com", "@live.com", "@yahoo.com"};
        
        //create randonly selects to generate the name of the full names, address, phone number
        for (int lcv = 0; lcv <= 300; lcv++){
            Address p = new Address();
            //using the setter to create fname, lname etc
            p.setFirstName(firstNames[rnd.nextInt(firstNames.length)]);
            p.setLastName(lastNames[rnd.nextInt(lastNames.length)]);
            
            //combine number and street name into address1
            int house = rnd.nextInt(100)+ 1;//generate number between 1 to 100
            String area = areas[rnd.nextInt(areas.length)];
            String street = streets[rnd.nextInt(streets.length)];
            String address1 = String.format("%d %s %s", house, area, street);
            
            //set complety address
            p.setAddress1(address1);
            p.setAddress2(towns[rnd.nextInt(towns.length)]);
            p.setAddress3("Co. Kildare");
            
            //create the phoneNumber
            //give our first part of the phoneNumber
            //create main part of the phoneNumber
            int num = rnd.nextInt(8999999) + 1000000;
            String phone = String.format("(%s)-%d", phoneOps[rnd.nextInt(phoneOps.length)], num);
            p.setPhone(phone);
            
            //generate the emails concatenating the firstname and lastname
            String email = String.format("%s.%s%s", p.getFirstName().toLowerCase(),
                    p.getLastName().toLowerCase(), emails[rnd.nextInt(emails.length)]);
            p.setEmail(email);
            
            //save the objects into ArrayList
            //ArrayList will be passed to GUI
            list1.add(p);
            
            //System.out.println(p);
            
            
        }//end for
        //new it wll pass the arraylist to the AddressGUI constructor
        new AddressGUI(list1);
        
    }//end method createData

}//end class Test