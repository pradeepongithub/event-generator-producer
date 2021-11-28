# Getting Started
event-generator-producer microservice produces the employee card swipe events on every 30 sec

### Build & run container app 

* Run this command from root of the project to build the docker container
```` 
docker build --rm -t producer .
````
* Run this command to run the docker container & connect to the kafka broker inside net_kafka network
```` 
docker run -it --rm --network net_kafka producer
````

### Broker dependencies has been specified in docker-compose file here:
  
```` 
  https://github.com/pradeepongithub/event-generator-consumer/blob/master/docker-compose.yml
````