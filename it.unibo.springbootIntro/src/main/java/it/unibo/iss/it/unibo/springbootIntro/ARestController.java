package it.unibo.iss.it.unibo.springbootIntro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * the fundamental difference between a web application and a REST API is that 
 * the response from a web application is generally view (HTML + CSS + JavaScript)  
 * because they are intended for human viewers while 
 * REST API just return data in form of JSON or XML 
 * because most of the REST clients are programs. 
 * This difference is also obvious in the @Controller and @RestController annotation.
 * Read more: https://javarevisited.blogspot.com/2017/08/difference-between-restcontroller-and-controller-annotations-spring-mvc-rest.html#ixzz6EZz3m5yT
 
 * The job of @Controller is to create a Map of model object and find a view 
 * but @RestController simply return the object and object data is directly written 
 * into HTTP response as JSON or XML.

 */
@RestController  //combination of @Controller and @ResponseBody
public class ARestController { 
	
	private ConnRobotTcp robotConn  = new ConnRobotTcp();
	public ARestController() {
		robotConn.connect("localhost", 8018, "basicrobot");
	}
	
    @GetMapping("/cmd")  
    public String handleCmd(@RequestParam(value = "v", defaultValue = "h") String robotcmd ) {
    	ApplModel.curState = "executing cmd=>"+ robotcmd;
    	robotConn.forward(robotcmd);
        return String.format("ARestController handleCmd %s", robotcmd );  
    }
 
 
}
