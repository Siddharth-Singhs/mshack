package com.example.android.myapplication.LoginWork;


public class UserInformation {

    public String Name;
    public String Email;
    public String Status;
    public String Latitude;
    public String Longitude;
   // public ArrayList<String> VillageAdopted=new ArrayList<>();


    public UserInformation(String Name, String Email)
    {
        this.Name=Name;
        this.Email=Email;
       this.Status="";
       this.Latitude="";
       this.Longitude="";
    }
   public UserInformation(String Status)
    {
       this.Status=Status;
    }
   public UserInformation(String Latitude, String Longitude, int number)
   {
       this.Latitude=Latitude;
       this.Longitude=Longitude;
   }
}


