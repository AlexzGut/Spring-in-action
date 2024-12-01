package tacos.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.models.Ingredient;
import tacos.models.Ingredient.Type;

import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        ingredientMap.put("PIAP", new Ingredient("PIAP", "Pineapple", Type.FLAVORS));
        ingredientMap.put("CHCH", new Ingredient("CHCH", "Chocolate Chips", Type.ADDITIONS));
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}
