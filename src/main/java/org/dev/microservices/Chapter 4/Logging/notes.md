# Logging

## Vagrant

This lesson requires the *services* Vagrant Box. You can start it by running the following command from ```vagrant-boxes/```:

```
vagrant up services
```

## Important IP Addresses

The following IP addresses are used on my machine (Mac) during the video. Below are the IP addresses used. The Vagrant IP addresses should always be same, but depending on your operating system, the IP address of your localhost and Docker host could be different.

- **services** Vagrant Box: ```192.168.33.10```- 
- Docker Host:
  - Linux: ```localhost``` (Docker runs natively on Linux)
  - Mac: ```192.168.99.100```
- Local Host (Private Network): ```192.168.33.1``` Used when referring to local machine from within a Docker container or Vagrant Box
- Local Host: ```localhost``` (Used when running applications in IDE)

## Important URLs

The following URLs are used in this lesson:

- Consul Server: ```http://192.168.33.10:8500```
- Consul Agent (Local Docker): ```http://192.168.99.100:8500```
- Kibana on **services** Vagrant Box: ```http://192.168.33.10:5601```
- Elasticsearch on **services** Vagrant Box: ```http://192.168.33.10:9200```

## Application Configuration

The following environment variables are set when running Sample Service from IDE:

- ```ADVERTISED_HOST=192.168.33.1```
- ```ADVERTISED_PORT=8080```
- ```CONSUL_HOST=192.168.99.100``` (Refers to local Consul running in Docker)
- ```LOGSTASH_HOST=192.168.99.100``` (Refers to local Logstash running in Docker)
- ```LOGSTASH_ENABLED=true```

## Docker Commands

Consul in Docker that joins with the Consul running in the **services** Vagrant Box:

```
docker run -p 8300:8300 -p 8301:8301 -p 8301:8301/udp -p 8302:8302 -p 8302:8302/udp -p 8400:8400 -p 8500:8500 -d progrium/consul -join 192.168.33.10 -advertise 192.168.99.100
```

Logstash running on Docker host:

```
docker run -p 51515:51515 logstash -e 'input { tcp { port => 51515 codec => json_lines } } output { elasticsearch { host => "192.168.33.10" protocol => "http"} }'
```
