package ch.fhnw.deardevbackend.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class YappiException extends RuntimeException {
    private final String message;
}
