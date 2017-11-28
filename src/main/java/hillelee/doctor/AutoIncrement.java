package hillelee.doctor;

public class AutoIncrement {
    private static Integer integer = 0;

    public static synchronized Integer doIncrement() {
        return ++integer;
    }
    public static synchronized Integer getValue() {
        return integer;
    }
}
