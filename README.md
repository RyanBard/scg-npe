# NullPointerException in spring-cloud-gateway

Note that reproducing this seems to require that the uri and the predicate Path in the [application.yml](src/main/resources/application.yml) don't match.  I'm not sure if this is an invalid config, because I haven't got an example working enough to know whether the full uri feeds into the Path filter or not.  After analyzing the code in a debugger, I'm not sure how this isn't a problem for other situations (where the uri and predicate Path don't mismatch).

## Curl to reproduce the error

```
$ curl -v 'http://localhost:8080/def/xyz'
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /def/xyz HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.54.0
> Accept: */*
> 
< HTTP/1.1 500 Internal Server Error
< transfer-encoding: chunked
< X-Response-Foo: Bar
< X-Response-Blah: Blargh
< Content-Type: application/json;charset=UTF-8
< 
* Connection #0 to host localhost left intact
{"timestamp":1513119090377,"path":"/def/xyz","message":null,"status":500,"error":"Internal Server Error"}
```

## Server error

```
[INFO] --- spring-boot-maven-plugin:2.0.0.M6:run (default-cli) @ scg-npe ---

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::             (v2.0.0.M6)

2017-12-12 17:50:18.074  INFO 25634 --- [           main] scg.example.ScgApplication               : Starting ScgApplication on jbard-M01.vmware.com with PID 25634 (/Users/jbard/projects/scg-npe/target/classes started by jbard in /Users/jbard/projects/scg-npe)
2017-12-12 17:50:18.080  INFO 25634 --- [           main] scg.example.ScgApplication               : No active profile set, falling back to default profiles: default
2017-12-12 17:50:18.167  INFO 25634 --- [           main] .r.c.ReactiveWebServerApplicationContext : Refreshing org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext@697519ca: startup date [Tue Dec 12 17:50:18 EST 2017]; root of context hierarchy
2017-12-12 17:50:20.144  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [After]
2017-12-12 17:50:20.144  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Before]
2017-12-12 17:50:20.144  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Between]
2017-12-12 17:50:20.144  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Cookie]
2017-12-12 17:50:20.144  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Header]
2017-12-12 17:50:20.144  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Host]
2017-12-12 17:50:20.145  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Method]
2017-12-12 17:50:20.145  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Path]
2017-12-12 17:50:20.145  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Query]
2017-12-12 17:50:20.145  INFO 25634 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [RemoteAddr]
2017-12-12 17:50:20.295  INFO 25634 --- [           main] o.s.w.r.r.m.a.ControllerMethodResolver   : Looking for @ControllerAdvice: org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext@697519ca: startup date [Tue Dec 12 17:50:18 EST 2017]; root of context hierarchy
2017-12-12 17:50:20.849  INFO 25634 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2017-12-12 17:50:20.987  INFO 25634 --- [ctor-http-nio-1] r.ipc.netty.tcp.BlockingNettyContext     : Started HttpServer on /0:0:0:0:0:0:0:0:8080
2017-12-12 17:50:20.988  INFO 25634 --- [           main] o.s.b.web.embedded.netty.NettyWebServer  : Netty started on port(s): 8080
2017-12-12 17:50:20.993  INFO 25634 --- [           main] scg.example.ScgApplication               : Started ScgApplication in 3.462 seconds (JVM running for 7.603)
2017-12-12 17:51:30.388 ERROR 25634 --- [ctor-http-nio-2] .a.w.r.e.DefaultErrorWebExceptionHandler : Failed to handle request [GET http://localhost:8080/def/xyz]

java.lang.NullPointerException: null
	at org.springframework.cloud.gateway.filter.NettyRoutingFilter.filter(NettyRoutingFilter.java:67) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.handler.FilteringWebHandler$GatewayFilterAdapter.filter(FilteringWebHandler.java:121) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.filter.OrderedGatewayFilter.filter(OrderedGatewayFilter.java:40) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.handler.FilteringWebHandler$DefaultGatewayFilterChain.filter(FilteringWebHandler.java:103) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter.filter(RouteToRequestUrlFilter.java:59) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.handler.FilteringWebHandler$GatewayFilterAdapter.filter(FilteringWebHandler.java:121) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.filter.OrderedGatewayFilter.filter(OrderedGatewayFilter.java:40) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.handler.FilteringWebHandler$DefaultGatewayFilterChain.filter(FilteringWebHandler.java:103) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.filter.factory.AddResponseHeaderGatewayFilterFactory.lambda$apply$0(AddResponseHeaderGatewayFilterFactory.java:47) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.filter.OrderedGatewayFilter.filter(OrderedGatewayFilter.java:40) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.handler.FilteringWebHandler$DefaultGatewayFilterChain.filter(FilteringWebHandler.java:103) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.filter.factory.AddResponseHeaderGatewayFilterFactory.lambda$apply$0(AddResponseHeaderGatewayFilterFactory.java:47) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.filter.OrderedGatewayFilter.filter(OrderedGatewayFilter.java:40) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.handler.FilteringWebHandler$DefaultGatewayFilterChain.filter(FilteringWebHandler.java:103) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.filter.NettyWriteResponseFilter.filter(NettyWriteResponseFilter.java:52) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.handler.FilteringWebHandler$GatewayFilterAdapter.filter(FilteringWebHandler.java:121) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.filter.OrderedGatewayFilter.filter(OrderedGatewayFilter.java:40) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.handler.FilteringWebHandler$DefaultGatewayFilterChain.filter(FilteringWebHandler.java:103) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.cloud.gateway.handler.FilteringWebHandler.handle(FilteringWebHandler.java:87) ~[spring-cloud-gateway-core-2.0.0.M5.jar:2.0.0.M5]
	at org.springframework.web.reactive.result.SimpleHandlerAdapter.handle(SimpleHandlerAdapter.java:62) ~[spring-webflux-5.0.1.RELEASE.jar:5.0.1.RELEASE]
	at org.springframework.web.reactive.DispatcherHandler.invokeHandler(DispatcherHandler.java:168) ~[spring-webflux-5.0.1.RELEASE.jar:5.0.1.RELEASE]
	at org.springframework.web.reactive.DispatcherHandler.lambda$handle$1(DispatcherHandler.java:160) ~[spring-webflux-5.0.1.RELEASE.jar:5.0.1.RELEASE]
	at reactor.core.publisher.MonoFlatMap$FlatMapMain.onNext(MonoFlatMap.java:118) [reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxSwitchIfEmpty$SwitchIfEmptySubscriber.onNext(FluxSwitchIfEmpty.java:67) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoNext$NextSubscriber.onNext(MonoNext.java:76) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxConcatMap$ConcatMapImmediate.innerNext(FluxConcatMap.java:271) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxConcatMap$ConcatMapInner.onNext(FluxConcatMap.java:798) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxSwitchIfEmpty$SwitchIfEmptySubscriber.onNext(FluxSwitchIfEmpty.java:67) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.Operators$MonoSubscriber.complete(Operators.java:1092) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoFlatMap$FlatMapMain.onNext(MonoFlatMap.java:144) [reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoNext$NextSubscriber.onNext(MonoNext.java:76) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.tryOnNext(FluxFilterFuseable.java:129) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxIterable$IterableSubscriptionConditional.fastPath(FluxIterable.java:485) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxIterable$IterableSubscriptionConditional.request(FluxIterable.java:385) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.request(FluxFilterFuseable.java:170) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoNext$NextSubscriber.request(MonoNext.java:102) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.request(FluxMap.java:149) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoFlatMap$FlatMapMain.onSubscribe(MonoFlatMap.java:103) [reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onSubscribe(FluxMap.java:86) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoNext$NextSubscriber.onSubscribe(MonoNext.java:64) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.onSubscribe(FluxFilterFuseable.java:79) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:87) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:55) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxFilterFuseable.subscribe(FluxFilterFuseable.java:51) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoNext.subscribe(MonoNext.java:40) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoMap.subscribe(MonoMap.java:55) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoFlatMap.subscribe(MonoFlatMap.java:60) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoSwitchIfEmpty.subscribe(MonoSwitchIfEmpty.java:44) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoMap.subscribe(MonoMap.java:55) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.Mono.subscribe(Mono.java:2913) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxConcatMap$ConcatMapImmediate.drain(FluxConcatMap.java:418) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxConcatMap$ConcatMapImmediate.onSubscribe(FluxConcatMap.java:210) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:91) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:55) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.FluxConcatMap.subscribe(FluxConcatMap.java:121) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoNext.subscribe(MonoNext.java:40) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoSwitchIfEmpty.subscribe(MonoSwitchIfEmpty.java:44) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoFlatMap.subscribe(MonoFlatMap.java:60) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoFlatMap.subscribe(MonoFlatMap.java:60) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoOnErrorResume.subscribe(MonoOnErrorResume.java:44) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoOnErrorResume.subscribe(MonoOnErrorResume.java:44) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoOnErrorResume.subscribe(MonoOnErrorResume.java:44) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.Mono.subscribe(Mono.java:2913) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen$ThenIgnoreMain.drain(MonoIgnoreThen.java:167) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen.subscribe(MonoIgnoreThen.java:56) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoOnErrorResume.subscribe(MonoOnErrorResume.java:44) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.core.publisher.MonoPeekTerminal.subscribe(MonoPeekTerminal.java:61) ~[reactor-core-3.1.1.RELEASE.jar:3.1.1.RELEASE]
	at reactor.ipc.netty.channel.ChannelOperations.applyHandler(ChannelOperations.java:388) ~[reactor-netty-0.7.1.RELEASE.jar:0.7.1.RELEASE]
	at reactor.ipc.netty.http.server.HttpServerOperations.onHandlerStart(HttpServerOperations.java:359) ~[reactor-netty-0.7.1.RELEASE.jar:0.7.1.RELEASE]
	at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:163) ~[netty-common-4.1.16.Final.jar:4.1.16.Final]
	at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:403) ~[netty-common-4.1.16.Final.jar:4.1.16.Final]
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:463) ~[netty-transport-4.1.16.Final.jar:4.1.16.Final]
	at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:858) ~[netty-common-4.1.16.Final.jar:4.1.16.Final]
	at java.lang.Thread.run(Thread.java:748) ~[na:1.8.0_144]
```

