package model.enums;

/**
 * Created by luiz on 26/11/15.
 */
public enum UserType {
    DEVELOPER,
    APPROVING,
    ANALYST,
    ADMIN;

    public static UserType getType(int type){
        for (UserType value : values()) if (value.ordinal() == type) return value;
        return DEVELOPER;
    }
}
