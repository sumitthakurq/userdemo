pipeline {
  agent any

  environment {
    DOCKERHUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIAL')
 //   VERSION = "${env.BUILD_ID}"

  }

  tools {
    maven "Maven"
  }

  stages {

    stage('Maven Build'){
        steps{
        sh 'mvn clean package '
        }
    }
/*
    stage('SonarQube Analysis') {
  steps {
    sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://35.177.176.53:9000/ -Dsonar.login=squ_f5774eac2b3c6528a9503f9385607a78f40fd460'
  }
}


   stage('Check code coverage') {
            steps {
                script {
                    def token = "squ_f5774eac2b3c6528a9503f9385607a78f40fd460"
                    def sonarQubeUrl = "http://35.177.176.53:9000/api"
                    def componentKey = "com.web:usersb"
                    def coverageThreshold = 60.0

                    def response = sh (
                        script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                        returnStdout: true
                    ).trim()

                    def coverage = sh (
                        script: "echo '${response}' | jq -r '.component.measures[0].value'",
                        returnStdout: true
                    ).trim().toDouble()

                    echo "Coverage: ${coverage}"

                    if (coverage < coverageThreshold) {
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        } 

*/
      stage('Docker Build and Push') {
      steps {
          sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
          sh 'docker build -t sumit30/usersb-v1 .'
          sh 'docker push sumit30/usersb-v1'
      }
    } 


     stage('Cleanup Workspace') {
      steps {
        deleteDir()
       
      }
    }



    stage('Update Image Tag in GitOps') {
      steps {
         checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[ credentialsId:
         'git-ssh', url: 'https://github.com/udemy-dev-withK8s-AWS-codedecode/deployment-folder.git']])
        script {
       sh '''
          sed -i "s/image:.*/image: sumit30\\/usersb-v1/" aws/usersb-manifest.yml
        '''
          sh 'git checkout master'
          sh 'git add .'
          sh 'git commit -m "Update image tag"'
        sshagent(['git-ssh'])
            {
                  sh('git push')
            }
        }
      }
    }

  }

}


