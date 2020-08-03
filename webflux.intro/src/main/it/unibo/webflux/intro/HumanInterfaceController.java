package it.unibo.webflux.intro;

import java.time.Duration;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class HumanInterfaceController {
 	private int elCount = 0;
	private String outS = "";

	private ControllerUtils ctrlUtil = new ControllerUtils();
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
//	private void sendMsgToGui( String msg ) {
//    	simpMessagingTemplate.convertAndSend(WebSocketConfig.topicForClient, new ResourceRep( HtmlUtils.htmlEscape( msg )  ));		
//	}

 
	@GetMapping("/")
//    @ResponseBody
    public String entry(Model model) {
		elCount = 0;	//reset counter of elements
    	return "robotGuiSocket";  
    }

	@GetMapping("/update")
//  @ResponseBody
  public String autonomousupdate(Model model) {
		ctrlUtil.setSimpMessagingTemplate( simpMessagingTemplate );
		new Thread() {
			int n = 0;
			public void run() {
				for( int i=0; i<5; i++) {
					ctrlUtil.sendMsgToGui( "autonomous update " + n++ );
					ctrlUtil.delay(1000);
				}
			}
		}.start();
  	return "robotGuiSocket";  
  }
	
	
	public void demoCreate0() {
		  Flux bridge = Flux.push(
			emitter -> { //emitter type= Consumer<? super FluxSink<String>>
			  for (int i = 0; i < 3; i++) {
				emitter.next(String.valueOf(i));
			  }
		  });
//		 bridge.subscribe(item -> System.out.println("demoCreate0 - 1: " + item));
//		 bridge.subscribe(item -> System.out.println("demoCreate0 - 2: " + item));
		}

//	@GetMapping("/updateflux1")
//	private String createHotFlux() {
// 		DirectProcessor<String> hotSource = DirectProcessor.create();
//		Flux<String> hotFlux              = hotSource.map(String::toUpperCase) ;
//		hotSource.delayElements(Duration.ofMillis(1000L));
//		int myn = ++elCount;   //a new hotflux
//		hotFlux.subscribe( 
//				v     -> sendMsgToGui(v) ,
//				error -> System.out.println("autonomousupdateflux error= " + error ),
//				()    -> System.out.println("autonomousupdateflux END " )
//		);
//		for( int i=0; i<10; i++) {
//			hotSource.onNext("hotFlux_" + myn +" value= " + i);		   	
//  			delay(1000);
//		}
//		hotSource.onComplete();		
//		
//		return "robotGuiSocket"; 
//	}
 	
/*	
	private DirectProcessor<String> createHotSource() {
		DirectProcessor<String> hotSource = DirectProcessor.create();
//		Flux<String> hotFlux              = hotSource.map( String::toUpperCase ) ;  //
		int myn = ++elCount;   //a new hotflux
		hotSource.subscribe( 
				v     -> sendMsgToGui( v ) ,
				error -> System.out.println("autonomousupdateflux error= " + error ),
				()    -> sendMsgToGui( "hotSource_" + myn +" ends" )
		); 
		return hotSource;
 	}
	
	private void populateHotFlux( DirectProcessor<String> hotSource ) {
		int myn = elCount;    
		new Thread() {
 			public void run() {
				for( int i=0; i<10; i++) {
					hotSource.onNext("hotSource_" + myn +" emits value= " + i);		    	
					delay( 1000 + myn*50);
				}
				hotSource.onComplete();		
			}
		}.start();		
	}
*/	
	@GetMapping("/updateflux")
//  @ResponseBody
  public String autonomousupdateflux(Model model) {
		DirectProcessor<String> hotSource =  ctrlUtil.createHotSource(simpMessagingTemplate);
		ctrlUtil.populateHotFlux( hotSource );
  	return "robotGuiSocket";  
  }
	
	@GetMapping("/gui")
//    @ResponseBody
    public Publisher<String> showGui() {
    	return Mono.just( "robotGuiSocket" );
    }

//	@GetMapping("/todo")
//    @ResponseBody
//    public Publisher<String> handler() {
//		hotFlux.subscribe( d -> outS = outS + " | " + d );
//		buildAnswer();
//		delay(3000); 
//   		return Mono.just( outS );
//    }
    
   
//	public void buildAnswer() {
//   		hotFlux.subscribe(d -> System.out.println("Local observer: "+d));	 
//  		for( int i= 0; i<5; i++ ) { 
//  			delay(1000); 
//  			hotSource.onNext("value"+ n++); 
//  		}
//		hotSource.onComplete();		
// 	}
	
	
    /*
     * Update the page via socket.io. Thanks to Eugenio Cerulo
     * https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/messaging/handler/annotation/MessageMapping.html
     * https://spring.io/guides/gs/messaging-stomp-websocket/
     */

    	@MessageMapping("/showresource")
    	@SendTo("/topic/display")
    	public ResourceRep showresource(@Payload String message) {	//The receiver will look at a field named content
    		ResourceRep rep = new ResourceRep( HtmlUtils.htmlEscape("showresource after message=" + message)  );  
    		return rep;
    	}
    	
    	
//	private void delay(int dt) {
//		try {
//			Thread.sleep(dt);
//		} catch (InterruptedException e) {
//				e.printStackTrace();
//		}		
//	}
    
}