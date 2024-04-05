package connectfour.model.network;

public abstract class Payload{
	public abstract String serialize();
	public abstract void deserialize(String payload);
}
