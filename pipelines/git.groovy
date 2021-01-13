node {
  git branch: 'main', url: 'https://github.com/hiddenwaffle/jinx.git'
  sh 'ls -lrt'
  sh 'git log | tail -n 100'
  sh 'pwd'
}
