package binaries.app.codeutsava.restapi.model.buyer;

import java.io.Serializable;

public class BuyerFoodgrainResponse implements Serializable {
    public int id;
    public String type;
    public String price;
    public String life;

    @Override
    public String toString() {
        return "BuyerFoodgrainResponse{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", life='" + life + '\'' +
                '}';
    }
}
