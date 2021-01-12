package core;

import java.util.HashSet;
import java.util.Set;

public class ObjectSetContainer implements ValuesContainer {
    Set<Object> values;

    public ObjectSetContainer(Object singleValue) {
        this.values = new HashSet<>();
        values.add(singleValue);
    }

    public ObjectSetContainer(Set<Object> values) {
        this.values = new HashSet<>(values);
    }

    @Override
    public Object pickOne() {
        int index = (int)(Math.random()*values.size());
        int i = 0;
        for (Object item : values) {
            if (index == i)
                return item;
            ++i;
        }
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @Override
    public ValuesContainer getSubContainer() {
        double prop = Math.random();
        Set<Object> subset = new HashSet<>();
        for (Object object : values) {
            if (prop < Math.random())
                subset.add(object);
        }
        return new ObjectSetContainer(subset);
    }

    @Override
    public boolean containsAny(ValuesContainer container) {
        if (!(container instanceof ObjectSetContainer))
            return false;
        for (Object object : ((ObjectSetContainer)container).values) {
            if (values.contains(object))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Object object : values) {
            builder.append(String.format("%s, ", object.toString()));
        }

        if (values.size() > 0)
            builder.delete(builder.length() - 2, builder.length());
        builder.append("}");
        return builder.toString();
    }
}
