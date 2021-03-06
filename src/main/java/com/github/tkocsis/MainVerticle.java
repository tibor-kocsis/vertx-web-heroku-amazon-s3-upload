package com.github.tkocsis;

import java.util.logging.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class MainVerticle extends AbstractVerticle {

	private static final Logger log = Logger.getLogger(MainVerticle.class.getName());
	
	@Override
	public void start() throws Exception {
		log.info("Starting app with config: " + config().encodePrettily());
		
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.get("/").handler(ctx -> {
			ctx.response().end("hello");
		});
		router.get("/web").handler(StaticHandler.create("web"));
		vertx.createHttpServer().requestHandler(router::accept)
			.listen(Integer.parseInt(config().getString("http_port")));
		
		log.info("App started");
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}
	
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(MainVerticle.class.getName(), new DeploymentOptions().setConfig(new JsonObject()
				.put("http_port", System.getenv("PORT") != null ? System.getenv("PORT") : "8080")));
	}
}
