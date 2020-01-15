package binaries.app.codeutsava.restapi.model.farmer;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class FarmerActiveBidListResponse implements Serializable {
    public String id;
    public String isActive;
    public String transno;
    public String quantity;
    public String nbids;
    public String description;
    public String deadline;
    public Buyer buyer;
    public Type type;

    public class Type {
        public String type;
    }

    public class Buyer {

        public String name;
        public String contact;
        public String city;
        public String state;

        @Override
        public String toString() {
            return "Buyer{" +
                    "name='" + name + '\'' +
                    ", contact='" + contact + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "FarmerActiveBidListResponse{" +
                "id='" + id + '\'' +
                ", isActive='" + isActive + '\'' +
                ", transno='" + transno + '\'' +
                ", quantity='" + quantity + '\'' +
                ", nbids='" + nbids + '\'' +
                ", description='" + description + '\'' +
                ", deadline='" + deadline + '\'' +
                ", buyer=" + buyer +
                '}';
    }


}
