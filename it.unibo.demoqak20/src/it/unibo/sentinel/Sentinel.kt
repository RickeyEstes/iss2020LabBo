/* Generated by AN DISI Unibo */ 
package it.unibo.sentinel

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sentinel ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 var counter=0  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
					}
					 transition( edgeName="goto",targetState="watch", cond=doswitchGuarded({ counter==0  
					}) )
					transition( edgeName="goto",targetState="end", cond=doswitchGuarded({! ( counter==0  
					) }) )
				}	 
				state("watch") { //this:State
					action { //it:State
						println("sentinel | WATCH")
					}
					 transition(edgeName="t00",targetState="handleAlarm",cond=whenEventGuarded("alarm",{counter==0}))
				}	 
				state("timeout") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("sentinel | TIMEOUT")
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("handleAlarm") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						if( checkMsgContent( Term.createTerm("alarm(V)"), Term.createTerm("alarm(V)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("sentinel | ALARM ${payloadArg(0)} ")
						}
						stateTimer = TimerActor("timer_handleAlarm", 
							scope, context!!, "local_tout_sentinel_handleAlarm", 1000.toLong() )
					}
					 transition(edgeName="t01",targetState="explore",cond=whenTimeout("local_tout_sentinel_handleAlarm"))   
				}	 
				state("explore") { //this:State
					action { //it:State
						println("sentinel | exploring ...")
					}
					 transition( edgeName="goto",targetState="watch", cond=doswitch() )
				}	 
				state("end") { //this:State
					action { //it:State
						println("sentinel | ENDS")
						terminate(0)
					}
				}	 
			}
		}
}
