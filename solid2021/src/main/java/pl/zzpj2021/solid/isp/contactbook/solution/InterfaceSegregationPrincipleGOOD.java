package pl.zzpj2021.solid.isp.contactbook.solution;

class InterfaceSegregationPrincipleGOOD {
	

	public InterfaceSegregationPrincipleGOOD() {
	}
	
	public static void main(String[] args) {
		
		InterfaceSegregationPrincipleGOOD interfaceSegregationPrinciple = new InterfaceSegregationPrincipleGOOD();
		interfaceSegregationPrinciple.contactPeople();
		
	}
	
	public void contactPeople() {
		
		Contact contact = new Contact("Jan Kowalski", "Kielce", "jan.kowalski@gmail.com", "83744-23434");
		contact.sendMessage(contact, "promocja", "tanio dzisiaj!");
		contact.makeCall(contact);
	}
	

}
