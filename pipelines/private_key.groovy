node {
    withCredentials([sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK')]) {
      sh 'echo "HEPBURN_PK: ${HEPBURN_PK}"'
    }
}
