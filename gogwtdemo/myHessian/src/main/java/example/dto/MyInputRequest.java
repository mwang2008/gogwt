package example.dto;

public class MyInputRequest implements java.io.Serializable {
	private String firstName;
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String toString() {
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append("MyInputRequest[");
		sbuilder.append(" firstName=" + firstName);
		sbuilder.append(" lastName=" + lastName);
		sbuilder.append("]");
		
		return sbuilder.toString();
		
	}

}
