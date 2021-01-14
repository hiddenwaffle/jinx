node {
    git branch: 'main', url: 'https://github.com/hiddenwaffle/jinx.git'
    docker.image('python:3.9.1').inside {
        stage('Setup') {
            sh 'curl -sSL https://raw.githubusercontent.com/python-poetry/poetry/master/get-poetry.py | python -'
            sh '$HOME/.poetry/bin/poetry install'
            sh '''mkdir ~/.ssh && chmod 700 ~/.ssh '''
            sh '''echo "$(getent hosts host.docker.internal | awk '{ print $1 }') hepburn.local" >> /etc/hosts '''
            sh '''echo "[hepburn.local]:2222 ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBO6WMqyabG5PbImHJTuuNaaVa2glHxrScWDBrFmROenvA4miDHPx5Q5ZqrSqjwJ4CtneDosKgqULVfm09S0hLGg=" >> ~/.ssh/known_hosts '''
            sh '''echo "$(getent hosts host.docker.internal | awk '{ print $1 }') peck.local" >> /etc/hosts '''
            sh '''echo "[peck.local]:2200 ecdsa-sha2-nistp256 AAAAE2VjZHNhLXNoYTItbmlzdHAyNTYAAAAIbmlzdHAyNTYAAABBBKTORHuY2wx1VjenGYm8y6q3cyxz7IfFYDAPdZDnLJyV/9OJhMLZXd8jHg+XsrnNS25EoTg9mNWhDSFQCiFJdaA=" >> ~/.ssh/known_hosts '''
        }
        stage('Do it') {
            withCredentials([sshUserPrivateKey(credentialsId: 'hepburn-pk', keyFileVariable: 'HEPBURN_PK')]) {
                sh '''$HOME/.poetry/bin/poetry run ansible -i inventory/hosts.yml roman -l hepburn.local -a 'free -h' --private-key $HEPBURN_PK '''
                //sh '''ssh hepburn.local -p 2222 -i $HEPBURN_PK -v 'df -H' '''
            }
        }
    }
}
