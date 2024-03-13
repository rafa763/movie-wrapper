package tech.xserver.xmovies.validators;

import org.springframework.stereotype.Service;

@Service
public class ValidatorsImpl implements Validators {
    public boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true; // Parsing succeeded, input is a valid integer
        } catch (NumberFormatException e) {
            return false; // Parsing failed, input is not a valid integer
        }
    }

    public boolean isValidTimeWindow(String input) {
        return input.equalsIgnoreCase("day") || input.equalsIgnoreCase("week");
    }
}
