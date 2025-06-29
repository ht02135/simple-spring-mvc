package simple.packages.spring.util;

//comment out log4j2 related code cuz it crash web deploy in tomcat
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@Component
public class InternalLogger {

	private static final Logger logger = LogManager.getLogger(InternalLogger.class);
	
	public static void info(String s) {
		System.out.println("logger.info s="+s);
		logger.info("logger.info s="+s);
	}
	
	/*
	log4j2
	1>running log3j2 in eclipse via main worked...  i see log in c:\temp
	2>running log4j2 in tomcat -> nothing happens... 
	*/
	public static void main(String[] args) {
		logger.info("logger.main test");
	}
	
	public InternalLogger() {
		super();
	}
}
