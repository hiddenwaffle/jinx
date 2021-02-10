// Parallel execution; notice how parallel() is an outer level to node()
stage('Parallel') {
  parallel one: {
    node {
      test('one')
    }
  }, two: {
    node {
      test('two')
    }
  }, three: {
    node {
      test('three')
    }
  }
}

void test(a) {
  echo "TEST {a}"
}
