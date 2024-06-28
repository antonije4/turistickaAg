package enums;

public enum UserType {
    Ugostitelj("Ugostitelj"),
    Turista("Turista"),
    PrivilegedUser("Privilegovani Korisnik");

    private final String key;
    UserType(String key){
        this.key=key;
    }

    public String getKey() {
        return key;
    }
}
