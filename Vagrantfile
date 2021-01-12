Vagrant.configure("2") do |config|
  config.vm.define "hepburn" do |hepburn|
    hepburn.vm.box = "centos/7"
    hepburn.ssh.port = 2222
  end
  config.vm.define "peck" do |peck|
    peck.vm.box = "centos/7"
    peck.ssh.port = 2200
  end
end
