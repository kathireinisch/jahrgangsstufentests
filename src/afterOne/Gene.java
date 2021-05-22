package afterOne;

public class Gene {
    private String name;
    private String id;
    private String seq;

    public Gene(String name, String seq, String id){
        this.name = name;
        this.seq = seq;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSeq() {
        return seq;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }




    //Aufgabe 3
    //3a)
    public int getLength(){
        int length = seq.length();
        return length; //or return seq.length();
    }

    //3b)
    public float calculateGcContent() {

        //initilaize counter for G/C-Content in String
        int gcCounter = 0;

        //iterare through every char in String
        for (int i = 0; i < seq.length(); i++) {
            char base = seq.charAt(i);

            //check if char C or G: if yes -> increase counter
            if(base == 'C' || base == 'G'){
                gcCounter++;
            }
        }

        //get length of Sequence by calling getLength() (from 3a)
        int length = getLength();

        //calculate GC-Content: gcCounter divided by length
        //use float, since division could give you decimal digits
        float gcContent = gcCounter/length;

        //return gcContent, since calculateGcContent() is not void (= doesn't return anything), but float
        return gcContent; //or return (gcCounter/length);
    }

    //3c)
    public String calculateComplement(){
        //initialize new String, where the complement will be build
        String complementString = "";


        //iterare through every char in String
        for (int i = 0; i < seq.length(); i++) {
            char base = seq.charAt(i);

            //1. Option: if-statements
            //check base and append complement of this base to new String (complementString)
            if(base == 'C'){
                complementString += 'G';
            }
            if(base == 'G'){
                complementString += 'C';
            }
            if(base == 'A'){
                complementString += 'T';
            }
            if(base == 'T'){
                complementString += 'A';
            }

            //2. Option: switch-case
            /*switch (base){
                case 'G':
                    complementString += 'C';
                    break;
                case 'C':
                    complementString += 'G';
                    break;
                case 'A':
                    complementString += 'T';
                    break;
                case 'T':
                    complementString += 'A';
                    break;
            }*/

        }

        return complementString;
    }


    //3d)
    public String calculateReverseComplement(){
        //calculate Complement by calling calculateComplement()
        String complement = calculateComplement();

        //reverse this String
        String reverse = new StringBuffer(complement).reverse().toString();
        //or other methods from the internet (e.g: https://www.geeksforgeeks.org/reverse-a-string-in-java/)

        return reverse;
    }




}
