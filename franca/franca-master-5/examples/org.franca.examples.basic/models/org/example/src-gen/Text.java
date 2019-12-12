import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Text {
	
	public static void main(String[] args) {
		new Text().run();
	}
	
	protected void doTest() {
		System.out.println("Executing command test (Code)");
	}
	protected void doSadfa() {
		System.out.println("Executing command sadfa (Code)");
	}
	
	protected void run() {
		boolean executeActions = true;
		String currentState = "jere";
		String lastEvent = null;
		while (true) {
			if (currentState.equals("jere")) {
				if (executeActions) {
					executeActions = false;
				}
				System.out.println("Your are now in state 'jere'. Possible events are [].");
				lastEvent = receiveEvent();
			}
			if (currentState.equals("here")) {
				if (executeActions) {
					doSadfa();
					executeActions = false;
				}
				System.out.println("Your are now in state 'here'. Possible events are [].");
				lastEvent = receiveEvent();
			}
			
		}
	}
	
	private String receiveEvent() {
		System.out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException ioe) {
			System.out.println("Problem reading input");
			return "";
		}
	}
}
