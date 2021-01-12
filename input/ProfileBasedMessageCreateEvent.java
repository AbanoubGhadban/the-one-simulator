package input;

import java.util.Map;

import core.World;
import routing.ProfileBasedEpidemicRouter;
import core.DTNHost;

import core.Message;
import core.ValuesContainer;

public class ProfileBasedMessageCreateEvent extends MessageCreateEvent {
     Map<String, ValuesContainer> profileSpecs;

    public ProfileBasedMessageCreateEvent(int from, int to, String id, int size, int responseSize, double time, Map<String, ValuesContainer> profileSpecs) {
        super(from, to, id, size, responseSize, time);
        this.profileSpecs = profileSpecs;
    }
    
    @Override
    public void processEvent(World world) {
        DTNHost to = world.getNodeByAddress(this.toAddr);
		DTNHost from = world.getNodeByAddress(this.fromAddr);			
		
        Message m = new Message(from, to, this.id, this.size);
        m.addProperty(ProfileBasedEpidemicRouter.PROFILE_SPECS_PROPERTY, this.profileSpecs);
		m.setResponseSize(this.responseSize);
		from.createNewMessage(m);
    }
}
