/* Generated by AN DISI Unibo */ 
package it.unibo.mbotrobot

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Mbotrobot ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("mbotrobot | START")
						robotMbot.mbotSupport.create(myself ,"/dev/ttyUSB0" )
						delay(1000) 
						robotMbot.mbotSupport.move( "l"  )
						delay(1000) 
						robotMbot.mbotSupport.move( "r"  )
						updateResourceRep( "stopped"  
						)
						  var realsonar = context!!.hasActor("realsonar")  
						 			if( realsonar != null ){ 
						 				println("mbotrobot | WORKING IN A REAL ENV") 
						 				//ACTIVATE THE DATA SOURCE realsonar
						 				forward("sonarstart", "sonarstart(1)" ,"realsonar" ) 				
						 				//SET THE PIPE
						 				realsonar.
						 				subscribeLocalActor("datacleaner").
						 				subscribeLocalActor("distancefilter").
						 				subscribeLocalActor("mbotrobot")		//in order to perceive obstacle
						 			}else{
						 				println("nanorobot | WARNING: realsonar NOT FOUND")
						 			}
						discardMessages = false
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
					}
					 transition(edgeName="t10",targetState="execcmd",cond=whenDispatch("cmd"))
					transition(edgeName="t11",targetState="handleObstacle",cond=whenEvent("obstacle"))
					transition(edgeName="t12",targetState="handleSonar",cond=whenEvent("sonar"))
				}	 
				state("execcmd") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("cmd(X)"), Term.createTerm("cmd(MOVE)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								updateResourceRep( "move(${payloadArg(0)})"  
								)
								robotMbot.mbotSupport.move( payloadArg(0)  )
						}
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleObstacle") { //this:State
					action { //it:State
						println("mbotrobot | handleObstacle")
						robotMbot.mbotSupport.move( "h"  )
						robotMbot.mbotSupport.move( "s"  )
						delay(400) 
						robotMbot.mbotSupport.move( "h"  )
						if( checkMsgContent( Term.createTerm("obstacle(D)"), Term.createTerm("obstacle(TARGET)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								updateResourceRep( "obstacle"  
								)
						}
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleSonar") { //this:State
					action { //it:State
						println("$name in ${currentState.stateName} | $currentMsg")
					}
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
}
