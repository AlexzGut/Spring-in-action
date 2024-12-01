package tacos.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Cake {

    private Long id;

    private Date createdAt = new Date();

    @NotNull
    @Size(min = 5, message = "Cake name must be at least 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "Choose at least one ingredient")
    private List<Ingredient> ingredients;
}
