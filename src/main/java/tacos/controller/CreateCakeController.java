package tacos.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import tacos.model.Cake;
import tacos.model.CakeOrder;
import tacos.model.Ingredient.Type;
import tacos.model.Ingredient;
import tacos.repository.IngredientRepository;

@Slf4j // This annotation generate a (Simple Logging Facade for Java) Logger static property in the class.
@Controller
@RequestMapping("/create")
@SessionAttributes("cakeOrder") // indicates that the CakeOrder object added to the model by the cakeOrder method should be maintained in session.
public class CreateCakeController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public CreateCakeController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // The methods annotated with @ModelAttribute are executed before any handler methods like (GetMapping or PostMapping) are invoked
    // This functionality ensures attributes are added to the model and available to the views or other controller methods

    // The order of execution of the methods annotated with @MethodAttribute is from top to bottom in the order
    // they are declared in the controller. If a controller extend another controller the methods annotated with @MethodAttribute
    // in the parent controller are executed first.
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Map<String, List<Ingredient>> ingredientsByType = new HashMap<>();

        List<Ingredient> ingredients = (List<Ingredient>) ingredientRepository.findAll();

        Type[] types = Type.values();
        for (Type type : types) {
            ingredientsByType.put(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        // A model containing <Type, List of Ingredients>, which is used to dynamically generate a form so the user can select the ingredients of the cake.
        model.addAttribute("ingredientsByType", ingredientsByType);
    }

    // this annotation is used in Spring MVC to indicate that
    // the CakeOrder object returned by the method should be added to the model
    // with the name specified by 'name' -> "cakeOrder"
    @ModelAttribute(name = "cakeOrder")
    public CakeOrder order() {
        return new CakeOrder();
    }

    @ModelAttribute(name = "cake")
    public Cake cake() {
        return new Cake();
    }

    @GetMapping // when an HTTP GET request is received for /create, Spring MVC will call this method.
    public String displayCreateCakeForm() {
        return "create_cake";
    }

    @PostMapping
    // CakeOrder object is explicitly annotated with @ModelAttribute because it is not the primary form object bound to the request
    // Note: For complex objects like Cake, Spring teats them as @ModelAttribute
    public String processCake(@Valid Cake cake, Errors errors, @ModelAttribute CakeOrder cakeOrder) {
        if (errors.hasErrors()) {
            log.error("Errors: {}", errors.getAllErrors());
            return "create_cake";
        }
        cakeOrder.addCake(cake);
        log.info("Processing cake: {}", cake);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(t -> t.getType().equals(type))
                .toList();
    }
}