# Simple Marathon Deploy

## Vagrant

This lesson requires the *services* Vagrant Box. You can start it by running the following command from ```vagrant-boxes/```:

```
vagrant up services
```

This lesson requires the *mesos* Vagrant Box. You can start it by running the following command from ```vagrant-boxes/```:

```
vagrant up mesos
```

## Important IP Addresses

The following IP addresses are used on my machine (Mac) during the video. Below are the IP addresses used. The Vagrant IP addresses should always be same, but depending on your operating system, the IP address of your localhost and Docker host could be different.

- **services** Vagrant Box: ```192.168.33.10```
- **mesos** Vagrant Box: ```10.141.141.10```

## Important URLs

The following URLs are used in this lesson:

- Consul Server: ```http://192.168.33.10:8500```
- Consul Agent (On **mesos** Vagrant Box): ```http://10.141.141.10:8500```
- Marathon: ```http://10.141.141.10:8080```

## POSTMa