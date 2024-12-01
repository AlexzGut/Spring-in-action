package tacos.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CakeOrder {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date placedAt;

    @NotBlank(message = "Delivery Name is required")
    private String deliveryName;

    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @NotBlank(message = "City is required")
    private String deliveryCity;

    @NotBlank(message = "State is required")
    private String deliveryState;

    @NotBlank(message = "Postal is required")
    private String deliveryPostal;

    @CreditCardNumber(message = "Must be a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])(/)([2-9][0-9])$", message="Must be formatted MM/YY")
    // ^ The beginning of a line
    // $ The end of a line
    // [] a range
    // full reference of regular-expression constructs https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private List<Cake> cakes = new ArrayList<>();

    public void addCake(Cake cake) {
        this.cakes.add(cake);
    }
}