## Things I found while debugging

In [NettyRoutingFilter stopped at line 67](https://github.com/spring-cloud/spring-cloud-gateway/blob/v2.0.0.M5/spring-cloud-gateway-core/src/main/java/org/springframework/cloud/gateway/filter/NettyRoutingFilter.java#L67):

* requestUrl.toString: `"//localhost:8080/abc/**"`
* scheme: `null`
* isAlreadyRouted(exchange): `false`

It will NullPointerException on the `!scheme.equals("http")`.

In [RouteToRequestUrlFilter stopped at line 54](https://github.com/spring-cloud/spring-cloud-gateway/blob/v2.0.0.M5/spring-cloud-gateway-core/src/main/java/org/springframework/cloud/gateway/filter/RouteToRequestUrlFilter.java#L54):

* exchange.request.request: `"GET:/def/xyz"`
* exchange.request.uri: `"http://localhost:8080/def/xyz"`
* exchange.request.path.fullPath: `"/def/xyz"`
* route.uri: `"/abc/**"`

Line 54 will set the scheme to `"http"` from the `"http://localhost:8080/def/xyz"` in the `UriComponentsBuilder.fromHttpRequest(exchange.getRequest())` call, however, 
line 55's `.uri(route.getUri())` will overwrite the scheme to `null` from the `"/abc/**"`".

## Environment info

```
$ ./mvnw -version
Apache Maven 3.5.2 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T03:58:13-04:00)
Maven home: /Users/jbard/.m2/wrapper/dists/apache-maven-3.5.2-bin/28qa8v9e2mq69covern8vmdkj0/apache-maven-3.5.2
Java version: 1.8.0_144, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.12.6", arch: "x86_64", family: "mac"
```
