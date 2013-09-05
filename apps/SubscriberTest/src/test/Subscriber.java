package test;

import org.fusesource.stomp.client.Future;
import org.fusesource.stomp.client.FutureConnection;
import org.fusesource.stomp.client.Stomp;
import org.fusesource.stomp.codec.StompFrame;

import static org.fusesource.stomp.client.Constants.*;

public class Subscriber extends Thread {

	private SubscriberFrame parent;
	private FutureConnection connection;
	private boolean stop;
	
	public Subscriber(SubscriberFrame parent) {
		this.parent = parent;
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
		stop = true;
		connection.close();
	}
	
	public void subscribe(String topic) {
		try {
			StompFrame frame = new StompFrame(SUBSCRIBE);
			frame.addHeader(DESTINATION, StompFrame.encodeHeader("/topic/" + topic));
			frame.addHeader(SELECTOR, StompFrame.encodeHeader("role = 'Role2'"));
			frame.addHeader(ID, connection.nextId());
			Future<StompFrame> response = connection.request(frame);
			response.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		Future<StompFrame> receiveFuture;
		StompFrame received;
		System.out.println("Inside thread...");
		while (!stop) {
			try {
				parent.updateOutput("Waiting...");
				receiveFuture = connection.receive();
				received = receiveFuture.await();
				parent.updateOutput(received.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
