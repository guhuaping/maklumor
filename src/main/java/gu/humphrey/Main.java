package gu.humphrey;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(args[0]));

    staticFileLocation("/public");

    get("/hello", (req, res) -> "Hello World");

      get("/hook", (req, res) -> {
          if ("subscribe".equals(req.attribute("hub.mode")) &&
                  "1234567890".equals(req.attribute("hub.verify_token"))) {
              System.out.println("Validating webhook");
              //res.status(200).send(req.query['hub.challenge']);
              return req.attribute("hub.challenge");
          } else {
              System.out.println("Failed validation. Make sure the validation tokens match.");
              return "token code not match";
          }
      });

    get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());


  }

}
