// Based on documentation: https://www.jenkins.io/doc/book/pipeline/

node {
  withEnv(["TEST_ENV=helloworld"]) {
    sh 'echo $TEST_ENV'
  }
  sh 'echo "Is TEST_ENV still defined? <<$TEST_ENV>>"'
  echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
  myvar = """${sh(
    returnStdout: true,
    script: 'echo "this_is_my_vars_value"'
  )}"""
  echo "myvar == ${myvar}"
  // Groovy interpolation
  withEnv(["mytestvar=200"]) {
    mytestvar = 100
    echo '${mytestvar}' // ${mytestvar}
    echo '$mytestvar' // $mytestvar
    echo "${mytestvar}" // 100
    echo "$mytestvar" // 100
    sh "echo $mytestvar" // 100
    sh 'echo $mytestvar' // 200
  }
  // Credential interpolation testing - START
  https://www.jenkins.io/doc/book/pipeline/jenkinsfile/#interpolation-of-sensitive-environment-variables
  stage('Insecure') {
    withCredentials([string(credentialsId: 'credential1', variable: 'credential1')]) {
      // credential1 = 'doesthisoverwrite?1' // yes it would
      // This sh gives a warning because it is using Groovy interpolation of a credential
      sh "echo 'credentials(): ${credential1}' > 111insecure.out"
    }
  }
  stage('More Secure (?)') {
      withCredentials([string(credentialsId: 'credential1', variable: 'credential1')]) {
        // This sh does not give a warning because it is using shell interpolation
        sh 'echo "credentials(): $credential1" > 222A-more-secure-question-mark.out'
        credential1 = 'doesthisoverwrite?2' // no it does not
        sh 'echo "credentials(): $credential1" > 222B-more-secure-question-mark.out'
      }
  }
  // Credential interpolation testing - END
  properties([
    parameters([
      string(
        defaultValue: 'Hello',
        description: 'How should I greet the world?',
        name: 'Greeting'
      )
    ])
  ])
  stage('Use of Params?') {
    echo "<<${params.Greeting}>> World!"
  }
  try {
    sh 'asdf'
  } catch (Exception e) {
    echo "in catch <<$e>>"
  } finally {
    echo 'in finally'
  }
  archiveArtifacts artifacts: '*.out'
}

// Parallel execution; notice how parallel() is an outer level to node()
stage('Parallel') {
  parallel one: {
    node {
      echo 'IN ONE - Before'
      sleep 2
      echo 'IN ONE - After'
    }
  }, two: {
    node {
      echo 'IN TWO - Before'
      sleep 1
      echo 'IN TWO - After'
    }
  }
}
