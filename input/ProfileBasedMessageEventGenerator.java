package input;

import java.util.HashMap;
import java.util.Map;

import core.Settings;
import core.ValuesContainer;

public class ProfileBasedMessageEventGenerator extends MessageEventGenerator {
    public static final String PROFILE_ATTRIBUTES_S = "profileAttributes";
    public static final String ATTRIBUTE_PREFIX_S = "att_";

    Map<String, ValuesContainer> profileSpecs;


    public ProfileBasedMessageEventGenerator(Settings s) {
        super(s);
        profileSpecs = new HashMap<>();

        String[] attributes = s.getCsvSetting(PROFILE_ATTRIBUTES_S);
        for (String attribute : attributes) {
            ValuesContainer container = s.getValuesContainer(ATTRIBUTE_PREFIX_S + attribute);
            profileSpecs.put(attribute, container);
        }
    }

    Map<String, ValuesContainer> getSubSpecs() {
        Map<String, ValuesContainer> subSpecs = new HashMap<>();
        for (Map.Entry<String, ValuesContainer> i : profileSpecs.entrySet()) {
            subSpecs.put(i.getKey(), i.getValue().getSubContainer());
        }
        return subSpecs;
    }
    
    @Override
    public ExternalEvent nextEvent() {
        MessageCreateEvent mce = (MessageCreateEvent)super.nextEvent();
        mce = new ProfileBasedMessageCreateEvent(mce.fromAddr, mce.toAddr, mce.id,
                                                 mce.size, mce.responseSize, mce.time,
                                                 getSubSpecs());
        return mce;
    }
}
