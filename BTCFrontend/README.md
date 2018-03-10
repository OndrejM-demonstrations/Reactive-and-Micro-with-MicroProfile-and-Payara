TODO

OK - add react
- create a page to display BTC rate and get it via REST request to backend
- poll BTC rate (config rate, default 10s) - then configure it via sys props and then dynamically via DAS
- REST endpoint to provide BTC rate
- provide rate from bitstamp (config URL)
- add retry, fallback, circuit breaker (only works for synchronous code)
- add fallback - show last known value stored in Hazelcast/JCache)
- read data from Kafka (configure Kafka via MP config if possible)
- send data from Kafka to frontend using CDI event bus
- display incoming data from backend about BTC Tx in a table via SSE
