def version

node {
	withCredentials([[$class: 'StringBinding', credentialsId: 'docker', variable: 'password']]) { 
    	git url: 'https://github.com/rickfast/weather-service.git'

    	Properties properties = new Properties()
		def file = readFile('gradle.properties')
		properties.load(new StringReader(file))

		version = "${properties.getProperty('version')}.${env.BUILD_NUMBER}"

    	sh """
      	docker login -u rickfast -p ${env.password} -e rick.t.fast@gmail.com
      	export GRADLE_OPTS=-Ddocker.push=true
      	./gradlew clean dockerImage
    	"""
  }
}

node {
	git url: 'https://github.com/rickfast/oreilly-marathon-deploy.git'
	sh """
	  ./marathon rickfast/weather-service:$version weather-service LOGSTASH_ENABLED=true
	"""
}