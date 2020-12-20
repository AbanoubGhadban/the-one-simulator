package routing;

import core.Message;
import core.Settings;

public abstract class LimitedHopsRouter extends ActiveRouter {

	/** LimitedHops router's settings name space ({@value})*/ 
	public static final String LIMITEDHOPS_NS = "LimitedHopsRouter";
    /** Max no of hops -setting id ({@value}). Boolean valued.
	 * Specify the max no of hops that message can pass by before being dropped */
    public static final String MAX_HOPS_S = "maxHops";
    
    // Maximum no of hops that message can pass by before being dropped
    protected int maxHops;

    /**
	 * Constructor. Creates a new message router based on the settings in
	 * the given Settings object.
	 * @param s The settings object
	 */
	public LimitedHopsRouter(Settings s) {
        super(s);
        Settings snwSettings = new Settings(LIMITEDHOPS_NS);
        
        if (snwSettings.contains(MAX_HOPS_S)) {
            this.maxHops = snwSettings.getInt(MAX_HOPS_S);
        } else {
            this.maxHops = Integer.MAX_VALUE;
        }
	}
	
	/**
	 * Copy constructor.
	 * @param r The router prototype where setting values are copied from
	 */
	protected LimitedHopsRouter(LimitedHopsRouter r) {
        super(r);
        this.maxHops = r.maxHops;
    }
    
    @Override
	protected int checkReceiving(Message m) {
		if (m.getHopCount() >= this.maxHops) {
			return DENIED_EXCEEDED_MAX_HOPS;
		}
		else {
			 return super.checkReceiving(m);
		}
	}
    
}
