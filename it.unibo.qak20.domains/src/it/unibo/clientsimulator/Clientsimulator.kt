/* Generated by AN DISI Unibo */ 
package it.unibo.clientsimulator

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Clientsimulator ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 var ClientId = 1  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="dorequest", cond=doswitch() )
				}	 
				state("dorequest") { //this:State
					action { //it:State
						request("enterrequest", "enterrequest($ClientId)" ,"waiter" )  
						 ClientId = ClientId+1  
					}
					 transition(edgeName="t06",targetState="handleEnteranswer",cond=whenReply("enteranswer"))
				}	 
				state("handleEnteranswer") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition( edgeName="goto",targetState="dorequest", cond=doswitchGuarded({ ClientId < 4  
					}) )
					transition( edgeName="goto",targetState="endOfJob", cond=doswitchGuarded({! ( ClientId < 4  
					) }) )
				}	 
				state("endOfJob") { //this:State
					action { //it:State
						println("clientsimulator BYE")
					}
				}	 
			}
		}
}
