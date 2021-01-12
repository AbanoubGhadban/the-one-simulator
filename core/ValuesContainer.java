package core;

public interface ValuesContainer {
    Object pickOne();
    boolean contains(Object o);
    boolean containsAny(ValuesContainer container);
    ValuesContainer getSubContainer();
}
