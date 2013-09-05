package test;

import org.fusesource.stomp.client.Future;
import org.fusesource.stomp.client.FutureConnection;
import org.fusesource.stomp.client.Stomp;
import org.fusesource.stomp.codec.StompFrame;

import static org.fusesource.stomp.client.Constants.*;

public class Publisher {

	private FutureConnection connection;
	
	public Publisher() {
	}
	
	public void connect(String server, int port) {
		try {
			Stomp stomp = new Stomp(server, port);
			Future<FutureConnection> future = stomp.connectFuture();
			connection = future.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		connection.close();
	}
	
	public void publish(String user, String role, float lattitude, float longitude) {
		try {
			StompFrame frame = new StompFrame(SEND);
			frame.addHeader(DESTINATION, StompFrame.encodeHeader("/topic/" + user));
			frame.addHeader(ID, connection.nextId());
			frame.addHeader(Constants.USER, StompFrame.encodeHeader(user));
			frame.addHeader(Constants.ROLE, StompFrame.encodeHeader(role));
			frame.addHeader(Constants.LATTITUDE, StompFrame.encodeHeader(String.valueOf(lattitude)));
			frame.addHeader(Constants.LONGITUDE, StompFrame.encodeHeader(String.valueOf(longitude)));
			Future<Void> send = connection.send(frame);
			send.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
