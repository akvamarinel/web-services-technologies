package org.example.exception;

import jakarta.xml.ws.WebFault;
import lombok.Getter;


@Getter
@WebFault(faultBean = "org.example.exception.ServiceFault")
public class UserServiceException extends Exception {
    private static final long serialVersionUID = 1L;
    private final ServiceFault serviceFault;

    public UserServiceException(String msg, ServiceFault serviceFault) {
        super(msg);
        this.serviceFault = serviceFault;
    }

    public UserServiceException(String message, ServiceFault fault, Throwable cause) {
        super(message, cause);
        this.serviceFault = fault;
    }

}