package cz.devfire.java2.spaceInvaders.server;

import io.quarkus.runtime.Quarkus;
import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "REST")
public class Server {

	public static void launch(String[] args) {
		log.info("Starting server ...");
		Quarkus.run(args);
	}

}
