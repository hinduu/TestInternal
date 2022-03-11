 package com.idf.ping;

import java.rmi.RemoteException;
import java.util.Map.Entry;
import java.util.UUID;

import com.idf.ping.ManagementServiceStub.RequestIdType;
import com.idf.ping.ManagementServiceStub.SetTemporaryPasswordRequest;
import com.idf.ping.ManagementServiceStub.SetTemporaryPasswordRequestType;
import com.idf.ping.ManagementServiceStub.SetTemporaryPasswordResponse;
import com.idf.ping.ManagementServiceStub.SetTemporaryPasswordResponseType;
import com.idf.ping.ManagementServiceStub.TemporaryPasswordType;
import com.idf.ping.ManagementServiceStub.UserIdType;

//import sun.security.action.GetBooleanSecurityPropertyAction;

public class SymPasswordImpl {
	public static void main(String args[]) throws RemoteException {
		SymPasswordImpl obj = new SymPasswordImpl();
		
		obj.getSymToken("md236@ntrs.com");
	}
	
	/*
	 * This class will return a temporary password from Symantec VIP 
	 * Request params - Request ID and User ID
	 * Response contains the Temporary password 
	 */
	public String getSymToken(String emailAddress) throws RemoteException {
		UUID uId = UUID.randomUUID();
		SetTemporaryPasswordRequest req = new SetTemporaryPasswordRequest();
		SetTemporaryPasswordRequestType reqType = new SetTemporaryPasswordRequestType();
		SetTemporaryPasswordResponse respType = new SetTemporaryPasswordResponse();
       // String pathToP12File = "C:\\Users\\VN84\\Desktop\\ping\\catchpoint\\access_certification_vip_cert\\access_certification_vip_cert.p12";
	   String pathToP12File = "C:\\Users\\md236\\Downloads\\access_certification_vip_cert.p12";
	   //String pathToP12File = "/Users/md236/Downloads/access_certification_vip_cert.p12";
	      //String pathToP12File = "access_certification_vip_cert.p12";

	      
		String password = "Ntrs1234";
		System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
		System.setProperty("javax.net.ssl.keyStore", pathToP12File);
		System.setProperty("javax.net.ssl.keyStorePassword", password);
		System.setProperty("http.proxyHost", "https-proxy.ntrs.com");
		System.setProperty("http.proxyPort", "443");
		
		//for (Entry<Object, Object> e : System.getProperties().entrySet()) {
      //      System.out.println("key: " + e.getKey() + " value: " + e.getValue());
     //   }
		String symToken = null;
		try { 
			
			ManagementServiceStub stub = new 
					ManagementServiceStub("https://userservices-auth.vip.symantec.com/vipuserservices/ManagementService_1_10");
			RequestIdType IdType = new RequestIdType();
			IdType.setRequestIdType("100000");
			UserIdType usrId = new UserIdType();
			usrId.setUserIdType(emailAddress);
			reqType.setUserId(usrId);
			reqType.setRequestId(IdType);
			req.setSetTemporaryPasswordRequest(reqType);
	 		respType = stub.setTemporaryPassword(req);
	 		SetTemporaryPasswordResponseType tempPwdType = respType.getSetTemporaryPasswordResponse();
	 		TemporaryPasswordType tempPwd=tempPwdType.getTemporaryPassword();
	 		 symToken = tempPwd.localTemporaryPasswordType;
	 		System.out.println(symToken);	
	 		
		} catch (Exception ex) {
			System.out.println("Exception - " + ex.getMessage());
		}
		return symToken;
	}
}
