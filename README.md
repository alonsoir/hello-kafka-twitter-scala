	This is a tiny project that i forked from activator-hello-kafka as a first step to interact with kafka 
	and twitter first, after that i am going to integrate it with spark-streaming and some nosql database 
	like mongodb and cassandra...
 
	The project uses sbt-pack to create some unix commands, so, before start running, make sure there is a 
	kafka broker, a mongo instance and a cassandra instance running in your local machine.
	
	MacBook-Pro-Retina-de-Alonso:Downloads aironman$ cd hello-kafka-twitter-scala-develop/
	MacBook-Pro-Retina-de-Alonso:hello-kafka-twitter-scala-develop aironman$ sbt
	[info] Loading project definition from /Users/aironman/Downloads/hello-kafka-twitter-scala-develop/project
	[info] Updating {file:/Users/aironman/Downloads/hello-kafka-twitter-scala-develop/project/}hello-kafka-twitter-scala-develop-build...
	[info] Resolving org.fusesource.jansi#jansi;1.4 ...
	[info] Done updating.
	[info] Set current project to hello-kafka-twitter-scala-develop (in build file:/Users/aironman/Downloads/hello-kafka-twitter-scala-develop/)
	> pack
	[info] Updating {file:/Users/aironman/Downloads/hello-kafka-twitter-scala-develop/}hello-kafka-twitter-scala-develop...
	[info] Resolving org.scala-lang#scalap;2.10.4 ...
	[info] Done updating.
	[warn] Scala version was updated by one of library dependencies:
	[warn] 	* org.scala-lang:scala-library:(2.10.4, 2.10.2, 2.10.3, 2.10.0, 2.10.1) -> 2.10.5
	[warn] To force scalaVersion, add the following:
	[warn] 	ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
	[warn] Run 'evicted' to see detailed eviction warnings
	[info] Compiling 19 Scala sources to /Users/aironman/Downloads/hello-kafka-twitter-scala-develop/target/scala-2.10/classes...
	[warn] there were 2 deprecation warning(s); re-run with -deprecation for details
	[warn] one warning found
	[warn] Multiple main classes detected.  Run 'show discoveredMainClasses' to see the list
	[info] Packaging /Users/aironman/Downloads/hello-kafka-twitter-scala-develop/target/scala-2.10/hello-kafka-twitter-scala-develop_2.10-0.1-SNAPSHOT.jar ...
	[info] Done packaging.
	[info] Creating a distributable package in target/pack
	[info] Copying libraries to target/pack/lib
	[info] project jars:
	....
	[info] Generating target/pack/VERSION
	[info] done.
	[success] Total time: 14 s, completed 11-abr-2016 17:10:19
	> 

	After seeing this, the unix commands are ready to test.

	twitter-producer is going to push tweets in real time to the topic named Obq6c

	MacBook-Pro-Retina-de-Alonso:bin aironman$ pwd
	/Users/aironman/Downloads/hello-kafka-twitter-scala-develop/target/pack/bin
	MacBook-Pro-Retina-de-Alonso:bin aironman$ ./twitter-producer Obq6c
	topicName is Obq6c
	log4j:WARN No appenders could be found for logger (kafka.utils.VerifiableProperties).
	log4j:WARN Please initialize the log4j system properly.
	log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
	id is 719446195169431552
	url is https://t.co/dnlYtPItZX
	retweet_count is 0
	retweeted is false
	id is 719446209471979520
	text is 俺下手すぎてやばい https://t.co/dnlYtPItZX
	place is null
	lang is ja
	id is 719446195169431552
	url is https://t.co/dnlYtPItZX
	screen_name is makki_vain
	id is 3803136794
	lang is ja
	time_zone is null
	url is null
	name is makkii
	...


	kafka-connector is going to pull data from the topic Obq6c, 192.168.59.3:9092 is the kafka broker

	MacBook-Pro-Retina-de-Alonso:bin aironman$ ./kafka-connector 192.168.59.3:9092 Obq6c
	Initializing Streaming Spark Context and kafka connector...
	Using Spark's default log4j profile: org/apache/spark/log4j-defaults.properties
	16/04/11 17:20:43 INFO SparkContext: Running Spark version 1.6.1

	Before you run these commands, you need a mongo instance running:

	MacBook-Pro-Retina-de-Alonso:~ aironman$ mongod
	2016-04-11T17:25:36.631+0200 I CONTROL  [initandlisten] MongoDB starting : pid=18557 port=27017 dbpath=/data/db 64-bit host=MacBook-Pro-Retina-de-Alonso.local
	2016-04-11T17:25:36.631+0200 I CONTROL  [initandlisten] db version v3.2.4
	2016-04-11T17:25:36.631+0200 I CONTROL  [initandlisten] git version: e2ee9ffcf9f5a94fad76802e28cc978718bb7a30
	2016-04-11T17:25:36.631+0200 I CONTROL  [initandlisten] allocator: system
	2016-04-11T17:25:36.631+0200 I CONTROL  [initandlisten] modules: none
	2016-04-11T17:25:36.631+0200 I CONTROL  [initandlisten] build environment:
	2016-04-11T17:25:36.631+0200 I CONTROL  [initandlisten]     distarch: x86_64
	2016-04-11T17:25:36.631+0200 I CONTROL  [initandlisten]     target_arch: x86_64
	2016-04-11T17:25:36.631+0200 I CONTROL  [initandlisten] options: {}
	2016-04-11T17:25:36.632+0200 I -        [initandlisten] Detected data files in /data/db created by the 'wiredTiger' storage engine, so setting the active storage engine to 'wiredTiger'.
	2016-04-11T17:25:36.632+0200 I STORAGE  [initandlisten] wiredtiger_open config: create,cache_size=9G,session_max=20000,eviction=(threads_max=4),config_base=false,statistics=(fast),log=(enabled=true,archive=true,path=journal,compressor=snappy),file_manager=(close_idle_time=100000),checkpoint=(wait=60,log_size=2GB),statistics_log=(wait=0),
	2016-04-11T17:25:37.699+0200 I CONTROL  [initandlisten] 
	2016-04-11T17:25:37.699+0200 I CONTROL  [initandlisten] ** WARNING: soft rlimits too low. Number of files is 256, should be at least 1000
	2016-04-11T17:25:37.706+0200 I NETWORK  [HostnameCanonicalizationWorker] Starting hostname canonicalization worker
	2016-04-11T17:25:37.706+0200 I FTDC     [initandlisten] Initializing full-time diagnostic data capture with directory '/data/db/diagnostic.data'
	2016-04-11T17:25:37.708+0200 I NETWORK  [initandlisten] waiting for connections on port 27017

	And a kafka broker:

	MacBook-Pro-Retina-de-Alonso:~ aironman$ kafka-server-start /usr/local/etc/kafka/server.properties
	[2016-04-11 10:08:25,678] INFO KafkaConfig values: 
		advertised.host.name = null
		metric.reporters = []
		quota.producer.default = 9223372036854775807
		offsets.topic.num.partitions = 50
		log.flush.interval.messages = 9223372036854775807
		auto.create.topics.enable = true
		controller.socket.timeout.ms = 30000
		log.flush.interval.ms = null
		principal.builder.class = class org.apache.kafka.common.security.auth.DefaultPrincipalBuilder
		replica.socket.receive.buffer.bytes = 65536
		min.insync.replicas = 1
		replica.fetch.wait.max.ms = 500
		num.recovery.threads.per.data.dir = 1
		ssl.keystore.type = JKS
		default.replication.factor = 1
		ssl.truststore.password = null
		log.preallocate = false
		sasl.kerberos.principal.to.local.rules = [DEFAULT]
		fetch.purgatory.purge.interval.requests = 1000
		...

	Lets check if we have data stored in the mongo instance:

	MacBook-Pro-Retina-de-Alonso:~ aironman$ mongo
	MongoDB shell version: 3.2.4
	connecting to: test
	Server has startup warnings: 
	2016-04-12T11:05:18.142+0200 I CONTROL  [initandlisten] 
	2016-04-12T11:05:18.142+0200 I CONTROL  [initandlisten] ** WARNING: soft rlimits too low. Number of files is 256, should be at least 1000
	> use alonsodb;
	switched to db alonsodb
	> db.tweets.find();
	{ "_id" : ObjectId("56faadd88d6a6aba39593d73"), "id" : ISODate("2016-03-29T16:31:20.468Z"), "tweets" : "description {\n  \"createdAt\": \"Mar 29, 2016 6:31:12 PM\",\n  \"id\": 714852418983370752,\n  \"text\": \"RT @mo_younapi: 明日は大阪だよ！\\n物販では、ゆるめるモ！グッズのようなぴグッズも販売されるかも！缶バッジとTシャツとリストバンドかな？\\n\\nそれと、出番後にも15分くらいだけ物販やるかも！その場合はサインなしツーチェキのみかなあ。\",\n  \"source\": \"\\u003ca href\\u003d\\\"http://twitter.com\\\" rel\\u003d\\\"nofollow\\\"\\u003eTwitter Web Client\\u003c/a\\u003e\",\n  \"isTruncated\": false,\n  \"inReplyToStatusId\": -1,\n  \"inReplyToUserId\": -1,\n  \"isFavorited\": false,\n  \"retweetCount\": 0,\n  \"isPossiblySensitive\": false,\n  \"contributorsIDs\": [],\n  \"retweetedStatus\": {\n    \"createdAt\": \"Mar 29, 2016 5:22:25 PM\",\n    \"id\": 714835107480113153,\n    \"text\": \"明日は大阪だよ！\\n物販では、ゆるめるモ！グッズのようなぴグッズも販売されるかも！缶バッジとTシャツとリストバンドかな？\\n\\nそれと、出番後にも15分くらいだけ物販やるかも！その場合はサインなしツーチェキのみかなあ。\",\n    \"source\": \"\\u003ca href\\u003d\\\"http://twitter.com/download/iphone\\\" rel\\u003d\\\"nofollow\\\"\\u003eTwitter for iPhone\\u003c/a\\u003e\",\n    \"isTruncated\": false,\n    \"inReplyToStatusId\": 714679679182090240,\n    \"inReplyToUserId\": 1883898606,\n    \"isFavorited\": false,\n    \"inReplyToScreenName\": \"mo_younapi\",\n    \"retweetCount\": 6,\n    \"isPossiblySensitive\": false,\n    \"contributorsIDs\": [],\n    \"userMentionEntities\": [],\n    \"urlEntities\": [],\n    \"hashtagEn
	...
	> db.tweets.count();
	15168

	The mongo instance has data, yuhu! :)

	

	TODO

	Integrate kafka with Twitter. DONE!
	
	Integrate with spark-streaming.DONE!
	
	Integrate with Mongo.DONE!
	
	Integrate the kafka connector with yaml dependencies 
	so i can change in real time the initial parameters..PENDING
	Check TwitterStream.scala TwitterClient object...

	Integrate with Cassandra.PENDING
	Use the library from stratio...
	
Interesting links

	http://derekwyatt.org/2011/09/01/heres-one-of-the-reasons-why-monads-are-awesome/
