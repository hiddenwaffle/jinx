node {
    git branch: 'main', url: 'https://github.com/hiddenwaffle/jinx.git'
    withCredentials([sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK')]) {
      sh 'echo "HEPBURN_PK: ${HEPBURN_PK}"'
      sh 'type ansible'
    }
}
