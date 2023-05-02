package ar.edu.itba.paw.interfaces;

import java.util.Map;

public interface EmailService {
     void sendEmail(final String addressTo, final String subject, final String message);
     String lenderMailFormat(Map<String,Object> variables,String mailTemplate);

}
