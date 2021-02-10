// Parallel execution; notice how parallel() is an outer level to node()
node {
    stage('Parallel') {
      parallel one: {
          test('one')
      }, two: {
          test('two')
      }, three: {
          test('three')
      }
    }
}

void test(a) {
    echo "TEST ${a}"
}
