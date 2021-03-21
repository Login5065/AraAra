package pl.zzpj2021.solid.isp.contactbook.solution;

interface Emailer {
	
	 default void sendMessage(Contact emailable, String subject, String body) {
		
		String emailAddress = emailable.getEmailAddress();
		
		sendEmail(emailAddress, subject, body);
	}

	
	
	
	
	default void sendEmail(String emailAddress, String subject, String body) {
		// TODO Auto-generated method stub
		
	}
}