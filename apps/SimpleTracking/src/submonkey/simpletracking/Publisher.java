package submonkey.simpletracking;

import org.fusesource.stomp.client.Future;
import org.fusesource.stomp.client.FutureConnection;
import org.fusesource.stomp.client.Stomp;
import org.fusesource.stomp.codec.StompFrame;

import android.util.Log;
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
			Log.d("publisher", e.getMessage());
		}
	}
	
	public void disconnect() {
		connection.close();
	}
	
	public void publish(String user, String role, double latitude, double longitude) {
		try {
			StompFrame frame = new StompFrame(SEND);
			frame.addHeader(DESTINATION, StompFrame.encodeHeader("/topic/" + user));
			frame.addHeader(ID, connection.nextId());
			frame.addHeader(Constants.USER, StompFrame.encodeHeader(user));
			frame.addHeader(Constants.ROLE, StompFrame.encodeHeader(role));
			frame.addHeader(Constants.LATITUDE, StompFrame.encodeHeader(String.valueOf(latitude)));
			frame.addHeader(Constants.LONGITUDE, StompFrame.encodeHeader(String.valueOf(longitude)));
			Future<Void> send = connection.send(frame);
			send.await();
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("publisher", e.getMessage());
		}
	}
}
