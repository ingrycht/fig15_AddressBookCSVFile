//Generate Test text Data
public class Address {
    //create instance variables
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String address3;
    private String phone;
    private String email;
    
    //create 2 constructors, one with no arguments, one with 7 arguments

    public Address() {
    }//end no args constructor

    public Address(String firstName, String lastName, String address1, String address2, String address3, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.phone = phone;
        this.email = email;
    }//end 7 args constructor
    
    //all the setters and getters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
    //create toString method
    @Override
    public String toString() {
        return "Address{" + "firstName = " + firstName + ", lastName = " + lastName +
                ", address1 = " + address1 + ", address2 = " + address2 + 
                ", address3 = " + address3 + ", phone = " + phone + ", email = " + email + '}';
    }//end toString method
    //create a hole bounch of data
    
}//end class address