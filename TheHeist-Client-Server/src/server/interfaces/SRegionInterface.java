/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.interfaces;

import message.Message;
import message.MessageException;

/**
 * This interface defines methods of the interfaces
 *
 * @author Andre Cardoso: 65069, Dercio Bucuane: 83457
 */
public interface SRegionInterface {

    /**
     * receives a message, process in the specific shared region and return a response
     *
     * @param msg the input message
     * @return output message
     * @throws message.MessageException implemented message exception
     */
    Message processAndReply(Message msg) throws MessageException;
}
