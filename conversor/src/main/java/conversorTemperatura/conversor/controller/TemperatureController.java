package conversorTemperatura.conversor.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import conversorTemperatura.conversor.exception.InvalidTemperatureException;


import conversorTemperatura.conversor.exception.InvalidTemperatureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemperatureController {

    @GetMapping("/convert")
    public double convertTemperature(
            @RequestParam double value,
            @RequestParam String from,
            @RequestParam String to) {
        validateTemperature(value, from);
        switch (from.toLowerCase()) {
            case "celsius":
                if (to.equalsIgnoreCase("fahrenheit")) {
                    return (value * 9/5) + 32;
                } else if (to.equalsIgnoreCase("kelvin")) {
                    return value + 273.15;
                }
                break;
            case "fahrenheit":
                if (to.equalsIgnoreCase("celsius")) {
                    return (value - 32) * 5/9;
                } else if (to.equalsIgnoreCase("kelvin")) {
                    return (value - 32) * 5/9 + 273.15;
                }
                break;
            case "kelvin":
                if (to.equalsIgnoreCase("celsius")) {
                    return value - 273.15;
                } else if (to.equalsIgnoreCase("fahrenheit")) {
                    return (value - 273.15) * 9/5 + 32;
                }
                break;
            default:
                throw new InvalidTemperatureException("Unidade de temperatura não suportada: " + from);
        }
        throw new InvalidTemperatureException("Conversão de " + from + " para " + to + " não suportada.");
    }

    private void validateTemperature(double temperature, String unit) {
        if (unit.equalsIgnoreCase("celsius") && temperature < -273.15) {
            throw new InvalidTemperatureException("Temperatura não pode ser abaixo do zero absoluto (-273.15°C)");
        } else if (unit.equalsIgnoreCase("kelvin") && temperature < 0) {
            throw new InvalidTemperatureException("Temperatura não pode ser abaixo do zero absoluto (0K)");
        } else if (unit.equalsIgnoreCase("fahrenheit") && temperature < -459.67) {
            throw new InvalidTemperatureException("Temperatura não pode ser abaixo do zero absoluto (-459.67°F)");
        }
    }
}