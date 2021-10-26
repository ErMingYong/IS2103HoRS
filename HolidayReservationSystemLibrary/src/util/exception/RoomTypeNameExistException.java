/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author mingy
 */
public class RoomTypeNameExistException extends Exception {

    public RoomTypeNameExistException() {
    }

    public RoomTypeNameExistException(String string) {
        super(string);
    }
    
}
