package com.petopia;

import com.petopia.db.InitializeDatabase;

public class Main{
    public static void main(String[] args) {
        //instantiations
        InitializeDatabase initDb = new InitializeDatabase();
        LogIn login = new LogIn();   
   
        initDb.initializeDatabase();
        login.LogIn();  
    }  
}

/*
Daff:
EmployeeInfo
GenerateREceipt
Login
Main
ManageServices
Services
EmployeeQuery
GenerateReceiptQuery
LoginQuery
DatabaseConnections
DisconnectDatabase
InitializeDatabase
Employee
EmployeeAccess
Functions
PetopiaHeader
* added a few formatting, colors, cleaned and changed code lines a bit on some of
Wendell & Adam's classes

Wendell:
AppointmentInfo
PetInfo
AppointmentQuery
PetQuery\
Appointment
Pet
Service

Adam:
CustomerInfo
GroomingServiceOption
SelectGroomingService
UpdateService
CustomerQuery
PaymentQuery
ServiceQuery
Customer
PetopiaHeader - added methods to Daff's
PetQuery - added a method to Wendell's
Login - added code lines

Clarisse/Henessy:
CustomerInfo - added a method to Adam's
*/