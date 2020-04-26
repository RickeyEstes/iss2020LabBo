/* Generated by AN DISI Unibo */ 
package it.unibo.robotboundary

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Robotboundary ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 var NumStep=0  
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("boundaryrobot | START")
						discardMessages = true
					}
					 transition( edgeName="goto",targetState="waitcmd", cond=doswitch() )
				}	 
				state("waitcmd") { //this:State
					action { //it:State
						println("boundaryrobot | waitcmd")
					}
					 transition(edgeName="t00",targetState="work",cond=whenDispatch("start"))
				}	 
				state("work") { //this:State
					action { //it:State
						println("boundaryrobot | working ${NumStep}")
						updateResourceRep( "moving"  
						)
						emit("simulateobstacle", "simulateobstacle(0)" ) 
					}
					 transition(edgeName="t01",targetState="stopped",cond=whenDispatch("stop"))
					transition(edgeName="t02",targetState="wall",cond=whenEvent("collision"))
				}	 
				state("wall") { //this:State
					action { //it:State
						 NumStep++  
						println("boundaryrobot | handleCollision ${NumStep}")
						updateResourceRep( "rotating"   
						)
					}
					 transition( edgeName="goto",targetState="work", cond=doswitchGuarded({ NumStep<4  
					}) )
					transition( edgeName="goto",targetState="endWork", cond=doswitchGuarded({! ( NumStep<4  
					) }) )
				}	 
				state("stopped") { //this:State
					action { //it:State
						println("boundaryrobot | stopped")
						updateResourceRep( "stopped"  
						)
					}
					 transition(edgeName="t03",targetState="work",cond=whenDispatch("resume"))
				}	 
				state("endWork") { //this:State
					action { //it:State
						println("boundaryrobot | ends")
						updateResourceRep( "terminated"  
						)
						terminate(0)
					}
				}	 
			}
		}
}
