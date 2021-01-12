package core;

public class RangeValuesContainer implements ValuesContainer {
    int minValue, maxValue;

    public RangeValuesContainer(int minValue, int maxValue) {
        assert minValue < maxValue : "RangeValuesContainer: minValue must be less than maxValue";
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Object pickOne() {
        int range = maxValue - minValue;
        return (Integer)(int)(Math.random()*range + minValue);
    }

    @Override
    public boolean contains(Object o) {
        return (o instanceof Integer) && minValue <= (Integer)o && (Integer)o <= maxValue;
    }

    @Override
    public boolean containsAny(ValuesContainer container) {
        if (!(container instanceof RangeValuesContainer))
            return false;
        RangeValuesContainer cont = (RangeValuesContainer)container;
        return (minValue >= cont.minValue && minValue < cont.maxValue) ||
               (maxValue > cont.minValue && maxValue <= cont.maxValue);
    }

    @Override
    public ValuesContainer getSubContainer() {
        int newMin = minValue + (int)((maxValue - minValue)*Math.random());
        int newMax = newMin + (int)((maxValue - newMin)*Math.random()) + 1;
        return new RangeValuesContainer(newMin, newMax);
    }
    
    @Override
    public String toString() {
        return String.format("Range from %d to %d", minValue, maxValue);
    }
}
