
package com.petopia.db;

import com.petopiables.Functions;

public class DisconnectDatabase extends DatabaseConnections{
    public void disconnectFromDatabase(){
        Functions function = new Functions();
        
        function.cleanConsoleTwenty();    
        System.out.println("Disconnecting Database.....");
        closeAllConnections();
    }
}
