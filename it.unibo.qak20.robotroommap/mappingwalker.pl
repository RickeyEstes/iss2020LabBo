%====================================================================================
% mappingwalker description   
%====================================================================================
mqttBroker("localhost", "1883", "unibo/polar").
context(ctxmappingwalker, "localhost",  "TCP", "8030").
context(ctxbasicrobot, "192.168.1.68",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( mappingwalker, ctxmappingwalker, "it.unibo.mappingwalker.Mappingwalker").