/* Generated by AN DISI Unibo */ 
package it.unibo.sentinel

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sentinel ( name: String, scope: CoroutineScope ) : ActorBasicFsm( name, scope){
 	
	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						stateTimer = TimerActor("timer_s0", 
							scope, context!!, "local_tout_sentinel_s0", 1000.toLong() )
					}
					 transition(edgeName="t03",targetState="timeout",cond=whenTimeout("local_tout_sentinel_s0"))   
					transition(edgeName="t04",targetState="handleAlarm",cond=whenEvent("alarm"))
				}	 
				state("timeout") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("sentinel handles timeout ")
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
				state("handleAlarm") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
						println("sentinel handles alarm ")
					}
					 transition( edgeName="goto",targetState="s0", cond=doswitch() )
				}	 
			}
		}
}