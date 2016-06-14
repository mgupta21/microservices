# Dockerizing Microservices

## Note

**Weather Service was configured for port 8082 in the last few lessons, so remember to use the version of ```weather-service``` included in this lesson's working files!**

## Vagrant

This lesson requires the *services* Vagrant Box. You can start it by running the following command from ```vagrant-boxes/```:

```
vagrant up services
```

## Important IP Addresses

The following IP addresses are used on my machine (Mac) during the video. Below are the IP addresses used. The Vagrant IP addresses should always be same, but depending on your operating system, the IP address of your localhost and Docker host could be different.

- **services** Vagrant Box: ```192.168.33.10```
- Docker Host:
  - Linux: ```localhost``` (Docker runs natively on Linux)
  - Mac: ```192.168.99.100```
- Local Host (Private Network): ```192.168.33.1``` Used when referring to local machine from within a Docker container or Vagrant Box
- Local Host: ```localhost``` (Used when running applications in IDE)

## Important URLs

The following URLs are used in this lesson:

- Consul Server: ```http://192.168.33.10:8500```
- Consul Agent (Local Docker): ```http://192.168.99.100:8500```
- Open Weather Map: ```http://openweathermap.org/api```
- Weather Service running on Docker: ```http://192.168.99.100:8080/weather/{city}```. Example: ```http://192.168.99.100:8080/weather/chicago``` returns weather data for Chicago IL
- Docker Hub: ```https://hub.docker.com```

## Application Configuration

The following environment variables are set when running Weather Service in Docker:

- ```ADVERTISED_HOST=192.168.99.100```
- ```ADVERTISED_PORT=8080```
- ```CONSUL_HOST=192.168.99.100``` (Refers to local Consul running in Docker)

## Commands

This lesson requires a local Consul running in Docker that joins with the Consul running in the **services** Vagrant Box:

```
docker run -p 8300:8300 -p 8301:8301 -p 8301:8301/udp -p 8302:8302 -p 8302:8302/udp -p 8400:8400 -p 8500:8500 -d progrium/consul -join 192.168.33.10 -advertise 192.168.99.100
```

Commands to Dockerize and run Weather Service:

Set System Property to push to Docker Registry:

```
export GRADLE_OPTS=-Ddocker.push=true
```

To login to a registry (you'll be prompted for your credentials):

```
docker login 
```

To run the Gradle build:

```
./gradlew clean dockerImage
```

To run Weather Service in the Docker host:

```
docker run -p 8080:8080 -e ADVERTISED_PORT=8080 -e ADVERTISED_HOST=192.168.99.100 -e CONSUL_HOST=192.168.99.100 -e APP_ID=<your app ID> rickfast/weather-service:1.0
```