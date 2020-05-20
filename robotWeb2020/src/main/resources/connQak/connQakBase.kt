package connQak 
import it.unibo.`is`.interfaces.IObserver
import java.util.Observable
import it.unibo.kactor.ApplMessage
import connQak.connQakCoap
 
enum class ConnectionType {
    TCP, MQTT, COAP, HTTP
}

//@file:JvmName("ConnUtils")
abstract class connQakBase(val hostIP : String,   val port : String,   val destName : String) {
lateinit var currQakConn  : connQakBase
	
	companion object{
	fun create(connType: ConnectionType,   hostIP : String,   port : String,
			   destName : String) : connQakBase{
		  showSystemInfo()
		  when( connType ){
				 ConnectionType.COAP ->
				 	{return connQakCoap(hostIP, port, destName)}  
//				 ConnectionType.MQTT -> 
//				 	{return connQakMqtt(hostIP, port, destName)}  
//  				 ConnectionType.HTTP ->  
// 				 	{return connQakHttp(hostIP, port, destName)} 
//				 ConnectionType.TCP ->
//				 	{return connQakTcp(hostIP, port, destName)}  
   				 else -> { println("WARNING: protocol unknown");
					 return connQakCoap(hostIP, port, destName)} 
 		  }		
	}
		
	fun showSystemInfo(){
		println(
			"connQakBase  | COMPUTER memory="+ Runtime.getRuntime().totalMemory() +
					" num of processors=" +  Runtime.getRuntime().availableProcessors());
		println(
			"connQakBase  | NUM of threads="+ Thread.activeCount() +
					" currentThread=" + Thread.currentThread() );
	}
	fun println(msg : String){
		System.out.println(msg)
	}
	}//object

	
	  abstract fun createConnection( )     
      abstract fun forward( msg : ApplMessage )
      abstract fun request( msg : ApplMessage )
      abstract fun emit( msg : ApplMessage )
	
	fun println(msg : String){
		System.out.println(msg)
	}
	
}

 
 
 