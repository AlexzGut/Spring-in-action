package tacos.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CakeOrder {

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<Cake> cakes = new ArrayList<>();

    public void addCake(Cake cake) {
        this.cakes.add(cake);
    }
}
