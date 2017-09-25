package com.github.tkocsis;

import java.util.logging.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

	private static final Logger log = Logger.getLogger(MainVerticle.class.getName());
	
	@Override
	public void start() throws Exception {
		log.info("Starting app...");
		
		Router router = Router.router(vertx);
		router.get("/").handler(ctx -> {
			ctx.response().end("hello");
		});
		vertx.createHttpServer().requestHandler(router::accept)
			.listen(config().getInteger("http_port"));
		
		log.info("App started");
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}
	
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(MainVerticle.class.getName(), new DeploymentOptions().setConfig(new JsonObject()
				.put("http_port", System.getenv("PORT") != null ? System.getenv("PORT") : 8080)));
	}
}
