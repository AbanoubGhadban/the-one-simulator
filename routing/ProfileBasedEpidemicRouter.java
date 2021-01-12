package routing;

import java.util.Map;

import core.DTNHost;
import core.Message;
import core.MessageListener;
import core.Settings;
import core.ValuesContainer;

public class ProfileBasedEpidemicRouter extends EpidemicRouter {
    public static final String PROFILEBASEDMULTICAST_NS = "ProfileBasedEpidemicRouter";
    public static final String PROFILE_SPECS_PROPERTY = PROFILEBASEDMULTICAST_NS + "." + "profileSpecs";

    public ProfileBasedEpidemicRouter(Settings s) {
        super(s);
    }

    protected ProfileBasedEpidemicRouter(ProfileBasedEpidemicRouter r) {
		super(r);
    }
    
    boolean matchProfile(Message m) {
        Map<String, ValuesContainer> profile = getHost().getProfile();
        Map<String, ValuesContainer> msgProfile = (Map<String, ValuesContainer>)m.getProperty(PROFILE_SPECS_PROPERTY);

        if (profile == null || msgProfile == null)
            return false;

        for (Map.Entry<String, ValuesContainer> i : profile.entrySet()) {
            if (msgProfile.containsKey(i.getKey()) && msgProfile.get(i.getKey()).containsAny(i.getValue()))
                return true; 
        }
        return false;
    }
    
    @Override
    public Message messageTransferred(String id, DTNHost from) {
        Message m = super.messageTransferred(id, from);
        if (isDeliveredMessage(m) || !matchProfile(m))
            return m;

        this.deliveredMessages.put(id, m);
        for (MessageListener ml : this.mListeners) {
			ml.messageTransferred(m, from, this.getHost(), true);
        }
        return m;
    }
}
