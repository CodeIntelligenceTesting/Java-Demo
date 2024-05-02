package com.demo.handler;

import com.demo.dto.UserDTO;
import com.demo.helper.DatabaseMock;

import java.util.Collection;

/**
 * Handler class that provides functionality for the controller class.
 */
public class UserHandler {

    private static final DatabaseMock db = DatabaseMock.getInstance();

    public static Collection<UserDTO> returnUsers(){
        return db.getAllUsers();
    }

    public static UserDTO returnSpecificUser(String id) {
        return db.getUserWithId(id);
    }

    public static boolean deleteUser(String id) {
        return db.deleteUser(id);
    }

    public static String createUser(UserDTO userDTO){
        return updateUser(userDTO, db.getNextFreeCategoryId());
    }

    public static String updateUser(UserDTO userDTO, String id) {
        db.putUser(id, userDTO);
        return id;
    }
}
