package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServiceFault {
    private String message;

    public ServiceFault(String message) {
        this.message = message;
    }
    public static ServiceFault newServiceException(String message) {
        return new ServiceFault(message);
    }

}

