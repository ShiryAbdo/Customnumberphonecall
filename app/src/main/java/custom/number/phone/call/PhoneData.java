package custom.number.phone.call;

/**
 * Created by falcon on 04/11/2017.
 */

public class PhoneData {
    private  String name ;
    private  String phone ;

    public PhoneData(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
