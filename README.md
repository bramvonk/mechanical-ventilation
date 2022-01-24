# mechanical-ventilation-setter
Web application with REST interface for setting my Dutch 3-setting mechanical ventilation with a 2-relay module linked to a Raspberry pi

## Requirements
Requires JDK 8.

Uses pi4j 1.3, which depends on WiringPi, which has been deprecated. Install/compile your own using:

    #sudo apt-get remove wiringpi -y
    sudo apt-get --yes install git-core gcc make
    cd ~
    git clone https://github.com/WiringPi/WiringPi --branch master --single-branch wiringpi
    cd ~/wiringpi
    sudo ./build

## Installation
First build it using:

    mvn package

Then make it a service: see ```mechanicalventilation.service.example``` .

## Usage
    curl -H 'Content-Type: application/json' -X PUT -d '{"setting":"LOW"}' http://localhost:8080/mechanicalventilation
    curl -H 'Content-Type: application/json' -X PUT -d '{"setting":"MEDIUM"}' http://localhost:8080/mechanicalventilation
    curl -H 'Content-Type: application/json' -X PUT -d '{"setting":"HIGH"}' http://localhost:8080/mechanicalventilation
