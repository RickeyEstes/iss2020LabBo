%====================================================================================
% boundarywalker description   
%====================================================================================
mqttBroker("mqtt.eclipse.org", "1883", "unibo/boundarywalker").
context(ctxboundarywalker, "localhost",  "TCP", "8068").
context(ctxbasicrobot, "192.168.1.68",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( boundarywalker, ctxboundarywalker, "it.unibo.boundarywalker.Boundarywalker").
