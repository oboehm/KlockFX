# KlockFX auf RasPi Zero

## Java 8

Java 11 wird auf dem RasPi Zero nicht unterstützt.
Daher muss Java 8 auf dem RasPi installiert werden:

    sudo apt install openjdk-8-jdk

Allerdings hat Oracle irgendwann mal den Support zu JavaFX eingestellt.
Daher wird JavaFX nach Anleitung aus [JavaFX und Raspbian OS](https://www.sbuechler.de/tipps-tricks/94-javafx-und-raspbian-os) installiert:

    unzip armv6hf-sdk-8.60.12.zip -d /home/pi/Downloads/
    readlink -f $(which java)
    cd Downloads/
    sudo chown -R root:root armv6hf-sdk
    cd armv6hf-sdk
    sudo mv lib/javafx-mx.jar  /usr/lib/jvm/java-8-openjdk-armhf/lib/
    cd rt/lib/
    mv j* /usr/lib/jvm/java-8-openjdk-armhf/jre/lib/
    sudo mv j* /usr/lib/jvm/java-8-openjdk-armhf/jre/lib/
    sudo mv arm/* /usr/lib/jvm/java-8-openjdk-armhf/jre/lib/arm/
    sudo mv ext/* /usr/lib/jvm/java-8-openjdk-armhf/jre/lib/ext/

Danach kann die JAR-Datei kopiert und gestartet werdenL

    scp target/KlockFX-0.7-uberjar.jar pi@raspberrypi.fritz.box:
    java -jar KlockFX-0.7-uberjar.jar

Um die Anwendung automatisch beim Booten zu starten, ruft man sie am besten in

    /etc/rc.local

auf und achtet darauf, dass die richtige Zeitzone eingestellt ist.
Falls nicht, kann sie über

    sudo dpkg-reconfigure tzdata

konfiguriert werden.
