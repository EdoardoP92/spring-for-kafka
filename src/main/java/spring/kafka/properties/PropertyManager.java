package spring.kafka.properties;

public class PropertyManager {
	
	public static String getProperty(String prop) {
		
		return (System.getProperty(prop) != null) ? 
				System.getProperty(prop) : System.getenv(prop);
	}

}
