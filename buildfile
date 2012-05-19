# Generated by Buildr 1.4.6, change to your liking
require 'buildr/scala'

# Version number for this release
VERSION_NUMBER = "1.0.1"

# Group identifier for your projects
GROUP = "echo"
COPYRIGHT = "Callum Stott 2012"

# Specify Maven 2.0 remote repositories here, like this:
repositories.remote << "http://www.ibiblio.org/maven2/"

# Project setup
Project.local_task :docs
Project.local_task :console

define "echo" do
  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT

  package :jar
  test.using :specs
  
  task :console => :compile do
    system 'scala -classpath target:target/classes'
  end
  
  task :docs => :compile do
    system 'scaladoc -classpath target:target/classes -d target/doc $(find . -name "*.scala" | grep -v "test")'
  end
end
